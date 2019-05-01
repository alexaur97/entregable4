
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	private Collection<Item>	items;


	@ManyToMany
	public Collection<Item> getItems() {
		return this.items;
	}

	public void setItems(final Collection<Item> items) {
		this.items = items;
	}

}
