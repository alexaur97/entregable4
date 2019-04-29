
package controllers.all;

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

import services.ActorService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialprofile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final int id = this.actorService.findByPrincipal().getId();
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(id);
			result = new ModelAndView("socialprofile/list");
			result.addObject("socialProfiles", socialProfiles);
			result.addObject("requestURI", "socialprofile/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int socialProfileId) {
		ModelAndView result;
		final SocialProfile socialProfile;

		try {
			final int id = this.actorService.findByPrincipal().getId();
			Assert.notNull(socialProfileId);
			socialProfile = this.socialProfileService.findOne(socialProfileId);
			Assert.isTrue(socialProfile.getActor().getId() == id);
			result = new ModelAndView("socialprofile/show");
			result.addObject("socialProfile", socialProfile);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int socialProfileId) {
		ModelAndView result;
		final SocialProfile socialProfile;

		try {
			final int id = this.actorService.findByPrincipal().getId();
			Assert.notNull(socialProfileId);
			socialProfile = this.socialProfileService.findOne(socialProfileId);
			Assert.isTrue(socialProfile.getActor().getId() == id);
			this.socialProfileService.delete(socialProfile);
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(id);
			result = new ModelAndView("socialprofile/list");
			result.addObject("socialProfiles", socialProfiles);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		SocialProfile socialProfile;
		socialProfile = new SocialProfile();

		try {
			final Actor a = this.actorService.findByPrincipal();
			socialProfile.setId(0);
			socialProfile.setActor(a);

			result = this.createEditModelAndView(socialProfile);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView res;
		try {

			final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
			Assert.notNull(socialProfile);
			final Integer actorId = this.actorService.findByPrincipal().getId();
			final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(actorId);
			Assert.isTrue(socialProfiles.contains(socialProfile));
			res = this.createEditModelAndView(socialProfile);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("socialProfile") SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView res;

		socialProfile = this.socialProfileService.reconstruct(socialProfile, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(socialProfile);
		else
			try {

				this.socialProfileService.save(socialProfile);
				res = new ModelAndView("redirect:/socialprofile/list.do");

			} catch (final Throwable oops) {

				res = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");

			}

		return res;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		return this.createEditModelAndView(socialProfile, null);
	}
	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("socialprofile/edit");
		result.addObject("socialProfile", socialProfile);
		result.addObject("message", messageCode);
		return result;
	}

}
