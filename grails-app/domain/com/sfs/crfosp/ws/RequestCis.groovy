package com.sfs.crfosp.ws

import com.sfs.crfosp.cis.KodOpovedeCis
import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.ws.enums.CiselnikyEnum

class RequestCis {

	RequestLog request
	SystemCis spsys
	KodOpovedeCis stav
	
	Date dtOd
	Date dtDo
	String retmes
	
	static mapping = {
		table 'WREQWS5'
		version false
		request column: "ID"
		spsys column: "SPSYS"
		stav column: "STAV"
		dtDo column: "DTDO"
		dtOd column: "DTOD"
		retmes column: "RETMES"
	}
    static constraints = {
    }
}
