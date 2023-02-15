package com.sfs.crfosp.security



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional

import org.hibernate.criterion.Restrictions

import com.sfs.smartsfs.sec.UserController
import com.sfs.smartsfs.sec.UserRole
import com.sfs.smartsfs.sec.UserRoleGroup

@Transactional(readOnly = true)
class UzivatelController extends UserController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		def hideAdmin=new Boolean(configHolderService.getValueForKey('smartsfs.nadm'))?:false

		if(hideAdmin==true&&SpringSecurityUtils.ifNotGranted("ROLE_SUPER")){
			def restr = "{alias}.id not in(select r.user_id from SC_ROLE a,SC_USER_ROLE r where a.authority='ROLE_SUPER' and r.role_id=a.id)"
			respond response:responseSCService.gridResponse(Uzivatel.class,params,Restrictions.sqlRestriction(restr))
		}else{
			respond response:responseSCService.gridResponse(Uzivatel.class,params)
		}
	}
	
	@Transactional
	def save() {
		def inAtt = params
		if(inAtt?.orgId) inAtt.orgId = String.valueOf(intAtt.orgId)
		if(inAtt?.orgUnitId) inAtt.orgUnitId = String.valueOf(intAtt.orgUnitId)

		Uzivatel instance = Uzivatel.newInstance(inAtt)
		instance.password = inAtt.password
		//		instance.id = Long.valueOf(params.id) //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		Uzivatel.withTransaction {status->
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
		Uzivatel instance = Uzivatel.lock(params.id)
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"user.label"),
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
		inAtt.remove("password")//iba cez zmenu hesla

		if(inAtt?.orgId) inAtt.orgId = String.valueOf(inAtt.orgId)
		if(inAtt?.orgUnitId) inAtt.orgUnitId = String.valueOf(inAtt.orgUnitId)
		instance.properties = inAtt
		instance.validate()

		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}

		Uzivatel.withTransaction {status->
			try {
				instance.save flush:true
				if (super.springSecurityService.loggedIn &&
				super.springSecurityService.principal.username == instance.username) {
					super.springSecurityService.reauthenticate instance.username
				}
			} catch (Exception e) {
				e.printStackTrace()
				status.setRollbackOnly()
				respond response:[status:-1,data:e.message]
				return
			}
		}
		respond response:[status:0,data:instance]
	}

	@Transactional
	def delete(){
		if(SpringSecurityUtils.ifNotGranted("ROLE_ADMIN")){
			respond response:[status:-1,data: message(code:"springSecurity.denied.message")]
			return
		}

		Uzivatel instance = Uzivatel.lock(params.id)
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"user.label"),
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
		Uzivatel.withTransaction {status->
			try {
				UserRoleGroup.removeAll(instance)
				UserRole.removeAll(instance)
				instance.delete flush:true
			} catch (Exception e) {
				e.printStackTrace()
				status.setRollbackOnly()
				respond response:[status:-1,data:e.message]
				return
			}
		}
		respond response:[status:0,data:instance]
	}

}
