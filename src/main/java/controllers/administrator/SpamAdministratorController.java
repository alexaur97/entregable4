
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/spam/administrator")
public class SpamAdministratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/spammer", method = RequestMethod.GET)
	public ModelAndView spammer() {
		ModelAndView result;
		Boolean msg;

		try {

			final Actor actor = this.actorService.findByPrincipal();
			final Collection<Actor> actors = this.actorService.findOthersActors(actor.getId());
			this.actorService.isSpammer();
			msg = true;
			result = new ModelAndView("actor/list");
			result.addObject("msg", msg);
			result.addObject("requestURI", "actor/administrator/list.do");
			result.addObject("actors", actors);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
