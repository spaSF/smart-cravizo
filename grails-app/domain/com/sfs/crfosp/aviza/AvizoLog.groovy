package com.sfs.crfosp.aviza

import java.io.Serializable

import com.sfs.crfosp.cis.AvizVybavCis

class AvizoLog implements Serializable{
	
	Avizo avizo
	
	Long code
	String msg
	
	static mapping = {
		table 'AV_LOG'
		version false
		id generator: 'sequence', params: [sequence: "AV_LOG_SEQ"], sqlType: 'NUMBER', length: 19
	}
	
    static constraints = {
		//id nullable:false
    }
}
