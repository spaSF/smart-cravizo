package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.TitulCis
import com.sfs.crfosp.cis.TypTitulCis

@XmlAccessorType(XmlAccessType.NONE)
class Titul implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="TITUL",required=true)
	TitulCis titul
	@XmlElement(name="TYPTIT",required=true)
	TypTitulCis typtit
	
	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRTITOS'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
		titul column:'TITUL',lazy:false,fetch:'join'
		typtit column:'TYPTIT',lazy:false,fetch:'join'
	}
	static constraints = {
		id nullable:false
		titul nullable:false
		typtit nullable:false
	}
}
