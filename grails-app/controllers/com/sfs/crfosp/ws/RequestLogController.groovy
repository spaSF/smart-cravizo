package com.sfs.crfosp.ws



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import groovy.sql.Sql

import org.hibernate.criterion.Restrictions

@Transactional(readOnly = true)
class RequestLogController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def dataSource
	def responseSCService
	def intWSService
	def encrypterService
	def excelExportHelperService
	
	def index() {
		encrypterService.authCrypter()
		respond response:responseSCService.gridResponse(RequestLog.class,params,Restrictions.sqlRestriction("wsin like 'wsa%'"))
	}

	@Transactional
	def osobaRequestLog() {
		def mparams = params
		log.debug(params)
		respond response:responseSCService.gridResponse(RequestLog.class,params)
	
//		if (mparams['criteria']) {
//			def criteria = responseSCService.getAsList( mparams.criteria)
//			mparams.remove('criteria')
//			Integer osobaCrit = criteria.findIndexOf{responseSCService.parseAsJson(it).fieldName=="_osoba"}
//			
//			def osobaid
//			if (osobaCrit != null){
//				osobaid = responseSCService.parseAsJson(criteria[osobaCrit])?.value
//				criteria.remove(osobaCrit)
//				mparams.put('criteria',criteria)
//				String restr = "{alias}.id in(select a.reqid from CRWSOS a where a.crosoba=${osobaid})"
//				respond response:responseSCService.gridResponse(RequestLog.class,mparams
//				,Restrictions.sqlRestriction(restr))
//			} else {
//				respond response:[status:-1,data:message(code:"default.not.found.message",args:[
//						message(code:"requestLog.label"),
//						null
//					])]
//			}
//		} else {
//			respond response:[status:0,data:[],startRow:0,endRow:0,totalRows:0]
////			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
////					message(code:"requestLog.label"),
////					null
////				])]
//		}
	}

	def osobaRequestLogXls() {

		def mparams = params
//		log.debug params	
//		def criteria = responseSCService.parseAsJson(mparams.filter)["criteria"]
//		Integer osobaCrit = criteria.findIndexOf{responseSCService.parseAsJson(it).fieldName=="osoby"}
//		criteria.remove(osobaCrit)
//		def mfilter = responseSCService.parseAsJson(params["filter"])
//		mparams.remove("filter")
//		mfilter.remove("criteria")
//		mfilter.put('criteria',criteria)
//		mparams["filter"] = mfilter
		try{
			def fname = "OsobaRequestLog_${(new Date()).format('yyyy-MM-dd_HH_mm_ss')}.xlsx"
			render(file:excelExportHelperService.defaultReport(mparams)?.toByteArray(),fileName:fname,contentType :"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
		} catch (Exception exception) {
			render view:"/smarterror",model: [exception: exception.toString().doRadecku()]
		}
		System.gc()	
	}
	
	@Transactional
	def ws1resendOut(){

		log.debug "ws1SENDOUT -- params:"+params
		if(params.id){
			encrypterService.authCrypter()
			RequestLog instance = RequestLog.lock(params.id)
			if(instance){
				def ret =  intWSService.ws1(null,null,null,null,null,null,instance.id,true)

				if(ret.overenieResponse.getRespkod()==9999){
					respond response:[status:-1,data:ret.overenieResponse.getRespmsg()]
				}else{
					respond response:[status:0,data:ret.overenieResponse]
				}
			}else{
				respond response:[status:-1,data:message(code:"default.not.found.message",args:[
						message(code:"requestLog.label"),
						params.id
					])]
			}
		}
	}
	@Transactional
	def wsResend(){
		log.debug "wsRESEND -- params:"+params
		if(params.id){
			encrypterService.authCrypter()
			RequestLog instance = RequestLog.lock(params.id)
			if(instance){
				if(instance.wsin=="ws1"){
					def ret =  intWSService.ws1(null,null,null,null,null,null,instance.id,false)
					if(ret.overenieResponse.getRespkod()==9999){
						respond response:[status:-1,data:ret.overenieResponse.getRespmsg()]
					}else{
						respond response:[status:0,data:ret.overenieResponse]
					}
				}else if(instance.wsin=="ws4"){
					def ret =  intWSService.ws4(null,instance.id)
					if(ret.zaujemResponse.getRespkod()==9999){
						respond response:[status:-1,data:ret.zaujemResponse.getRespmsg()]
					}else{
						respond response:[status:0,data:ret.zaujemResponse]
					}
				}
			}else{
				respond response:[status:-1,data:message(code:"default.not.found.message",args:[
						message(code:"requestLog.label"),
						params.id
					])]
			}
		}
	}
	
	@Transactional
	def getRequestFile() {
		def regId = params.id
		def sql = Sql.newInstance(dataSource)
		def data
		try {
			sql.eachRow('select crfo_xml.xc2b2utf8(to_clob(a.user_data.text_vc)) as vcdata, crfo_xml.xc2b2utf8(a.user_data.text_lob) as lobdata from crfo_reqtxt_bkp a where a.corrid = :rqid',[rqid:regId]) { row ->
				log.debug row
				if (row.lobdata.length() > 0) data = row.lobdata?.binaryStream
				if (row.vcdata.length() > 0 && !data) data = row.vcdata?.binaryStream
				//data = row.lobdata?row.lobdata.binaryStream:row.vcdata?.binaryStream
			}
			if (data) {
				render(file:data,fileName:"req_${regId}.xml",contentType :"application/xml")
			}else{
				render view:"/smarterror",model: [exception:message(code:"eventLog.getFile.notFound.message",args:[
						message(code:"req_${reqId}.xml"),
						params.id
					])]
			}
		}catch(Exception exception){
			log.debug exception.printStackTrace()
			render view:"/smarterror",model: [exception: exception]
		}
	}

	@Transactional
	def getResponseFile() {
		def resId = params.id
		def sql = Sql.newInstance(dataSource)
		def data
		try {
			sql.eachRow('select crfo_xml.xc2b2utf8(to_clob(a.user_data.text_vc)) as vcdata, crfo_xml.xc2b2utf8(a.user_data.text_lob) as lobdata from crfo_restxt_bkp a where a.corrid = :rsid',[rsid:resId]) { row ->
				log.debug row
				if (row.lobdata.length() > 0) data = row.lobdata?.binaryStream
				if (row.vcdata.length() > 0 && !data) data = row.vcdata?.binaryStream
				//data = row.lobdata?row.lobdata.binaryStream:row.vcdata?.binaryStream
			}
			if (data) {
				render(file:data,fileName:"res_${resId}.xml",contentType :"application/xml")
			}else{
				render view:"/smarterror",model: [exception:message(code:"eventLog.getFile.notFound.message",args:[
						message(code:"res_${resId}.xml"),
						params.id
					])]
			}
		}catch(Exception exception){
			log.debug exception.printStackTrace()
			render view:"/smarterror",model: [exception: exception]
		}
	}
		
	@Transactional
	def save() {
		RequestLog instance = RequestLog.newInstance(params)
		//		instance.id = Long.valueOf(params.id) //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		RequestLog.withTransaction {status->
			try {
				instance.save flush:true
			} catch (Exception e) {
				status.setRollbackOnly()
				respond response:[status:-1,data:e.message]
				return
			}
		}
		respond response:[status:0,data:instance]
	}

	@Transactional
	def update() {
		RequestLog instance = RequestLog.lock(params.id)
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"requestLog.label"),
					params.id
				])]
			return
		}

		def inAtt = params
		if(instance.version!=Long.valueOf(inAtt.version?:instance.version)){
			//zmenilo sa!
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[instance.id])]
			return
		}
		inAtt.remove("dateCreated")
		inAtt.remove("lastUpdated")
		instance.properties = inAtt
		instance.validate()

		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}

		RequestLog.withTransaction {status->
			try {
				instance.save flush:true
			} catch (Exception e) {
				status.setRollbackOnly()
				respond response:[status:-1,data:e.message]
				return
			}
		}
		respond response:[status:0,data:instance]
	}

	@Transactional
	def delete() {
		RequestLog instance = RequestLog.lock(params.id)
		if (!instance) {
			respond response:[status:-4,data:message(code:"default.not.found.message",args:[
					message(code:"requestLog.label"),
					params.id
				])]
			return
		}

		def inAtt = params
		if(instance.version!=Long.valueOf(inAtt.version?:instance.version)){
			//zmenilo sa!
			respond response:[status:-4,data:message(code:"default.optimistic.locking.failure",args:[instance.id])]
			return
		}


		RequestLog.withTransaction {status->
			try {
				instance.delete flush:true
			} catch (Exception e) {
				status.setRollbackOnly()
				respond response:[status:-1,data:e.message]
				return
			}
		}
		respond response:[status:0,data:instance]
	}

}
