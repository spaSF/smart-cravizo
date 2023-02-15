package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.ws.RequestLog

@XmlAccessorType(XmlAccessType.NONE)
class AvizoUzivatelOut implements Serializable {
	@XmlElement(name="PO")
	String uziv
	@XmlElement(name="TI")
	Long reqid
	
	def dajAvizoUzivatelOut(RequestLog requestLog) {
		def springSecurityService
		def avizoUzivatelOut = new AvizoUzivatelOut()
		avizoUzivatelOut.uziv = requestLog.uziv
		avizoUzivatelOut.reqid = requestLog.id
		
		return avizoUzivatelOut
	}
}
