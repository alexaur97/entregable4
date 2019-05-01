
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Provider;

@Service
@Transactional
public class ProviderService {

	//Managed repository -------------------
	@Autowired
	private ProviderRepository	providerRepository;

	@Autowired
	private ActorService		actorService;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public ProviderService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Provider create() {
		Provider result;

		result = new Provider();

		return result;
	}

	public Collection<Provider> findAll() {
		Collection<Provider> result;

		result = this.providerRepository.findAll();

		return result;
	}

	public Provider findOne(final int providerId) {
		Provider result;

		result = this.providerRepository.findOne(providerId);

		return result;
	}

	public void save(final Provider provider) {
		Assert.notNull(provider);

		this.providerRepository.save(provider);
	}

	public void delete(final Provider provider) {
		this.providerRepository.delete(provider);
	}

	public Provider findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Provider p = this.findByUserId(user.getId());
		Assert.notNull(p);
		this.actorService.auth(p, Authority.PROVIDER);
		return p;
	}

	private Provider findByUserId(final int id) {
		final Provider p = this.providerRepository.findByUserId(id);
		return p;
	}

	//Other Methods--------------------

	public Collection<Provider> top5ProvidersPerItems() {
		final Collection<Provider> providers = this.providerRepository.top5ProvidersPerItems();
		final ArrayList<Provider> result = new ArrayList<>();
		int index = 0;
		for (final Provider p : providers)
			if (index < 5) {
				result.add(p);
				index++;
			}
		return result;
	}
}
