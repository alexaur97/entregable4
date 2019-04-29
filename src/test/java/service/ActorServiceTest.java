
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ActorService;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;


	//Este test testea el requisito 7.1  Un actor autenticado 
	//puede editar sus datos personales

	// Análisis del sentence coverage: 
	// 1. El usuario edita sus datos personales.
	// 2. Los datos editados se guardan correctamente.

	// Análisis del data coverage:

	// Estamos verificando en nuestro modelo de datos que un usuario puede editar sus datos personales

	@Test
	public void testEditActor() {
		super.authenticate("rookie1");
		final Actor a = this.actorService.findByPrincipal();
		a.setPhone("8798798");
		this.actorService.save(a);
		super.unauthenticate();
	}

}
