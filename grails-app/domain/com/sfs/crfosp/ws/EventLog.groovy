package com.sfs.crfosp.ws

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

import com.sfs.crfosp.ws.enums.EventStatusEnum

@XmlAccessorType(XmlAccessType.NONE)
class EventLog implements Serializable{
	Long id
	Date recdate
	EventStatusEnum status
	Date statustime
	Integer respkod
	String respmsg
	String wskod
	Set<EventOsoba> osoby

	static hasMany = [osoby:EventOsoba,]
	static mappedBy = [osoby:"event"]

	static mapping = {
		table 'CREVENT'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		status column:'STATUS',sqlType:'varchar2',length:50
		wskod column:'WSKOD',sqlType:'varchar2',length:256
	}
	static constraints = {
		id nullable:false
		recdate nullable:false
		statustime nullable:false
		respkod nullable:true
		respmsg nullable:true, maxSize:512
	}
}
