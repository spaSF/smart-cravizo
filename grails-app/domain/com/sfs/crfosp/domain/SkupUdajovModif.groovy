package com.sfs.crfosp.domain

import com.sfs.crfosp.cis.SkupUdajovCis


class SkupUdajovModif implements Serializable{
	Long id
	SkupUdajovCis skud
	Date dtzmeny
	
	static belongsTo =[osoba:Osoba]
	
	static mapping = {
		table 'CRZMSU'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		skud column:'SKUD',sqlType:'char',length:2,fetch:'join'
		osoba column:'CROSOBA'
	}
	static constraints = {
		id nullable:false
		osoba nullable:false
		skud nullable:false
		dtzmeny nullable:false
	}
}
