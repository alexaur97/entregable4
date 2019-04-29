
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculumRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.EducationData;
import domain.Rookie;
import domain.MiscellaniusData;
import domain.PersonalData;
import domain.PositionData;
import forms.CurriculumCreateForm;

@Service
@Transactional
public class CurriculumService {

	//Managed repository -------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private PersonalDataService		personalDataService;

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private MiscellaniusDataService	miscellaniusDataService;

	@Autowired
	private PositionDataService		positionDataService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public CurriculumService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Curriculum create() {
		this.rookieService.findByPrincipal();
		Curriculum result;
		result = new Curriculum();
		result.setCopy(false);
		final Collection<EducationData> educationData = new ArrayList<>();
		final Collection<MiscellaniusData> miscellaniusData = new ArrayList<>();
		final Collection<PositionData> positionData = new ArrayList<>();
		result.setEducationData(educationData);
		result.setMiscellaniusData(miscellaniusData);
		result.setPositionData(positionData);
		return result;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;
		final Actor principal = this.actorService.findByPrincipal();
		result = this.curriculumRepository.findOne(curriculumId);
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ROOKIE);
		if (principal.getUserAccount().getAuthorities().contains(auth)) {
			final Rookie h = this.rookieService.findByPrincipal();
			final Collection<Curriculum> curriculums = h.getCurriculums();
			Assert.isTrue(curriculums.contains(result));
			Assert.isTrue(result.getCopy() == false);
		}
		return result;
	}
	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		final Rookie principal = this.rookieService.findByPrincipal();
		final Collection<Curriculum> curriculums = principal.getCurriculums();

		if (curriculum.getId() != 0) {
			Assert.isTrue(curriculum.getCopy() == false);
			Assert.isTrue(curriculums.contains(curriculum));
		}

		final Curriculum result = this.curriculumRepository.save(curriculum);
		if (curriculum.getId() == 0) {
			curriculums.add(result);
			principal.setCurriculums(curriculums);
			this.rookieService.save(principal);
		}
		return result;
	}

	public void delete(final Curriculum curriculum) {
		final Rookie principal = this.rookieService.findByPrincipal();
		final Collection<Curriculum> curriculums = principal.getCurriculums();
		Assert.isTrue(curriculums.contains(curriculum));

		final Curriculum result = this.curriculumRepository.findOne(curriculum.getId());
		curriculums.remove(curriculum);
		principal.setCurriculums(curriculums);
		this.rookieService.save(principal);
		this.curriculumRepository.delete(result);
	}
	public Collection<Curriculum> findAllByPrincipal() {
		final Rookie principal = this.rookieService.findByPrincipal();
		final Collection<Curriculum> result = this.curriculumRepository.findAllByPrincipal(principal.getId());
		return result;
	}

	public Collection<Curriculum> findAllNotCopyByPrincipal() {
		final Rookie principal = this.rookieService.findByPrincipal();
		final Collection<Curriculum> result = this.curriculumRepository.findAllNotCopyByPrincipal(principal.getId());
		return result;
	}

	public Curriculum constructByForm(final CurriculumCreateForm c) {
		final Curriculum result = this.create();
		result.setIdName(c.getIdName());
		final PersonalData a = this.personalDataService.create();
		a.setFullname(c.getFullname());
		a.setGithub(c.getGithub());
		a.setLinkedin(c.getLinkedin());
		a.setPhone(c.getPhone());
		a.setStatement(c.getStatement());
		result.setPersonalData(a);
		return result;
	}

	public Curriculum reconstruct(final Curriculum curriculum, final BindingResult binding) {
		final Curriculum c = this.findOne(curriculum.getId());
		//result.setIdName(curriculum.getIdName());
		final Curriculum result = curriculum;
		result.setCopy(c.getCopy());
		result.setEducationData(c.getEducationData());
		result.setMiscellaniusData(c.getMiscellaniusData());
		result.setPersonalData(c.getPersonalData());
		result.setPositionData(c.getPositionData());

		this.validator.validate(result, binding);
		return result;
	}

	public Curriculum findByPersonalData(final int id) {
		this.rookieService.findByPrincipal();
		final Curriculum result = this.curriculumRepository.findByPersonalData(id);
		Assert.isTrue(result.getCopy() == false);
		return result;
	}

	public Curriculum copyCurriculum(final Curriculum curriculum) {
		final Rookie principal = this.rookieService.findByPrincipal();
		Assert.isTrue(principal.getCurriculums().contains(curriculum));

		final Curriculum copy = this.create();
		copy.setIdName(curriculum.getIdName());
		copy.setCopy(true);

		final PersonalData personalData = this.copyPersonalData(curriculum.getPersonalData());

		final Collection<EducationData> educationDatas = this.copyEducationData(curriculum.getEducationData());
		final Collection<MiscellaniusData> miscellaniusDatas = this.copyMiscellaniusData(curriculum.getMiscellaniusData());
		final Collection<PositionData> positionDatas = this.copyPositionData(curriculum.getPositionData());

		copy.setPersonalData(personalData);
		copy.setEducationData(educationDatas);
		copy.setMiscellaniusData(miscellaniusDatas);
		copy.setPositionData(positionDatas);

		final Curriculum result = this.save(copy);
		return result;
	}

	private Collection<PositionData> copyPositionData(final Collection<PositionData> positionDatas) {
		final Collection<PositionData> result = new ArrayList<>();
		for (final PositionData p : positionDatas) {
			final PositionData positionData = this.positionDataService.create();
			positionData.setDescription(p.getDescription());
			positionData.setEndDate(p.getEndDate());
			positionData.setStartDate(p.getStartDate());
			positionData.setTitle(p.getTitle());
			result.add(positionData);
		}
		return result;
	}

	private Collection<MiscellaniusData> copyMiscellaniusData(final Collection<MiscellaniusData> miscellaniusDatas) {
		final Collection<MiscellaniusData> result = new ArrayList<>();
		for (final MiscellaniusData m : miscellaniusDatas) {
			final MiscellaniusData miscellaniusData = this.miscellaniusDataService.create();
			miscellaniusData.setAttachments(m.getAttachments());
			miscellaniusData.setText(m.getText());
			result.add(miscellaniusData);
		}
		return result;
	}

	private Collection<EducationData> copyEducationData(final Collection<EducationData> educationDatas) {
		final Collection<EducationData> result = new ArrayList<>();
		for (final EducationData e : educationDatas) {
			final EducationData educationData = this.educationDataService.create();
			educationData.setDegree(e.getDegree());
			educationData.setEndDate(e.getEndDate());
			educationData.setInstitution(e.getInstitution());
			educationData.setMark(e.getMark());
			educationData.setStartDate(e.getStartDate());
			result.add(educationData);
		}
		return result;
	}

	private PersonalData copyPersonalData(final PersonalData personalData) {
		final PersonalData result = this.personalDataService.create();
		result.setFullname(personalData.getFullname());
		result.setGithub(personalData.getGithub());
		result.setLinkedin(personalData.getLinkedin());
		result.setPhone(personalData.getPhone());
		result.setStatement(personalData.getStatement());
		return result;
	}

	//Other Methods--------------------

	public Collection<Curriculum> findByRookie(final Integer idH) {
		final Collection<Curriculum> res = this.curriculumRepository.findAllByPrincipal(idH);
		return res;
	}

	public Curriculum findByPositionData(final int positionDataId) {
		final Curriculum res = this.curriculumRepository.findByPositonData(positionDataId);
		Assert.isTrue(res.getCopy() == false);
		return res;
	}

	public Curriculum findByMiscellaneousData(final MiscellaniusData miscellaneousData) {
		final Curriculum res = this.curriculumRepository.findByMiscellaneousData(miscellaneousData.getId());
		Assert.isTrue(res.getCopy() == false);
		return res;
	}

	public Curriculum findByEducationData(final EducationData educationData) {
		final Curriculum res = this.curriculumRepository.findByEducationData(educationData.getId());
		Assert.isTrue(res.getCopy() == false);
		return res;
	}

	public Collection<Curriculum> findAllByPrincipalNoCopy() {
		final Rookie principal = this.rookieService.findByPrincipal();
		final Collection<Curriculum> result = this.curriculumRepository.findAllByPrincipalNoCopy(principal.getId());
		return result;
	}
}
