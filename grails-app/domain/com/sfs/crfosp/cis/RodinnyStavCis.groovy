package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RodinnyStavCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	String kod
	static mapping = {
		table 'CRODST'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		kod column:'KOD',sqlType:'char',length:1
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:255
		kod nullable:true, maxSize:1
	}
}
