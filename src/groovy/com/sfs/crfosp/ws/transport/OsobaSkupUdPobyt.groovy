package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper

import com.sfs.crfosp.domain.Pobyt

@XmlAccessorType(XmlAccessType.NONE)
class OsobaSkupUdPobyt implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(name="DT_ZMENA",required=true)
	Date dtupd
	@XmlElementWrapper(name="POBYT_OSOBY_LIST")
	@XmlElement(name="POBYT_OSOBY")
	Set<Pobyt> pobyt
}
