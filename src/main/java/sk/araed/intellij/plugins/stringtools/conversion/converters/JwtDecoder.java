package sk.araed.intellij.plugins.stringtools.conversion.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellij.openapi.diagnostic.Logger;
import java.util.Base64;
import org.apache.commons.lang3.StringUtils;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionResult;
import sk.araed.intellij.plugins.stringtools.conversion.Converter;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 10-Oct-2017.
 */
public class JwtDecoder implements Converter {
	@Override
	public ConversionResult convert(String input) {
		if (StringUtils.isEmpty(input)) {
			return new ConversionResult().withResult("");
		}

		final String[] chunks = input.split("\\.");
		if (chunks.length < 2 || chunks.length > 3) {
			return new ConversionResult().withError(ResourceKey.JWT_FORMAT_ERROR);
		}

		try {
			Base64.Decoder decoder = Base64.getUrlDecoder();
			final String header = new String(decoder.decode(chunks[0]));
			final String payload = new String(decoder.decode(chunks[1]));

			final Gson gson = new GsonBuilder().setPrettyPrinting().create();
			final String headerPretty = gson.toJson(JsonParser.parseString(header));
			final String payloadPretty = gson.toJson(JsonParser.parseString(payload));
			return new ConversionResult().withResult(
					String.format("%s\n\n%s", headerPretty, payloadPretty));
		} catch (Exception e) {
			Logger.getInstance(JwtDecoder.class).error("Error decoding jwt token:" + e.getMessage(), e);
			return new ConversionResult().withError(ResourceKey.JWT_FORMAT_ERROR);

		}
	}



}
