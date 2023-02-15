package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class Priezvisko implements Serializable,Comparable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="PRIEZVISKO")
	String priezvisko
	@XmlElement(name="PRIEZVISKO_IDX",required=true)
	Integer priezvisko_idx
	
	@Override
	public int compareTo(obj) {
		this.getPriezvisko_idx().compareTo(obj.priezvisko_idx)
	}

	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRPRIEZ'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		priezvisko column:'PRIEZVISKO',sqlType:'varchar2',length:250
		osoba column:'CROSOBA'
	}
	static constraints = {
		id nullable:false
		priezvisko nullable:true, maxSize:250
		priezvisko_idx nullable:false
	}
}
