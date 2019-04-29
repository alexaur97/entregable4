package services; 

import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.util.Assert; 

import repositories.ProviderRepository;

import domain.Provider; 
import domain.Sponsorship;

@Service 
@Transactional 
public class ProviderService { 

	//Managed repository -------------------
	@Autowired
	private ProviderRepository providerRepository;


	//Supporting Services ------------------


	//COnstructors -------------------------
	public ProviderService(){
		super();
	}


	//Simple CRUD methods--------------------

	public Provider create(){
		Provider result;

		result = new Provider();

		return result;
	}

	public Collection<Provider> findAll(){
		Collection<Provider> result;

		result = providerRepository.findAll();

		return result;
	}

	public Provider findOne(int providerId){
		Provider result;

		result = providerRepository.findOne(providerId);

		return result;
	}

	public void save(Provider provider){
		Assert.notNull(provider);

		providerRepository.save(provider);
	}

	public void delete(Provider provider){
		providerRepository.delete(provider);
	}


	//Other Methods--------------------
} 
