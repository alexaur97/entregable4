
package services;

import java.util.ArrayList;
import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Rookie;
import forms.RookieRegisterForm;

@Service
@Transactional
public class RookieService {

	//Managed repository -------------------
	@Autowired
	private RookieRepository	rookieRepository;

	//Supporting Services ------------------
	@Autowired
	private ActorService		actorService;


	//COnstructors -------------------------
	public RookieService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Rookie create() {
		final Rookie result = new Rookie();
		final UserAccount ua = new UserAccount();

		result.setUserAccount(ua);

		final Authority a = new Authority();
		a.setAuthority(Authority.ROOKIE);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		result.getUserAccount().setAuthorities(authorities);

		final CreditCard creditCard = new CreditCard();
		result.setCreditCard(creditCard);
		return result;
	}

	public Collection<Rookie> findAll() {
		Collection<Rookie> result;

		result = this.rookieRepository.findAll();

		return result;
	}

	public Rookie findOne(final int rookieId) {
		Rookie result;

		result = this.rookieRepository.findOne(rookieId);

		return result;
	}

	public Rookie save(final Rookie rookie) {
		Assert.notNull(rookie);

		final Rookie result = this.rookieRepository.save(rookie);
		System.out.println(result);
		return result;
	}

	public void delete(final Rookie rookie) {
		this.rookieRepository.delete(rookie);
	}

	//Other Methods--------------------

	private Rookie findByUserId(final int id) {
		final Rookie h = this.rookieRepository.findByUserId(id);
		return h;
	}

	public Rookie findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Rookie h = this.findByUserId(user.getId());
		Assert.notNull(h);
		this.actorService.auth(h, Authority.ROOKIE);
		return h;
	}

	public Rookie constructByForm(final RookieRegisterForm rookieRegisterForm) {
		Assert.isTrue(rookieRegisterForm.getPassword().equals(rookieRegisterForm.getConfirmPassword()));
		final Rookie result = this.create();
		final Collection<String> emails = this.actorService.findAllEmails();
		final String email = rookieRegisterForm.getEmail();
		final Boolean bEmail = !emails.contains(email);
		Assert.isTrue(bEmail);

		final Collection<String> accounts = this.actorService.findAllAccounts();
		final UserAccount userAccount = result.getUserAccount();
		final Boolean bAccount = !accounts.contains(rookieRegisterForm.getUsername());
		Assert.isTrue(bAccount);

		final Md5PasswordEncoder pe = new Md5PasswordEncoder();
		final String password = pe.encodePassword(rookieRegisterForm.getPassword(), null);
		userAccount.setPassword(password);
		userAccount.setUsername(rookieRegisterForm.getUsername());
		result.setUserAccount(userAccount);

		result.setAddress(rookieRegisterForm.getAddress());
		result.setBanned(false);

		final CreditCard creditCard = result.getCreditCard();
		creditCard.setBrandName(rookieRegisterForm.getBrandName());
		creditCard.setCvv(rookieRegisterForm.getCvv());
		creditCard.setExpirationMonth(rookieRegisterForm.getExpirationMonth());
		creditCard.setExpirationYear(rookieRegisterForm.getExpirationYear());
		creditCard.setHolderName(rookieRegisterForm.getHolderName());
		creditCard.setNumber(rookieRegisterForm.getNumber());
		final Boolean b = Utils.creditCardIsExpired(creditCard);
		Assert.isTrue(!b);
		result.setCreditCard(creditCard);

		result.setEmail(rookieRegisterForm.getEmail());
		result.setName(rookieRegisterForm.getName());
		result.setPhone(rookieRegisterForm.getPhone());
		result.setPhoto(rookieRegisterForm.getPhoto());
		result.setSpammer(false);
		result.setSurnames(rookieRegisterForm.getSurnames());

		final String vat = rookieRegisterForm.getVAT();
		result.setVAT(vat.toUpperCase());
		return result;
	}

	public Collection<Rookie> rookiesHaveMadeMoreApplications() {
		final Collection<Rookie> result = this.rookieRepository.rookiesHaveMadeMoreApplications();
		Assert.notNull(result);
		return result;
	}

	public Collection<Double> statsCurriculaPerRookie() {
		final Collection<Double> result = this.rookieRepository.statsCurriculaPerRookie();
		Assert.notNull(result);
		return result;
	}

}
