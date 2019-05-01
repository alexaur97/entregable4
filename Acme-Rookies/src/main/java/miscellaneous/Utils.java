
package miscellaneous;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;

import domain.CreditCard;

public class Utils {

	public static Boolean creditCardIsExpired(final CreditCard creditCard) {
		final LocalDate date = LocalDate.now();
		final Integer month = date.getMonthOfYear();
		final Integer year = date.getYear();
		Boolean res = false;
		if ((creditCard.getExpirationYear() + 2000) < year)
			res = true;
		else if ((creditCard.getExpirationYear() + 2000) == year && creditCard.getExpirationMonth() < month)
			res = true;
		return res;
	}

	public static Boolean creditCardIsExpired(final Integer month, final Integer year) {
		final LocalDate date = LocalDate.now();
		final Integer m = date.getMonthOfYear();
		final Integer y = date.getYear();
		Boolean res = false;
		if ((year + 2000) < y)
			res = true;
		else if ((year + 2000) == y && month < m)
			res = true;
		return res;
	}

	public static Boolean validateURL(final Collection<String> collection) {
		final String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=_|!:,.;]*[-a-zA-Z0-9+&@#/%=_|]";
		final Pattern patt = Pattern.compile(regex);
		Boolean b = true;

		if (!collection.isEmpty())
			for (final String s : collection) {
				final Matcher matcher = patt.matcher(s);
				if (!matcher.matches()) {
					b = false;
					break;
				}
			}
		return b;
	}

}
