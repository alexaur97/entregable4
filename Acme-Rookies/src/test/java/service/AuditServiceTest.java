
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


	//Requisito 3.2 Un Actor autenticado como Auditor puede administrar sus auditorias.

	@Test
	public void testEditAuditGood() {
		super.authenticate("auditor1");
		final int IdAudit = super.getEntityId("audit1");
		Audit audit = this.auditService.findOne(IdAudit);
		audit.setText("text");
		audit.setScore(2);
		audit.setMode("FINAL");

		audit = this.auditService.reconstruct(audit, null);

		this.auditService.save(audit);
		super.unauthenticate();
	}
	//	Para el caso negativo estamos intentando que un Auditor se asigne una posicion en modo borrador
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "reconstruct" comprueba
	// que la position seleccionada cumpla todas las condiciones.

	@Test
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

}
