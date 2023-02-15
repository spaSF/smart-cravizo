package com.sfs.crfosp.mailnotif.enums

import java.io.Serializable

public enum NotificationEnum implements Serializable {
	CAKAJUCA(0),
	EVIDOVANA(1),
	ODOSLANA(2),
	IGNOROVANA(8),
	CHYBNA(9)
	
	Integer id
	
	NotificationEnum(Integer id) { this.id = id }
	
	String getKey() { name() }
}
