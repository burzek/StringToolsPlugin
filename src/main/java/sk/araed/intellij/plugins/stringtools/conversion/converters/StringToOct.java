package sk.araed.intellij.plugins.stringtools.conversion.converters;

import java.nio.charset.Charset;

import java.nio.charset.StandardCharsets;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionResult;
import sk.araed.intellij.plugins.stringtools.conversion.Converter;

/**
 * @author boris.brinza 12-Apr-2017.
 */
public class StringToOct implements Converter {

	private static final char[] OCT_CHARS = "012345678".toCharArray();

	@Override
	public ConversionResult convert(String input) {
		return new ConversionResult().withResult(asOct(input.getBytes(StandardCharsets.US_ASCII)));
	}

	private String asOct(byte[] buf) {

		char[] chars = new char[3 * buf.length];
		for (int i = 0; i < buf.length; i++) {
			chars[3 * i] = OCT_CHARS[(buf[i] & 192) >>> 6];
			chars[3 * i + 1] = OCT_CHARS[(buf[i] & 56) >>> 3];
			chars[3 * i + 2] = OCT_CHARS[buf[i] & 7];
		}
		return new String(chars);
	}

}

