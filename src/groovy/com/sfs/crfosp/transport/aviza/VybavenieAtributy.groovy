package com.sfs.crfosp.transport.aviza

import groovy.transform.InheritConstructors
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import com.sfs.crfosp.aviza.Avizo
import com.sfs.crfosp.aviza.AvizoAtributy

@XmlAccessorType(XmlAccessType.NONE)
@InheritConstructors
class VybavenieAtributy implements Serializable {
	private static final long serialVersionUID=1L
	
	@XmlElement(required=true,name="AVIZOVANA_POLOZKA")
	Long polozka
	@XmlElement(required=true,name="POPIS_POLOZKY")
	String popis
	@XmlElement(name = "AVIZOVANA_HODNOTA_POLOZKY")
	String newval;
	@XmlElement(required=true,name="STAV_VYBAVENIA_POLOZKY")
	Long stavKod
	@XmlElement(required=true,name="STAV_POPIS")
	String stavPopis
	
	VybavenieAtributy(AvizoAtributy avizoAtributy) {
			this.polozka = avizoAtributy.polozka.id
			this.popis = avizoAtributy.polozka.popis
			this.newval = avizoAtributy.newval
			this.stavKod = avizoAtributy.status.id
			this.stavPopis = avizoAtributy.status.getKey()
	}
}
