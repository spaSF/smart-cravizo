package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

class RegUlicaObceView implements Serializable{
	Long id
	Date dtod
	Date dtdo
	String nazov
	UzemnyCelokCis obec
	TypUzCelokCis typuc
	Long ulica

	static mapping = {
		table 'sc_wulicaobce'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		nazov column:'NAZOV',sqlType:'varchar2',length:100
		typuc column:'typuc'
		obec column:'obec'
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:100
	}
}
