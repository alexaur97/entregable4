
package controllers.provider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Provider;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/provider/")
public class SponsorshipProviderController extends AbstractController {

	//Repository
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private ProviderService		providerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		//		try {
		final Provider provider = this.providerService.findByPrincipal();
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findByProvider(provider.getId());
		result = new ModelAndView("sponsorship/list");
		result.addObject("requestURI", "application/company/list.do");
		result.addObject("sponsorships", sponsorships);

		//		} catch (final Exception e) {
		//			result = new ModelAndView("redirect:/#");
		//		}

		return result;
	}
}
