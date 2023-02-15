package com.sfs.crfosp.ws.enums

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

@XmlType(name = "OsobaStatusType")
@XmlEnum
public enum OsobaStatusEnum implements Serializable {
	/*aktivna*/
	A('AktĂ­vna'),
	/*neaktivna*/
	N('NeaktĂ­vna'),
	/*ref.udaje z requestu dohladania*/
	R('DoÄŤasnĂˇ'),
	/*ref.udaje z requestu dohladania*/ // #24420
	X('NereferenÄŤnĂˇ')
	
	String value
	
	OsobaStatusEnum (String value){
		this.value = value
	}
	
	String toString() { value }
	String getKey() { name() }
}
