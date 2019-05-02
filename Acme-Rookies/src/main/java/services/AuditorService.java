
package services;

import java.util.ArrayList;
import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Auditor;
import domain.CreditCard;
import domain.Position;
import forms.AuditorRegisterForm;

@Service
@Transactional
public class AuditorService {

	//Managed repository -------------------
	@Autowired
	private AuditorRepository		auditorRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;



	//Supporting Services ------------------

	//Supporting Services ------------------

	//COnstructors -------------------------
	public AuditorService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Auditor create() {
		this.administratorService.findByPrincipal();
		Auditor result;
		result = new Auditor();
		result.setBanned(false);
		result.setSpammer(false);

		final Collection<Position> positions = new ArrayList<>();
		result.setPositions(positions);

		final CreditCard creditCard = new CreditCard();
		result.setCreditCard(creditCard);

		final UserAccount userAccount = new UserAccount();
		result.setUserAccount(userAccount);

		final Authority a = new Authority();
		a.setAuthority(Authority.AUDITOR);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		result.getUserAccount().setAuthorities(authorities);

		return result;
	}
	public Collection<Auditor> findAll() {
		Collection<Auditor> result;

		result = this.auditorRepository.findAll();

		return result;
	}

	public Auditor findOne(final int auditorId) {
		Auditor result;

		result = this.auditorRepository.findOne(auditorId);

		return result;
	}

	public void save(final Auditor auditor) {
		Assert.notNull(auditor);

		this.auditorRepository.save(auditor);
	}

	public void delete(final Auditor auditor) {
		this.auditorRepository.delete(auditor);
	}

	public Auditor findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Auditor h = this.findByUserId(user.getId());
		Assert.notNull(h);
		this.actorService.auth(h, Authority.AUDITOR);
		return h;
	}

	private Auditor findByUserId(final int id) {
		final Auditor h = this.auditorRepository.findByUserId(id);
		return h;

	}

	public Auditor selfAssignPos(final Position position) {
		final Auditor auditor = this.findByPrincipal();
		auditor.getPositions().add(position);
		return auditor;
	}

	public Auditor reconstruct(final Auditor auditor, final BindingResult binding) {
		final Auditor res = auditor;
		final Auditor a = this.findOne(auditor.getId());

		final Collection<Position> positions = a.getPositions();

		res.getPositions().addAll(positions);
		res.setAddress(a.getAddress());
		res.setBanned(a.getBanned());
		res.setCreditCard(a.getCreditCard());
		res.setEmail(a.getEmail());
		res.setName(a.getName());
		res.setPhone(a.getPhone());
		res.setPhoto(a.getPhoto());
		res.setSurnames(a.getSurnames());
		res.setVAT(a.getVAT());
		res.setUserAccount(a.getUserAccount());
		res.setSpammer(a.getSpammer());

		this.validator.validate(res, binding);
		return res;
	}

	public Auditor constructByForm(final AuditorRegisterForm auditorRegisterForm) {
		Assert.isTrue(auditorRegisterForm.getPassword().equals(auditorRegisterForm.getConfirmPassword()));
		final Auditor result = this.create();
		final Collection<String> emails = this.actorService.findAllEmails();
		final String email = auditorRegisterForm.getEmail();
		final boolean bEmail = !emails.contains(email);
		Assert.isTrue(bEmail);

		final Collection<String> accounts = this.actorService.findAllAccounts();
		final UserAccount userAccount = result.getUserAccount();
		final Boolean bAccount = !accounts.contains(auditorRegisterForm.getUsername());
		Assert.isTrue(bAccount);

		final Md5PasswordEncoder pe = new Md5PasswordEncoder();
		final String password = pe.encodePassword(auditorRegisterForm.getPassword(), null);
		userAccount.setPassword(password);
		userAccount.setUsername(auditorRegisterForm.getUsername());
		result.setUserAccount(userAccount);
		result.setAddress(auditorRegisterForm.getAddress());
		result.setBanned(false);

		final CreditCard creditCard = result.getCreditCard();
		creditCard.setBrandName(auditorRegisterForm.getBrandName());
		creditCard.setCvv(auditorRegisterForm.getCvv());
		creditCard.setExpirationMonth(auditorRegisterForm.getExpirationMonth());
		creditCard.setExpirationYear(auditorRegisterForm.getExpirationYear());
		creditCard.setHolderName(auditorRegisterForm.getHolderName());
		creditCard.setNumber(auditorRegisterForm.getNumber());
		final Boolean b = Utils.creditCardIsExpired(creditCard);
		Assert.isTrue(!b);
		result.setCreditCard(creditCard);

		result.setEmail(auditorRegisterForm.getEmail());
		result.setName(auditorRegisterForm.getName());
		result.setPhone(this.actorService.addCountryCode(auditorRegisterForm.getPhone()));
		result.setPhoto(auditorRegisterForm.getPhoto());
		result.setSpammer(false);
		result.setSurnames(auditorRegisterForm.getSurnames());

		final String vat = auditorRegisterForm.getVAT();
		result.setVAT(vat.toUpperCase());
		return result;
	}

	//Other Methods--------------------
}
