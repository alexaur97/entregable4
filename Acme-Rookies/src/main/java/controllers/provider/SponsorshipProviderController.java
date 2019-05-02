
package controllers.provider;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Controller
@RequestMapping("/sponsorship/provider/")
public class SponsorshipProviderController extends AbstractController {

	//Repository
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private ProviderService		providerService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private CreditCardService	creditCardService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Provider provider = this.providerService.findByPrincipal();
			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findByProvider(provider.getId());
			result = new ModelAndView("sponsorship/list");
			result.addObject("requestURI", "application/company/list.do");
			result.addObject("sponsorships", sponsorships);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			final Provider provider = this.providerService.findByPrincipal();
			Assert.isTrue(sponsorship.getProvider().equals(provider));
			result = new ModelAndView("sponsorship/show");
			result.addObject("sponsorship", sponsorship);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final SponsorshipForm sponsorshipForm;
		sponsorshipForm = new SponsorshipForm();

		try {
			this.providerService.findByPrincipal();
			final Collection<Position> positions = this.positionService.findFinalNotBanned();
			result = new ModelAndView("sponsorship/create");
			result.addObject("sponsorshipForm", sponsorshipForm);
			result.addObject("positions", positions);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SponsorshipForm sponsorshipForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			final Collection<Position> positions = this.positionService.findFinalNotBanned();
			res = new ModelAndView("sponsorship/create");
			res.addObject("positions", positions);
		}

		else
			try {
				Sponsorship sponsorship;
				this.providerService.findByPrincipal();
				sponsorship = this.sponsorshipService.recostruction(sponsorshipForm);
				this.sponsorshipService.save(sponsorship);
				res = new ModelAndView("redirect:/sponsorship/provider/list.do");

			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/#");
			}
		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			final Provider provider = this.providerService.findByPrincipal();
			Assert.isTrue(sponsorship.getProvider().equals(provider));
			this.sponsorshipService.delete(sponsorship);

			result = new ModelAndView("redirect:/sponsorship/provider/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {

		ModelAndView result;

		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			final Provider provider = this.providerService.findByPrincipal();
			Assert.isTrue(sponsorship.getProvider().equals(provider));
			final Collection<Position> positions = this.positionService.findFinalNotBanned();
			result = new ModelAndView("sponsorship/edit");
			result.addObject("sponsorship", sponsorship);
			result.addObject("positions", positions);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView res;
		final Sponsorship sponsorshipFinal = this.sponsorshipService.recostructionEdit(sponsorship, binding);

		if (binding.hasErrors()) {
			final Collection<Position> positions = this.positionService.findFinalNotBanned();
			res = new ModelAndView("sponsorship/edit");
			res.addObject("positions", positions);
		}

		else
			try {

				this.providerService.findByPrincipal();
				this.sponsorshipService.save(sponsorshipFinal);
				res = new ModelAndView("redirect:/sponsorship/provider/list.do");

			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/#");
			}
		return res;
	}
}
