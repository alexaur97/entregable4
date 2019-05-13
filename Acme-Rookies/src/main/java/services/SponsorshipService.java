
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService {

	//Managed repository -------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;
	@Autowired
	private ProviderService			provideService;
	@Autowired
	private CreditCardService		creditCardService;
	@Autowired
	private Validator				validator;
	@Autowired
	ProviderService					providerService;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public SponsorshipService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Sponsorship create() {
		Sponsorship result;

		result = new Sponsorship();

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);

		return result;
	}

	public void save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		this.sponsorshipRepository.save(sponsorship);
	}

	public void delete(final Sponsorship sponsorship) {
		final Provider provider = this.providerService.findByPrincipal();
		Assert.isTrue(sponsorship.getProvider().equals(provider));
		this.sponsorshipRepository.delete(sponsorship);
	}

	//Other Methods--------------------

	public Collection<Sponsorship> findSponsorshipsByPosition(final int positionId) {
		return this.sponsorshipRepository.findSponshorshipsByPosition(positionId);
	}

	public Sponsorship randomSponshorship(final int positionId) {
		Sponsorship result = null;
		final Collection<Sponsorship> sponsorships = this.findSponsorshipsByPosition(positionId);
		for (final Sponsorship s : sponsorships) {
			result = s;
			break;
		}
		return result;
	}
	public Collection<Sponsorship> findByProvider(final Integer providerId) {
		return this.sponsorshipRepository.findSponshorshipsByProvider(providerId);
	}
	public Sponsorship recostruction(final SponsorshipForm sponsorshipForm) {
		final Sponsorship sponsorship = new Sponsorship();
		sponsorship.setBanner(sponsorshipForm.getBanner());
		sponsorship.setPosition(sponsorshipForm.getPosition());
		sponsorship.setProvider(this.provideService.findByPrincipal());
		sponsorship.setTargetPage(sponsorshipForm.getTargetPage());
		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName(sponsorshipForm.getBrandName());
		creditCard.setCvv(sponsorshipForm.getCvv());
		creditCard.setExpirationMonth(sponsorshipForm.getExpirationMonth());
		creditCard.setExpirationYear(sponsorshipForm.getExpirationYear());
		creditCard.setHolderName(sponsorshipForm.getHolderName());
		creditCard.setNumber(sponsorshipForm.getNumber());
		creditCard = this.creditCardService.save(creditCard);
		sponsorship.setCreditCard(creditCard);
		return sponsorship;
	}
	public Sponsorship recostructionEdit(final Sponsorship sponsorshipFinal, final BindingResult binding) {
		final Sponsorship sponsorship = this.findOne(sponsorshipFinal.getId());
		sponsorshipFinal.setCreditCard(sponsorship.getCreditCard());
		sponsorshipFinal.setProvider(sponsorship.getProvider());
		this.validator.validate(sponsorshipFinal, binding);
		return sponsorshipFinal;
	}
	public Collection<Double> statsSponsorshipsByProvider() {
		final Collection<Double> result = this.sponsorshipRepository.statsSponsorshipsByProvider();
		return result;
	}

	public Collection<Double> statsSponsorshipsByPosition() {
		final Collection<Double> result = this.sponsorshipRepository.statsSponsorshipsByPosition();
		return result;
	}
}
