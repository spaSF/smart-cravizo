package com.sfs.crfosp.ws.services

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.sql.Sql

import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil

import com.sfs.crfosp.cis.KodOpovedeCis
import com.sfs.crfosp.cis.RequestParamCis
import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.security.Uzivatel
import com.sfs.crfosp.ws.RequestFiles
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.CiselnikyEnum
import com.sfs.crfosp.ws.enums.RequestStatusEnum
import com.sfs.crfosp.ws.enums.SkupUdajovEnum
import com.sfs.crfosp.ws.transport.AktualizaciaFOData
import com.sfs.crfosp.ws.transport.AktualizaciaFOResponse
import com.sfs.crfosp.ws.transport.Ciselniky
import com.sfs.crfosp.ws.transport.CiselnikyResponse
import com.sfs.crfosp.ws.transport.NovoNarodeneDeti
import com.sfs.crfosp.ws.transport.NovoNarodeneDetiResponse
import com.sfs.crfosp.ws.transport.OverenieRegistracieResponse
import com.sfs.crfosp.ws.transport.ZaujemFOData
import com.sfs.crfosp.ws.transport.ZaujemFOResponse
import com.sfs.crfosp.ws.transport.ZmenySkupUdData
import com.sfs.crfosp.ws.transport.ZmenySkupUdResponse


@Transactional
class IntWSService {

	def dataSource
	def encrypterService
	def springSecurityService
	def configHolderService

	def logWSFilesService
	
