package com.sfs.crfosp.mapper

import org.grails.plugin.resource.mapper.MapperPhase

class WblogicResourceMapper {
	def phase = MapperPhase.MUTATION
	
		static defaultIncludes = [ '**/*.js' ]
	
		def map(resource, config){
			log.debug resource.processedFile.name
			resource.contentType="application/javascript"
			
		}
}
