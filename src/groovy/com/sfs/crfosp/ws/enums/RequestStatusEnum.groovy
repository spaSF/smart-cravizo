package com.sfs.crfosp.ws.enums

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

@XmlType(name = "ResponseStatus")
@XmlEnum
public enum RequestStatusEnum implements Serializable {
	OPEN,
	SENDEXT,
	RESPONDED,
	EXTIDLE,
	CLOSED,
	ERROR
	
//	String value
//	
//	RequestStatusEnum (String value){
//		this.value = value
//	}
}
