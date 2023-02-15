package com.sfs.crfosp.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.TypRoleVztCis
import com.sfs.crfosp.cis.TypVztahCis
import com.sfs.crfosp.cis.UzemnyCelokCis

@XmlAccessorType(XmlAccessType.NONE)
class RodinnyVztah implements Serializable{
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="IFO")
	String ifo
	@XmlElement(name="TYPVZT",required=true)
	TypVztahCis typvzt
	@XmlElement(name="DTVZNIK")
	@XmlSchemaType(name = "date")
	Date dtvznik
	@XmlElement(name="DTZANIK")
	@XmlSchemaType(name = "date")
	Date dtzanik
	@XmlElement(name="FG_NEPL",required=true)
	Boolean fg_nepl
	@XmlElement(name="DVDNEPL")
	String dvdnepl
	@XmlElement(name="MIESTOVYDTXT")
	String miestovydtxt
	@XmlElement(name="MIESTOVYD")
	UzemnyCelokCis miestovyd
	@XmlElement(name="PTYPROLE")
	TypRoleVztCis ptyprole
	@XmlElement(name="VTYPROLE")
	TypRoleVztCis vtyprole
	@XmlElement(name="SOBMATRTXT")
	String sobmatrtxt
	@XmlElement(name="SOBMATR")
	Long sobmatr
	@XmlElement(name="OPRODIC")
	String oprodic
	
	//identifikator objektu na mv
	Integer idmv
	
//	Osoba getOsobaVztahu(){
//		Osoba.findByIfo(this.ifo)
//	}
	//Transientne pole
	Osoba osobaVztahu
	static transients = ['osobaVztahu']
		
	static belongsTo =[osoba:Osoba]

	static mapping = {
		table 'CRRODVZ'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		ifo column:'IFO',sqlType:'char',length:32
		fg_nepl column:'FG_NEPL',sqlType:'char',length:1
		dvdnepl column:'DVDNEPL',sqlType:'varchar2',length:100
		sobmatrtxt column:'SOBMATRTXT',sqlType:'varchar2',length:100
		osoba column:'CROSOBA'
		typvzt column:'TYPVZT',fetch:'join'
		miestovyd column:'MIESTOVYD',fetch:'join'
		ptyprole column:'PTYPROLE', fetch:'join'
		vtyprole column:'VTYPROLE', fetch:'join'	
	}
	static constraints = {
		id nullable:false
		ifo nullable:true, maxSize:32
		typvzt nullable:false
		dtvznik nullable:true
		dtzanik nullable:true
		fg_nepl nullable:false, maxSize:1
		dvdnepl nullable:true, maxSize:100
		miestovydtxt nullable:true, maxSize:255
		miestovyd nullable:true
		ptyprole nullable:true
		vtyprole nullable:true
		sobmatrtxt nullable:true, maxSize:100
		sobmatr nullable:true
		oprodic nullable:true, maxSize:2560
	}

	//getter pre transientne pole osobaVztahu
	public Osoba getOsobaVztahu () {
		//println "ifo = " + this.ifo
		return Osoba.findByIfo(this.ifo);
	}

}
