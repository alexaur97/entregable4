
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Message msg;
		msg = new Message();

		try {
			this.administratorService.findByPrincipal();
			result = new ModelAndView("message/create");
			result.addObject("msg", msg);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("msg") final Message msg, final BindingResult binding) {
		ModelAndView res;

		final Message messageF = this.messageService.reconstructAdmnistrator(msg, binding);

		if (binding.hasErrors())
			res = new ModelAndView("message/create");
		else
			try {
				final int adminId = this.administratorService.findByPrincipal().getId();
				final Collection<Actor> actors = this.actorService.findOthersActors(adminId);
				for (final Actor actor : actors) {

					final Message m = this.messageService.reconstructAdmnistrator2(messageF, actor, binding);
					this.messageService.save(m);
					final Message mCopy = this.messageService.reconstructAdmnistrator2Copy(messageF, actor, binding);
					this.messageService.save(mCopy);
				}
				res = new ModelAndView("redirect:/message/list.do");

			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/#");
			}
		return res;
	}

	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	public ModelAndView notifyWelcome() {
		ModelAndView result;

		try {
			final int adminId = this.administratorService.findByPrincipal().getId();
			final Collection<Actor> actors = this.actorService.findOthersActors(adminId);
			final Message messageF = this.messageService.notifyWelcome();

			for (final Actor actor : actors) {
				final Message m = this.messageService.reconstructAdmnistrator2(messageF, actor, null);
				this.messageService.save(m);
				final Message mCopy = this.messageService.reconstructAdmnistrator2Copy(messageF, actor, null);
				this.messageService.save(mCopy);
			}
			result = new ModelAndView("redirect:/message/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
}
