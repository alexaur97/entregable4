
package services;

import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaniusDataRepository;
import domain.Curriculum;
import domain.MiscellaniusData;

@Service
@Transactional
public class MiscellaniusDataService {

	//Managed repository -------------------
	@Autowired
	private MiscellaniusDataRepository	miscellaniusDataRepository;

	@Autowired
	private RookieService				rookieService;

	@Autowired
	private CurriculumService			curriculumService;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public MiscellaniusDataService() {
		super();
	}

	//Simple CRUD methods--------------------

	public MiscellaniusData create() {
		MiscellaniusData result;

		result = new MiscellaniusData();

		return result;
	}

	public Collection<MiscellaniusData> findAll() {
		Collection<MiscellaniusData> result;

		result = this.miscellaniusDataRepository.findAll();

		return result;
	}

	public MiscellaniusData findOne(final int miscellaniusDataId) {
		MiscellaniusData result;

		result = this.miscellaniusDataRepository.findOne(miscellaniusDataId);

		return result;
	}

	public void save(final MiscellaniusData miscellaniusData, final Curriculum curriculum) {
		Assert.notNull(miscellaniusData);
		final Collection<String> attach = miscellaniusData.getAttachments();
		Assert.isTrue(Utils.validateURL(attach));

		if (miscellaniusData.getId() != 0) {
			final Curriculum c = this.curriculumService.findByMiscellaneousData(miscellaniusData);
			Assert.isTrue(c.getCopy() == false);
		} else {
			final Collection<MiscellaniusData> ed = curriculum.getMiscellaniusData();
			ed.add(miscellaniusData);
			curriculum.setMiscellaniusData(ed);
			this.curriculumService.save(curriculum);
		}
		this.miscellaniusDataRepository.save(miscellaniusData);
	}

	public void delete(final MiscellaniusData miscellaniusData) {
		this.rookieService.findByPrincipal();
		final Curriculum c = this.curriculumService.findByMiscellaneousData(miscellaniusData);
		Assert.isTrue(c.getCopy() == false);
		final Collection<MiscellaniusData> pd = c.getMiscellaniusData();
		pd.remove(miscellaniusData);
		c.setMiscellaniusData(pd);
		this.curriculumService.save(c);
		this.miscellaniusDataRepository.delete(miscellaniusData);
	}

	//Other Methods--------------------
}
