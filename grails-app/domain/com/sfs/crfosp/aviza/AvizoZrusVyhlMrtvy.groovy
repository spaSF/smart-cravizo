package com.sfs.crfosp.aviza

import java.sql.Timestamp

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.domain.ZrusVyhlMrtvy

@XmlAccessorType(XmlAccessType.NONE)
class AvizoZrusVyhlMrtvy implements Serializable{
	
	Long id
	AvizoOsoba osoba
	ZrusVyhlMrtvy zrusVyhlMrtvy
	AvizoAtributy atrVyhlMrtvy
	
	@XmlAttribute(required=true,name="ID")
	Long zrusVyhlMrtvyid
	
	Date dtprav
	@XmlElement(name="DTPRAV")
	@XmlSchemaType(name = "date")
	Date dtpravA
	
	Boolean zrusnezrus
	@XmlElement(name="MRTVY")
	Boolean zrusnezrusA
		
	static mapping = {
		tablePerHierarchy true
		table 'AV_CRZRMR'
		id generator:'sequence',params:[sequence:"AV_CRZRMR_SEQ"],sqlType:'NUMBER',length:19
		osoba column:'CROSOBA'
	}
	static constraints = {
		//id nullable:false
		//dtprav nullable:true
	}
	
	def beforeInsert(){
		if (this.zrusVyhlMrtvyid) {
			this.setAvizoZrusVyhlMrtvy ()
		}
	}

	def beforeUpdate(){
		this.setAvizoZrusVyhlMrtvy ()
	}

	public String getZrusVyhlMrtvyOld () {
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

	public String getZrusVyhlMrtvyNew () {
		String retval="";
		this.gormPersistentEntity.persistentPropertyNames.each {
			if (it[-1..-1] == "A" && this[it] != null)  {
				def val = this[it].toString().trim()
				if (this[it] in Date || this[it] in Timestamp) val = this[it].getDateString()
				retval = retval.concat( this[it]?it.concat(":").concat(val).concat(","):"")			}
		}
		return retval?retval.substring(0,retval.length() - 1):retval
	}

	def setAvizoZrusVyhlMrtvy () {
		if (!this.zrusVyhlMrtvy && this.zrusVyhlMrtvyid) this.zrusVyhlMrtvy = ZrusVyhlMrtvy.get(this.zrusVyhlMrtvyid)
		if (this.zrusVyhlMrtvy) {
			this.zrusVyhlMrtvyid=this.zrusVyhlMrtvy.id
			this.dtprav=this.zrusVyhlMrtvy.dtprav
		}
	}
	
	def remAttr() {
		this.atrVyhlMrtvy = null
	}
}
