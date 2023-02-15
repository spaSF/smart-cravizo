package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper

import com.sfs.crfosp.domain.RodinnyVztah

@XmlAccessorType(XmlAccessType.NONE)
class OsobaSkupUdRodVzt implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(name="DT_ZMENA",required=true)
	Date dtupd
	@XmlElementWrapper(name="RODINNY_VZTAH_LIST")
	@XmlElement(name="RODINNY_VZTAH")
	Set<RodinnyVztah> rodinnyVztah
}
