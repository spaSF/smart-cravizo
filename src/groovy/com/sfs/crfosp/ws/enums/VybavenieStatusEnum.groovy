package com.sfs.crfosp.ws.enums

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

public enum VybavenieStatusEnum implements Serializable {
		NEPOTVRDENE(0), // Nepotvrdena hodnota
		POTVRDENE(1), // Potvrdená hodnota
		OPRAVENE (2), //	Opravená hodnota
		CHYBNE (3) //	Chybna hodnota
		
	Integer id

    VybavenieStatusEnum(Integer id) { this.id = id }
	
	String getKey() { name() }
}
