package com.sfs.crfosp.reports.dto;

import com.sfs.crfosp.domain.Osoba
import com.sfs.crfosp.security.Uzivatel
 
public class OsobaReport {
	public Osoba osoba;
	public Uzivatel uzivatel;
	
	public Osoba getOsoba() {
		return osoba;
	}
	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}
	public Uzivatel getUzivatel() {
		return uzivatel;
	}
	public OsobaReport(Osoba osoba, Uzivatel uzivatel) {
		super();
		this.osoba = osoba;
		this.uzivatel = uzivatel;
	}
	public void setUzivatel(Uzivatel uzivatel) {
		this.uzivatel = uzivatel;
	}
}
