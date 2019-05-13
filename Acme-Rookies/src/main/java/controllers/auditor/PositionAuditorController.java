
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.AuditorService;
import services.PositionService;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/position/auditor/")
public class PositionAuditorController {

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Auditor auditor = this.auditorService.findByPrincipal();
			Collection<Position> positions;

			positions = auditor.getPositions();
			result = new ModelAndView("position/myList");
			result.addObject("requestURI", "position/auditor/myList.do");
			result.addObject("positions", positions);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result;
		final Position position;

		try {
			final Auditor auditor = this.auditorService.findByPrincipal();
			Assert.notNull(positionId);
			position = this.positionService.findOne(positionId);

			Assert.isTrue(auditor.getPositions().contains(position));
			final Boolean b = position.getProblems().isEmpty();

			Boolean d = true;
			final Collection<Audit> audit = this.auditService.findByPosition(positionId);
			if (audit.isEmpty())
				d = false;

			result = new ModelAndView("position/show");
			result.addObject("position", position);
			result.addObject("b", b);
			result.addObject("d", d);
			result.addObject("audit", audit);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
