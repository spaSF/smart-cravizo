import com.sfs.crfosp.sec.AvizoUserDetailsService
import com.sfs.crfosp.sec.LdapUserDetailsContextMapper
import com.sfs.crfosp.soap.interceptor.LogInterceptor
import com.sfs.crfosp.soap.interceptor.PrettyLoggingFeature
// Place your Spring DSL code here
beans = {
	loggingInOutInterceptor(com.sfs.crfosp.soap.interceptor.LogInterceptor){
		logWSFilesService = ref('logWSFilesService')
	}
	prettyLoggingFeature(com.sfs.crfosp.soap.interceptor.PrettyLoggingFeature){}
	
	ldapUserDetailsMapper(LdapUserDetailsContextMapper){
		userDetailsService = ref('userDetailsService')
		configHolderService= ref('configHolderService')
	}
	userDetailsService(AvizoUserDetailsService){
		springSecurityService = ref('springSecurityService')
	}
}
