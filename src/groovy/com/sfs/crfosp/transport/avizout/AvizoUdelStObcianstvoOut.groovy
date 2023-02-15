package com.sfs.crfosp.transport.avizout

import java.util.Date

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import com.sfs.crfosp.aviza.AvizoUdelStObcianstvo

@XmlAccessorType(XmlAccessType.NONE)
class AvizoUdelStObcianstvoOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long udelstobid

	@XmlElement(name="DP")
	//@XmlElement(name="DT_PREVZATIA")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtprevA

	@XmlElement(name="UD")
	//@XmlElement(name="UDELENIE_STRATA")
	Boolean fg_udstA

	def dajAvizoUdelStObcianstvoOut(AvizoUdelStObcianstvo avizoUdelStObcianstvo) {
		def avizoUdelStObcianstvoOut = new AvizoUdelStObcianstvoOut()
		avizoUdelStObcianstvoOut.udelstobid = avizoUdelStObcianstvo.udelstobid
		avizoUdelStObcianstvoOut.dtprevA = avizoUdelStObcianstvo.dtprev
		avizoUdelStObcianstvoOut.fg_udstA = avizoUdelStObcianstvo.fg_udst
		
//		avizoUdelStObcianstvoOut.udelstobid = avizoUdelStObcianstvo.udelstobid
//		avizoUdelStObcianstvoOut.dtprevA = avizoUdelStObcianstvo.dtprevA ? avizoUdelStObcianstvo.dtprev : null
//		avizoUdelStObcianstvoOut.fg_udstA = avizoUdelStObcianstvo.fg_udstA ? avizoUdelStObcianstvo.fg_udst : null
		return avizoUdelStObcianstvoOut
	}
}
