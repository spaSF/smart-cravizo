package com.sfs.crfosp.ws.transport

import java.util.Set

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.NarodnostCis
import com.sfs.crfosp.cis.PohlavieCis
import com.sfs.crfosp.cis.RodinnyStavCis
import com.sfs.crfosp.cis.StatCis
import com.sfs.crfosp.cis.UzemnyCelokCis
import com.sfs.crfosp.domain.MenoOsoby
import com.sfs.crfosp.domain.Priezvisko
import com.sfs.crfosp.domain.RodnePriezvisko
import com.sfs.crfosp.domain.StatnaPrislusnot
import com.sfs.crfosp.domain.Titul

@XmlAccessorType(XmlAccessType.NONE)
class OsobaSkupUdIdent implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(name="DT_ZMENA",required=true)
	Date dtupd
	@XmlElement(name="DT_NARODENIA")
	@XmlSchemaType(name = "date")
	Date dtnar
	@XmlElement(name="UC_NARODENIA")
	UzemnyCelokCis ucnar
	@XmlElement(name="MIESTO_NARODENIA")
	String mnar
	@XmlElement(name="STAT_NARODENIA")
	StatCis stnar
	@XmlElement(name="ROD_MATRIKA")
	String rodmatr
	@XmlElement(name="POHLAVIE")
	PohlavieCis pohl
	@XmlElement(name="ROD_STAV")
	RodinnyStavCis rodst
	@XmlElement(name="NARODNOST")
	NarodnostCis narodnost
	@XmlElement(name="IDENIF_CUDZINCA")
	String ic
	@XmlElement(name="ROK_NARODENIA")
	Integer roknar
	//@XmlElement(name="OKRES_NARODENIA")
	//UzemnyCelokCis okrnar
	
	@XmlElementWrapper(name="MENO_LIST")
	@XmlElement(name="MENO")
	Set<MenoOsoby> meno

	@XmlElementWrapper(name="PRIEZ_LIST")
	@XmlElement(name="PRIEZVISKO")
	Set<Priezvisko> priezvisko

	@XmlElementWrapper(name="RODNEPRIEZ_LIST")
	@XmlElement(name="RODNE_PRIEZVISKO")
	Set<RodnePriezvisko> rodnepriezvisko

	@XmlElementWrapper(name="TITUL_LIST")
	@XmlElement(name="TITUL")
	Set<Titul> titul

	@XmlElementWrapper(name="STATNA_PRISL_LIST")
	@XmlElement(name="STATNA_PRISL")
	Set<StatnaPrislusnot> statnaPrislusnost
}
