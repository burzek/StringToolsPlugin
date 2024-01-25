package sk.araed.intellij.plugins.stringtools.conversion.converters;

import sk.araed.intellij.plugins.stringtools.conversion.ConversionResult;
import sk.araed.intellij.plugins.stringtools.conversion.Converter;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 10-Oct-2017.
 */
public class LuhnDigitAppender implements Converter {
	@Override
	public ConversionResult convert(String input) {
		ConversionResult result = new ConversionResult();
		if (!input.isEmpty()) {
			if (!isValid(input)) {
				result = result.withResult("???").withError(ResourceKey.ERR_INVALID_INPUT);
			} else {
				result.withResult(compute(input));
			}
		}

		return result;
	}

	private String compute(String input) {
		int counter = 0;
		long sum = 0;
		for (int i = input.length() - 1; i >= 0; i--) {
			int val = input.charAt(i) - '0';
			if (counter++ % 2 == 0) {
				val *= 2;
			}
			if (val >= 10) {
				val -= 9;
			}
			sum += val;
		}
		sum *= 9;
		return input + ((char) ((sum % 10) + '0'));
	}

	private boolean isValid(String input) {
		//only numbers are allowed
		return input.chars().allMatch(Character::isDigit);
	}

}
