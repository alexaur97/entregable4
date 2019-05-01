
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

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "rookie")
})
public class Finder extends DomainEntity {

	private String				keyword;
	private Date				deadline;
	private Integer				minSalary;
	private Integer				maxSalary;

	private Rookie				rookie;

	public Date					lastSearch;
	public Collection<Position>	positions;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getDeadline() {
		return this.deadline;
	}
	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@Min(0)
	public Integer getMinSalary() {
		return this.minSalary;
	}
	public void setMinSalary(final Integer minSalary) {
		this.minSalary = minSalary;
	}

	@Min(0)
	public Integer getMaxSalary() {
		return this.maxSalary;
	}
	public void setMaxSalary(final Integer maxSalary) {
		this.maxSalary = maxSalary;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}
	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastSearch() {
		return this.lastSearch;
	}
	public void setLastSearch(final Date lastSearch) {
		this.lastSearch = lastSearch;
	}

	@NotNull
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}
	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}
