package com.isuwang.soa.monitor.api;

      import com.isuwang.dapeng.core.*;
      import com.isuwang.org.apache.thrift.*;
      import java.util.ServiceLoader;
      import com.isuwang.soa.monitor.api.MonitorServiceCodec.*;
      import com.isuwang.soa.monitor.api.service.MonitorService;

      /**
       * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

      **/
      public class MonitorServiceClient implements MonitorService{
        private final String serviceName;
        private final String version;

        private SoaConnectionPool pool;

      public MonitorServiceClient() {
        this.serviceName = "com.isuwang.soa.monitor.api.service.MonitorService";
        this.version = "1.0.0";

        ServiceLoader<SoaConnectionPoolFactory> factories = ServiceLoader.load(SoaConnectionPoolFactory.class);
        for (SoaConnectionPoolFactory factory: factories) {
          this.pool = factory.getPool();
          break;
        }
        this.pool.registerClientInfo(serviceName,version);
      }

      
        
       /**
       * 

 上送QPS信息

       **/
          
            public void uploadQPSStat(java.util.List<com.isuwang.soa.monitor.api.domain.QPSStat> qpsStats) throws SoaException{

              String methodName = "uploadQPSStat";

              uploadQPSStat_args uploadQPSStat_args = new uploadQPSStat_args();
              uploadQPSStat_args.setQpsStats(qpsStats);
                

              uploadQPSStat_result response = pool.send(serviceName,version,"uploadQPSStat",uploadQPSStat_args, new UploadQPSStat_argsSerializer(), new UploadQPSStat_resultSerializer());

              
                  
                
          }
          
        

        
        
       /**
       * 

 上送平台处理数据

       **/
          
            public void uploadPlatformProcessData(java.util.List<com.isuwang.soa.monitor.api.domain.PlatformProcessData> platformProcessDatas) throws SoaException{

              String methodName = "uploadPlatformProcessData";

              uploadPlatformProcessData_args uploadPlatformProcessData_args = new uploadPlatformProcessData_args();
              uploadPlatformProcessData_args.setPlatformProcessDatas(platformProcessDatas);
                

              uploadPlatformProcessData_result response = pool.send(serviceName,version,"uploadPlatformProcessData",uploadPlatformProcessData_args, new UploadPlatformProcessData_argsSerializer(), new UploadPlatformProcessData_resultSerializer());

              
                  
                
          }
          
        

        
        
       /**
       * 

 上送DataSource信息

       **/
          
            public void uploadDataSourceStat(java.util.List<com.isuwang.soa.monitor.api.domain.DataSourceStat> dataSourceStat) throws SoaException{

              String methodName = "uploadDataSourceStat";

              uploadDataSourceStat_args uploadDataSourceStat_args = new uploadDataSourceStat_args();
              uploadDataSourceStat_args.setDataSourceStat(dataSourceStat);
                

              uploadDataSourceStat_result response = pool.send(serviceName,version,"uploadDataSourceStat",uploadDataSourceStat_args, new UploadDataSourceStat_argsSerializer(), new UploadDataSourceStat_resultSerializer());

              
                  
                
          }
          
        

        

      /**
      * getServiceMetadata
      **/
      public String getServiceMetadata() throws SoaException {
        String methodName = "getServiceMetadata";
          getServiceMetadata_args getServiceMetadata_args = new getServiceMetadata_args();
          getServiceMetadata_result response = pool.send(serviceName,version,methodName,getServiceMetadata_args, new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
          return response.getSuccess();
      }

      }
    