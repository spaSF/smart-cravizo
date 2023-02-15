package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlType

import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil

import com.sfs.crfosp.cis.SkupUdajovCis
import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.domain.SkupUdajovModif
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.RequestStatusEnum
import com.sfs.crfosp.ws.enums.SkupUdajovEnum

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WS2_DATA")
class AktualizaciaFOData implements Serializable {
	private static final long serialVersionUID=1L

	@XmlAttribute(required=true,name="DOTAZ_ID")
	Long id
	@XmlElement(name="SYSTEM_SP",required=true)
	SystemCis systemSP
	@XmlElement(name="UZIV")
	String uziv
	@XmlElement(name="STAV",required=true)
	RequestStatusEnum stav
	@XmlElement(name="NAVRATOVY_KOD")
	Integer respkod
	@XmlElement(name="NAVRATOVA_SPRAVA")
	String respmsg
	@XmlElementWrapper(name="UDAJE_FO")
	@XmlElement(name="FO")
	List<OsobaSkupUd> foList

	AktualizaciaFOData(){
		
	}
	
	AktualizaciaFOData(RequestLog req,List<SkupUdajovEnum> skupUdaj){
		this.id=req.getId()
		this.systemSP=req.getSystemSP()
		this.uziv=req.getUziv()
		this.stav=req.getStav()
		this.respkod=req.getRespkod()
		this.respmsg=req.getRespmsg()

		this.foList = new ArrayList<OsobaSkupUd>()

		req.getOsoby().each {rqOsoba->
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.meno)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.priezvisko)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.rodnepriezvisko)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.titul)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.statnaPrislusnost)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.ucnar)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.pobyt)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.zakazPobytu)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.obmPravnejSpos)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.rodinnyVztah)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.doklad)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.udelStObcianstvo)
			GrailsHibernateUtil.unwrapIfProxy(rqOsoba.osoba.zrusVyhlMrtvy)
			
			def o = new OsobaSkupUd(id: rqOsoba.getOsoba().id,ifo: rqOsoba.getOsoba().ifo, ifo_tot:rqOsoba.osoba.ifo_tot)

			if(skupUdaj.contains(SkupUdajovEnum.rodne_cislo)){
				o.identifikator = new OsobaSkupUdId(rc: rqOsoba.getOsoba().rc)
				Date d=SkupUdajovModif.findAllByOsobaAndSkud(rqOsoba.getOsoba(),
						SkupUdajovCis.findById(String.valueOf(SkupUdajovEnum.rodne_cislo.value())),[sort:"dtzmeny",order:"desc",max:1])[0]?.dtzmeny?:rqOsoba.getOsoba().getDtupd()
				o.identifikator.setDtupd(d)
			}
			if(skupUdaj.contains(SkupUdajovEnum.ident_udaje)){
				o.identUdaje = new OsobaSkupUdIdent(dtnar: rqOsoba.getOsoba().dtnar, ucnar: rqOsoba.getOsoba().ucnar, ic: rqOsoba.getOsoba().ic, meno: rqOsoba.getOsoba().meno, mnar: rqOsoba.getOsoba().mnar, 
				pohl: rqOsoba.getOsoba().pohl, priezvisko: rqOsoba.getOsoba().priezvisko, rodnepriezvisko: rqOsoba.getOsoba().rodnepriezvisko, rodmatr: rqOsoba.getOsoba().rodmatr, //okrnar: rqOsoba.getOsoba().okrnar,
				roknar: rqOsoba.getOsoba().roknar, stnar: rqOsoba.getOsoba().stnar, titul: rqOsoba.getOsoba().titul, rodst: rqOsoba.getOsoba().rodst, narodnost: rqOsoba.getOsoba().narodnost, statnaPrislusnost:rqOsoba.getOsoba().statnaPrislusnost)
				o.identUdaje.setDtupd(SkupUdajovModif.findAllByOsobaAndSkud(rqOsoba.getOsoba(),
						SkupUdajovCis.findById(String.valueOf(SkupUdajovEnum.ident_udaje.value())),[sort:"dtzmeny",order:"desc",max:1])[0]?.dtzmeny?:rqOsoba.getOsoba().getDtupd())
			}
			if(skupUdaj.contains(SkupUdajovEnum.adresa)){
				o.pobytUdaje = new OsobaSkupUdPobyt(pobyt: rqOsoba.getOsoba().pobyt)
				o.pobytUdaje.setDtupd(SkupUdajovModif.findAllByOsobaAndSkud(rqOsoba.getOsoba(),
						SkupUdajovCis.findById(String.valueOf(SkupUdajovEnum.adresa.value())),[sort:"dtzmeny",order:"desc",max:1])[0]?.dtzmeny?:rqOsoba.getOsoba().getDtupd())
			}
			if(skupUdaj.contains(SkupUdajovEnum.rodinne_vztahy)){
				o.vztahyUdaje = new OsobaSkupUdRodVzt(rodinnyVztah: rqOsoba.getOsoba().rodinnyVztah )
				o.vztahyUdaje.setDtupd(SkupUdajovModif.findAllByOsobaAndSkud(rqOsoba.getOsoba(),
						SkupUdajovCis.findById(String.valueOf(SkupUdajovEnum.rodinne_vztahy.value())),[sort:"dtzmeny",order:"desc",max:1])[0]?.dtzmeny?:rqOsoba.getOsoba().getDtupd())
			}
			
			if(skupUdaj.contains(SkupUdajovEnum.umrtie)){
				o.umrtieUdaje = new OsobaSkupUdUmr(dtumr: rqOsoba.getOsoba().dtumr, mumr: rqOsoba.getOsoba().mumr, stumr: rqOsoba.getOsoba().stumr, umrmatr: rqOsoba.getOsoba().umrmatr)
				o.umrtieUdaje.setDtupd(SkupUdajovModif.findAllByOsobaAndSkud(rqOsoba.getOsoba(),
						SkupUdajovCis.findById(String.valueOf(SkupUdajovEnum.umrtie.value())),[sort:"dtzmeny",order:"desc",max:1])[0]?.dtzmeny?:rqOsoba.getOsoba().getDtupd())
			}
			
			this.foList.add(o)
		}
	}
}
