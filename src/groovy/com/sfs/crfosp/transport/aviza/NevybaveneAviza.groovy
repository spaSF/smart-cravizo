package com.sfs.crfosp.transport.aviza

import groovy.transform.InheritConstructors

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.aviza.AvizoAtributy
import com.sfs.crfosp.ws.RequestAvizo

@XmlAccessorType(XmlAccessType.NONE)
@InheritConstructors
class NevybaveneAviza implements Serializable {
	private static final long serialVersionUID=1L
	
	@XmlElement(required=true,name="ID")
	Long avizo
	@XmlElement(required=true,name="IFO")
	String ifo
	@XmlElement(required=true,name="POPIS")
	String popis
	@XmlElement(name="DT_ZMENY")
	@XmlSchemaType(name = "date")
	Date lastUpdated
	@XmlElementWrapper(name="POLOZKY_LIST")
	@XmlElement(name="POLOZKA")
	Set<VybavenieAtributy> vybavenieAtributy
	
//	NevybaveneAviza(RequestAvizo rqAvizo){
//		this.avizo = rqAvizo.getAvizo()?.id
//		this.ifo = rqAvizo.getAvizo()?.getOsoba().getIfo()
//		this.popis = rqAvizo.getAvizo()?.getPopis()
//		this.vybavenieAtributy = new ArrayList<VybavenieAtributy>()
//		rqAvizo.getAvizo()?.getAvizoAtributy()?.each{AvizoAtributy att->
//			this.vybavenieAtributy.add(new VybavenieAtributy(att))
//		}
//	}
	
	NevybaveneAviza(Avizo avizo){
		this.avizo = avizo.id
		this.ifo = avizo.osoba.ifo
		this.popis = avizo.popis
		this.lastUpdated = avizo.lastUpdated
		
		this.vybavenieAtributy = new ArrayList<VybavenieAtributy>()
		avizo.avizoAtributy?.each{AvizoAtributy att->
			this.vybavenieAtributy.add(new VybavenieAtributy(att))
		}
	}
}
