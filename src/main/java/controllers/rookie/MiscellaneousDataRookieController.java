
package controllers.rookie;

import java.util.Collection;

import javax.validation.Valid;

import miscellaneous.Utils;

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
import services.MiscellaniusDataService;
import domain.Curriculum;
import domain.MiscellaniusData;

@Controller
@RequestMapping("/miscellaneousData/rookie/")
public class MiscellaneousDataRookieController {

	@Autowired
	private MiscellaniusDataService	miscellaneousDataService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculumService		curriculumService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {

		ModelAndView result;

		try {
			this.rookieService.findByPrincipal();
			MiscellaniusData miscellaneousData;
			miscellaneousData = this.miscellaneousDataService.create();
			final Curriculum c = this.curriculumService.findOne(curriculumId);
			result = new ModelAndView("miscellaneousData/edit");
			result.addObject("miscellaniusData", miscellaneousData);
			result.addObject("curriculum", c);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		ModelAndView res;
		try {

			final MiscellaniusData miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
			Assert.notNull(miscellaneousData);
			final Integer idH = this.rookieService.findByPrincipal().getId();

			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(idH);
			final Curriculum curriculum = this.curriculumService.findByMiscellaneousData(miscellaneousData);
			Assert.isTrue(curriculums.contains(curriculum));
			res = this.createEditModelAndView(miscellaneousData, null, curriculum);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaniusData miscellaniusData, final BindingResult binding, @RequestParam("curriculumId") final int curriculumId) {
		ModelAndView res;

		if (binding.hasErrors()) {
			final Curriculum c = this.curriculumService.findOne(curriculumId);

			res = this.createEditModelAndView(miscellaniusData, c);
		} else
			try {
				final Curriculum c = this.curriculumService.findOne(curriculumId);
				this.miscellaneousDataService.save(miscellaniusData, c);
				res = new ModelAndView("redirect:/curriculum/rookie/show.do?curriculumId=" + c.getId());

			} catch (final Throwable oops) {

				final Curriculum c = this.curriculumService.findOne(curriculumId);
				final Collection<String> attach = miscellaniusData.getAttachments();

				if (!Utils.validateURL(attach))
					res = this.createEditModelAndView(miscellaniusData, "miscellaneousData.commit.errorURL", c);

				else
					res = this.createEditModelAndView(miscellaniusData, "miscellaneousData.commit.error", c);

			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaniusData miscellaneousData, final BindingResult binding) {
		ModelAndView result;
		try {
			this.miscellaneousDataService.delete(miscellaneousData);
			result = new ModelAndView("redirect:/curriculum/rookie/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		final MiscellaniusData miscellaneousData;

		try {
			this.rookieService.findByPrincipal();

			Assert.notNull(miscellaneousDataId);
			miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);

			final Curriculum cu = this.curriculumService.findByMiscellaneousData(miscellaneousData);

			this.curriculumService.findOne(cu.getId()); //Esto es para comprobar que el curriculum no sea ajeno

			final Integer idH = this.rookieService.findByPrincipal().getId();
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(idH);
			Assert.isTrue(curriculums.contains(cu));
			final Boolean b = miscellaneousData.getAttachments().isEmpty();
			result = new ModelAndView("miscellaneousData/show");
			result.addObject("miscellaniusData", miscellaneousData);
			result.addObject("curriculum", cu);
			result.addObject("b", b);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaniusData miscellaniusData, final Curriculum c) {
		return this.createEditModelAndView(miscellaniusData, null, c);
	}
	protected ModelAndView createEditModelAndView(final MiscellaniusData miscellaniusData, final String messageCode, Curriculum c) {
		final ModelAndView res;
		if (c.equals(null))
			c = this.curriculumService.findByMiscellaneousData(miscellaniusData);
		res = new ModelAndView("miscellaneousData/edit");
		res.addObject("miscellaniusData", miscellaniusData);
		res.addObject("message", messageCode);
		res.addObject("curriculum", c);

		return res;
	}

}
