package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.ws.RequestLog
import com.sfs.crfosp.ws.enums.RequestStatusEnum

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WS4_DATA")
class ZaujemFOData implements Serializable {
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
	List<ZaujemFO> foList

	ZaujemFOData(RequestLog req){
		if(!req) return
		this.id=req.getId()
		this.systemSP=req.getSystemSP()
		this.uziv=req.getUziv()
		this.stav=req.getStav()
		this.respkod=req.getRespkod()
		this.respmsg=req.getRespmsg()

		this.foList = new ArrayList<ZaujemFO>()
		req.getOsoby().each {rqOsoba->
			if(rqOsoba.getRespkod()){
				def o = new ZaujemFO(id: rqOsoba.getOsoba().id,respkod: rqOsoba.getRespkod(), respmsg: rqOsoba.getRespmsg(), ifo: rqOsoba.getOsoba().ifo)
				this.foList.add(o)
			}
		}
	}
}
