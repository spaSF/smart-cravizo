package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

@XmlAccessorType(XmlAccessType.NONE)
class UdelStObcianstvo implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="DT_PREVZATIA")
	@XmlSchemaType(name = "date")
	Date dtprev
	@XmlElement(name="UDELENIE_STRATA")
	Boolean fg_udst

	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRUSSTOB'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		fg_udst column:'FG_UDST',sqlType:'char',length:1
		osoba column:'CROSOBA'
	}
	static constraints = {
		id nullable:false
		dtprev nullable:true
		fg_udst nullable:true, maxSize:1
	}
}
