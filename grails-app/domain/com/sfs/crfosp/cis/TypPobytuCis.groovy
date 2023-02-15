package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class TypPobytuCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	static mapping = {
		table 'CTYPPOB'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		nazov column:'NAZOV',sqlType:'varchar2',length:50
	}
	static constraints = {
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:50
	}
}
