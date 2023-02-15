package com.sfs.crfosp.ws.services

import com.sfs.crfosp.endpoint.Doklad;
import com.sfs.crfosp.endpoint.Meno
import com.sfs.crfosp.endpoint.ModifikujuciExtSystem
import com.sfs.crfosp.endpoint.ObmedzeniaPravSposob
import com.sfs.crfosp.endpoint.Osoba
import com.sfs.crfosp.endpoint.PozadovaneTypyInf
import com.sfs.crfosp.endpoint.PozadovanyTypInf
import com.sfs.crfosp.endpoint.Priezvisko
import com.sfs.crfosp.endpoint.RodinnyVztah
import com.sfs.crfosp.endpoint.RodnePriezvisko
import com.sfs.crfosp.endpoint.RozhodnutiaOOsZaMrtvu
import com.sfs.crfosp.endpoint.StatnaPrislusnost
import com.sfs.crfosp.endpoint.StatneObcianstvo
import com.sfs.crfosp.endpoint.Titul
import com.sfs.crfosp.endpoint.VolajuciSystem
import com.sfs.crfosp.endpoint.VystupnyDokument
import com.sfs.crfosp.endpoint.Ws20RozsireneUdajeFOByIFO
import com.sfs.crfosp.endpoint.WsrbVstupnyDokument
import com.sfs.crfosp.endpoint.ZakazPobytu
import com.sfs.crfosp.endpoint.SKUPINAUDAJOV
import com.sfs.crfosp.ws.enums.SkupUdajovEnum
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.datatype.XMLGregorianCalendar
import javax.xml.datatype.DatatypeFactory;
import sk.ditec.ekr.registration_v1_0.Registration
import sk.egov.mvsr.rfo.datatypes.podp.ext.poskytnutierozsrirenychudajovzregobws_v1_0.TPOD
import sk.egov.mvsr.rfo.datatypes.podp.ext.poskytnutierozsrirenychudajovzregobws_v1_0.TransEnvTypeIn
import sk.egov.mvsr.rfo.datatypes.podp.ext.poskytnutierozsrirenychudajovzregobws_v1_0.TransEnvTypeOut
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
import spock.lang.Specification

class IntWS20TransformServiceSpec extends Specification  {


	TransEnvTypeOut transEnvTypeOut
	Date datetime
	Date date
	
	def setup ()
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(TransEnvTypeOut.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		transEnvTypeOut = jaxbUnmarshaller.unmarshal(new File("test/resources/PoskytnutieRozsrirenychUdajovZREGOBWS-v1.0-TransEnvOut.xml")).getValue();
		datetime = new Date().parse("yyyy-MM-dd'T'HH:mm:ss", "2001-12-31T12:00:00")
		date = new Date().parse("yyyy-MM-dd", "2001-01-01")
	}
	
