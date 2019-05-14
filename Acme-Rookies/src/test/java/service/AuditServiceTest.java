
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AuditService;
import utilities.AbstractTest;
import domain.Audit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	@Autowired
	private AuditService	auditService;


	//Requisito 3.2 Un Actor autenticado como Auditor puede editar sus auditorias.

	@Test
	public void testEditAuditGood() {
		super.authenticate("auditor1");
		final int IdAudit = super.getEntityId("audit1");
		Audit audit = this.auditService.findOne(IdAudit);
		System.out.println(audit.getMode());
		audit.setText("text");
		audit.setScore(2);
		audit.setMode("FINAL");

		System.out.println(audit.getMode());
		audit = this.auditService.reconstruct(audit, null);

		this.auditService.save(audit);
		super.unauthenticate();
	}
	//	Para el caso negativo estamos intentando que un Auditor edite una auditoria en modo borrador
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "save" comprueba
	// que la auditoria anteriormente estuviera en modo borrador.

	@Test(expected = NullPointerException.class)
	public void testEditAuditError() {
		super.authenticate("auditor1");
		final int IdAudit = super.getEntityId("audit2");
		Audit audit = this.auditService.findOne(IdAudit);
		audit.setText("text");
		audit.setScore(2);
		audit.setMode("FINAL");

		audit = this.auditService.reconstruct(audit, null);

		this.auditService.save(audit);
		super.unauthenticate();
	}

	//Requisito 3.2 Un Actor autenticado como Auditor puede borrar sus auditorias.

	@Test
	public void testDeleteAuditGood() {
		super.authenticate("auditor1");
		final int IdAudit = super.getEntityId("audit1");
		final Audit audit = this.auditService.findOne(IdAudit);

		this.auditService.delete(audit);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Auditor elimine una auditoria en modo borrador
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "save" comprueba
	// que la auditoria anteriormente estuviera en modo borrador.

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteAuditError() {
		super.authenticate("auditor1");
		final int IdAudit = super.getEntityId("audit2");
		final Audit audit = this.auditService.findOne(IdAudit);

		this.auditService.delete(audit);
		super.unauthenticate();
	}

}
