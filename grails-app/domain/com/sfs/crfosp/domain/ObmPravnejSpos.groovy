package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.TypObmSposCis

@XmlAccessorType(XmlAccessType.NONE)
class ObmPravnejSpos implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="TYPOBM",required=true)
	TypObmSposCis typobm
	@XmlElement(name="DTOD")
	@XmlSchemaType(name = "date")
	Date dtod
	@XmlElement(name="DTDO")
	@XmlSchemaType(name = "date")
	Date dtdo
	@XmlElement(name="POZN")
	String pozn
	
	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRPRSPOS'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		typobm column:'TYPOBM',fetch:'join',lazy:false
		osoba column:'CROSOBA'
	}
	static constraints = {
		id nullable:false
		typobm nullable:false
		dtod nullable:true
		dtdo nullable:true
		pozn nullable:true, maxSize:2560
	}
}
