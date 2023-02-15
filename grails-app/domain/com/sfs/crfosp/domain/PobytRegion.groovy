package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class PobytRegion implements Serializable,Comparable {
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="REGIDX",required=true)
	Integer regidx
	@XmlElement(name="NAZOV",required=true)
	String nazov
	
	@Override
	public int compareTo(obj) {
		this.getRegidx().compareTo(obj.regidx)
	}
	static belongsTo = [crpobyt:Pobyt]
	
	static mapping = {
		table 'CRREGPOB'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		nazov column:'NAZOV',sqlType:'varchar2',length:250
		crpobyt column:'CRPOBYT'
	}
	static constraints = {
		id nullable:false
		regidx nullable:false
		nazov nullable:false, maxSize:250
	}
}
