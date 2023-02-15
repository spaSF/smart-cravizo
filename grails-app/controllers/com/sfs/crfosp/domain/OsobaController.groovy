package com.sfs.crfosp.domain



import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

import com.sfs.crfosp.reports.dto.OsobaReport
import com.sfs.crfosp.security.UzivatelLog
import com.sfs.crfosp.security.Uzivatel

@Transactional(readOnly = true)
class OsobaController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def responseSCService
	def encrypterService
	def springSecurityService
	def jasperService

	def index() {
		// #27707
		def mpars = params
		encrypterService.authCrypter()
		if (params.id && SpringSecurityUtils.ifNotGranted("ROLE_SUPER")) {
			def user = (Uzivatel) springSecurityService.getCurrentUser()
			//def osoba = Osoba.get(params.id)
			def osoba = Long.valueOf(params.id)
			UzivatelLog uzivatelLog = UzivatelLog.findByOsobaAndUserAndCasGreaterThan(osoba,user,new Date().format("ddMMyyyy"))
			if (!uzivatelLog) {
				uzivatelLog	 = new UzivatelLog()
				uzivatelLog.cas = new Date()
				uzivatelLog.user = (Uzivatel) springSecurityService.getCurrentUser()
				uzivatelLog.osoba = osoba
				uzivatelLog.save flush:true
			}
		}
		respond response:responseSCService.gridResponse(Osoba.class,params)
	}

	@Transactional
	def report() {
		try {
			log.debug(params)
			def osoba  = Osoba.findById (params.id)
			def uzivatel = (Uzivatel) springSecurityService.getCurrentUser()
			def osobaReport =new OsobaReport(osoba, uzivatel)

			def reportPath = servletContext.getRealPath("/reports")
			JasperReportDef reportDef =  new JasperReportDef(fileFormat: JasperExportFormat.PDF_FORMAT, filePath: 'osoba_detail')
			def reportData = []
			reportData.add(osobaReport)
			reportDef.setReportData(reportData)
			reportDef.addParameter("REPORTS_DIR",reportPath)

			ByteArrayOutputStream  stream = jasperService.generateReport(reportDef)
			render(file:stream.toByteArray(),fileName:"osoba_${params.id}.pdf",contentType :"application/pdf")
		}catch(Exception exception){
			log.debug exception.printStackTrace()
			render view:"/smarterror",model: [exception: exception]
		}

		return false
	}

	@Transactional
	def save() {
		Osoba instance = Osoba.newInstance(params)
		//		instance.id = Long.valueOf(params.id) //ak je id assigned
		instance.validate()
		if (instance.hasErrors()) {
			respond response:[status:-4,data:instance,errors:responseSCService.handleValidationErrors(instance.errors)]
			return
		}
		Osoba.withTransaction {status->
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
		Osoba instance = Osoba.lock(params.id)
		if (!instance) {
			respond response:[status:-1,data:message(code:"default.not.found.message",args:[
					message(code:"osoba.label"),
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

		Osoba.withTransaction {status->
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
		Osoba instance = Osoba.lock(params.id)
		if (!instance) {
			respond response:[status:-4,data:message(code:"default.not.found.message",args:[
					message(code:"osoba.label"),
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


		Osoba.withTransaction {status->
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
