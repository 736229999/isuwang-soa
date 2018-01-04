package com.isuwang.soa.account.scala.enums

class AccountJournalBusinessCategory private(val id: Int, val name: String) extends com.isuwang.dapeng.core.enums.TEnum(id,name) {}

      /**
       * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

      * 

 业务类型

      **/
      object AccountJournalBusinessCategory {

      
            val KUAISU = new AccountJournalBusinessCategory(1, "快塑账户")
          
            val AUCTION = new AccountJournalBusinessCategory(2, "竞拍")
          
            val FIXEDPRICE = new AccountJournalBusinessCategory(3, "一口价")
          
            val ETRADE = new AccountJournalBusinessCategory(4, "电子盘")
          
      val UNDEFINED = new AccountJournalBusinessCategory(-1,"UNDEFINED") // undefined enum
      

      def findByValue(v: Int): AccountJournalBusinessCategory = {
        v match {
          case 1 => KUAISU
            case 2 => AUCTION
            case 3 => FIXEDPRICE
            case 4 => ETRADE
            
          case _ => new AccountJournalBusinessCategory(v,"#"+ v)
        }
      }

      def apply(v: Int) = findByValue(v)
      def unapply(v: AccountJournalBusinessCategory): Option[Int] = Some(v.id)

    }
    