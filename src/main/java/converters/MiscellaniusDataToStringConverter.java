
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaniusData;

@Component
@Transactional
public class MiscellaniusDataToStringConverter implements Converter<MiscellaniusData, String> {

	@Override
	public String convert(final MiscellaniusData miscellaniusData) {
		String result;
		if (miscellaniusData == null)
			result = null;
		else
			result = String.valueOf(miscellaniusData.getId());
		return result;
	}

}
