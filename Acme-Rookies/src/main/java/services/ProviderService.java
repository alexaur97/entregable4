
package services;

import java.util.ArrayList;
import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Provider;
import forms.ProviderRegisterForm;

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
		final Provider result = new Provider();
		final UserAccount ua = new UserAccount();

		result.setUserAccount(ua);

		final Authority a = new Authority();
		a.setAuthority(Authority.PROVIDER);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		result.getUserAccount().setAuthorities(authorities);

		final CreditCard creditCard = new CreditCard();
		result.setCreditCard(creditCard);
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

	public Provider save(final Provider provider) {
		Assert.notNull(provider);
		return this.providerRepository.save(provider);
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

	public Collection<Provider> providersWithMoreThan10pcSponsorships() {
		final Collection<Provider> result = this.providerRepository.providersWithMoreThan10pcSponsorships();
		return result;
	}

	public Provider constructByForm(final ProviderRegisterForm providerRegisterForm) {
		Assert.isTrue(providerRegisterForm.getPassword().equals(providerRegisterForm.getConfirmPassword()));
		final Provider result = this.create();
		final Collection<String> emails = this.actorService.findAllEmails();
		final String email = providerRegisterForm.getEmail();
		final Boolean bEmail = !emails.contains(email);
		Assert.isTrue(bEmail);

		final Collection<String> accounts = this.actorService.findAllAccounts();
		final UserAccount userAccount = result.getUserAccount();
		final Boolean bAccount = !accounts.contains(providerRegisterForm.getUsername());
		Assert.isTrue(bAccount);

		final Md5PasswordEncoder pe = new Md5PasswordEncoder();
		final String password = pe.encodePassword(providerRegisterForm.getPassword(), null);
		userAccount.setPassword(password);
		userAccount.setUsername(providerRegisterForm.getUsername());
		result.setUserAccount(userAccount);

		result.setAddress(providerRegisterForm.getAddress());
		result.setBanned(false);

		final CreditCard creditCard = result.getCreditCard();
		creditCard.setBrandName(providerRegisterForm.getBrandName());
		creditCard.setCvv(providerRegisterForm.getCvv());
		creditCard.setExpirationMonth(providerRegisterForm.getExpirationMonth());
		creditCard.setExpirationYear(providerRegisterForm.getExpirationYear());
		creditCard.setHolderName(providerRegisterForm.getHolderName());
		creditCard.setNumber(providerRegisterForm.getNumber());
		final Boolean b = Utils.creditCardIsExpired(creditCard);
		Assert.isTrue(!b);
		result.setCreditCard(creditCard);

		result.setEmail(providerRegisterForm.getEmail());
		result.setName(providerRegisterForm.getName());
		result.setPhone(this.actorService.addCountryCode(providerRegisterForm.getPhone()));
		result.setPhoto(providerRegisterForm.getPhoto());
		result.setSpammer(false);
		result.setSurnames(providerRegisterForm.getSurnames());

		final String vat = providerRegisterForm.getVAT();
		result.setVAT(vat.toUpperCase());
		return result;
	}
}
