package com.sfs.crfosp.aviza

import java.util.Date

import javax.xml.bind.annotation.XmlElement

import com.sfs.crfosp.cis.NarodnostCis
import com.sfs.crfosp.cis.PohlavieCis
import com.sfs.crfosp.cis.RodinnyStavCis
import com.sfs.crfosp.cis.StatCis
import com.sfs.crfosp.cis.StupenZverejCis
import com.sfs.crfosp.cis.SystemCis
import com.sfs.crfosp.cis.TypOsobyCis
import com.sfs.crfosp.cis.UzemnyCelokCis
import com.sfs.crfosp.ws.enums.AvizoStatusEnum
import com.sfs.crfosp.ws.enums.OsobaStatusEnum
import com.sfs.crfosp.ws.enums.RequestStatusEnum
import com.sfs.crfosp.ws.enums.VybavenieStatusEnum

@com.sfs.smartsfs.audit.AuditStamp
class AvizoOsobaView{
	Long id
	String popis
	AvizoStatusEnum status
	VybavenieStatusEnum lastStatus
	Boolean ws
	SystemCis systemSP
	Long avizoid
	String corrId
	Date datumVybav
	// osoba
	String ifo
	String ifo_tot
	String rc
	Date dtnar
	UzemnyCelokCis ucnar
	String mnar
	StatCis stnar
	PohlavieCis pohl
	RodinnyStavCis rodst
	String rodmatr
	NarodnostCis narodnost
	Date dtumr
	UzemnyCelokCis ucumr
	String mumr
	String umrmatr
	StatCis stumr
	String bifo
	TypOsobyCis typoso
	UzemnyCelokCis okrnar
	UzemnyCelokCis okrumr
	String ic
	StupenZverejCis stzverej
	Integer roknar
	Date dtukon
	String dovnepl
	Long rodmatr_id
	Long umrmatr_id
	Date dtcre
	Date dtupd
	//OsobaStatusEnum ostatus
	String menocele
	String priezcele
	String rodprcele

	static mapping = {
		table 'SC_WAVIZOSOBA'
		version false
		id generator:'assigned',sqlType:'NUMBER',length:19		

		ifo column:'IFO',sqlType:'char'
		ifo_tot column:'IFO_TOT'
		rc column:'RC',sqlType:'char'
//		mnar column:'MNAR'
//		rodmatr column:'RODMATR'
		mumr column:'MUMR',sqlType:'varchar2'
//		umrmatr column:'UMRMATR'
		bifo column:'BIFO'
		ic column:'IC',sqlType:'char'
		dovnepl column:'DOVNEPL'
//		ostatus column:'OSTATUS',sqlType:'char'
		pohl column:'POHL_ID',lazy:false
//		ucnar column:'UCNAR'
//		stnar column:'STNAR'
//		rodst column:'RODST'
//		narodnost column:'NARODNOST'
//		ucumr column:'UCUMR'
//		stumr column:'STUMR'
//		typoso column:'TYPOSO'
//		okrnar column:'OKRNAR'
//		okrumr column:'OKRUMR'
//		stzverej column:'STZVEREJ'

	}
	static constraints = {
		ifo nullable:true, maxSize:32
		ifo_tot nullable:true, maxSize:100
		rc nullable:true, maxSize:10
		dtnar nullable:true
		ucnar nullable:true
		mnar nullable:true, maxSize:80
		stnar nullable:true
		pohl nullable:true
		rodst nullable:true
		rodmatr nullable:true, maxSize:100
		narodnost nullable:true
		dtumr nullable:true
		ucumr nullable:true
		mumr nullable:true, maxSize:80
		umrmatr nullable:true, maxSize:100
		stumr nullable:true
		bifo nullable:true, maxSize:20
		typoso nullable:true
		okrnar nullable:true
		okrumr nullable:true
		ic nullable:true, maxSize:32
		stzverej nullable:true
		roknar nullable:true
		dtukon nullable:true
		dovnepl nullable:true, maxSize:100
		rodmatr_id nullable:true
		umrmatr_id nullable:true
		dtcre nullable:true
		dtupd nullable:true
//		ostatus nullable:true, maxSize:1
		menocele nullable:true, maxSize:2000
		priezcele nullable:true, maxSize:2000
		rodprcele nullable:true, maxSize:2000
	}
}

