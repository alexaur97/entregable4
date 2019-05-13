
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AuditService {

	//Managed repository -------------------
	@Autowired
	private AuditRepository	auditRepository;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private Validator		validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public AuditService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Audit create() {
		Audit result;

		result = new Audit();

		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;

		result = this.auditRepository.findAll();

		return result;
	}

	public Audit findOne(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);

		return result;
	}

	public void save(final Audit audit) {
		Assert.notNull(audit);
		if (audit.getId() != 0) {
			final Audit auditDB = this.findOne(audit.getId());
			Assert.isTrue(auditDB.getMode().equals("DRAFT"));
		}
		this.auditRepository.save(audit);
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		final Audit auditDB = this.findOne(audit.getId());
		Assert.isTrue(auditDB.getMode().equals("DRAFT"));
		this.auditRepository.delete(audit);
	}

	public Collection<Audit> findByAuditor() {
		final Auditor auditor = this.auditorService.findByPrincipal();
		final Collection<Audit> res = this.auditRepository.findByPrincipal(auditor.getId());
		return res;
	}

	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		final Audit res = audit;

		final Auditor auditor = this.auditorService.findByPrincipal();
		res.setAuditor(auditor);
		final Date date = new Date();
		res.setMoment(date);

		this.validator.validate(res, binding);
		return res;
	}

	//Other Methods--------------------

	public Collection<Object> statsAuditScorePerPosition() {
		final Collection<Object> result = this.auditRepository.statsAuditScorePerPosition();
		return result;
	}

	public Collection<Object> statsAuditScorePerCompany() {
		final Collection<Object> result = this.auditRepository.statsAuditScorePerCompany();
		return result;
	}

	public Collection<Audit> findByPosition(final int positionId) {
		final Collection<Audit> res = this.auditRepository.findByPosition(positionId);
		return res;
	}
}
