package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import com.sfs.crfosp.aviza.AvizoOsoba

@XmlAccessorType(XmlAccessType.NONE)
class AvizoOsobaOut implements Serializable{
	@XmlElement(name="ID",required=true)
	//@XmlElement(name="IFO",required=true)
	String ifo
	@XmlElement(name="IP")
	//@XmlElement(name="IFO_TOT")
	String ifo_tot
	@XmlElement(name="NI")
	//@XmlElement(name="NARODNOST")
	Long narodnostA
	@XmlElement(name="PI")
	//@XmlElement(name="POHLAVIE")
	Long pohlA
	@XmlElement(name="RS")
	//@XmlElement(name="ROD_STAV")
	Long rodstA
	@XmlElement(name="RC")
	//@XmlElement(name="RC")
	String rcA
	@XmlElement(name="SZ")
	//@XmlElement(name="STUPEN_ZVEREJ")
	Long stzverejA
	@XmlElement(name="TV")
	//@XmlElement(name="TYP_OSOBY")
	Long typosoA
	@XmlElement(name="IC")
	//@XmlElement(name="IDENIF_CUDZINCA")
	String icA
	@XmlElement(name="SU")
	//@XmlElement(name="STAT_UMRTIA")
	Long stumrA
	@XmlElement(name="PA")
	//@XmlElement(name="MATRIKA_UMRTIA")
	String umrmatrA
	@XmlElement(name="UC")
	//@XmlElement(name="UC_UMRTIA")
	Long ucumrA
	@XmlElement(name="MU")
	//@XmlElement(name="MIESTO_UMRTIA")
	String mumrA
	@XmlElement(name="DU")
	//@XmlElement(name="DT_UMRTIA")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtumrA
	@XmlElement(name="SN")
	//@XmlElement(name="STAT_NARODENIA")
	Long stnarA
	@XmlElement(name="PG")
	//@XmlElement(name="ROD_MATRIKA")
	String rodmatrA
	@XmlElement(name="UE")
	//@XmlElement(name="UC_NARODENIA")
	Long ucnarA
	@XmlElement(name="MN")
	//@XmlElement(name="MIESTO_NARODENIA")
	String mnarA
	@XmlElement(name="DN")
	//@XmlElement(name="DT_NARODENIA")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtnarA
	@XmlElement(name="RN")
	//@XmlElement(name="ROK_NARODENIA")
	Integer roknarA
	@XmlElement(name="BI")
	//@XmlElement(name="BIFO")
	String bifoA
	@XmlElement(name="UL")
	//@XmlElement(name="OKRES_NARODENIA")
	Long okrnarA
	@XmlElement(name="UO")
	//@XmlElement(name="OKRES_UMRTIA")
	Long okrumrA

	@XmlElementWrapper(name="AUTList", required=true, nillable = true)
	@XmlElement(name="AUT")
	//@XmlElementWrapper(name="UDEL_STRATA_OBC_LIST")
	//@XmlElement(name="UDEL_STRATA_OBC")
	Set<AvizoUdelStObcianstvoOut> udelStObcianstvo

	@XmlElementWrapper(name="ASNList", required=true, nillable = true)
	@XmlElement(name="ASN")
	Set<AvizoObmPravnejSposOut> obmPravnejSpos

	@XmlElementWrapper(name="AMEList", required=true, nillable = true)
	@XmlElement(name="AME")
	//@XmlElementWrapper(name="MENO_LIST")
	//@XmlElement(name="MENO")
	Set<AvizoMenoOut> meno

	@XmlElementWrapper(name="APRList", required=true, nillable = true)
	@XmlElement(name="APR")
	//@XmlElementWrapper(name="PRIEZ_LIST")
	//@XmlElement(name="PRIEZVISKO")
	Set<AvizoPriezviskoOut> priezvisko

	@XmlElementWrapper(name="ARPList", required=true, nillable = true)
	@XmlElement(name="ARP")
	//@XmlElementWrapper(name="RODNEPRIEZ_LIST")
	//@XmlElement(name="RODNE_PRIEZVISKO")
	Set<AvizoRodnePriezOut> rodnepriezvisko

	@XmlElementWrapper(name="ATSList", required=true, nillable = true)
	@XmlElement(name="ATSL")
	//@XmlElementWrapper(name="TITUL_LIST")
	//@XmlElement(name="TITUL")
	Set<AvizoTitulOut> titul

	@XmlElement(name="ADSList", required=true, nillable = true)
	String adsllist

	@XmlElementWrapper(name="ASRList", required=true, nillable = true)
	@XmlElement(name="ASR")
	//@XmlElementWrapper(name="STATNA_PRISL_LIST")
	//@XmlElement(name="STATNA_PRISL")
	Set<AvizoStatnaPrislusnotOut> statnaPrislusnost

	@XmlElementWrapper(name="APZList", required=true, nillable = true)
	@XmlElement(name="APZ")
	Set<AvizoZrusVyhlMrtvyOut> zrusVyhlMrtvy

	@XmlElementWrapper(name="APOList", required=true, nillable = true)
	@XmlElement(name="APO")
	//@XmlElementWrapper(name="POBYT_OSOBY_LIST")
	//@XmlElement(name="POBYT_OSOBY")
	Set<AvizoPobytOut> pobyt

	@XmlElementWrapper(name="AVSList", required=true, nillable = true)
	@XmlElement(name="AVS")
	Set<AvizoRodinnyVztahOut> rodinnyVztah

	@XmlElement(name="AZPList", required=true, nillable = true)
	String azplist