	@Transactional
	OverenieRegistracieResponse ws1(String rc,Date dtNar,
			String meno,String priezvisko,String rodnePriezvisko,
			String ifo,Long reqId,Boolean goOut) {

		log.debug "WS1 START"
		
		OverenieRegistracieResponse resp = new OverenieRegistracieResponse()

		resp.setRequestDateTime(new Date())

		def reqLogId = reqId // *** set requestLog.id into requestFile
		def sql = Sql.newInstance(dataSource)
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		RequestLog.withTransaction{status->
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WS1')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id
			try {
				if(reqId){
					sql.call("{call cr_intws.ws1_overRequest(?,?,?,?)}", [reqId , goOut?1:0, Sql.INTEGER, Sql.VARCHAR]) { retKod,retM ->
						resp.overenieResponse = RequestLog.get(reqId)
						resp.overenieResponse.refresh()
					}
				}else{
					String formatDtNar=dtNar?.format(configHolderService.getValueForKey('crfosp.wsparams.dateformat'))

					String param = String.valueOf("ot_rqparam(crreqpar_seq.nextval, 'ME', '${meno?meno.replaceAll("'","''"):''}', 1)")
							.concat(",ot_rqparam(crreqpar_seq.nextval, 'PR', '${priezvisko?priezvisko.replaceAll("'","''"):''}', 1)")
							.concat(",ot_rqparam(crreqpar_seq.nextval, 'RC', '${rc?rc:''}', 1)")
							.concat(",ot_rqparam(crreqpar_seq.nextval, 'RP', '${rodnePriezvisko?rodnePriezvisko.replaceAll("'","''"):''}', 1)")
							.concat(",ot_rqparam(crreqpar_seq.nextval, 'DN', '${formatDtNar?formatDtNar:''}', 1)")
							.concat(",ot_rqparam(crreqpar_seq.nextval, 'IF', '${ifo?ifo:''}', 1)")

					param = "ct_rqparam(${param})"

					//novy request
					sql.call("{call cr_intws.ws1_overFO(?,${param},?,?,?,?)}", [spSys , Sql.BIGINT, Sql.INTEGER, Sql.INTEGER, Sql.VARCHAR]) { rqID,out,retKod,retM ->
						resp.overenieResponse = RequestLog.get(rqID)		
						reqLogId = rqID // *** set requestLog.id into requestFile
					}

				}
				if(resp.overenieResponse){
					GrailsHibernateUtil.unwrapIfProxy(resp.overenieResponse)
					resp.overenieResponse.getOsoby()?.each {rqOsoba->
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.meno)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.priezvisko)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.rodnepriezvisko)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.titul)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.statnaPrislusnost)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.pobyt)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.zakazPobytu)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.obmPravnejSpos)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.rodinnyVztah)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.doklad)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.udelStObcianstvo)
						GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.zrusVyhlMrtvy)

					}

					GrailsHibernateUtil.unwrapIfProxy(resp.overenieResponse.systemSP)
					//					if(resp.overenieResponse.systemSP.equals( SystemCis.get(spSys))){
					//						resp.overenieResponse.setSystemSP(SystemCis.get(spSys))
					//					}
					log.debug resp.overenieResponse as JSON					
				}
				

				
			} catch (Exception e) {
				status.setRollbackOnly()
				resp.overenieResponse = new RequestLog()
				resp.overenieResponse.setSystemSP(SystemCis.get(spSys))
				resp.overenieResponse.setUziv(aktualnyUzivatel.getUsername())
				resp.overenieResponse.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()

				resp.overenieResponse.setRespmsg(baseMsg.concat(e.message))
			}
		}
		
		// *** set requestLog.id into requestFile
		if (reqLogId) {
			RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
				it.setRequest(RequestLog.get(reqLogId))
				it.save flush:true
			}	
		}
		
		//		resp.overenieResponse.setSystemSP(SystemCis.get(resp.overenieResponse.systemSP?.getId()))
		log.debug "Response from DB: ma system " + resp.overenieResponse.systemSP.nazov +" ma status? " + resp.overenieResponse.stav
		resp.setResponseDateTime(new Date())	
		log.debug "WS1 END"
		return resp
	}

	@Transactional
	ZmenySkupUdResponse ws3(SkupUdajovEnum skupUdaj,Date zmenyOd){
		log.debug "WS3 START"
		
		ZmenySkupUdResponse resp = new ZmenySkupUdResponse ()

		resp.setRequestDateTime(new Date())
		def reqLogId  // *** set requestLog.id into requestFile
		def sql = Sql.newInstance(dataSource)
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)

		RequestLog.withTransaction{
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WS3')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id

			String formatZmenyOd=zmenyOd?.format(configHolderService.getValueForKey('crfosp.wsparams.datetimeformat'))
			String param = String.valueOf("ot_rqparam(crreqpar_seq.nextval, 'SKU', '${skupUdaj?skupUdaj.value():''}', 0)")
					.concat(",ot_rqparam(crreqpar_seq.nextval, 'DZM', '${formatZmenyOd?formatZmenyOd:''}', 0)")

			param = "ct_rqparam(${param})"
			try{
				sql.call("{call cr_intws.ws3_zmeny(?,${param},?,?,?)}",[spSys, Sql.BIGINT, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
					resp.zmenyResponse = new ZmenySkupUdData(RequestLog.findById(reqID))
					reqLogId = reqID // *** set requestLog.id into requestFile
				}
				if (resp.zmenyResponse) {
					//def query = "select ifo from CRWSOS where reqid = ${resp.zmenyResponse.id}"
					//sql.eachRow(query){ row -> resp.zmenyResponse.ifoList << ("$row.ifo").toString() }
						
					// toto je rychlejsie
					resp.zmenyResponse.ifoList = sql.rows( "select ifo from CRWSOS where reqid = ${resp.zmenyResponse.id}")["IFO"]
				}
			}catch (Exception e) {
				resp.zmenyResponse = new ZmenySkupUdData()
				resp.zmenyResponse.setSystemSP(SystemCis.get(spSys))
				resp.zmenyResponse.setUziv(aktualnyUzivatel.getUsername())
				resp.zmenyResponse.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()

				resp.zmenyResponse.setRespmsg(baseMsg.concat(e.message))
			}

		}
		
		// *** set requestLog.id into requestFile
		if (reqLogId) {
			RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
			   it.setRequest(RequestLog.get(reqLogId))
			   it.save flush:true
		   }
		}
		
		log.debug "Response from DB: " + (resp.zmenyResponse as JSON)
		resp.setResponseDateTime(new Date())
		log.debug "WS3 END"
		return resp
	}

	@Transactional
	AktualizaciaFOResponse ws2(SkupUdajovEnum[] skupUdaj,String[] ifo,String ifoOrRc){
		log.debug "WS2 START"
		
		AktualizaciaFOResponse resp = new AktualizaciaFOResponse()

		resp.setRequestDateTime(new Date())
		def reqLogId // *** set requestLog.id into requestFile
		def sql = Sql.newInstance(dataSource)
		
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		//dostrani nulls
		if(ifo) ifo=ifo.grep()
		if(skupUdaj) skupUdaj = skupUdaj.grep()
		if(!skupUdaj){
			//daj vsetky
			skupUdaj =[SkupUdajovEnum.rodne_cislo, SkupUdajovEnum.ident_udaje, SkupUdajovEnum.umrtie, SkupUdajovEnum.rodinne_vztahy, SkupUdajovEnum.adresa]
			log.debug skupUdaj
		}

		RequestLog.withTransaction{status->
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WS2')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id

			log.debug "WS2 params IFO: "+ifo.toString().substring(1,ifo.toString().length()-1);
			log.debug "WS2 params SKU: "+(skupUdaj*.value()).toString().substring(1,(skupUdaj*.value()).toString().length()-1);

			try{
				sql.call("{call cr_intws.ws2_aktualFO(?,?,?,?,?,?,?)}",[spSys, ifo.toString().substring(1,ifo.toString().length()-1), (skupUdaj*.value()).toString().substring(1,(skupUdaj*.value()).toString().length()-1), ifoOrRc, Sql.BIGINT, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
					def req=RequestLog.findById(reqID)
					req.refresh()
					resp.aktualizaciaResponse= new AktualizaciaFOData(req,skupUdaj.asType(List))
					reqLogId = reqID
				}
			}catch (Exception e) {
				resp.aktualizaciaResponse = new AktualizaciaFOData()
				resp.aktualizaciaResponse.setSystemSP(SystemCis.get(spSys))
				resp.aktualizaciaResponse.setUziv(aktualnyUzivatel.getUsername())
				resp.aktualizaciaResponse.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()

				resp.aktualizaciaResponse.setRespmsg(baseMsg.concat(e.message))
			}

		}
		
		// *** set requestLog.id into requestFile
		if (reqLogId) {
			RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
				log.debug("requestFile.id = " + it.id.toString())
			   it.setRequest(RequestLog.get(reqLogId))
			   it.save flush:true
		   }
		}
			
		//		def debg = resp as JSON
		//		log.debug "Response from DB: " + debg
		resp.setResponseDateTime(new Date())
		log.debug "WS2 END"
		return resp
	}
	@Transactional
	AktualizaciaFOResponse ws2Recall(Long reqId,Boolean goOut){
		log.debug "WS2RECALL START"
		
		AktualizaciaFOResponse resp = new AktualizaciaFOResponse()

		resp.setRequestDateTime(new Date())
		def sql = Sql.newInstance(dataSource)
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)

		RequestLog.withTransaction{status->
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WS2')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id

			log.debug "WS2 OVER params: "+reqId;
			SkupUdajovEnum[] skupUdaj
			try{
				sql.call("{call cr_intws.ws2_overRequest(?,?,?,?)}",[reqId , goOut?1:0, Sql.INTEGER, Sql.VARCHAR]){ retKod,retM ->
					def req=RequestLog.findById(reqId)
					req.refresh()
					def firstIfoRec = req.getParametre().find {it.paramnm==RequestParamCis.get("IF")}?.recno
					def rec = req.getParametre().findAll{it.recno==firstIfoRec}
					if(rec){
						skupUdaj=rec.findAll {it.paramnm.id=='SKU'}.collect {SkupUdajovEnum.keyOfvalue(Integer.valueOf(it.paramval))}
					}else{
						skupUdaj =[SkupUdajovEnum.rodne_cislo, SkupUdajovEnum.ident_udaje, SkupUdajovEnum.umrtie, SkupUdajovEnum.rodinne_vztahy, SkupUdajovEnum.adresa]
					}
					resp.aktualizaciaResponse= new AktualizaciaFOData(req,skupUdaj.asType(List))
				}
			}catch (Exception e) {
				resp.aktualizaciaResponse = new AktualizaciaFOData()
				resp.aktualizaciaResponse.setSystemSP(SystemCis.get(spSys))
				resp.aktualizaciaResponse.setUziv(aktualnyUzivatel.getUsername())
				resp.aktualizaciaResponse.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()

				resp.aktualizaciaResponse.setRespmsg(baseMsg.concat(e.message))
			}

		}
		
		// *** set requestLog.id into requestFile
		RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
		   it.setRequest(RequestLog.get(reqId))
		   it.save flush:true
	   }
		
		resp.setResponseDateTime(new Date())
		log.debug "WS2RECALL END"
		return resp
	}

	@Transactional
	ZaujemFOResponse ws4(String[] ifo,Long reqId){
		log.debug "WS4 START"
		ZaujemFOResponse resp = new ZaujemFOResponse ()
		resp.setRequestDateTime(new Date())
		def reqLogId = reqId // *** set requestLog.id into requestFile
		def sql = Sql.newInstance(dataSource)
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		//dostrani nulls
		if(ifo) ifo=ifo.grep()

		RequestLog.withTransaction{
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WS4')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id

			log.debug "WS4 params: ${ifo} ${reqId}";

			try{
				if(reqId){
					sql.call("{call cr_intws.ws4_over(?,?,?)}", [reqId , Sql.INTEGER, Sql.VARCHAR]) { retKod,retM ->
						def rqlog = RequestLog.findById(reqId)
						rqlog.refresh()
						resp.zaujemResponse= new ZaujemFOData()
					}
				}else{
					sql.call("{call cr_intws.ws4_zaujem(?,?,?,?,?)}",[spSys, ifo.toString().substring(1,ifo.toString().length()-1), Sql.BIGINT, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
						resp.zaujemResponse= new ZaujemFOData(RequestLog.findById(reqID))
						reqLogId = reqID
					}
				}
			}catch (Exception e) {
				resp.zaujemResponse = new ZaujemFOData()
				resp.zaujemResponse.setSystemSP(SystemCis.get(spSys))
				resp.zaujemResponse.setUziv(aktualnyUzivatel.getUsername())
				resp.zaujemResponse.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()

				resp.zaujemResponse.setRespmsg(baseMsg.concat(e.message))
			}
		}
		
		// *** set requestLog.id into requestFile
		if (reqLogId) {
			RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
			   it.setRequest(RequestLog.get(reqLogId))
			   it.save flush:true
		   }
		}
		
		log.debug "Response from DB: " + (resp as JSON)
		resp.setResponseDateTime(new Date())
		log.debug "WS4 END"
		return resp

	}
	
	NovoNarodeneDetiResponse ws6(Date zmenyOd){
		log.debug "WS6 START"
		
		NovoNarodeneDetiResponse resp = new NovoNarodeneDetiResponse ()

		resp.setRequestDateTime(new Date())
		def reqLogId  // *** set requestLog.id into requestFile
		def sql = Sql.newInstance(dataSource)
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)

		RequestLog.withTransaction{
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WS6')//logovanie v SQL
			def spSys = aktualnyUzivatel.getSystem()?.id

			String formatZmenyOd=zmenyOd?.format(configHolderService.getValueForKey('crfosp.wsparams.datetimeformat'))
			String param = String.valueOf("ot_rqparam(crreqpar_seq.nextval, 'DZM', '${formatZmenyOd?formatZmenyOd:''}', 0)")

			param = "ct_rqparam(${param})"
			try{
				sql.call("{call cr_intws.ws6_nnd(?,${param},?,?,?)}",[spSys, Sql.BIGINT, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retM ->
					resp.nndResponse = new NovoNarodeneDeti(RequestLog.findById(reqID))
					reqLogId = reqID // *** set requestLog.id into requestFile
				}

			}catch (Exception e) {
				resp.nndResponse = new NovoNarodeneDeti()
				resp.nndResponse.setSystemSP(SystemCis.get(spSys))
				resp.nndResponse.setUziv(aktualnyUzivatel.getUsername())
				resp.nndResponse.setRespkod( 9999 )
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()

				resp.nndResponse.setRespmsg(baseMsg.concat(e.message))
			}

		}
		
		// *** set requestLog.id into requestFile
		if (reqLogId) {
			try {
			RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
			   it.setRequest(RequestLog.get(reqLogId))
			   it.save flush:true
		   }
			}catch (Exception e) { 
				e.printStackTrace(); 
				}
		}
		
		log.debug "Response from DB: " + (resp.nndResponse as JSON)
		resp.setResponseDateTime(new Date())
		log.debug "WS6 END"
		return resp
	}
	
	CiselnikyResponse ws5(Date dtOd, Date dtDo, String[] ciss){
		log.debug "WS5 START"
		
		CiselnikyResponse resp = new CiselnikyResponse ()

		resp.setRequestDateTime(new Date())
		def reqLogId  // *** set requestLog.id into requestFile
		def sql = Sql.newInstance(dataSource)
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		def spSys = aktualnyUzivatel.getSystem()?.id
		
		RequestLog.withTransaction{
			encrypterService.authCrypter()
			encrypterService.setSqlSaveLog('WS5')//logovanie v SQL
			
			String formatDtOd=dtOd?.format(configHolderService.getValueForKey('crfosp.wsparams.datetimeformat'))
			String formatDtDo=dtDo?.format(configHolderService.getValueForKey('crfosp.wsparams.datetimeformat'))

			try{
				
				RequestLog instance = new RequestLog()
				instance.systemSP = aktualnyUzivatel.getSystem()
				instance.uziv = aktualnyUzivatel
				instance.wsin = "ws5"
				instance.reqtime = new Date()
				instance.stav = RequestStatusEnum.RESPONDED
				instance.statustime = new Date()
				instance.respkod = 0
				instance.respmsg = 'OK'
				RequestLog.withTransaction {status->
					try {
						instance.save flush:true
					} catch (Exception e) {
						status.setRollbackOnly()
						respond response:[status:-1,data:e.message]
						return
					}
				}
				reqLogId = instance.id
				
				resp.id=instance.id
				resp.systemSP=spSys
				resp.uziv=aktualnyUzivatel.name
				resp.stav=instance.stav
				resp.respkod=instance.respkod
				resp.respmsg=instance.respmsg
				
				if(ciss) ciss=ciss.grep()
				
				
				resp.cisResponse = new Ciselniky(dtOd, dtDo, ciss);
				
//				sql.call("{call cr_intws.ws5_cis(?,?,?,?)}",[spSys, Sql.BIGINT, Sql.INTEGER, Sql.VARCHAR]){ reqID,retKod,retMsg ->
//					resp.cisResponse = new Ciselniky(RequestLog.findById(reqID))
//					reqLogId = reqID // *** set requestLog.id into requestFile
//				}
////				spSys In Varchar2, reqId Out Number, retCode Out Number, retMsg Out Varchar2
			}catch (Exception e) {
				//resp.cisResponse = new Ciselniky()
				resp.setSystemSP(aktualnyUzivatel.getSystem().nazov)
				resp.setUziv(aktualnyUzivatel.getUsername())
				resp.setRespkod( 9999 )
				resp.stav = RequestStatusEnum.ERROR
				String baseMsg = KodOpovedeCis.findById(9999).getMsg()
				resp.setRespmsg(baseMsg.concat(e.message))
			}

		}

		// *** set requestLog.id into requestFile
		if (reqLogId) {
			try {
			RequestFiles.findByMsgIdAndDirestionAndRequestIsNull(logWSFilesService.getRequestId(),"request")?.each(){
			   it.setRequest(RequestLog.get(reqLogId))
			   it.save flush:true
		   }
			}catch (Exception e) { 
				e.printStackTrace(); 
				}
		}
				
		log.debug "Response from DB: " + (resp.cisResponse as JSON)
		resp.setResponseDateTime(new Date())
		log.debug "WS5 END"
		return resp
	}
	
	def GetSqlResult(String query){
		def sql = Sql.withInstance(dataSource)
		def rows = sql.rows(query)
		return rows
	}
}
