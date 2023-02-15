package com.sfs.crfosp.ws.services
import grails.transaction.Transactional
import groovy.sql.Sql

import java.sql.Timestamp

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller

import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.springframework.context.i18n.LocaleContextHolder

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.aviza.AvizoAtributy
import com.sfs.crfosp.aviza.AvizoLog
import com.sfs.crfosp.aviza.AvizoOsoba
import com.sfs.crfosp.cis.AvizVybavCis
import com.sfs.crfosp.cis.KodOpovedeCis
import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.domain.Osoba
import com.sfs.crfosp.security.Uzivatel
import com.sfs.crfosp.transport.aviza.NevybaveneAviza
import com.sfs.crfosp.transport.aviza.NevybaveneAvizaData
import com.sfs.crfosp.transport.aviza.NevybaveneAvizaResponse
import com.sfs.crfosp.transport.aviza.VybavenieAvizData
import com.sfs.crfosp.transport.aviza.VybavenieAvizResponse
import com.sfs.crfosp.transport.aviza.ZaznamenanieAviza
import com.sfs.crfosp.transport.aviza.ZaznamenanieAvizaResponse
import com.sfs.crfosp.transport.avizout.AvizoOut
import com.sfs.crfosp.ws.RequestAvizo
import com.sfs.crfosp.ws.RequestFiles
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.AvizoStatusEnum
import com.sfs.crfosp.ws.enums.RequestStatusEnum
import com.sfs.crfosp.ws.enums.VybavenieStatusEnum

@Transactional
class IntAvizaWSService {


	def dataSource
	def encrypterService
	def springSecurityService
	def configHolderService
	def grailsApplication
	def messageSource
	def logWSFilesService
	def sessionFactory

	@Transactional
	VybavenieAvizResponse wsa2(String[] avizo) {
		log.debug "WSA2 START"

		VybavenieAvizResponse resp = new VybavenieAvizResponse()

		resp.setRequestDateTime(new Date())
		def reqLogId // *** set requestLog.id into requestFile
		def sql = Sql.newInstance(dataSource)
		log.debug springSecurityService.principal.username
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		//dostrani nulls
		if(avizo) avizo=avizo.grep()

		RequestLog.withTransaction{status->
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WSA2')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id

			log.debug "WSA2 params AVIZO: "+avizo.toString().substring(1,avizo.toString().length()-1);

			try{
				sql.call("{call cr_intws.wsa2_vybavenie(?,?,?,?,?)}",[spSys, avizo.toString().substring(1,avizo.toString().length()-1), Sql.BIGINT, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
					def req=RequestLog.findById(reqID)
					req.refresh()
					resp.aviza = new VybavenieAvizData(req)
					reqLogId = reqID
				}
			}catch (Exception e) {
				resp.aviza = new VybavenieAvizData()
				resp.aviza.setSystemSP(SystemCis.get(spSys))
				resp.aviza.setUziv(aktualnyUzivatel.getUsername())
				resp.aviza.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()
				resp.aviza.setRespmsg(baseMsg.concat(e.message))
			}
		}

		// *** set requestLog.id into requestFile
		if (reqLogId) {
			RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
				it.setRequest(RequestLog.get(reqLogId))
				it.save flush:true
			}
		}

		//		def debg = resp as JSON
		//		log.debug "Response from DB: " + debg
		resp.setResponseDateTime(new Date())
		log.debug "WSA2 END"
		return resp
	}


