package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class UlicaObceCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="ULICA",required=true)
	RegUlicaCis ulica
	@XmlElement(name="OBEC",required=true)
	UzemnyCelokCis obec
	@XmlElement(name="TYPUC",required=true)
	TypUzCelokCis typuc
	static mapping = {
		table 'CREGULICAUC'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		ulica column:'ULICA'
		obec column:'OBEC'
		typuc column:'TYPUC'
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		ulica nullable:false
		obec nullable:false
		typuc nullable:false
	}
}
