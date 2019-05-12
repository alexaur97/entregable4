
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AuditorService;
import utilities.AbstractTest;
import domain.Auditor;
import forms.AuditorRegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	@Autowired
	private AuditorService	auditorService;


	//Requisito 4.2 Un Administrator debe poder crear un nuevo auditor en el sistema

	@Test
	public void testCreateAuditorGood() {
		super.authenticate("admin");
		Auditor aud = this.auditorService.create();
		final AuditorRegisterForm form = new AuditorRegisterForm();
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

		aud = this.auditorService.constructByForm(form);
		this.auditorService.save(aud);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un actor que no es administrador cree un nuevo auditor.
	//Esto debe provocar un error.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "create" comprueba
	// que el actor logueado no tiene la autoridad ADMINISTRATOR.

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAuditorError() {
		super.authenticate("company1");
		Auditor aud = this.auditorService.create();
		final AuditorRegisterForm form = new AuditorRegisterForm();
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

		aud = this.auditorService.constructByForm(form);
		this.auditorService.save(aud);
		super.unauthenticate();
	}
}
