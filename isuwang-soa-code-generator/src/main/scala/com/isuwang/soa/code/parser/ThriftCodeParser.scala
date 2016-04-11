package com.isuwang.soa.code.parser

import java.io._
import java.util

import com.github.mustachejava.Mustache
import com.google.common.base.Charsets
import com.google.common.io.CharStreams
import com.isuwang.soa.core
import com.isuwang.soa.core.metadata.TEnum.EnumItem
import com.isuwang.soa.core.metadata._
import com.twitter.scrooge.ast._
import com.twitter.scrooge.frontend.{Importer, ResolvedDocument, ThriftParser, TypeResolver}
import com.twitter.scrooge.java_generator._

import scala.collection.JavaConversions._
import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.util.control.Breaks._


/**
  * Thrift Code 解析器
  *
  * @author craneding
  * @date 15/7/22
  */
class ThriftCodeParser {

  private val templateCache = new TrieMap[String, Mustache]
  private val docCache = new mutable.HashMap[String, Document]()
  private val enumCache = new util.ArrayList[TEnum]()
  private val structCache = new util.ArrayList[core.metadata.Struct]()
  private val serviceCache = new util.ArrayList[core.metadata.Service]()

  /**
    * 生成文档
    *
    * @param resource 源文件
    * @return 文档
    */
  private def generateDoc(resource: String): Document = {

    val homeDir = resource.substring(0, if (resource.lastIndexOf("/") == -1) resource.lastIndexOf("\\") else resource.lastIndexOf("/"))

    val br = new BufferedReader(new InputStreamReader(new FileInputStream(resource), Charsets.UTF_8))
    val txt = CharStreams.toString(br)

    val importer = Importer(Seq(homeDir))
    val parser = new ThriftParser(importer, true)
    val doc = parser.parse(txt, parser.document)

    try {
      TypeResolver()(doc).document
    } finally {
      println(s"parse ${resource} success")
    }
  }

  /**
    * 获取生成器
    *
    * @param doc0 文档结构
    * @param genHashcode 是否生成HashCode
    * @return 生成器
    */
  private def getGenerator(doc0: Document, genHashcode: Boolean = false): ApacheJavaGenerator = {
    new ApacheJavaGenerator(new ResolvedDocument(doc0, new TypeResolver()), "thrift", templateCache, genHashcode = genHashcode)
    //new ApacheJavaGenerator(Map(), "thrift", templateCache, genHashcode = genHashcode)
  }

  private def toDocString(docstring: scala.Option[String]): String =
    if (docstring == None)
      null
    else {
      val result = docstring.get.toString();

      val in = new BufferedReader(new StringReader(result));

      val buffer = new StringBuilder();

      breakable {
        while (true) {
          var line = in.readLine();

          if (line == null)
            break;

          //          line = line.trim();

          if (line.matches("^\\s*[*]{1,2}/$")) {
            line = ""
          } else if (line.matches("^\\s*[*].*$")) {
            line = line.trim();
            line = line.substring(1)
          } else if (line.matches("^\\s*/[*]{2}.*$")) {
            line = line.trim();
            line = line.substring("/**".length)
          }

          if (line.length > 0) {
            if (buffer.length > 0)
              buffer.append("\n")
            buffer.append(line);
          } else {
            buffer.append("\n");
          }
        }
      }

      in.close()

      //result = result.replace("/**", "").replace("**/", "").replace("*/", "").trim;
      //val pattern = "[ ]*[*][\\s]?".r;
      //result = (pattern replaceAllIn(result, ""));

      return buffer.toString();
    }


