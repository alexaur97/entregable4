
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SpamWordRepository;
import domain.SpamWord;

@Component
@Transactional
public class StringToSpamWordConverter implements Converter<String, SpamWord> {

	@Autowired
	SpamWordRepository	SpamWordRepository;


	@Override
	public SpamWord convert(final String text) {
		SpamWord result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.SpamWordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
