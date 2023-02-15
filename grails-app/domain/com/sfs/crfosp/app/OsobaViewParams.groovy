package com.sfs.crfosp.app

class OsobaViewParams{
	String crit
	Integer page
	Integer pgsize
	String sort
	String order

	static mapping = {
		table 'CRTMPWPAR'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		order column:'ORD'
	}
	static constraints = {
		crit nullable:true, maxSize:2000
		page nullable:true
		pgsize nullable:true
		sort nullable:true
		order nullable:true
	}
}

