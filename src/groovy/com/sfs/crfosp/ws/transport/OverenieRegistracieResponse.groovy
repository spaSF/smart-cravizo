package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.ws.RequestLog

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WS1_ODPOVED")
class OverenieRegistracieResponse implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(name = "RequestDateTime",required=true)
	Date requestDateTime
	@XmlAttribute(name = "ResponseDateTime",required=true)
	Date responseDateTime
	@XmlElement(name="OverenieRegResponse")
	RequestLog overenieResponse
}
