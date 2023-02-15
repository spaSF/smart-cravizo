package com.sfs.crfosp.aviza

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.domain.Priezvisko


@XmlAccessorType(XmlAccessType.NONE)
class AvizoPriezvisko implements Serializable,Comparable{
	Long id

	AvizoOsoba osoba

	//@XmlAttribute(required=true,name="ID")
	Priezvisko priezviskosoby

	AvizoAtributy atrPriezvisko

	@XmlAttribute(required=true,name="ID")
	Long  priezviskosobyid

	String priezvisko
	// @XmlElement(name="PR")
	@XmlElement(name="PRIEZVISKO")
	String priezviskoA
	Integer priezvisko_idx
	// @XmlElement(name="PO",required=true)
	@XmlElement(name="PRIEZVISKO_IDX",required=true)
	Integer priezvisko_idxA

	@Override
	public int compareTo(obj) {
		if (this.getPriezvisko_idxA() ) {
			this.getPriezvisko_idxA().compareTo(obj.priezvisko_idxA)
		} else {
			this.getPriezvisko_idx().compareTo(obj.priezvisko_idx)
		}
	}

	//static belongsTo=[osoba:AvizoOsoba]

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRPRIEZ'
		//version false
		id generator:'sequence',params:[sequence:"AV_CRPRIEZ_SEQ"],sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
	}
	static constraints = {
		//id nullable:false
	}

	def beforeInsert(){
		if (this.priezviskosobyid) {
			this.setAvizoPriezvisko()
		}
	}
	def beforeUpdate(){
		this.setAvizoPriezvisko()
	}

	public String getPriezviskoOld () {
		String retval="index:"+this.priezvisko_idx.toString()+", priezvisko:"+this.priezvisko;
		return retval.trim();
	}

	public String getPriezviskoNew () {
		String retval="index:"+this.priezvisko_idxA.toString()+", priezvisko:"+this.priezviskoA;
		return retval.trim();
	}

	def setAvizoPriezvisko () {
		if (!this.priezviskosoby && this.priezviskosobyid) this.priezviskosoby = Priezvisko.get(this.priezviskosobyid)
		if (this.priezviskosoby) {
			this.priezviskosobyid = this.priezviskosoby.id
			this.priezvisko=this.priezviskosoby.priezvisko
			this.priezvisko_idx=this.priezviskosoby.priezvisko_idx
		}
	}
	
	def remAttr() {
		this.atrPriezvisko = null
	}
}
