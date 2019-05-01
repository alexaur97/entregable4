
package controllers.rookie;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.RookieService;
import services.PositionDataService;
import domain.Curriculum;
import domain.Rookie;
import domain.PositionData;

@Controller
@RequestMapping("/positionData/rookie/")
public class PositionDataHackerController {

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private CurriculumService	curriculumService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam("curriculumId") final int curriculumId) {

		ModelAndView result;
		PositionData positionData;

		try {
			positionData = this.positionDataService.create();
			final Curriculum c = this.curriculumService.findOne(curriculumId);
			final Rookie h = this.rookieService.findByPrincipal();
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(h.getId());
			Assert.isTrue(curriculums.contains(c));
			positionData.setId(0);

			result = new ModelAndView("positionData/edit");
			result.addObject("positionData", positionData);
			result.addObject("curriculum", c);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionDataId) {
		ModelAndView res;
		try {

			final PositionData positionData = this.positionDataService.findOne(positionDataId);
			Assert.notNull(positionData);
			final Integer idH = this.rookieService.findByPrincipal().getId();
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(idH);
			final Curriculum curriculum = this.curriculumService.findByPositionData(positionDataId);
			Assert.isTrue(curriculums.contains(curriculum));
			res = this.createEditModelAndView(positionData, null, curriculum);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PositionData positionData, final BindingResult binding, @RequestParam("curriculumId") final int curriculumId) {
		ModelAndView res;

		if (binding.hasErrors()) {
			final Curriculum c = this.curriculumService.findOne(curriculumId);
			res = this.createEditModelAndView(positionData, c);
		} else
			try {
				final Curriculum c = this.curriculumService.findOne(curriculumId);
				this.positionDataService.save(positionData, c);

				res = new ModelAndView("redirect:/curriculum/rookie/show.do?curriculumId=" + c.getId());

			} catch (final Throwable oops) {
				final Curriculum c = this.curriculumService.findOne(curriculumId);

				if (positionData.getStartDate().after(positionData.getEndDate()))
					res = this.createEditModelAndView(positionData, "positionData.error.date2", c);

				else if (positionData.getStartDate().after(new Date()))
					res = this.createEditModelAndView(positionData, "positionData.error.date", c);
				else
					res = this.createEditModelAndView(positionData, "positionData.commit.error", c);

			}

		return res;
	}
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PositionData positionData, final BindingResult binding) {
		ModelAndView result;
		try {
			this.positionDataService.delete(positionData);
			result = new ModelAndView("redirect:/curriculum/rookie/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final PositionData positionData, final Curriculum c) {
		return this.createEditModelAndView(positionData, null, c);
	}
	protected ModelAndView createEditModelAndView(final PositionData positionData, final String messageCode, Curriculum c) {
		final ModelAndView res;

		if (c.equals(null))
			c = this.curriculumService.findByPositionData(positionData.getId());

		res = new ModelAndView("positionData/edit");
		res.addObject("positionData", positionData);
		res.addObject("message", messageCode);
		res.addObject("curriculum", c);

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionDataId) {
		ModelAndView result;
		final PositionData positionData;

		try {
			this.rookieService.findByPrincipal();
			Assert.notNull(positionDataId);
			positionData = this.positionDataService.findOne(positionDataId);
			final Curriculum cu = this.curriculumService.findByPositionData(positionData.getId());
			final Integer idH = this.rookieService.findByPrincipal().getId();
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(idH);
			Assert.isTrue(curriculums.contains(cu));

			result = new ModelAndView("positionData/show");
			result.addObject("positionData", positionData);
			result.addObject("curriculum", cu);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
