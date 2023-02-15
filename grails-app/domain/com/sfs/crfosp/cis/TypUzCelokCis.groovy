package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class TypUzCelokCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="KOD")
	String kod
	@XmlElement(name="NAZOV")
	String nazov
	@XmlElement(name="UROVEN")
	Long uroven
	static mapping = {
		table 'CTYPUC'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		kod column:'KOD',sqlType:'varchar2',length:100
		nazov column:'NAZOV',sqlType:'varchar2',length:100
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		kod nullable:true, maxSize:100
		nazov nullable:true, maxSize:100
		uroven nullable:true
	}
}
