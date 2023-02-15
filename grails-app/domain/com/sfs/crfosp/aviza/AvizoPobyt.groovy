package com.sfs.crfosp.aviza

import java.sql.Timestamp

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.StatCis
import com.sfs.crfosp.cis.TypPobytuCis
import com.sfs.crfosp.domain.Pobyt

@XmlAccessorType(XmlAccessType.NONE)
class AvizoPobyt implements Serializable{
	Long id

	AvizoOsoba osoba

	Pobyt pobyt
	@XmlAttribute(required=true,name="ID")
	Long pobytid

	AvizoAtributy atrPobyt

	TypPobytuCis typpob
	// @XmlElement(name="TP",required=true)
	@XmlElement(name="TYPPOB",required=true)
	Long typpobA

	Date dtod
	// @XmlElement(name="DTOD")
	// @XmlSchemaType(name = "date")
	Date dtodA

	Date dtdo
	// @XmlElement(name="DTDO")
	// @XmlSchemaType(name = "date")
	Date dtdoA

	Date dtprihl
	// @XmlElement(name="DP")
	@XmlElement(name="DTPRIHL")
	@XmlSchemaType(name = "date")
	Date dtprihlA

	Date dtukon
	//@XmlElement(name="DU")
	@XmlElement(name="DTUKON")
	@XmlSchemaType(name = "date")
	Date dtukonA

	Long ucpobyt
	Long ucpobytA

	String obecnaz
	// @XmlElement(name="OBECNAZ")
	String obecnazA

	String ukoho
	//@XmlElement(name="UKOHO")
	String ukohoA

	Long supcis
	// @XmlElement(name="SC")
	@XmlElement(name="SUPCIS")
	Long supcisA

	String orientc
	@XmlElement(name="ORIENTC")
	// @XmlElement(name="OC")
	String orientcA

	String znakoc
	// @XmlElement(name="ZO")
	@XmlElement(name="ZNAKOC")
	String znakocA

	String castobce
	//@XmlElement(name="CASTOBCE")
	String castobceA

	String okres
	//@XmlElement(name="OKRES")
	String okresA

	String ulica
	//@XmlElement(name="ULICA")
	String ulicaA

	StatCis pobstat
	@XmlElement(name="POBSTAT")
	Long pobstatA

	String zmiesto
	// @XmlElement(name="MP")
	@XmlElement(name="ZMIESTO")
	String zmiestoA

	String zokres
	// @XmlElement(name="OP")
	@XmlElement(name="ZOKRES")
	String zokresA

	String zobec
	// @XmlElement(name="OO")
	@XmlElement(name="ZOBEC")
	String zobecA

	String zcastob
	// @XmlElement(name="CO")
	@XmlElement(name="ZCASTOB")
	String zcastobA

	String zulica
	// @XmlElement(name="UM")
	@XmlElement(name="ZULICA")
	String zulicaA

	String zorientcis
	// @XmlElement(name="OI")
	@XmlElement(name="ZORIENTCIS")
	String zorientcisA

	String zsupisc
	// @XmlElement(name="SS")
	@XmlElement(name="ZSUPISC")
	String zsupiscA

	String zcastbud
	// @XmlElement(name="CB")
	@XmlElement(name="ZCASTBUD")
	String zcastbudA

	Boolean fg_mimo
	//@XmlElement(name="FG_MIMO")
	Boolean fg_mimoA

	String pobcis
	// @XmlElement(name="PC")
	@XmlElement(name="POBCIS")
	String pobcisA

	String bytcis
	@XmlElement(name="BYTCIS")
	String bytcisA

	String regadrid
	@XmlElement(name="REGADRID")
	//@XmlElement(name="IA")
	String regadridA

	Long regvchod
	@XmlElement(name="REGVCHODID")
	//@XmlElement(name="VD")
	Long regvchodA

	// RegVchodDomuCis regvchod
	Long regdom
	@XmlElement(name="REGDOMID")
	//@XmlElement(name="DI")
	Long regdomA

