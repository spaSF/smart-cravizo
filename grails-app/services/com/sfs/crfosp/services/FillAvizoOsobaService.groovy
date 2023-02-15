package com.sfs.crfosp.services

import grails.transaction.Transactional
import groovy.sql.Sql

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.aviza.AvizoAtributy
import com.sfs.crfosp.aviza.AvizoLog
import com.sfs.crfosp.aviza.AvizoMeno
import com.sfs.crfosp.aviza.AvizoObmPravnejSpos
import com.sfs.crfosp.aviza.AvizoOsoba
import com.sfs.crfosp.aviza.AvizoPobyt
import com.sfs.crfosp.aviza.AvizoPriezvisko
import com.sfs.crfosp.aviza.AvizoRodinnyVztah
import com.sfs.crfosp.aviza.AvizoRodnePriez
import com.sfs.crfosp.aviza.AvizoStatnaPrislusnot
import com.sfs.crfosp.aviza.AvizoTitul
import com.sfs.crfosp.aviza.AvizoUdelStObcianstvo
import com.sfs.crfosp.aviza.AvizoZrusVyhlMrtvy
import com.sfs.crfosp.cis.AvizVybavCis
import com.sfs.crfosp.domain.Osoba
import com.sfs.crfosp.security.Uzivatel
import com.sfs.crfosp.ws.RequestAvizo
import com.sfs.crfosp.ws.RequestFiles
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.AvizoStatusEnum
import com.sfs.crfosp.ws.enums.RequestStatusEnum
import com.sfs.crfosp.ws.enums.VybavenieStatusEnum
import com.sfs.crfosp.ws.services.IntAvizaWSService

@Transactional
class FillAvizoOsobaService {

	def dataSource
	def springSecurityService
	def encrypterService
	GrailsApplication grailsApplication
	IntAvizaWSService intAvizaWSService
	MessageSource messageSource
	
