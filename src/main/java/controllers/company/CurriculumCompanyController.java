
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import domain.Curriculum;
import domain.EducationData;
import domain.MiscellaniusData;
import domain.PositionData;

@Controller
@RequestMapping("/curriculum/company/")
public class CurriculumCompanyController extends AbstractController {

	//Repository
	@Autowired
	private CompanyService		companyService;

	//Servicios

	@Autowired
	private ApplicationService	applicationService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		ModelAndView result;
		try {
			final Company company = this.companyService.findByPrincipal();
			final Application application = this.applicationService.findOne(applicationId);
			final Collection<Application> apps = this.applicationService.findApplicationsByCompany(company.getId());
			final Curriculum curriculum = application.getCurriculum();
			Assert.isTrue(apps.contains(application));
			Assert.isTrue(application.getStatus() != "PENDING");
			final Collection<EducationData> educationData = curriculum.getEducationData();
			final Collection<MiscellaniusData> miscellaniusData = curriculum.getMiscellaniusData();
			final Collection<PositionData> positionData = curriculum.getPositionData();
			result = new ModelAndView("curriculum/show");
			result.addObject("application", application);
			result.addObject("curriculum", curriculum);
			result.addObject("application", application);
			result.addObject("educationDatas", educationData);
			result.addObject("miscellaniusDatas", miscellaniusData);
			result.addObject("positionDatas", positionData);
			result.addObject("requestURI", "/curriculum/company/show.do?curriculumId=" + curriculum.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

}
