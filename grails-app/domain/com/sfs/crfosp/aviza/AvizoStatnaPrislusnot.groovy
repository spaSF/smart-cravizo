package com.sfs.crfosp.aviza

import java.io.Serializable

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.StatCis
import com.sfs.crfosp.domain.StatnaPrislusnot

@XmlAccessorType(XmlAccessType.NONE)
class AvizoStatnaPrislusnot implements Serializable{

	Long id
	AvizoOsoba osoba

	//@XmlAttribute(required=true,name="ID")
	StatnaPrislusnot statnaPrislusnot

	@XmlAttribute(required=true,name="ID")
	Long statnaPrislusnotid

	AvizoAtributy atrStprisl

	StatCis stpris
	// @XmlElement(name="SI",required=true)
	@XmlElement(name="STPRIS",required=true)
	Long stprisA

	String stprisNazov
	String stprisNazovA

	Boolean fg_sr
	
	//static belongsTo =[osoba:AvizoOsoba]
	//static transients = ['_statnaPrislusnotid']

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRSTPR'
		//version false
		id generator:'sequence',params:[sequence:"AV_CRSTPR_SEQ"],sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
		stpris fetch:'join'
		stprisA fetch:'join'
	}
	static constraints = {
		//id nullable:false
		//stprisA nullable:false
	}

	def beforeInsert(){
		if (this.statnaPrislusnotid) {
			this.setAvizoStatnaPrislusnot ()
		}
	}
	def beforeUpdate(){
		this.setAvizoStatnaPrislusnot ()
	}

	public String getStatnaPrislusnotOld () {
		String retval="stpris:"+this.stpris.toString()
		return retval.trim();
	}

	public String getStatnaPrislusnotNew () {
		String retval="stpris:"+this.stprisA.toString()
		return retval.trim();
	}

	def setAvizoStatnaPrislusnot () {
		if (!this.statnaPrislusnot && this.statnaPrislusnotid) this.statnaPrislusnot = StatnaPrislusnot.get(this.statnaPrislusnotid)
		if (this.statnaPrislusnot) {
			this.statnaPrislusnotid=this.statnaPrislusnot.id
			this.stpris=this.statnaPrislusnot.stpris
			this.stprisNazov=this.statnaPrislusnot.stprisNazov
		}
	}
	
	def remAttr() {
		this.atrStprisl = null
	}
}
