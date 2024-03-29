grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 512, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 6644, minMemory: 1024, debug: false, maxPerm: 512, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 512, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 512]
]

grails.project.dependency.resolver = "maven" // or ivy
//vyhodim nepotrebne veci z WAR
grails.war.resources = { stagingDir ->
//	delete(dir: "${stagingDir}/isomorphic/system/modules-debug/" )
	delete(dir: "${stagingDir}/isomorphic/skins/Enterprise/" )
	delete(dir: "${stagingDir}/isomorphic/skins/Graphite/" )
	delete{fileset (dir: "${stagingDir}/tmp/")}
//	delete{fileset (dir: "${stagingDir}/reports/"){include(name: "*.jrxml")} }
//	delete{fileset (dir: "${stagingDir}/js/"){exclude(name: "min/**")} }
}
grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// specify dependency exclusions here; for example, uncomment this to disable ehcache:
		// excludes 'ehcache'
	}
	log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	checksums true // Whether to verify checksums on resolve
	legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

	repositories {
			
		mavenRepo "http://alfa-tcsfs:8080/artifactory/libs-release"
		mavenRepo ("http://alfa-tcsfs:8080/artifactory/libs-snapshot"){updatePolicy 'always'}

		inherits true // Whether to inherit repository definitions from plugins

		grailsPlugins()
		grailsHome()
		mavenLocal()
		grailsCentral()
		mavenCentral()
		// uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"
	}

	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
		// runtime 'mysql:mysql-connector-java:5.1.27'
		// runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'
        test "org.grails:grails-datastore-test-support:1.0-grails-2.3"
		
		runtime 'com.oracle:ojdbc7:12.1.0.2'
		runtime 'c3p0:c3p0:0.9.1.2'
	}

	plugins {
		compile ("com.sfs:smart-sfs:2.5.1-SNAPSHOT")
		// plugins for the build system only
		build ":tomcat:7.0.70"

		//WServices
		compile ":cxf:2.1.1"

		//PDF export templates
		compile ":jasper:1.11.0"


		// plugins for the compile step
		compile ":scaffolding:2.1.2"
		compile ':cache:1.1.8'

		// plugins needed at runtime but not for compilation
		runtime ":hibernate4:4.3.10"
		runtime ":database-migration:1.4.0"
		//        runtime ":jquery:1.11.1"
		runtime ":resources:1.2.14"
		
		//LDAP
		compile "org.grails.plugins:spring-security-ldap:2.0.1"
		
		//mail notifikacie
		compile "org.grails.plugins:mail:1.0.7"
		compile "com.sfs:quartz-sfs:1.0.1-SNAPSHOT"
		// Uncomment these (or add new ones) to enable additional resources capabilities
		//runtime ":zipped-resources:1.0.1"
		//runtime ":cached-resources:1.1"
		//runtime ":yui-minify-resources:0.1.5"

		// An alternative to the default resources plugin is the asset-pipeline plugin
		//compile ":asset-pipeline:1.6.1"

		// Uncomment these to enable additional asset-pipeline capabilities
		//compile ":sass-asset-pipeline:1.5.5"
		//compile ":less-asset-pipeline:1.5.3"
		//compile ":coffee-asset-pipeline:1.5.0"
		//compile ":handlebars-asset-pipeline:1.3.0.1"
	}
}
