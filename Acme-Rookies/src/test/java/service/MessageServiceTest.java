
package service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ActorService;
import services.AdministratorService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;


	//Este test testea el requisito 23.2 Un Actor autenticado puede enviar un mensaje a otro

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Selecciona enviar mensaje en la pestaña de mensajes
	// 3. Introduce los datos que se le pide en el formulario correctamente
	// 4. Le da a enviar una vez que ha introducido los datos
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede
	// enviar un mensaje
	@Test
	public void testCreateMessageGood() {
		super.authenticate("company1");
		final Actor recipient = this.actorService.findByPrincipal();
		final Message msg = this.messageService.create();
		msg.setSubject("Test subject");
		msg.setBody("Test body");
		final ArrayList<String> tags = new ArrayList<>();
		tags.add("tag1");
		tags.add("tag2");
		msg.setTags(tags);
		msg.setRecipient(recipient);

		final Message message = this.messageService.reconstruct(msg, null);
		this.messageService.save(message);
		super.unauthenticate();
	}

	// Para el caso negativo estamos intentando que un actor envíe un mensaje
	// sin estar logueado. Esto debe provocar un fallo
	// en el sistema porque es necesario que el actor esté logueado para enviar el mensaje.
	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Selecciona enviar mensaje en la pestaña de mensajes
	// 3. El cliente se desloguea
	// 4. Introduce los datos que se le pide en el formulario 
	// 5. Le da a enviar una vez que ha introducido los datos
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro sistema que no se puede enviar un mensaje sin estar logueado

	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageError() {
		super.authenticate(null);
		final Actor recipient = this.actorService.findByPrincipal();
		final Message msg = this.messageService.create();
		msg.setSubject("Test subject");
		msg.setBody("Test body");
		final ArrayList<String> tags = new ArrayList<>();
		tags.add("tag1");
		tags.add("tag2");
		msg.setTags(tags);
		msg.setRecipient(recipient);
		final Message message = this.messageService.reconstruct(msg, null);
		this.messageService.save(message);
		super.unauthenticate();
	}

	//Este test testea el requisito 23.2 Un Actor autenticado puede eliminar definitivamente uno de sus mensajes

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Selecciona borrar mensaje en uno de sus mensajes borrados
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede
	// borrar definitivamente un mensaje
	@Test
	public void testDeleteMessageGood() throws ParseException {
		super.authenticate("company1");
		final Actor a = this.actorService.findByPrincipal();
		final Collection<Message> msgs = this.messageService.findSend(a.getId());
		final String tag = "DELETED";
		for (final Message m : msgs) {
			m.getTags().add(tag);
			m.setDeleted(true);
			this.messageService.save(m);
		}
		final Collection<Message> msgd = this.messageService.findDeleted(a.getId());
		for (final Message me : msgd)
			this.messageService.delete(me);

		super.unauthenticate();
	}

	// Para el caso negativo estamos intentando que un actor borre un mensaje
	// sin estar logueado. Esto debe provocar un fallo
	// en el sistema porque es necesario que el actor esté logueado para borar el mensaje.
	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea
	// 2. Entra en la vista de sus mensajes
	// 3. El cliente se desloguea
	// 4. Selecciona borrar mensaje en uno de sus mensajes borrados 
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro sistema que no se puede borrar un mensaje sin estar logueado
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteMessageError() throws ParseException {
		super.authenticate(null);
		final Actor a = this.actorService.findByPrincipal();
		final Collection<Message> msgs = this.messageService.findSend(a.getId());
		final String tag = "DELETED";
		for (final Message m : msgs) {
			m.getTags().add(tag);
			m.setDeleted(true);
			this.messageService.save(m);
		}
		final Collection<Message> msgd = this.messageService.findDeleted(a.getId());
		for (final Message me : msgd)
			this.messageService.delete(me);

		super.unauthenticate();
	}
	//Este test testea el requisito 4.1 Un Actor autenticado como Administrador puede enviar solo una notificacion de aviso de renombnramiento de la web

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea como Admin
	// 2. Selecciona notify en la pestaña de mensajes
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede
	// enviar una notificacion
	@Test
	public void testNotifyGood() {
		super.authenticate("admin");
		final int adminId = this.administratorService.findByPrincipal().getId();
		final Collection<Actor> actors = this.actorService.findOthersActors(adminId);
		final Message messageF = this.messageService.notifyWelcome();

		for (final Actor actor : actors) {
			final Message m = this.messageService.reconstructAdmnistrator2(messageF, actor, null);
			this.messageService.save(m);
			final Message mCopy = this.messageService.reconstructAdmnistrator2Copy(messageF, actor, null);
			this.messageService.save(mCopy);
		}

	}
	// Para el caso negativo estamos intentando que un actor envíe esta notificación dos veces.
	//  Esto debe provocar un fallo
	// en el sistema porque solo se puede enviar una vez la notificación.
	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea como administrador
	// 2. Selecciona enviar notificacion en la pestaña de mensajes
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro sistema que no se puede enviar mas de una notificacion
	@Test(expected = IllegalArgumentException.class)
	public void testNotifyError() {
		super.authenticate("admin");
		final int adminId = this.administratorService.findByPrincipal().getId();
		final Collection<Actor> actors = this.actorService.findOthersActors(adminId);
		final Message messageF = this.messageService.notifyWelcome();

		for (final Actor actor : actors) {
			final Message m = this.messageService.reconstructAdmnistrator2(messageF, actor, null);
			this.messageService.save(m);
			final Message mCopy = this.messageService.reconstructAdmnistrator2Copy(messageF, actor, null);
			this.messageService.save(mCopy);
		}
		final Message messageF2 = this.messageService.notifyWelcome();

		for (final Actor actor : actors) {
			final Message m = this.messageService.reconstructAdmnistrator2(messageF2, actor, null);
			this.messageService.save(m);
			final Message mCopy = this.messageService.reconstructAdmnistrator2Copy(messageF2, actor, null);
			this.messageService.save(mCopy);
		}
	}
}
