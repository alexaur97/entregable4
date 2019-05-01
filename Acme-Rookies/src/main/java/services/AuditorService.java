package services; 

import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.util.Assert; 

import repositories.AuditorRepository;

import domain.Auditor; 

@Service 
@Transactional 
public class AuditorService { 

	//Managed repository -------------------
	@Autowired
	private AuditorRepository auditorRepository;


	//Supporting Services ------------------


	//COnstructors -------------------------
	public AuditorService(){
		super();
	}


	//Simple CRUD methods--------------------

	public Auditor create(){
		Auditor result;

		result = new Auditor();

		return result;
	}

	public Collection<Auditor> findAll(){
		Collection<Auditor> result;

		result = auditorRepository.findAll();

		return result;
	}

	public Auditor findOne(int auditorId){
		Auditor result;

		result = auditorRepository.findOne(auditorId);

		return result;
	}

	public void save(Auditor auditor){
		Assert.notNull(auditor);

		auditorRepository.save(auditor);
	}

	public void delete(Auditor auditor){
		auditorRepository.delete(auditor);
	}


	//Other Methods--------------------
} 
