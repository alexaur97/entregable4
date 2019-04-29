
package controllers.all;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FinderService;
import services.RookieService;
import domain.Rookie;
import forms.RookieRegisterForm;

@Controller
@RequestMapping("/rookie")
public class RookieController {

	@Autowired
	private RookieService	rookieService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FinderService	finderService;


	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RookieRegisterForm rookieRegisterForm = new RookieRegisterForm();
			result = this.createEditModelAndView(rookieRegisterForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RookieRegisterForm rookieRegisterForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(rookieRegisterForm);
		else
			try {
				final Rookie rookie = this.rookieService.constructByForm(rookieRegisterForm);
				final Rookie saved = this.rookieService.save(rookie);
				this.finderService.createFinder(saved);
				System.out.println(saved);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rookieRegisterForm);

				final Collection<String> accounts = this.actorService.findAllAccounts();
				final Collection<String> emails = this.actorService.findAllEmails();

				if (accounts.contains(rookieRegisterForm.getUsername()))
					result.addObject("message", "rookie.username.error");
				else if (emails.contains(rookieRegisterForm.getEmail()))
					result.addObject("message", "rookie.email.error");
				else if (!rookieRegisterForm.getConfirmPassword().equals(rookieRegisterForm.getPassword()))
					result.addObject("message", "rookie.password.error");
				else if (Utils.creditCardIsExpired(rookieRegisterForm.getExpirationMonth(), rookieRegisterForm.getExpirationYear()))
					result.addObject("message", "company.expired.card.error");
				else
					result.addObject("message", "rookie.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final RookieRegisterForm rookieRegisterForm) {
		return this.createEditModelAndView(rookieRegisterForm, null);
	}

	protected ModelAndView createEditModelAndView(final RookieRegisterForm rookieRegisterForm, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("rookie/signup");
		result.addObject("rookieRegisterForm", rookieRegisterForm);
		result.addObject("message", messageCode);

		final Locale l = LocaleContextHolder.getLocale();
		final String lang = l.getLanguage();
		result.addObject("lang", lang);

		return result;
	}
}
