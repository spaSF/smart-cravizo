package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.RequestStatusEnum

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WS6_DATA")
class NovoNarodeneDeti implements Serializable {
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
	@XmlElementWrapper(name="IDENTIFIKATORY_FO")
	@XmlElement(name="IFO")
	List<String> ifoList

	NovoNarodeneDeti(RequestLog req){
		this.id=req.id
		this.systemSP=req.systemSP
		this.uziv=req.uziv
		this.stav=req.stav
		this.respkod=req.getRespkod()
		this.respmsg=req.getRespmsg()
		this.ifoList = new ArrayList<String>(req.osoby*.osoba.ifo)
	}

}
