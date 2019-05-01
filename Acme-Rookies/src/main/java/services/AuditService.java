package services; 

import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.util.Assert; 

import repositories.AuditRepository;

import domain.Audit; 
import domain.Item;

@Service 
@Transactional 
public class AuditService { 

	//Managed repository -------------------
	@Autowired
	private AuditRepository auditRepository;


	//Supporting Services ------------------


	//COnstructors -------------------------
	public AuditService(){
		super();
	}


	//Simple CRUD methods--------------------

	public Audit create(){
		Audit result;

		result = new Audit();

		return result;
	}

	public Collection<Audit> findAll(){
		Collection<Audit> result;

		result = auditRepository.findAll();

		return result;
	}

	public Audit findOne(int auditId){
		Audit result;

		result = auditRepository.findOne(auditId);

		return result;
	}

	public void save(Audit audit){
		Assert.notNull(audit);

		auditRepository.save(audit);
	}

	public void delete(Audit audit){
		auditRepository.delete(audit);
	}


	//Other Methods--------------------
} 
