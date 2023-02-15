import org.apache.log4j.DailyRollingFileAppender
import org.apache.log4j.PatternLayout
import grails.util.Holders
import java.lang.System

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = [
	'Gecko',
	'WebKit',
	'Presto',
	'Trident'
]
grails.mime.types = [ // the first one is the default format
	all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
	atom:          'application/atom+xml',
	css:           'text/css',
	csv:           'text/csv',
	form:          'application/x-www-form-urlencoded',
	html:          [
		'text/html',
		'application/xhtml+xml'
	],
	js:            'text/javascript',
	json:          [
		'application/json',
		'text/json'
	],
	multipartForm: 'multipart/form-data',
	rss:           'application/rss+xml',
	text:          'text/plain',
	hal:           [
		'application/hal+json',
		'application/hal+xml'
	],
	xml:           [
		'text/xml',
		'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = [
	'/images/*',
	'/css/*',
	'/js/*',
	// '/isomorphic/*',//neda sa tlacit cez print preview,
	'/plugins/*'
]
grails.resources.adhoc.includes = [
	'/images/**',
	'/css/**',
	'/js/**',
	'/isomorphic/**',
	'/plugins/**'
]
grails.resources.adhoc.excludes = ['/isomorphic/IDACall**','/isomorphic/HttpProxy**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside ${}
				scriptlet = 'html' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
		}
		// escapes all not-encoded output at final stage of outputting
		// filteringCodecForContentType.'text/html' = 'html'
	}
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
	development {
		grails.logging.jul.usebridge = true
	}
	production {
		grails.logging.jul.usebridge = false
		// TODO: grails.serverURL = "http://www.changeme.com"
	}
}

grails.databinding.dateFormats = [
	"yyyy-MM-dd'T'HH:mm:ss.S",
	"yyyy-MM-dd'T'HH:mm:ss'Z'",
	'yyyy-MM-dd HH:mm:ss.S',
	'yyyy-MM-dd HH:mm:ss',
	'dd.MM.yyyy HH:mm:ss',
	'dd.MM.yyyy',
	'yyyy-MM-dd'
]
// Added by the Smart SFS plugin>>>>>

//aby netrimoval
grails.databinding.convertEmptyStringsToNull=true
grails.databinding.trimStrings=false

grails.gorm.default.constraints = {'*'(nullable: true)}
smartsfs.supportedLocales = [
	new Locale('sk'),
	Locale.ENGLISH
]
smartsfs.defaultLocale = new Locale('sk');

// tomcat CATALINA_HOME variable
logfile.srvHome = System.getProperty("catalina.home")?System.getProperty("catalina.home"):".."
logfile.path="/logs/"

