package com.sfs.crfosp.aviza

import java.util.Set

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.NarodnostCis
import com.sfs.crfosp.cis.PohlavieCis
import com.sfs.crfosp.cis.RodinnyStavCis
import com.sfs.crfosp.cis.StatCis
import com.sfs.crfosp.cis.StupenZverejCis
import com.sfs.crfosp.cis.TypOsobyCis
import com.sfs.crfosp.cis.UzemnyCelokCis
import com.sfs.crfosp.domain.MenoOsoby
import com.sfs.crfosp.domain.ObmPravnejSpos
import com.sfs.crfosp.domain.Osoba
import com.sfs.crfosp.domain.Priezvisko
import com.sfs.crfosp.domain.RodnePriezvisko
import com.sfs.crfosp.domain.Titul
import com.sfs.crfosp.domain.ZrusVyhlMrtvy

@XmlAccessorType(XmlAccessType.NONE)
class AvizoOsoba implements Serializable{
	Long id
	
	Osoba osoba
	
	Avizo avizo
	
	//@XmlAttribute(required=true,name="ID")
	Long get_osobaid() { osoba.id }
	
	// @XmlElement(name="ID",required=true)
	@XmlElement(name="IFO",required=true)
	String ifo
	// @XmlElement(name="IP")
	@XmlElement(name="IFO_TOT")
	String ifo_tot
	
	String rc
	// @XmlElement(name="RC")
	@XmlElement(name="RC")
	String rcA
	AvizoAtributy atrRc
	
	Date dtnar
	// @XmlElement(name="DN")
	@XmlElement(name="DT_NARODENIA")
	@XmlSchemaType(name = "date")
	Date dtnarA
	AvizoAtributy atrDtnar
	
	UzemnyCelokCis ucnar
	// @XmlElement(name="UE")
	@XmlElement(name="UC_NARODENIA")
	Long ucnarA	
	AvizoAtributy atrUcnar
	
	String mnar
	// @XmlElement(name="MN")
	@XmlElement(name="MIESTO_NARODENIA")
	String mnarA
	AvizoAtributy atrMnar
	
	StatCis stnar
	// @XmlElement(name="SN")
	@XmlElement(name="STAT_NARODENIA")
	Long stnarA
	AvizoAtributy atrStnar
	
	PohlavieCis pohl
	// @XmlElement(name="PI")
	@XmlElement(name="POHLAVIE")
	Long pohlA
	AvizoAtributy atrPohl
	
	RodinnyStavCis rodst
	// @XmlElement(name="RS")
	@XmlElement(name="ROD_STAV")
	Long rodstA
	AvizoAtributy atrRodst
	
	String rodmatr
	// @XmlElement(name="PE")
	@XmlElement(name="ROD_MATRIKA")
	String rodmatrA
	AvizoAtributy atrRodmatr
	
	NarodnostCis narodnost
	// @XmlElement(name="NI")
	@XmlElement(name="NARODNOST")
	Long narodnostA
	AvizoAtributy atrNarodnost
	
	Date dtumr
	// @XmlElement(name="DU")
	@XmlElement(name="DT_UMRTIA")
	@XmlSchemaType(name = "date")
	Date dtumrA
	AvizoAtributy atrDtumr
	
	UzemnyCelokCis ucumr
	// @XmlElement(name="UC")
	@XmlElement(name="UC_UMRTIA")
	Long ucumrA
	AvizoAtributy atrUcumr
	
	String mumr
	// @XmlElement(name="MU")
	@XmlElement(name="MIESTO_UMRTIA")
	String mumrA
	AvizoAtributy atrMumr
	
	String umrmatr
	// @XmlElement(name="PM")
	@XmlElement(name="MATRIKA_UMRTIA")
	String umrmatrA
	AvizoAtributy atrUmrmatr
	
	StatCis stumr
	// @XmlElement(name="SU")
	@XmlElement(name="STAT_UMRTIA")
	Long stumrA	
	AvizoAtributy atrStumr
	
	String bifo
	// @XmlElement(name="BI")
	@XmlElement(name="BIFO")
	String bifoA
	AvizoAtributy atrBifo
	
	TypOsobyCis typoso
	// @XmlElement(name="TV")
	@XmlElement(name="TYP_OSOBY")
	Long typosoA
	AvizoAtributy atrTyposo
	
	UzemnyCelokCis okrnar
	// @XmlElement(name="UL")
	@XmlElement(name="OKRES_NARODENIA")
	Long okrnarA
	AvizoAtributy atrOkrnar
	
	UzemnyCelokCis okrumr
	// @XmlElement(name="UO")
	@XmlElement(name="OKRES_UMRTIA")
	Long okrumrA
	AvizoAtributy atrOkrumr
	
	String ic
	// 	@XmlElement(name="IC")
	@XmlElement(name="IDENIF_CUDZINCA")
	String icA
	AvizoAtributy atrIc
	
	StupenZverejCis stzverej	
	// @XmlElement(name="SZ")
	@XmlElement(name="STUPEN_ZVEREJ")
	Long stzverejA
	AvizoAtributy atrStzverej
	
