package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class OsobaSkupUd implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(required=true,name="ID")
	Long id
	@XmlElement(name="IFO",required=true)
	String ifo
	@XmlElement(name="IFO_TOT")
	String ifo_tot
	
	@XmlElement(name="IDENTIFIKATOR")
	OsobaSkupUdId identifikator
	@XmlElement(name="IDENTIFIKACNE_UDAJE")
	OsobaSkupUdIdent identUdaje
	@XmlElement(name="UMRTIE_UDAJE")
	OsobaSkupUdUmr umrtieUdaje
	@XmlElement(name="ADRESNE_UDAJE")
	OsobaSkupUdPobyt pobytUdaje
	@XmlElement(name="RODINNE_VZTAHY_UDAJE")
	OsobaSkupUdRodVzt vztahyUdaje
}
