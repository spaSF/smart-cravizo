package com.sfs.crfosp.ws.transport

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlType

import com.sfs.crfosp.cis.AvizVybavCis
import com.sfs.crfosp.cis.DruhDokladuCis
import com.sfs.crfosp.cis.NarodnostCis
import com.sfs.crfosp.cis.PohlavieCis
import com.sfs.crfosp.cis.RegDomCis
import com.sfs.crfosp.cis.RegUlicaCis
import com.sfs.crfosp.cis.RegVchodDomuCis
import com.sfs.crfosp.cis.RodinnyStavCis
import com.sfs.crfosp.cis.StatCis
import com.sfs.crfosp.cis.StavDomVchodCis
import com.sfs.crfosp.cis.StavDomuCis
import com.sfs.crfosp.cis.StupenZverejCis
import com.sfs.crfosp.cis.TitulCis
import com.sfs.crfosp.cis.TypObmSposCis
import com.sfs.crfosp.cis.TypOsobyCis
import com.sfs.crfosp.cis.TypPobytuCis
import com.sfs.crfosp.cis.TypRoleVztCis
import com.sfs.crfosp.cis.TypTitulCis
import com.sfs.crfosp.cis.TypUzCelokCis
import com.sfs.crfosp.cis.TypVztahCis
import com.sfs.crfosp.cis.UzemnyCelokCis
import com.sfs.crfosp.ws.enums.CiselnikyEnum

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="WS5_DATA")
class Ciselniky implements Serializable {

	private static final long serialVersionUID=1L

	def configHolderService

	@XmlElementWrapper(name="POHLAVIECIS_LIST")
	@XmlElement(name="POHLAVIECIS")
	Set<PohlavieCis> pohlavieCis

	@XmlElementWrapper(name="STATCIS_LIST")
	@XmlElement(name="STATCIS")
	Set<StatCis> statCis

	@XmlElementWrapper(name="NARODNOSTCIS_LIST")
	@XmlElement(name="NARODNOSTCIS")
	Set<NarodnostCis> narodnostCis

	@XmlElementWrapper(name="RODINNYSTAVCIS_LIST")
	@XmlElement(name="RODINNYSTAVCIS")
	Set<RodinnyStavCis> rodinnyStavCis

	@XmlElementWrapper(name="STUPENZVERENEJCIS_LIST")
	@XmlElement(name="STUPENZVERENEJCIS")
	Set<StupenZverejCis> stupenZverejCis

	@XmlElementWrapper(name="TYPOSOBYCIS_LIST")
	@XmlElement(name="TYPOSOBYCIS")
	Set<TypOsobyCis> typOsobyCis

	@XmlElementWrapper(name="TITULCIS_LIST")
	@XmlElement(name="TITULCIS")
	Set<TitulCis> titulCis

	@XmlElementWrapper(name="TYPTITULCIS_LIST")
	@XmlElement(name="TYPTITULCIS")
	Set<TypTitulCis> typTitulCis

	@XmlElementWrapper(name="TYPOBMSPOSCIS_LIST")
	@XmlElement(name="TYPOBMSPOSCIS")
	Set<TypObmSposCis> typObmSposCis

	@XmlElementWrapper(name="TYPPOBYTUCIS_LIST")
	@XmlElement(name="TYPPOBYTUCIS")
	Set<TypPobytuCis> typPobytuCis

	@XmlElementWrapper(name="DRUHDOKLADUCIS_LIST")
	@XmlElement(name="DRUHDOKLADUCIS")
	Set<DruhDokladuCis> druhDokladuCis

	@XmlElementWrapper(name="TYPVZTAHCIS_LIST")
	@XmlElement(name="TYPVZTAHCIS")
	Set<TypVztahCis> typVztahCis

	@XmlElementWrapper(name="TYPROLEVZTCIS_LIST")
	@XmlElement(name="TYPROLEVZTCIS")
	Set<TypRoleVztCis> typRoleVztCis

	@XmlElementWrapper(name="REGISTERDOMOV_LIST")
	@XmlElement(name="REGISTERDOMOV")
	Set<RegDomCis> regDomCis

	@XmlElementWrapper(name="REGISTERULIC_LIST")
	@XmlElement(name="REGISTERULIC")
	Set<RegUlicaCis> regUlicaCis

	@XmlElementWrapper(name="REGISTERVCHOD_LIST")
	@XmlElement(name="REGISTERVCHOD")
	Set<RegVchodDomuCis> regVchodDomuCis

	@XmlElementWrapper(name="UZEMNYCELOK_LIST")
	@XmlElement(name="UZEMNYCELOK")
	Set<UzemnyCelokCis> uzemnyCelokCis

	@XmlElementWrapper(name="STAVDOMUCIS_LIST")
	@XmlElement(name="STAVDOMUCIS")
	Set<StavDomuCis> stavDomuCis

	@XmlElementWrapper(name="STAVDOMVCHODCIS_LIST")
	@XmlElement(name="STAVDOMVCHODCIS")
	Set<StavDomVchodCis> stavDomVchodCis

	@XmlElementWrapper(name="TYPUZCELOKCIS_LIST")
	@XmlElement(name="TYPUZCELOKCIS")
	Set<TypUzCelokCis> typUzCelokCis

	@XmlElementWrapper(name="AVIZVYBAVCIS_LIST")
	@XmlElement(name="AVIZVYBAVCIS")
	Set<AvizVybavCis> avizVybavCis
	
