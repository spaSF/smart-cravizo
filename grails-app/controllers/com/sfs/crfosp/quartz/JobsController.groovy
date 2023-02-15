package com.sfs.crfosp.quartz

import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals
import static org.springframework.http.HttpMethod.*
import static org.springframework.http.HttpStatus.*
import grails.plugins.quartz.CustomTriggerFactoryBean
import grails.plugins.quartz.GrailsJobClassConstants
import grails.plugins.quartz.config.TriggersConfigBuilder

import org.quartz.CronTrigger
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.quartz.impl.StdSchedulerFactory
import org.quartz.impl.matchers.GroupMatcher
import org.springframework.transaction.annotation.Transactional

import com.sfs.crfosp.mailnotif.AvizoStatusJob

@Transactional(readOnly = true)
class JobsController {
    static final Map<String, Trigger> triggers = [:]

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def responseSCService
    Scheduler quartzScheduler
	AvizoStatusJob avizoStatusJob
	
    def index() {	
		if (params.id) {
			List dataList = []
			dataList << list().data[params.id.toInteger() - 1]
			respond response:[data:dataList, startRow: 0, endRow: dataList.size()-1, totalRows:dataList.size(), status:0]
			return
		} else {
			respond response:list()
		}
    }

//	def detail() {
//		def myid = params.id
//		List dataList = []
//		dataList << list().data[0]
//		respond response:[data:dataList, startRow: 0, endRow: dataList.size()-1, totalRows:dataList.size(), status:0]
//	}
	
    def list() {
        def jobsList = []
		def countId = 1
		
        def listJobGroups = quartzScheduler.jobGroupNames
        listJobGroups?.each {jobGroup ->
            quartzScheduler.getJobKeys(jobGroupEquals(jobGroup))?.each {jobKey ->
                def jobName = jobKey.name
                List<Trigger> triggers = quartzScheduler.getTriggersOfJob(jobKey)
                if (triggers) {
                    triggers.each {trigger ->
                        def currentJob = createJob(jobGroup, jobName, jobsList, trigger.key.name, trigger.key.group, trigger.startTime, trigger.previousFireTime, trigger.nextFireTime, trigger.finalFireTime, trigger.cronExpression, countId)
                        currentJob.trigger = trigger						
                        def state = quartzScheduler.getTriggerState(trigger.key)
                        currentJob.triggerStatus = Trigger.TriggerState.find {
                            it == state
                        } ?: "UNKNOWN"
						countId = countId + 1
                    }
//                } else {
//                    createJob(jobGroup, jobName, jobsList)
                }
            }
        }
		if (jobsList) {			
			[data: jobsList, startRow: 0, endRow: jobsList.size()-1, totalRows:jobsList.size(), status:0]
		} else {
			[data: jobsList, startRow: 0, endRow: 0, totalRows:0, status:0]
		}
    }

    private createJob(String jobGroup, String jobName, List jobsList, String triggerName = "handleAvizoStatusTrigger", String triggerGroup = "", Date startTime = null, Date lastRun = null, Date nextRun = null, Date finalFireTime = null, String cronExpression = null,  Integer genId = 1) {
        def currentJob = [class: 'com.sfs.crfosp.mailnotif.AvizoStatusJob', id: genId, triggerGroup:triggerGroup, triggerName:triggerName, starttime:startTime, lastrun:lastRun, nextrun:nextRun, finalfiretime:finalFireTime, cronexp:cronExpression, group: jobGroup, name: jobName] + (QuartzMonitorJobFactory.jobRuns[triggerName] ?: [:])
        jobsList << currentJob
        return currentJob
    }

