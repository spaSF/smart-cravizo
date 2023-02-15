package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class MenoOsoby implements Serializable,Comparable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="MENO")
	String meno
	@XmlElement(name="MENO_IDX",required=true)
	Integer meno_idx
	
	@Override
	public int compareTo(obj) {
		this.getMeno_idx().compareTo(obj.meno_idx)
	}

	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRMENO'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		meno column:'MENO',sqlType:'varchar2',length:250
		osoba column:'CROSOBA'
	}
	static constraints = {
		id nullable:false
		meno nullable:true, maxSize:250
		meno_idx nullable:false
	}
}
