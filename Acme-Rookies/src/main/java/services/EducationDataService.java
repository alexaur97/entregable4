
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import domain.Curriculum;
import domain.EducationData;

@Service
@Transactional
public class EducationDataService {

	//Managed repository -------------------
	@Autowired
	private EducationDataRepository	educationDataRepository;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	CurriculumService				curriculumService;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public EducationDataService() {
		super();
	}

	//Simple CRUD methods--------------------

	public EducationData create() {
		EducationData result;

		result = new EducationData();

		return result;
	}

	public Collection<EducationData> findAll() {
		Collection<EducationData> result;

		result = this.educationDataRepository.findAll();

		return result;
	}

	public EducationData findOne(final int educationDataId) {
		EducationData result;

		result = this.educationDataRepository.findOne(educationDataId);

		return result;
	}

	public void save(final EducationData educationData, final Curriculum curriculum) {
		Assert.notNull(educationData);
		if (educationData.getEndDate() != null)
			Assert.isTrue(educationData.getStartDate().before(educationData.getEndDate()));

		if (educationData.getId() != 0) {
			final Curriculum c = this.curriculumService.findByEducationData(educationData);
			Assert.isTrue(c.getCopy() == false);
		} else {
			final Collection<EducationData> ed = curriculum.getEducationData();
			ed.add(educationData);
			curriculum.setEducationData(ed);
			this.curriculumService.save(curriculum);
		}
		this.educationDataRepository.save(educationData);
	}

	public void delete(final EducationData educationData) {
		this.rookieService.findByPrincipal();
		final Curriculum c = this.curriculumService.findByEducationData(educationData);
		Assert.isTrue(c.getCopy() == false);
		final Collection<EducationData> pd = c.getEducationData();
		pd.remove(educationData);
		c.setEducationData(pd);
		this.curriculumService.save(c);
		this.educationDataRepository.delete(educationData);
	}

	//Other Methods--------------------
}
