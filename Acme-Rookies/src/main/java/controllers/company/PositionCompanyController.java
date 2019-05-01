
package controllers.company;

import java.util.Collection;
import java.util.Date;

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
import services.PositionService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/position/company/")
public class PositionCompanyController extends AbstractController {

	//Servicio
	@Autowired
	private CompanyService	companyService;

	//Servicios
	@Autowired
	private ProblemService	problemService;

	@Autowired
	private PositionService	positionService;


	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Company company = this.companyService.findByPrincipal();
			Collection<Position> positions;
			Collection<Position> positionsCancelled;

			positions = this.positionService.findByCompany(company.getId());
			positionsCancelled = this.positionService.findByCompanyCancelled(company.getId());
			result = new ModelAndView("position/myList");
			result.addObject("requestURI", "position/company/myList.do");
			result.addObject("positions", positions);
			result.addObject("positionsCancelled", positionsCancelled);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		try {
			Position position;
			position = new Position();

			//			final Company c = this.companyService.findByPrincipal();
			//			position.setCompany(c);

			result = this.createEditModelAndView(position);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView res;
		try {

			final Position position = this.positionService.findOne(positionId);
			Assert.notNull(position);
			Assert.isTrue(position.getMode().equals("DRAFT"));
			final Integer idC = this.companyService.findByPrincipal().getId();
			final Collection<Position> positions = this.positionService.findByCompany(idC);
			Assert.isTrue(positions.contains(position));
			res = this.createEditModelAndView(position);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("position") Position position, final BindingResult binding) {
		ModelAndView res;
		position = this.positionService.reconstruct(position, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(position);
		else
			try {
				final Date date = new Date();
				Assert.isTrue(position.getDeadline().after(date));
				this.positionService.save(position);
				res = new ModelAndView("redirect:/position/company/myList.do");

			} catch (final Throwable oops) {
				if (!position.getDeadline().after(new Date()))
					res = this.createEditModelAndView(position, "position.error.date");
				else
					res = this.createEditModelAndView(position, "position.commit.error");

			}

		return res;
	}
	@RequestMapping(value = "/editMode", method = RequestMethod.GET)
	public ModelAndView editMode(@RequestParam final int positionId) {
		ModelAndView res;
		try {

			Position position = this.positionService.findOne(positionId);
			Assert.notNull(position);
			final Integer idC = this.companyService.findByPrincipal().getId();
			final Collection<Position> positions = this.positionService.findByCompany(idC);
			Assert.isTrue(positions.contains(position));
			position = this.positionService.saveMode(position);
			this.positionService.save(position);

			res = new ModelAndView("redirect:/position/company/myList.do");
			res.addObject("position", position);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int positionId) {
		ModelAndView res;
		try {
			//final Company company = this.companyService.findByPrincipal();

			Position position = this.positionService.findOne(positionId);
			Assert.notNull(position);
			final Integer idC = this.companyService.findByPrincipal().getId();
			final Collection<Position> positions = this.positionService.findByCompany(idC);
			Assert.isTrue(positions.contains(position));

			position = this.positionService.cancel(position);
			this.positionService.save(position);

			//	final Collection<Position> positionsCancelled = this.positionService.findByCompanyCancelled(company.getId());

			res = new ModelAndView("redirect:/position/company/myList.do");
			//			res.addObject("position", position);
			//			res.addObject("positionsCancelled", positionsCancelled);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position, final BindingResult binding) {
		ModelAndView result;
		final Position pos = this.positionService.findOne(position.getId());
		try {

			Assert.isTrue(pos.getMode().equals("DRAFT"));
			this.positionService.delete(pos);
			result = new ModelAndView("redirect:/position/company/myList.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(pos, oops.getMessage());

			final String msg = oops.getMessage();
			if (msg.equals("positioncannotDelete")) {
				final Boolean positioncannotDelete = true;
				result.addObject("positioncannotDelete", positioncannotDelete);

			}
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(final Position position) {
		return this.createEditModelAndView(position, null);
	}
	protected ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		final ModelAndView res;
		res = new ModelAndView("position/edit");
		final Collection<Problem> problems = this.problemService.findAllByPrincipalIdFinal();
		res.addObject("problems", problems);
		res.addObject("position", position);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result;
		final Position position;

		try {
			final Company company = this.companyService.findByPrincipal();
			Assert.notNull(positionId);
			position = this.positionService.findOne(positionId);
			final String mode = position.getMode();
			if (mode.equals("DRAFT"))
				Assert.isTrue(position.getCompany().equals(company));
			final Boolean b = position.getProblems().isEmpty();
			result = new ModelAndView("position/show");
			result.addObject("position", position);
			result.addObject("b", b);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
