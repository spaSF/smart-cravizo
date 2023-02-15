package com.sfs.crfosp.ws.enums

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

@XmlType(name = "PovodType")
@XmlEnum
public enum PovodEnum implements Serializable {
	/*automat*/
	A('Automatický vstup'),
	/*manuál*/
	N('Manuálny vstup')

	String value
	
	PovodEnum (String value){
		this.value = value
	}
	
	String toString() { value }
	String getKey() { name() }
}
