package com.sfs.crfosp.aviza

import java.io.Serializable

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.TitulCis
import com.sfs.crfosp.cis.TypTitulCis
import com.sfs.crfosp.domain.Titul

@XmlAccessorType(XmlAccessType.NONE)
class AvizoTitul implements Serializable{
	Long id
	AvizoOsoba osoba

	//@XmlAttribute(required=true,name="ID")
	Titul titulOsoby

	AvizoAtributy atrTitul

	@XmlAttribute(required=true,name="ID")
	Long titulOsobyid

	TitulCis titul
	// @XmlElement(name="TI",required=true)
	@XmlElement(name="TITUL",required=true)
	Long titulA

	TypTitulCis typtit
	// @XmlElement(name="TT",required=true)
	@XmlElement(name="TYPTIT",required=true)
	Long typtitA

	//static belongsTo =[osoba:AvizoOsoba]
	//static transients = ['_titulOsobyid']

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRTITOS'
		//version false
		id generator:'sequence',params:[sequence:"AV_CRTITOS_SEQ"],sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
		titul lazy:false,fetch:'join'
		typtit lazy:false,fetch:'join'
		titulA lazy:false,fetch:'join'
		typtitA lazy:false,fetch:'join'
	}

	static constraints = {
		//id nullable:false
		//titulA nullable:false
		//typtitA nullable:false
	}

	def beforeInsert(){
		if (this.titulOsobyid) {
			this.setAvizoTitul ()
		}
	}
	def beforeUpdate(){
		this.setAvizoTitul ()
	}

	public String getTitulOld () {
		String retval="titul:"+this.titul.toString()+", typ titulu:"+this.typtit.toString();
		return retval.trim();
	}

	public String getTitulNew () {
		String retval="titul:"+this.titulA.toString()+", typ titulu:"+this.typtitA.toString();
		return retval.trim();
	}

	def setAvizoTitul () {
		if (!this.titulOsoby && this.titulOsobyid) this.titulOsoby = Titul.get(this.titulOsobyid)
		if (this.titulOsoby) {
			this.titulOsobyid = this.titulOsoby.id
			this.titul=this.titulOsoby.titul
			this.typtit=this.titulOsoby.typtit
		}
	}
	
	def remAttr() {
		this.atrTitul = null
	}
}