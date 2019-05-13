
package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ItemService;
import utilities.AbstractTest;
import domain.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ItemServiceTest extends AbstractTest {

	@Autowired
	private ItemService	itemService;


	//Este test testea el requisito 10.1 Un Actor autenticado como Provider debe
	// poder editar uno de sus items

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El proveedor se loguea
	// 2. Se va al desplegable de Mis Artefactos y selecciona "Detalles" del artefacto que quiere editar
	// 3. El proveedor edita los datos que desee y guarda los cambios.

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un proveedor puede editar sus artefactos

	@Test
	public void TestEditItemGood() {
		super.authenticate("provider1");
		final int id = super.getEntityId("item1");
		Item i = this.itemService.findOne(id);
		i.setDescription("Cambiada");
		i = this.itemService.reconstruct(i, null);
		this.itemService.save(i);
		super.unauthenticate();

	}

	//Este test testea el requisito 10.2 Un Actor autenticado como Provider no debe
	// poder editar los artefactos de otro proveedor

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El proveedor se loguea
	// 2. Se va a los detalles de un artefacto para editarlo
	// 3. Introduce por url una id de un artefacto que no le pertenece para intentar editarlo,
	// cambia los datos e intenta guardarlo.

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un proveedor no puede
	// editar un artefacto que no le pertenece

	@Test(expected = IllegalArgumentException.class)
	public void testEditItemError() {
		super.authenticate("provider1");
		final int id = super.getEntityId("item2");
		Item i = this.itemService.findOne(id);
		i.setDescription("Cambiada");
		i = this.itemService.reconstruct(i, null);
		this.itemService.save(i);
		super.unauthenticate();
	}

	//Este test testea el requisito 10.2 Un Actor autenticado como Provider debe
	// poder eliminar sus items.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El proveedor se loguea
	// 2. El proveedor elije un artefacto para visualizarlo o editarlo
	// 3. El proveedor decide eliminar ese artefacto

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un proveedor puede
	// eliminar un artefacto suyo

	@Test
	public void testDeleteItem() {
		super.authenticate("provider1");
		final int id = super.getEntityId("item1");
		final Item i = this.itemService.findOne(id);
		this.itemService.delete(i);
		super.unauthenticate();
	}

	//Este test testea el requisito 9.2 Un Actor autenticado como Provider no debe
	// poder eliminar un artefacto que no es suyo.

	// Análisis del sentence coverage (Pasos que sigue el test en nuestro código): 
	// 1. El proveedor se loguea
	// 2. El proveedor visualiza un artefacto que no le pertenece
	// 3. El proveedor intenta eliminar ese artefacto pero no puede hacerlo

	// Análisis del data coverage (¿Que y como estamos verificando en nuestro modelo de datos?):

	// Estamos verificando en nuestro modelo de datos que un proveedor no puede
	// eliminar un artefacto que no es suyo

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteItemError() {
		super.authenticate("provider1");
		final int id = super.getEntityId("item2");
		final Item i = this.itemService.findOne(id);
		this.itemService.delete(i);
		super.unauthenticate();
	}

}
