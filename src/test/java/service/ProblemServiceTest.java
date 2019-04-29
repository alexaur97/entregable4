
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ProblemService;
import utilities.AbstractTest;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProblemServiceTest extends AbstractTest {

	@Autowired
	private ProblemService	problemService;


	//Este test testea el requisito 9.2 Un Actor autenticado como Company debe
	// poder editar sus problemas.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. La empresa se loguea
	// 2. La empresa elije un problema para editar
	// 3. La empresa edita el problema y lo guarda 

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que una empresa puede
	// editar un problema suyo

	@Test
	public void testEditProblem() {
		super.authenticate("company1");
		final int id = super.getEntityId("problem1");
		Problem p = this.problemService.findOne(id);
		p.setHint("setting hint");
		p = this.problemService.reconstruct(p, null);
		this.problemService.save(p);
		super.unauthenticate();
	}

	//Este test testea el requisito 9.2 Un Actor autenticado como Company no debe
	// poder editar sus problemas en modo FINAL.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. La empresa se loguea
	// 2. La empresa elije un problema en FINAL para editar
	// 3. La empresa edita el problema pero no puede guardarlo

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que una empresa no puede
	// editar un problema en modo FINAL

	@Test(expected = IllegalArgumentException.class)
	public void testEditProblemInFinal() {
		super.authenticate("company1");
		final int id = super.getEntityId("problem2");
		Problem p = this.problemService.findOne(id);
		p.setHint("setting hint");
		p = this.problemService.reconstruct(p, null);
		this.problemService.save(p);
		super.unauthenticate();
	}

	//Este test testea el requisito 9.2 Un Actor autenticado como Company debe
	// poder eliminar sus problemas.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. La empresa se loguea
	// 2. La empresa elije un problema para visualizarlo o editarlo
	// 3. La empresa decide eliminar ese problema

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que una empresa puede
	// eliminar un problema suyo

	@Test
	public void testDeleteProblem() {
		super.authenticate("company1");
		final int id = super.getEntityId("problem1");
		final Problem p = this.problemService.findOne(id);
		this.problemService.delete(p);
		super.unauthenticate();
	}

	//Este test testea el requisito 9.2 Un Actor autenticado como Company no debe
	// poder eliminar un problema que no es suyo.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. La empresa se loguea
	// 2. La empresa visualiza un problema que no le pertenece
	// 3. La empresa intenta eliminar ese problema pero no puede hacerlo

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que una empresa no puede
	// eliminar un problema que no es suyo

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteProblemWithOtherCompany() {
		super.authenticate("company2");
		final int id = super.getEntityId("problem1");
		final Problem p = this.problemService.findOne(id);
		this.problemService.delete(p);
		super.unauthenticate();
	}
}