// Smart SFS plugin application resources:
environments {
	development {
		crfosp.ldap.host="ldap://alfa-dc1.internal.softforsolutions.com:389/"
		crfosp.ldap.userdn='CN=redmine,OU=System Users,OU=HQ,DC=internal,DC=softforsolutions,DC=com'
		crfosp.ldap.userpwd='Ticket.2014'
		crfosp.ldap.base='OU=TestSP,DC=internal,DC=softforsolutions,DC=com'
		//mailagent
		grails.mail.host="alfa-mail.internal.softforsolutions.com"
		grails.mail.port=587
		grails.mail.username="SFS\\adm_pro"
		grails.mail.password="Biznis.2006"
		grails.mail.default.from="adm_pro@softforsolutions.com"
	    grails.mail.props = ["mail.debug.auth":"true","mail.debug":"true","mail.smtp.ssl.enable":"false","mail.smtp.ssl.trust":"alfa-mail.internal.softforsolutions.com"
			, "mail.smtp.auth":"true","mail.transport.protocol":"smtp","mail.smtp.starttls.enable":"true"]

		//
		grails.converters.default.pretty.print = true
		log4j = {
			appenders {
				console name:"consout",
				layout: new PatternLayout("[%p] [%c{3}] %m%n")
			}
			error 'org.codehaus.groovy.grails.web.servlet',
				// controllers
				'org.codehaus.groovy.grails.web.pages',
				// GSP
				'org.codehaus.groovy.grails.web.sitemesh',
				// layouts
				'org.codehaus.groovy.grails.web.mapping.filter',
				// URL mapping
				'org.codehaus.groovy.grails.web.mapping',
				// URL mapping
				'org.codehaus.groovy.grails.commons',
				// core / classloading
				'org.codehaus.groovy.grails.plugins',
				// plugins
				'org.codehaus.groovy.grails.orm.hibernate',
				// hibernate integration
				'org.springframework',
				'org.hibernate',
				'net.sf.ehcache.hibernate',
				'org.grails.cxf'
			debug  'grails.app',
				// 'org.hibernate.SQL',
				'com.sfs.crfo.security',
				'grails.app.jobs',
				'grails.plugins.quartz.QuartzGrailsPlugin',
				'com.sfs.crfosp.mailnotif',
				'org.grails.cxf'
//								'org.springframework.security',
				//,'com.sfs.crfosp.soap.interceptor.LogInterceptor'
		}
		grails.resources.modules = {
			scapplication {
				resource url:'js/application.js'
				resource url:'js/scWsTest.js'
				resource url:'js/scOsobaHistory.js'
				resource url:'js/avizoDetail.js'
				resource url:'js/homeComp.js'
			}
		}
	}
	sfstest {
		//v grails command -Dgrails.env=sfstest war alebo v -Dgrails.env=sfstest run-app
		crfosp.ldap.host="ldap://alfa-dc1.internal.softforsolutions.com:389/"
		crfosp.ldap.userdn='CN=redmine,OU=System Users,OU=HQ,DC=internal,DC=softforsolutions,DC=com'
		crfosp.ldap.userpwd='Ticket.2014'
		crfosp.ldap.base='OU=TestSP,DC=internal,DC=softforsolutions,DC=com'
		//mailagent
		grails.mail.host="alfa-mail.internal.softforsolutions.com"
		grails.mail.port=587
		grails.mail.username="SFS\\adm_pro"
		grails.mail.password="Biznis.2006"
		grails.mail.default.from="adm_pro@softforsolutions.com"
		grails.mail.props = ["mail.debug.auth":"true","mail.debug":"true","mail.smtp.ssl.enable":"false","mail.smtp.ssl.trust":"alfa-mail.internal.softforsolutions.com"
			, "mail.smtp.auth":"true","mail.transport.protocol":"smtp","mail.smtp.starttls.enable":"true"]

		//
		grails.converters.default.pretty.print = true
		log4j = {
			appenders {
				appender new DailyRollingFileAppender(
						name: 'dailyAppender',
						datePattern: "'.'dd",  // See the API for all patterns.
						//fileName: "servers/nnp_ms1/logs/cravizo-test.log", #28856
						fileName: "${Holders.config.logfile.srvHome}${Holders.config.logfile.path}cravizo_test.log",
						layout: pattern(conversionPattern:'%d [%t] %-5p %c{2} %x - %m%n')
						)
				layout: new PatternLayout("[%p] [%c{3}] %m%n")
			}
			error  dailyAppender:['grails.app',
				'org.codehaus.groovy.grails.web.servlet',
				// controllers
				'org.codehaus.groovy.grails.web.pages',
				// GSP
				'org.codehaus.groovy.grails.web.sitemesh',
				// layouts
				'org.codehaus.groovy.grails.web.mapping.filter',
				// URL mapping
				'org.codehaus.groovy.grails.web.mapping',
				// URL mapping
				'org.codehaus.groovy.grails.commons',
				// core / classloading
				'org.codehaus.groovy.grails.plugins',
				// plugins
				'org.codehaus.groovy.grails.orm.hibernate',
				// hibernate integration
				'org.springframework',
				'org.hibernate',
				'net.sf.ehcache.hibernate',
				'org.grails.cxf']
			info dailyAppender:['grails.app']
			debug   dailyAppender:['grails.app',
				//'org.hibernate.SQL',
				'com.sfs.crfo.security',
				'grails.app.jobs',
				'grails.plugins.quartz.QuartzGrailsPlugin',
				'com.sfs.crfosp.mailnotif',
//								'org.springframework.security',
				'org.grails.cxf']
				//,'com.sfs.crfosp.soap.interceptor.LogInterceptor'
		}
		grails.resources.modules = {
			smartsfs{
				resource url: '/isomorphic/system/modules/ISC_Core.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_Foundation.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_Containers.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_Grids.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_Forms.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_DataBinding.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_Calendar.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_History.js', disposition: 'head'
				resource url: '/isomorphic/system/modules/ISC_RichTextEditor.js', disposition: 'head'
			}
			scapplication {
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scGlobal.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scRestDataSource.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scLogin.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scFormItems.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scTabset.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scUser.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scApplication.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scLocale.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scGrid.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scDetail.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scButtons.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'scCalendar.js']
				resource url: [plugin: 'smart-sfs', dir: 'js/min', file: 'smart-sfs.js']			
				resource url:'js/min/application.js'
				resource url:'js/min/scWsTest.js'
				resource url:'js/min/scOsobaHistory.js'
				resource url:'js/min/avizoDetail.js'
				resource url:'js/min/homeComp.js'
			}
		}
	}
	test {
		crfosp.ldap.host="ldap://sr-ba-wdc-p010.socpoist.sk:389/"
		crfosp.ldap.userdn='CN=ZIUP_AD_read,OU=ZIUP,OU=Aplikacni,OU=Users,OU=System,OU=Ustredie,DC=socpoist,DC=sk'
		crfosp.ldap.userpwd='SFSauth678'
		crfosp.ldap.base='DC=socpoist,DC=sk'
		//mailagent
		grails.mail.host="smtpsp.socpoist.sk"
		grails.mail.port=25
		grails.mail.username="meno"
		grails.mail.password="heslo"
		grails.mail.default.from="crfo_avizo@socpoist.sk"
		grails.mail.props = ["mail.debug.auth":"true","mail.smtp.ssl.enable":"false","mail.smtp.ssl.trust":"smtpsp.socpoist.sk"
			, "mail.smtp.auth":"true","mail.transport.protocol":"smtp","mail.smtp.starttls.enable":"true"]


		log4j = {
			appenders {
				appender new DailyRollingFileAppender(
						name: 'dailyAppender',
						datePattern: "'.'dd",  // See the API for all patterns.
						//fileName: "servers/rfosp_ms1/logs/cravizo.log", #28856
						fileName: "${Holders.config.logfile.srvHome}${Holders.config.logfile.path}cravizo_test.log",
						layout: pattern(conversionPattern:'%d [%t] %-5p %c{2} %x - %m%n')
						)
				layout: new PatternLayout("[%p] [%c{3}] %m%n")
			}

			error  dailyAppender:[
			'org.codehaus.groovy.grails.web.servlet',        // controllers
			'org.codehaus.groovy.grails.web.pages',          // GSP
			'org.codehaus.groovy.grails.web.sitemesh',       // layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping',        // URL mapping
			'org.codehaus.groovy.grails.commons',            // core / classloading
			'org.codehaus.groovy.grails.plugins',            // plugins
			'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate','grails.app','org.springframework.security']
			info dailyAppender:['grails.app']
			debug  dailyAppender:[
				'grails.app'] //,'org.springframework.security','org.hibernate.SQL'
		}
		grails.resources.modules = {
			scapplication {
				resource url:'js/min/application.js'
				resource url:'js/min/scWsTest.js'
				resource url:'js/min/scOsobaHistory.js'
				resource url:'js/min/avizoDetail.js'
				resource url:'js/min/homeComp.js'
			}
		}
	}
	production {
		log4j = {
			appenders {
				appender new DailyRollingFileAppender(
						name: 'dailyAppender',
						datePattern: "'.'dd",  // See the API for all patterns.
						fileName: "${Holders.config.logfile.srvHome}${Holders.config.logfile.path}cravizo.log",
						layout: pattern(conversionPattern:'%d [%t] %-5p %c{2} %x - %m%n')
						)
				layout: new PatternLayout("[%p] [%c{3}] %m%n")
			}

			error  dailyAppender:[
			'org.codehaus.groovy.grails.web.servlet',        // controllers
			'org.codehaus.groovy.grails.web.pages',          // GSP
			'org.codehaus.groovy.grails.web.sitemesh',       // layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping',        // URL mapping
			'org.codehaus.groovy.grails.commons',            // core / classloading
			'org.codehaus.groovy.grails.plugins',            // plugins
			'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate','grails.app','org.springframework.security']
			info dailyAppender:['grails.app']
			debug  dailyAppender:[
				'grails.app'] //,'org.springframework.security','org.hibernate.SQL'
		}

		grails.resources.modules = {
			scapplication {
				resource url:'js/min/application.js'
				resource url:'js/min/scWsTest.js'
				resource url:'js/min/scOsobaHistory.js'
				resource url:'js/min/avizoDetail.js'
				resource url:'js/min/homeComp.js'
			}
		}
	}
}

