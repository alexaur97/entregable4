
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Item extends DomainEntity {

	private String				name;
	private String				description;
	private Collection<String>	links;
	private Collection<String>	photos;

	private Provider			provider;


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
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getLinks() {
		return this.links;
	}
	public void setLinks(final Collection<String> links) {
		this.links = links;
	}

	@ElementCollection
	public Collection<String> getPhotos() {
		return this.photos;
	}
	public void setPhotos(final Collection<String> photos) {
		this.photos = photos;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}
	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

}
