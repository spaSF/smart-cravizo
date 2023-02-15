package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.aviza.AvizoTitul
import com.sfs.crfosp.cis.TitulCis
import com.sfs.crfosp.cis.TypTitulCis
import com.sfs.crfosp.domain.Titul

@XmlAccessorType(XmlAccessType.NONE)
class AvizoTitulOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long titulOsobyid

	@XmlElement(name="TI",required=true)
	//@XmlElement(name="TITUL",required=true)
	Long titulA

	@XmlElement(name="TT",required=true)
	//@XmlElement(name="TYPTIT",required=true)
	Long typtitA

	def dajAvizoTitulOut(AvizoTitul avizoTitul) {
		if (avizoTitul.titulA || avizoTitul.typtitA) {
			def avizoTitulOut = new AvizoTitulOut()
			avizoTitulOut.titulOsobyid = avizoTitul.titulOsobyid
			avizoTitulOut.titulA = avizoTitul.titul?.id
			avizoTitulOut.typtitA = avizoTitul.typtit?.id
			
//			avizoTitulOut.titulOsobyid = avizoTitul.titulOsobyid
//			avizoTitulOut.titulA = avizoTitul.titulA ? avizoTitul.titul?.id : null
//			avizoTitulOut.typtitA = avizoTitul.typtitA ? avizoTitul.typtit?.id : null
			return avizoTitulOut
		}
	}

}