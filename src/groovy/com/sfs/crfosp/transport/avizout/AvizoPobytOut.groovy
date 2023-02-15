package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import com.sfs.crfosp.aviza.AvizoPobyt
import com.sfs.crfosp.cis.TypPobytuCis

@XmlAccessorType(XmlAccessType.NONE)
class AvizoPobytOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long pobytid

	@XmlElement(name="TP",required=true)
	//@XmlElement(name="TYPPOB",required=true)
	Long typpobA

	@XmlElement(name="TPOTPNA",required=true)
	String cistyppobytu
	//	// @XmlElement(name="DTOD")
	//	// @XmlSchemaType(name = "date")
	//	Date dtodA
	//
	//	// @XmlElement(name="DTDO")
	//	// @XmlSchemaType(name = "date")
	//	Date dtdoA

	@XmlElement(name="DP")
	//@XmlElement(name="DTPRIHL")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtprihlA

	@XmlElement(name="DU")
	//@XmlElement(name="DTUKON")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtukonA

	//	Long ucpobytA
	//
	//	@XmlElement(name="OBECNAZ")
	//	String obecnazA
	//
	//	@XmlElement(name="UKOHO")
	//	String ukohoA

	@XmlElement(name="SC")
	//@XmlElement(name="SUPCIS")
	Long supcisA

	@XmlElement(name="PC")
	//@XmlElement(name="POBCIS")
	String pobcisA

	// UzemnyCelokCis regobec
	@XmlElement(name="OC")
	String orientcA

	@XmlElement(name="ZO")
	//@XmlElement(name="ZNAKOC")
	String znakocA

	@XmlElement(name="UI")
	Long regulicaA

	// UzemnyCelokCis regobcast
	@XmlElement(name="UC")
	Long regobecA

	// RegUlicaCis regulica
	@XmlElement(name="UE")
	Long regobcastA

	@XmlElement(name="UL")
	Long regokresA
	// UzemnyCelokCis regokres

	@XmlElement(name="SI")
	Long pobstatA

	@XmlElement(name="MP")
	//@XmlElement(name="ZMIESTO")
	String zmiestoA

	@XmlElement(name="OO")
	//@XmlElement(name="ZOBEC")
	String zobecA

	@XmlElement(name="CO")
	//@XmlElement(name="ZCASTOB")
	String zcastobA

	@XmlElement(name="UM")
	//@XmlElement(name="ZULICA")
	String zulicaA

	@XmlElement(name="OP")
	//@XmlElement(name="ZOKRES")
	String zokresA

	@XmlElement(name="OI")
	//@XmlElement(name="ZORIENTCIS")
	String zorientcisA

	@XmlElement(name="SS")
	//@XmlElement(name="ZSUPISC")
	String zsupiscA

	@XmlElement(name="CB")
	//@XmlElement(name="ZCASTBUD")
	String zcastbudA

	@XmlElement(name="CY")
	String bytcisA

	//Identifikátor adresy v registry adries.
	@XmlElement(name="IA")
	String regadridA

	// ID vchodu domu
	@XmlElement(name="VD")
	Long regvchodA

	// RegDomCis regdom
	@XmlElement(name="DI")
	Long regdomA

	@XmlElement(name="AREList", nillable=true)
	String fakeList

	//	@XmlElement(name="CASTOBCE")
	//	String castobceA
	//
	//	@XmlElement(name="OKRES")
	//	String okresA
	//
	//	@XmlElement(name="ULICA")
	//	String ulicaA
	//
	//	@XmlElement(name="FG_MIMO")
	//	Boolean fg_mimoA

	def dajAvizoPobytOut(AvizoPobyt avizoPobyt) {
		if (avizoPobyt.typpobA || avizoPobyt.dtprihlA || avizoPobyt.dtukonA || avizoPobyt.supcisA || avizoPobyt.pobcisA || avizoPobyt.orientcA || avizoPobyt.znakocA || avizoPobyt.regulicaA || avizoPobyt.regobecA || avizoPobyt.regobcastA || avizoPobyt.regokresA || avizoPobyt.pobstatA || avizoPobyt.zmiestoA || avizoPobyt.zobecA || avizoPobyt.zcastobA || avizoPobyt.zulicaA || avizoPobyt.zokresA || avizoPobyt.zorientcisA || avizoPobyt.zsupiscA || avizoPobyt.zcastbudA || avizoPobyt.bytcisA || avizoPobyt.regadridA || avizoPobyt.regvchodA || avizoPobyt.regdomA) {
			def avizoPobytOut = new AvizoPobytOut()
			avizoPobytOut.pobytid = avizoPobyt.idmv ? Long.valueOf(avizoPobyt.idmv.longValue()) : avizoPobyt.pobytid
			avizoPobytOut.typpobA  = avizoPobyt.typpob.id
			avizoPobytOut.cistyppobytu = avizoPobyt.typpob.nazov
			avizoPobytOut.dtprihlA = avizoPobyt.dtprihl
			avizoPobytOut.dtukonA = avizoPobyt.dtukon
			avizoPobytOut.supcisA = avizoPobyt.supcis
			avizoPobytOut.pobcisA = avizoPobyt.pobcis
			avizoPobytOut.orientcA = avizoPobyt.orientc
			avizoPobytOut.znakocA = avizoPobyt.znakoc
			avizoPobytOut.regulicaA = avizoPobyt.regulica
			avizoPobytOut.regobecA = avizoPobyt.regobec
			avizoPobytOut.regobcastA = avizoPobyt.regobcast
			avizoPobytOut.regokresA = avizoPobyt.regokres
			avizoPobytOut.pobstatA = avizoPobyt.pobstat.id
			avizoPobytOut.zmiestoA = avizoPobyt.zmiesto
			avizoPobytOut.zobecA = avizoPobyt.zobec
			avizoPobytOut.zcastobA = avizoPobyt.zcastob
			avizoPobytOut.zulicaA = avizoPobyt.zulica
			avizoPobytOut.zokresA = avizoPobyt.zokres
			avizoPobytOut.zorientcisA = avizoPobyt.zorientcis
			avizoPobytOut.zsupiscA = avizoPobyt.zsupisc
			avizoPobytOut.zcastbudA = avizoPobyt.zcastbud
			avizoPobytOut.bytcisA = avizoPobyt.bytcis
			avizoPobytOut.regadridA = avizoPobyt.regadrid
			avizoPobytOut.regvchodA = avizoPobyt.regvchod
			avizoPobytOut.regdomA = avizoPobyt.regdom
			
/*			avizoPobytOut.typpobA  = avizoPobytOut.pobytid ? avizoPobyt.typpob?.id : null
			avizoPobytOut.cistyppobytu = avizoPobytOut.pobytid ? avizoPobyt.typpob?.nazov : null
			avizoPobytOut.dtprihlA = avizoPobytOut.pobytid ? avizoPobyt.dtprihl : null
			avizoPobytOut.dtukonA = avizoPobyt.dtukonA ? avizoPobyt.dtukon : null
			avizoPobytOut.supcisA = avizoPobyt.supcisA ? avizoPobyt.supcis : null
			avizoPobytOut.pobcisA = avizoPobyt.pobcisA ? avizoPobyt.pobcis : null
			avizoPobytOut.orientcA = avizoPobyt.orientcA ? avizoPobyt.orientc : null
			avizoPobytOut.znakocA = avizoPobyt.znakocA ? avizoPobyt.znakoc : null
			avizoPobytOut.regulicaA = avizoPobyt.regulicaA ? avizoPobyt.regulica : null
			avizoPobytOut.regobecA = avizoPobyt.regobecA ? avizoPobyt.regobec : null
			avizoPobytOut.regobcastA = avizoPobyt.regobcastA ? avizoPobyt.regobcast : null
			avizoPobytOut.regokresA = avizoPobyt.regokresA ? avizoPobyt.regokres : null
			avizoPobytOut.pobstatA = avizoPobyt.pobstatA ? avizoPobyt.pobstat?.id : null
			avizoPobytOut.zmiestoA = avizoPobyt.zmiestoA ? avizoPobyt.zmiesto : null
			avizoPobytOut.zobecA = avizoPobyt.zobecA ? avizoPobyt.zobec : null
			avizoPobytOut.zcastobA = avizoPobyt.zcastobA ? avizoPobyt.zcastob : null
			avizoPobytOut.zulicaA = avizoPobyt.zulicaA ? avizoPobyt.zulica : null
			avizoPobytOut.zokresA = avizoPobyt.zokresA ? avizoPobyt.zokres : null
			avizoPobytOut.zorientcisA = avizoPobyt.zorientcisA ? avizoPobyt.zorientcis : null
			avizoPobytOut.zsupiscA = avizoPobyt.zsupiscA ? avizoPobyt.zsupisc : null
			avizoPobytOut.zcastbudA = avizoPobyt.zcastbudA ? avizoPobyt.zcastbud : null
			avizoPobytOut.bytcisA = avizoPobyt.bytcisA ? avizoPobyt.bytcis : null
			avizoPobytOut.regadridA = avizoPobyt.regadridA ? avizoPobyt.regadrid : null
			avizoPobytOut.regvchodA = avizoPobyt.regvchodA ? avizoPobyt.regvchod : null
			avizoPobytOut.regdomA = avizoPobyt.regdomA ? avizoPobyt.regdom : null*/
			
			return avizoPobytOut
		}
	}

}
