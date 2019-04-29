
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Curriculum;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	//Managed repository -------------------
	@Autowired
	private PersonalDataRepository	personalDataRepository;

	@Autowired
	private CurriculumService		curriculumService;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public PersonalDataService() {
		super();
	}

	//Simple CRUD methods--------------------

	public PersonalData create() {
		PersonalData result;

		result = new PersonalData();

		return result;
	}

	public Collection<PersonalData> findAll() {
		Collection<PersonalData> result;

		result = this.personalDataRepository.findAll();

		return result;
	}

	public PersonalData findOne(final int personalDataId) {
		PersonalData result;

		result = this.personalDataRepository.findOne(personalDataId);

		return result;
	}

	public PersonalData save(final PersonalData personalData) {
		Assert.notNull(personalData);
		if (personalData.getId() != 0) {
			final Curriculum c = this.curriculumService.findByPersonalData(personalData.getId());
			final Collection<Curriculum> allPrincipal = this.curriculumService.findAllByPrincipal();
			Assert.isTrue(c.getCopy() == false);
			Assert.isTrue(allPrincipal.contains(c));
		}
		final PersonalData result = this.personalDataRepository.save(personalData);
		return result;
	}

	//Other Methods--------------------
}