  private def toDataType(fieldType: FieldType, defaultDoc: Document, docString: String): DataType = {
    val dataType = new DataType()

    fieldType match {
      case _: BaseType =>
        val clazz = fieldType.getClass

        if (clazz == TI16.getClass) {
          dataType.setKind(DataType.KIND.SHORT)
        } else if (clazz == TI32.getClass) {
          dataType.setKind(DataType.KIND.INTEGER)
        } else if (clazz == TI64.getClass) {
          dataType.setKind(DataType.KIND.LONG)

          //2016-2-18 In order to generate Date type
          if (docString.toLowerCase.contains("@datatype(name=\"date\")"))
            dataType.setKind(DataType.KIND.DATE)

        } else if (clazz == TDouble.getClass) {
          dataType.setKind(DataType.KIND.DOUBLE)

          //2016-4-08 In order to generate BigDecimal type
          if (docString.toLowerCase.contains("@datatype(name=\"bigdecimal\")"))
            dataType.setKind(DataType.KIND.BIGDECIMAL)

        } else if (clazz == TByte.getClass) {
          dataType.setKind(DataType.KIND.BYTE)
        } else if (clazz == TBool.getClass) {
          dataType.setKind(DataType.KIND.BOOLEAN)
        } else if (clazz == TString.getClass) {
          dataType.setKind(DataType.KIND.STRING)
        } else {
          dataType.setKind(DataType.KIND.BINARY)
        }
      case EnumType(enum, scopePrefix) =>
        dataType.setKind(DataType.KIND.ENUM)

        val doc1 = if (scopePrefix != None) docCache(scopePrefix.get.name) else defaultDoc
        val enumController = new EnumController(enum, getGenerator(doc1), doc1.namespace("java"))

        dataType.setQualifiedName(enumController.namespace + "." + enumController.name)

      case StructType(struct, scopePrefix) =>
        dataType.setKind(DataType.KIND.STRUCT)

        val doc1 = if (scopePrefix != None) docCache(scopePrefix.get.name) else defaultDoc
        val structController = new StructController(struct, false, getGenerator(doc1), doc1.namespace("java"))

        dataType.setQualifiedName(structController.namespace + "." + structController.name)
      case _: ListType =>
        dataType.setKind(DataType.KIND.LIST)

        dataType.setValueType(toDataType(fieldType.asInstanceOf[ListType].eltType, defaultDoc, docString))
      case _: SetType =>
        dataType.setKind(DataType.KIND.SET)

        dataType.setValueType(toDataType(fieldType.asInstanceOf[SetType].eltType, defaultDoc, docString))
      case _: MapType =>
        dataType.setKind(DataType.KIND.MAP)

        dataType.setKeyType(toDataType(fieldType.asInstanceOf[MapType].keyType, defaultDoc, docString))
        dataType.setValueType(toDataType(fieldType.asInstanceOf[MapType].valueType, defaultDoc, docString))
      case _ =>
        dataType.setKind(DataType.KIND.VOID)
    }

    dataType
  }

  private def findEnums(doc: Document, generator: ApacheJavaGenerator): util.List[TEnum] = {
    val results = new util.ArrayList[TEnum]()

    doc.enums.foreach(e => {
      val controller = new EnumController(e, generator, doc.namespace("java"))

      val tenum = new TEnum()
      if (controller.has_namespace)
        tenum.setNamespace(controller.namespace)
      tenum.setName(controller.name)
      tenum.setDoc(toDocString(e.docstring))
      tenum.setEnumItems(new util.ArrayList[EnumItem]())

      for (index <- (0 until controller.constants.size)) {
        val enumFiled = controller.constants(index)

        val name = enumFiled.name
        val value = enumFiled.value.toString.toInt
        val docString = toDocString(e.values(index).docstring)

        val enumItem = new EnumItem()
        enumItem.setLabel(name)
        enumItem.setValue(value)
        enumItem.setDoc(docString)
        tenum.getEnumItems.add(enumItem)
      }

      results.add(tenum)
    })

    results
  }

  private def findStructs(doc0: Document, generator: ApacheJavaGenerator): List[core.metadata.Struct] =
    doc0.structs.toList.map { (struct: StructLike) =>
      val controller = new StructController(struct, false, generator, doc0.namespace("java"))

      new core.metadata.Struct {
        //this.setNamespace(if (controller.has_non_nullable_fields) controller.namespace else null)
        this.setNamespace(controller.namespace)
        this.setName(controller.name)
        this.setDoc(toDocString(struct.docstring))

        val fields0 = controller.allFields.zip(controller.fields).toList.map { case (field, fieldController) =>
          val tag0 = field.index.toString.toInt
          val name0 = field.originalName
          //val optional0 = fieldController.optional_or_nullable.toString.toBoolean
          val optional0 = field.requiredness.isOptional
          val docSrting0 = toDocString(field.docstring)
          var dataType0: DataType = null

          dataType0 = toDataType(field.fieldType, doc0, docSrting0)

          new core.metadata.Field {
            this.setTag(tag0)
            this.setName(name0)
            this.setOptional(optional0)
            this.setDoc(docSrting0)
            this.setDataType(dataType0)
            this.setPrivacy(true)
          }
        }

        this.setFields(fields0)
      }
    }


