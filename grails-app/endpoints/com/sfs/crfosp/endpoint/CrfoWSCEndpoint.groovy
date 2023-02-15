package com.sfs.crfosp.endpoint

import grails.transaction.Transactional

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.springframework.beans.factory.annotation.Autowired

import com.sfs.crfosp.ws.services.IntWSService
import com.sfs.crfosp.ws.transport.CiselnikyResponse

@WebService(name = 'CrfoWSCEndpoint',
targetNamespace = 'http://endpoint.crfosp.sfs.com/',
serviceName = 'CrfoWSCEndpointService',
portName = 'CrfoWSCEndpointPort')
@GrailsCxfEndpoint(wsdl='crfoWSC.wsdl',expose = EndpointType.JAX_WS_WSDL,inInterceptors = ['loggingInOutInterceptor'],outInterceptors = ['loggingInOutInterceptor'])
//@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,inInterceptors = ['loggingInOutInterceptor'],outInterceptors = ['loggingInOutInterceptor'])
//@GrailsCxfEndpoint(expose = EndpointType.JAX_WS)
class CrfoWSCEndpoint {
	static excludes = []

	@Autowired
	private IntWSService intWSService

	@WebResult(name='CISELNIKY_ODPOVED')
	@WebMethod
	@Transactional
	CiselnikyResponse ws5Cis(@WebParam(name="CISELNIKY_OD") Date dtOd,@WebParam(name="CISELNIKY_DO") Date dtDo,@WebParam(name="CISELNIKY_LIST")String[] ciss){
		return intWSService.ws5(dtOd,dtDo,ciss)
	}
}
