package com.sfs.crfosp.endpoint

import grails.transaction.Transactional

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.transport.aviza.NevybaveneAvizaResponse
import com.sfs.crfosp.transport.aviza.VybavenieAvizResponse
import com.sfs.crfosp.transport.aviza.ZaznamenanieAvizaResponse

@WebService(name = 'CrfoWSAEndpoint',
	targetNamespace = 'http://endpoint.crfosp.sfs.com/',
	serviceName = 'CrfoWSAEndpointService',
	portName = 'CrfoWSAEndpointPort')
	@GrailsCxfEndpoint(wsdl='crfoWSA.wsdl',expose = EndpointType.JAX_WS_WSDL,inInterceptors = ['loggingInOutInterceptor'],outInterceptors = ['loggingInOutInterceptor'])	
//@GrailsCxfEndpoint(expose = EndpointType.JAX_WS)
class CrfoWSAEndpoint {
	static excludes = []

	def intAvizaWSService

	@WebResult(name='AVIZO_ODPOVED')
	@WebMethod
	@Transactional
	ZaznamenanieAvizaResponse wsa1ZaznamenanieAviza(@WebParam(name="AVIZO") Avizo avizo) {
		return intAvizaWSService.wsa1(avizo)
	}

	@WebResult(name='VYBAVENIE_AVIZ_ODPOVED')
	@WebMethod
	@Transactional
	VybavenieAvizResponse wsa2VybavenieAviz(@WebParam(name="AVIZO_LIST") String[] avizo){ // list ID Aviz
		return intAvizaWSService.wsa2(avizo)
	}

	@WebResult(name='NEVYBAVENE_AVIZA_ODPOVED')
	@WebMethod
	@Transactional
	NevybaveneAvizaResponse wsa3NevybaveneAviza(@WebParam(name="IFO")String ifo){
		return intAvizaWSService.wsa3(ifo)
	}
}
