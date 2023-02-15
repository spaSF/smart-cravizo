package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.aviza.AvizoStatnaPrislusnot
import com.sfs.crfosp.cis.StatCis

@XmlAccessorType(XmlAccessType.NONE)
class AvizoStatnaPrislusnotOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long statnaPrislusnotid

	@XmlElement(name="SI",required=true)
	//@XmlElement(name="STPRIS",required=true)
	Long stprisA

	@XmlElement(name="STASINA")
	String stprisNazovA

	@XmlElement(name="PH")
	Boolean fg_sr
	
	def dajAvizoStatnaPrislusnotOut(AvizoStatnaPrislusnot avizoStatnaPrislusnot) {
		if (avizoStatnaPrislusnot.stprisA || avizoStatnaPrislusnot.stprisNazovA) {
			def avizoStatnaPrislusnotOut = new AvizoStatnaPrislusnotOut()
			avizoStatnaPrislusnotOut.statnaPrislusnotid = avizoStatnaPrislusnot.statnaPrislusnotid
			avizoStatnaPrislusnotOut.stprisA = avizoStatnaPrislusnot.stpris?.id
			avizoStatnaPrislusnotOut.stprisNazovA = avizoStatnaPrislusnot.stpris?.nazov
			avizoStatnaPrislusnotOut.fg_sr = avizoStatnaPrislusnotOut.stprisA ? avizoStatnaPrislusnot.fg_sr ? avizoStatnaPrislusnot.fg_sr : Boolean.FALSE : null

//			avizoStatnaPrislusnotOut.statnaPrislusnotid = avizoStatnaPrislusnot.statnaPrislusnotid
//			avizoStatnaPrislusnotOut.stprisA = avizoStatnaPrislusnot.stprisA ? avizoStatnaPrislusnot.stpris?.id : null
//			avizoStatnaPrislusnotOut.stprisNazovA = avizoStatnaPrislusnot.stprisA ? avizoStatnaPrislusnot.stpris?.nazov : null
//			avizoStatnaPrislusnotOut.fg_sr = avizoStatnaPrislusnotOut.stprisA ? avizoStatnaPrislusnot.fg_sr ? avizoStatnaPrislusnot.fg_sr : Boolean.FALSE : null
			return avizoStatnaPrislusnotOut
		}
	}
}
