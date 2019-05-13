
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;
import utilities.AbstractTest;
import domain.Position;
import domain.Sponsorship;
import forms.SponsorshipForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	SponsorshipService	sponsorshipService;
	@Autowired
	ProviderService		providerService;
	@Autowired
	PositionService		positionService;


	//Este test testea el requisito 13.1 Un Actor autenticado como Provider puede crear un nuevo sponsorship

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea como Provider
	// 2. Selecciona crear sponsorship en la pestaña de sponsorships
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede
	// crear una sponsorship
	@Test
	public void testCreateGood() {
		super.authenticate("provider1");
		final int idPosition = super.getEntityId("position2");
		final Position position = this.positionService.findOne(idPosition);
		this.providerService.findByPrincipal();
		final SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setBanner("https://herramientas-online.com/generador-tarjeta-credito-cvv.php");
		sponsorshipForm.setBrandName("BrandName");
		sponsorshipForm.setCvv(320);
		sponsorshipForm.setExpirationMonth(3);
		sponsorshipForm.setExpirationYear(23);
		sponsorshipForm.setHolderName("holderName");
		sponsorshipForm.setNumber("5390389640123794");
		sponsorshipForm.setPosition(position);
		sponsorshipForm.setTargetPage("https://herramientas-online.com/generador-tarjeta-credito-cvv.php");
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.recostruction(sponsorshipForm);
		this.sponsorshipService.save(sponsorship);

	}
	// Para el caso negativo estamos intentando que un actor logueado como Company cree un sponsorship.
	//  Esto debe provocar un fallo
	// en el sistema porque solo puede crear sponsorship un Provider.
	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea como Company
	// 2. Selecciona enviar notificacion en la pestaña de mensajes
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro sistema que no se puede enviar mas de una notificacion
	@Test(expected = IllegalArgumentException.class)
	public void testCreateError() {
		super.authenticate("company1");
		final int idPosition = super.getEntityId("position2");
		final Position position = this.positionService.findOne(idPosition);
		this.providerService.findByPrincipal();
		final SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setBanner("https://herramientas-online.com/generador-tarjeta-credito-cvv.php");
		sponsorshipForm.setBrandName("BrandName");
		sponsorshipForm.setCvv(320);
		sponsorshipForm.setExpirationMonth(3);
		sponsorshipForm.setExpirationYear(23);
		sponsorshipForm.setHolderName("holderName");
		sponsorshipForm.setNumber("5390389640123794");
		sponsorshipForm.setPosition(position);
		sponsorshipForm.setTargetPage("https://herramientas-online.com/generador-tarjeta-credito-cvv.php");
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.recostruction(sponsorshipForm);
		this.sponsorshipService.save(sponsorship);

	}
	//Este test testea el requisito 13.1 Un Actor autenticado como Provider puede crear un nuevo sponsorship

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea como Provider
	// 2. Selecciona edita una sponsorship en la pestaña de sponsorships
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede
	// crear una sponsorship
	@Test
	public void testEditGood() {
		super.authenticate("provider1");
		final int sponsorshipId = super.getEntityId("sponsorship1");
		this.providerService.findByPrincipal();
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		sponsorship.setBanner("https://herramientas-online.com/generador-tarjeta-credito-cvv.php");
		final Sponsorship sponsorshipFinal = this.sponsorshipService.recostructionEdit(sponsorship, null);
		this.sponsorshipService.save(sponsorshipFinal);
	}
	@Test(expected = NullPointerException.class)
	public void testEditError() {
		super.authenticate("provider1");
		final int sponsorshipId = super.getEntityId("sponsorship1");
		this.providerService.findByPrincipal();
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		sponsorship.setBanner("hola");
		final Sponsorship sponsorshipFinal = this.sponsorshipService.recostructionEdit(sponsorship, null);
		this.sponsorshipService.save(sponsorshipFinal);
	}
	//Este test testea el requisito 13.1 Un Actor autenticado como Provider puede borrar un sponsorship

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El cliente se loguea como Provider
	// 2. Selecciona borrar una sponsorship en la pestaña de sponsorships
	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un usuario puede
	// crear una sponsorship
	@Test
	public void testDeleteGood() {
		super.authenticate("provider1");
		final int sponsorshipId = super.getEntityId("sponsorship1");
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		this.sponsorshipService.delete(sponsorship);

	}
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteError() {
		super.authenticate("provider1");
		final int sponsorshipId = super.getEntityId("sponsorship3");
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		this.sponsorshipService.delete(sponsorship);

	}
}
