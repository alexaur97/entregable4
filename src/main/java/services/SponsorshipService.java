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
	private SponsorshipRepository sponsorshipRepository;


	//Supporting Services ------------------


	//COnstructors -------------------------
	public SponsorshipService(){
		super();
	}


	//Simple CRUD methods--------------------

	public Sponsorship create(){
		Sponsorship result;

		result = new Sponsorship();

		return result;
	}

	public Collection<Sponsorship> findAll(){
		Collection<Sponsorship> result;

		result = sponsorshipRepository.findAll();

		return result;
	}

	public Sponsorship findOne(int sponsorshipId){
		Sponsorship result;

		result = sponsorshipRepository.findOne(sponsorshipId);

		return result;
	}

	public void save(Sponsorship sponsorship){
		Assert.notNull(sponsorship);

		sponsorshipRepository.save(sponsorship);
	}

	public void delete(Sponsorship sponsorship){
		sponsorshipRepository.delete(sponsorship);
	}


	//Other Methods--------------------
} 
