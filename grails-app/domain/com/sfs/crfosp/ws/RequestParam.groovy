package com.sfs.crfosp.ws

import com.sfs.crfosp.cis.RequestParamCis;

class RequestParam{
	Long id
	RequestLog request
	Integer recno
	RequestParamCis paramnm
	String paramval
	Boolean ext

	static mapping = {
		table 'CRREQPAR'
		version false
		id generator:'sequence',params:[sequence:'CRREQPAR_SEQ']
		paramnm column:'PARAMNM'
		ext sqlType:'NUMBER'
		request column:'REQID'
	}
	static constraints = {
		request nullable:false
		recno nullable:false
		paramnm nullable:false, maxSize:50
		paramval nullable:false, maxSize:255
		ext nullable:false
	}
}

