
package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.CurriculumService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Application;
import domain.Curriculum;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private CurriculumService	curriculumService;


	//Este test testea el requisito 9.3  Un actor autenticado como Company
	//puede aceptar una Application submitted

	// Análisis del sentence coverage: 
	// 1. El usuario acepta la Application
	// 2. La Application se guarda correctamente

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que una Company puede aceptar una Application submitted

	@Test
	public void testAcceptApplicationGood() {
		super.authenticate("company1");
		final int applicationId = super.getEntityId("application3");
		final Application application = this.applicationService.acceptApplicationChanges(applicationId);
		final Application a = this.applicationService.saveCompany(application);
		Assert.isTrue(a.getStatus().equals("ACCEPTED"));
	}

	//Este test testea el requisito 9.3  Un actor autenticado como Company
	// no puede aceptar una Application rechazada

	// Análisis del sentence coverage: 
	// 1. El usuario intenta aceptar la Application
	// 2. Se produce un error al intentar sobreescribir la Application

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario no puede aceptar una 
	//Application rechazada

	@Test(expected = IllegalArgumentException.class)
	public void testAcceptApplicationError() {
		super.authenticate("company1");
		final int applicationId = super.getEntityId("application2");
		final Application application = this.applicationService.acceptApplicationChanges(applicationId);
		this.applicationService.saveCompany(application);
	}
	//Este test testea el requisito 9.3  Un actor autenticado como Company
	//  puede rechazar una Application submitted

	// Análisis del sentence coverage: 
	// 1. El usuario intenta rechazar la Application
	// 2. La Application se rechaza y se guarda correctamente

	// Análisis del data coverage:
	// Estamos verificando en nuestro modelo de datos que un usuario puede rechazar una 
	//Application submitted

	@Test
	public void testRejectApplicationGood() {
		super.authenticate("company1");
		final int applicationId = super.getEntityId("application3");
		final Application application = this.applicationService.rejectApplicationChanges(applicationId);
		final Application a = this.applicationService.saveCompany(application);
		Assert.isTrue(a.getStatus().equals("REJECTED"));
	}

	//Este test testea el requisito 9.3  Un actor autenticado como Company
	// no puede rechazar una Application aceptada

	// Análisis del sentence coverage: 
	// 1. El usuario intenta rechazar la Application
	// 2. Se produce un error al intentar sobreescribir la Application

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario no puede rechazar una 
	//Application aceptada

	@Test(expected = IllegalArgumentException.class)
	public void testRejectApplicationError() {
		super.authenticate("company1");
		final int applicationId = super.getEntityId("application1");
		final Application application = this.applicationService.acceptApplicationChanges(applicationId);
		this.applicationService.saveCompany(application);
	}

	//Este test testea el requisito 9.3  Un actor autenticado como Rookie
	// puede crear una Application

	// Análisis del sentence coverage: 
	// 1. El usuario rellena los datos de la Application
	// 2. La Application se crea correctamente

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario autenticado como Rookie
	// puede crear una Application

	@Test
	public void testCreateApplicationGood() {
		super.authenticate("rookie1");
		final Application application = new Application();
		final int positionId = super.getEntityId("position1");
		final Position position = this.positionService.findOne(positionId);
		application.setPosition(position);
		final int curriculumId = super.getEntityId("curriculum1");
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		application.setCurriculum(curriculum);
		final Application applicationFinal = this.applicationService.recostructionCreate(application, null);
		this.applicationService.saveRookie(applicationFinal);
	}

	//Este test testea el requisito 9.3  Un actor autenticado como Rookie
	// no puede crear una Application sin Currículum ni Position

	// Análisis del sentence coverage: 
	// 1. El usuario intenta crear la Application
	// 2. Se produce un error al validar la Application
	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario no puede crear una 
	//Application si Currículum ni Position

	@Test(expected = NullPointerException.class)
	public void testCreateApplicationBad() {
		super.authenticate("rookie1");
		final Application application = new Application();
		final Application applicationFinal = this.applicationService.recostructionCreate(application, null);
		this.applicationService.saveRookie(applicationFinal);
	}

	//Este test testea el requisito 9.3  Un actor autenticado como Rookie
	// puede editar una Application

	// Análisis del sentence coverage: 
	// 1. El usuario edita la Application
	// 2. La Application se guarda correctamente

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario puede editar una 
	//Application

	@Test
	public void testEditApplicationGood() {
		super.authenticate("rookie2");
		final int applicationId = super.getEntityId("application5");
		final Application application = this.applicationService.findOne(applicationId);
		application.setExplanation("HOLA");
		application.setCodeLink("HOLA");
		final Application applicationFinal = this.applicationService.recostructionEdit(application, null);
		this.applicationService.saveRookie(applicationFinal);

	}

	//Este test testea el requisito 9.3  Un actor autenticado como Rookie
	// no puede editar una Application con Explanation y CodeLink vacíos

	// Análisis del sentence coverage: 
	// 1. El usuario intenta editar la Application
	// 2. Se produce un error al validar la Application

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario puede editar una 
	// Application

	@Test(expected = IllegalArgumentException.class)
	public void testEditApplicationBad() {
		super.authenticate("rookie2");
		final int applicationId = super.getEntityId("application5");
		final Application application = this.applicationService.findOne(applicationId);
		application.setExplanation("");
		application.setCodeLink("");
		final Application applicationFinal = this.applicationService.recostructionEdit(application, null);
		this.applicationService.saveRookie(applicationFinal);

	}
}
