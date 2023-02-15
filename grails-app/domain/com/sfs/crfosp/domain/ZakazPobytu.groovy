package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

@XmlAccessorType(XmlAccessType.NONE)
class ZakazPobytu implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="DTOD")
	@XmlSchemaType(name = "date")
	Date dtod
	@XmlElement(name="DTDO")
	@XmlSchemaType(name = "date")
	Date dtdo
	@XmlElement(name="POZN")
	String pozn
	@XmlElement(name="ZAKAZ_REGION")
	Set<ZakazPobytuRegion> zakazRegion
	
	static belongsTo =[osoba:Osoba]
	
	static hasMany = [zakazRegion:ZakazPobytuRegion]
	static mappedBy = [zakazRegion:"zakazPobytu"]
	
	static mapping = {
		table 'CRZAKPOB'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
		zakazRegion lazy:false
	}
	static constraints = {
		id nullable:false
		dtod nullable:true
		dtdo nullable:true
		pozn nullable:true, maxSize:2560
	}
}
