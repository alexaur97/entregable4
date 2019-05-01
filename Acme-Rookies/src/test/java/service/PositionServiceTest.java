
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

import services.PositionService;
import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService	positionService;


	//Requisito 9.1 Un Actor autenticado como Empresa puede crear una posicion.
	@Test
	public void testCreatePositionGood() throws ParseException {
		super.authenticate("company1");
		Position position = this.positionService.create();
		position.setTitle("title");
		position.setDescription("description");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final String stringFecha = "02-09-2020";
		final Date fecha = sdf.parse(stringFecha);
		position.setDeadline(fecha);
		position.setProfileRequired("profileRequired");
		position.setSkillRequired("skillRequired");
		position.setTechRequired("techRequired");
		position.setSalaryOffered(200);
		position = this.positionService.reconstruct(position, null);
		this.positionService.save(position);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Rookie modifique una posicion
	//esto debe provocar un fallo en el sistema porque solo puede modificarlo una empresa
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "reconstruct" comprueba
	// que el usuario logueado sea una empresa.

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePositionError() throws ParseException {
		super.authenticate("rookie1");
		Position position = this.positionService.create();
		position.setTitle("title");
		position.setDescription("description");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final String stringFecha = "02-09-2020";
		final Date fecha = sdf.parse(stringFecha);
		position.setDeadline(fecha);
		position.setProfileRequired("profileRequired");
		position.setSkillRequired("skillRequired");
		position.setTechRequired("techRequired");
		position.setSalaryOffered(200);
		position = this.positionService.reconstruct(position, null);
		this.positionService.save(position);
		super.unauthenticate();
	}
	//Requisito 9.1 Un Actor autenticado como Empresa puede editar una posicion.

	@Test
	public void testEditModePositionGood() {
		super.authenticate("company1");

		final int IdPosition = super.getEntityId("position3");
		Position position = this.positionService.findOne(IdPosition);

		position = this.positionService.saveMode(position);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que una Empresa modifique una posicion
	// cambiando su modo a FINAL sin tener al menos dos problemas asociados
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "saveMode" comprueba
	// que la posicion tenga dos o mas problemas asociados.

	@Test(expected = IllegalArgumentException.class)
	public void testEditModePositionError() {
		super.authenticate("company1");

		final int IdPosition = super.getEntityId("position2");
		Position position = this.positionService.findOne(IdPosition);

		position = this.positionService.saveMode(position);
		super.unauthenticate();
	}

	//Requisito 9.1 Un Actor autenticado como Empresa puede borrar una posicion.

	@Test
	public void testDeletePositionGood() {
		super.authenticate("company1");

		final int IdPosition = super.getEntityId("position2");
		final Position position = this.positionService.findOne(IdPosition);

		this.positionService.delete(position);
		super.unauthenticate();
	}
	//	Para el caso negativo estamos intentando que una Empresa elimine una posicion
	// en modo FINAL , esto debe provocar un error porque en estado final solo se podria cancelar,
	//pero no eliminar la posicion.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "delete" comprueba
	// que la posicion está en modo FINAL y se produce un error.

	@Test(expected = IllegalArgumentException.class)
	public void testDeletePositionError() {
		super.authenticate("company1");

		final int IdPosition = super.getEntityId("position8");
		final Position position = this.positionService.findOne(IdPosition);

		this.positionService.delete(position);
		super.unauthenticate();
	}
	//Requisito 9.1 Un Actor autenticado como Empresa puede cancelar una posicion cuando esta en modo FINAL.

	@Test
	public void testCancelPositionGood() {
		super.authenticate("company1");

		final int IdPosition = super.getEntityId("position1");
		final Position position = this.positionService.findOne(IdPosition);

		this.positionService.cancel(position);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que una Empresa cancele una posicion
	// en modo borrador , esto debe provocar un error porque en estado borrador solo se podria eliminar.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "cancel" comprueba
	// que la posicion está en modo borrador y se produce un error.
	@Test(expected = IllegalArgumentException.class)
	public void testCancelPositionError() {
		super.authenticate("company1");

		final int IdPosition = super.getEntityId("position2");
		final Position position = this.positionService.findOne(IdPosition);

		this.positionService.cancel(position);
		super.unauthenticate();
	}

	//Requisito 9.1 Un Actor autenticado como Empresa puede editar una posicion cuando esta en modo Borrador.

	@Test
	public void testEditPositionGood() {
		super.authenticate("company1");
		final int IdPosition = super.getEntityId("position2");
		Position position = this.positionService.findOne(IdPosition);
		position.setDescription("nueva descripcion");
		position = this.positionService.reconstruct(position, null);
		this.positionService.save(position);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que una Empresa edite una posicion
	// en modo FINAL , esto debe provocar un error porque en estado FINAL no se podria eliminar.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "save" comprueba
	// que la posicion está en modo final y se produce un error.
	@Test(expected = IllegalArgumentException.class)
	public void testEditPositionError() {
		super.authenticate("company1");

		final int IdPosition = super.getEntityId("position1");
		Position position = this.positionService.findOne(IdPosition);

		position.setDescription("nueva descripcion");
		position = this.positionService.reconstruct(position, null);
		this.positionService.save(position);
		super.unauthenticate();
	}

}
