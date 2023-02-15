package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
@com.sfs.smartsfs.audit.AuditStamp
class SystemCis implements Serializable{
	@XmlElement(required=true,name="KOD")
	String id
	@XmlElement(name="NAZOV",required=true)
	String nazov

	Boolean fg_platny
	
	static mapping = {
		table 'CSPSYS'
		id generator:'assigned',type:'string',sqlType:'VARCHAR2',length:10
		fg_platny column:'FG_PLATNY',sqlType:'NUMBER',length:1
	}
	static constraints = {
		nazov nullable:false, maxSize:255
		fg_platny nullable:false
	}
}
