package sk.araed.intellij.plugins.stringtools.conversion.converters;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import sk.araed.intellij.plugins.stringtools.conversion.ConversionResult;
import sk.araed.intellij.plugins.stringtools.conversion.Converter;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 10-Oct-2017.
 */
public abstract class HashConverter implements Converter {
	protected enum HASH_TYPE {
		MD5("MD5"), SHA_256("SHA-256"), SHA_512("SHA-512");

		private final String algName;

		HASH_TYPE(String algName) {
			this.algName = algName;
		}
		private String getAlgorithmName() {
			return algName;
		}
	}
	private final Map<HASH_TYPE, MessageDigest> digestMap = new HashMap<>();

	@Override
	public final ConversionResult convert(String input) {
		if (input == null || input.isEmpty()) {
			return new ConversionResult().withResult("");
		}
		try {
			MessageDigest messageDigest = getMessageDigestInstance();
			byte[] dig = messageDigest.digest(input.getBytes(Charset.defaultCharset()));
			StringBuilder sb = new StringBuilder(dig.length * 2);
			for (byte b: dig) {
				sb.append(String.format("%02x", b));
			}
			return new ConversionResult().withResult(sb.toString());
		} catch (NoSuchAlgorithmException e) {
			return new ConversionResult().withResult("???").withError(ResourceKey.ERR_INTERNAL.withParam("stacktrace", e.toString()));
		}

	}

	private synchronized MessageDigest getMessageDigestInstance() throws NoSuchAlgorithmException {
		for (final Provider provider : Security.getProviders()) {
			System.out.println("" + provider.getName());
			for (final Service service : provider.getServices()) {
				System.out.println("     " + service.getAlgorithm());
			}
		}

		MessageDigest messageDigest = digestMap.get(getHashType());
		if (messageDigest == null) {
			messageDigest = MessageDigest.getInstance(getHashType().getAlgorithmName());
			digestMap.put(getHashType(), messageDigest);
		}
		return messageDigest;

	}

	protected abstract HASH_TYPE getHashType();
}
