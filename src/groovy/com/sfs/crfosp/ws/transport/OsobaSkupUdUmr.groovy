package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType

import com.sfs.crfosp.cis.StatCis

@XmlAccessorType(XmlAccessType.NONE)
class OsobaSkupUdUmr implements Serializable{
	private static final long serialVersionUID=1L
	@XmlAttribute(name="DT_ZMENA",required=true)
	Date dtupd
	@XmlElement(name="DT_UMRTIA")
	@XmlSchemaType(name = "date")
	Date dtumr
	@XmlElement(name="MIESTO_UMRTIA")
	String mumr
	@XmlElement(name="MATRIKA_UMRTIA")
	String umrmatr
	@XmlElement(name="STAT_UMRTIA")
	StatCis stumr
}
