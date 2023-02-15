package com.sfs.crfosp.domain

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
import com.sfs.crfosp.cis.StupenZverejCis
import com.sfs.crfosp.cis.TypOsobyCis
import com.sfs.crfosp.cis.UzemnyCelokCis
import com.sfs.crfosp.ws.enums.OsobaStatusEnum

@XmlAccessorType(XmlAccessType.NONE)
class Osoba implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="IFO",required=true)
	String ifo
	@XmlElement(name="IFO_TOT")
	String ifo_tot
	@XmlElement(name="RC")
	String rc
	@XmlElement(name="DT_NARODENIA")
	@XmlSchemaType(name = "date")
	Date dtnar
	@XmlElement(name="UC_NARODENIA")
	UzemnyCelokCis ucnar
	@XmlElement(name="MIESTO_NARODENIA")
	String mnar
	@XmlElement(name="STAT_NARODENIA")
	StatCis stnar
	@XmlElement(name="POHLAVIE")
	PohlavieCis pohl
	@XmlElement(name="ROD_STAV")
	RodinnyStavCis rodst
	@XmlElement(name="ROD_MATRIKA")
	String rodmatr
	@XmlElement(name="NARODNOST")
	NarodnostCis narodnost
	@XmlElement(name="DT_UMRTIA")
	@XmlSchemaType(name = "date")
	Date dtumr
	@XmlElement(name="UC_UMRTIA")
	UzemnyCelokCis ucumr
	@XmlElement(name="MIESTO_UMRTIA")
	String mumr
	@XmlElement(name="MATRIKA_UMRTIA")
	String umrmatr
	@XmlElement(name="STAT_UMRTIA")
	StatCis stumr
	@XmlElement(name="BIFO")
	String bifo
	@XmlElement(name="TYP_OSOBY")
	TypOsobyCis typoso
	@XmlElement(name="OKRES_NARODENIA")
	UzemnyCelokCis okrnar
	@XmlElement(name="OKRES_UMRTIA")
	UzemnyCelokCis okrumr
	@XmlElement(name="IDENIF_CUDZINCA")
	String ic
	@XmlElement(name="STUPEN_ZVEREJ")
	StupenZverejCis stzverej
	@XmlElement(name="ROK_NARODENIA")
	Integer roknar
	@XmlElement(name="DT_UKONCENIA")
	@XmlSchemaType(name = "date")
	Date dtukon
	@XmlElement(name="DOVOD_NEPLATNOSTI")
	String dovnepl
	Long rodmatr_id
	Long umrmatr_id
	@XmlElement(name="DT_CREATE",required=true)
	Date dtcre
	@XmlElement(name="DT_UPDATE",required=true)
	Date dtupd
	@XmlElement(name="STATUS",required=true)
	OsobaStatusEnum status
	@XmlElement(name="CISLO_ZMENY",required=true)
	Integer ciszmeny
	@XmlElement(name="DT_ZMENY",required=true)
	Date dtzmeny
	Boolean cdata = Boolean.FALSE
	
	@XmlElementWrapper(name="MENO_LIST")
	@XmlElement(name="MENO")
	SortedSet<MenoOsoby> meno

	@XmlElementWrapper(name="PRIEZ_LIST")
	@XmlElement(name="PRIEZVISKO")
	SortedSet<Priezvisko> priezvisko

	@XmlElementWrapper(name="RODNEPRIEZ_LIST")
	@XmlElement(name="RODNE_PRIEZVISKO")
	SortedSet<RodnePriezvisko> rodnepriezvisko

	@XmlElementWrapper(name="TITUL_LIST")
	@XmlElement(name="TITUL")
	Set<Titul> titul

	@XmlElementWrapper(name="STATNA_PRISL_LIST")
	@XmlElement(name="STATNA_PRISL")
	Set<StatnaPrislusnot> statnaPrislusnost

	@XmlElementWrapper(name="POBYT_OSOBY_LIST")
	@XmlElement(name="POBYT_OSOBY")
	Set<Pobyt> pobyt

	@XmlElementWrapper(name="ZAKAZ_POBYTU_LIST")
	@XmlElement(name="ZAKAZ_POBYTU")
	Set<ZakazPobytu> zakazPobytu

	@XmlElementWrapper(name="OBMEDZ_PRAV_SPOS_LIST")
	@XmlElement(name="OBMEDZ_PRAV_SPOS")
	Set<ObmPravnejSpos> obmPravnejSpos

	@XmlElementWrapper(name="RODINNY_VZTAH_LIST")
	@XmlElement(name="RODINNY_VZTAH")
	Set<RodinnyVztah> rodinnyVztah
	
	@XmlElementWrapper(name="DOKLAD_LIST")
	@XmlElement(name="DOKLAD")
	Set<Doklad> doklad
	
	@XmlElementWrapper(name="UDEL_STRATA_OBC_LIST")
	@XmlElement(name="UDEL_STRATA_OBC")
	Set<UdelStObcianstvo> udelStObcianstvo
	
	@XmlElementWrapper(name="ZRUS_VYHL_MRTVY_LIST")
	@XmlElement(name="ZRUS_VYHL_MRTVY")
	Set<ZrusVyhlMrtvy> zrusVyhlMrtvy
	
	//Transientne polia pre potreby reportu
	String menoCele
	String priezCele
	String rodPrCele
	String titulPred
	String titulZa
	static transients = [	'menoCele', 'priezCele', 'rodPrCele', 'titulPred', 'titulZa' ]
	
	static shortFields = ['ifo','rc','bifo','ic','menoCele', 'priezCele']
	
	static hasMany = [meno:MenoOsoby,priezvisko:Priezvisko,rodnepriezvisko:RodnePriezvisko,titul:Titul
		,statnaPrislusnost:StatnaPrislusnot,pobyt:Pobyt,zakazPobytu:ZakazPobytu,obmPravnejSpos:ObmPravnejSpos
		,rodinnyVztah:RodinnyVztah,doklad:Doklad,udelStObcianstvo:UdelStObcianstvo,zrusVyhlMrtvy:ZrusVyhlMrtvy
		,skupUdajovModif:SkupUdajovModif
		,zaujmOsobaSys:ZaujmOsobaSys,osobaHistory:OsobaHistory]
	static mappedBy = [meno:"osoba",priezvisko:"osoba",rodnepriezvisko:"osoba",titul:"osoba"
		,statnaPrislusnost:"osoba",pobyt:"osoba",zakazPobytu:"osoba",obmPravnejSpos:"osoba",rodinnyVztah:"osoba"
		,doklad:"osoba",zrusVyhlMrtvy:"osoba"
		,skupUdajovModif:"osoba"
		,zaujmOsobaSys:"osoba",osobaHistory:"osoba"]

	static mapping = {
		table 'CROSOBA'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		ifo column:'IFO',sqlType:'char',length:32
		ifo_tot column:'IFO_TOT',sqlType:'varchar2',length:100
		rc column:'RC',sqlType:'char',length:10
		mnar column:'MNAR',sqlType:'varchar2',length:80
		rodmatr column:'RODMATR',sqlType:'varchar2',length:100
		mumr column:'MUMR',sqlType:'varchar2',length:80
		umrmatr column:'UMRMATR',sqlType:'varchar2',length:100
		bifo column:'BIFO',sqlType:'varchar2',length:20
		ic column:'IC',sqlType:'char',length:32
		dovnepl column:'DOVNEPL',sqlType:'varchar2',length:100
		status column:'STATUS',sqlType:'char',length:1
		//referencies
		ucnar column:'UCNAR',fetch:'join'
		stnar column:'STNAR',fetch:'join'
		pohl column:'POHL',fetch:'join'
		rodst column:'RODST',fetch:'join'
		narodnost column:'NARODNOST',fetch:'join'
		ucumr column:'UCUMR',fetch:'join'
		stumr column:'STUMR',fetch:'join'
		typoso column:'TYPOSO',fetch:'join'
		okrnar column:'OKRNAR',fetch:'join'
		okrumr column:'OKRUMR',fetch:'join'
		stzverej column:'STZVEREJ',fetch:'join'
		//many eager
//		meno lazy:false
//		priezvisko lazy:false
//		rodnepriezvisko lazy:false
//		titul lazy:false
//		statnaPrislusnost lazy:false
//		pobyt lazy:false
//		zakazPobytu lazy:false
//		obmPravnejSpos lazy:false
//		rodinnyVztah lazy:false
//		doklad lazy:false
//		udelStObcianstvo lazy:false
//		zrusVyhlMrtvy lazy:false	
	}
	static constraints = {
		id nullable:false
		ifo nullable:false, maxSize:32, unique: true
		ifo_tot nullable:true, maxSize:100
		rc nullable:true, maxSize:10
		dtnar nullable:true
		ucnar nullable:true
		mnar nullable:true, maxSize:80
		stnar nullable:true
		pohl nullable:true
		rodst nullable:true
		rodmatr nullable:true, maxSize:100
		narodnost nullable:true
		dtumr nullable:true
		ucumr nullable:true
		mumr nullable:true, maxSize:80
		umrmatr nullable:true, maxSize:100
		stumr nullable:true
		bifo nullable:true, maxSize:20
		typoso nullable:true
		okrnar nullable:true
		okrumr nullable:true
		ic nullable:true, maxSize:32
		stzverej nullable:true
		roknar nullable:true
		dtukon nullable:true
		dovnepl nullable:true, maxSize:100
		rodmatr_id nullable:true
		umrmatr_id nullable:true
		dtcre nullable:false
		dtupd nullable:false
		status nullable:false, maxSize:1
		ciszmeny nullable:true
		dtzmeny nullable:true
	}
	//getter pre transientne pole menoCele
	public String getMenoCele () {
		String retval="";
		for(MenoOsoby m : meno ) {
			retval += m.meno + " ";
		}
		return retval.trim();
	}
	//getter pre transientne pole priezCele
	public String getPriezCele () {
		String retval="";
		for(Priezvisko m : priezvisko ) {
			retval += m.priezvisko + " ";
		}
		return retval.trim();
	}
	//getter pre transientne pole rodPrCele
	public String getRodPrCele () {
		String retval="";
		for(RodnePriezvisko m : rodnepriezvisko ) {
			retval += m.priezvisko + " ";
		}
		return retval.trim();
	}
	
	//getter pre transientne pole titulPred
	public String getTitulPred () {
		String retval="";
		for(Titul m : titul ) {
			if(m.typtit.id==1) retval += m.titul.nazov + " ";
		}
		return retval.trim();
	}
	
	//getter pre transientne pole titulZa
	public String getTitulZa () {
		String retval="";
		for(Titul m : titul ) {
			if(m.typtit.id>1) retval += m.titul.nazov + " ";
		}
		return retval.trim();
	}
}
