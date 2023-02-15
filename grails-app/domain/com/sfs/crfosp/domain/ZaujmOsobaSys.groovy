package com.sfs.crfosp.domain

import com.sfs.crfosp.cis.SystemCis


class ZaujmOsobaSys implements Serializable{
	Long id
	SystemCis spsys
	Date dtod
	Date dtdo

	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRSYSZO'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		spsys column:'SPSYS',sqlType:'varchar2',length:10,fetch:'join'
		osoba column:'CROSOBA'
	}
	static constraints = {
		id nullable:false
		spsys nullable:false
		osoba nullable:false
		dtod nullable:false
		dtdo nullable:false
	}
}
