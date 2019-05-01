
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaniusDataRepository;
import domain.MiscellaniusData;

@Component
@Transactional
public class StringToMiscellaniusDataConverter implements Converter<String, MiscellaniusData> {

	@Autowired
	MiscellaniusDataRepository	miscellaniusDataRepository;


	@Override
	public MiscellaniusData convert(final String text) {
		MiscellaniusData result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.miscellaniusDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
