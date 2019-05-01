package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	public Collection<Position>	positions;
	
	@NotNull
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}
	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}
