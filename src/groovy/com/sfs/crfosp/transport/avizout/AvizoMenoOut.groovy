package com.sfs.crfosp.transport.avizout

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.aviza.AvizoMeno

@XmlAccessorType(XmlAccessType.NONE)
class AvizoMenoOut implements Serializable{

	@XmlAttribute(required=true,name="ID")
	Long menoosobyid	
	@XmlElement(name="PO",required=true)
	//@XmlElement(name="MENO_IDX",required=true)
	Integer meno_idxA
	@XmlElement(name="ME")
	//@XmlElement(name="MENO")
	String menoA

	def dajAvizoMenoOut(AvizoMeno avizoMeno) {
		if (avizoMeno.menoA) {
			def avizoMenoOut = new AvizoMenoOut()
			avizoMenoOut.menoosobyid = avizoMeno.menoosobyid
			avizoMenoOut.menoA = avizoMeno.meno
			avizoMenoOut.meno_idxA = avizoMeno.meno_idx
//			avizoMenoOut.menoA = avizoMeno.menoA ? avizoMeno.meno : null
//			avizoMenoOut.meno_idxA = avizoMeno.meno_idxA ? avizoMeno.meno_idx : null
			return avizoMenoOut
		}
	}
}