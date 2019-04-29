
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	private String	fullname;
	private String	statement;
	private String	phone;
	private String	github;
	private String	linkedin;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getFullname() {
		return this.fullname;
	}
	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getGithub() {
		return this.github;
	}

	public void setGithub(final String github) {
		this.github = github;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLinkedin() {
		return this.linkedin;
	}
	public void setLinkedin(final String linkedin) {
		this.linkedin = linkedin;
	}

}
