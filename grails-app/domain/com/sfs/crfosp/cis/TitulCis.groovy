package com.sfs.crfosp.cis

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class TitulCis implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	Date dtod
	Date dtdo
	@XmlElement(name="NAZOV")
	String nazov
	@XmlElement(name="TYP",required=true)
	TypTitulCis typ
	static mapping = {
		table 'CTITUL'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:10
		typ column:'TYP',lazy:false
		nazov column:'NAZOV',sqlType:'varchar2',length:60
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		nazov nullable:true, maxSize:60
		typ nullable:false
	}
}