	Integer roknar
	// @XmlElement(name="RN")
	@XmlElement(name="ROK_NARODENIA")
	Integer roknarA
	AvizoAtributy atrRoknar
	
	// @XmlElement(name="DT_UKONCENIA")
	// @XmlSchemaType(name = "date")
	Date dtukon
	// @XmlElement(name="DOVOD_NEPLATNOSTI")
	String dovnepl
	Long rodmatr_id
	Long rodmatrA_id
	Long umrmatr_id
	Long umrmatrA_id
	// @XmlElement(name="DT_CREATE",required=true)
	Date dtcre
	// @XmlElement(name="DT_UPDATE",required=true)
	Date dtupd
	// @XmlElement(name="CISLO_ZMENY",required=true)
	Integer ciszmeny
	// @XmlElement(name="DT_ZMENY",required=true)
	Date dtzmeny

	// @XmlElementWrapper(name="AMEList")
	// @XmlElement(name="AME")
	@XmlElementWrapper(name="MENO_LIST")
	@XmlElement(name="MENO")
	SortedSet<AvizoMeno> meno
	
	// @XmlElementWrapper(name="APRList")
	// @XmlElement(name="APR")
	@XmlElementWrapper(name="PRIEZ_LIST")
	@XmlElement(name="PRIEZVISKO")
	SortedSet<AvizoPriezvisko> priezvisko

	// @XmlElementWrapper(name="ARPList")
	// @XmlElement(name="ARP")
	@XmlElementWrapper(name="RODNEPRIEZ_LIST")
	@XmlElement(name="RODNE_PRIEZVISKO")
	SortedSet<AvizoRodnePriez> rodnepriezvisko

	// @XmlElementWrapper(name="ATSList")
	// @XmlElement(name="ATSL")
	@XmlElementWrapper(name="TITUL_LIST")
	@XmlElement(name="TITUL")
	Set<AvizoTitul> titul

	// @XmlElementWrapper(name="ASRList") 
	// @XmlElement(name="ASR")
	@XmlElementWrapper(name="STATNA_PRISL_LIST")
	@XmlElement(name="STATNA_PRISL")
	Set<AvizoStatnaPrislusnot> statnaPrislusnost

	// @XmlElementWrapper(name="APOList")
	// @XmlElement(name="APO")
	@XmlElementWrapper(name="POBYT_OSOBY_LIST")
	@XmlElement(name="POBYT_OSOBY")
	Set<AvizoPobyt> pobyt

	@XmlElementWrapper(name="OBMEDZ_PRAV_SPOS_LIST")
	@XmlElement(name="OBMEDZ_PRAV_SPOS")
	Set<AvizoObmPravnejSpos> obmPravnejSpos
	
	@XmlElementWrapper(name="RODINNY_VZTAH_LIST")
	@XmlElement(name="RODINNY_VZTAH")
	Set<AvizoRodinnyVztah> rodinnyVztah

	// @XmlElementWrapper(name="AUTList")
	// @XmlElement(name="AUT")
	@XmlElementWrapper(name="UDEL_STRATA_OBC_LIST")
	@XmlElement(name="UDEL_STRATA_OBC")
	Set<AvizoUdelStObcianstvo> udelStObcianstvo
	
	@XmlElementWrapper(name="ZRUS_VYHL_MRTVY_LIST")
	@XmlElement(name="ZRUS_VYHL_MRTVY")
	Set<AvizoZrusVyhlMrtvy> zrusVyhlMrtvy
	
	String menoCele
	String priezCele
	String rodPrCele
	//Transientne polia pre potreby reportu
	String titulPred
	String titulZa
	String popis

		//static transients = ['menoCele', 'priezCele', 'rodPrCele', 'titulPred', 'titulZa' , '_osobaid', 'popis']
	static transients = [ 'titulPred', 'titulZa' , '_osobaid', 'popis']
	
	static shortFields = ['ifo', 'rc', 'bifo', 'ic', 'menoCele', 'priezCele', 'popis']

	//static belongsTo=[avizo:Avizo]
	
	static hasMany = [meno:AvizoMeno,priezvisko:AvizoPriezvisko,rodnepriezvisko:AvizoRodnePriez,titul:AvizoTitul
		,statnaPrislusnost:AvizoStatnaPrislusnot,pobyt:AvizoPobyt, obmPravnejSpos:AvizoObmPravnejSpos,
		,rodinnyVztah:AvizoRodinnyVztah,udelStObcianstvo:AvizoUdelStObcianstvo,zrusVyhlMrtvy:AvizoZrusVyhlMrtvy]
	static mappedBy = [AvizoMeno:"osoba",AvizoPriezvisko:"osoba",AvizoRodnePriez:"osoba",AvizoTitul:"osoba"
		,AvizoStatnaPrislusnot:"osoba",AvizoPobyt:"osoba",AvizoObmPravnejSpos:"osoba",AvizoRodinnyVztah:"osoba"
		,AvizoUdelStObcianstvo:"osoba",AvizoZrusVyhlMrtvy:"osoba"]

