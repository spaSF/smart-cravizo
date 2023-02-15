
package com.sfs.crfosp.ws.services

import com.sfs.crfosp.endpoint.PozadovaneTypyInf
import com.sfs.crfosp.endpoint.SKUPINAUDAJOV
import com.sfs.crfosp.endpoint.ObjectFactory

import grails.plugin.springsecurity.SpringSecurityUtils

import grails.test.spock.IntegrationSpec
import spock.lang.Specification

class IntWS20ServiceIntegrationSpec extends Specification { // IntegrationSpec {

	def intWS20Service
	def username 
	
	def setup ()
	{
		username = "admin"
	}
/*	
	void "ws20RozsireneUdajeFOByIFO ziskaj informácie pre ifo 1127964727"() {
		
			setup:
				def ifo = "1127964727"
				def vystupnyDokument
				def of = new ObjectFactory()
				def pozadovaneTypyInf = of.createPozadovaneTypyInf()				
				pozadovaneTypyInf.pozadovanyTypInf.add(of.createPozadovanyTypInf())
				
				pozadovaneTypyInf.pozadovanyTypInf[0].pozadujemInf = 1
				pozadovaneTypyInf.pozadovanyTypInf[0].skupinaudajov = SKUPINAUDAJOV.RODNE_CISLO
				pozadovaneTypyInf.pozadovanyTypInf[0].chcemHistInf = 1
				
			when:
				SpringSecurityUtils.doWithAuth(username) {
					vystupnyDokument = intWS20Service.ws20RozsireneUdajeFOByIFO(ifo, pozadovaneTypyInf)				
				}
				
			then:
				vystupnyDokument.navratovykod == 0
				vystupnyDokument.dotazid != null
		}
*/		
    void "ws20RozsireneUdajeFOByIFORecall vráť infomácie pre ifo 1127964727"() {

		setup:
			def dotazID = 237191
			def vystupnyDokument
			
		when: 		
			SpringSecurityUtils.doWithAuth(username) {
				vystupnyDokument = intWS20Service.ws20RozsireneUdajeFOByIFORecall(dotazID)
			}
			
		then:
			vystupnyDokument.navratovykod == 1
    }
}
