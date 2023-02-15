package com.sfs.crfosp.security

import com.sfs.smartsfs.sec.RoleGroup;
/**
 * mapovanie LDAP skupin na app Role Groups
 * @author mkr
 *
 */
@com.sfs.smartsfs.audit.AuditStamp
class LdapGroup {

	RoleGroup roleGroup
	String ldapGroupKey
	
	static mapping = {
		table "CR_LDAP_GROUP"
		id generator:'sequence',params:[sequence:'CR_LDAP_GROUP_SEQ']
		roleGroup column:"XROLE_GROUP"
		ldapGroupKey length:50
	}
	
    static constraints = {
		roleGroup nullable:false
		ldapGroupKey blank:false,nullable:false,maxSize:50
    }
}