	def stop() {
		
		def mparams = params
		def triggerKeys = quartzScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(params.triggerGroup))
		def key = triggerKeys?.find {it.name == params.triggerName}
		if (key) {
			def trigger = quartzScheduler.getTrigger(key)
			if (trigger) {
				triggers[params.jobName] = trigger
				quartzScheduler.unscheduleJob(key)
			} else {
				respond response:[status:-1,data:"No trigger could be found for $key"]
			}
		} else {
			respond response:[status:-1,data:"No job key could be found for: $params.triggerName"]
		}
		respond response:list()
	}
		
	def start_def() {
		if (!params.jobName) params.jobName = AvizoStatusJob.canonicalName
		def trigger = triggers[params.jobName]
		if (trigger) {
			quartzScheduler.scheduleJob (trigger)
		} else {
			respond response:[status:-1,data:"No trigger could be found for $params.jobName"]
		}
	   respond response:list()
	}
			
	def start() {			
		def mpar = params
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		if (!scheduler.started) {
			scheduler.start();
		}	
		def builder = new TriggersConfigBuilder(params.jobName?:AvizoStatusJob.canonicalName)
		Map triggers = builder.build AvizoStatusJob.defaultTriggers
		if (!triggers) {
			respond response:[status:-1,data:"No triggers could be found for $params.jobName"]
			return
		}
		triggers.each { name, Expando descriptor ->
			if (name == (params.triggerName?:name)) {
				Trigger triggerOfJob = createTrigger (descriptor, params.jobName?:AvizoStatusJob.canonicalName)
				if (triggerOfJob) {
					if (! quartzScheduler.checkExists(triggerOfJob.getKey ())) {
						quartzScheduler.scheduleJob (triggerOfJob)
					}
				} else {
					respond response:[status:-1,data:"No triggerOfJob could be found for $params.jobName"]
					return
				}
			}
		}		
		respond response:list()
	}

	Trigger createTrigger (Expando descriptor, String jobName) {
		CustomTriggerFactoryBean factory = new CustomTriggerFactoryBean()
		factory.triggerClass = descriptor.triggerClass
		factory.triggerAttributes = descriptor.triggerAttributes
		factory.jobDetail = quartzScheduler.getJobDetail (new JobKey (jobName, GrailsJobClassConstants.DEFAULT_GROUP))
		factory.afterPropertiesSet()
		factory.object
	}	
				
    def pause() {	
		def triggerKeys = quartzScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(params.triggerGroup))
		def key = triggerKeys?.find {it.name == params.triggerName}
		if (key) {
			quartzScheduler.pauseTrigger(key)				
        } else {
			respond response:[status:-1,data:"No job key could be found for: $params.triggerName"]
        }
        respond response:list()
    }

    def resume() {
		def triggerKeys = quartzScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(params.triggerGroup))
		def key = triggerKeys?.find {it.name == params.triggerName}
        if (key) {
            quartzScheduler.resumeTrigger(key)
        } else {
			respond response:[status:-1,data:"No job key could be found for: $params.triggerName"]		
        }
        respond response:list()
    }
	
    def runNow() {
		def mpar = params
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(params.jobGroup))
        def key = jobKeys?.find {it.name == params.jobName}
        if (key) {		
			          
			String jobName = params.jobName; // Your Job Name
			String groupName = params.jobGroup; // Your Job Group
			Trigger trigger = TriggerBuilder.newTrigger()
						.withIdentity(jobName, groupName)
						.startNow()
						.build();
			
			//quartzScheduler.triggerJob(key)
        } else {
            respond response:[status:-1,data:"No job key could be found for $params.jobGroup : $params.jobName"]
        }
        respond response:list()
    }

	def update() {
		if (!params.triggerName || !params.triggerGroup) {
			flash.message = "Invalid trigger parameters"
			redirect(action: "list")
			return
		}

		CronTrigger trigger = quartzScheduler.getTrigger(new TriggerKey(params.triggerName, params.triggerGroup)) as CronTrigger
		if (!trigger) {
			flash.message = "No such trigger"
			redirect(action: "list")
			return
		}

		try {
			trigger.cronExpression = params.cronexp
			quartzScheduler.rescheduleJob(new TriggerKey(params.triggerName, params.triggerGroup), trigger)
		} catch (Exception ex) {
			flash.message = "cron expression (${params.cronexpression}) was not correct: $ex"
			//render(view: "editCronTrigger", model: [trigger: trigger])
			respond response:[status:-1,data:"cron expression (${params.cronexpression}) was not correct: $ex"]
			return
		}
		//redirect(action: "list")
		respond response:list()
	}
	
    def startScheduler = {
        quartzScheduler.start()
        redirect(action: "list")
    }

    def stopScheduler = {
        quartzScheduler.standby()
        redirect(action: "list")
    }

    def editCronTrigger = {
        def trigger = quartzScheduler.getTrigger(new TriggerKey(params.triggerName, params.triggerGroup))
        if (!(trigger instanceof CronTrigger)) {
            flash.message = "This trigger is not a cron trigger"
            redirect(action: "list")
            return
        }
        //[trigger: trigger]
		respond response:trigger
    }

    def saveCronTrigger = {
        if (!params.triggerName || !params.triggerGroup) {
            flash.message = "Invalid trigger parameters"
            redirect(action: "list")
            return
        }

        CronTrigger trigger = quartzScheduler.getTrigger(new TriggerKey(params.triggerName, params.triggerGroup)) as CronTrigger
        if (!trigger) {
            flash.message = "No such trigger"
            redirect(action: "list")
            return
        }

        try {
            trigger.cronExpression = params.cronexpression
            quartzScheduler.rescheduleJob(new TriggerKey(params.triggerName, params.triggerGroup), trigger)
        } catch (Exception ex) {
            flash.message = "cron expression (${params.cronexpression}) was not correct: $ex"
            //render(view: "editCronTrigger", model: [trigger: trigger])
			respond response:[status:-1,data:"cron expression (${params.cronexpression}) was not correct: $ex"]
            return
        }
        //redirect(action: "list")
		respond response:list()
    }
}
