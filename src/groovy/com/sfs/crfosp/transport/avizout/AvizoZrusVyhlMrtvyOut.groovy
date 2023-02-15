package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.aviza.AvizoZrusVyhlMrtvy

@XmlAccessorType(XmlAccessType.NONE)
class AvizoZrusVyhlMrtvyOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long zrusVyhlMrtvyid
		
	@XmlElement(name="DP")
	//@XmlElement(name="DTPRAV")
	@XmlSchemaType(name = "date")
	Date dtpravA

	@XmlElement(name="VY")
	//@XmlElement(name="MRTVY")
	Boolean zrusnezrusA
	
	def dajAvizoZrusVyhlMrtvyOut(AvizoZrusVyhlMrtvy avizoZrusVyhlMrtvy) {
		if (avizoZrusVyhlMrtvy.zrusVyhlMrtvyid) {
			def avizoZrusVyhlMrtvyOut = new AvizoZrusVyhlMrtvyOut()
			avizoZrusVyhlMrtvyOut.zrusVyhlMrtvyid = avizoZrusVyhlMrtvy.zrusVyhlMrtvyid
			avizoZrusVyhlMrtvyOut.dtpravA = avizoZrusVyhlMrtvy.dtprav
			avizoZrusVyhlMrtvyOut.zrusnezrusA = avizoZrusVyhlMrtvy.zrusnezrus
			
//			avizoZrusVyhlMrtvyOut.zrusVyhlMrtvyid = avizoZrusVyhlMrtvy.zrusVyhlMrtvyid
//			avizoZrusVyhlMrtvyOut.dtpravA = avizoZrusVyhlMrtvy.dtpravA ? avizoZrusVyhlMrtvy.dtprav : null
//			avizoZrusVyhlMrtvyOut.zrusnezrusA = avizoZrusVyhlMrtvy.zrusnezrusA ? avizoZrusVyhlMrtvy.zrusnezrus : null
			return avizoZrusVyhlMrtvyOut
		}
	}
}