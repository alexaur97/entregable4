
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "rookie")
})
public class Application extends DomainEntity {

	private Date		moment;
	private String		explanation;
	private String		codeLink;
	private Date		submitMoment;
	private String		status;

	private Rookie		rookie;
	private Problem		problem;
	private Position	position;
	private Curriculum	curriculum;


	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getExplanation() {
		return this.explanation;
	}
	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCodeLink() {
		return this.codeLink;
	}
	public void setCodeLink(final String codeLink) {
		this.codeLink = codeLink;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:ss HH:mm")
	public Date getSubmitMoment() {
		return this.submitMoment;
	}
	public void setSubmitMoment(final Date submitMoment) {
		this.submitMoment = submitMoment;
	}

	@Pattern(regexp = "^ACCEPTED|REJECTED|SUBMITTED|PENDING$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getStatus() {
		return this.status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}
	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Problem getProblem() {
		return this.problem;
	}
	public void setProblem(final Problem problem) {
		this.problem = problem;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}
	public void setPosition(final Position position) {
		this.position = position;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}
	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
