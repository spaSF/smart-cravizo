package com.sfs.crfosp.security

import com.sfs.crfosp.domain.Osoba

// #27707
class UzivatelLog {

	Date cas
	Uzivatel user
	Long osoba
	
	static mapping = {
		table 'CRUSRLOG'
		id generator:'sequence',params:[sequence:'CRZUSRLOG_SEQ']
		osoba column:'OSOBA_ID',sqlType:'NUMBER',length:19
	}
	
    static constraints = {
		user nullable:false
		cas nullable:false
    }
}
