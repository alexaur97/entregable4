
package controllers.provider;

import java.util.Collection;

import miscellaneous.Utils;

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
import services.ItemService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/item/provider/")
public class ItemProviderController extends AbstractController {

	@Autowired
	ItemService		itemService;

	@Autowired
	ProviderService	providerService;

	@Autowired
	ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Item> items;
			final Provider p = this.providerService.findByPrincipal();
			final int providerId = p.getId();
			items = this.itemService.getItemsByProvider(providerId);
			result = new ModelAndView("item/list");
			result.addObject("requestURI", "item/list.do");
			result.addObject("items", items);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		try {
			Item item;
			item = new Item();

			result = this.createEditModelAndView(item);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int itemId) {
		ModelAndView res;
		try {

			final Item item = this.itemService.findOne(itemId);
			Assert.notNull(item);
			final Provider provider = this.providerService.findByPrincipal();
			Assert.isTrue(item.getProvider().equals(provider));
			res = this.createEditModelAndView(item);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("item") Item item, final BindingResult binding) {
		ModelAndView res;
		item = this.itemService.reconstruct(item, binding);
		final Collection<String> fotos = item.getPhotos();
		final Collection<String> links = item.getLinks();
		final Boolean b = Utils.validateURL(fotos);
		final Boolean a = Utils.validateURL(links);

		if (binding.hasErrors())
			res = this.createEditModelAndView(item);
		else
			try {
				Assert.isTrue(b);
				Assert.isTrue(a);
				this.itemService.save(item);
				res = new ModelAndView("redirect:/item/provider/list.do");

			} catch (final Throwable oops) {
				if (b.equals(false))
					res = this.createEditModelAndView(item, "item.error.photo");
				else if (a.equals(false))
					res = this.createEditModelAndView(item, "item.error.link");
				else
					res = this.createEditModelAndView(item, "item.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Item item, final BindingResult binding) {
		ModelAndView result;
		final Item res = this.itemService.findOne(item.getId());
		try {

			this.itemService.delete(res);
			result = new ModelAndView("redirect:/item/provider/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(res, oops.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int itemId) {
		ModelAndView result;
		try {
			Assert.notNull(itemId);
			//			final Company company = this.companyService.findByPrincipal();
			final Item item = this.itemService.findOne(itemId);
			Assert.notNull(item);
			//			Assert.isTrue(problem.getCompany().equals(company));
			result = new ModelAndView("item/show");
			result.addObject("item", item);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item) {
		return this.createEditModelAndView(item, null);
	}
	protected ModelAndView createEditModelAndView(final Item item, final String messageCode) {
		final ModelAndView res;
		res = new ModelAndView("item/edit");
		res.addObject("item", item);
		res.addObject("message", messageCode);

		return res;
	}

}
