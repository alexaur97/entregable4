
package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.UserAccount;
import services.CompanyService;
import utilities.AbstractTest;
import domain.Company;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CompanyServiceTest extends AbstractTest {

	@Autowired
	private CompanyService	companyService;


	//Este test testea el requisito 7.1  Un actor no autenticado 
	//puede registrarse en el sistema como COMPANY

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El usuario rellena los datos obligatorios para crear su cuenta.
	// 2. La empresa se crea correctamente

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede registrarse como Company en el sistema

	@Test
	public void testCreateCompany() {
		final Company c = this.companyService.create();
		c.setBanned(false);
		c.setCommercialName("comercial");
		final CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("bn");
		creditCard.setCvv(111);
		creditCard.setExpirationMonth(11);
		creditCard.setExpirationYear(88);
		creditCard.setNumber("4296908289844192");
		creditCard.setHolderName("Mike");
		c.setCreditCard(creditCard);
		c.setEmail("email@email.com");
		c.setName("name");
		c.setPhone("9999999");
		c.setPhoto("https://cdn.pixabay.com/photo/2014/06/01/21/05/photo-effect-359981_960_720.jpg");
		c.setSpammer(false);
		c.setSurnames("alfa beta");
		c.setVAT("ESA9999999B");

		final UserAccount userAccount = c.getUserAccount();
		userAccount.setPassword("password");
		userAccount.setUsername("username");
		c.setUserAccount(userAccount);

		this.companyService.save(c);
	}
}
