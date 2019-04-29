
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	//Managed repository -------------------
	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	//Supporting Services ------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//COnstructors -------------------------
	public SocialProfileService() {
		super();
	}

	//Simple CRUD methods--------------------

	public SocialProfile create() {
		SocialProfile result;

		result = new SocialProfile();

		return result;
	}

	public Collection<SocialProfile> findAll() {
		Collection<SocialProfile> result;

		result = this.socialProfileRepository.findAll();

		return result;
	}

	public SocialProfile findOne(final int socialProfileId) {
		SocialProfile result;

		result = this.socialProfileRepository.findOne(socialProfileId);

		return result;
	}

	public void save(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		final int id = this.actorService.findByPrincipal().getId();
		Assert.isTrue(socialProfile.getActor().getId() == id);

		this.socialProfileRepository.save(socialProfile);
	}

	public void delete(final SocialProfile socialProfile) {

		final int id = this.actorService.findByPrincipal().getId();
		Assert.isTrue(socialProfile.getActor().getId() == id);

		this.socialProfileRepository.delete(socialProfile);
	}

	public Collection<SocialProfile> findByActor(final Integer actorId) {
		final Collection<SocialProfile> result = this.socialProfileRepository.findByActor(actorId);
		return result;

	}

	public SocialProfile reconstruct(final SocialProfile socialProfile, final BindingResult binding) {
		final SocialProfile res = socialProfile;
		final Actor a = this.actorService.findByPrincipal();

		res.setActor(a);

		this.validator.validate(res, binding);
		return res;
	}

	//Other Methods--------------------
}
