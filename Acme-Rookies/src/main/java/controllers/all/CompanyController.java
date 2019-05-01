
package controllers.all;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.CompanyService;
import services.PositionService;
import domain.Company;
import domain.Position;
import forms.CompanyRegisterForm;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final CompanyRegisterForm companyRegisterForm = new CompanyRegisterForm();
			result = this.createEditModelAndView(companyRegisterForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CompanyRegisterForm companyRegisterForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(companyRegisterForm);
		else
			try {
				final Company company = this.companyService.constructByForm(companyRegisterForm);
				final Company saved = this.companyService.save(company);
				System.out.println(saved);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(companyRegisterForm);

				final Collection<String> accounts = this.actorService.findAllAccounts();
				final Collection<String> emails = this.actorService.findAllEmails();

				if (accounts.contains(companyRegisterForm.getUsername()))
					result.addObject("message", "company.username.error");
				else if (emails.contains(companyRegisterForm.getEmail()))
					result.addObject("message", "company.email.error");
				else if (!companyRegisterForm.getConfirmPassword().equals(companyRegisterForm.getPassword()))
					result.addObject("message", "company.password.error");
				else if (Utils.creditCardIsExpired(companyRegisterForm.getExpirationMonth(), companyRegisterForm.getExpirationYear()))
					result.addObject("message", "company.expired.card.error");
				else
					result.addObject("message", "company.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyRegisterForm companyRegisterForm) {
		return this.createEditModelAndView(companyRegisterForm, null);
	}

	protected ModelAndView createEditModelAndView(final CompanyRegisterForm companyRegisterForm, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("company/signup");
		result.addObject("companyRegisterForm", companyRegisterForm);
		result.addObject("message", messageCode);

		final Locale l = LocaleContextHolder.getLocale();
		final String lang = l.getLanguage();
		result.addObject("lang", lang);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int companyId) {
		ModelAndView result;
		final Company company;

		try {
			Assert.notNull(companyId);
			final Collection<Position> positions = this.positionService.findByCompanyFinal(companyId);
			company = this.companyService.findOne(companyId);
			if (company.getBanned()) {
				final Authority auth = new Authority();
				auth.setAuthority(Authority.ADMINISTRATOR);
				Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
			}
			final Boolean b = positions.isEmpty();
			result = new ModelAndView("company/show");
			final Double score = this.companyService.scoreByCompany(company);
			result.addObject("score", score);
			result.addObject("positions", positions);
			result.addObject("company", company);
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
			Collection<Company> companies;
			companies = this.companyService.companiesNotBanned();
			result = new ModelAndView("company/list");
			result.addObject("requestURI", "company/list.do");
			result.addObject("companies", companies);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