	def dajAvizoOsobaOut(AvizoOsoba avizoOsoba) {
		def avizoOsobaOut = new AvizoOsobaOut()
		avizoOsobaOut.ifo = avizoOsoba.ifo.trim()
		avizoOsobaOut.rcA=avizoOsoba.rcA?avizoOsoba.rc:null
		avizoOsobaOut.dtnarA=avizoOsoba.dtnarA?avizoOsoba.dtnar:null
		avizoOsobaOut.ucnarA=avizoOsoba.ucnarA?avizoOsoba.ucnar?.id:null
		avizoOsobaOut.mnarA=avizoOsoba.mnarA?avizoOsoba.mnar:null
		avizoOsobaOut.stnarA=avizoOsoba.stnarA?avizoOsoba.stnar?.id:null
		avizoOsobaOut.pohlA=avizoOsoba.pohlA?avizoOsoba.pohl?.id:null
		avizoOsobaOut.rodstA=avizoOsoba.rodstA?avizoOsoba.rodst?.id:null
		avizoOsobaOut.rodmatrA=avizoOsoba.rodmatrA?avizoOsoba.rodmatr:null
		avizoOsobaOut.narodnostA=avizoOsoba.narodnostA?avizoOsoba.narodnost?.id:null
		avizoOsobaOut.dtumrA=avizoOsoba.dtumrA?avizoOsoba.dtumr:null
		avizoOsobaOut.ucumrA=avizoOsoba.ucumrA?avizoOsoba.ucumr?.id:null
		avizoOsobaOut.mumrA=avizoOsoba.mumrA?avizoOsoba.mumr:null
		avizoOsobaOut.umrmatrA=avizoOsoba.umrmatrA?avizoOsoba.umrmatr:null
		avizoOsobaOut.stumrA=avizoOsoba.stumrA?avizoOsoba.stumr?.id:null
		avizoOsobaOut.bifoA=avizoOsoba.bifoA?avizoOsoba.bifo:null
		avizoOsobaOut.typosoA=avizoOsoba.typosoA?avizoOsoba.typoso?.id:null
		avizoOsobaOut.okrnarA=avizoOsoba.okrnarA?avizoOsoba.okrnar?.id:null
		avizoOsobaOut.okrumrA=avizoOsoba.okrumrA?avizoOsoba.okrumr?.id:null
		avizoOsobaOut.icA=avizoOsoba.icA?avizoOsoba.ic?.trim():null
		avizoOsobaOut.stzverejA=avizoOsoba.stzverejA?avizoOsoba.stzverej?.id:null
		avizoOsobaOut.roknarA=avizoOsoba.roknarA?avizoOsoba.roknar:null

		avizoOsobaOut.meno = avizoOsoba.meno?[]:null
		avizoOsoba.meno*.each{avizoOsobaOut.meno.add(new AvizoMenoOut().dajAvizoMenoOut(it))}

		avizoOsobaOut.priezvisko = avizoOsoba.priezvisko?[]:null
		avizoOsoba.priezvisko*.each{avizoOsobaOut.priezvisko.add(new AvizoPriezviskoOut().dajAvizoPriezviskoOut(it))}

		avizoOsobaOut.rodnepriezvisko = avizoOsoba.rodnepriezvisko?[]:null
		avizoOsoba.rodnepriezvisko*.each{avizoOsobaOut.rodnepriezvisko.add(new AvizoRodnePriezOut().dajAvizoRodnePriezOut(it))}

		avizoOsobaOut.titul = avizoOsoba.titul?[]:null
		avizoOsoba.titul*.each{avizoOsobaOut.titul.add(new AvizoTitulOut().dajAvizoTitulOut(it))}

		avizoOsobaOut.statnaPrislusnost = avizoOsoba.statnaPrislusnost?[]:null
		avizoOsoba.statnaPrislusnost*.each{avizoOsobaOut.statnaPrislusnost.add(new AvizoStatnaPrislusnotOut().dajAvizoStatnaPrislusnotOut(it))}

		avizoOsobaOut.pobyt = avizoOsoba.pobyt?[]:null
		avizoOsoba.pobyt?.each{avizoOsobaOut.pobyt.add(new AvizoPobytOut().dajAvizoPobytOut(it))}

		avizoOsobaOut.rodinnyVztah = avizoOsoba.rodinnyVztah?[]:null
		avizoOsoba.rodinnyVztah*.each{avizoOsobaOut.rodinnyVztah.add(new AvizoRodinnyVztahOut().dajAvizoRodinnyVztahOut(it))}

		avizoOsobaOut.udelStObcianstvo = avizoOsoba.udelStObcianstvo?[]:null
		avizoOsoba.udelStObcianstvo*.each{avizoOsobaOut.udelStObcianstvo.add(new AvizoUdelStObcianstvoOut().dajAvizoUdelStObcianstvoOut(it))}

		avizoOsobaOut.obmPravnejSpos = avizoOsoba.obmPravnejSpos?[]:null
		avizoOsoba.obmPravnejSpos*.each{avizoOsobaOut.obmPravnejSpos.add(new AvizoObmPravnejSposOut().dajAvizoObmPravnejSposOut(it))}

		avizoOsobaOut.zrusVyhlMrtvy = avizoOsoba.zrusVyhlMrtvy?[]:null
		avizoOsoba.zrusVyhlMrtvy*.each{avizoOsobaOut.zrusVyhlMrtvy.add(new AvizoZrusVyhlMrtvyOut().dajAvizoZrusVyhlMrtvyOut(it))}

		
		return avizoOsobaOut
	}

}
