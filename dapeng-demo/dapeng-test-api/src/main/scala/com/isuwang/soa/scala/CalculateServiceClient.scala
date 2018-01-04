package com.isuwang.soa.scala

import com.isuwang.dapeng.core._;
        import com.isuwang.org.apache.thrift._;
        import java.util.ServiceLoader;
        import com.isuwang.soa.scala.CalculateServiceCodec._;
        import com.isuwang.soa.scala.service.CalculateService;

        /**
         * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

        **/
        class CalculateServiceClient extends CalculateService {

        import java.util.function.{ Function ⇒ JFunction, Predicate ⇒ JPredicate, BiPredicate }
          implicit def toJavaFunction[A, B](f: Function1[A, B]) = new JFunction[A, B] {
          override def apply(a: A): B = f(a)
        }

          val serviceName = "com.isuwang.soa.service.CalculateService"
          val version = "1.0.0"
          val pool = {
            val serviceLoader = ServiceLoader.load(classOf[SoaConnectionPoolFactory])
          if (serviceLoader.iterator().hasNext) {
          val poolImpl = serviceLoader.iterator().next().getPool
          poolImpl.registerClientInfo(serviceName,version)
          poolImpl
          } else null
           }

        def getServiceMetadata: String = {
        pool.send(
          serviceName,
          version,
          "getServiceMetadata",
          new getServiceMetadata_args,
          new GetServiceMetadata_argsSerializer,
          new GetServiceMetadata_resultSerializer
        ).success
        }


        
             /**
             * 
             **/
            def calcualteWordCount(filename:String ,word:String ) : Int = {

              val response = pool.send(
              serviceName,
              version,
              "calcualteWordCount",
              calcualteWordCount_args(filename,word),
              new CalcualteWordCount_argsSerializer(),
              new CalcualteWordCount_resultSerializer())

              response.success

            }
          
             /**
             * 
             **/
            def calcualteWordsCount(fileName:String ) : Map[String, Int] = {

              val response = pool.send(
              serviceName,
              version,
              "calcualteWordsCount",
              calcualteWordsCount_args(fileName),
              new CalcualteWordsCount_argsSerializer(),
              new CalcualteWordsCount_resultSerializer())

              response.success

            }
          
      }
      