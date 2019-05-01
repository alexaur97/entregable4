
package controllers.rookie;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.RookieService;
import services.PersonalDataService;
import domain.Curriculum;
import domain.PersonalData;

@Controller
@RequestMapping("/personalData/rookie")
public class PersonalDataHackerController {

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RookieService		rookieService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		try {

			final PersonalData personalData = this.personalDataService.findOne(personalDataId);
			Assert.notNull(personalData);
			final Integer idH = this.rookieService.findByPrincipal().getId();
			final Collection<Curriculum> curriculums = this.curriculumService.findByRookie(idH);
			final Curriculum curriculum = this.curriculumService.findByPersonalData(personalData.getId());
			Assert.isTrue(curriculums.contains(curriculum));

			result = this.editModelAndView(personalData);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final PersonalData personalData, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.editModelAndView(personalData);
		else
			try {
				final PersonalData saved = this.personalDataService.save(personalData);
				final Curriculum curriculum = this.curriculumService.findByPersonalData(saved.getId());
				result = new ModelAndView("redirect:/curriculum/rookie/show.do?curriculumId=" + curriculum.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(personalData, "personalData.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final PersonalData personalData) {
		return this.editModelAndView(personalData, null);
	}

	protected ModelAndView editModelAndView(final PersonalData personalData, final String messageCode) {
		final ModelAndView result = new ModelAndView("personalData/edit");
		result.addObject("personalData", personalData);
		result.addObject("message", messageCode);

		final Curriculum curriculum = this.curriculumService.findByPersonalData(personalData.getId());
		result.addObject("curriculum", curriculum);

		final Locale l = LocaleContextHolder.getLocale();
		final String lang = l.getLanguage();
		result.addObject("lang", lang);
		return result;
	}

}
