
        package com.isuwang.soa.service;

        import com.isuwang.dapeng.core.Processor;
        import com.isuwang.dapeng.core.Service;
        import com.isuwang.dapeng.core.SoaGlobalTransactional;

        import java.util.concurrent.Future;

        /**
         * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

        * 
        **/
        @Service(name="com.isuwang.soa.service.CalculateService",version = "1.0.0")
        @Processor(className = "com.isuwang.soa.CalculateServiceAsyncCodec$Processor")
        public interface CalculateServiceAsync  extends com.isuwang.dapeng.core.definition.AsyncService {
        
            /**
            * 
            **/
            
            
              Future<Integer> calcualteWordCount(String filename, String word, long timeout) throws com.isuwang.dapeng.core.SoaException;
            
          
            /**
            * 
            **/
            
            
              Future<java.util.Map<String, Integer>> calcualteWordsCount(String fileName, long timeout) throws com.isuwang.dapeng.core.SoaException;
            
          
      }
      