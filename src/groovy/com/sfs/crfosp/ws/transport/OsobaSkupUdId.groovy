package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class OsobaSkupUdId implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(name="DT_ZMENA",required=true)
	Date dtupd
	@XmlElement(name="RC")
	String rc
}
