
package controllers.rookie;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CurriculumService;
import services.RookieService;
import services.PositionService;
import controllers.AbstractController;
import domain.Application;
import domain.Curriculum;
import domain.Rookie;
import domain.Position;

@Controller
@RequestMapping("/application/rookie/")
public class ApplicationRookieController extends AbstractController {

	//Repository
	@Autowired
	private RookieService		rookieService;

	//Servicios

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CurriculumService	curriculumService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		ModelAndView result;
		try {
			final Rookie rookie = this.rookieService.findByPrincipal();
			final Application application = this.applicationService.findOne(applicationId);
			Assert.isTrue(application.getRookie().equals(rookie));
			final Boolean b = application.getProblem().getAttachments().isEmpty();
			result = new ModelAndView("application/show");
			result.addObject("application", application);
			result.addObject("b", b);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Rookie rookie = this.rookieService.findByPrincipal();
			final Collection<Application> applications;
			Collection<Application> applicationsPending;
			applicationsPending = this.applicationService.findApplicationsPendingByRookie(rookie.getId());
			applications = this.applicationService.findApplicationsRookie(rookie.getId());
			result = new ModelAndView("application/list");
			result.addObject("applications", applications);
			result.addObject("applicationsPending", applicationsPending);
			result.addObject("requestURI", "application/rookie/list.do");
			final String p = "PENDING";
			result.addObject("p", p);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final Application application;
		application = new Application();

		try {

			final Collection<Position> positions = this.positionService.findPositionsFinal();
			final Collection<Curriculum> curriculums = this.curriculumService.findAllNotCopyByPrincipal();
			this.rookieService.findByPrincipal();
			result = new ModelAndView("application/create");
			result.addObject("application", application);
			result.addObject("curriculums", curriculums);
			result.addObject("positions", positions);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Application application, final BindingResult binding) {
		ModelAndView res;
		final Application applicationFinal = this.applicationService.recostructionCreate(application, binding);
		if (binding.hasErrors()) {
			final Collection<Position> positions = this.positionService.findPositionsFinal();
			final Collection<Curriculum> curriculums = this.curriculumService.findAllByPrincipal();
			res = new ModelAndView("application/create");
			res.addObject("curriculums", curriculums);
			res.addObject("positions", positions);
		} else
			try {
				this.rookieService.findByPrincipal();
				this.applicationService.saveRookie(applicationFinal);
				res = new ModelAndView("redirect:/application/rookie/list.do");

			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/#");
			}
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {

		ModelAndView result;
		final Application application;

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(application.getStatus().equals("PENDING"));
			final Rookie rookie = this.rookieService.findByPrincipal();
			final Collection<Position> positions = this.positionService.findPositionsFinal();
			Assert.isTrue(application.getRookie().equals(rookie));
			result = new ModelAndView("application/edit");
			result.addObject("application", application);
			result.addObject("positions", positions);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final Application application, final BindingResult binding) {
		ModelAndView res;
		final Application applicationFinal = this.applicationService.recostructionEdit(application, binding);
		if (binding.hasErrors()) {
			final Collection<Position> positions = this.positionService.findPositionsFinal();
			final Collection<Curriculum> curriculums = this.curriculumService.findAllByPrincipal();
			res = new ModelAndView("application/create");
			res.addObject("curriculums", curriculums);
			res.addObject("positions", positions);
		} else
			try {
				this.rookieService.findByPrincipal();
				this.applicationService.saveRookie(applicationFinal);
				res = new ModelAndView("redirect:/application/rookie/list.do");

			} catch (final Throwable oops) {
				if (application.getExplanation().isEmpty() || application.getCodeLink().isEmpty()) {
					res = new ModelAndView("application/edit");
					res.addObject("application", application);
					res.addObject("message", "application.explanationLink.error");

				} else
					res = new ModelAndView("redirect:/#");
			}
		return res;
	}
}
