package com.sfs.crfosp.aviza

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.domain.RodnePriezvisko

@XmlAccessorType(XmlAccessType.NONE)
class AvizoRodnePriez implements Serializable,Comparable{
	Long id
	AvizoOsoba osoba

	//@XmlAttribute(required=true,name="ID")
	RodnePriezvisko rodnePriezvisko

	@XmlAttribute(required=true,name="ID")
	Long rodnePriezviskoid

	AvizoAtributy atrRodpr

	String priezvisko
	// @XmlElement(name="PR")
	@XmlElement(name="PRIEZVISKO")
	String priezviskoA
	Integer priezvisko_idx
	// 	@XmlElement(name="PO",required=true)
	@XmlElement(name="PRIEZVISKO_IDX",required=true)
	Integer priezvisko_idxA

	@Override
	public int compareTo(obj) {
		if (this.getPriezvisko_idxA()) {
			this.getPriezvisko_idxA().compareTo(obj.priezvisko_idxA)
		} else {
			this.getPriezvisko_idx().compareTo(obj.priezvisko_idx)
		}
	}

	//static belongsTo =[osoba:AvizoOsoba]
	//static transients = ['_rodnepriezviskoid']

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRPRROD'
		//version false
		id generator:'sequence',params:[sequence:"AV_CRPRROD_SEQ"],sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
	}
	static constraints = {
		//id nullable:false
		priezvisko maxSize:250
		//priezvisko_idxA nullable:false
		priezviskoA maxSize:250
	}

	def beforeInsert(){
		if (this.rodnePriezviskoid) {
			this.setAvizoRodnePriez ()
		}
	}
	def beforeUpdate(){
		this.setAvizoRodnePriez ()
	}

	public String getRodnePriezOld () {
		String retval="index:"+this.priezvisko_idx.toString()+", priezvisko:"+this.priezvisko;
		return retval.trim();
	}

	public String getRodnePriezNew () {
		String retval="index:"+this.priezvisko_idxA.toString()+", priezvisko:"+this.priezviskoA;
		return retval.trim();
	}

	def setAvizoRodnePriez () {
		if (!this.rodnePriezvisko && this.rodnePriezviskoid) this.rodnePriezvisko = RodnePriezvisko.get(this.rodnePriezviskoid)
		if (this.rodnePriezvisko) {
			this.rodnePriezviskoid = this.rodnePriezvisko.id
			this.priezvisko=this.rodnePriezvisko.priezvisko
			this.priezvisko_idx=this.rodnePriezvisko.priezvisko_idx
		}
	}
	
	def remAttr() {
		this.atrRodpr = null
	}

}
