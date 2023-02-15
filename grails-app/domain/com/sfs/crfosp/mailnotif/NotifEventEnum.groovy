package com.sfs.crfosp.mailnotif
/**
 * ciselnik udalosti pre notifikacie
 * sluzia ako konstanty pre spracovanie a rozlisenie templatov
 * @author MKR
 *
 */
@com.sfs.smartsfs.audit.AuditStamp
class NotifEventEnum {
	String id
	String description
	
	static mapping = {
		table 'SC_NOTIFEV'
		version true
		id generator:'assigned'
	}
	
    static constraints = {
		id maxSize:50
		description nullable:false,maxSize:255
    }
}
