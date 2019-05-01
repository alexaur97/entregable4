
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "personal_data")
})
public class Curriculum extends DomainEntity {

	private Boolean							copy;
	private String							idName;

	private PersonalData					personalData;
	private Collection<EducationData>		educationData;
	private Collection<MiscellaniusData>	miscellaniusData;
	private Collection<PositionData>		positionData;


	@NotNull
	public Boolean getCopy() {
		return this.copy;
	}

	public void setCopy(final Boolean copy) {
		this.copy = copy;
	}
	@NotBlank
	public String getIdName() {
		return this.idName;
	}

	public void setIdName(final String idName) {
		this.idName = idName;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	public void setEducationData(final Collection<EducationData> educationData) {
		this.educationData = educationData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaniusData> getMiscellaniusData() {
		return this.miscellaniusData;
	}

	public void setMiscellaniusData(final Collection<MiscellaniusData> miscellaniusData) {
		this.miscellaniusData = miscellaniusData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	public void setPositionData(final Collection<PositionData> positionData) {
		this.positionData = positionData;
	}

}
