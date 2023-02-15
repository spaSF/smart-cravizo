package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class StatCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	@XmlElement(name="KOD")
	String kod
	@XmlElement(name="KOD3")
	String kod3
	static mapping = {
		table 'CSTAT'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		nazov column:'NAZOV',sqlType:'varchar2',length:100
		kod column:'KOD',sqlType:'char',length:2
		kod3 column:'KOD3',sqlType:'char',length:3
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:100
		kod nullable:true, maxSize:2
		kod3 nullable:true, maxSize:3
	}
}