	// RegDomCis regdom
	// @XmlElement(name="UI")
	Long regulica
	@XmlElement(name="REGULICAID")
	//@XmlElement(name="UI")
	Long regulicaA

	// RegUlicaCis regulica
	// @XmlElement(name="UE")
	Long regobcast
	@XmlElement(name="REGCASTOBID")
	//@XmlElement(name="UE")
	Long regobcastA

	// UzemnyCelokCis regobcast
	// @XmlElement(name="UC")
	Long regobec
	//@XmlElement(name="UC")
	@XmlElement(name="REGOBECID")
	Long regobecA

	// @XmlElement(name="UL")
	Long regokres
	@XmlElement(name="REGOKRESID")
	// @XmlElement(name="UL")
	Long regokresA
	// UzemnyCelokCis regokres

	//	SortedSet<PobytRegion> pobytRegion
	//	@XmlElement(name="POBYT_REGION")
	//	SortedSet<PobytRegion> pobytRegionA

	Integer idmv

	// Transient
	String _ulica
	String _orientc
	String _supisc
	String _castobce
	String _obec
	String _okres
	static transients = ['_ulica', '_orientc', '_supisc', '_castobce', '_obec', '_okres', '_pobytid']

	//static belongsTo =[osoba:AvizoOsoba]

