package com.sfs.crfosp.security

import com.sfs.crfosp.cis.SystemCis
import com.sfs.smartsfs.sec.User

/** Uzivatel potrebny len pri spusteni app
 *  Nike inde nie je potrebny
 *  Ide o diskriminator ak ma byt pre app iny na jednej scheme
 * @author SPa
 *
 */

class FakefUzivatel extends User{


	SystemCis system

	String _fullName
	
	static transients=["_fullName"]

	static constraints = {
	}

	static mapping = {
		discriminator "AppUser" //"AppUser" // "LdapUser"
		system column:'XSPSYS',index:"SC_USER_SPSYS_FK",fetch:"join",lazy:false
	}
	def afterLoad(){
		
		if(this.name) this._fullName = this.name+" "
		if(this.surname) this._fullName = this._fullName?(this._fullName + this.surname):this.surname
		
		if(!this._fullName) this._fullName = this.username
	}

}
