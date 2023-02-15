package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import com.sfs.crfosp.aviza.AvizoObmPravnejSpos
import com.sfs.crfosp.cis.TypObmSposCis

@XmlAccessorType(XmlAccessType.NONE)
class AvizoObmPravnejSposOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long obmprsposid
	
	@XmlElement(name="SN",required=true)	
	//@XmlElement(name="TYPOBM",required=true)
	Long typobmA
	
	@XmlElement(name="SNPSNNA",required=true)	
	String snpsnna
	
	@XmlElement(name="DZ")
	//@XmlElement(name="DTOD")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtodA

	@XmlElement(name="DK")
	//@XmlElement(name="DTDO")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtdoA
	
	@XmlElement(name="PO")
	//@XmlElement(name="POZN")
	Boolean poznA

	def dajAvizoObmPravnejSposOut(AvizoObmPravnejSpos avizoObmPravnejSpos) {
		def avizoObmPravnejSposOut = new AvizoObmPravnejSposOut()
		avizoObmPravnejSposOut.obmprsposid = avizoObmPravnejSpos.obmprsposid
		avizoObmPravnejSposOut.typobmA = avizoObmPravnejSpos.typobm?.id
		avizoObmPravnejSposOut.snpsnna = avizoObmPravnejSpos.typobm?.nazov
		avizoObmPravnejSposOut.dtodA = avizoObmPravnejSpos.dtod
		avizoObmPravnejSposOut.dtdoA = avizoObmPravnejSpos.dtdo
		avizoObmPravnejSposOut.poznA = avizoObmPravnejSpos.pozn
//		avizoObmPravnejSposOut.typobmA = avizoObmPravnejSpos.typobmA ? avizoObmPravnejSpos.typobm?.id : null		
//		avizoObmPravnejSposOut.snpsnna = avizoObmPravnejSpos.typobmA ? avizoObmPravnejSpos.typobm?.nazov : null		
//		avizoObmPravnejSposOut.dtodA = avizoObmPravnejSpos.dtodA ? avizoObmPravnejSpos.dtod : null
//		avizoObmPravnejSposOut.dtdoA = avizoObmPravnejSpos.dtdoA ? avizoObmPravnejSpos.dtdo : null
//		avizoObmPravnejSposOut.poznA = avizoObmPravnejSpos.poznA ? avizoObmPravnejSpos.pozn : null
		return avizoObmPravnejSposOut
	}
}
