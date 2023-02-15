package com.sfs.crfosp.ws.services

import grails.transaction.Transactional

import javax.servlet.http.HttpServletRequest

import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.web.context.request.RequestContextHolder

import com.sfs.crfosp.ws.RequestFiles
import com.sfs.crfosp.ws.RequestLog
import com.sfs.smartsfs.isc.SmartFile
import com.sfs.smartsfs.lazyLob.LazyBlob

@Transactional
class LogWSFilesService {

	def storeSmartFile(String msgId, String xmlStr, String msgType) {

		String title = StringUtils.substringBetween(xmlStr, "<USE_EXTWS>", "</USE_EXTWS>")
		if (!title) title = StringUtils.substringBetween(xmlStr, "<use_extws>", "</use_extws>")
		if (title) {
			title = title.replaceAll("\\s","");
			if (title == "true" || title == "false" || title == "1" || title == "0") {
				println title
			} else {
				throw new IllegalArgumentException("The String '${title}' did not match either specified value");
			}
		}

		def grailsApplication = new SmartFile().domainClass.grailsApplication
		def webRootDir = grailsApplication.mainContext.servletContext.getRealPath("/")
		def tmpDir = grailsApplication.config.smartsfs?.temp?.directory?:"/tmp"
		def realTempDir = new File(webRootDir, tmpDir)
		realTempDir.mkdirs()

		SmartFile smartFile
		if (msgType == "request") {
			smartFile = SmartFile.get(Long.valueOf(msgId))
		} else {
			smartFile = new SmartFile();
		}
		
		smartFile.filename = msgType + "_sp_" + msgId + ".xml"
		smartFile.filesize = xmlStr.length()
		smartFile.contentType = "text/xml"
		smartFile.storeLocal = false
		smartFile.fullpath = realTempDir.getPath().concat("\\${smartFile.filename}")
		smartFile.lBlob = new LazyBlob(xmlStr.getBytes("UTF-8"), smartFile); // file.getBytes()
		smartFile.save flush:true
		
		RequestFiles rqFiles = new RequestFiles();

		if (xmlStr.indexOf("DOTAZ_ID=\"") > 0) {
			def reqId = xmlStr.substring(xmlStr.indexOf("DOTAZ_ID=\"")+10);
			reqId = reqId.getAt(0..reqId.indexOf("\"")-1)
			if (reqId.isNumber()) {
				rqFiles.setRequest(RequestLog.get(reqId.toInteger()));
			}
		}
		
		rqFiles.smartfile = smartFile;
		rqFiles.direstion = msgType
		rqFiles.msgId = msgId
		rqFiles.save flush:true
		log.debug("rqFiles.save - id = " + rqFiles?.id + ", direstion = " + msgType + ", msgId = " + msgId)
	}

	def setRequestId(HttpServletRequest request, String logMsqId) {
		//String sessionId = RequestContextHolder.getRequestAttributes()?.getSessionId();
		//request.setAttribute("MSG_ID", sessionId + logMsqId);
		SmartFile smartFile = new SmartFile();
		smartFile.save flush:true
		request.setAttribute("MSG_ID", smartFile.id.toString());
	}

	def getRequestId() {
		def webUtils = WebUtils.retrieveGrailsWebRequest()
		def request = webUtils.getCurrentRequest()
		return request.getAttribute("MSG_ID")
	}

}
