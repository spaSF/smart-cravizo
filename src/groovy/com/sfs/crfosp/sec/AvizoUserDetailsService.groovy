package com.sfs.crfosp.sec

import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import grails.transaction.Transactional

import org.apache.commons.logging.LogFactory
import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

import com.sfs.crfosp.security.Uzivatel
import com.sfs.smartsfs.sec.ScUserDetails
import com.sfs.smartsfs.sec.User

@Transactional
class AvizoUserDetailsService extends GormUserDetailsService {
	def springSecurityService //inject grails SpringSecurityService pre pristup k principal
	private static final log = LogFactory.getLog(this)

	@Override
	protected Collection<GrantedAuthority> loadAuthorities(user, String username, boolean loadRoles) {
		if (!loadRoles) {
			return []
		}
		def role=user.getAllAuthorities().collect { new SimpleGrantedAuthority(it.authority) }
		return role
	}

	@Override
	public UserDetails loadUserByUsername(String username, boolean loadRoles)
	throws UsernameNotFoundException {
		log.debug LCH.getLocale()
		Uzivatel.withTransaction { status ->
			def user = username=="admin"?User.findByUsername(username):Uzivatel.findByUsername(username)
			if (!user) {
				log.warn "User not found: $username"
				throw new NoStackUsernameNotFoundException()
			}
			Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
			return new ScUserDetails(user.username, user.password, user.enabled, !user.accountExpired,
					!user.passwordExpired, !user.accountLocked, authorities, user.id,user.name,user.surname,user.email,user.lang?.getId(),user.country?.getId()
					,false,"")
		}
	}
}