	Ciselniky(Date dtOd, Date dtDo, String[] typList){

		dtOd = dtOd? dtOd : new Date().parse("dd.mm.yyyy", "01.01.1900")
		dtDo = dtDo? dtDo : new Date()

		def ciselnikyList = typList.toString().trim().replaceAll(~/^\[|\]$/, '').split(',').collect{ it.trim().toUpperCase()}
		ciselnikyList.removeAll('NULL')

		List<StatCis> lsc = []
		if (ciselnikyList.contains(CiselnikyEnum.statCis.name().toUpperCase()) || !ciselnikyList) {
			this.statCis = StatCis.findAll("from StatCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}

		List<NarodnostCis> lnc = []
		if (ciselnikyList.contains(CiselnikyEnum.narodnostCis.name().toUpperCase()) || !ciselnikyList) {
			this.narodnostCis = NarodnostCis.findAll("from NarodnostCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<PohlavieCis> lpc =[]
		if (ciselnikyList.contains(CiselnikyEnum.pohlavieCis.name().toUpperCase()) || !ciselnikyList) {
			this.pohlavieCis = PohlavieCis.findAll("from PohlavieCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<RodinnyStavCis> lrsc = []
		if (ciselnikyList.contains(CiselnikyEnum.rodinnyStavCis.name().toUpperCase()) || !ciselnikyList) {
			this.rodinnyStavCis = RodinnyStavCis.findAll("from RodinnyStavCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<StupenZverejCis> lszc = []
		if (ciselnikyList.contains(CiselnikyEnum.stupenZverejCis.name().toUpperCase()) || !ciselnikyList) {
			this.stupenZverejCis = StupenZverejCis.findAll("from StupenZverejCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TypOsobyCis> ltoc = []
		if (ciselnikyList.contains(CiselnikyEnum.typOsobyCis.name().toUpperCase()) || !ciselnikyList) {
			this.typOsobyCis = TypOsobyCis.findAll("from TypOsobyCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TitulCis> ltc = []
		if (ciselnikyList.contains(CiselnikyEnum.titulCis.name().toUpperCase()) || !ciselnikyList) {
			this.titulCis = TitulCis.findAll("from TitulCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TypTitulCis> lttc = []
		if (ciselnikyList.contains(CiselnikyEnum.typTitulCis.name().toUpperCase()) || !ciselnikyList) {
			this.typTitulCis = TypTitulCis.findAll("from TypTitulCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TypObmSposCis> ltosc = []
		if (ciselnikyList.contains(CiselnikyEnum.typObmSposCis.name().toUpperCase()) || !ciselnikyList) {
			this.typObmSposCis = TypObmSposCis.findAll("from TypObmSposCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TypPobytuCis> ltpc = []
		if (ciselnikyList.contains(CiselnikyEnum.typPobytuCis.name().toUpperCase()) || !ciselnikyList) {
			this.typPobytuCis = TypPobytuCis.findAll("from TypPobytuCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<DruhDokladuCis> lddc = []
		if (ciselnikyList.contains(CiselnikyEnum.druhDokladuCis.name().toUpperCase()) || !ciselnikyList) {
			this.druhDokladuCis = DruhDokladuCis.findAll("from DruhDokladuCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TypVztahCis> ltvc = []
		if (ciselnikyList.contains(CiselnikyEnum.typVztahCis.name().toUpperCase()) || !ciselnikyList) {
			this.typVztahCis = TypVztahCis.findAll("from TypVztahCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TypRoleVztCis> ltrvc = []
		if (ciselnikyList.contains(CiselnikyEnum.typRoleVztCis.name().toUpperCase()) || !ciselnikyList) {
			this.typRoleVztCis = TypRoleVztCis.findAll("from TypRoleVztCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<RegDomCis> lrdc = []
		if (ciselnikyList.contains(CiselnikyEnum.regDomCis.name().toUpperCase()) || !ciselnikyList) {
			this.regDomCis = RegDomCis.findAll("from RegDomCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<RegUlicaCis> lruc = []
		if (ciselnikyList.contains(CiselnikyEnum.regUlicaCis.name().toUpperCase()) || !ciselnikyList) {
			this.regUlicaCis = RegUlicaCis.findAll("from RegUlicaCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<RegVchodDomuCis> lrvdc = []
		if (ciselnikyList.contains(CiselnikyEnum.regVchodDomuCis.name().toUpperCase()) || !ciselnikyList) {
			this.regVchodDomuCis = RegVchodDomuCis.findAll("from RegVchodDomuCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<UzemnyCelokCis> lucc = []
		if (ciselnikyList.contains(CiselnikyEnum.uzemnyCelokCis.name().toUpperCase()) || !ciselnikyList) {
			this.uzemnyCelokCis = UzemnyCelokCis.findAll("from UzemnyCelokCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}		
		List<StavDomuCis> ltsdc = []
		if (ciselnikyList.contains(CiselnikyEnum.stavDomuCis.name().toUpperCase()) || !ciselnikyList) {
			this.stavDomuCis = StavDomuCis.findAll("from StavDomuCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<StavDomVchodCis> lsdvc = []
		if (ciselnikyList.contains(CiselnikyEnum.stavDomVchodCis.name().toUpperCase()) || !ciselnikyList) {
			this.stavDomVchodCis = StavDomVchodCis.findAll("from StavDomVchodCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<TypUzCelokCis> ltucc = []
		if (ciselnikyList.contains(CiselnikyEnum.typUzCelokCis.name().toUpperCase()) || !ciselnikyList) {
			this.typUzCelokCis = TypUzCelokCis.findAll("from TypUzCelokCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
		List<AvizVybavCis> lavc = []
		if (ciselnikyList.contains(CiselnikyEnum.avizVybavCis.name().toUpperCase()) || !ciselnikyList) {
			this.avizVybavCis = AvizVybavCis.findAll("from AvizVybavCis as b where (b.dtod >= :dtod or b.dtod is null) and (b.dtdo >= :dtod or b.dtdo is null) order by id",
					[dtod: dtOd],[dtdo: dtDo])
		}
	}
}
