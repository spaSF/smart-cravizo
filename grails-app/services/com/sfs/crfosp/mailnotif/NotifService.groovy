package com.sfs.crfosp.mailnotif

import java.text.MessageFormat

import grails.plugin.mail.MailService
import grails.transaction.Transactional

import com.sfs.crfosp.mailnotif.enums.NotificationEnum
import com.sfs.crfosp.mailnotif.enums.NotificationException
import com.sfs.smartsfs.app.util.ConfigHolderService

@Transactional
class NotifService {

	ConfigHolderService configHolderService
	MailService mailService

	def getPropValue(def notifObject,String prop){
		if(prop.contains('.')){
			String prop1=prop.substring(0, prop.indexOf('.'))
			String prop2=prop.substring(prop.indexOf('.')+1)
			getPropValue(notifObject."${prop1}",prop2)
		}else{
			return String.valueOf(notifObject."${prop}")
		}
	}
	
	String getTextFromTemplate(NotifTemplate temp, def notifObject){
		def args = []
		temp.getObjectProps().split(",")?.eachWithIndex{prop,idx ->
			args<<getPropValue(notifObject,prop)
		}
		return MessageFormat.format(temp.getTemplateText(),args.toArray())
	}
	@Transactional(noRollbackFor=[NotificationException])
	Notification createNotif(NotifEventEnum evnt, def notifObject, String recipient) throws NotificationException{

		Notification notif = new Notification(event: evnt, status: NotificationEnum.EVIDOVANA)
		NotifTemplate subjTempl = NotifTemplate.findWhere(event:evnt,isBody:false,templatePart:0)
		notif.setSubject(getTextFromTemplate(subjTempl,notifObject))

		NotifTemplate bodyTempl = NotifTemplate.findWhere(event:evnt,isBody:true,templatePart:0)
		notif.setBody(getTextFromTemplate(bodyTempl,notifObject))

		notif.setSender(configHolderService.getValueForKey("grails.mail.default.from"))
		if(recipient){
			notif.setRecipients(recipient)
		}else{
			throw new NotificationException("Nie je mozne urcit adresata notifikacie")
		}
		return notif
	}


	Notification addToNotifBody(NotifEventEnum evnt, def notifObject, Integer part, Notification notif) {
		
		if(notifObject in Collection){
			notifObject.each{
				addToNotifBody(evnt,it,part,notif)
			}
		}else{
			NotifTemplate bodyTempl = NotifTemplate.findWhere(event:evnt,isBody:true,templatePart:part)
			notif.addToBodyText(getTextFromTemplate(bodyTempl,notifObject))
		}
		return notif
	}

	@Transactional(noRollbackFor=[NotificationException])
	Notification sendNotification(Notification notif)throws NotificationException{
		try{
			mailService.sendMail{
				from notif.getSender()
				to notif.getRecipients()
				subject notif.getSubject()
				html notif.getBody()
			}
			notif.setNotifSended(new Date())
			notif.setStatus(NotificationEnum.ODOSLANA)
		}catch(Exception e){
			throw new NotificationException(e.getLocalizedMessage()?:e.getMessage())
		}
		return notif
	}
}