	//static hasMany = [pobytRegion:PobytRegion]
	//static mappedBy =[pobytRegion:"crpobyt"]

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRPOBYT'
		//version false
		id generator:'sequence',params:[sequence:"AV_CRPOBYT_SEQ"],sqlType:'NUMBER',length:19
		fg_mimo sqlType:'char'
		typpob fetch:'join',lazy:false
		pobstat fetch:'join'
		//		pobytRegion lazy:false
		osoba column:'CROSOBA'
		// A
		fg_mimoA sqlType:'char'
		typpobA fetch:'join',lazy:false
		pobstatA fetch:'join'
		//		pobytRegionA lazy:false
	}
	static constraints = {
		// typpobA nullable:false
		obecnaz maxSize:100
		ukoho maxSize:80
		znakoc maxSize:1
		castobce maxSize:100
		okres maxSize:100
		ulica maxSize:100
		zmiesto maxSize:120
		zokres maxSize:120
		zobec maxSize:50
		zcastob maxSize:250
		zulica maxSize:250
		zorientcis maxSize:20
		zsupisc maxSize:20
		zcastbud maxSize:250
		fg_mimo maxSize:1
		pobcis maxSize:12
		bytcis maxSize:10
		regadrid maxSize:100
		orientc maxSize:100

		obecnazA maxSize:100
		ukohoA maxSize:80
		znakocA maxSize:1
		castobceA maxSize:100
		okresA maxSize:100
		ulicaA maxSize:100
		zmiestoA maxSize:120
		zokresA maxSize:120
		zobecA maxSize:50
		zcastobA maxSize:250
		zulicaA maxSize:250
		zorientcisA maxSize:20
		zsupiscA maxSize:20
		zcastbudA maxSize:250
		fg_mimoA maxSize:1
		pobcisA maxSize:12
		bytcisA maxSize:10
		regadridA maxSize:100
		orientcA maxSize:100
	}

	//getter pre transientne polia
	public String get_ulica () {
		if (this.ulica) return this.ulica;
		if (this.zulica) return this.zulica;
		return;
	}

	public String get_obec () {
		if (this.obecnaz) return this.obecnaz;
		if (this.zobec) return this.zobec;
		if (this.zmiesto) return this.zmiesto;
		return;
	}

	public String get_orientc () {
		if (this.orientc) return this.orientc;
		if (this.zorientcis) return this.zorientcis;
		return;
	}

	public String get_supisc () {
		if (this.supcis) return this.supcis;
		if (this.zsupisc) return this.zsupisc;
		return;
	}

	public String get_castobce () {
		if (this.castobce) return this.castobce;
		if (this.zcastob) return this.zcastob;
		return;
	}

	public String get_okres () {
		if (this.okres) return this.okres;
		if (this.zokres) return this.zokres;
		return;
	}

	def beforeInsert(){
		if (this.pobytid) {
			this.setAvizoPobyt()
		}
		//		Pobyt.gormPersistentEntity.persistentPropertyNames.each{
		//			AvizoPobyt.gormPersistentEntity.persistentPropertyNames[it].each{
		//				this[it] =
		//			}
		//		}
	}

	def beforeUpdate(){
		this.setAvizoPobyt()
	}

	public String getPobytOld () {
		String retval="";
		def thisFields = this.gormPersistentEntity.persistentPropertyNames
		thisFields.each {fnm ->
			def efld = thisFields.find{ item -> item.startsWith(fnm+"A") }
			if (fnm[-1..-1] != "A" && this[fnm] != null && efld == fnm+"A" && this[fnm+"A"] != null )  {
				def val =  this[fnm].toString().trim()
				if (val.length() > 13) {
					if (val[0..14] == "com.sfs.crfosp.") val = this[fnm].id.toString()
				}
				if (this[fnm] in Date || this[fnm] in Timestamp) val = this[fnm].getDateString()
				retval = retval.concat(val?fnm.concat(":").concat(val.toString().trim()).concat(","):"")
			}
		}
		return retval?retval.substring(0,retval.length() - 1):retval
	}

	public String getPobytNew () {
		String retval="";
		this.gormPersistentEntity.persistentPropertyNames.each {
			if (it[-1..-1] == "A" && this[it] != null)  {
				def val = this[it].toString().trim()
				if (this[it] in Date || this[it] in Timestamp) val = this[it].getDateString()
				retval = retval.concat( this[it]?it.concat(":").concat(val).concat(","):"")
			}
		}
		return retval?retval.substring(0,retval.length() - 1):retval
	}

	def setAvizoPobyt () {
		if (!this.pobyt && this.pobytid)  this.pobyt = Pobyt.get(this.pobytid)
		if (this.pobyt) {
			this.pobytid = this.pobyt.id
			this.typpob=this.pobyt.typpob
			this.dtod=this.pobyt.dtod
			this.dtdo=this.pobyt.dtdo
			this.dtprihl=this.pobyt.dtprihl
			this.dtukon=this.pobyt.dtukon
			this.ucpobyt=this.pobyt.ucpobyt
			this.obecnaz=this.pobyt.obecnaz
			this.ukoho=this.pobyt.ukoho
			this.supcis=this.pobyt.supcis
			this.znakoc=this.pobyt.znakoc
			this.castobce=this.pobyt.castobce
			this.okres=this.pobyt.okres
			this.ulica=this.pobyt.ulica
			this.pobstat=this.pobyt.pobstat
			this.zmiesto=this.pobyt.zmiesto
			this.zokres=this.pobyt.zokres
			this.zobec=this.pobyt.zobec
			this.zcastob=this.pobyt.zcastob
			this.zulica=this.pobyt.zulica
			this.zorientcis=this.pobyt.zorientcis
			this.zsupisc=this.pobyt.zsupisc
			this.zcastbud=this.pobyt.zcastbud
			this.fg_mimo=this.pobyt.fg_mimo
			this.pobcis=this.pobyt.pobcis
			this.bytcis=this.pobyt.bytcis
			this.regadrid=this.pobyt.regadrid
			this.regvchod=this.pobyt.regvchod
			this.regdom=this.pobyt.regdom
			this.regulica=this.pobyt.regulica
			this.regobcast=this.pobyt.regobcast
			this.regobec=this.pobyt.regobec
			this.orientc=this.pobyt.orientc
			this.regokres=this.pobyt.regokres
			this.idmv=this.pobyt.idmv
		}
	}
	
	def remAttr() {
		this.atrPobyt = null
	}
}
