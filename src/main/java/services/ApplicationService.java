
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Company;
import domain.Curriculum;
import domain.Rookie;
import domain.Problem;

@Service
@Transactional
public class ApplicationService {

	//Managed repository -------------------
	@Autowired
	private ApplicationRepository	applicationRepository;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private RookieService			rookieService;
	@Autowired
	private ProblemService			problemService;
	@Autowired
	private CurriculumService		curriculumService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private Validator				validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Application create() {
		Application result;

		result = new Application();

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public void save(final Application application) {
		Assert.notNull(application);
		if (application.getId() != 0)
			this.messageService.changedStatus(application.getProblem().getCompany());
		this.applicationRepository.save(application);
	}
	public Application saveCompany(final Application application) {
		final Application result;
		Assert.notNull(application);
		final Company company = this.companyService.findByPrincipal();
		Assert.isTrue(company.equals(application.getPosition().getCompany()));
		result = this.applicationRepository.save(application);
		this.messageService.changedStatus(application.getRookie());
		return result;

	}
	public void saveRookie(final Application application) {
		Assert.notNull(application);
		final Rookie rookie = this.rookieService.findByPrincipal();
		Assert.isTrue(application.getRookie().equals(rookie));
		if (application.getId() == 0)
			Assert.isTrue(application.getStatus().equals("PENDING"));
		else {
			Assert.isTrue(!(application.getExplanation() == null));
			if (!(application.getExplanation() == null))
				Assert.isTrue(!(application.getExplanation().isEmpty()));
			Assert.isTrue(!(application.getCodeLink() == null));
			if (!(application.getCodeLink() == null))
				Assert.isTrue(!(application.getCodeLink().isEmpty()));

		}
		this.applicationRepository.save(application);
	}

	public void delete(final Application application) {
		this.applicationRepository.delete(application);
	}

	//Other Methods--------------------

	public Collection<Double> statsApplicationsPerRookie() {
		final Collection<Double> result = this.applicationRepository.statsApplicationsPerRookie();
		Assert.notNull(result);
		return result;
	}

	public void delete(final Collection<Application> applications) {
		this.applicationRepository.delete(applications);
	}

	public Collection<Application> findAllByProblem(final int id) {
		final Collection<Application> result = this.applicationRepository.findAllByProblem(id);
		return result;
	}

	//Other Methods--------------------
	public Collection<Application> findApplicationsByCompany(final int id) {
		final Collection<Application> result = this.applicationRepository.findApplicationsByCompany(id);
		return result;
	}
	public Application acceptApplicationChanges(final int applicationId) {
		Assert.notNull(applicationId);
		final Company company = this.companyService.findByPrincipal();
		final Application application = this.applicationRepository.findOne(applicationId);
		Assert.isTrue(company.equals(application.getPosition().getCompany()));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		application.setStatus("ACCEPTED");
		return application;

	}
	public Application rejectApplicationChanges(final int applicationId) {
		Assert.notNull(applicationId);
		final Company company = this.companyService.findByPrincipal();
		final Application application = this.applicationRepository.findOne(applicationId);
		Assert.isTrue(company.equals(application.getPosition().getCompany()));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		application.setStatus("REJECTED");
		return application;

	}
	public Collection<Application> findApplicationsByRookie(final int id) {
		final Collection<Application> result = this.applicationRepository.findApplicationsByRookie(id);
		return result;
	}

	public Collection<Application> findApplicationsPendingByRookie(final int id) {
		final Collection<Application> result = this.applicationRepository.findApplicationsPendingByRookie(id);
		return result;
	}

	public Collection<Application> findApplicationsPendingByCompany(final int id) {
		final Collection<Application> result = this.applicationRepository.findApplicationsPendingByCompany(id);
		return result;
	}

	public Collection<Application> findApplicationsCompany(final int id) {
		final Collection<Application> result = this.applicationRepository.findApplicationsCompany(id);
		return result;
	}

	public Collection<Application> findApplicationsRookie(final int id) {
		final Collection<Application> result = this.applicationRepository.findApplicationsRookie(id);
		return result;
	}
	public Application recostructionCreate(final Application application, final BindingResult binding) {
		if (application.getCurriculum() != null) {
			final Curriculum curriculumCopy = this.curriculumService.copyCurriculum(application.getCurriculum());
			application.setCurriculum(curriculumCopy);
		}
		application.setRookie(this.rookieService.findByPrincipal());
		application.setMoment(new Date());
		application.setStatus("PENDING");
		if (!(application.getPosition() == null)) {
			final Problem problem = this.problemService.findProblemByPosition(application.getPosition().getId());
			application.setProblem(problem);
		}

		this.validator.validate(application, binding);
		return application;

	}
	public Application recostructionEdit(final Application application, final BindingResult binding) {

		final Application a = this.applicationRepository.findOne(application.getId());
		application.setCurriculum(a.getCurriculum());
		application.setRookie(a.getRookie());
		application.setMoment(a.getMoment());
		application.setPosition(a.getPosition());
		application.setProblem(a.getProblem());
		application.setStatus("SUBMITTED");
		final Date moment = new Date();
		application.setSubmitMoment(moment);
		this.validator.validate(application, binding);
		return application;

	}
}
