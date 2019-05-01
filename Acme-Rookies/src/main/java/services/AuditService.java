
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;

@Service
@Transactional
public class AuditService {

	//Managed repository -------------------
	@Autowired
	private AuditRepository	auditRepository;


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

		this.auditRepository.save(audit);
	}

	public void delete(final Audit audit) {
		this.auditRepository.delete(audit);
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
}
