package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.StatCis
import com.sfs.crfosp.cis.TypPobytuCis

@XmlAccessorType(XmlAccessType.NONE)
class Pobyt implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="TYPPOB",required=true)
	TypPobytuCis typpob
	@XmlElement(name="DTOD")
	@XmlSchemaType(name = "date")
	Date dtod
	@XmlElement(name="DTDO")
	@XmlSchemaType(name = "date")
	Date dtdo
	@XmlElement(name="DTPRIHL")
	@XmlSchemaType(name = "date")
	Date dtprihl
	@XmlElement(name="DTUKON")
	@XmlSchemaType(name = "date")
	Date dtukon
	Long ucpobyt
	@XmlElement(name="OBECNAZ")
	String obecnaz
	@XmlElement(name="UKOHO")
	String ukoho
	@XmlElement(name="SUPCIS")
	Long supcis
	@XmlElement(name="ZNAKOC")
	String znakoc
	@XmlElement(name="CASTOBCE")
	String castobce
	@XmlElement(name="OKRES")
	String okres
	@XmlElement(name="ULICA")
	String ulica
	@XmlElement(name="POBSTAT")
	StatCis pobstat
	@XmlElement(name="ZMIESTO")
	String zmiesto
	@XmlElement(name="ZOKRES")
	String zokres
	@XmlElement(name="ZOBEC")
	String zobec
	@XmlElement(name="ZCASTOB")
	String zcastob
	@XmlElement(name="ZULICA")
	String zulica
	@XmlElement(name="ZORIENTCIS")
	String zorientcis
	@XmlElement(name="ZSUPISC")
	String zsupisc
	@XmlElement(name="ZCASTBUD")
	String zcastbud
	@XmlElement(name="FG_MIMO")
	Boolean fg_mimo
	@XmlElement(name="POBCIS")
	String pobcis
	@XmlElement(name="BYTCIS")
	String bytcis
	String regadrid
	Long regvchod
	// RegVchodDomuCis regvchod
	Long regdom
	// RegDomCis regdom
	Long regulica
	// RegUlicaCis regulica
	Long regobcast
	// UzemnyCelokCis regobcast
	Long regobec
	// UzemnyCelokCis regobec
	@XmlElement(name="ORIENTC")
	String orientc
	Long regokres
	// UzemnyCelokCis regokres
	
	//identifikator objektu na mv
	Integer idmv
	
	@XmlElement(name="POBYT_REGION")
	SortedSet<PobytRegion> pobytRegion
	
	// Transient
	String _ulica
	String _orientc
	String _supisc
	String _castobce
	String _obec
	String _okres
	static transients = [	'_ulica', '_orientc', '_supisc', '_castobce', '_obec', '_okres'  ]
	
	static belongsTo =[osoba:Osoba]
	
	static hasMany = [pobytRegion:PobytRegion]
	static mappedBy =[pobytRegion:"crpobyt"]
	
	static mapping = {
		table 'CRPOBYT'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		obecnaz column:'OBECNAZ',sqlType:'varchar2',length:100
		ukoho column:'UKOHO',sqlType:'varchar2',length:80
		znakoc column:'ZNAKOC',sqlType:'varchar2',length:1
		castobce column:'CASTOBCE',sqlType:'varchar2',length:100
		okres column:'OKRES',sqlType:'varchar2',length:100
		ulica column:'ULICA',sqlType:'varchar2',length:100
		zmiesto column:'ZMIESTO',sqlType:'varchar2',length:120
		zokres column:'ZOKRES',sqlType:'varchar2',length:120
		zobec column:'ZOBEC',sqlType:'varchar2',length:50
		zcastob column:'ZCASTOB',sqlType:'varchar2',length:250
		zulica column:'ZULICA',sqlType:'varchar2',length:250
		zorientcis column:'ZORIENTCIS',sqlType:'varchar2',length:20
		zsupisc column:'ZSUPISC',sqlType:'varchar2',length:20
		zcastbud column:'ZCASTBUD',sqlType:'varchar2',length:250
		fg_mimo column:'FG_MIMO',sqlType:'char',length:1
		pobcis column:'POBCIS',sqlType:'varchar2',length:12
		bytcis column:'BYTCIS',sqlType:'varchar2',length:10
		regadrid column:'REGADRID',sqlType:'varchar2',length:100
		orientc column:'ORIENTC',sqlType:'varchar2',length:100
		typpob column:'TYPPOB',fetch:'join',lazy:false
		pobstat column:'POBSTAT',fetch:'join'
		pobytRegion lazy:false
		osoba column:'CROSOBA'
//		regvchod column:'REGVCHOD',fetch:'join'
//		regdom column:'REGDOM',fetch:'join'
//		regulica column:'REGULICA',fetch:'join'
//		regobcast column:'REGOBCAST',fetch:'join'
//		regobec column:'REGOBEC',fetch:'join'
//		regokres column:'REGOKRES',fetch:'join'
	}
	static constraints = {
		typpob nullable:false
		dtod nullable:true
		dtdo nullable:true
		dtprihl nullable:true
		dtukon nullable:true
		ucpobyt nullable:true
		obecnaz nullable:true, maxSize:100
		ukoho nullable:true, maxSize:80
		supcis nullable:true
		znakoc nullable:true, maxSize:1
		castobce nullable:true, maxSize:100
		okres nullable:true, maxSize:100
		ulica nullable:true, maxSize:100
		pobstat nullable:true
		zmiesto nullable:true, maxSize:120
		zokres nullable:true, maxSize:120
		zobec nullable:true, maxSize:50
		zcastob nullable:true, maxSize:250
		zulica nullable:true, maxSize:250
		zorientcis nullable:true, maxSize:20
		zsupisc nullable:true, maxSize:20
		zcastbud nullable:true, maxSize:250
		fg_mimo nullable:true, maxSize:1
		pobcis nullable:true, maxSize:12
		bytcis nullable:true, maxSize:10
		regadrid nullable:true, maxSize:100
		regvchod nullable:true
		regdom nullable:true
		regulica nullable:true
		regobcast nullable:true
		regobec nullable:true
		orientc nullable:true, maxSize:100
		regokres nullable:true
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
	
}
