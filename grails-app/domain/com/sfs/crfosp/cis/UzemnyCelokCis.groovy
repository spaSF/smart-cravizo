package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class UzemnyCelokCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	String kod
	@XmlElement(name="NAZOV")
	String nazov
	@XmlElement(name="TYPUC")
	TypUzCelokCis typ
	@XmlElement(name="NADRADENYUC")
	UzemnyCelokCis ucnadr
	
	static shortFields = ["kod","nazov","typ"]
	
	static mapping = {
		table 'CUZCELOK'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		typ column:'TYP'
		ucnadr column:'UCNADR'
		kod column:'KOD',sqlType:'varchar2',length:100
		nazov column:'NAZOV',sqlType:'varchar2',length:100
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		kod nullable:true, maxSize:100
		nazov nullable:true, maxSize:100
		typ nullable:true
		ucnadr nullable:true
	}
}
