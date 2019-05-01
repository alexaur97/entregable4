
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.CompanyService;
import services.FinderService;
import services.ItemService;
import services.PositionService;
import services.ProviderService;
import services.RookieService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Provider;
import domain.Rookie;

@Controller
@RequestMapping("/stats/administrator")
public class StatsAdministratorController extends AbstractController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private ItemService			itemService;

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		result = new ModelAndView("stats/display");

		try {
			final Collection<Double> positionsPerCompany = this.positionService.statsPositionsPerCompany();
			final Collection<Double> applicationsPerRookie = this.applicationService.statsApplicationsPerRookie();
			final Collection<Company> companiesHaveOfferedMorePositions = this.companyService.companiesHaveOfferedMorePositions();
			final Collection<Rookie> rookiesHaveMadeMoreApplications = this.rookieService.rookiesHaveMadeMoreApplications();
			final Collection<Double> salaryOfferedPerPosition = this.positionService.statsSalaryOfferedPerPosition();
			final Collection<Position> worstPositionsSalary = this.positionService.worstPositionsSalary();
			final Collection<Position> bestPositionsSalary = this.positionService.bestPositionsSalary();
			final Collection<Double> curriculaPerRookie = this.rookieService.statsCurriculaPerRookie();
			final Collection<Double> statsResultsFinders = this.finderService.statsResultsFinders();
			final Collection<Double> emptyVsNonEmptyFindersRatio = this.finderService.emptyVsNonEmptyFindersRatio();
			final Collection<Object> statsAuditScorePerPosition = this.auditService.statsAuditScorePerPosition();
			final Collection<Object> statsAuditScorePerCompany = this.auditService.statsAuditScorePerCompany();
			final Collection<Company> companiesWithHighestAuditScore = this.companyService.companiesWithHighestAuditScore();
			final Double avgSalaryHighestPositions = this.positionService.avgSalaryHighestPositions();
			final Collection<Double> statsNumberItemsPerProvider = this.itemService.statsNumberItemsPerProvider();
			final Collection<Provider> top5ProvidersPerItems = this.providerService.top5ProvidersPerItems();
			final Collection<Double> statsSponsorshipsByProvider = this.sponsorshipService.statsSponsorshipsByProvider();
			final Collection<Double> statsSponsorshipsByPosition = this.sponsorshipService.statsSponsorshipsByPosition();
			final Collection<Provider> providersWithMoreThan10pcSponsorships = this.providerService.providersWithMoreThan10pcSponsorships();

			result.addObject("providersWithMoreThan10pcSponsorships", providersWithMoreThan10pcSponsorships);
			result.addObject("statsSponsorshipsByPosition", statsSponsorshipsByPosition);
			result.addObject("statsSponsorshipsByProvider", statsSponsorshipsByProvider);
			result.addObject("top5ProvidersPerItems", top5ProvidersPerItems);
			result.addObject("statsNumberItemsPerProvider", statsNumberItemsPerProvider);
			result.addObject("avgSalaryHighestPositions", avgSalaryHighestPositions);
			result.addObject("companiesWithHighestAuditScore", companiesWithHighestAuditScore);
			result.addObject("statsAuditScorePerCompany", statsAuditScorePerCompany);
			result.addObject("statsAuditScorePerPosition", statsAuditScorePerPosition);
			result.addObject("statsResultsFinders", statsResultsFinders);
			result.addObject("emptyVsNonEmptyFindersRatio", emptyVsNonEmptyFindersRatio);
			result.addObject("curriculaPerRookie", curriculaPerRookie);
			result.addObject("worstPositionsSalary", worstPositionsSalary);
			result.addObject("bestPositionsSalary", bestPositionsSalary);
			result.addObject("salaryOfferedPerPosition", salaryOfferedPerPosition);
			result.addObject("rookiesHaveMadeMoreApplications", rookiesHaveMadeMoreApplications);
			result.addObject("companiesHaveOfferedMorePositions", companiesHaveOfferedMorePositions);
			result.addObject("applicationsPerRookie", applicationsPerRookie);
			result.addObject("positionsPerCompany", positionsPerCompany);
			result.addObject("requestURI", "/stats/administrator/display.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");

		}
		return result;
	}
}
