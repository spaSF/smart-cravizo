package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class KodOpovedeCis implements Serializable{
	@XmlAttribute(required=true,name="KOD")
	Integer id
	@XmlElement(name="SPRAVA",required=true)
	String msg
	static mapping = {
		table 'CRESPKOD'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:5
	}
	static constraints = {
		id nullable:false
		msg nullable:false, maxSize:500
	}
}
