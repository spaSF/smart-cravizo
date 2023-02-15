package com.sfs.crfosp.aviza

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.domain.Osoba
import com.sfs.crfosp.security.Uzivatel
import com.sfs.crfosp.ws.RequestAvizo
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.AvizoStatusEnum
import com.sfs.crfosp.ws.enums.PovodEnum
import com.sfs.crfosp.ws.enums.VybavenieStatusEnum

@XmlAccessorType(XmlAccessType.NONE)
@com.sfs.smartsfs.audit.AuditStamp
class Avizo {
	@XmlElement(name="POPIS",required=true)
	String popis
	AvizoStatusEnum status
	VybavenieStatusEnum lastStatus
	Boolean ws // = Boolean.FALSE
	String avizoid
	String corrId
	Date datumVybav
	SystemCis systemSP
	Osoba osoba
	@XmlElement(name="OSOBA",required=true)
	AvizoOsoba avosoba
	PovodEnum povod = PovodEnum.A
	RequestLog request
	
	static hasMany = [
		//requestLog:RequestLog,
		avizaRequest:RequestAvizo,
		avizoAtributy:AvizoAtributy,
		avizoLog:AvizoLog
	]

	static mappedBy = [
		//requestLog:"avizo",
		avizaRequest:"avizo",
		avizoAtributy:"avizo",
		avizoLog:"avizo"
	]

	static mapping = {
		table 'CRAVIZO'
		// version false
		id generator:'sequence',params:[sequence:"CRAVIZO_SEQ"]
		avosoba cascade: 'all-delete-orphan'
		avizoLog cascade:"all,delete-orphan", lazy:false
		popis column:'POPIS',sqlType:'varchar2',length:2000
		povod sqlType:'char'
	}

	static constraints = {
		lastStatus nullable:false
		status nullable:false
		povod nullable:false
		popis nullable:true, maxSize:2000
		// popis nullable:false
	}

	def springSecurityService
	def beforeInsert(){
		if (this.ws) {
			Uzivatel usr = Uzivatel.findByUsername(springSecurityService.principal.username)
			this.systemSP = usr.getSystem()
		}
	}

	def beforeUpdate(){
		if (this.ws) {
			Uzivatel usr = Uzivatel.findByUsername(springSecurityService.principal.username)
			this.systemSP = usr.getSystem()
		}
	}

}
