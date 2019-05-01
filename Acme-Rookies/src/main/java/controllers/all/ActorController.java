
package controllers.all;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.RookieService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Rookie;
import forms.ActorEditForm;
import forms.AdministratorEditForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private RookieService			rookieService;
	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		try {
			final Actor actor = this.actorService.findByPrincipal();
			result = new ModelAndView("actor/edit");
			if (this.actorService.authEdit(actor, "ADMINISTRATOR")) {
				final AdministratorEditForm administratorEditForm = this.administratorService.toForm(actor);
				result.addObject("actorEditForm", administratorEditForm);
			} else {
				final ActorEditForm actorEditForm = this.actorService.toForm(actor);
				result.addObject("actorEditForm", actorEditForm);
			}
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/editAdm", method = RequestMethod.GET)
	public ModelAndView editAdm() {
		ModelAndView result;

		try {
			final Actor actor = this.actorService.findByPrincipal();
			result = new ModelAndView("actor/editAdm");

			final AdministratorEditForm administratorEditForm = this.administratorService.toForm(actor);
			result.addObject("administratorEditForm", administratorEditForm);

			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorEditForm actorEditForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("actor/edit");
			res.addObject("actorEditForm", actorEditForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);

		} else
			try {
				final Actor actor = this.actorService.findByPrincipal();
				if (this.actorService.authEdit(actor, "COMPANY")) {
					final Company company = this.companyService.reconstructEdit(actorEditForm);
					this.companyService.save(company);
				} else if (this.actorService.authEdit(actor, "ROOKIE")) {
					final Rookie rookie = this.rookieService.reconstructEdit(actorEditForm);
					this.rookieService.save(rookie);
				}
				res = new ModelAndView("redirect:/#");
			} catch (final Throwable oops) {
				res = new ModelAndView("actor/edit");
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("lang", lang);

				res.addObject("requestURI", "actor/edit.do");
				res.addObject("message", "actor.commit.error");
			}
		return res;
	}
	//JAVI

	@RequestMapping(value = "/editAdm", method = RequestMethod.POST, params = "saveAdmin")
	public ModelAndView saveAdmin(@Valid final AdministratorEditForm administratorEditForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("actor/editAdm");
			res.addObject("administratorEditForm", administratorEditForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);

		} else
			try {
				final Administrator administrator = this.administratorService.reconstructEdit(administratorEditForm);
				this.administratorService.save(administrator);

				res = new ModelAndView("redirect:/#");
			} catch (final Throwable oops) {
				res = new ModelAndView("actor/editAdm");
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("lang", lang);

				res.addObject("requestURI", "actor/edit.do");
				res.addObject("message", "actor.commit.error");
			}
		return res;
	}

}
