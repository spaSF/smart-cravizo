package com.sfs.crfosp.ws

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.mailnotif.Notification
import com.sfs.crfosp.mailnotif.enums.NotificationEnum

class RequestAvizo {
	RequestLog request
	Avizo avizo
	Integer respkod
	String respmsg

	//notifikacia ak ide o request na MV 412
	NotificationEnum notifStatus

	static hasOne =[notification:Notification]
	
	static shortFields =["notifStatus","respkod","respmsg"]

	static mapping = {
		table 'AV_REQAVIZO'
		version false
		id generator:'sequence',params:[sequence:"AV_REQAVIZO_SEQ"]
		request column:'REQID'
		respkod column:'RESPKOD'
		respmsg column:'RESPMSG'
	}
	static constraints = {
		id nullable:false
		request nullable:false
		avizo nullable:false,lazy:false
		respkod nullable:true,max:99999
		respmsg nullable:true,maxSize:4000
		notifStatus nullable:true
		notification nullable:true

	}
}
