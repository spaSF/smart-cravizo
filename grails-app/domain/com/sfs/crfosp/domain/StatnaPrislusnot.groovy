package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.StatCis

@XmlAccessorType(XmlAccessType.NONE)
class StatnaPrislusnot implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="STPRIS",required=true)
	StatCis stpris
	
	String stprisNazov
	
	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRSTPR'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
		stpris column:'STPRIS',fetch:'join'
		stprisNazov column:'STPRIS_NAZOV'
	}
	static constraints = {
		id nullable:false
		stpris nullable:false
	}
}