	static mapping = {
		table 'AV_CROSOBA'
		//version false
		id generator:'sequence',params:[sequence:"AV_OSOBA_SEQ"],sqlType:'NUMBER',length:19
		menoCele column:'MENOCELE'
		priezCele column:'PRIEZCELE'
		rodPrCele column:'RODPRCELE'
		ifo sqlType:'char'
		rc sqlType:'char'
		ic sqlType:'char'
		//referencies
		ucnar fetch:'join'
		stnar fetch:'join'
		pohl fetch:'join'
		rodst fetch:'join'
		narodnost fetch:'join'
		ucumr fetch:'join'
		stumr fetch:'join'
		typoso fetch:'join'
		okrnar fetch:'join'
		okrumr fetch:'join'
		stzverej fetch:'join'
		// A
		rcA sqlType:'char'
		icA sqlType:'char'
		osoba column:'CROSOBA'
	}

	static constraints = {
		// id nullable:false
		ifo maxSize:32
		ifo_tot maxSize:100
		rc maxSize:10
		mnar maxSize:80
		rodmatr maxSize:100
		mumr maxSize:80
		umrmatr maxSize:100
		bifo maxSize:20
		ic maxSize:32
		dovnepl maxSize:100
		rcA maxSize:10
		mnarA maxSize:80
		rodmatrA maxSize:100
		mumrA maxSize:80
		umrmatrA maxSize:100
		icA maxSize:32
	}

	//getter pre transientne pole titulPred
	public String getTitulPred () {
		String retval="";
		for(AvizoTitul m : titul ) {
			if(m.typtit.id==1) retval += m.titul.nazov + " ";
		}
		return retval.trim();
	}

	//getter pre transientne pole titulZa
	public String getTitulZa () {
		String retval="";
		for(AvizoTitul m : titul ) {
			if(m.typtit.id>1) retval += m.titul.nazov + " ";
		}
		return retval.trim();
	}
	
	public String getPopis () {
		return this.avizo.popis;
	}

	public void setPopis () {
		this.avizo.popis = this.popis;
	}
	
//	def beforeInsert() {		
//		AvizVybavCis.findAllByDomain(this.class.getName())each {polozka -> 
//			this.class.fields.each {
//				if ( this[it.name] != null && it.name == polozka.pole) {
//					AvizoAtributy avizoAtributy = new AvizoAtributy()
//					avizoAtributy.avizo = Avizo.findByOsoba(this)
//					avizoAtributy.polozka = polozka
//					avizoAtributy.status = VybavenieStatusEnum.nepotvrdene
//					avizoAtributy.oldval = this[it.name[0..-1]]
//					avizoAtributy.newval = this[it.name]
//				}
//			}
//		}
//	}
	
	def setAvizoOsoba () {
	if (!this.osoba && this.ifo) this.osoba = Osoba.findByIfo(this.ifo.padRight(32, ' '))
		if (this.osoba) {
			this.rc	=	this.osoba.rc
			this.dtnar	=	this.osoba.dtnar
			this.ucnar	=	this.osoba.ucnar
			this.mnar	=	this.osoba.mnar
			this.stnar	=	this.osoba.stnar
			this.pohl	=	this.osoba.pohl
			this.rodst	=	this.osoba.rodst
			this.rodmatr	=	this.osoba.rodmatr
			this.narodnost	=	this.osoba.narodnost
			this.dtumr	=	this.osoba.dtumr
			this.ucumr	=	this.osoba.ucumr
			this.mumr	=	this.osoba.mumr
			this.umrmatr	=	this.osoba.umrmatr
			this.stumr	=	this.osoba.stumr
			this.bifo	=	this.osoba.bifo
			this.typoso	=	this.osoba.typoso
			this.okrnar	=	this.osoba.okrnar
			this.okrumr	=	this.osoba.okrumr
			this.ic	=	this.osoba.ic
			this.stzverej	=	this.osoba.stzverej
			this.roknar	=	this.osoba.roknar
			this.dtukon	=	this.osoba.dtukon
			this.dovnepl	=	this.osoba.dovnepl
			this.rodmatr_id	=	this.osoba.rodmatr_id
			this.umrmatr_id	=	this.osoba.umrmatr_id
			this.menoCele	=	this.osoba.getMenoCele()
			this.priezCele	=	this.osoba.getPriezCele()
			this.rodPrCele	=	this.osoba.getRodPrCele()
		}
	}

	def remAttr(){
		this.atrRc = null
		this.atrDtnar = null
		this.atrUcnar = null
		this.atrMnar = null
		this.atrStnar = null
		this.atrPohl = null
		this.atrRodst = null
		this.atrRodmatr = null
		this.atrNarodnost = null
		this.atrDtumr = null
		this.atrUcumr = null
		this.atrMumr = null
		this.atrUmrmatr = null
		this.atrStumr = null
		this.atrBifo = null
		this.atrTyposo = null
		this.atrOkrnar = null
		this.atrOkrumr = null
		this.atrIc = null
		this.atrStzverej = null
		this.atrRoknar = null
	}
}
