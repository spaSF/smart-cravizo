package com.sfs.crfosp.services

import grails.transaction.Transactional
import groovy.sql.Sql


@Transactional
class EncrypterService {
	def dataSource
	def springSecurityService
	def configHolderService

	def authCrypter() throws Exception{
		assert dataSource != null, "Datasource is null! No Good!!!"
		String result
		def sql = Sql.newInstance(dataSource)
		Date dbSysdate
		sql.eachRow('select sysdate from dual') { row ->
			dbSysdate = row.sysdate
		}
		String ck = dbSysdate.format("yyddHHMMssmm")
		sql.call("{call cr_crypter.auth(?,?)}",[springSecurityService.principal.username, ck.toLong()])
		log.debug ck
	}

	def setSqlSaveLog(String proces){
		int dbgLevel = Integer.valueOf(configHolderService.getValueForKey("crfosp.dblog.enabled")?:"9")
		if(dbgLevel>0){
			assert dataSource != null, "Datasource is null! No Good!!!"
			def sql = Sql.newInstance(dataSource)
			int rewriteLog = Integer.valueOf(configHolderService.getValueForKey("crfosp.dblog.rewrite")?:"1")
			sql.call("{call crfo_output.prtshowlevel(?,?,?)}",[dbgLevel, proces, rewriteLog])
		}
	}

	def encryptValue(String valToEnc) {
		assert dataSource != null, "Datasource is null! No Good!!!"
		String result
		def sql = Sql.newInstance(dataSource)
		//identity
		//		println('USER:'+springSecurityService.principal.username)
		Date dbSysdate
		sql.eachRow('select sysdate from dual') { row ->
			//			log.debug " select SYSDATE: ${row.sysdate}"
			dbSysdate = row.sysdate
		}
		String ck = dbSysdate.format("yyddHHMMssmm")
		//		log.debug "kontrolny sucet: ${ck}"
		def ret = sql.call("{call cr_crypter.auth(?,?)}",[springSecurityService.principal.username, ck.toLong()])
		//encrypt
		try {
			sql.call("{? = call cr_crypter.getEncryptedValue(?,'ROLE_WSREAD')}", [Sql.VARCHAR , valToEnc]) { enc -> result = enc }
		} catch (Exception e) {
			e.printStackTrace()
		}
		return result
	}


}
