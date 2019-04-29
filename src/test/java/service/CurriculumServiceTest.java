
package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.CurriculumService;
import services.RookieService;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.Rookie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RookieService		rookieService;


	//Requisito 17.1 Un rookie puede editar un currículum que le pertenezca

	@Test
	public void testEditCurriculum() {
		super.authenticate("rookie1");
		final Rookie principal = this.rookieService.findByPrincipal();
		final Curriculum curriculum = (Curriculum) principal.getCurriculums().toArray()[0];
		curriculum.setIdName("Setting");
		this.curriculumService.save(curriculum);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Rookie modifique un currículum ajeno
	//esto debe provocar un error.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "save" comprueba
	// que el currículum a editar no pertenece a la lista de currículums del rookie logueado.

	@Test(expected = IllegalArgumentException.class)
	public void testEditCurriculumOtherRookie() {
		super.authenticate("rookie2");
		this.rookieService.findByPrincipal();
		final int idH = super.getEntityId("actor5");
		final Curriculum curriculum = (Curriculum) this.curriculumService.findByRookie(idH).toArray()[0];
		curriculum.setIdName("Setting");
		this.curriculumService.save(curriculum);
		super.unauthenticate();
	}

	//Requisito 17.1 Un rookie puede eliminar un currículum que le pertenezca

	@Test
	public void testDeleteCurriculum() {
		super.authenticate("rookie1");
		final Rookie principal = this.rookieService.findByPrincipal();
		final Curriculum curriculum = (Curriculum) principal.getCurriculums().toArray()[0];

		this.curriculumService.delete(curriculum);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Rookie elimine un currículum ajeno
	//esto debe provocar un error.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "delete" comprueba
	// que el currículum a eliminar no pertenece a la lista de currículums del rookie logueado.

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteCurriculumOtherRookie() {
		super.authenticate("rookie2");
		this.rookieService.findByPrincipal();
		final int idH = super.getEntityId("actor5");
		final Curriculum curriculum = (Curriculum) this.curriculumService.findByRookie(idH).toArray()[0];
		curriculum.setIdName("Setting");
		this.curriculumService.delete(curriculum);
		super.unauthenticate();
	}

	//Requisito 17.1 Un rookie puede crear la copia de un currículum que le pertenezca

	@Test
	public void testCopyCurriculum() {
		super.authenticate("rookie1");
		final Rookie principal = this.rookieService.findByPrincipal();
		final Curriculum curriculum = (Curriculum) principal.getCurriculums().toArray()[0];
		final Curriculum copy = this.curriculumService.copyCurriculum(curriculum);
		Assert.isTrue(curriculum.getId() != copy.getId());
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Rookie cree la copia de un currículum ajeno
	//esto debe provocar un error.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "copyCurriculum" comprueba
	// que el currículum a copiar no pertenece a la lista de currículums del rookie logueado.

	@Test(expected = IllegalArgumentException.class)
	public void testCopyCurriculumOtherRookie() {
		super.authenticate("rookie2");
		this.rookieService.findByPrincipal();
		final int idH = super.getEntityId("actor5");
		final Curriculum curriculum = (Curriculum) this.curriculumService.findByRookie(idH).toArray()[0];
		final Curriculum copy = this.curriculumService.copyCurriculum(curriculum);
		Assert.isTrue(curriculum.getId() != copy.getId());
		super.unauthenticate();
	}

}
