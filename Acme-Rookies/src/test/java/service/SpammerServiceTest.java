
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ActorService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SpammerServiceTest extends AbstractTest {

	@Autowired
	ActorService	actorService;


	//Este test testea el requisito 24.2 Un administrador puede lanzar un proceso 
	// para clasificar a los usuarios como spammers o no spammers.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El administrador se loguea
	// 2. Se dirije al listado de actores
	// 3. Una vez en el listado de actores selecciona el botón de actores spammers
	// 4. Si el proceso ha ido con éxito, el sistema avisa al administrador.
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un administrador
	// puede lanzar un proceso para clasificar a los actores como spammers o no spammers.

	@Test
	public void testIsSpammerGood() {

		super.authenticate("admin");
		this.actorService.isSpammer();

	}

	//En el caso de error, vamos a comprobar que este proceso solo lo puede
	//lanzar un administrador.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. Se loguea un cliente
	// 2. Se dirije al listado de actores
	// 3. Una vez en el listado de actores selecciona el botón de actores spammers
	// 4. Si el proceso ha ido con éxito, el sistema avisa al administrador.
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que solo los administradores
	// pueden ejecutar este proceso

	@Test(expected = IllegalArgumentException.class)
	public void testIsSpammerError() {

		super.authenticate("company1");
		this.actorService.isSpammer();

	}
}
