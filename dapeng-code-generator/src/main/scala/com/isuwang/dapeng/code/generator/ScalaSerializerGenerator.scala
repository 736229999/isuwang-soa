package com.isuwang.dapeng.code.generator

import java.util

import com.isuwang.dapeng.core.metadata.DataType.KIND
import com.isuwang.dapeng.core.metadata._

import scala.xml.Elem

class ScalaSerializerGenerator extends CodeGenerator {

  override def generate(services: util.List[Service], outDir: String, generateAll:Boolean , structs: util.List[Struct], enums:util.List[TEnum]): Unit = {}

  override def generate(services: util.List[Service], outDir: String): Unit = {

  }
    def toStructSerializerTemplate(serviceName:String, struct:Struct ): Elem ={
    return {
      <div> package com.isuwang.soa.scala.serializer;

        import com.isuwang.dapeng.core._
        import com.isuwang.org.apache.thrift._
        import com.isuwang.org.apache.thrift.protocol._

        /**
        * Autogenerated by Dapeng-Code-Generator (1.2.1)
        *
        * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
        *  @generated
        **/

        <div>class {struct.name}Serializer extends TCommonBeanSerializer[{struct.getNamespace() + "." + struct.name}]<block>
          {getReadMethod(struct,"")}{getWriteMethod(struct,"")}{getValidateMethod(struct,"")}

          @throws[TException]
          override def toString(bean: {struct.namespace}.{struct.name}): String = if (bean == null) "null" else bean.toString

        </block>
        </div>
        </div>
    }
  }
  def toArgsMethodSerializerTemplate(serviceName:String,method:Method): Elem ={
    return {
      <div> package com.isuwang.soa.scala.serializer;

        import com.isuwang.dapeng.core._
        import com.isuwang.org.apache.thrift._
        import com.isuwang.org.apache.thrift.protocol._

        import com.isuwang.soa.scala.{serviceName}Codec;

        /**
        * Autogenerated by Dapeng-Code-Generator (1.2.1)
        *
        * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
        *  @generated
        **/
        class {method.name.charAt(0).toUpper + method.name.substring(1)}_argsSerializer extends TCommonBeanSerializer[{serviceName}Codec.{method.name}_args]<block>
        {getReadMethod(method.getRequest,serviceName+"Codec.")}{getWriteMethod(method.getRequest,serviceName+"Codec.")}{getValidateMethod(method.getRequest,serviceName+"Codec.")}

        override def toString(bean: {serviceName}Codec.{method.name}_args): String = if(bean == null)  "null" else bean.toString
      </block>
      </div>
    }
  }
  def toResultMethodSerializerTemplate(serviceName:String,method:Method): Elem ={
    return {
      <div> package com.isuwang.soa.scala.serializer;

        import com.isuwang.dapeng.core._
        import com.isuwang.org.apache.thrift._
        import com.isuwang.org.apache.thrift.protocol._

        import com.isuwang.soa.scala.{serviceName}Codec;

        /**
        * Autogenerated by Dapeng-Code-Generator (1.2.1)
        *
        * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
        *  @generated
        **/
        class {method.name.charAt(0).toUpper + method.name.substring(1)}_resultSerializer extends TCommonBeanSerializer[{serviceName}Codec.{method.name}_result]<block>

        @throws[TException]
        override def read(iprot: TProtocol): {serviceName}Codec.{method.name}_result = <block>

          var schemeField: com.isuwang.org.apache.thrift.protocol.TField = null
          iprot.readStructBegin

          {if(method.response.fields.get(0).dataType.kind != KIND.VOID) <div>var success : {toScalaDataType(method.response.fields.get(0).dataType)} = {getDefaultValueWithType(method.response.fields.get(0).dataType)}</div>}

          while (schemeField == null || schemeField.`type` != com.isuwang.org.apache.thrift.protocol.TType.STOP) <block>

            schemeField = iprot.readFieldBegin

            schemeField.id match <block>
              case 0 =>
              schemeField.`type` match <block>
                case {toThriftDateType(method.response.fields.get(0).dataType)} =>  {if(method.response.fields.get(0).dataType.kind != KIND.VOID) <div>success = {getScalaReadElement(method.response.fields.get(0).dataType, 0)}</div> else <div>com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)</div>}
                case _ => com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)
              </block>
              case _ => com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)
            </block>

            iprot.readFieldEnd
          </block>

          iprot.readStructEnd
          val bean = {serviceName}Codec.{method.name}_result({if(method.response.fields.get(0).dataType.kind != KIND.VOID) <div>success</div>})
          validate(bean)

          bean
        </block>

        {getWriteMethod(method.getResponse,serviceName+"Codec.")}
        {getValidateMethod(method.getResponse,serviceName+"Codec.")}

        override def toString(bean: {serviceName}Codec.{method.name}_result): String = if(bean == null)  "null" else bean.toString
      </block>

      </div>
    }

  }

  def toMetadataArgsSerializerTemplate(serviceName:String): Elem ={
    return {
      <div> package com.isuwang.soa.scala.serializer;

        import com.isuwang.dapeng.core._
        import com.isuwang.org.apache.thrift._
        import com.isuwang.org.apache.thrift.protocol._

        import com.isuwang.soa.scala.{serviceName}Codec;

        /**
        * Autogenerated by Dapeng-Code-Generator (1.2.1)
        *
        * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
        *  @generated
        **/

        class GetServiceMetadata_argsSerializer extends TCommonBeanSerializer[GetServiceMetadata_args] <block>

        @throws[TException]
        override def read(iprot: TProtocol): GetServiceMetadata_args = <block>

          iprot.readStructBegin

          var schemeField: com.isuwang.org.apache.thrift.protocol.TField = null

          while (schemeField == null || schemeField.`type` != com.isuwang.org.apache.thrift.protocol.TType.STOP) <block>
            schemeField = iprot.readFieldBegin
            com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)
            iprot.readFieldEnd
          </block>

          iprot.readStructEnd

          val bean = GetServiceMetadata_args()
          validate(bean)

          bean
        </block>

        @throws[TException]
        override def write(bean: GetServiceMetadata_args, oproto: TProtocol): Unit = <block>
          validate(bean)
          oproto.writeStructBegin(new com.isuwang.org.apache.thrift.protocol.TStruct("GetServiceMetadata_args"))

          oproto.writeFieldStop
          oproto.writeStructEnd
        </block>

        @throws[TException]
        override def validate(bean: GetServiceMetadata_args): Unit = <block></block>

        override def toString(bean: GetServiceMetadata_args): String = if (bean == null) "null" else bean.toString
      </block>


      </div>
    }

  }
  def toMetadataResultSerializerTemplate(service:Service): Elem ={
    return {
      <div> package com.isuwang.soa.scala.serializer;

        import com.isuwang.dapeng.core._
        import com.isuwang.org.apache.thrift._
        import com.isuwang.org.apache.thrift.protocol._

        import com.isuwang.soa.scala.{service.name}Codec;

        /**
        * Autogenerated by Dapeng-Code-Generator (1.2.1)
        *
        * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
        *  @generated
        **/
        class GetServiceMetadata_resultSerializer extends TCommonBeanSerializer[GetServiceMetadata_result] <block>
        @throws[TException]
        override def read(iprot: TProtocol): GetServiceMetadata_result = <block>
          iprot.readStructBegin

          var schemeField: com.isuwang.org.apache.thrift.protocol.TField = null

          var success: String = null

          while (schemeField == null || schemeField.`type` != com.isuwang.org.apache.thrift.protocol.TType.STOP) <block>
            schemeField = iprot.readFieldBegin

            schemeField.id match <block>
              case 0 =>
              schemeField.`type` match <block>
                case com.isuwang.org.apache.thrift.protocol.TType.STRING => success = iprot.readString
                case _ => com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)
              </block>
              case _ => com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)
            </block>
            iprot.readFieldEnd
          </block>

          iprot.readStructEnd
          val bean = GetServiceMetadata_result(success)
          validate(bean)

          bean
        </block>

        @throws[TException]
        override def write(bean: GetServiceMetadata_result, oproto: TProtocol): Unit = <block>
          validate(bean)
          oproto.writeStructBegin(new com.isuwang.org.apache.thrift.protocol.TStruct("getServiceMetadata_result"))

          oproto.writeFieldBegin(new com.isuwang.org.apache.thrift.protocol.TField("success", com.isuwang.org.apache.thrift.protocol.TType.STRING, 0.asInstanceOf[Short]))
          oproto.writeString(bean.success)
          oproto.writeFieldEnd

          oproto.writeFieldStop
          oproto.writeStructEnd
        </block>

        @throws[TException]
        override def validate(bean: GetServiceMetadata_result): Unit = <block>
          if (bean.success == null)
          throw new SoaException(SoaBaseCode.NotNull, "success字段不允许为空")
        </block>

        override def toString(bean: GetServiceMetadata_result): String = if (bean == null) "null" else bean.toString

      </block>
      </div>
    }

  }
  def toMetadataArgsTemplate(): Elem = return {
    <div> package com.isuwang.soa.scala.serializer;


      /**
      * Autogenerated by Dapeng-Code-Generator (1.2.1)
      *
      * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
      *  @generated
      **/
      case class GetServiceMetadata_args()
    </div>
  }
  def toMetadataResultTemplate(): Elem = return {
    <div> package com.isuwang.soa.scala.serializer;


      /**
      * Autogenerated by Dapeng-Code-Generator (1.2.1)
      *
      * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
      *  @generated
      **/
      case class GetServiceMetadata_result(success: String)
    </div>
  }






  def getReadMethod(struct: Struct,codecName :String): Elem = {
    <div>
      @throws[TException]
      override def read(iprot: TProtocol): {codecName}{toStructName(struct)} = <block>

      var schemeField: com.isuwang.org.apache.thrift.protocol.TField = null
      iprot.readStructBegin()

      {toFieldArrayBuffer(struct.getFields).map{(field : Field) =>{
        <div>var {nameAsId(field.name)}: {if(field.isOptional) <div>Option[</div>}{toScalaDataType(field.dataType)}{if(field.isOptional) <div>]</div>} = {if(field.isOptional) <div>None</div> else getDefaultValueWithType(field.dataType)}
        </div>
      }}}

      while (schemeField == null || schemeField.`type` != com.isuwang.org.apache.thrift.protocol.TType.STOP) <block>

        schemeField = iprot.readFieldBegin

        schemeField.id match <block>
          {toFieldArrayBuffer(struct.getFields).map{(field : Field) =>{
            <div>
              case {field.tag} =>
              schemeField.`type` match <block>
              case {toThriftDateType(field.dataType)} => {nameAsId(field.name)} = {if(field.isOptional) <div>Option(</div>}{getScalaReadElement(field.dataType, 0)}{if(field.isOptional) <div>)</div>}
              case _ => com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)
            </block>
            </div>
          }}}
          case _ => com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)
        </block>
      </block>

      iprot.readFieldEnd
      iprot.readStructEnd

      val bean = {codecName}{toStructName(struct)}({toFieldArrayBuffer(struct.getFields).map{(field : Field) =>{<div>{nameAsId(field.name)} = {nameAsId(field.name)}{if(field != struct.getFields.get(struct.getFields.size-1)) <span>,</span>}</div>}}})
      validate(bean)

      bean
    </block>
    </div>
  }
  def getWriteMethod(struct: Struct,codecName:String): Elem = {

    var index = 0
    <div>
      @throws[TException]
      override def write(bean: {codecName}{toStructName(struct)}, oprot: TProtocol): Unit = <block>

      validate(bean)
      oprot.writeStructBegin(new com.isuwang.org.apache.thrift.protocol.TStruct("{struct.name}"))

      {toFieldArrayBuffer(struct.fields).map{(field : Field) =>{
        if(field.dataType.getKind() == DataType.KIND.VOID) {}
        else {
          <div>
            {if(field.isOptional) <div>if(bean.{nameAsId(field.name)}.isDefined)</div>}<block>
            val elem{index} = bean.{nameAsId(field.name)} {if(field.isOptional) <div>.get</div>}
            oprot.writeFieldBegin(new com.isuwang.org.apache.thrift.protocol.TField("{nameAsId(field.name)}", {toThriftDateType(field.dataType)}, {field.tag}.asInstanceOf[Short]))
            {toScalaWriteElement(field.dataType, index)}
            oprot.writeFieldEnd
            {index = index + 1}
          </block></div>
        }
      }
      }
      }
      oprot.writeFieldStop
      oprot.writeStructEnd
    </block>
    </div>
  }
  def getValidateMethod(struct: Struct,codecName:String) : Elem = {
    <div>
      @throws[TException]
      override def validate(bean: {codecName}{toStructName(struct)}): Unit = <block>
      {
      toFieldArrayBuffer(struct.fields).map{(field : Field) =>{
        <div>{
          if(!field.isOptional && field.dataType.kind != DataType.KIND.VOID && checkIfNeedValidate(field.isOptional, field.dataType)){
            <div>
              if(bean.{nameAsId(field.name)} == null)
              throw new SoaException(SoaBaseCode.NotNull, "{nameAsId(field.name)}字段不允许为空")
            </div>}}</div>
          <div>{
            if(!field.isOptional && field.dataType.kind == KIND.STRUCT && field.dataType.kind != DataType.KIND.VOID){
              <div>
                if(bean.{nameAsId(field.name)} != null)
                new {field.dataType.qualifiedName.substring(field.dataType.qualifiedName.lastIndexOf(".")+1)}Serializer().validate(bean.{nameAsId(field.name)})
              </div>}}</div>
          <div>{
            if(field.isOptional && field.dataType.kind == KIND.STRUCT && field.dataType.kind != DataType.KIND.VOID){
              <div>
                if(bean.{nameAsId(field.name)}.isDefined)
                new {field.dataType.qualifiedName.substring(field.dataType.qualifiedName.lastIndexOf(".")+1)}Serializer().validate(bean.{nameAsId(field.name)}.get)
              </div>}}</div>
      }
      }
      }
    </block>
    </div>
  }
  def toStructName(struct: Struct): String = {
    if (struct.getNamespace == null) {
      return struct.getName()
    } else {
      return struct.getNamespace + "." + struct.getName()
    }
  }
  def toThriftDateType(dataType:DataType): Elem = {
    dataType.kind match {
      case KIND.VOID => <div>com.isuwang.org.apache.thrift.protocol.TType.VOID</div>
      case KIND.BOOLEAN => <div>com.isuwang.org.apache.thrift.protocol.TType.BOOL</div>
      case KIND.BYTE => <div>com.isuwang.org.apache.thrift.protocol.TType.BYTE</div>
      case KIND.SHORT => <div>com.isuwang.org.apache.thrift.protocol.TType.I16</div>
      case KIND.INTEGER => <div>com.isuwang.org.apache.thrift.protocol.TType.I32</div>
      case KIND.LONG => <div>com.isuwang.org.apache.thrift.protocol.TType.I64</div>
      case KIND.DOUBLE => <div>com.isuwang.org.apache.thrift.protocol.TType.DOUBLE</div>
      case KIND.STRING => <div>com.isuwang.org.apache.thrift.protocol.TType.STRING</div>
      case KIND.MAP => <div>com.isuwang.org.apache.thrift.protocol.TType.MAP</div>
      case KIND.LIST => <div>com.isuwang.org.apache.thrift.protocol.TType.LIST</div>
      case KIND.SET => <div>com.isuwang.org.apache.thrift.protocol.TType.SET</div>
      case KIND.ENUM => <div>com.isuwang.org.apache.thrift.protocol.TType.I32</div>
      case KIND.STRUCT => <div>com.isuwang.org.apache.thrift.protocol.TType.STRUCT</div>
      case KIND.DATE => <div>com.isuwang.org.apache.thrift.protocol.TType.I64</div>
      case KIND.BIGDECIMAL => <div>com.isuwang.org.apache.thrift.protocol.TType.STRING</div>
      case KIND.BINARY => <div>com.isuwang.org.apache.thrift.protocol.TType.STRING</div>
      case _ => <div></div>
    }
  }
  def toScalaDataType(dataType:DataType): Elem = {
    dataType.kind match {
      case KIND.VOID => <div></div>
      case KIND.BOOLEAN => <div>Boolean</div>
      case KIND.BYTE => <div>Byte</div>
      case KIND.SHORT => <div>Short</div>
      case KIND.INTEGER => <div>Int</div>
      case KIND.LONG => <div>Long</div>
      case KIND.DOUBLE => <div>Double</div>
      case KIND.STRING => <div>String</div>
      case KIND.BINARY => <div>java.nio.ByteBuffer</div>
      case KIND.DATE => <div>java.util.Date</div>
      case KIND.BIGDECIMAL => <div>BigDecimal</div>
      case KIND.MAP =>
        return {<div>Map[{toScalaDataType(dataType.getKeyType)}, {toScalaDataType(dataType.getValueType)}]</div>}
      case KIND.LIST =>
        return {<div>List[{toScalaDataType(dataType.getValueType)}]</div>}
      case KIND.SET =>
        return {<div>Set[{toScalaDataType(dataType.getValueType)}]</div>}
      case KIND.ENUM =>
        val ref = dataType.getQualifiedName
        val enumName = ref.substring(ref.lastIndexOf("."))
        return {<div>{ref}{enumName}</div>}
      case KIND.STRUCT =>
        val ref = dataType.getQualifiedName
        return {<div>{ref}</div>}
    }
  }

  val keywords = Set("type") // TODO is there any other keyword need to be escape
  def nameAsId(name: String) = if(keywords contains name) s"`$name`" else name

  def getScalaReadElement(dataType: DataType, index: Int):Elem = {
    dataType.kind match {
      case KIND.BOOLEAN => <div>iprot.readBool</div>
      case KIND.STRING => <div>iprot.readString</div>
      case KIND.BYTE => <div>iprot.readByte</div>
      case KIND.SHORT =>  <div>iprot.readI16</div>
      case KIND.INTEGER => <div>iprot.readI32</div>
      case KIND.LONG => <div>iprot.readI64</div>
      case KIND.DOUBLE => <div>iprot.readDouble</div>
      case KIND.BINARY => <div>iprot.readBinary</div>
      case KIND.BIGDECIMAL => <div>BigDecimal(iprot.readString)</div>
      case KIND.DATE => <div>new java.util.Date(iprot.readI64)</div>
      case KIND.STRUCT => <div>new {dataType.qualifiedName.substring(dataType.qualifiedName.lastIndexOf(".")+1)}Serializer().read(iprot)</div>
      case KIND.ENUM => <div>{dataType.qualifiedName}.findByValue(iprot.readI32)</div>
      case KIND.MAP => <div><block>
        val _map{index} : com.isuwang.org.apache.thrift.protocol.TMap = iprot.readMapBegin
        val _result{index} = (0 until _map{index}.size).map(_ => <block>( {getScalaReadElement(dataType.keyType, index+1)} -> {getScalaReadElement(dataType.valueType, index+2)} )</block>).toMap
        iprot.readMapEnd
        _result{index}
      </block></div>
      case KIND.LIST => <div><block>
        val _list{index} : com.isuwang.org.apache.thrift.protocol.TList = iprot.readListBegin
        val _result{index} = (0 until _list{index}.size).map(_ => <block>{getScalaReadElement(dataType.valueType, index+1)}</block>).toList
        iprot.readListEnd
        _result{index}
      </block></div>
      case KIND.SET => <div><block>
        val _set{index} : com.isuwang.org.apache.thrift.protocol.TSet = iprot.readSetBegin
        val _result{index} = (0 until _set{index}.size).map(_ => <block>{getScalaReadElement(dataType.valueType, index+1)}</block>).toSet
        iprot.readSetEnd
        _result{index}
      </block></div>
      case KIND.VOID => <div>com.isuwang.org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.`type`)</div>
      case _ => <div></div>
    }
  }
  def getDefaultValueWithType(dataType: DataType): Elem = {

    dataType.kind match {
      case KIND.BOOLEAN => <div>false</div>
      case KIND.BYTE => <div>0</div>
      case KIND.SHORT => <div>0</div>
      case KIND.INTEGER => <div>0</div>
      case KIND.LONG => <div>0</div>
      case KIND.DOUBLE => <div>0.00</div>
      case KIND.STRING => <div>null</div>
      case KIND.BINARY => <div>null</div>
      case KIND.DATE => <div>null</div>
      case KIND.BIGDECIMAL => <div>null</div>
      case KIND.LIST => <div>List.empty</div>
      case KIND.SET => <div>Set.empty</div>
      case KIND.MAP => <div>Map.empty</div>
      case _ => <div>null</div>
    }

  }
  def toScalaWriteElement(dataType: DataType, index: Int):Elem = {

    dataType.kind match {
      case KIND.BOOLEAN => <div>oprot.writeBool(elem{index})</div>
      case KIND.BYTE => <div>oprot.writeByte(elem{index})</div>
      case KIND.SHORT => <div>oprot.writeI16(elem{index})</div>
      case KIND.INTEGER => <div>oprot.writeI32(elem{index})</div>
      case KIND.LONG => <div>oprot.writeI64(elem{index})</div>
      case KIND.DOUBLE => <div>oprot.writeDouble(elem{index})</div>
      case KIND.STRING => <div>oprot.writeString(elem{index})</div>
      case KIND.ENUM => <div>oprot.writeI32(elem{index}.id)</div>
      case KIND.BINARY => <div>oprot.writeBinary(elem{index})</div>
      case KIND.DATE => <div>oprot.writeI64(elem{index}.getTime)</div>
      case KIND.BIGDECIMAL => <div>oprot.writeString(elem{index}.toString)</div>
      case KIND.STRUCT => <div> new {dataType.qualifiedName.substring(dataType.qualifiedName.lastIndexOf(".")+1)}Serializer().write(elem{index}, oprot)</div>
      case KIND.LIST => <div>
        oprot.writeListBegin(new com.isuwang.org.apache.thrift.protocol.TList({toThriftDateType(dataType.valueType)}, elem{index}.size))
        elem{index}.foreach(elem{index+1} => <block>{toScalaWriteElement(dataType.valueType, index+1)}</block>)
        oprot.writeListEnd
      </div>
      case KIND.MAP => <div>
        oprot.writeMapBegin(new com.isuwang.org.apache.thrift.protocol.TMap({toThriftDateType(dataType.keyType)}, {toThriftDateType(dataType.valueType)}, elem{index}.size))

        elem{index}.map<block>case(elem{index+1}, elem{index+2}) => <block>
          {toScalaWriteElement(dataType.keyType, index+1)}
          {toScalaWriteElement(dataType.valueType, index+2)}
        </block>
        </block>
        oprot.writeMapEnd
      </div>
      case KIND.SET => <div>
        oprot.writeSetBegin(new com.isuwang.org.apache.thrift.protocol.TSet({toThriftDateType(dataType.valueType)}, elem{index}.size))
        elem{index}.foreach(elem{index+1} => <block>{toScalaWriteElement(dataType.valueType, index+1)}</block>)
        oprot.writeSetEnd
      </div>
      case _ => <div></div>
    }
  }
  def checkIfNeedValidate(optional: Boolean, dataType: DataType): Boolean = {

    if(optional)
      false
    else
      dataType.kind match {

        case DataType.KIND.BOOLEAN => false
        case KIND.SHORT => false
        case KIND.INTEGER => false
        case KIND.LONG => false
        case KIND.DOUBLE => false
        case _ => true
      }

  }














}
