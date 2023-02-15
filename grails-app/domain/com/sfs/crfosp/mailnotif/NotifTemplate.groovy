package com.sfs.crfosp.mailnotif

import com.sun.org.apache.xalan.internal.xsltc.compiler.ValueOf

@com.sfs.smartsfs.audit.AuditStamp
class NotifTemplate {
	/**
	 * udalost notifikacie
	 */
	NotifEventEnum event
	/**
	 * template pre clazz
	 * pouzit v pripade viacurovnoveho template jednej udalosti
	 * napr. avizo + avizo atributy kolekcia
	 */
	String clazz
	/**
	 * prepinac body/subject template
	 */
	Boolean isBody = Boolean.TRUE
	/**
	 * poradie do templatu, posklada body
	 */
	Integer templatePart = Integer.valueOf(0)
	/**
	 * text body/subject notifikacie
	 * vkladane dynamicke hodnoty su properties objektu 
	 * vstupujuceho do metode vytvorenia notifikacie
	 */
	String templateText
	/**
	 * properties objektu pre template
	 * zoznam oddeleny ciarkou
	 * nahradza %# ako v message source
	 */
	String objectProps
	static mapping = {
		table 'SC_NOTIFTMP'
		version true
		id generator:'native',params:[sequence:'SC_NOTIFTMP_SEQ']

	}
    static constraints = {
		templateText maxSize:2000
		objectProps maxSize:2000
		isBody nullable:false
		templatePart nullable:false
    }
}
