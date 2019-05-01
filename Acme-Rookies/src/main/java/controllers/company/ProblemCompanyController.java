
package controllers.company;

import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private CompanyService	companyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Problem> problems = this.problemService.findAllByPrincipalId();

			result = new ModelAndView("problem/myList");
			result.addObject("problems", problems);
			result.addObject("requestURI", "problem/company/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Problem problem = this.problemService.create();
			result = this.createEditModelAndView(problem);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int problemId) {
		ModelAndView result;
		try {
			//			final Company company = this.companyService.findByPrincipal();
			final Problem problem = this.problemService.findOne(problemId);
			//			Assert.isTrue(problem.getCompany().equals(company));
			final Boolean b = problem.getAttachments().isEmpty();
			result = new ModelAndView("problem/show");
			result.addObject("problem", problem);
			result.addObject("b", b);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		try {
			final Company company = this.companyService.findByPrincipal();
			final Problem problem = this.problemService.findOne(problemId);
			Assert.isTrue(!problem.getMode().equals("FINAL"));
			Assert.isTrue(problem.getCompany().equals(company));
			result = this.createEditModelAndView(problem);
			result.addObject("problem", problem);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("problem") Problem problem, final BindingResult binding) {
		ModelAndView result;
		problem = this.problemService.reconstruct(problem, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(problem);
		else
			try {
				final Boolean b = Utils.validateURL(problem.getAttachments());
				if (!b)
					result = this.createEditModelAndView(problem, "problem.url.error");
				else {
					final Problem saved = this.problemService.save(problem);
					result = new ModelAndView("redirect:/problem/company/show.do?problemId=" + saved.getId());
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(problem, "problem.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int problemId) {
		ModelAndView result;
		try {
			final Problem problem = this.problemService.findOne(problemId);
			this.problemService.delete(problem);
			result = new ModelAndView("redirect:/problem/company/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Problem problem) {
		return this.createEditModelAndView(problem, null);
	}

	protected ModelAndView createEditModelAndView(final Problem problem, final String messageCode) {
		ModelAndView result;
		if (problem.getId() == 0)
			result = new ModelAndView("problem/create");
		else
			result = new ModelAndView("problem/edit");
		problem.setMode("DRAFT");
		result.addObject("problem", problem);
		result.addObject("message", messageCode);
		return result;
	}

}
