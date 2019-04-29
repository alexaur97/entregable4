
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;

@Controller
@RequestMapping("actor/administrator")
public class ActorAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Administrator admin = this.administratorService.findByPrincipal();
			final Collection<Actor> actors = this.actorService.findOthersActors(admin.getId());
			result = new ModelAndView("actor/list");
			result.addObject("requestURI", "actor/administrator/list.do");
			result.addObject("actors", actors);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/banned", method = RequestMethod.GET)
	public ModelAndView banned(@RequestParam final int actorId) {
		ModelAndView result;
		final Collection<Actor> actors;
		try {
			final Administrator admin = this.administratorService.findByPrincipal();
			Assert.isTrue(!(admin.getId() == actorId));
			final Actor actor = this.actorService.findOne(actorId);
			Assert.isTrue(actor.getSpammer().equals(true));
			actor.setBanned(true);
			this.actorService.save(actor);
			actors = this.actorService.findAll();
			result = new ModelAndView("actor/list");
			result.addObject("requestURI", "actor/administrator/list.do");
			result.addObject("actors", actors);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/unbanned", method = RequestMethod.GET)
	public ModelAndView unbanned(@RequestParam final int actorId) {
		ModelAndView result;
		final Collection<Actor> actors;
		try {
			final Administrator admin = this.administratorService.findByPrincipal();
			Assert.isTrue(!(admin.getId() == actorId));
			final Actor actor = this.actorService.findOne(actorId);
			actor.setBanned(false);
			this.actorService.save(actor);
			actors = this.actorService.findAll();
			result = new ModelAndView("actor/list");
			result.addObject("requestURI", "actor/administrator/list.do");
			result.addObject("actors", actors);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
}
