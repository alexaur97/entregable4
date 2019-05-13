
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.AuditorService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/audit/auditor/")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService	auditService;
	@Autowired
	private AuditorService	auditorService;


	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			this.auditorService.findByPrincipal();
			final Collection<Audit> audits = this.auditService.findByAuditor();

			result = new ModelAndView("audit/myList");
			result.addObject("requestURI", "audit/auditor/myList.do");
			result.addObject("audits", audits);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		try {
			Audit audit;
			audit = new Audit();

			result = this.createEditModelAndView(audit);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView res;
		try {

			final Audit audit = this.auditService.findOne(auditId);
			Assert.notNull(audit);

			Assert.isTrue(audit.getMode().equals("DRAFT"));
			final Collection<Audit> audits = this.auditService.findByAuditor();
			Assert.isTrue(audits.contains(audit));
			res = this.createEditModelAndView(audit);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("audit") Audit audit, final BindingResult binding) {
		ModelAndView res;
		try {
			Assert.notNull(audit.getPosition());

			audit = this.auditService.reconstruct(audit, binding);

			if (binding.hasErrors())
				res = this.createEditModelAndView(audit);
			else
				try {

					this.auditService.save(audit);
					res = new ModelAndView("redirect:/audit/auditor/myList.do");

				} catch (final Throwable oops) {

					res = this.createEditModelAndView(audit, "audit.commit.error");

				}
		} catch (final Throwable oops) {

			res = this.createEditModelAndView(audit, "audit.commit.error");

		}
		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		final Audit au = this.auditService.findOne(audit.getId());
		try {

			final Audit auditDB = this.auditService.findOne(audit.getId());
			Assert.notNull(auditDB);

			final Collection<Audit> audits = this.auditService.findByAuditor();
			Assert.isTrue(audits.contains(auditDB));
			this.auditService.delete(au);
			result = new ModelAndView("redirect:/audit/auditor/myList.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(au, oops.getMessage());

			final String msg = oops.getMessage();
			if (msg.equals("auditcannotDelete")) {
				final Boolean auditcannotDelete = true;
				result.addObject("auditcannotDelete", auditcannotDelete);

			}
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int auditId) {
		ModelAndView res;
		try {

			final Audit audit = this.auditService.findOne(auditId);
			Assert.notNull(audit);

			final Collection<Audit> audits = this.auditService.findByAuditor();
			Assert.isTrue(audits.contains(audit));
			res = new ModelAndView("audit/show");
			res.addObject("audit", audit);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Audit audit) {
		return this.createEditModelAndView(audit, null);
	}
	protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
		final Auditor auditor = this.auditorService.findByPrincipal();

		final Collection<Position> positions = auditor.getPositions();
		final ModelAndView res;
		res = new ModelAndView("audit/edit");
		res.addObject("audit", audit);
		res.addObject("positions", positions);
		res.addObject("message", messageCode);

		return res;
	}

}
