
      package com.isuwang.soa.monitor.api.service;

      import com.isuwang.dapeng.core.Processor;
      import com.isuwang.dapeng.core.Service;
      import com.isuwang.dapeng.core.SoaGlobalTransactional;

      /**
       * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

      * 

 监控服务

      **/
      @Service(name="com.isuwang.soa.monitor.api.service.MonitorService",version = "1.0.0")
      @Processor(className = "com.isuwang.soa.monitor.api.MonitorServiceCodec$Processor")
      public interface MonitorService {
      
          /**
          * 

 上送QPS信息

          **/
          
             
             void uploadQPSStat(java.util.List<com.isuwang.soa.monitor.api.domain.QPSStat> qpsStats) throws com.isuwang.dapeng.core.SoaException;
            
        
          /**
          * 

 上送平台处理数据

          **/
          
             
             void uploadPlatformProcessData(java.util.List<com.isuwang.soa.monitor.api.domain.PlatformProcessData> platformProcessDatas) throws com.isuwang.dapeng.core.SoaException;
            
        
          /**
          * 

 上送DataSource信息

          **/
          
             
             void uploadDataSourceStat(java.util.List<com.isuwang.soa.monitor.api.domain.DataSourceStat> dataSourceStat) throws com.isuwang.dapeng.core.SoaException;
            
        
      }
      