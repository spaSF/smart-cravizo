package com.sfs.crfosp.sec

import grails.transaction.Transactional

import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper

import com.sfs.crfosp.security.LdapGroup
import com.sfs.crfosp.security.Uzivatel
import com.sfs.smartsfs.sec.RoleGroup
import com.sfs.smartsfs.sec.UserRoleGroup

@Transactional
class LdapUserDetailsContextMapper implements UserDetailsContextMapper{

	def userDetailsService
	def configHolderService

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection authorities) {
		println "${username.toUpperCase()}" //#17684 uppercase username
		println ctx.getDn()?.getRdns()
		String mailAttName = configHolderService.getValueForKey("crfosp.ldap.mailAttName")?:"mail"
		String pobAttName = configHolderService.getValueForKey("crfosp.ldap.pobAttName")?:"spOfficeLocationSPZ1"
		String memberAttName = configHolderService.getValueForKey("crfosp.ldap.memberAttName")?:"memberOf"
		def memb = ctx.getObjectAttributes(memberAttName)
		List<RoleGroup> roleGroups = new ArrayList<RoleGroup>()
		memb.each {m->
			def ld = LdapGroup.findAll().find{m.toString().contains(it.ldapGroupKey)}
			if(ld) roleGroups<<ld.roleGroup
		}
		ctx.getDn()?.getRdns().each{println it.getType()+" "+it.getValue()}
		def dnuser=ctx.getDn()?.getRdns().find{it.getType().toLowerCase()=="cn"}?.getValue()
		def pobocka = ctx.getObjectAttribute(pobAttName)
		def email = ctx.getObjectAttribute(mailAttName)
		Uzivatel user = Uzivatel.findByUsername(username.toUpperCase())
		if(user){
			//check group
			user.getAuthorities()?.each {RoleGroup gr->
				if(!roleGroups.find {it==gr}){
					UserRoleGroup.remove(user,gr,true)
				}
			}
			if(roleGroups) {
				roleGroups.each {gr->
					UserRoleGroup.create(user, gr, true)
				}
			}
		}
		if(!user&&roleGroups){
			Uzivatel.withTransaction { status ->
				//vyrob usera ked presiel cez LDAP
				def newUser = new Uzivatel(username: username.toUpperCase(), lang:'sk')
				newUser.password = 'sekretheslo@@$$$.'
				if(dnuser){
					def names=dnuser.split(" ")
					if(names.size()>1){
						newUser.setName(names[0])
						newUser.setSurname(names[1])
					}
				}
				if(email){
					newUser.setEmail(email.toString())
				}
				newUser.save(flush:true)
				roleGroups.each {gr->
					UserRoleGroup.create(newUser, gr, true)
				}
			}
		}
		return userDetailsService.loadUserByUsername(username.toUpperCase(),true)
	}

	@Override
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
		throw new IllegalStateException("Only retrieving data from AD is currently supported")
	}
}
