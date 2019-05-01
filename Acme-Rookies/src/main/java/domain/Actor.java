
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"email"
	})
})
public class Actor extends DomainEntity {

	// Atributos Privados

	private String		name;
	private String		surnames;
	private String		VAT;
	private CreditCard	creditCard;
	private String		photo;
	private String		email;
	private String		phone;
	private String		address;
	private Boolean		spammer;
	private Boolean		banned;

	public UserAccount	userAccount;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]$")
	//Una letra mayúscula o número, siete números y una letra mayúscula o un número
	public String getVAT() {
		return this.VAT;
	}

	public void setVAT(final String vAT) {
		this.VAT = vAT;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@Column(unique = true)
	@Pattern(regexp = "([a-zA-Z0-9])+@([a-zA-Z0-9]+\\.[a-zA-Z0-9]+)*|[a-zA-Z0-9]+[ a-zA-Z0-9]*\\<([a-zA-Z0-9])+@([a-zA-Z0-9]+\\.[a-zA-Z0-9]+)*\\>")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	public Boolean getSpammer() {
		return this.spammer;
	}

	public void setSpammer(final Boolean spammer) {
		this.spammer = spammer;
	}

	@NotNull
	public Boolean getBanned() {
		return this.banned;
	}

	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
