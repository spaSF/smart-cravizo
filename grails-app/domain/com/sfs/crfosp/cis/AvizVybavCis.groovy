package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class AvizVybavCis {
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	String popis
	String xcode411
	String domain
	String pole
	
	static mapping = {
		table 'CRAVIZOCIS'
		version false
		id generator:"assigned",sqlType:"NUMBER(10)"
		nazov sqlType:"VARCHAR2(255)"
		popis sqlType:"VARCHAR2(255)"
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
	}
}
