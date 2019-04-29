
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CurriculumCreateForm {

	private String	idName;
	private String	fullname;
	private String	github;
	private String	linkedin;
	private String	phone;
	private String	statement;


	@NotBlank
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	@NotBlank
	@URL
	public String getGithub() {
		return this.github;
	}

	public void setGithub(final String github) {
		this.github = github;
	}

	@URL
	@NotBlank
	public String getLinkedin() {
		return this.linkedin;
	}

	public void setLinkedin(final String linkedin) {
		this.linkedin = linkedin;
	}

	@NotBlank
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotBlank
	public String getIdName() {
		return this.idName;
	}

	public void setIdName(final String idName) {
		this.idName = idName;
	}

}
