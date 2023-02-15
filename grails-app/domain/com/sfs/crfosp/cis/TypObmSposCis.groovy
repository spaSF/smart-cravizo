package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class TypObmSposCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="NAZOV")
	String nazov
	Date dtod
	Date dtdo
	static mapping = {
		table 'CTYPOBSPO'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
	}
	static constraints = {
		id nullable:false
		nazov nullable:true, maxSize:255
		dtod nullable:true
		dtdo nullable:true
	}
}
