package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class ZaujemFO implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="IFO",required=true)
	String ifo
	@XmlElement(name="NAVRATOVY_KOD")
	Integer respkod
	@XmlElement(name="NAVRATOVA_SPRAVA")
	String respmsg
}
