package com.sfs.crfosp.ws

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.domain.Osoba

class EventOsoba implements Serializable{
	Long id
	EventLog event
	Osoba osoba
	String ifoResponse
	Integer respkod
	String respmsg
	Integer impKod
	String impMsg

	static mapping = {
		table 'CREVOS'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		event column:'EVENTID'
		osoba column:'CROSOBA',lazy:false
		ifoResponse column:'IFO',sqlType:'char',length:32
		respkod column:'RESPKOD',sqlType:'NUMBER',length:5
		respmsg column:'RESPMSG',sqlType:'VARCHAR2',length:512
		impKod column:'IMPKOD',sqlType:'NUMBER',length:5
		impMsg column:'IMPMSG',sqlType:'VARCHAR2',length:512
	}
	static constraints = {
		id nullable:false
		event nullable:false
		osoba nullable:true
		ifoResponse nullable:true
		respkod nullable:true
		respmsg nullable:true
		impKod nullable:true
		impMsg nullable:true
	}
}
