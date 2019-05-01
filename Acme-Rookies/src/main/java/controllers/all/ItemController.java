
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	ItemService		itemService;

	@Autowired
	ProviderService	providerService;


	@RequestMapping(value = "/listByProvider", method = RequestMethod.GET)
	public ModelAndView listByProvider(@RequestParam final int providerId) {
		ModelAndView result;
		try {
			Collection<Item> items;
			items = this.itemService.getItemsByProvider(providerId);
			result = new ModelAndView("item/list");
			result.addObject("requestURI", "item/list.do");
			result.addObject("items", items);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Item> items;
			items = this.itemService.findAll();
			result = new ModelAndView("item/list");
			result.addObject("requestURI", "item/list.do");
			result.addObject("items", items);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
