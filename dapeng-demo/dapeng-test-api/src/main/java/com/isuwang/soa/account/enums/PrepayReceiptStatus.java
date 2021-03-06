package com.isuwang.soa.account.enums;

        /**
         * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

        * 

预付款账户回款申请 状态 0 未确认:(unconfirmed),1:已确认(confirmed),2:失败(failed)

        **/
        public enum PrepayReceiptStatus implements com.isuwang.org.apache.thrift.TEnum{
        
            /**
            *

 未确认

            **/
            UnConfirmed(0),
          
            /**
            *

 已确认

            **/
            Confirmed(1),
          
            /**
            *

 失败

            **/
            Failed(2);
          

        private final int value;

        private PrepayReceiptStatus(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }

        public static PrepayReceiptStatus findByValue(int value){
            switch(value){
            
                 case 0:
                    return UnConfirmed;
               
                 case 1:
                    return Confirmed;
               
                 case 2:
                    return Failed;
               
               default:
                   return null;
            }
        }
        }
      