grails.plugin.springsecurity.useBasicAuth = true
grails.plugin.springsecurity.basic.realmName = "CRFO SP"
grails.plugin.springsecurity.filterChain.chainMap = [
	'/services/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
	'/**': 'JOINED_FILTERS,-basicAuthenticationFilter,-basicExceptionTranslationFilter'
]

grails.plugin.springsecurity.providerNames = [
	'daoAuthenticationProvider',
	'ldapAuthProvider',
	'rememberMeAuthenticationProvider',
	'anonymousAuthenticationProvider'
]

grails.plugin.springsecurity.ldap.context.managerDn = '${crfosp.ldap.userdn}'
grails.plugin.springsecurity.ldap.context.managerPassword = '${crfosp.ldap.userpwd}'
//grails.plugin.springsecurity.ldap.context.server = 'ldap://ldap.forumsys.com:389/' //http://www.forumsys.com/tutorials/integration-how-to/ldap/online-ldap-test-server/
grails.plugin.springsecurity.ldap.context.server = '${crfosp.ldap.host}' //SFS localny test

grails.plugin.springsecurity.ldap.authorities.ignorePartialResultException = true // typically needed for Active Directory
//grails.plugin.springsecurity.ldap.authenticator.dnPatterns="uid={0}" //'uid={0},CN=putwhatyoumust,O=putwhatyoumust'
grails.plugin.springsecurity.ldap.search.base = '${crfosp.ldap.base}' //'CN=putwhatyoumust,O=putwhatyoumust'
grails.plugin.springsecurity.ldap.search.filter="sAMAccountName={0}" //"uid={0}" //sAMAccountName={0}
grails.plugin.springsecurity.ldap.search.searchSubtree = true
grails.plugin.springsecurity.ldap.auth.hideUserNotFoundExceptions = false

