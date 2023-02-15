package com.sfs.crfosp.transport.aviza

import java.util.Date

import groovy.transform.InheritConstructors

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper


import com.sfs.crfosp.aviza.AvizoAtributy
import com.sfs.crfosp.ws.RequestAvizo
import com.sfs.crfosp.ws.enums.AvizoStatusEnum

@XmlAccessorType(XmlAccessType.NONE)
@InheritConstructors
class VybavenieAvizo implements Serializable {
	private static final long serialVersionUID=1L
	
	@XmlElement(required=true,name="ID")
	Long avizo
	@XmlElement(required=true,name="IFO")
	String ifo
//	@XmlElement(name="NAVRATOVY_KOD")
	Integer respkod
//	@XmlElement(name="NAVRATOVA_SPRAVA")
	String respmsg
	@XmlElement(required=true,name="VYTVORENE")
	Date vytvor
	@XmlElement(name="VYBAVENE")
	Date vybav
	@XmlElement(name="STAV_AVIZA")
	Long status
	@XmlElementWrapper(name="POLOZKY_LIST")
	@XmlElement(name="POLOZKA")
	Set<VybavenieAtributy> vybavenieAtributy

	VybavenieAvizo(RequestAvizo rqAvizo){
		this.avizo = rqAvizo.getAvizo()?.id
		this.ifo = rqAvizo.getAvizo()?.getOsoba().getIfo()
		this.respkod = rqAvizo.getRespkod()
		this.vytvor = rqAvizo.getAvizo()?.dateCreated
		this.vybav = rqAvizo.getAvizo()?.datumVybav
		this.status = rqAvizo.getAvizo().status.id
		this.vybavenieAtributy = new ArrayList<VybavenieAtributy>()
		rqAvizo.getAvizo()?.getAvizoAtributy()?.each{AvizoAtributy att->
			this.vybavenieAtributy.add(new VybavenieAtributy(att))
		}
		this.respmsg = rqAvizo.getRespmsg()
	}
}
