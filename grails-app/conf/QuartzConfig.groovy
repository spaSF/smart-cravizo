quartz {
	autoStartup = true
	jdbcStore = true
	waitForJobsToCompleteOnShutdown = true
	//exposeSchedulerInRepository = false
	props {
		scheduler.skipUpdateCheck = true
		scheduler.instanceName = 'quartzScheduler'
		scheduler.instanceId = 'AUTO'
		scheduler.idleWaitTime = 1000

		threadPool.'class' = 'org.quartz.simpl.SimpleThreadPool'
		threadPool.threadCount = 10
		threadPool.threadPriority = 7

		jobStore.misfireThreshold = 60000

		jobStore.'class' = 'org.quartz.impl.jdbcjobstore.JobStoreTX'
		jobStore.driverDelegateClass = 'org.quartz.impl.jdbcjobstore.StdJDBCDelegate'

		jobStore.useProperties = false
		jobStore.tablePrefix = 'QRTZ_'
		jobStore.isClustered = true
		jobStore.clusterCheckinInterval = 5000

		plugin.shutdownhook.'class' = 'org.quartz.plugins.management.ShutdownHookPlugin'
		plugin.shutdownhook.cleanShutdown = false
	}
}

environments {
	development {
		quartz {
			props {
				jobStore.driverDelegateClass = 'org.quartz.impl.jdbcjobstore.StdJDBCDelegate'
			}
		}
	}
	sfstest {
		quartz {
			props {
				jobStore.driverDelegateClass = 'org.quartz.impl.jdbcjobstore.StdJDBCDelegate'				
			}
		}
	}
	test {
		quartz {
			props { 				
//				jobStore.driverDelegateClass = 'org.quartz.impl.jdbcjobstore.oracle.weblogic.WebLogicOracleDelegate'
				jobStore.driverDelegateClass = 'org.quartz.impl.jdbcjobstore.StdJDBCDelegate'
			}
		}
	}
	production { 
		quartz { 
				jobStore.driverDelegateClass = ' org.quartz.impl.jdbcjobstore.oracle.weblogic.WebLogicOracleDelegate'
			} 
		}
}