  private def findServices(doc: Document, generator: ApacheJavaGenerator): util.List[core.metadata.Service] = {
    val results = new util.ArrayList[core.metadata.Service]()

    doc.services.foreach(s => {
      val controller = new ServiceController(s, generator, doc.namespace("java"))

      val service = new core.metadata.Service()

      service.setNamespace(if (controller.has_namespace) controller.namespace else null)
      service.setName(controller.name)
      service.setDoc(toDocString(s.docstring))

      val methods = new util.ArrayList[Method]()
      for (tmpIndex <- (0 until controller.functions.size)) {
        val functionField = controller.functions(tmpIndex)
        //controller.functions.foreach(functionField => {
        val request = new core.metadata.Struct()
        val response = new core.metadata.Struct()

        request.setName(functionField.name + "_args")
        response.setName(functionField.name + "_result")

        val method = new Method()
        method.setName(functionField.name)
        method.setRequest(request)
        method.setResponse(response)
        method.setDoc(toDocString(s.functions(tmpIndex).docstring))

        if (method.getDoc != null && method.getDoc.contains("@IsSoaTransactionProcess"))
          method.setSoaTransactionProcess(true)
        else
          method.setSoaTransactionProcess(false)

        request.setFields(new util.ArrayList[core.metadata.Field]())
        response.setFields(new util.ArrayList[core.metadata.Field]())

        for (index <- (0 until functionField.fields.size)) {
          val field = functionField.fields(index)

          val tag = index + 1
          val name = field.name

          var docSrting = ""
          if (s.functions.get(tmpIndex).args.get(index).docstring.isDefined)
            docSrting = toDocString(s.functions.get(tmpIndex).args.get(index).docstring)

          val f = field.field_type.getClass.getDeclaredField("fieldType");
          f.setAccessible(true)
          val dataType = toDataType(f.get(field.field_type).asInstanceOf[FieldType], doc, docSrting)

          val tfiled = new core.metadata.Field()
          tfiled.setTag(tag)
          tfiled.setName(name)
          tfiled.setDoc(docSrting)
          tfiled.setDataType(dataType)
          tfiled.setOptional(field.optional)
          request.getFields.add(tfiled)
        }

        var docSrting = ""
        if (s.functions.get(tmpIndex).docstring.isDefined)
          docSrting = toDocString(s.functions.get(tmpIndex).docstring)

        val f = functionField.return_type.getClass.getDeclaredField("fieldType");
        f.setAccessible(true)

        var dataType: DataType = null
        if (f.get(functionField.return_type).getClass == com.twitter.scrooge.ast.Void.getClass) {
          dataType = new DataType()
          dataType.setKind(DataType.KIND.VOID)
        } else {
          dataType = toDataType(f.get(functionField.return_type).asInstanceOf[FieldType], doc, docSrting)
        }

        val tfiled = new core.metadata.Field()
        tfiled.setTag(0)
        tfiled.setName("success")
        tfiled.setDoc(docSrting)
        tfiled.setDataType(dataType)
        tfiled.setOptional(false)
        response.getFields.add(tfiled)

        methods.add(method)
      }

      service.setMethods(methods)

      results.add(service)
    })

    results
  }

  def toServices(resources: Array[String], serviceVersion: String): util.List[core.metadata.Service] = {
    resources.foreach(resource => {
      val doc = generateDoc(resource)

      docCache.put(resource.substring(resource.lastIndexOf(File.separator) + 1, resource.lastIndexOf(".")), doc)
    })

    docCache.values.foreach(doc => {
      val generator = getGenerator(doc)

      enumCache.addAll(findEnums(doc, generator))

      structCache.addAll(findStructs(doc, generator))

      serviceCache.addAll(findServices(doc, generator))
    })

    for (index <- (0 until serviceCache.size())) {
      val service = serviceCache.get(index)

      service.setEnumDefinitions(enumCache)
      service.setStructDefinitions(structCache)
      service.setMeta(new core.metadata.Service.ServiceMeta {
        if (serviceVersion != null && !serviceVersion.trim.equals(""))
          this.version = serviceVersion.trim
        else
          this.version = "1.0.0"
        this.timeout = 30000
      })
    }

    return serviceCache;
  }

}
