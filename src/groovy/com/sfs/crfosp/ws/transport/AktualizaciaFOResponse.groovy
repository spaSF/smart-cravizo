package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WS2_ODPOVED")
class AktualizaciaFOResponse implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(name = "RequestDateTime",required=true)
	Date requestDateTime
	@XmlAttribute(name = "ResponseDateTime",required=true)
	Date responseDateTime
	@XmlElement(name="WS2_ODPOVED_DATA")
	AktualizaciaFOData aktualizaciaResponse
}
