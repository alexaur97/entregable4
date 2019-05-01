
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CompanyService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Company;

@Controller
@RequestMapping("company/administrator")
public class CompanyAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CompanyService			companyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Administrator admin = this.administratorService.findByPrincipal();
			final Collection<Company> companies = this.companyService.findAll();
			final Collection<Double> scores = this.companyService.scores();
			final Integer end = companies.size() - 1;
			result = new ModelAndView("company/listScore");
			result.addObject("requestURI", "company/administrator/list.do");
			result.addObject("companies", companies);
			result.addObject("scores", scores);
			result.addObject("end", end);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
