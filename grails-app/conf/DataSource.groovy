dataSource {
	pooled = true
	jmxExport = true
	driverClassName = "org.h2.Driver"
	username = "sa"
	password = ""
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = false
	//cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
	cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
	singleSession = true // configure OSIV singleSession mode
	jdbc.use_get_generated_keys=true //VELICE DULEZITE INACE NEPOJDU SEQUENCRE
}

// environment specific settings
environments {
	hibernate { default_schema="CRFO" //aby sa neplietol s ZIUP
	}
	development {
		jasper.dir.reports = '/reports'
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
			pooled = true
			driverClassName = "oracle.jdbc.driver.OracleDriver"
			//dialect = "org.hibernate.dialect.Oracle10gDialect"
			dialect = "com.sfs.smartsfs.util.NowaitOracleDialect"
			//url = "jdbc:oracle:thin:@delta-jvpezu.internal.softforsolutions.com:1521:JVPEZU"
			url = "jdbc:oracle:thin:@10.100.0.45:1521:EZU"
			username = "crfo"
			password = "crfo"
			//logSql = true
		}
	}
	test {
		jasper.dir.reports = '/reports'
		hibernate { default_schema="CRFO" //aby sa neplietol s ZIUP
		}
		dataSource {
			dbCreate = ""
			dialect = "com.sfs.smartsfs.util.NowaitOracleDialect"
			jndiName = "java:comp/env/crfot"
			// jndiName = "CRFOT" #28856
		}
	}
	sfstest {
		jasper.dir.reports = '/reports'
		hibernate { default_schema="CRFO" //aby sa neplietol s ZIUP
		}
		dataSource {
			dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
			dialect = "com.sfs.smartsfs.util.NowaitOracleDialect"
			jndiName = "java:comp/env/crfot"
		}
	}
	production {
		jasper.dir.reports = '/reports'
		hibernate { default_schema="CRFO" //aby sa neplietol s ZIUP
		}
		dataSource {
			dbCreate = "validate"
			//dialect = "org.hibernate.dialect.Oracle10gDialect"
			dialect = "com.sfs.smartsfs.util.NowaitOracleDialect"
			jndiName = "CRFO"
		}
		//		dataSource {
		//			dbCreate = "validate" // one of 'create', 'create-drop', 'update', 'validate', ''
		//			pooled = true
		//			driverClassName = "oracle.jdbc.driver.OracleDriver"
		//			dialect = "org.hibernate.dialect.Oracle10gDialect"
		//			url = "jdbc:oracle:thin:@192.168.2.44:1521:JVPEZU"
		//			username = "crfo"
		//			password = "crfo"
		//			//logSql = true
		//		}
	}
}
