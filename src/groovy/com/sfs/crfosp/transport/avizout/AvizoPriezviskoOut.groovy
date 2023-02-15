package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.aviza.AvizoPriezvisko
import com.sfs.crfosp.domain.Priezvisko


@XmlAccessorType(XmlAccessType.NONE)
class AvizoPriezviskoOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long  priezviskosobyid

	@XmlElement(name="PO",required=true)
	//@XmlElement(name="PRIEZVISKO_IDX",required=true)
	Integer priezvisko_idxA

	@XmlElement(name="PR")
	//@XmlElement(name="PRIEZVISKO")
	String priezviskoA

	def dajAvizoPriezviskoOut(AvizoPriezvisko avizoPriezvisko) {
		if (avizoPriezvisko.priezviskoA) {
			def avizoPriezviskoOut = new AvizoPriezviskoOut()
			avizoPriezviskoOut.priezviskosobyid = avizoPriezvisko.priezviskosobyid
			avizoPriezviskoOut.priezviskoA = avizoPriezvisko.priezvisko
			avizoPriezviskoOut.priezvisko_idxA = avizoPriezvisko.priezvisko_idx
//			avizoPriezviskoOut.priezviskoA = avizoPriezvisko.priezviskoA ? avizoPriezvisko.priezvisko : null
//			avizoPriezviskoOut.priezvisko_idxA = avizoPriezvisko.priezvisko_idxA ? avizoPriezvisko.priezvisko_idx : null
			return avizoPriezviskoOut
		}
	}
}
