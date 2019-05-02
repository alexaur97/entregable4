
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private CreditCard	creditCard;
	private String		banner;
	private String		targetPage;
	private Provider	provider;
	private Position	position;


	@Valid
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTargetPage() {
		return this.targetPage;
	}
	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}
	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}
	public void setPosition(final Position position) {
		this.position = position;
	}

}
