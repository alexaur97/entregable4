
package service;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.SocialProfileService;
import utilities.AbstractTest;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	@Autowired
	SocialProfileService	socialProfileService;


	//Este test testea el requisito 23.1 Un Actor autenticado debe
	// poder crear sus perfiles.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Selecciona crear un nuevo perfil en mis perfiles
	// 3. Introduce los datos que se le pide en el formulario correctamente
	// 4. Le da a crear una vez que ha introducido los datos
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede
	// crear un perfil

	@Test
	public void testCreateSocialProfileGood() throws ParseException {

		super.authenticate("company1");
		SocialProfile socialProfile = this.socialProfileService.create();
		socialProfile.setNick("company1");
		socialProfile.setSocialNetwork("twitter");
		socialProfile.setLink("http://twitter.es");
		socialProfile = this.socialProfileService.reconstruct(socialProfile, null);
		this.socialProfileService.save(socialProfile);
		super.unauthenticate();

	}
	// Para el caso negativo estamos intentando que un actor cree su perfil
	// introduciendo mal el link de una red social , esto debe provocar un fallo
	// en el sistema porque el link debe cumplir el patrón de una URL
	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Selecciona crear un nuevo perfil en mis perfiles
	// 3. Introduce los datos que se le pide en el formulario 
	// 4. Le da a crear una vez que ha introducido los datos
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que no se puede introducir
	// un link que no sea una URL válida.

	@Test(expected = NullPointerException.class)
	public void testCreateSocialProfileError() throws ParseException {

		super.authenticate("company1");
		SocialProfile socialProfile = this.socialProfileService.create();
		socialProfile.setNick("company1");
		socialProfile.setSocialNetwork("twitter");
		socialProfile.setLink("twitter");
		socialProfile = this.socialProfileService.reconstruct(socialProfile, null);
		this.socialProfileService.save(socialProfile);
		super.unauthenticate();

	}

	//Este test testea el requisito 23.1 Un Actor autenticado debe
	// poder eliminar sus perfiles.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Selecciona sus perfiles y selecciona editar en uno de ellos
	// 3. Pulsa en borrar uno de sus perfiles
	// 4. El sistema borra el perfil seleccionado
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede 
	// eliminar uno de sus perfiles

	@Test
	public void testDeleteSocialProfileGood() {

		super.authenticate("company2");
		final int socialProfileId = super.getEntityId("socialProfile3");
		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		this.socialProfileService.delete(socialProfile);
		super.unauthenticate();

	}

	// Para el caso negativo estamos intentando que un actor elimine un
	// perfil que no le pertenece introduciendo malevolamente una id en la url
	// que no es la suya

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Selecciona sus perfiles y selecciona editar en uno de ellos
	// 3. Al darle a delete cambia desde la url la id del perfil a borrar e
	// introduce una id de otro perfil
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario solo puede
	// borrar sus perfiles y no los de otro actor

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteSocialProfileError() throws ParseException {

		super.authenticate("company2");
		final int socialProfileId = super.getEntityId("socialProfile1");
		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		this.socialProfileService.delete(socialProfile);
		super.unauthenticate();

	}
}
