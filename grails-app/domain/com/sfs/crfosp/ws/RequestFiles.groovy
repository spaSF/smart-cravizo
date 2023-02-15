package com.sfs.crfosp.ws

import com.sfs.smartsfs.isc.SmartFile

class RequestFiles implements Serializable{
	RequestLog request
	SmartFile smartfile
	String direstion
	String msgId
	
	static mapping = {
		table 'CRREQUESTFILE'
		request column:'REQUEST'
		smartfile column:'SMARTFILE'
		version false
	}
	static constraints = {
		request nullable:true
		smartfile nullable:true
		direstion nullable:true
	}
	
}
