package com.sfs.crfosp.aviza

import com.sfs.crfosp.cis.AvizVybavCis
import com.sfs.crfosp.ws.enums.AvizoStatusEnum
import com.sfs.crfosp.ws.enums.VybavenieStatusEnum

class AvizoAtributy {
	Long id
	Avizo avizo
	AvizVybavCis polozka
	VybavenieStatusEnum status
	String oldval
	String newval
	// id entity ref atributu
	Long refid
	//static belongsTo = [avizo: Avizo]
	String errMsg

	static hasMany = [
		atrRc:AvizoOsoba,
		atrDtnar:AvizoOsoba,
		atrUcnar:AvizoOsoba,
		atrMnar	:AvizoOsoba,
		atrStnar:AvizoOsoba,
		atrPohl:AvizoOsoba,
		atrRodst:AvizoOsoba,
		atrRodmatr:AvizoOsoba,
		atrNarodnost:AvizoOsoba,
		atrDtumr:AvizoOsoba,
		atrUcumr:AvizoOsoba,
		atrMumr:AvizoOsoba,
		atrUmrmatr:AvizoOsoba,
		atrStumr:AvizoOsoba,
		atrBifo:AvizoOsoba,
		atrTyposo:AvizoOsoba,
		atrOkrnar:AvizoOsoba,
		atrOkrumr:AvizoOsoba,
		atrIc:AvizoOsoba,
		atrStzverej:AvizoOsoba,
		atrRoknar:AvizoOsoba,
		atrMeno:AvizoMeno,
		atrObmStPr:AvizoObmPravnejSpos,
		atrPobyt:AvizoPobyt,
		atrPriezvisko:AvizoPriezvisko,
		atrRodvz:AvizoRodinnyVztah,
		atrRodpr:AvizoRodnePriez,
		atrStprisl:AvizoStatnaPrislusnot,
		atrTitul:AvizoTitul,
		atrStob:AvizoUdelStObcianstvo,
		atrVyhlMrtvy:AvizoZrusVyhlMrtvy
	]

	static mappedBy = [
		atrRc:"atrRc",
		atrDtnar:"atrDtnar",
		atrUcnar:"atrUcnar",
		atrMnar:"atrMnar",
		atrStnar:"atrStnar",
		atrPohl:"atrPohl",
		atrRodst:"atrRodst",
		atrRodmatr:"atrRodmatr",
		atrNarodnost:"atrNarodnost",
		atrDtumr:"atrDtumr",
		atrUcumr:"atrUcumr",
		atrMumr:"atrMumr",
		atrUmrmatr:"atrUmrmatr",
		atrStumr:"atrStumr",
		atrBifo:"atrBifo",
		atrTyposo:"atrTyposo",
		atrOkrnar:"atrOkrnar",
		atrOkrumr:"atrOkrumr",
		atrIc:"atrIc",
		atrStzverej:"atrStzverej",
		atrRoknar:"atrRoknar",
		atrMeno:"atrMeno",
		atrObmStPr:"atrObmStPr",
		atrPobyt:"atrPobyt",
		atrPriezvisko:"atrPriezvisko",
		atrRodvz:"atrRodvz",
		atrRodpr:"atrRodpr",
		atrStprisl:"atrStprisl",
		atrTitul:"atrTitul",
		atrStob:"atrStob",
		atrVyhlMrtvy:"atrVyhlMrtvy"
	]

	static mapping = {
		tablePerHierarchy true
		table 'CRAVIZOATTROS'
		// version false
		id generator:'sequence',params:[sequence:"CRREQAVYB_CRAV_SEQ"]
		//		status column:'STATUS' //,sqlType:'NUMBER',length:2
		discriminator "AvizoAtributy"
	}
	static constraints = {
		//		id nullable:false
		avizo nullable:false
		status nullable:false
		//		polozka validator: { AvizVybavCis val, AvizoAtributy obj, errors ->
		//			Avizo.findByOsobaAndStatusInList(obj.avizo.osoba,[AvizoStatusEnum.ODOSLANE,AvizoStatusEnum.CAKAJUCE,AvizoStatusEnum.NEVYBAVENE]).each {
		//				//def results = this.findByPolozkaAndAvizoAndStatus(val, it, VybavenieStatusEnum.NEPOTVRDENE)
		//				def results = this.findByPolozkaAndAvizo(val, it)
		//				if(results) {
		//					//errors.rejectValue('polozka', 'uniquePolozkaOsoby', errMsg)
		//					errors.reject('uniquePolozkaOsoby', results.avizo.id.toString())
		//				}
		//			}
		//		}
	}

	public void validatePolozkaAviza(){
		def obj = this
		Avizo.findAllByOsobaAndStatusInList(obj.avizo.osoba,[AvizoStatusEnum.ODOSLANE, AvizoStatusEnum.CAKAJUCE, AvizoStatusEnum.NEVYBAVENE]).each {
			def results = AvizoAtributy.findByPolozkaAndAvizo(obj.polozka, it)
			if(results) {
				obj.errors.reject('uniquePolozkaOsoby', results.avizo.id.toString())
			}
		}
	}

}
