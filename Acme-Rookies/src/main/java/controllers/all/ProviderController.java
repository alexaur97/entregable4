
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/provider")
public class ProviderController {

	@Autowired
	ProviderService	providerService;

	@Autowired
	ItemService		itemService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Provider> providers;
			providers = this.providerService.findAll();
			result = new ModelAndView("provider/list");
			result.addObject("requestURI", "provider/list.do");
			result.addObject("providers", providers);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/showByItem", method = RequestMethod.GET)
	public ModelAndView showByItem(@RequestParam final int itemId) {
		ModelAndView result;
		final Provider provider;

		try {
			Assert.notNull(itemId);

			final Item item = this.itemService.findOne(itemId);
			provider = item.getProvider();

			result = new ModelAndView("provider/show");
			result.addObject("provider", provider);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
