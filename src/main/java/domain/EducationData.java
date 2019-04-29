
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationData extends DomainEntity {

	private String	degree;
	private String	institution;
	private String	mark;
	private Date	endDate;
	private Date	startDate;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDegree() {
		return this.degree;
	}
	public void setDegree(final String degree) {
		this.degree = degree;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getInstitution() {
		return this.institution;
	}
	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@Pattern(regexp = "[0-9]{1}||10")
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMark() {
		return this.mark;
	}
	public void setMark(final String mark) {
		this.mark = mark;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

}
