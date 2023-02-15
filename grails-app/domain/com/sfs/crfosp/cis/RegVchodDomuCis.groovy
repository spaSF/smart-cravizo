package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RegVchodDomuCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	@XmlElement(name="KOD")
	String kod
	@XmlElement(name="ORIENTC")
	Long orientc
	@XmlElement(name="ORIENTCZN")
	String orientczn
	@XmlElement(name="PSC")
	String psc
	@XmlElement(name="ULICA")
	RegUlicaCis ulica
	@XmlElement(name="DOM",required=true)
	RegDomCis dom
	@XmlElement(name="STAVVCHOD")
	StavDomVchodCis stavvchod
	static mapping = {
		table 'CREGDOMVCH'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		ulica column:'ULICA',lazy:false
		dom column:'DOM',lazy:false
		stavvchod column:'STAVVCHOD',lazy:false
		kod column:'KOD',sqlType:'varchar2',length:12
		orientczn column:'ORIENTCZN',sqlType:'char',length:1
		psc column:'PSC',sqlType:'varchar2',length:5
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:255
		kod nullable:true, maxSize:12
		orientc nullable:true
		orientczn nullable:true, maxSize:1
		psc nullable:true, maxSize:5
		ulica nullable:true
		dom nullable:false
		stavvchod nullable:true
	}
}
