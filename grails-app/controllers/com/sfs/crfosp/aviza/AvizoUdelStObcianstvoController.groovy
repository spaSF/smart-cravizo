package com.sfs.crfosp.aviza



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.springframework.dao.PessimisticLockingFailureException

import com.sfs.crfosp.services.FillAvizoOsobaService

@Transactional(readOnly = true)
class AvizoUdelStObcianstvoController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def responseSCService
	FillAvizoOsobaService fillAvizoOsobaService
	
	def index() {
		respond response:responseSCService.gridResponse(AvizoUdelStObcianstvo.class,params)
	}

	@Transactional
	def save() {
		AvizoUdelStObcianstvo instance = AvizoUdelStObcianstvo.newInstance(params)
		//		instance.id = Long.valueOf(params.id) //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		AvizoUdelStObcianstvo.withTransaction {status->
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
		AvizoUdelStObcianstvo instance
		try {
			instance = AvizoUdelStObcianstvo.lock(params.id)
		} catch (PessimisticLockingFailureException e) {
			e.printStackTrace()
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[params.id])]
			return
		}
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"avizoUdelStObcianstvo.label"),
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

		AvizoUdelStObcianstvo.withTransaction {status->
			try {
				instance.save flush:true
				fillAvizoOsobaService.setAtributyRelFromFrm(instance.osoba, instance.class.getSimpleName())
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
		AvizoUdelStObcianstvo instance
		try {
			instance = AvizoUdelStObcianstvo.lock(params.id)
		} catch (PessimisticLockingFailureException e) {
			e.printStackTrace()
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[params.id])]
			return
		}
		if (!instance) {
			respond response:[status:-4,data:message(code:"default.not.found.message",args:[
					message(code:"avizoUdelStObcianstvo.label"),
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


		AvizoUdelStObcianstvo.withTransaction {status->
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
