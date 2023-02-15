package com.sfs.crfosp.aviza

import java.io.Serializable
import java.util.Date

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.domain.UdelStObcianstvo

@XmlAccessorType(XmlAccessType.NONE)
class AvizoUdelStObcianstvo implements Serializable{

	Long id
	AvizoOsoba osoba

	UdelStObcianstvo udelStOb

	AvizoAtributy atrStob

	@XmlAttribute(required=true,name="ID")
	Long udelstobid

	Date dtprev
	// @XmlElement(name="DP")
	@XmlElement(name="DT_PREVZATIA")
	@XmlSchemaType(name = "date")
	Date dtprevA

	Boolean fg_udst
	// @XmlElement(name="UD")
	@XmlElement(name="UDELENIE_STRATA")
	Boolean fg_udstA

	//static belongsTo =[osoba:AvizoOsoba]
	//static transients = ['_udelStObid']

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRUSSTOB'
		//version false
		id generator:'sequence',params:[sequence:"AV_CRUSSTOB_SEQ"],sqlType:'NUMBER',length:19
		fg_udst sqlType:'char'
		fg_udstA sqlType:'char'
		osoba column:'CROSOBA'
	}
	static constraints = {
		//id nullable:false
		fg_udstA maxSize:1
	}

	def beforeInsert(){
		if (this.udelstobid) {
			this.setAvizoUdelStObcianstvo ()
		}
	}

	def beforeUpdate(){
		this.setAvizoUdelStObcianstvo ()
	}

	public String getUdelStObcianstvoOld () {
		String retval="datum:"+this.dtprev.toString()+", strata:"+this.fg_udst.toString();
		return retval.trim();
	}

	public String getUdelStObcianstvoNew () {
		String retval="datum:"+this.dtprevA.toString()+", strata:"+this.fg_udstA.toString();
		return retval.trim();
	}

	def setAvizoUdelStObcianstvo () {
		if (!this.udelStOb && this.udelstobid) this.udelStOb = UdelStObcianstvo.get(this.udelstobid)
		if (this.udelStOb) {
			this.udelstobid=this.udelStOb.id
			this.dtprev=this.udelStOb.dtprev
			this.fg_udst=this.udelStOb.fg_udst
		}
	}
	
	def remAttr() {
		this.atrStob = null
	}
}
