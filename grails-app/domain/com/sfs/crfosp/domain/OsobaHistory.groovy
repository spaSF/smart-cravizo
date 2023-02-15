package com.sfs.crfosp.domain


class OsobaHistory implements Serializable{
   Long id
   String entity
   Long entityId
   String field
   String oldVal
   String newVal
   Date dateChange
 
   static belongsTo =[osoba:Osoba]
   
   static mapping = {
      table 'CRHISDAT'
      version false
      id generator:'sequence',params:[sequence:'CRHISDAT_SEQ']
	  entityId column:'ENT_ID'
	  oldVal column:'OLD_VAL'
	  newVal column:'NEW_VAL'
      entity column:'ENTITY',sqlType:'varchar2',length:30
      field column:'FIELD',sqlType:'varchar2',length:30
	  dateChange column:'DATE_CHANGE'
	  osoba column:'CROSOBA'
   }
   static constraints = {
      id nullable:false 
      entity nullable:false, maxSize:30 
      entityId nullable:false 
      field nullable:false, maxSize:30 
      oldVal nullable:true, maxSize:255 
      newVal nullable:true, maxSize:255 
      dateChange nullable:false 
   }
}

