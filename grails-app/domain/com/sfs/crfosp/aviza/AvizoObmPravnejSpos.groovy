package com.sfs.crfosp.aviza

import java.sql.Timestamp

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.TypObmSposCis
import com.sfs.crfosp.domain.ObmPravnejSpos

@XmlAccessorType(XmlAccessType.NONE)
class AvizoObmPravnejSpos implements Serializable{

	Long id
	AvizoOsoba osoba
	
	ObmPravnejSpos obmprspos
	
	AvizoAtributy atrObmStPr
	
	@XmlAttribute(required=true,name="ID")
	Long obmprsposid
	
	TypObmSposCis typobm
	@XmlElement(name="TYPOBM",required=true)
	Long typobmA
	
	Date dtod
	@XmlElement(name="DTOD")
	@XmlSchemaType(name = "date")
	Date dtodA
	
	Date dtdo
	@XmlElement(name="DTDO")
	@XmlSchemaType(name = "date")
	Date dtdoA
	
	String pozn
	@XmlElement(name="POZN")
	String poznA
	
	
	static mapping = {
		tablePerHierarchy true
		table 'AV_CRPRSPOS'
		id generator:'sequence',params:[sequence:"AV_CRPRSPOS_SEQ"],sqlType:'NUMBER',length:19
		typobm column:'TYPOBM',fetch:'join',lazy:false
		osoba column:'CROSOBA'
	}
	static constraints = {
		//id nullable:false
		//typobm nullable:false
		dtod nullable:true
		dtdo nullable:true
		pozn nullable:true, maxSize:2560
	}

	def beforeInsert(){
		if (this.obmprsposid) {
			this.setAvizoObmPravnejSpos ()
		}
	}

	def beforeUpdate(){
		this.setAvizoObmPravnejSpos ()
	}

	public String getObmPravnejSposOld () {
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

	public String getObmPravnejSposNew () {
		String retval="";
		this.gormPersistentEntity.persistentPropertyNames.each {
			if (it[-1..-1] == "A" && this[it] != null)  {
				def val = this[it].toString().trim()
				if (this[it] in Date || this[it] in Timestamp) val = this[it].getDateString()
				retval = retval.concat( this[it]?it.concat(":").concat(val).concat(","):"")			}
		}
		return retval?retval.substring(0,retval.length() - 1):retval
	}

	def setAvizoObmPravnejSpos () {
		if (!this.obmprspos && this.obmprsposid) this.obmprspos = ObmPravnejSpos.get(this.obmprsposid)
		if (this.obmprspos) {
			this.obmprsposid=this.obmprspos.id
			this.typobm=this.obmprspos.typobm
			this.dtod=this.obmprspos.dtod
			this.dtdo=this.obmprspos.dtdo
			this.pozn=this.obmprspos.pozn
		}
	}
	
	def remAttr() {
		this.atrObmStPr = null
	}
}
