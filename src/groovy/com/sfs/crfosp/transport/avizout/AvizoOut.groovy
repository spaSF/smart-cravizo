package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.ws.RequestLog

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="TransEnvIn", namespace="http://www.egov.sk/mvsr/RFO/Podp/Ext/ZaznamenanieAvizaONezrovnalostiachWS-v1.0")
//@XmlType(name="AAN")
class AvizoOut implements Serializable {
	
	def springSecurityService
	
	@XmlAttribute(required=true,name="corrID")
	String corrId
	
	@XmlElement(name="AAN",required=true)
	AvizoAAnOut avizoAAn
	
	@XmlElement(name="UES",required=true)
	AvizoUzivatelOut avizoUzivatel
	
	def dajAvizoOut(Avizo avizo) {
		def avizoOut = new AvizoOut()
		avizoOut.corrId = avizo.corrId
		avizoOut.avizoAAn = new AvizoAAnOut().dajAvizoAAnOut(avizo)
		avizoOut.avizoUzivatel = new AvizoUzivatelOut().dajAvizoUzivatelOut(avizo.request)		
		return avizoOut
	}
}