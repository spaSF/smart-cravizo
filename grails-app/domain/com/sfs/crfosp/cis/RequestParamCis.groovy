package com.sfs.crfosp.cis

class RequestParamCis {

	String id
	String popis
	
	static mapping = {
		table "CPARAMS"
		version false
		id column:"KOD",generator:'assigned',type:'string',length:50
	}
	
    static constraints = {
		id blank:false,nullable:false,maxSize:50
    }
}
