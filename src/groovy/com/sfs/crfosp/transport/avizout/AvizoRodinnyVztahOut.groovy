package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import com.sfs.crfosp.aviza.AvizoRodinnyVztah
import com.sfs.crfosp.cis.TypRoleVztCis
import com.sfs.crfosp.cis.TypVztahCis

@XmlAccessorType(XmlAccessType.NONE)
class AvizoRodinnyVztahOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long rodinnyVztahid

	//@XmlElement(name="IFO")
	@XmlElement(name="ID")
	String ifoA

	//@XmlElement(name="TYPVZT",required=true)
	@XmlElement(name="TR",required=true)
	Long typvztA

	@XmlElement(name="TRZTRNA",required=true)
	String typvzttext

	//@XmlElement(name="DTVZNIK")
	@XmlElement(name="DV")
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
	Date dtvznikA

	//@XmlElement(name="MIESTOVYD")
	@XmlElement(name="UC")
	Long miestovydA

	//@XmlElement(name="MIESTOVYDTXT")
	@XmlElement(name="MV")
	String miestovydtxtA

	//@XmlElement(name="SOBMATR")
	@XmlElement(name="PA")
	Long sobmatrA

	//@XmlElement(name="PTYPROLE")
	@XmlElement(name="TL",required=true)
	Long ptyproleA

	@XmlElement(name="TRRTLNA",required=true)
	String ptyproletext

	//@XmlElement(name="VTYPROLE")
	@XmlElement(name="TE",required=true)
	Long vtyproleA

	@XmlElement(name="TRRTENA",required=true)
	String vtyproletext

	def dajAvizoRodinnyVztahOut(AvizoRodinnyVztah avizoRodinnyVztah) {
		if (avizoRodinnyVztah.ifoA || avizoRodinnyVztah.dtvznikA || avizoRodinnyVztah.dtzanikA || avizoRodinnyVztah.fg_neplA || avizoRodinnyVztah.dvdneplA || avizoRodinnyVztah.miestovydtxtA || avizoRodinnyVztah.miestovydA || avizoRodinnyVztah.ptyproleA || avizoRodinnyVztah.vtyproleA || avizoRodinnyVztah.sobmatrtxtA || avizoRodinnyVztah.sobmatrA || avizoRodinnyVztah.oprodicA ) {
			def avizoRodinnyVztahOut = new AvizoRodinnyVztahOut()
			avizoRodinnyVztahOut.rodinnyVztahid = avizoRodinnyVztah.idmv ? Long.valueOf(avizoRodinnyVztah.idmv.longValue()) : avizoRodinnyVztah.rodinnyVztah.id
			avizoRodinnyVztahOut.ifoA = avizoRodinnyVztah.ifoA
			avizoRodinnyVztahOut.typvztA = avizoRodinnyVztah.typvzt?.id
			avizoRodinnyVztahOut.dtvznikA = avizoRodinnyVztah.dtvznik
			avizoRodinnyVztahOut.miestovydA = avizoRodinnyVztah.miestovyd
			avizoRodinnyVztahOut.miestovydtxtA = avizoRodinnyVztah.miestovydtxt
			avizoRodinnyVztahOut.sobmatrA = avizoRodinnyVztah.sobmatr
			avizoRodinnyVztahOut.ptyproleA = avizoRodinnyVztah.ptyprole?.id
			avizoRodinnyVztahOut.vtyproleA = avizoRodinnyVztah.vtyprole?.id
			avizoRodinnyVztahOut.typvzttext = avizoRodinnyVztah.typvzt?.nazov
			avizoRodinnyVztahOut.ptyproletext = avizoRodinnyVztah.ptyprole?.nazov
			avizoRodinnyVztahOut.vtyproletext = avizoRodinnyVztah.vtyprole?.nazov
			
//			avizoRodinnyVztahOut.rodinnyVztahid = avizoRodinnyVztah.idmv ? Long.valueOf(avizoRodinnyVztah.idmv.longValue()) : avizoRodinnyVztah.rodinnyVztah.id
//			avizoRodinnyVztahOut.ifoA = avizoRodinnyVztah.ifoA
//			avizoRodinnyVztahOut.typvztA = avizoRodinnyVztah.typvztA ?  avizoRodinnyVztah.typvzt.id : null
//			avizoRodinnyVztahOut.dtvznikA = avizoRodinnyVztah.dtvznikA
//			avizoRodinnyVztahOut.miestovydA = avizoRodinnyVztah.miestovydA
//			avizoRodinnyVztahOut.miestovydtxtA = avizoRodinnyVztah.miestovydtxtA
//			avizoRodinnyVztahOut.sobmatrA = avizoRodinnyVztah.sobmatrA
//			avizoRodinnyVztahOut.ptyproleA = avizoRodinnyVztah.ptyproleA ? avizoRodinnyVztah.ptyprole.id : null
//			avizoRodinnyVztahOut.vtyproleA = avizoRodinnyVztah.vtyproleA ? avizoRodinnyVztah.vtyprole.id : null
//			avizoRodinnyVztahOut.typvzttext = avizoRodinnyVztahOut.typvztA ? avizoRodinnyVztah.typvzt?.nazov : null
//			avizoRodinnyVztahOut.ptyproletext = avizoRodinnyVztahOut.ptyproleA ? avizoRodinnyVztah.ptyprole?.nazov : null
//			avizoRodinnyVztahOut.vtyproletext = avizoRodinnyVztahOut.vtyproleA ? avizoRodinnyVztah.vtyprole?.nazov : null
			return avizoRodinnyVztahOut
		}
	}

}
