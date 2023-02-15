package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RegDomCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	@XmlElement(name="SUPISC")
	Long supisc
	@XmlElement(name="KATC")
	String katc
	@XmlElement(name="STAVDOM",required=true)
	StavDomuCis stavdom
	@XmlElement(name="OBEC",required=true)
	UzemnyCelokCis obec
	@XmlElement(name="CASTOBCE")
	UzemnyCelokCis castobce
	static mapping = {
		table 'CREGDOM'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		stavdom column:'STAVDOM',lazy:false
		obec column:'OBEC',lazy:false
		castobce column:'CASTOBCE',lazy:false
		katc column:'KATC',sqlType:'varchar2',length:20
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:255
		supisc nullable:true
		katc nullable:true, maxSize:20
		stavdom nullable:false
		obec nullable:false
		castobce nullable:true
	}
}
