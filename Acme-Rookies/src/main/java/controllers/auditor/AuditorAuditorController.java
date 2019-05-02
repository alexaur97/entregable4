
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.PositionService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("auditor/auditor")
public class AuditorAuditorController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private AuditorService	auditorService;


	@RequestMapping(value = "/editPosition", method = RequestMethod.GET)
	public ModelAndView editPos() {
		ModelAndView res;
		try {

			final Auditor auditor = this.auditorService.findByPrincipal();

			final Collection<Position> position = this.positionService.findPositionsFinal();
			res = new ModelAndView("auditor/editPos");
			res.addObject("auditor", auditor);
			res.addObject("position", position);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/editPosition", method = RequestMethod.POST, params = "save")
	public ModelAndView savePos(@ModelAttribute("auditor") Auditor auditor, final BindingResult binding) {
		ModelAndView res;

		auditor = this.auditorService.reconstruct(auditor, binding);
		final Collection<Position> pos = this.positionService.findPositionsFinal();

		if (binding.hasErrors()) {
			res = new ModelAndView("auditor/editPos");
			res.addObject("auditor", auditor);
			res.addObject("position", pos);
		} else
			try {

				this.auditorService.save(auditor);
				res = new ModelAndView("redirect:/audit/auditor/myList.do");

			} catch (final Throwable oops) {
				res = new ModelAndView("auditor/editPos");
				res.addObject("auditor", auditor);
				res.addObject("position", pos);
				res.addObject("auditor.commit.error");
			}

		return res;
	}

}
