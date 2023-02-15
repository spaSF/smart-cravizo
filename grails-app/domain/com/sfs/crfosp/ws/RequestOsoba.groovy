package com.sfs.crfosp.ws

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.domain.Osoba

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="wsResponseOsoba")
class RequestOsoba implements Serializable{
	RequestLog request
	@XmlElement(name="OSOBA")
	Osoba osoba

	@XmlElement(name="IFO_RESPONSE")
	String ifoResponse
	@XmlAttribute(name="PRIZNAK_4BEZ")
	Boolean priznak4Bez

	@XmlElement(name="NAVRATOVY_KOD")
	Integer respkod
	@XmlElement(name="NAVRATOVA_SPRAVA")
	String respmsg
	@XmlElement(name="IMPORT_KOD")
	Integer impKod
	@XmlElement(name="IMPORT_SPRAVA")
	String impMsg

	static mapping = {
		table 'CRWSOS'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		request column:'REQID'
		osoba column:'CROSOBA',lazy:false
		priznak4Bez column:'FG4BEZ',sqlType:'NUMBER',length:1
		ifoResponse column:'IFO',sqlType:'char',length:32
		respkod column:'RESPKOD',sqlType:'NUMBER',length:5
		respmsg column:'RESPMSG',sqlType:'VARCHAR2',length:512
		impKod column:'IMPKOD',sqlType:'NUMBER',length:5
		impMsg column:'IMPMSG',sqlType:'VARCHAR2',length:512
	}
	static constraints = {
		id nullable:false
		request nullable:false
		osoba nullable:true
		priznak4Bez nullable:true
		ifoResponse nullable:true
		respkod nullable:true
		respmsg nullable:true
		impKod nullable:true
		impMsg nullable:true
	}
}
