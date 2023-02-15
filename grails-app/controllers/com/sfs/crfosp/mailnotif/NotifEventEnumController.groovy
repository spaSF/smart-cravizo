package com.sfs.crfosp.mailnotif

import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.dao.PessimisticLockingFailureException

@Transactional(readOnly = true)
class NotifEventEnumController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def responseSCService

	def index() {
		respond response:responseSCService.gridResponse(NotifEventEnum.class,params)
	}

	@Transactional
	def save() {
		NotifEventEnum instance = NotifEventEnum.newInstance(params)
		instance.id = params.id //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		NotifEventEnum.withTransaction {status->
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
		NotifEventEnum instance
		try {
			instance = NotifEventEnum.lock(params.id)
		} catch (PessimisticLockingFailureException e) {
			e.printStackTrace()
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[params.id])]
			return
		}
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"notifEventEnum.label"),
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
		instance.properties = inAtt
		instance.validate()

		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}

		NotifEventEnum.withTransaction {status->
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
		NotifEventEnum instance
		try {
			instance = NotifEventEnum.lock(params.id)
		} catch (PessimisticLockingFailureException e) {
			e.printStackTrace()
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[params.id])]
			return
		}
		if (!instance) {
			respond response:[status:-4,data:message(code:"default.not.found.message",args:[
					message(code:"notifEventEnum.label"),
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


		NotifEventEnum.withTransaction {status->
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
