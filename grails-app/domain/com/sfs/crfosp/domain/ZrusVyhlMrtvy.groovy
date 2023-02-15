package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

@XmlAccessorType(XmlAccessType.NONE)
class ZrusVyhlMrtvy implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="DTPRAV")
	@XmlSchemaType(name = "date")
	Date dtprav

	static belongsTo =[osoba:Osoba]
		
	static mapping = {
		table 'CRZRMR'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
	}
	static constraints = {
		id nullable:false
		dtprav nullable:true
	}
}