// These are crucial for LDAP/DB configuration
grails.plugin.springsecurity.ldap.mapper.userDetailsClass='com.sfs.smartsfs.sec.ScUserDetails'
grails.plugin.springsecurity.ldap.authorities.retrieveGroupRoles = false
grails.plugin.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
grails.plugin.springsecurity.ldap.authorities.clean.dashes = true
grails.plugin.springsecurity.ldap.authorities.clean.uppercase = true

// Smart SFS springsecurity config:
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.successHandler.alwaysUseDefault = true
grails.plugin.springsecurity.logout.alwaysUseDefaultTargetUrl = true
grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false

grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.sfs.smartsfs.sec.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.sfs.smartsfs.sec.UserRole'
grails.plugin.springsecurity.authority.className = 'com.sfs.smartsfs.sec.Role'
grails.plugin.springsecurity.authority.groupAuthorityNameField = 'authorities'
grails.plugin.springsecurity.useRoleGroups = true
grails.plugin.springsecurity.requestMap.className = 'com.sfs.smartsfs.sec.RequestMap'
grails.plugin.springsecurity.securityConfigType = 'Requestmap'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                              ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/assets/**':                     ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll']]

//AUDIT CONFIG - audittrail doplni audit polia v entitach s anotaciou @gorm.AuditStamp
smartsfs{
		audittrail{
			// ** if field is not specified then it will default to 'createdBy'
			createdBy.field = 'createdBy'  // createdBy is default
			// ** fully qualified class name for the type
			createdBy.type   = 'java.lang.String' //Long is the default
			// ** the constraints settings
			createdBy.constraints = 'nullable:false,editable:false,bindable:false,maxSize:50'
			createdDate.field = 'dateCreated'
			createdDate.type  = 'java.util.Date'
			createdDate.constraints = 'nullable:false,editable:false,bindable:false'
			editedBy.field = 'updatedBy'
			editedBy.type = 'java.lang.String'
			editedBy.constraints = 'nullable:false,editable:false,bindable:false,maxSize:50'
			editedDate.field = 'lastUpdated'
			editedDate.type  = 'java.util.Date'
			editedDate.constraints = 'nullable:false,editable:false,bindable:false'
			//custom closure to return the current user who is logged in
			currentUserClosure = {ctx->
				//ctx is the applicationContext
				//default is basically
				def uname= ctx.springSecurityService.principal?.username
				return uname?:'admin'
			}
		}
}


