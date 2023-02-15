package com.sfs.crfosp.transport.aviza

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WSA3_ODPOVED")
class NevybaveneAvizaResponse implements Serializable {
	private static final long serialVersionUID=1L
	@XmlAttribute(name = "RequestDateTime",required=true)
	Date requestDateTime
	@XmlAttribute(name = "ResponseDateTime",required=true)
	Date responseDateTime
	@XmlElement(name="WSA3_ODPOVED_DATA")
	NevybaveneAvizaData aviza
}
