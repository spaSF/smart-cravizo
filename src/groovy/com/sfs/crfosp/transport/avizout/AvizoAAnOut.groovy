package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.ws.RequestLog

@XmlAccessorType(XmlAccessType.NONE)
class AvizoAAnOut implements Serializable {
	@XmlElement(name="PO",required=true)
	String popis
	@XmlElement(name="AUS",required=true)
	AvizoOsobaOut avosoba
	
	def dajAvizoAAnOut(Avizo avizo) {
		AvizoAAnOut avizoAAnOut = new AvizoAAnOut()
		avizoAAnOut.popis = avizo.popis
		avizoAAnOut.avosoba = new AvizoOsobaOut().dajAvizoOsobaOut(avizo.avosoba)
		return avizoAAnOut
	}
}
