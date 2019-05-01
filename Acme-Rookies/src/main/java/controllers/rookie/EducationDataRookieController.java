
package controllers.rookie;

import java.util.Collection;

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
import services.EducationDataService;
import services.RookieService;
import domain.Curriculum;
import domain.EducationData;
import domain.Rookie;

@Controller
@RequestMapping("/educationData/rookie/")
public class EducationDataRookieController {

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculumService		curriculumService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {

		ModelAndView result;
		EducationData educationData;

		try {

			educationData = this.educationDataService.create();
			final Curriculum c = this.curriculumService.findOne(curriculumId);
			final Rookie h = this.rookieService.findByPrincipal();

			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(h.getId());
			Assert.isTrue(curriculums.contains(c));

			educationData.setId(0);

			result = new ModelAndView("educationData/edit");
			result.addObject("educationData", educationData);
			result.addObject("curriculum", c);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		ModelAndView res;
		try {

			final EducationData educationData = this.educationDataService.findOne(educationDataId);
			Assert.notNull(educationData);
			final Integer idH = this.rookieService.findByPrincipal().getId();
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(idH);
			final Curriculum curriculum = this.curriculumService.findByEducationData(educationData);
			Assert.isTrue(curriculums.contains(curriculum));
			res = this.createEditModelAndView(educationData, null, curriculum);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationData educationData, final BindingResult binding, @RequestParam("curriculumId") final int curriculumId) {
		ModelAndView res;

		if (binding.hasErrors()) {
			final Curriculum c = this.curriculumService.findOne(curriculumId);
			res = this.createEditModelAndView(educationData, c);
		} else
			try {
				final Curriculum c = this.curriculumService.findOne(curriculumId);
				this.educationDataService.save(educationData, c);

				res = new ModelAndView("redirect:/curriculum/rookie/show.do?curriculumId=" + c.getId());

			} catch (final Throwable oops) {
				final Curriculum c = this.curriculumService.findOne(curriculumId);

				if (educationData.getStartDate().after(educationData.getEndDate()))
					res = this.createEditModelAndView(educationData, "educationData.error.date2", c);

				else
					res = this.createEditModelAndView(educationData, "educationData.commit.error", c);

			}

		return res;
	}
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationData educationData, final BindingResult binding) {
		ModelAndView result;

		try {
			this.educationDataService.delete(educationData);
			result = new ModelAndView("redirect:/curriculum/rookie/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int educationDataId) {
		ModelAndView result;
		final EducationData educationData;

		try {

			this.rookieService.findByPrincipal();
			Assert.notNull(educationDataId);

			educationData = this.educationDataService.findOne(educationDataId);

			final Integer idH = this.rookieService.findByPrincipal().getId();
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(idH);
			final Curriculum curriculum = this.curriculumService.findByEducationData(educationData);
			Assert.isTrue(curriculums.contains(curriculum));

			result = new ModelAndView("educationData/show");
			result.addObject("educationData", educationData);
			result.addObject("curriculum", curriculum);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationData educationData, final Curriculum c) {
		return this.createEditModelAndView(educationData, null, c);
	}
	protected ModelAndView createEditModelAndView(final EducationData educationData, final String messageCode, Curriculum c) {
		final ModelAndView res;
		if (c.equals(null))
			c = this.curriculumService.findByEducationData(educationData);
		res = new ModelAndView("educationData/edit");
		res.addObject("educationData", educationData);
		res.addObject("message", messageCode);
		res.addObject("curriculum", c);

		return res;
	}

}
