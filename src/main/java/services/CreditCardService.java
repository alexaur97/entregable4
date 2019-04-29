
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Actor;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private ActorService			actorService;


	public CreditCard create() {
		return new CreditCard();
	}

	public CreditCard findAllByPrincipal() {
		final Actor a = this.actorService.findByPrincipal();
		final CreditCard result = this.creditCardRepository.findByPrincipal(a.getId());
		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		final CreditCard result = this.creditCardRepository.save(creditCard);
		return result;
	}

}
