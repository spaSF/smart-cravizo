package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class SkupUdajovCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	String id
	@XmlElement(name="NAZOV",required=true)
	String nazov
	static mapping = {
		table 'CSKUD'
		version false
		id generator:'assigned',sqlType:'CHAR',length:2
		id column:'ID',sqlType:'char',length:2
	}
	static constraints = {
		id nullable:false, maxSize:2
		nazov nullable:false, maxSize:255
	}
}