	def "PozadovaneTypyInf to TransEnvTypeIn" ()
	{
		setup:
			JAXBContext jaxbContext = JAXBContext.newInstance(Ws20RozsireneUdajeFOByIFO.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Ws20RozsireneUdajeFOByIFO ws20RozsireneUdajeFOByIFO = jaxbUnmarshaller.unmarshal(new File("test/resources/WSRB_320_PoskytnutieRozsrirenychUdajovZREGOBWS-v1.0.xml"));
			
		when: 
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			TransEnvTypeIn transEnvTypeIn = intWS20TransformService.pozadovaneTypyInf(ws20RozsireneUdajeFOByIFO.wsrbVstupnyDokument.ifo , 
				ws20RozsireneUdajeFOByIFO.wsrbVstupnyDokument.pozadovaneTypyInf, 123456L, "admin")
		
		then:
			transEnvTypeIn.POD.ues.TI == "123456"
			transEnvTypeIn.POD.ues.PO == "admin"
			transEnvTypeIn.POD.oz == true
			transEnvTypeIn.POD.getIF() == "ifo"
			transEnvTypeIn.POD.spiList.spi[0].ho == 4
			transEnvTypeIn.POD.spiList.spi[0].hu == true
			transEnvTypeIn.POD.spiList.spi[0].tudhona == "Identifikačné údaje"
	}
	
	def "PozadovaneTypyInf to Registration" ()
	{
		setup:
			JAXBContext jaxbContext = JAXBContext.newInstance(Ws20RozsireneUdajeFOByIFO.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Ws20RozsireneUdajeFOByIFO ws20RozsireneUdajeFOByIFO = jaxbUnmarshaller.unmarshal(new File("test/resources/WSRB_320_PoskytnutieRozsrirenychUdajovZREGOBWS-v1.0.xml"));
			
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			Registration registration = intWS20TransformService.pozadovaneTypyInf2Registration(ws20RozsireneUdajeFOByIFO.wsrbVstupnyDokument.ifo , 
				ws20RozsireneUdajeFOByIFO.wsrbVstupnyDokument.pozadovaneTypyInf, 123456L, "admin")
			TPOD pod = registration.documentUnauthorized.object.transEnvIn.POD
			
		then:
			registration.id == "RFO_PS_ROZSIRENE_UDAJE_O_OSOBE_Z_REGOB_BEZ_ZEP_WS_IN_1_0"
			registration.documentUnauthorized.id == "RFO_PS_ROZSIRENE_UDAJE_O_OSOBE_Z_REGOB_BEZ_ZEP_WS_IN_1_0"
			registration.documentUnauthorized.object.id == "123456"
			registration.documentUnauthorized.object.identifier == "http://www.egov.sk/mvsr/RFO/datatypes/Podp/Ext/PoskytnutieRozsrirenychUdajovZREGOBWS-v1.0.xsd"
			registration.documentUnauthorized.object.transEnvIn.corrID == "123456"
			pod.ues.TI == "123456"
			pod.ues.PO == "admin"
			pod.oz == true
			pod.getIF() == "ifo"
			pod.spiList.spi[0].ho == 4
			pod.spiList.spi[0].hu == true
			pod.spiList.spi[0].tudhona == "Identifikačné údaje"
	}

	def "PozadovaneTypyInf to Registration marshal" ()
	{
		setup:
			JAXBContext jaxbContext = JAXBContext.newInstance(Ws20RozsireneUdajeFOByIFO.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Ws20RozsireneUdajeFOByIFO ws20RozsireneUdajeFOByIFO = jaxbUnmarshaller.unmarshal(new File("test/resources/WSRB_320_PoskytnutieRozsrirenychUdajovZREGOBWS-v1.0.xml"));
			
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			String registration = intWS20TransformService.marshalPozadovaneTypyInf2Registration(ws20RozsireneUdajeFOByIFO.wsrbVstupnyDokument.ifo , 
				ws20RozsireneUdajeFOByIFO.wsrbVstupnyDokument.pozadovaneTypyInf, 123456L, "admin")
			
		then:
			registration.size() > 0
	}

	def "UnmarsalRegistration" ()
	{
		setup:
			InputStream is = new FileInputStream(new File("test/resources/PoskytnutieRozsrirenychUdajovZREGOBWS-v1.0-Registration.xml"));
			
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			VystupnyDokument vystupnyDokument = intWS20TransformService.unmarsalRegistration(is)
			
		then:
			vystupnyDokument.NAVRATOVYKOD == 1
	}

	def "TransEnvOut" () {
		setup:
			
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			VystupnyDokument vystupnyDokument = intWS20TransformService.transEnvOut(transEnvTypeOut)
	
		then:
			vystupnyDokument.NAVRATOVASPRAVA == 'this:NU'
			vystupnyDokument.NAVRATOVYKOD == 0
			vystupnyDokument.datCasVytvorenia.toGregorianCalendar().time == datetime	
			vystupnyDokument.modifikujuceExtSystemy.modifikujuciExtSystem[0].extSystemCis == 'this:ZOBZONA'
			vystupnyDokument.modifikujuceExtSystemy.modifikujuciExtSystem[0].id == 0
			vystupnyDokument.modifikujuceExtSystemy.modifikujuciExtSystem[0].extSystem == 0
	}

	def "Doklad" () {
		setup:

		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <Doklad> doklady = intWS20TransformService.doklady(transEnvTypeOut.getPOV().getDCDList().getDCD())

		then:
			doklady.size() == 1
			doklady[0].druh == 0
			doklady[0].druhCis == 'this:DDCDDNA'
			doklady[0].identifikator == 'this:CD'
			doklady[0].drziOsoba == true
	}
	
	def "Meno" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <Meno> mena = intWS20TransformService.mena(transEnvTypeOut.getPOV().getMOSList().getMOS())

		then:
			mena.size() == 1
			mena[0].id == 0
			mena[0].datCasZaciatkuPlatn.toGregorianCalendar().time == datetime
			mena[0].datCasUkonceniaPlatn.toGregorianCalendar().time == datetime
			mena[0].meno == 'this:ME'
			mena[0].poradie == 0
	}

	def "ModifikujuciExtSystem" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <ModifikujuciExtSystem> list = intWS20TransformService.modifikujuciExtSystem (transEnvTypeOut.getPOV().getZUNList().getZUN())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].extSystemCis == 'this:ZOBZONA'
			list[0].extSystem == 0
	}
	
