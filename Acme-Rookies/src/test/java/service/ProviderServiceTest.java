
package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.UserAccount;
import services.ProviderService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProviderServiceTest extends AbstractTest {

	@Autowired
	private ProviderService	providerService;


	//Este test testea el requisito 9.3  Un actor no autenticado 
	//puede registrarse en el sistema como PROVIDER

	// Análisis del sentence coverage: 
	// 1. El usuario rellena los datos obligatorios para crear su cuenta.
	// 2. El proveedor se crea correctamente

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario puede registrarse como Proveedor en el sistema

	@Test
	public void testCreateProvider() {
		final Provider p = this.providerService.create();
		p.setBanned(false);
		final CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("bn");
		creditCard.setCvv(111);
		creditCard.setExpirationMonth(11);
		creditCard.setExpirationYear(88);
		creditCard.setNumber("4296908289844192");
		creditCard.setHolderName("Mike");
		p.setCreditCard(creditCard);
		p.setEmail("email@email.com");
		p.setName("name");
		p.setPhone("9999999");
		p.setPhoto("https://cdn.pixabay.com/photo/2014/06/01/21/05/photo-effect-359981_960_720.jpg");
		p.setSpammer(false);
		p.setSurnames("alfa beta");
		p.setVAT("ESA9999999B");

		final UserAccount userAccount = p.getUserAccount();
		userAccount.setPassword("password");
		userAccount.setUsername("username");
		p.setUserAccount(userAccount);

		this.providerService.save(p);
	}

}
