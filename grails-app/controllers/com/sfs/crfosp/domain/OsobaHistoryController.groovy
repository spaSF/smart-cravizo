package com.sfs.crfosp.domain



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OsobaHistoryController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def responseSCService
	def encrypterService
	
	def index() {
		encrypterService.authCrypter()
		respond response:responseSCService.gridResponse(OsobaHistory.class,params)
	}

	@Transactional
	def save() {
		OsobaHistory instance = OsobaHistory.newInstance(params)
		//		instance.id = Long.valueOf(params.id) //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		OsobaHistory.withTransaction {status->
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
		OsobaHistory instance = OsobaHistory.lock(params.id)
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"osobaHistory.label"),
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

		OsobaHistory.withTransaction {status->
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
		OsobaHistory instance = OsobaHistory.lock(params.id)
		if (!instance) {
			respond response:[status:-4,data:message(code:"default.not.found.message",args:[
					message(code:"osobaHistory.label"),
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


		OsobaHistory.withTransaction {status->
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