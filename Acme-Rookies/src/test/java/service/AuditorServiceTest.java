
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AuditorService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Auditor;
import domain.Position;
import forms.AuditorRegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PositionService	positionService;


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
	//Requisito 3.1 Un Actor autenticado como Auditor puede asignarse una posicion.

	@Test
	public void testSelfAssignPositionGood() {
		super.authenticate("auditor1");
		Auditor auditor = this.auditorService.findByPrincipal();
		final int IdPosition = super.getEntityId("position2");
		final Position position = this.positionService.findOne(IdPosition);
		auditor.getPositions().add(position);
		auditor = this.auditorService.reconstruct(auditor, null);

		this.auditorService.save(auditor);
		super.unauthenticate();
	}
	//	Para el caso negativo estamos intentando que un Auditor se asigne una posicion en modo borrador
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "reconstruct" comprueba
	// que la position seleccionada cumpla todas las condiciones.

	@Test(expected = IllegalArgumentException.class)
	public void testSelfAssignPositionError() {
		super.authenticate("auditor1");
		Auditor auditor = this.auditorService.findByPrincipal();
		final int IdPosition = super.getEntityId("position3");
		final Position position = this.positionService.findOne(IdPosition);
		auditor.getPositions().add(position);
		auditor = this.auditorService.reconstruct(auditor, null);

		this.auditorService.save(auditor);
		super.unauthenticate();
	}
}
