package com.sfs.crfosp.mailnotif

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional

import org.quartz.JobExecutionContext

import com.sfs.crfosp.mailnotif.enums.NotificationEnum
import com.sfs.crfosp.mailnotif.enums.NotificationException
import com.sfs.crfosp.security.Uzivatel
import com.sfs.crfosp.ws.RequestAvizo

class AvizoStatusJob {

	NotifService notifService
	def concurrent = false
	static defaultTriggers  = {
		cron    name: 'handleAvizoStatusTrigger', cronExpression: "0 0/5 * * * ?"
	}

	@Transactional(noRollbackFor=[NotificationException])
	void sendNotification(RequestAvizo ra){
		Notification notif
		SpringSecurityUtils.doWithAuth("adminaviz") {
		try{
			notif=notifService.sendNotification(notifService.addToNotifBody(NotifEventEnum.findById("AVSTATUS"), ra.avizo.avizoAtributy, 1, notifService.createNotif(NotifEventEnum.findById("AVSTATUS"), ra.avizo,Uzivatel.findByUsername(ra.avizo.createdBy)?.email)))
			log.debug "notif status ${notif.getStatus()}"
			ra.setNotifStatus(notif.getStatus())
			ra.save()
			notif.setReqAvizo(ra)
			if(notif.validate()){
				notif.save()
			}else{
				if (notif.hasErrors()) {
					notif.errors.allErrors.each {
						log.debug it
					}
				}
			}
		}catch(NotificationException e){
			ra.setRespmsg(ra.respmsg+";NOTIFEXC:${e.getMessage()}")
			ra.setNotifStatus(NotificationEnum.CHYBNA)
			ra.save(flush:true)
			log.debug "notif exc:${e.getMessage()}"
		}catch(Exception e){
			e.printStackTrace()
			log.debug "notif error:${e.getMessage()}"
		}
		}
	}
	
	void handleAvizoStatus(){
		//najdi aviza ktore prisli z MV v aktualizacii stavu vybavenosti
		//budu mat poznaceny status notifikacie=0( Cakajuca)
		List finder = RequestAvizo.findAllByNotifStatus(NotificationEnum.CAKAJUCA)
		log.debug "nasel neco ${finder.size()}"
		finder?.each{RequestAvizo ra->
			log.debug "zrob notifikaciu pre RA id = ${ra.id}"
			sendNotification(ra)
		}
	}

	def execute(JobExecutionContext context) {
		def triggerName = context.trigger.key
		log.debug "execute trigger ${triggerName.name}"
		if (triggerName.name == 'handleAvizoStatusTrigger') {
			handleAvizoStatus()
		}
	}

}