	def "ObmedzeniaPravSposob" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <ObmedzeniaPravSposob> list = intWS20TransformService.obmedzeniaPravSposob (transEnvTypeOut.getPOV().getZPOList().getZPO())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].typObm == 0
			list[0].datZaciatkuPlatn.toGregorianCalendar().time == date
			list[0].datUkonceniaPlatn.toGregorianCalendar().time == date
			list[0].poznamka == 'this:PO'
			list[0].organVydalObm == 'this:VO'
			list[0].sudVydalObm == 0
			list[0].sudCisVydalObm == 'this:SUDSUNA'
			list[0].cisloRozhObm == 'this:CR'
			list[0].organUkoncilObm == 'this:VR'
			list[0].sudUkoncilObm  == 0 
			list[0].sudCisUkoncilObm == 'this:SUDSKNA'
			list[0].cisloRozhUkonceniaObm == 'this:CO'
	}
	
	def "Osoba" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <Osoba> list = intWS20TransformService.osoby (transEnvTypeOut.getPOV().getOEXList().getOEX())

		then:
			list.size() == 1
			list[0].ifo.value == 'this:ID'
			list[0].idPravejOsoby.value == 'this:IP'
			list[0].narodnost.value == 0
			list[0].narodnostCis.value == 'this:NARNINA'
			list[0].pohlavie.value == 0
			list[0].pohlavieCis.value == 'this:POHPONA'
			list[0].rodinnyStav.value == 0
			list[0].rodinnyStavCis.value == 'this:RSTRSNA'
			list[0].rc.value == 'this:RC'
			list[0].stupenZverejnenia.value == 0
			list[0].stupenZverejneniaCis.value == 'this:SZVSZNA'
			list[0].typOsoby.value == 0
			list[0].typOsobyCis.value == 'this:TVKTVNA'
			list[0].identifCudzinca.value == 'this:IC'
			list[0].datUmrtia.value.toGregorianCalendar().time == date
			list[0].statUmrtia.value == 0
			list[0].statUmrtiaCis.value == 'this:STAS2NA'
			list[0].umrtnaMatrika.value == 'this:UM'
			list[0].miestoUmrtiaMimoCis.value == 'this:M2'
			list[0].statNarodenia.value == 0
			list[0].statNarodeniaCis.value == 'this:STASNNA'
			list[0].rodnaMatrika.value == 'this:RM'
			list[0].miestoNarodeniaMimoCis.value == 'this:MR'
			list[0].miestoNarodenia.value == 0
			list[0].miestoCisNarodenia.value == 'this:UCEUCNA'
			list[0].datNarodenia.value.toGregorianCalendar().time == date
			list[0].bifo.value == 'this:BI'
			list[0].okresNarodenia.value == 0
			list[0].okresCisNarodenia.value == 'this:UCEULNA'
			list[0].okresUmrtia.value == 0
			list[0].okresCisUmrtia.value == 'this:UCEUKNA'
			list[0].datCasZaciatkuPlatn.value.toGregorianCalendar().time == datetime
			list[0].datCasUkonceniaPlatn.value.toGregorianCalendar().time == datetime
			list[0].okresNarodeniaMimoCis.value == 'this:ON'
			list[0].okresUmrtiaMimoCis.value == 'this:OU'
			list[0].rokNarodenia.value == 0
			list[0].idPoslednejZmenDavky.value == 0
			list[0].matrikaNarodenia.value == 0
			list[0].matrikaUmrtia.value == 0
			list[0].miestoUmrtia.value == 0
			list[0].miestoCisUmrtia.value == 'this:UCEU2NA'
			list[0].sysPriznak.value == 0
			list[0].sysPriznakCis == 'this:SPZSYNA'
	}
	
	def "Pobyt" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <Osoba> list = intWS20TransformService.pobyty (transEnvTypeOut.getPOV().getPOBList().getPOB())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].typPobytu == 0
			list[0].typPobytuCis == 'this:TB'
			list[0].datPrihlasenia.toGregorianCalendar().time == date
			list[0].datUkoncenia.toGregorianCalendar().time == date
			list[0].obecNazov == 'this:NK'
			list[0].supisCislo == 0
			list[0].castObceNazov == 'this:NC'
			list[0].okresNazov == 'this:NK'
			list[0].ulicaNazov == 'this:NU'
			list[0].statMimoSR == 0
			list[0].statNazov == 'this:NS'
			list[0].adresaMimoSR == 'this:MP'
			list[0].okresMimoSR == 'this:OP'
			list[0].obecMimoSR == null
			list[0].castObceMimoSR == 'this:CC'
			list[0].ulicaMimoSR == 'this:UM'
			list[0].orientCisloMimoSR == 'this:OS'
			list[0].supisCisloMimoSR == 'this:SI'
			list[0].miestoVBudoveMimoSR == 'this:CU'
			list[0].jePobytMimoSR == true
			list[0].indexDomu == 'this:PC'
			list[0].cisloBytu == 'this:CB'
			list[0].idAdresyCis.value == 'this:IA'
			list[0].vchodDomu.value == 0
			list[0].dom.value == 0
			list[0].ulica.value == 0
			list[0].castObce.value == 0
			list[0].obec.value == 0
			list[0].okres.value == 0
			list[0].orientacneCislo == 'this:OL'
			list[0].datPlatnUdeleniaPov.value.toGregorianCalendar().time == date
			list[0].datPlatnUkonceniaPov.value.toGregorianCalendar().time == date
			list[0].regionyAdrMimoSRPrePobyt.regionAdrMimoSRPrePobyt.size() == 1
			list[0].regionyAdrMimoSRPrePobyt.regionAdrMimoSRPrePobyt[0].id == 0
			list[0].regionyAdrMimoSRPrePobyt.regionAdrMimoSRPrePobyt[0].poradoveCislo == 0
			list[0].regionyAdrMimoSRPrePobyt.regionAdrMimoSRPrePobyt[0].nazov == 'this:RE'
	}
	
	def "Priezviska" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <Priezvisko> list = intWS20TransformService.priezviska (transEnvTypeOut.getPOV().getPRIList().getPRI())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].datCasZaciatkuPlatn.toGregorianCalendar().time == datetime
			list[0].datCasUkonceniaPlatn.toGregorianCalendar().time == datetime
			list[0].poradie == 0
			list[0].priezvisko == 'this:PR'
	}
	
	def "RodinnyVztah" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <RodinnyVztah> list = intWS20TransformService.rodinneVztahy (transEnvTypeOut.getPOV().getRVEList().getRVE())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].ifoVztahOsoby == 'this:IF'
			list[0].typVztahu == 0
			list[0].typVztahuCis == 'this:TRZTRNA'
			list[0].datVzniku.toGregorianCalendar().time == date
			list[0].datUkonceniaManzelstva.toGregorianCalendar().time == date
			list[0].dovodUkonceniaManzelstva == 'this:DN'
			list[0].obecMimoCisVydalaSobList == 'this:MV'
			list[0].obecVydalaSobList == 0
			list[0].obecCisVydalaSobList == 'this:UCEUCNA'
			list[0].typRoleOsoby1 == null
			list[0].typRoleCisOsoby1 == 'this:TRRTLNA'
			list[0].typRoleOsoby2 == 0
			list[0].typRoleCisOsoby2 == 'this:TRRTENA'
			list[0].sobasnaMatrikaNazov.value == 'this:SM'
			list[0].sobasnaMatrika.value == 0
			list[0].jeZrusenyRodinnyVztah == true
			list[0].rozhodnutiePreRodVztah.id == 0
			list[0].rozhodnutiePreRodVztah.sudRozhodol == 0
			list[0].rozhodnutiePreRodVztah.sudCisRozhodol == 'this:SUDSUNA'
			list[0].rozhodnutiePreRodVztah.cisloRozh == 'this:CR'
			list[0].rozhodnutiePreRodVztah.datVydaniaRozh.toGregorianCalendar().time == date
			list[0].rozhodnutiePreRodVztah.datPlatnRozh.toGregorianCalendar().time == date
			list[0].rozhodnutiePreRodVztah.organRozhodol == 'this:OK'
	}
	
	def "RodnePriezvisko" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <RodnePriezvisko> list = intWS20TransformService.rodnePriezviska (transEnvTypeOut.getPOV().getRPRList().getRPR())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].datCasZaciatkuPlatn.toGregorianCalendar().time == datetime 
			list[0].datCasUkonceniaPlatn.toGregorianCalendar().time == datetime
			list[0].poradie == 0
			list[0].rodnePriezvisko == 'this:RP'
	}
	
	def "RozhodnutiaOOsZaMrtvu" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <RozhodnutiaOOsZaMrtvu> list = intWS20TransformService.rozhodnutiaOOsZaMrtvu (transEnvTypeOut.getPOV().getPZMList().getPZM())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].sudRozhodol == 0
			list[0].sudRozhodolCis == 'this:SUDSINA'
			list[0].cisRozhVyhlaseniaZaMrtveho == 'this:CR'
			list[0].datVydaniaRozh.toGregorianCalendar().time == date
			list[0].datPlatnZruseniaZaMrtveho.toGregorianCalendar().time == date
			list[0].organRozhodol == 'this:OK'
			list[0].jeMrtvaOsoba == true
			
	}
	
	def "StatnaPrislusnost" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <StatnaPrislusnost> list = intWS20TransformService.statnaPrislusnost (transEnvTypeOut.getPOV().getSPRList().getSPR())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].datCasZaciatkuPlatn.toGregorianCalendar().time == datetime
			list[0].datCasUkonceniaPlatn.toGregorianCalendar().time == datetime
			list[0].statnaPrislusnost == 0
			list[0].statnaPrislusnostCis == 'this:STASTNA'			
	}
	
	def "StatneObcianstvo" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <StatneObcianstvo> list = intWS20TransformService.statneObcianstva (transEnvTypeOut.getPOV().getUSOList().getUSO())

		then:
			list.size() == 1
			list[0].cisloRozhUdelenia == 'this:CR'
			list[0].organRozhodol == 'this:VO'
			list[0].datVystaveniaListinyOUdeleni.toGregorianCalendar().time == date
			list[0].datPrevzatiaListiny.toGregorianCalendar().time == date
			list[0].jeUdelenieNieJeStrata == true
			list[0].poznamka == 'this:PO'
	}
	
	def "Titul" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <Titul> list = intWS20TransformService.tituly (transEnvTypeOut.getPOV().getTOSList().getTOS())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].datCasZaciatkuPlatn.toGregorianCalendar().time == datetime
			list[0].datCasUkonceniaPlatn.toGregorianCalendar().time == datetime
			list[0].titul == 0
			list[0].titulCis == 'this:TITTINA'
			list[0].typTitulu == 0
			list[0].typTituluCis == 'this:TTITTNA'
			list[0].identifikatorDiplomu == 'this:CD'
			list[0].vydavatelDiplomu == 'this:DV'					
	}
	
	def "ZakazPobytu" () {
		setup:
		
		when:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			List <ZakazPobytu> list = intWS20TransformService.zakazyPobytov (transEnvTypeOut.getPOV().getZPOList().getZPO())

		then:
			list.size() == 1
			list[0].id == 0
			list[0].datZaciatku.toGregorianCalendar().time == date
			list[0].datUkoncenia.toGregorianCalendar().time == date
			list[0].organVydal == 'this:VO'
			list[0].doplnUdaje == 'this:PO'
			list[0].sudVydal == 0
			list[0].sudCisVydal == 'this:SUDSUNA'
			list[0].cisloRozhVydania == 'this:CR'
			list[0].sudUkoncil == 0
			list[0].sudCisUkoncil == 'this:SUDSKNA'
			list[0].cisloRozhUkoncenia == 'this:CO'
			list[0].organUkoncil == 'this:VR'
			list[0].obceCisPreZakazPobytu.obecCisPreZakazPobytu[0].id == 0
			list[0].obceCisPreZakazPobytu.obecCisPreZakazPobytu[0].obec == 0
			list[0].obceCisPreZakazPobytu.obecCisPreZakazPobytu[0].obecCis == 'this:UCEUCNA'
	}
	
	def "volajuciSystem test" ()
	{
		setup:
			IntWS20TransformService intWS20TransformService = new IntWS20TransformService ()
			com.sfs.crfosp.cis.SystemCis system = new com.sfs.crfosp.cis.SystemCis ()
			system.setId('1234')
			system.setNazov('jvp')
			
		when:
			VolajuciSystem volajuciSystem = intWS20TransformService.volajuciSystem (system)
			
		then:
			volajuciSystem.systemsp.nazov == 'jvp'
			volajuciSystem.systemsp.kod == '1234'
	}
}
