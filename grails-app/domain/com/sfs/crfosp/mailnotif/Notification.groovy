package com.sfs.crfosp.mailnotif

import com.sfs.crfosp.mailnotif.enums.NotificationEnum
import com.sfs.crfosp.ws.RequestAvizo

@com.sfs.smartsfs.audit.AuditStamp
class Notification {

	NotifEventEnum event
	String sender
	String recipients
	String subject
	String body

	NotificationEnum status
	Date notifSended
	String errm
	
	RequestAvizo reqAvizo
	
	
	void addToBodyText(String text){
		this.body=(this.body?:"").concat(text)
	}

	static mapping = {
		table 'SC_NOTIF'
		version true
		id generator:'native',params:[sequence:'SC_NOTIF_SEQ']

	}
	
	static constraints = {
		sender nullable:false,maxSize:100
		recipients nullable:true,maxSize:1000
		subject nullable:false,maxSize:1000
		body nullable:false,maxSize:4000
		status nullable:false
		notifSended nullable:true
		reqAvizo nullable:true
		errm nullable:true,maxSize:1000
	}
}
