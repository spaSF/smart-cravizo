package com.sfs.crfosp.ws



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import groovy.sql.Sql

import org.hibernate.criterion.Restrictions

@Transactional(readOnly = true)
class EventLogController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def dataSource
	def responseSCService
	def encrypterService

	def index() {
		encrypterService.authCrypter()
		respond response:responseSCService.gridResponse(EventLog.class,params)
	}

	@Transactional
	def osobaEventLog() {	
		respond response:responseSCService.gridResponse(EventLog.class,params)
//		def mparams = params
//		if (mparams['criteria']) {
//			def criteria = responseSCService.getAsList( mparams.criteria)
//			mparams.remove('criteria')
//			Integer osobaCrit = criteria.findIndexOf{responseSCService.parseAsJson(it).fieldName=="_osoba"}
//			def osobaid
//			if (osobaCrit != null){
//				osobaid = responseSCService.parseAsJson(criteria[osobaCrit])?.value
//				criteria.remove(osobaCrit)
//				mparams.put('criteria',criteria)
//				String restr = "{alias}.id in(select a.eventid from CREVOS a where a.crosoba=${osobaid})"
//				respond response:responseSCService.gridResponse(EventLog.class,mparams
//				,Restrictions.sqlRestriction(restr))
//			} else {
//				respond response:[status:-1,data:message(code:"default.not.found.message",args:[
//						message(code:"eventLog.label"),
//						null
//					])]
//			}
//		} else {
//			respond response:[status:0,data:[],startRow:0,endRow:0,totalRows:0]
////			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
////					message(code:"eventLog.label"),
////					null
////				])]
//		}
	}
		
	@Transactional
	def getEventFile() {
		def eventId = params.id
		def sql = Sql.newInstance(dataSource)
		def data
		try {
			sql.eachRow('select crfo_xml.xc2b2utf8(to_clob(a.user_data.text_vc)) as vcdata, crfo_xml.xc2b2utf8(a.user_data.text_lob) as lobdata from crfo_events_bkp a, crevent b where a.msgid = b.msgid and b.id = :eid',[eid:eventId]) { row ->
				log.debug row
				if (row.lobdata.length() > 0) data = row.lobdata?.binaryStream
				if (row.vcdata.length() > 0 && !data) data = row.vcdata?.binaryStream
				//data = row.lobdata?row.lobdata.binaryStream:row.vcdata?.row.vcdata.binaryStream
			}
			if (data) {
				render(file:data,fileName:"${eventId}.xml",contentType :"application/xml")
			}else{
				render view:"/smarterror",model: [exception:message(code:"eventLog.getFile.notFound.message",args:[
						message(code:eventId),
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
		EventLog instance = EventLog.newInstance(params)
		//		instance.id = Long.valueOf(params.id) //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		EventLog.withTransaction {status->
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
		EventLog instance = EventLog.lock(params.id)
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"eventLog.label"),
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

		EventLog.withTransaction {status->
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
		EventLog instance = EventLog.lock(params.id)
		if (!instance) {
			respond response:[status:-4,data:message(code:"default.not.found.message",args:[
					message(code:"eventLog.label"),
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


		EventLog.withTransaction {status->
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
