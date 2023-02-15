package com.sfs.crfosp.security

import com.sfs.crfosp.app.OsobaView

// #27707
class UzivatelLogView {

	Date cas
	Uzivatel user
	OsobaView osoba
	String ifo
	Date dtnar
	String menocele
	String priezcele
	String rodprcele
	String surname
	String name
	
	static mapping = {
		table 'WCRUSRLOG'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19
		ifo sqlType:'char'
		//osoba column:'OSOBA_ID',sqlType:'NUMBER',length:19
	}
	
    static constraints = {
    }
}

/*
create or replace view wcrusrlog as
select a.*, b.ifo, b.dtnar, b.priezcele, b.menocele, b.rodprcele, c.surname, c.name from crusrlog a, crosoba b, sc_user c 
where b.id = a.osoba_id and c.id = a.user_id
*/