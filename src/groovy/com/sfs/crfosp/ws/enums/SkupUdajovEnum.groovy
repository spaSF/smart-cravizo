package com.sfs.crfosp.ws.enums;

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

@XmlType(name = "SKUPINA_UDAJOV")
@XmlEnum
enum SkupUdajovEnum {
	rodne_cislo(1),ident_udaje(2),umrtie(3),adresa(4),rodinne_vztahy(5)

	private final int value

	SkupUdajovEnum(int value) {
		this.value=value
	}
	public int value() {
		return value
	}
	static SkupUdajovEnum keyOfvalue( int value) {
		values().find { it.value == value}
	}
}