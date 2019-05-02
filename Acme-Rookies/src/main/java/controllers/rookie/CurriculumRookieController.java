
package controllers.rookie;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.RookieService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationData;
import domain.MiscellaniusData;
import domain.PositionData;
import domain.Rookie;
import forms.CurriculumCreateForm;

@Controller
@RequestMapping("/curriculum/rookie")
public class CurriculumRookieController extends AbstractController {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RookieService		rookieService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Curriculum> curriculums = this.curriculumService.findAllByPrincipalNoCopy();
			result = new ModelAndView("curriculum/list");
			result.addObject("curriculums", curriculums);
			result.addObject("requestURI", "/curriculum/rookie/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final CurriculumCreateForm curriculumCreateForm = new CurriculumCreateForm();
			result = this.createModelAndView(curriculumCreateForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		ModelAndView result;
		try {
			final Rookie h = this.rookieService.findByPrincipal();
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(h.getId());
			Assert.isTrue(curriculums.contains(curriculum));
			result = this.editModelAndView(curriculum);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculumId) {
		ModelAndView result;
		try {
			final Rookie h = this.rookieService.findByPrincipal();
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(h.getId());
			Assert.isTrue(curriculums.contains(curriculum));

			final Collection<EducationData> educationData = curriculum.getEducationData();
			final Collection<MiscellaniusData> miscellaniusData = curriculum.getMiscellaniusData();
			final Collection<PositionData> positionData = curriculum.getPositionData();
			result = new ModelAndView("curriculum/show");
			result.addObject("curriculum", curriculum);
			result.addObject("educationDatas", educationData);
			result.addObject("miscellaniusDatas", miscellaniusData);
			result.addObject("positionDatas", positionData);
			result.addObject("requestURI", "/curriculum/rookie/show.do?curriculumId=" + curriculum.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final CurriculumCreateForm curriculumCreateForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createModelAndView(curriculumCreateForm);
		else
			try {
				final Curriculum curriculum = this.curriculumService.constructByForm(curriculumCreateForm);
				final Curriculum saved = this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:/curriculum/rookie/show.do?curriculumId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createModelAndView(curriculumCreateForm, "curriculum.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("curriculum") Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		curriculum = this.curriculumService.reconstruct(curriculum, binding);
		if (binding.hasErrors())
			result = this.editModelAndView(curriculum);
		else
			try {
				final Curriculum saved = this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:/curriculum/rookie/show.do?curriculumId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(curriculum, "curriculum.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("curriculum") final Curriculum curriculum) {
		ModelAndView result;
		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/curriculum/rookie/list.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(curriculum, "curriculum.commit.error");
		}
		return result;
	}

	protected ModelAndView createModelAndView(final CurriculumCreateForm curriculumCreateForm) {
		return this.createModelAndView(curriculumCreateForm, null);
	}

	protected ModelAndView createModelAndView(final CurriculumCreateForm curriculumCreateForm, final String messageCode) {
		final ModelAndView result = new ModelAndView("curriculum/create");
		result.addObject("curriculumCreateForm", curriculumCreateForm);
		result.addObject("message", messageCode);
		final Locale l = LocaleContextHolder.getLocale();
		final String lang = l.getLanguage();
		result.addObject("lang", lang);
		return result;
	}

	protected ModelAndView editModelAndView(final Curriculum curriculum) {
		return this.editModelAndView(curriculum, null);
	}

	protected ModelAndView editModelAndView(final Curriculum curriculum, final String messageCode) {
		final ModelAndView result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);
		return result;
	}

}