	def setAvOsobaRelFromFrm(AvizoOsoba avizoOsoba) {
		avizoOsoba.setAvizoOsoba()
		if (avizoOsoba.osoba) avizoOsoba.ifo = avizoOsoba.osoba.ifo
		avizoOsoba.save(flush:true)
		if (!avizoOsoba.osoba && avizoOsoba.ifo) avizoOsoba.osoba = Osoba.findByIfo(avizoOsoba.ifo.padRight(32, ' '))
		if (avizoOsoba.osoba) {
			if (avizoOsoba.osoba.meno) {
				avizoOsoba.osoba.meno.each { it ->
					AvizoMeno rec = new AvizoMeno(menoosoby:it,osoba:avizoOsoba)
					rec.setAvizoMeno()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.priezvisko) {
				avizoOsoba.osoba.priezvisko.each { it ->
					AvizoPriezvisko rec = new AvizoPriezvisko(priezviskosoby:it, osoba:avizoOsoba)
					rec.setAvizoPriezvisko()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.rodnepriezvisko) {
				avizoOsoba.osoba.rodnepriezvisko.each { it ->
					AvizoRodnePriez rec = new AvizoRodnePriez(rodnePriezvisko:it, osoba:avizoOsoba)
					rec.setAvizoRodnePriez()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.titul) {
				avizoOsoba.osoba.titul.each { it ->
					AvizoTitul rec = new AvizoTitul(titulOsoby:it, osoba:avizoOsoba)
					rec.setAvizoTitul()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.statnaPrislusnost) {
				avizoOsoba.osoba.statnaPrislusnost.each { it ->
					AvizoStatnaPrislusnot rec = new AvizoStatnaPrislusnot(statnaPrislusnot:it, osoba:avizoOsoba)
					rec.setAvizoStatnaPrislusnot()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.pobyt) {
				avizoOsoba.osoba.pobyt.each { it ->
					AvizoPobyt rec = new AvizoPobyt(pobyt:it, osoba:avizoOsoba)
					rec.setAvizoPobyt()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.rodinnyVztah) {
				avizoOsoba.osoba.rodinnyVztah.each { it ->
					AvizoRodinnyVztah rec = new AvizoRodinnyVztah(rodinnyVztah:it, osoba:avizoOsoba)
					rec.setAvizoRodinnyVztah()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.udelStObcianstvo) {
				avizoOsoba.osoba.udelStObcianstvo.each { it ->
					AvizoUdelStObcianstvo rec = new AvizoUdelStObcianstvo(udelStOb:it, osoba:avizoOsoba)
					rec.setAvizoUdelStObcianstvo()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.obmPravnejSpos) {
				avizoOsoba.osoba.obmPravnejSpos.each { it ->
					AvizoObmPravnejSpos rec = new AvizoObmPravnejSpos(obmprspos:it, osoba:avizoOsoba)
					rec.setAvizoObmPravnejSpos()
					rec.save(flush:true)
				}
			}
			if (avizoOsoba.osoba.zrusVyhlMrtvy) {
				avizoOsoba.osoba.zrusVyhlMrtvy.each { it ->
					AvizoZrusVyhlMrtvy rec = new AvizoZrusVyhlMrtvy(zrusVyhlMrtvy:it, osoba:avizoOsoba)
					rec.setAvizoZrusVyhlMrtvy()
					rec.save(flush:true)
				}
			}
		}
	}

	def setAtributyFromFrm (AvizoOsoba avizoOsoba) {
		def ret
		// vymaz atributy
		avizoOsoba.remAttr()
		avizoOsoba.save(flush:true);
		AvizVybavCis.findAllByDomain('com.sfs.crfosp.aviza.AvizoOsoba').each {polozka ->		
			avizoOsoba.avizo.avizoAtributy.findAll { p -> p.polozka == polozka }?.each {
			//AvizoAtributy.findAllByAvizoAndPolozka(avizoOsoba.avizo, polozka).each {
				avizoOsoba.avizo.removeFromAvizoAtributy(it)
				it.delete(flush:true)
			}
			intAvizaWSService.deleteAvizoLog(avizoOsoba.avizo, polozka.id)
		}
		avizoOsoba.refresh()
		avizoOsoba.avizo.setStatus(AvizoStatusEnum.EVIDOVANE)
		ret = intAvizaWSService.setAvOsobaRel(avizoOsoba)
		ret = ret?ret + intAvizaWSService.newLn() + intAvizaWSService.setAvAttr(avizoOsoba):intAvizaWSService.setAvAttr(avizoOsoba)
		avizoOsoba.validate()
		if (avizoOsoba.hasErrors()) {
			avizoOsoba.avizo.setStatus(AvizoStatusEnum.CHYBNE)
		}
	}

	def sendAvizoOutFromFrm(Avizo avizo) {
		
		// ak nie su ziadne atributy mohlo by byt chybne
		avizo.avizoLog.findAll{ p -> p.code == 0 }?.each{ avizo.removeFromAvizoLog(it); it.delete(flush:true) }
		if (!avizo.avizoAtributy) {
			def mmsg = messageSource.getMessage("avizo.errors.nodata",[] as Object[],LocaleContextHolder.getLocale())
			avizo.avizoLog.add( new AvizoLog(avizo:avizo, code:0, msg:mmsg).save(flush:true) )
			//avizo.status = AvizoStatusEnum.CHYBNE
			//avizo.save(flush:true)
			return mmsg
		} 				
		
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		def spSys = aktualnyUzivatel.getSystem()?.id
		def ifo = avizo.avosoba.ifo
		String param = String.valueOf("ot_rqparam(crreqpar_seq.nextval, 1, '${ifo?ifo:''}', null,null,null,null,null,null,null,null,null)")		
		param = "ct_rqparam(${param})"
		def reqLogId // *** set requestLog.id into requestFile
		encrypterService.authCrypter()
		def sql = Sql.newInstance(dataSource)
		try{
			sql.call("{call cr_intws.wsa1_zaznamenanie(?,?,?,${param},?,?,?)}",[spSys, aktualnyUzivatel.username, avizo.id, Sql.INTEGER, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
				reqLogId = reqID // *** set requestLog.id into requestFile
			}
		} catch (Exception e) {
			e.printStackTrace()
			return e.getMessage()
		}

		RequestLog requestLog
		if (reqLogId) {
			requestLog = RequestLog.get(reqLogId)
			avizo.setRequest(requestLog)
			avizo.setCorrId(reqLogId.toString())
			try {
				RequestFiles.findByMsgId(intAvizaWSService.logWSFilesService.getRequestId()).each(){
					it.setRequest(requestLog)
					it.save flush:true
				}
			}catch (Exception e) {
				e.printStackTrace();
				return e.getMessage()
			}
		}

		avizo.ws = Boolean.FALSE
		avizo.corrId = reqLogId.toString()
		avizo.addToAvizaRequest(new RequestAvizo( request:requestLog, respkod:requestLog.respkod, respmsg:requestLog.respmsg ))
		
		setAtributyFromFrm (avizo.avosoba)
		avizo.validate()
		if (avizo.hasErrors()) {
			avizo.setStatus(AvizoStatusEnum.CHYBNE)
		} else {
			avizo.save flush:true
			//avizo.save()
		}

		if (avizo.status == AvizoStatusEnum.EVIDOVANE) {
			avizo.request.setIswsout(Boolean.TRUE)
			avizo.request.setStav(RequestStatusEnum.SENDEXT)			
			avizo.request.save(flush:true)
			intAvizaWSService.createXMLOut(avizo)
			// avizo.setStatus(AvizoStatusEnum.ODOSLANE) // je to v createXMLOut
		}
		if (avizo.status == AvizoStatusEnum.CHYBNE) {
			requestLog.refresh()
			requestLog.setRespkod(avizo.request.respkod=1?9999:avizo.request.respkod)
			requestLog.setRespmsg(avizo.request.respmsg + ret)
			requestLog.save(flush:true)
			avizo.request = requestLog
			avizo.save(flush:true)
			return avizo.request.respmsg
		}
		avizo.save(flush:true)
		return 'OK'
	}

	def setAtributyRelFromFrm(AvizoOsoba avizoOsoba, String className) {
		if (avizoOsoba) {
			avizoOsoba.avizo.avizoLog.findAll{ p -> p.code == 0 }?.each{ avizoOsoba.avizo.removeFromAvizoLog(it); it.delete(flush:true)}
			def retStr = intAvizaWSService.setAvizoAttrRef(avizoOsoba, className)
			def chybne = avizoOsoba.avizo.avizoAtributy.find {a -> a.status == VybavenieStatusEnum.CHYBNE } 
			if (chybne)	{
				avizoOsoba.avizo.setStatus(AvizoStatusEnum.CHYBNE)
			} else {
				avizoOsoba.avizo.setStatus(AvizoStatusEnum.EVIDOVANE)
			}
			avizoOsoba.avizo.save(flush:true)
		}
	}

}
