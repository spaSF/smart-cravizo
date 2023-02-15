package com.sfs.crfosp.transport.aviza

import groovy.transform.InheritConstructors

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.ws.RequestAvizo
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.RequestStatusEnum

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WSA3_DATA")
@InheritConstructors
class NevybaveneAvizaData implements Serializable {
	private static final long serialVersionUID=1L
	
	@XmlAttribute(required=true,name="DOTAZ_ID")
	Long id
	@XmlElement(name="SYSTEM_SP",required=true)
	SystemCis systemSP
	@XmlElement(name="UZIV")
	String uziv
	@XmlElement(name="STAV",required=true)
	RequestStatusEnum stav
	@XmlElement(name="NAVRATOVY_KOD")
	Integer respkod
	@XmlElement(name="NAVRATOVA_SPRAVA")
	String respmsg
	@XmlElementWrapper(name="NEVYBAVENIE_AVIZA_LIST")
	@XmlElement(name="AVIZO")
	Set<NevybaveneAviza> nevybaveneAviza

	NevybaveneAvizaData(RequestLog req){
		this.id=req.id
		this.systemSP=req.systemSP
		this.uziv=req.uziv
		this.stav=req.stav
		this.respkod=req.getRespkod()
		this.respmsg=req.getRespmsg()	
		this.nevybaveneAviza = new ArrayList<NevybaveneAviza>()
	}
}
