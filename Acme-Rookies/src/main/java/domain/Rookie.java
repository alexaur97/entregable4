
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Rookie extends Actor {

	private Collection<Curriculum>	curriculums;


	@JoinColumn
	@OneToMany
	public Collection<Curriculum> getCurriculums() {
		return this.curriculums;
	}

	public void setCurriculums(final Collection<Curriculum> curriculums) {
		this.curriculums = curriculums;
	}

}
