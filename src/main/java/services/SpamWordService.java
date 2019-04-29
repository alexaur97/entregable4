
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpamWordRepository;
import domain.SpamWord;

@Service
@Transactional
public class SpamWordService {

	@Autowired
	private SpamWordRepository	spamWordRepository;


	public SpamWordService() {
		super();

	}

	public SpamWord create() {
		SpamWord result;

		result = new SpamWord();

		return result;
	}

	public Collection<SpamWord> findAll() {
		Collection<SpamWord> result;

		result = this.spamWordRepository.findAll();

		return result;
	}

	public SpamWord findOne(final int spamWordId) {
		SpamWord result;

		result = this.spamWordRepository.findOne(spamWordId);

		return result;
	}

	public void save(final SpamWord spamWord) {
		Assert.notNull(spamWord);

		this.spamWordRepository.save(spamWord);
	}

	public void delete(final SpamWord spamWord) {
		this.spamWordRepository.delete(spamWord);
	}

}
