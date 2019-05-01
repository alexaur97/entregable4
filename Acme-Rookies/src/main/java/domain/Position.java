
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "company, cancelled, mode, deadline")
})
public class Position extends DomainEntity {

	private String				title;
	private String				description;
	private Date				deadline;
	private String				profileRequired;
	private String				skillRequired;
	private String				techRequired;
	private Integer				salaryOffered;
	private String				ticker;
	private String				mode;
	private Boolean				cancelled;

	private Company				company;
	private Collection<Problem>	problems;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}
	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getProfileRequired() {
		return this.profileRequired;
	}
	public void setProfileRequired(final String profileRequired) {
		this.profileRequired = profileRequired;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSkillRequired() {
		return this.skillRequired;
	}
	public void setSkillRequired(final String skillRequired) {
		this.skillRequired = skillRequired;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTechRequired() {
		return this.techRequired;
	}
	public void setTechRequired(final String techRequired) {
		this.techRequired = techRequired;
	}

	@Min(0)
	@NotNull
	public Integer getSalaryOffered() {
		return this.salaryOffered;
	}
	public void setSalaryOffered(final Integer salaryOffered) {
		this.salaryOffered = salaryOffered;
	}

	@NotBlank
	@Pattern(regexp = "[a-zA-Z]{4}-[0-9]{4}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	@Pattern(regexp = "^DRAFT|FINAL$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMode() {
		return this.mode;
	}
	public void setMode(final String mode) {
		this.mode = mode;
	}

	@NotNull
	public Boolean getCancelled() {
		return this.cancelled;
	}
	public void setCancelled(final Boolean cancelled) {
		this.cancelled = cancelled;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}
	public void setCompany(final Company company) {
		this.company = company;
	}

	//	@NotNull
	@ManyToMany
	public Collection<Problem> getProblems() {
		return this.problems;
	}
	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
	}

}
