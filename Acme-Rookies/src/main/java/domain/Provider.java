
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	private Collection<Item>	items;


	@OneToMany(mappedBy = "provider")
	public Collection<Item> getItems() {
		return this.items;
	}

	public void setItems(final Collection<Item> items) {
		this.items = items;
	}

}
