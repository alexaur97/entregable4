
package controllers.rookie;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.RookieService;
import services.PositionService;
import controllers.AbstractController;
import domain.Finder;
import domain.Position;

@Controller
@RequestMapping("finder/rookie")
public class FinderRookieController extends AbstractController {

	@Autowired
	RookieService	rookieService;

	@Autowired
	PositionService	positionService;

	@Autowired
	FinderService	finderService;


	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result = new ModelAndView("finder/view");

		try {
			final Finder finder = this.finderService.getFinderFromRookie(this.rookieService.findByPrincipal().getId());
			final Collection<Position> positions = finder.getPositions();

			result.addObject("positions", positions);
			result.addObject("finder", finder);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finder, final BindingResult binding) {
		ModelAndView res = new ModelAndView("finder/view");
		final Collection<Position> positions = finder.getPositions();
		if (binding.hasErrors()) {
			res.addObject("positions", positions);
			res.addObject("finder", finder);
		} else
			try {
				finder = this.finderService.reconstruct(finder);
				this.finderService.save(finder);
				res = new ModelAndView("redirect:/finder/rookie/view.do");
			} catch (final Throwable oops) {
				res.addObject("message", "finder.commit.error");
				res.addObject("positions", positions);
				res.addObject("finder", finder);
			}

		return res;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "clean")
	public ModelAndView clean(Finder finder, final BindingResult binding) {
		ModelAndView res = new ModelAndView("finder/view");
		final Collection<Position> positions = new ArrayList<Position>();

		if (binding.hasErrors()) {
			res.addObject("positions", positions);
			res.addObject("finder", finder);
		} else
			try {
				finder = this.finderService.reconstruct(finder);
				this.finderService.saveAfterClean(finder);
				res = new ModelAndView("redirect:/finder/rookie/view.do");
			} catch (final Throwable oops) {
				res.addObject("message", "finder.clean.commit.error");
				res.addObject("positions", positions);
				res.addObject("finder", finder);
			}

		return res;
	}

}
