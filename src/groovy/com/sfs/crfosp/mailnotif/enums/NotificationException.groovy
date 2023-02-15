package com.sfs.crfosp.mailnotif.enums

import org.codehaus.groovy.grails.exceptions.GrailsRuntimeException

class NotificationException extends GrailsRuntimeException {

	public NotificationException(String message) {
		super(message);
	}

	public NotificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificationException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return "NotificationEXC:"+super.toString();
	}

}
