package com.sfs.crfosp.mailnotif

import java.util.Date

import com.sfs.crfosp.mailnotif.enums.NotificationEnum
import com.sfs.crfosp.ws.RequestAvizo

class NotificationView {
	
	NotifEventEnum event
	String sender
	String recipients
	String subject
	String body

	NotificationEnum status
	Date notifSended
	String errm
	
	Long reqid
	Long avizoId
	
	RequestAvizo reqAvizo
	static mapping = {
		table 'av_wnotification'
		version true
		id generator:'assigned'

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
