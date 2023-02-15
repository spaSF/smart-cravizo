package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class StupenZverejCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	static mapping = {
		table 'CSTZVEREJ'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:255
	}
}
