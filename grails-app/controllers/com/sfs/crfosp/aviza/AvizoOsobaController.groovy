package com.sfs.crfosp.aviza



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.springframework.dao.PessimisticLockingFailureException

import com.sfs.crfosp.domain.Osoba
import com.sfs.crfosp.security.Uzivatel
import com.sfs.crfosp.ws.enums.AvizoStatusEnum
import com.sfs.crfosp.ws.enums.PovodEnum
import com.sfs.crfosp.ws.enums.VybavenieStatusEnum

@Transactional(readOnly = true)
class AvizoOsobaController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def responseSCService
	def fillAvizoOsobaService
	def springSecurityService
	def encrypterService
	
	def index() {
		encrypterService.authCrypter()
		respond response:responseSCService.gridResponse(AvizoOsoba.class,params)
	}
	
	@Transactional
	def createAvizo() {
		def mpars = params
		// kontrola ze ci uz existuje nejake zacate avizo
		encrypterService.authCrypter()
		def osoba = Osoba.get(params.id)
		Uzivatel aktualnyUzivatel = Uzivatel.findByUsername(springSecurityService.principal.username)
		Avizo avizoExist = Avizo.findByCreatedByAndOsobaAndStatus(aktualnyUzivatel.username, osoba, AvizoStatusEnum.EVIDOVANE)
		if (avizoExist) {
			respond response:[status:-1,data:message(code:"avizo.errors.exist",args:[avizoExist.avosoba.id.toString(),avizoExist.id.toString()])]
			return
		}
		Avizo avizo = new Avizo(povod:PovodEnum.N, status:AvizoStatusEnum.EVIDOVANE,lastStatus:VybavenieStatusEnum.NEPOTVRDENE)
		avizo.save(flush:true)
		AvizoOsoba avizoOsoba = new AvizoOsoba(avizo:avizo)
		avizoOsoba.osoba = osoba
		avizoOsoba.ifo = avizoOsoba.osoba.ifo
		fillAvizoOsobaService.setAvOsobaRelFromFrm(avizoOsoba)
		avizo.osoba = avizoOsoba.osoba
		avizo.avosoba = avizoOsoba
		avizo.save(flush:true)
		respond response:[status:0,data:avizoOsoba]
	}
	
	@Transactional
	def sendAvizo() {
		encrypterService.authCrypter()
		def avizoOsoba = AvizoOsoba.get(params.id)
		def retAvizo = fillAvizoOsobaService.sendAvizoOutFromFrm(avizoOsoba.avizo)
		if (retAvizo != 'OK') {
			respond response:[status:-1,data:message(code:"avizo.errors.send",args:[retAvizo])]
			return			
		}
		respond response:[status:0,data:avizoOsoba]
	}
	
	@Transactional
	def update() {
		encrypterService.authCrypter()
		AvizoOsoba instance
		try {
			instance = AvizoOsoba.lock(params.id)
		} catch (PessimisticLockingFailureException e) {
			e.printStackTrace()
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[params.id])]
			return
		}
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"avizoOsoba.label"),
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
		instance.avizo?.setPopis(inAtt.popis)
		
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}

		AvizoOsoba.withTransaction {status->
			try {
				instance.save flush:true	
				fillAvizoOsobaService.setAtributyFromFrm(instance)
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
	def save() {
		encrypterService.authCrypter()
		AvizoOsoba instance = AvizoOsoba.newInstance(params)
		//		instance.id = Long.valueOf(params.id) //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		AvizoOsoba.withTransaction {status->
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
	def update_bkp() {
		AvizoOsoba instance
		try {
			instance = AvizoOsoba.lock(params.id)
		} catch (PessimisticLockingFailureException e) {
			e.printStackTrace()
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[params.id])]
			return
		}
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"avizoOsoba.label"),
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

		AvizoOsoba.withTransaction {status->
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
		AvizoOsoba instance
		try {
			instance = AvizoOsoba.lock(params.id)
		} catch (PessimisticLockingFailureException e) {
			e.printStackTrace()
			respond response:[status:-1,data:message(code:"default.optimistic.locking.failure",args:[params.id])]
			return
		}
		if (!instance) {
			respond response:[status:-4,data:message(code:"default.not.found.message",args:[
					message(code:"avizoOsoba.label"),
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


		AvizoOsoba.withTransaction {status->
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
