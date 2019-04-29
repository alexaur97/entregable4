
package service;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.PersonalDataService;
import utilities.AbstractTest;
import domain.PersonalData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	@Autowired
	private PersonalDataService	personalDataService;


	//Requisito 17.1 Un rookie puede editar los Datos personales de su curriculum

	@Test
	public void testEditEducationData() {
		super.authenticate("rookie1");

		final int IdPersonalData = super.getEntityId("personalData1");
		final PersonalData personalData = this.personalDataService.findOne(IdPersonalData);
		personalData.setFullname("Setting");
		this.personalDataService.save(personalData);
		super.unauthenticate();
	}

	// Para el caso negativo estamos intentando que un Rookie modifique los datos
	// personales de un currículum ajeno
	// esto debe provocar un error.
	// Análisis del sentence coverage: el sistema al llamar al metodo del servicio "save" comprueba
	// que los datos personales no pertenecen a ningún currículum de la lista de currículums del rookie
	// logueado

	@Test(expected = IllegalArgumentException.class)
	public void testEditEducationDataOtherRookie() throws ParseException {
		super.authenticate("rookie2");

		final int IdPersonalData = super.getEntityId("personalData1");
		final PersonalData personalData = this.personalDataService.findOne(IdPersonalData);
		personalData.setFullname("Setting");
		this.personalDataService.save(personalData);
		super.unauthenticate();
	}

}
