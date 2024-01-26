package sk.araed.intellij.plugins.stringtools.conversion.converters;

import java.nio.charset.Charset;
import java.util.zip.CRC32;

import sk.araed.intellij.plugins.stringtools.conversion.ConversionResult;
import sk.araed.intellij.plugins.stringtools.conversion.Converter;

/**
 * @author boris.brinza 10-Oct-2017.
 */
public class CRC32CheckSum implements Converter {
	@Override
	public ConversionResult convert(String input) {
		if (input == null || input.isEmpty()) {
			return new ConversionResult().withResult("");
		}

		CRC32 crc32 = new CRC32();
		crc32.update(input.getBytes(Charset.defaultCharset()));
		return new ConversionResult().withResult(String.valueOf(crc32.getValue()));
	}
}
