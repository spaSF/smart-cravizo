package com.sfs.crfosp.aviza

import java.sql.Timestamp

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.TypRoleVztCis
import com.sfs.crfosp.cis.TypVztahCis
import com.sfs.crfosp.cis.UzemnyCelokCis
import com.sfs.crfosp.domain.Osoba
import com.sfs.crfosp.domain.RodinnyVztah

@XmlAccessorType(XmlAccessType.NONE)
class AvizoRodinnyVztah implements Serializable{
	Long id

	AvizoOsoba osoba

	RodinnyVztah rodinnyVztah
	@XmlAttribute(required=true,name="ID")
	Long rodinnyVztahid

	AvizoAtributy atrRodvz

	String ifo
	@XmlElement(name="IFO")
	String ifoA

	TypVztahCis typvzt
	@XmlElement(name="TYPVZT",required=true)
	Long typvztA

	Date dtvznik
	@XmlElement(name="DTVZNIK")
	@XmlSchemaType(name = "date")
	Date dtvznikA

	Date dtzanik
	@XmlElement(name="DTZANIK")
	@XmlSchemaType(name = "date")
	Date dtzanikA

	Boolean fg_nepl
	@XmlElement(name="FG_NEPL",required=true)
	Boolean fg_neplA

	String dvdnepl
	@XmlElement(name="DVDNEPL")
	String dvdneplA

	String miestovydtxt
	@XmlElement(name="MIESTOVYDTXT")
	String miestovydtxtA

	UzemnyCelokCis miestovyd
	@XmlElement(name="MIESTOVYD")
	Long miestovydA

	TypRoleVztCis ptyprole
	@XmlElement(name="PTYPROLE")
	Long ptyproleA

	TypRoleVztCis vtyprole
	@XmlElement(name="VTYPROLE")
	Long vtyproleA

	String sobmatrtxt
	@XmlElement(name="SOBMATRTXT")
	String sobmatrtxtA

	Long sobmatr
	@XmlElement(name="SOBMATR")
	Long sobmatrA

	String oprodic
	@XmlElement(name="OPRODIC")
	String oprodicA

	Integer idmv

	//	Osoba getOsobaVztahu(){
	//		Osoba.findByIfo(this.ifo)
	//	}
	//Transientne pole
	Osoba osobaVztahu
	static transients = ['osobaVztahu', '_rodinnyvztahid']

	//static belongsTo =[osoba:AvizoOsoba]

	static mapping = {
		tablePerHierarchy true
		table 'AV_CRRODVZ'
		//version false
		id generator:'sequence',params:[sequence:"AV_CRRODVZ_SEQ"],sqlType:'NUMBER',length:19
		ifo sqlType:'char'
		fg_nepl sqlType:'char'
		osoba column:'CROSOBA'
		typvzt fetch:'join'
		miestovyd fetch:'join'
		ptyprole fetch:'join'
		vtyprole fetch:'join'
		// A
		ifoA sqlType:'char'
		fg_neplA sqlType:'char'
		typvztA fetch:'join'
		miestovydA fetch:'join'
		ptyproleA fetch:'join'
		vtyproleA fetch:'join'
	}
	static constraints = {
		//id nullable:false
		//typvztA nullable:false
		//ptyproleA nullable:false
		//vtyproleA nullable:false

		ifo maxSize:32
		fg_nepl maxSize:1
		dvdnepl maxSize:100
		miestovydtxt maxSize:255
		sobmatrtxt maxSize:100
		oprodic maxSize:2560

		//ifoA maxSize:32
		//typvztA nullable:false
		fg_neplA maxSize:1 //,nullable:false
		dvdneplA maxSize:100
		miestovydtxtA maxSize:255
		sobmatrtxtA maxSize:100
		oprodicA maxSize:2560
	}

	//getter pre transientne pole osobaVztahu
	public Osoba getOsobaVztahu () {
		//println "ifo = " + this.ifo
		return Osoba.findByIfo(this.ifo);
	}
	def beforeInsert(){
		if (this.rodinnyVztahid) {
			this.setAvizoRodinnyVztah ()
		}
	}
	def beforeUpdate(){
		this.setAvizoRodinnyVztah ()
	}

	public String getRodinnyVztahOld () {
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

	public String getRodinnyVztahNew () {
		String retval="";
		this.gormPersistentEntity.persistentPropertyNames.each {
			if (it[-1..-1] == "A" && this[it] != null)  {
				def val = this[it].toString().trim()
				if (this[it] in Date || this[it] in Timestamp) val = this[it].getDateString()
				retval = retval.concat( this[it]?it.concat(":").concat(val).concat(","):"")			}
		}
		return retval?retval.substring(0,retval.length() - 1):retval
	}

	def setAvizoRodinnyVztah () {
		if (!this.rodinnyVztah && this.rodinnyVztahid) this.rodinnyVztah = RodinnyVztah.get(this.rodinnyVztahid)
		if (this.rodinnyVztah) {
			this.rodinnyVztahid = this.rodinnyVztah.id
			this.ifo=this.rodinnyVztah.ifo
			this.typvzt=this.rodinnyVztah.typvzt
			this.dtvznik=this.rodinnyVztah.dtvznik
			this.dtzanik=this.rodinnyVztah.dtzanik
			this.fg_nepl=this.rodinnyVztah.fg_nepl
			this.dvdnepl=this.rodinnyVztah.dvdnepl
			this.miestovydtxt=this.rodinnyVztah.miestovydtxt
			this.miestovyd=this.rodinnyVztah.miestovyd
			this.ptyprole=this.rodinnyVztah.ptyprole
			this.vtyprole=this.rodinnyVztah.vtyprole
			this.sobmatrtxt=this.rodinnyVztah.sobmatrtxt
			this.sobmatr=this.rodinnyVztah.sobmatr
			this.idmv=this.rodinnyVztah.idmv
		}
	}
	
	def remAttr() {
		this.atrRodvz = null
	}
}
