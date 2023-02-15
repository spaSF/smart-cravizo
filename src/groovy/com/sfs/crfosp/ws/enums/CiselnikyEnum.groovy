package com.sfs.crfosp.ws.enums

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

@XmlType(name = "TypRequestCis")
@XmlEnum
public enum CiselnikyEnum implements Serializable {
	pohlavieCis,
	statCis,
	narodnostCis,
	rodinnyStavCis,
	stupenZverejCis,
	typOsobyCis,
	titulCis,
	typTitulCis,
	typObmSposCis,
	typPobytuCis,
	druhDokladuCis,
	typVztahCis,
	typRoleVztCis,
	regDomCis,
	regUlicaCis,
	regVchodDomuCis,
	uzemnyCelokCis,
	stavDomuCis,
	stavDomVchodCis,
	typUzCelokCis,
	avizVybavCis
}
