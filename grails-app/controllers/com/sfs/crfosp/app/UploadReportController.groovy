package com.sfs.crfosp.app

import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*

class UploadReportController {
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def responseSCService
	
	def upload(){
		log.debug params //file pride pod parametrom upload (UploadItem na formulari)
		//handle uploaded file
		try {
			def uploadedFile = request.getFile('uploadFile')
			if(!uploadedFile.empty){
				println "Class: ${uploadedFile.class}"
				println "Name: ${uploadedFile.name}"
				println "OriginalFileName: ${uploadedFile.originalFilename}"
				println "Size: ${uploadedFile.size}"
				println "ContentType: ${uploadedFile.contentType}"
				def webRootDir = servletContext.getRealPath("/")
				def userDir = new File(webRootDir, "/reports")
//				userDir.mkdirs() //zrob adresar ak neni
				File localFile=new File( userDir, uploadedFile.originalFilename) //zrob prazdny file
				uploadedFile.transferTo( localFile) //svac do lokalneho file
				
				render "{response:{status:0,data:''}}"
			}else{
				render "{response:{status:-4,data:${message(code:'report.imports.missing')} }}"
			}

		} catch (Exception exception) {
			render view:"/smarterror",model: [exception: exception.message]
		}
	}
	
	def uploadSource(){
		log.debug params //file pride pod parametrom upload (UploadItem na formulari)
		//handle uploaded file
		try {
			def uploadedFile = request.getFile('uploadFile')
			String targetDir = responseSCService.parseAsJson(params._dataUpload)?.targetDir
			if(!uploadedFile.empty){
				println "Class: ${uploadedFile.class}"
				println "Name: ${uploadedFile.name}"
				println "OriginalFileName: ${uploadedFile.originalFilename}"
				println "Size: ${uploadedFile.size}"
				println "ContentType: ${uploadedFile.contentType}"
				println "TargetDir: ${targetDir}"
				def webRootDir = servletContext.getRealPath("/")
				def userDir = new File(webRootDir, targetDir?:"/js")
//				userDir.mkdirs() //zrob adresar ak neni
				File localFile=new File( userDir, uploadedFile.originalFilename) //zrob prazdny file
				uploadedFile.transferTo( localFile) //svac do lokalneho file
				
				render "{response:{status:0,data:''}}"
			}else{
				render "{response:{status:-4,data:${message(code:'report.imports.missing')} }}"
			}

		} catch (Exception exception) {
			render view:"/smarterror",model: [exception: exception.message]
		}

	}
}
