package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.UzemnyCelokCis

@XmlAccessorType(XmlAccessType.NONE)
class ZakazPobytuRegion implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="OBEC",required=true)
	UzemnyCelokCis obec
	
	static belongsTo= [zakazPobytu:ZakazPobytu]
	
	static mapping = {
		table 'CRZAKUC'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		obec column:'OBEC',fetch:'join'
		zakazPobytu column:'CRZAKPOB'
	}
	static constraints = {
		id nullable:false
		obec nullable:false
	}
}
