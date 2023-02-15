package com.sfs.crfosp.cis

class XsdSchemyCis implements Serializable{
	String nazov
	String dschema
	String popis
	Date datumpm
	Integer xcprac
	
	static mapping = {
		table 'ISCHEMA'
		dschema sqlType: 'clob'
		version false
	}
	static constraints = {
		nazov nullable:false, maxSize:255
		popis nullable:true, maxSize:255
	}
}
