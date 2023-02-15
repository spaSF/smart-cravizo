package com.sfs.crfosp.security



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.springframework.dao.PessimisticLockingFailureException

import com.sfs.crfosp.services.EncrypterService

@Transactional(readOnly = true)
class UzivatelLogViewController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def responseSCService
	EncrypterService encrypterService
	
	def index() {
		log.debug("UzivatelLogViewController params = " + params.toString())
		encrypterService.authCrypter()
		respond response:responseSCService.gridResponse(UzivatelLogView.class,params)
	}

}
