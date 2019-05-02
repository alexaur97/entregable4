
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

import services.ActorService;
import services.ItemService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Item;
import domain.Provider;
import forms.ProviderRegisterForm;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Provider> providers;
			providers = this.providerService.findAll();
			result = new ModelAndView("provider/list");
			result.addObject("requestURI", "provider/list.do");
			result.addObject("providers", providers);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/showByItem", method = RequestMethod.GET)
	public ModelAndView showByItem(@RequestParam final int itemId) {
		ModelAndView result;
		final Provider provider;

		try {
			Assert.notNull(itemId);

			final Item item = this.itemService.findOne(itemId);
			provider = item.getProvider();

			result = new ModelAndView("provider/show");
			result.addObject("provider", provider);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final ProviderRegisterForm providerRegisterForm = new ProviderRegisterForm();
			result = this.createEditModelAndView(providerRegisterForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProviderRegisterForm providerRegisterForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(providerRegisterForm);
		else
			try {
				final Provider provider = this.providerService.constructByForm(providerRegisterForm);
				final Provider saved = this.providerService.save(provider);
				System.out.println(saved);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(providerRegisterForm);

				final Collection<String> accounts = this.actorService.findAllAccounts();
				final Collection<String> emails = this.actorService.findAllEmails();

				if (accounts.contains(providerRegisterForm.getUsername()))
					result.addObject("message", "provider.username.error");
				else if (emails.contains(providerRegisterForm.getEmail()))
					result.addObject("message", "provider.email.error");
				else if (!providerRegisterForm.getConfirmPassword().equals(providerRegisterForm.getPassword()))
					result.addObject("message", "provider.password.error");
				else if (Utils.creditCardIsExpired(providerRegisterForm.getExpirationMonth(), providerRegisterForm.getExpirationYear()))
					result.addObject("message", "provider.expired.card.error");
				else
					result.addObject("message", "provider.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final ProviderRegisterForm providerRegisterForm) {
		return this.createEditModelAndView(providerRegisterForm, null);
	}

	protected ModelAndView createEditModelAndView(final ProviderRegisterForm providerRegisterForm, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("provider/signup");
		result.addObject("providerRegisterForm", providerRegisterForm);
		result.addObject("message", messageCode);

		final Locale l = LocaleContextHolder.getLocale();
		final String lang = l.getLanguage();
		result.addObject("lang", lang);

		return result;
	}
}
