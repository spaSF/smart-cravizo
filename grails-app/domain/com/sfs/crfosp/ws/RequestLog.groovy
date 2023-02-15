package com.sfs.crfosp.ws

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.ws.enums.RequestStatusEnum

@XmlAccessorType(XmlAccessType.NONE )
@XmlType(name="WS_ODPOVED")
class RequestLog implements Serializable{
	@XmlAttribute(required=true,name="DOTAZ_ID")
	Long id
	@XmlElement(name="SYSTEM_SP",required=true)
	SystemCis systemSP
	@XmlElement(name="UZIV")
	String uziv
	String wsin
	@XmlElement(name="POUZITE_EXTERNE_WS")
	Boolean iswsout
	Date reqtime
	@XmlElement(name="STAV",required=true)
	RequestStatusEnum stav
	Date statustime
	@XmlElement(name="NAVRATOVY_KOD")
	Integer respkod
	@XmlElement(name="NAVRATOVA_SPRAVA")
	String respmsg
	
//	@XmlElementWrapper(name="RESPONSE_OSOBY")
//	@XmlElement(name="RESPONSE_OSOBA")
//	Set<RequestOsoba> osoby

	//Avizo avizo

	static hasMany = [parametre:RequestParam,requestfiles:RequestFiles,avizo:Avizo,avizaRequest:RequestAvizo,osoby:RequestOsoba]
	static mappedBy = [parametre:"request",requestfiles:"request",avizo:"request",avizaRequest:"request",osoby:"request"]

	def setRespmsg(String respmsg){
		if(respmsg?.length()>512){
			this.respmsg = respmsg[0..511]
		}else{
			this.respmsg = respmsg
		}
	}

	static mapping = {
		table 'CRREQUEST'
		version false
		id generator:'assigned'
		wsin column:'WSIN',sqlType:'varchar2',length:50
		stav column:'STATUS',sqlType:'varchar2',length:50
		systemSP column:'SPSYS' ,fetch:'join',lazy:false
//		osoby joinTable: false
		parametre joinTable:false
		requestfiles joinTable: false
		avizaRequest joinTable: false
	}
	static constraints = {
		id nullable:false
		uziv nullable:true, maxSize:255
		wsin nullable:true, maxSize:50
		iswsout nullable:true
		reqtime nullable:false
//		systemSP nullable:false
		statustime nullable:true
		respkod nullable:true
		respmsg nullable:true, maxSize:512
	}
}
