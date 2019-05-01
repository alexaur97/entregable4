
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CurriculumService;
import services.EducationDataService;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationDataServiceTest extends AbstractTest {

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private CurriculumService		curriculumService;


	//Requisito 17.1 Un rookie puede editar los Datos Academicos de su curriculum

	@Test
	public void testEditEducationDataGood() {
		super.authenticate("rookie1");

		final int IdEducationData = super.getEntityId("educationData1");
		final EducationData edData = this.educationDataService.findOne(IdEducationData);

		final Curriculum curriculum = this.curriculumService.findByEducationData(edData);

		edData.setMark("10");
		this.educationDataService.save(edData, curriculum);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Rookie modifique los datos
	// Academicos con una fecha de inicio posterior a la de final,
	//esto debe provocar un error.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "save" comprueba
	// que las fechas tengan sentido logico.

	@Test(expected = IllegalArgumentException.class)
	public void testEditEducationDataError() throws ParseException {
		super.authenticate("rookie1");

		final int IdEducationData = super.getEntityId("educationData1");
		final EducationData edData = this.educationDataService.findOne(IdEducationData);
		final Curriculum curriculum = this.curriculumService.findByEducationData(edData);
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		final String stringFecha = "02/09/1926";
		final Date fecha = sdf.parse(stringFecha);

		edData.setEndDate(fecha);
		this.educationDataService.save(edData, curriculum);
		super.unauthenticate();
	}

	//Requisito 17.1 Un Actor autenticado como Rookie puede borrar los datos Academicos.

	@Test
	public void testDeleteEducationDataGood() {
		super.authenticate("rookie1");

		final int IdEducationData = super.getEntityId("educationData1");
		final EducationData edData = this.educationDataService.findOne(IdEducationData);
		this.educationDataService.delete(edData);
		super.unauthenticate();
	}
	//	Para el caso negativo estamos intentando que una Empresa elimine los datos academicos
	// del curriculum de un Rookie, esto debe provocar un error porque solo esta autorizado
	//para esta accion un Rookie.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "delete" comprueba
	// que el Actor es un rookie.

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteEducationDataError() {
		super.authenticate("company1");

		final int IdEducationData = super.getEntityId("educationData1");
		final EducationData edData = this.educationDataService.findOne(IdEducationData);

		this.educationDataService.delete(edData);
		super.unauthenticate();
	}

}
