
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;
import forms.AuditorRegisterForm;

@Controller
@RequestMapping("/auditor/administrator")
public class AuditorAdministratorController extends AbstractController {

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final AuditorRegisterForm auditorRegisterForm = new AuditorRegisterForm();
			result = this.createModelAndView(auditorRegisterForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final AuditorRegisterForm auditorRegisterForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createModelAndView(auditorRegisterForm);
		else
			try {
				final Auditor auditor = this.auditorService.constructByForm(auditorRegisterForm);
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(auditorRegisterForm);

				final Collection<String> accounts = this.actorService.findAllAccounts();
				final Collection<String> emails = this.actorService.findAllEmails();

				if (accounts.contains(auditorRegisterForm.getUsername()))
					result.addObject("message", "auditor.username.error");
				else if (emails.contains(auditorRegisterForm.getEmail()))
					result.addObject("message", "auditor.email.error");
				else if (!auditorRegisterForm.getConfirmPassword().equals(auditorRegisterForm.getPassword()))
					result.addObject("message", "auditor.password.error");
				else if (Utils.creditCardIsExpired(auditorRegisterForm.getExpirationMonth(), auditorRegisterForm.getExpirationYear()))
					result.addObject("message", "auditor.expired.card.error");
				else
					result.addObject("message", "auditor.commit.error");
			}
		return result;
	}

	protected ModelAndView createModelAndView(final AuditorRegisterForm auditorRegisterForm) {
		return this.createModelAndView(auditorRegisterForm, null);
	}

	protected ModelAndView createModelAndView(final AuditorRegisterForm auditorRegisterForm, final String messageCode) {
		final ModelAndView result = new ModelAndView("auditor/create");
		result.addObject("auditorRegisterForm", auditorRegisterForm);
		result.addObject("messageCode", messageCode);
		return result;
	}
}
