package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.DruhDokladuCis

@XmlAccessorType(XmlAccessType.NONE)
class Doklad implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="DRUHDOKL",required=true)
	DruhDokladuCis druhdokl
	@XmlElement(name="CIS_DOKLADU",required=true)
	String cisdok
	@XmlElement(name="DOKLAD_U_DRZITELA",required=true)
	Boolean fg_udrz
	
	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRDOKLAD'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		cisdok column:'CISDOK',sqlType:'varchar2',length:100
		fg_udrz column:'FG_UDRZ',sqlType:'char',length:1
		osoba column:'CROSOBA'
		druhdokl column:'DRUHDOKL',fetch:'join'
	}
	static constraints = {
		id nullable:false
		druhdokl nullable:false
		cisdok nullable:false, maxSize:100
		fg_udrz nullable:false, maxSize:1
	}
}
