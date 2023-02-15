package com.sfs.crfosp.ws

import com.sfs.crfosp.cis.KodOpovedeCis
import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.domain.Osoba

// create or replace view wcrrequest_nnd as
// SELECT a.*, b.rc, b.menocele, b.priezcele FROM crrequest_nnd a, SC_WOSOBA b where b.id(+) = a.crosoba and a.stav is not null

class RequestNND  implements Serializable{
	
	RequestLog request
	Osoba osoba
	SystemCis spsys
	KodOpovedeCis stav
	
	String ifo
	Date cas	
	String retmes
	Boolean zaujem
	String rc
	Date dtnar
	String menocele
	String priezcele
	
	static mapping = {
		table 'WCRREQUEST_NND'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		request column:'REQNO'
		osoba column:'CROSOBA',lazy:false
		spsys column:'SPSYS',sqlType:'varchar2',length:10,fetch:'join'
		stav column:'STAV'
		ifo column:'IFO',sqlType:'char',length:32
		rc column:'RC',sqlType:'char',length:10
	}
	static constraints = {
	}
}
