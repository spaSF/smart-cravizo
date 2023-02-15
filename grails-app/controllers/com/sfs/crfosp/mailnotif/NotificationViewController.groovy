package com.sfs.crfosp.mailnotif

import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.dao.PessimisticLockingFailureException

@Transactional(readOnly = true)
class NotificationViewController {

//	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def responseSCService

	def index() {
		respond response:responseSCService.gridResponse(NotificationView.class,params)
	}

}
