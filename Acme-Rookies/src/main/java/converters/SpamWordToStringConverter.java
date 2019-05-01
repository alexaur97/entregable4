
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SpamWord;

@Component
@Transactional
public class SpamWordToStringConverter implements Converter<SpamWord, String> {

	@Override
	public String convert(final SpamWord SpamWord) {
		String result;

		if (SpamWord == null)
			result = null;

		else
			result = String.valueOf(SpamWord.getId());

		return result;
	}

}
