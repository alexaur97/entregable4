
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	//Managed repository -------------------
	@Autowired
	private ItemRepository	itemRepository;

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private Validator		validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public ItemService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Item create() {
		Item result;

		result = new Item();

		return result;
	}

	public Collection<Item> findAll() {
		Collection<Item> result;

		result = this.itemRepository.findAll();

		return result;
	}

	public Item findOne(final int itemId) {
		Item result;

		result = this.itemRepository.findOne(itemId);

		return result;
	}

	public void save(final Item item) {
		Assert.notNull(item);

		this.itemRepository.save(item);
	}

	public void delete(final Item item) {
		final Provider p = this.providerService.findByPrincipal();
		Assert.isTrue(item.getProvider().equals(p));
		this.itemRepository.delete(item);
	}

	public Collection<Item> getItemsByProvider(final int providerId) {
		Assert.notNull(providerId);
		final Collection<Item> res = this.itemRepository.getItemsByProvider(providerId);
		//		for (final Item i : res) {
		//			final int id = i.getProvider().getId();
		//			Assert.isTrue(id == providerId);
		//	}
		return res;
	}

	public Item reconstruct(final Item item, final BindingResult binding) {

		final Item res = item;

		final Provider p = this.providerService.findByPrincipal();
		res.setProvider(p);

		this.validator.validate(res, binding);

		return res;
	}
	//Other Methods--------------------

}
