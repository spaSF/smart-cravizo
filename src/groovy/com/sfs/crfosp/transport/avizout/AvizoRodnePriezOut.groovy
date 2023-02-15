package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.aviza.AvizoRodnePriez

@XmlAccessorType(XmlAccessType.NONE)
class AvizoRodnePriezOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long rodnePriezviskoid
	@XmlElement(name="PO",required=true)
	// @XmlElement(name="PRIEZVISKO_IDX",required=true)
	Integer priezvisko_idxA
	@XmlElement(name="RP")
	//@XmlElement(name="PRIEZVISKO")
	String priezviskoA

	def dajAvizoRodnePriezOut(AvizoRodnePriez avizoRodnePriez) {
		if(avizoRodnePriez.priezviskoA) {
			def avizoRodnePriezOut = new AvizoRodnePriezOut()
			avizoRodnePriezOut.rodnePriezviskoid = avizoRodnePriez.rodnePriezviskoid
			avizoRodnePriezOut.priezviskoA = avizoRodnePriez.priezvisko
			avizoRodnePriezOut.priezvisko_idxA = avizoRodnePriez.priezvisko_idx
//			avizoRodnePriezOut.priezviskoA = avizoRodnePriez.priezviskoA ? avizoRodnePriez.priezvisko : null
//			avizoRodnePriezOut.priezvisko_idxA = avizoRodnePriez.priezvisko_idxA ? avizoRodnePriez.priezvisko_idx : null
			return avizoRodnePriezOut
		}
	}
}
