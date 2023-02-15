package com.sfs.crfosp.aviza

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.domain.MenoOsoby

@XmlAccessorType(XmlAccessType.NONE)
class AvizoMeno implements Serializable,Comparable{
	Long id
	MenoOsoby menoosoby

	@XmlAttribute(required=true,name="ID")
	Long menoosobyid

	AvizoOsoba osoba

	String meno
	// @XmlElement(name="ME")
	@XmlElement(name="MENO")
	String menoA

	Integer meno_idx
	// @XmlElement(name="PO",required=true)
	@XmlElement(name="MENO_IDX",required=true)
	Integer meno_idxA

	AvizoAtributy atrMeno

	@Override
	public int compareTo(obj) {
		if (this.getMeno_idxA()) {
			this.getMeno_idxA().compareTo(obj.meno_idxA)
		} else {
			this.getMeno_idx().compareTo(obj.meno_idx)
		}
	}

	// static belongsTo =[osoba:AvizoOsoba]
	//static transients = ['_menoid']

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRMENO'
		//version false
		id generator: 'sequence', params: [sequence: "AV_CRMENO_SEQ"], sqlType: 'NUMBER', length: 19
		osoba column: 'CROSOBA'
	}

	static constraints = {
		// id nullable:false
		meno maxSize:250
		//meno_idxA nullable:false
		//meno_idx nullable:false
		menoA maxSize:250
	}

	def beforeInsert() {
		if (this.menoosobyid) {
			this.setAvizoMeno()
		}
	}

	def beforeUpdate() {
		this.setAvizoMeno()
	}

	public String getMenoOld () {
		String retval="index:"+this.meno_idx.toString()+", meno:"+this.meno;
		return retval.trim();
	}

	public String getMenoNew () {
		String retval="index:"+this.meno_idxA.toString()+", meno:"+this.menoA;
		return retval.trim();
	}

	def setAvizoMeno () {
		if (!this.menoosoby && this.menoosobyid) this.menoosoby=MenoOsoby.get(this.menoosobyid)
		if (this.menoosoby) {
			this.menoosobyid = this.menoosoby.id
			this.meno=this.menoosoby.meno
			this.meno_idx=this.menoosoby.meno_idx
		}
	}

	def remAttr() {
		this.atrMeno = null
	}

}