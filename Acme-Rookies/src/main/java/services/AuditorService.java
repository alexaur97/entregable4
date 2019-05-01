
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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
import domain.Position;

@Service
@Transactional
public class AuditorService {

	//Managed repository -------------------
	@Autowired
	private AuditorRepository	auditorRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public AuditorService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Auditor create() {
		Auditor result;

		result = new Auditor();

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

	//Other Methods--------------------
}