	NevybaveneAvizaResponse wsa3(String ifo) {
		log.debug "WSA3 START"

		NevybaveneAvizaResponse resp = new NevybaveneAvizaResponse()

		resp.setRequestDateTime(new Date())
		def reqLogId // *** set requestLog.id into requestFile

		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		//dostrani nulls
		//if(ifo) ifo=ifo.grep()

		RequestLog.withTransaction{status->
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WSA3')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id

			String param = String.valueOf("ot_rqparam(crreqpar_seq.nextval, 1, '${ifo?ifo:''}', null,null,null,null,null,null,null,null,null)")
			param = "ct_rqparam(${param})"

			log.debug "WSA3 params IFO: "+ifo;

			def sql = Sql.newInstance(dataSource)
			Avizo.withTransaction{
				encrypterService.authCrypter()
				try{
					sql.call("{call cr_intws.wsa3_nevybaveneAviza(?,?,${param},?,?,?)}",[spSys, aktualnyUzivatel.username, Sql.INTEGER, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
						def req=RequestLog.findById(reqID)
						req.refresh()
						resp.aviza = new NevybaveneAvizaData(req)
						reqLogId = reqID // *** set requestLog.id into requestFile
						log.debug "WSA3 reqLogId: "+reqLogId.toString();
					}
				} catch (Exception e) {
					log.debug "WSA3 Exception "+e.getMessage();
					resp.aviza = new NevybaveneAvizaData()
					resp.aviza.setSystemSP(SystemCis.get(spSys))
					resp.aviza.setUziv(aktualnyUzivatel.getUsername())
					resp.aviza.setRespkod( 9999 )
					String baseMsg = KodOpovedeCis.findById(9999).getMsg()
					resp.aviza.setRespmsg(baseMsg.concat(e.getMessage()))
				}
			}

			//			avizo.setStatus(AvizoStatusEnum.EVIDOVANE)
			//			avizo.setLastStatus(VybavenieStatusEnum.NEPOTVRDENE)
			//			avizo.osoba = avizo.avosoba.osoba

			RequestLog requestLog

			if (reqLogId) {
				requestLog = RequestLog.get(reqLogId)
				//				Avizo.setRequest(requestLog)
				//				Avizo.setCorrId(reqLogId.toString())
				try {
					RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
						it.setRequest(requestLog)
						it.save flush:true
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				// ci vobec existuje osoba s tym ifo
				if (requestLog.respkod == 1) {
					def mifo=ifo.padRight(32, ' ')
					log.debug "WSA3 mifo: "+mifo;
					Osoba osoba = Osoba.findByIfo(mifo)
					def avizoTest = Avizo.findByOsoba(osoba)
					// ci ma avizo
					if (avizoTest) {
						log.debug "WSA3 avizoTest";
						resp.aviza = new NevybaveneAvizaData(requestLog)
						Avizo.findAllByOsobaAndStatus(osoba,AvizoStatusEnum.ODOSLANE).each{
							log.debug "WSA3 ODOSLANE id:"+it.id.toString();
							NevybaveneAviza nevybaveneAviza = new NevybaveneAviza(it)
							resp.aviza.nevybaveneAviza.add(nevybaveneAviza)
						}
					}
				}

				// *** set requestLog.id into requestFile
				RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
					it.setRequest(RequestLog.get(reqLogId))
					it.save flush:true
				}
			}

			//		def debg = resp as JSON
			//		log.debug "Response from DB: " + debg
			resp.setResponseDateTime(new Date())
			log.debug "WSA3 END"
			return resp
		}
	}


	@Transactional
	ZaznamenanieAvizaResponse wsa1(Avizo avizo) {

		log.debug "WSA1 START"

		ZaznamenanieAvizaResponse resp = new ZaznamenanieAvizaResponse()

		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		def spSys = aktualnyUzivatel.getSystem()?.id

		resp.setRequestDateTime(new Date())

		def reqLogId // *** set requestLog.id into requestFile

		log.debug "WSA1 ifo = "+avizo.avosoba.ifo

		encrypterService.authCrypter()

		def ifo = avizo.avosoba.ifo

		String param = String.valueOf("ot_rqparam(crreqpar_seq.nextval, 1, '${ifo?ifo:''}', null,null,null,null,null,null,null,null,null)")
		param = "ct_rqparam(${param})"

		def ret
		def sql = Sql.newInstance(dataSource)
		Avizo.withTransaction{
			encrypterService.authCrypter()
			try{
				sql.call("{call cr_intws.wsa1_zaznamenanie(?,?,?,${param},?,?,?)}",[spSys, aktualnyUzivatel.username, avizo.id, Sql.INTEGER, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
					reqLogId = reqID // *** set requestLog.id into requestFile
				}
			} catch (Exception e) {
				resp.avizoResponse = new ZaznamenanieAviza()
				resp.avizoResponse.setSystemSP(SystemCis.get(spSys))
				resp.avizoResponse.setUziv(aktualnyUzivatel.getUsername())
				resp.avizoResponse.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()
				resp.avizoResponse.setRespmsg(baseMsg.concat(e.message))
				return resp
			}
		}
		// avizo.refresh()
		log.debug "WSA1 reqLogId = "+reqLogId.toString()
		RequestLog requestLog
		if (reqLogId) {
			requestLog = RequestLog.get(reqLogId)
			avizo.setRequest(requestLog)
			avizo.setCorrId(reqLogId.toString())
			try {
				RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
					it.setRequest(requestLog)
					it.save flush:true
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		resp.avizoResponse = new ZaznamenanieAviza(requestLog)
		resp.setResponseDateTime(new Date())
		// zrus to ak v sql vznikla nejaka chyba
		if (requestLog.respkod > 1) {
			resp.avizoResponse.setRespkod(requestLog.respkod)
			resp.avizoResponse.setRespmsg(requestLog.respmsg)
			avizo.delete()
			return resp
		}

		avizo.avosoba.setAvizoOsoba()
		avizo.systemSP = aktualnyUzivatel.getSystem()
		avizo.setLastStatus(VybavenieStatusEnum.NEPOTVRDENE)
		avizo.osoba = avizo.avosoba.osoba
		avizo.setStatus(AvizoStatusEnum.EVIDOVANE)
		log.debug "WSA1 avizo.save"
		avizo.addToAvizaRequest(new RequestAvizo( request:requestLog, respkod:requestLog.respkod, respmsg:requestLog.respmsg ))
		avizo.save flush:true
		log.debug "WSA1 avizo.saved id = "+avizo.id

		avizo.avosoba.avizo = avizo
		avizo.avosoba.validate()
		if (avizo.avosoba.hasErrors()) {
			avizo.setStatus(AvizoStatusEnum.CHYBNE)
		} else {
			avizo.avosoba.save(flush:true)
			ret=setAvOsobaRel(avizo.avosoba)
		}

		resp.avizoResponse.avizo = avizo.id
		avizo.ws = Boolean.TRUE
		avizo.corrId = reqLogId.toString()

		ret = ret?ret + newLn() + setAvAttr(avizo.avosoba):setAvAttr(avizo.avosoba)
		avizo.validate()
		if (avizo.hasErrors()) {
			avizo.setStatus(AvizoStatusEnum.CHYBNE)
		} else {
			avizo.save flush:true
			//avizo.save(flush:true)
		}

		if (avizo.status == AvizoStatusEnum.EVIDOVANE) {
			avizo.request.setIswsout(true)
			avizo.request.setStav(RequestStatusEnum.SENDEXT)
			avizo.request.save(flush:true)
			avizo.save flush:true
			createXMLOut(avizo)
			// avizo.setStatus(AvizoStatusEnum.ODOSLANE) // je to v createXMLOut
		}

		log.debug 'wsa1 avizo.status: '+avizo.status
		if (avizo.status == AvizoStatusEnum.CHYBNE) {
			resp.avizoResponse.setStav(RequestStatusEnum.ERROR)
			resp.avizoResponse.setRespkod(avizo.request.respkod=avizo.request.respkod=1?9999:avizo.request.respkod)
			resp.avizoResponse.setRespmsg(avizo.request.respmsg)
			requestLog.refresh()
			requestLog.setRespkod(resp.avizoResponse.respkod)
			requestLog.setRespmsg(resp.avizoResponse.respmsg='OK'?ret:resp.avizoResponse.respmsg + ret)
			requestLog.save (flush:true)
			avizo.request = requestLog

		}
		avizo.save(flush:true)
		return resp
	}

	def setAvOsobaRel(AvizoOsoba avizoOsoba) {
		def retVal
		if (!avizoOsoba.osoba) avizoOsoba.osoba = Osoba.findByIfo(avizoOsoba.ifo.padRight(32, ' '))
		if (avizoOsoba.osoba) {
			if (avizoOsoba.meno) {
				avizoOsoba.meno.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoMeno'):setAvizoAttrRef(avizoOsoba,'AvizoMeno')
			}
			if (avizoOsoba.priezvisko) {
				avizoOsoba.priezvisko.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoPriezvisko'):setAvizoAttrRef(avizoOsoba,'AvizoPriezvisko')
			}
			if (avizoOsoba.rodnepriezvisko) {
				avizoOsoba.rodnepriezvisko.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoRodnePriez'):setAvizoAttrRef(avizoOsoba,'AvizoRodnePriez')
			}
			if (avizoOsoba.titul) {
				avizoOsoba.titul.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoTitul'):setAvizoAttrRef(avizoOsoba,'AvizoTitul')
			}
			if (avizoOsoba.statnaPrislusnost) {
				avizoOsoba.statnaPrislusnost.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoStatnaPrislusnot'):setAvizoAttrRef(avizoOsoba,'AvizoStatnaPrislusnot')
			}
			if (avizoOsoba.pobyt) {
				avizoOsoba.pobyt.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoPobyt'):setAvizoAttrRef(avizoOsoba,'AvizoPobyt')
			}
			if (avizoOsoba.rodinnyVztah) {
				avizoOsoba.rodinnyVztah.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoRodinnyVztah'):setAvizoAttrRef(avizoOsoba,'AvizoRodinnyVztah')
			}
			if (avizoOsoba.udelStObcianstvo) {
				avizoOsoba.udelStObcianstvo.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoUdelStObcianstvo'):setAvizoAttrRef(avizoOsoba,'AvizoUdelStObcianstvo')
			}
			if (avizoOsoba.obmPravnejSpos) {
				avizoOsoba.obmPravnejSpos.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoObmPravnejSpos'):setAvizoAttrRef(avizoOsoba,'AvizoObmPravnejSpos')
			}
			if (avizoOsoba.zrusVyhlMrtvy) {
				avizoOsoba.zrusVyhlMrtvy.each { it ->
					it.osoba = avizoOsoba
					it.save flush:true
				}
				retVal=retVal?retVal + newLn() + setAvizoAttrRef(avizoOsoba,'AvizoZrusVyhlMrtvy'):setAvizoAttrRef(avizoOsoba,'AvizoZrusVyhlMrtvy')
			}
		}
		return retVal
	}

	def setAvAttr(AvizoOsoba avizoOsoba) {
		def names = AvizoOsoba.gormPersistentEntity.persistentPropertyNames
		String errMsg
		AvizVybavCis.findAllByDomain(AvizoOsoba.class.getName())each {polozka ->
			def atrPolozka = polozka
			names.each {
				def hodn = avizoOsoba[it.toString()]
				def mpole1 = it.toString()
				def mpole2 =  polozka.pole.toString()
				if ( hodn != null && mpole1 == mpole2) {
					AvizoAtributy avizoAtributy = new AvizoAtributy()
					avizoAtributy.polozka = polozka
					def mOldVal = avizoOsoba[it[0..-2]].toString()
					if (mOldVal.length() > 13) {
						if (mOldVal[0..14] == "com.sfs.crfosp.") mOldVal = avizoOsoba[it[0..-2]].id.toString()
					}
					def mdt = avizoOsoba[it[0..-2]]
					if (avizoOsoba[it[0..-2]] in Date || avizoOsoba[it[0..-2]] in Timestamp) mOldVal = avizoOsoba[it[0..-2]].getDateString()
					avizoAtributy.oldval = mOldVal
					def mNewVal = avizoOsoba[it].toString()
					if (avizoOsoba[it] in Date || avizoOsoba[it] in Timestamp) mNewVal = avizoOsoba[it].getDateString()
					avizoAtributy.newval = mNewVal
					Long c = AvizoAtributy.countByAvizoAndPolozka(avizoAtributy.avizo, avizoAtributy.polozka)
					errMsg = setAvizoAtributyOthers(avizoAtributy, avizoOsoba, errMsg, c)
					def atrFname = 'atr'+it[0..-2].toLowerCase().capitalize()
					avizoOsoba[atrFname] = avizoAtributy
					//avizoOsoba.refresh()
					avizoOsoba.save(flush:true)
				}
			}
		}
		return errMsg
	}

	def setAvizoAttrRef(AvizoOsoba avizoOsoba, String domena) {
		def mdom = 'com.sfs.crfosp.aviza.' + domena
		String errMsg
		if (domena =='AvizoPobyt') {
			if (avizoOsoba.typoso.id == 1 || avizoOsoba.typoso.id == 2) {
				avizoOsoba.pobyt.each{
					AvizoAtributy avizoAtributy = new AvizoAtributy()
					if (it.typpob.id == 2) {
						avizoAtributy.polozka = AvizVybavCis.get(32)
					} else {
						avizoAtributy.polozka = AvizVybavCis.get(16)
					}
					def atrId = it.atrPobyt?.id
					it.remAttr()
					it.save(flush:true)
					deleteAvizoLog(avizoOsoba.avizo, avizoAtributy.polozka.id)
					AvizoAtributy.findAllById(atrId)?.each {
						avizoOsoba.avizo.removeFromAvizoAtributy(it)
						it.delete(flush:true) }
					if (it.typpobA || it.dtprihlA || it.dtukonA || it.supcisA || it.pobcisA || it.orientcA || it.znakocA || it.regulicaA || it.regobecA || it.regobcastA || it.regokresA || it.pobstatA || it.zmiestoA || it.zobecA || it.zcastobA || it.zulicaA || it.zokresA || it.zorientcisA || it.zsupiscA || it.zcastbudA || it.bytcisA || it.regadridA || it.regvchodA || it.regdomA) {
						avizoAtributy.oldval = it.getPobytOld()
						avizoAtributy.newval = it.getPobytNew()
						errMsg = setAvizoAtributyOthers(avizoAtributy, avizoOsoba, errMsg)
						it.atrPobyt = avizoAtributy
						it.save(flush:true)
					}
				}
			} else {
				avizoOsoba.pobyt.each{
					AvizoAtributy avizoAtributy = new AvizoAtributy()
					if (it.typpob.id == 2) {
						avizoAtributy.polozka = AvizVybavCis.get(46)
					} else {
						avizoAtributy.polozka = AvizVybavCis.get(47)
					}
					def atrId = it.atrPobyt?.id
					it.remAttr()
					it.save(flush:true)
					deleteAvizoLog(avizoOsoba.avizo, avizoAtributy.polozka.id)
					AvizoAtributy.findAllById(atrId)?.each {
						avizoOsoba.avizo.removeFromAvizoAtributy(it)
						it.delete(flush:true)
					}
					if (it.typpobA || it.dtprihlA || it.dtukonA || it.supcisA || it.pobcisA || it.orientcA || it.znakocA || it.regulicaA || it.regobecA || it.regobcastA || it.regokresA || it.pobstatA || it.zmiestoA || it.zobecA || it.zcastobA || it.zulicaA || it.zokresA || it.zorientcisA || it.zsupiscA || it.zcastbudA || it.bytcisA || it.regadridA || it.regvchodA || it.regdomA) {
						avizoAtributy.oldval = it.getPobytOld()
						avizoAtributy.newval = it.getPobytNew()
						errMsg = setAvizoAtributyOthers(avizoAtributy, avizoOsoba, errMsg)
						it.atrPobyt = avizoAtributy
						it.save(flush:true)
					}
				}
			}
		}

		if (domena =='AvizoRodinnyVztah') {
			avizoOsoba.rodinnyVztah.each{
				AvizoAtributy avizoAtributy = new AvizoAtributy()
				if (it.typvzt.id == 2) {
					avizoAtributy.polozka = AvizVybavCis.get(25)
				} else {
					avizoAtributy.polozka = AvizVybavCis.get(53)
					if (it.dtzanik || it.dtzanikA) avizoAtributy.polozka = AvizVybavCis.get(37)
				}
				if (it.sobmatrtxtA) avizoAtributy.polozka = AvizVybavCis.get(48)
				def atrId = it.atrRodvz?.id
				it.remAttr()
				it.save(flush:true)
				deleteAvizoLog(avizoOsoba.avizo, avizoAtributy.polozka.id)
				AvizoAtributy.findAllById(atrId)?.each {
					avizoOsoba.avizo.removeFromAvizoAtributy(it)
					it.delete(flush:true)
				}
				if (it.ifoA || it.dtvznikA || it.dtzanikA || it.fg_neplA || it.dvdneplA || it.miestovydtxtA || it.miestovydA || it.ptyproleA || it.vtyproleA || it.sobmatrtxtA || it.sobmatrA || it.oprodicA ) {
					avizoAtributy.oldval = it.getRodinnyVztahOld()
					avizoAtributy.newval = it.getRodinnyVztahNew()
					errMsg = setAvizoAtributyOthers(avizoAtributy, avizoOsoba, errMsg)
					it.atrRodvz = avizoAtributy
					it.save(flush:true)
				}
			}
		}

		if (domena != 'AvizoPobyt' && domena != 'AvizoRodinnyVztah' ) {
			AvizVybavCis.findAllByDomain(mdom)each {polozka ->
				AvizoAtributy avizoAtributy = new AvizoAtributy()
				avizoAtributy.polozka = polozka
				switch (domena) {
					case 'AvizoMeno':
						avizoOsoba.meno.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.menoA) {
								avizoAtributy.oldval = it.getMenoOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getMenoOld():it.getMenoOld()
								avizoAtributy.newval = it.getMenoNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getMenoNew():it.getMenoNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					case "AvizoPriezvisko":
						avizoOsoba.priezvisko.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.priezviskoA) {
								avizoAtributy.oldval = it.getPriezviskoOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getPriezviskoOld():it.getPriezviskoOld()
								avizoAtributy.newval = it.getPriezviskoNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getPriezviskoNew():it.getPriezviskoNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					case "AvizoRodnePriez":
						avizoOsoba.rodnepriezvisko.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.priezviskoA) {
								avizoAtributy.oldval = it.getRodnePriezOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getRodnePriezOld():it.getRodnePriezOld()
								avizoAtributy.newval = it.getRodnePriezNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getRodnePriezNew():it.getRodnePriezNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					case "AvizoTitul":
						avizoOsoba.titul.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.titulA || it.typtitA) {
								avizoAtributy.oldval = it.getTitulOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getTitulOld():it.getTitulOld()
								avizoAtributy.newval = it.getTitulNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getTitulNew():it.getTitulNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					case "AvizoStatnaPrislusnot":
						avizoOsoba.statnaPrislusnost.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.stprisA) {
								avizoAtributy.oldval = it.getStatnaPrislusnotOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getStatnaPrislusnotOld():it.getStatnaPrislusnotOld()
								avizoAtributy.newval = it.getStatnaPrislusnotNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getStatnaPrislusnotNew():it.getStatnaPrislusnotNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					case "AvizoUdelStObcianstvo":
						avizoOsoba.udelStObcianstvo.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.dtprevA) {
								avizoAtributy.oldval = it.getUdelStObcianstvoOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getUdelStObcianstvoOld():it.getUdelStObcianstvoOld()
								avizoAtributy.newval = it.getUdelStObcianstvoNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getUdelStObcianstvoNew():it.getUdelStObcianstvoNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					case "AvizoZrusVyhlMrtvy":
						avizoOsoba.zrusVyhlMrtvy.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.dtpravA||it.zrusnezrusA != null) {
								avizoAtributy.oldval = it.getZrusVyhlMrtvyOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getUdelStObcianstvoOld():it.getUdelStObcianstvoOld()
								avizoAtributy.newval = it.getZrusVyhlMrtvyNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getUdelStObcianstvoNew():it.getUdelStObcianstvoNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					case "AvizoObmPravnejSpos":
						avizoOsoba.obmPravnejSpos.each{
							def attrId = it[polozka.pole]?.id
							Integer hasval = 0
							it.remAttr()
							if (it.typobmA||it.dtodA||it.dtdoA||it.poznA) {
								avizoAtributy.oldval = it.getObmPravnejSposOld() //avizoAtributy.oldval?avizoAtributy.oldval + (char)13 + it.getUdelStObcianstvoOld():it.getUdelStObcianstvoOld()
								avizoAtributy.newval = it.getObmPravnejSposNew() //avizoAtributy.newval?avizoAtributy.newval + (char)13 + it.getUdelStObcianstvoNew():it.getUdelStObcianstvoNew()
								hasval = 1
							}
							errMsg = setAvizoAttrRefOneRec(avizoOsoba, avizoAtributy, polozka, it, attrId, hasval, errMsg)
						}
						break;
					default: println "Nic";
				}
			}
		}
		return errMsg
	}

	def setAvizoAttrRefOneRec(AvizoOsoba avizoOsoba, AvizoAtributy avizoAtributy, AvizVybavCis polozka, Object mDom, Long attrId, Integer hasval, String errMsg) {
		AvizoAtributy.findAllById(attrId).each {
			avizoOsoba.avizo.removeFromAvizoAtributy(it)
			it.delete(flush:true)
		}
		deleteAvizoLog(avizoOsoba.avizo, polozka.id)
		if (hasval > 0) {
			errMsg = setAvizoAtributyOthers(avizoAtributy, avizoOsoba, errMsg)
			mDom[polozka.pole] = avizoAtributy
			mDom.save(flush:true)
		}
		return errMsg
	}

	// transformacia - data do response
	def getTransformData (clazIn, clazOut) {

		def inClass = grailsApplication.getDomainClass(clazIn.getName())
		def outClass = Class.forName(clazOut.getName(), false, Thread.currentThread().contextClassLoader).newInstance()

		inClass.getPersistentProperties().each {GrailsDomainClassProperty pIn ->
			def mprops = outClass.getProperties()
			def mprop =	mprops.find{it.key==pIn.getName()}
			if (mprop) {
				def mval = inClass[pIn.getName()]
				mprop = inClass[pIn.getName()]
			}
		}
		return outClass
	}

	//@Transactional
	def createXMLOut(Avizo avizo) {
		def avizoOut = new AvizoOut().dajAvizoOut(avizo)
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AvizoOut.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			ByteArrayOutputStream bos = new ByteArrayOutputStream()
			jaxbMarshaller.marshal(avizoOut, bos);

			def session = sessionFactory.currentSession
			//def sql = Sql.newInstance(dataSource)
			def sql = new Sql(session.connection())
			sql.call("{call crfo_traffic.pEnqueueWSA(?,?,?)}",['411', avizo.corrId, bos.toByteArray()])
			Avizo avizodb = Avizo.get(avizo.id)
			avizodb.refresh()
			avizo = avizodb
			RequestLog reqlogdb = RequestLog.get(avizo.request.id)
			reqlogdb.refresh()
			avizo.request = reqlogdb
			if (avizo.request.stav == RequestStatusEnum.ERROR) avizo.status = AvizoStatusEnum.CHYBNE
			if (avizo.request.stav == RequestStatusEnum.CLOSED) avizo.status = AvizoStatusEnum.NEVYBAVENE
			if (avizo.request.stav == RequestStatusEnum.EXTIDLE) avizo.status = AvizoStatusEnum.ODOSLANE
			//File file = new File("D:\\file.xml");
			//jaxbMarshaller.marshal(avizoOut, file);
		} catch (JAXBException e) {
			e.printStackTrace();
			avizo.status = AvizoStatusEnum.CHYBNE
			avizo.request.setRespkod( 9999 )
			String baseMsg = KodOpovedeCis.findById(9999)?.getMsg()
			avizo.request.setRespmsg(baseMsg.concat(e.message))
		}
	}

	def newLn() {
		//return (char) (13) + (char) (10)
		return System.lineSeparator();
	}

	def setAvizoLog(Avizo avizo, Long errCode, String errText) {
		AvizoLog avizoLog = new AvizoLog()
		avizoLog.avizo = avizo
		avizoLog.code = errCode
		avizoLog.msg = errText
		avizoLog.save flush:true
		log.debug avizoLog.id.toString()
	}

	def validAAtr(AvizoAtributy obj){
		Avizo.findByOsobaAndStatusInList(obj.avizo.osoba,[AvizoStatusEnum.ODOSLANE, AvizoStatusEnum.CAKAJUCE, AvizoStatusEnum.NEVYBAVENE]).each {
			def results = AvizoAtributy.findByPolozkaAndAvizo(obj.polozka, it)
			if(results) {
				obj.errors.reject('uniquePolozkaOsoby', results.avizo.id.toString())
			}
		}
	}

	def deleteAvizoLog(Avizo avizo, Long errCode) {
		AvizoLog.findAllByAvizoAndCode(avizo,errCode)?.each{ AvizoLog al ->
			avizo.removeFromAvizoLog(al)
			al.delete flush:true
		}
	}

	def ifAvizoAtributyHasErrors(AvizoAtributy avizoAtributy, AvizoOsoba avizoOsoba, String errMsg) {
		avizoOsoba.avizo.setStatus(AvizoStatusEnum.CHYBNE)
		avizoAtributy.errors.allErrors.each {
			def mtext = messageSource.getMessage("aviza.AvizoAtributy.uniquePolozkaOsoby", [avizoAtributy.polozka.nazov, it.defaultMessage] as Object[],LocaleContextHolder.getLocale())
			setAvizoLog(avizoOsoba.avizo, avizoAtributy.polozka.id, mtext)
			errMsg = errMsg?errMsg + newLn() + mtext:mtext
			avizoAtributy.errMsg = mtext
			avizoAtributy.status = VybavenieStatusEnum.CHYBNE
		}
		avizoAtributy.clearErrors()
		return errMsg
	}

	def saveAvizoAtributy(AvizoAtributy avizoAtributy, Long c) {
		//def c = AvizoAtributy.countByAvizoAndPolozka(avizoAtributy.avizo, avizoAtributy.polozka)
		if (c == 0) avizoAtributy.save(flush:true)
	}

	def setAvizoAtributyOthers(AvizoAtributy avizoAtributy, AvizoOsoba avizoOsoba, String errMsg, Long c) {
		avizoAtributy.status = VybavenieStatusEnum.NEPOTVRDENE
		avizoAtributy.avizo = avizoOsoba.avizo
		avizoAtributy.validatePolozkaAviza()
		if (avizoAtributy.hasErrors()) errMsg = ifAvizoAtributyHasErrors(avizoAtributy, avizoOsoba, errMsg)
		saveAvizoAtributy(avizoAtributy,c)
		return errMsg
	}

	def setAvizoAtributyOthers(AvizoAtributy avizoAtributy, AvizoOsoba avizoOsoba, String errMsg) {
		errMsg = setAvizoAtributyOthers(avizoAtributy, avizoOsoba, errMsg,0)
		log.debug errMsg
		return errMsg
	}

}


