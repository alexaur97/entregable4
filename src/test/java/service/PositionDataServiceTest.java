
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CurriculumService;
import services.PositionDataService;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.PositionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionDataServiceTest extends AbstractTest {

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private CurriculumService	curriculumService;


	//Requisito 17.1 Un rookie puede editar los Datos de posición de su curriculum
	@Test
	public void testEditPositionDataGood() {
		super.authenticate("rookie1");

		final int IdPositionData = super.getEntityId("positionData1");
		final PositionData posData = this.positionDataService.findOne(IdPositionData);

		posData.setDescription("description");
		final Curriculum curriculum = this.curriculumService.findByPositionData(posData.getId());
		this.positionDataService.save(posData, curriculum);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Rookie modifique los datos
	// de la Posicion con una fecha de inicio posterior a la de final,
	//esto debe provocar un error.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "save" comprueba
	// que las fechas tengan sentido logico.

	@Test(expected = ConstraintViolationException.class)
	public void testEditPositionDataError() throws ParseException {
		super.authenticate("rookie1");

		final int IdPositionData = super.getEntityId("positionData1");
		final PositionData posData = this.positionDataService.findOne(IdPositionData);

		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		final String stringFecha = "02/09/2026";
		final Date fecha = sdf.parse(stringFecha);

		posData.setStartDate(fecha);
		final Curriculum curriculum = this.curriculumService.findByPositionData(posData.getId());
		this.positionDataService.save(posData, curriculum);
		super.unauthenticate();
	}

	//Requisito 17.1 Un Actor autenticado como Rookie puede borrar los datos de una posicion.

	@Test
	public void testDeletePositionDataGood() {
		super.authenticate("rookie1");

		final int IdPositionData = super.getEntityId("positionData1");
		final PositionData posData = this.positionDataService.findOne(IdPositionData);

		this.positionDataService.delete(posData);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que una Empresa elimine los datos de una posicion
	// del curriculum de un Rookie, esto debe provocar un error porque solo esta autorizado
	//para esta accion un Rookie.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "delete" comprueba
	// que el Actor es un rookie.

	@Test(expected = IllegalArgumentException.class)
	public void testDeletePositionDataError() {
		super.authenticate("company1");

		final int IdPositionData = super.getEntityId("positionData1");
		final PositionData posData = this.positionDataService.findOne(IdPositionData);

		this.positionDataService.delete(posData);
		super.unauthenticate();
	}

}
