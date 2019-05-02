
package controllers.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import controllers.AbstractController;
import domain.Audit;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		ModelAndView result;
		try {
			final Audit audit = this.auditService.findOne(auditId);
			result = new ModelAndView("audit/display");
			result.addObject("audit", audit);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
}
