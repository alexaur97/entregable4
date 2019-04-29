
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdministratorService;
import utilities.AbstractTest;
import domain.Administrator;
import forms.AdministratorRegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	//Requisito 11.1 Un Administrator debe poder crear un nuevo administrador del sistema

	@Test
	public void testCreateAdministratorGood() {
		super.authenticate("admin");
		Administrator adm = this.administratorService.create();
		final AdministratorRegisterForm form = new AdministratorRegisterForm();
		form.setName("name");
		form.setVAT("ES66666668");
		form.setSurnames("surnames");
		form.setPhoto("https://img.lovepik.com/element/40025/1507.png_860.png");
		form.setEmail("name@");
		form.setPhone("667890477");
		form.setAddress("Reina Mercedes");
		form.setUsername("username");
		form.setPassword("Ertuuuuuu7888");
		form.setConfirmPassword("Ertuuuuuu7888");
		form.setBrandName("Visa");
		form.setCvv(194);
		form.setExpirationMonth(02);
		form.setExpirationYear(22);
		form.setHolderName("Paolo Luna Rodríguez");
		form.setNumber("4278959519763522");

		adm = this.administratorService.reconstruct(form);
		this.administratorService.save(adm);
		super.unauthenticate();
	}
}
