
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	//Managed repository -------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


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
}
