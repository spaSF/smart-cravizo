package com.sfs.crfosp.ws.enums

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

public enum AvizoStatusEnum implements Serializable {
		EVIDOVANE(0),
		ODOSLANE(2),
		CAKAJUCE(3),
		NEVYBAVENE(4),
		VYBAVENE(5),
		UZAVRETE(6),
		CHYBNE(7)

	Integer id

    AvizoStatusEnum(Integer id) { this.id = id }
	
	String getKey() { name() }
}
