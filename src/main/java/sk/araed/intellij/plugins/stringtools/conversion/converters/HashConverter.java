package sk.araed.intellij.plugins.stringtools.conversion.converters;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionResult;
import sk.araed.intellij.plugins.stringtools.conversion.Converter;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 10-Oct-2017.
 */
public class HashConverter implements Converter {

	private final HASH_TYPE hashType;

	public enum HASH_TYPE {
		MD2("MD2"), MD4("MD4"), MD5("MD5"),
		SHA_256("SHA-256"), SHA_384("SHA-384"), SHA_512("SHA-512"),
		SHA3_256("SHA3-256"), SHA3_384("SHA3-384"), SHA3_512("SHA3-512"),
		KECCAK_256("KECCAK-256"), KECCAK_384("KECCAK-384"), KECCAK_512("KECCAK-512");

		private final String algName;

		HASH_TYPE(String algName) {
			this.algName = algName;
		}
		private String getAlgorithmName() {
			return algName;
		}
	}
	private final Map<HASH_TYPE, MessageDigest> digestMap = new HashMap<>();


	public HashConverter(HASH_TYPE hashType) {
		this.hashType = hashType;
	}

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
		MessageDigest messageDigest = digestMap.get(hashType);
		Security.addProvider(new BouncyCastleProvider());
		if (messageDigest == null) {

			messageDigest = MessageDigest.getInstance(hashType.getAlgorithmName());
			digestMap.put(hashType, messageDigest);
		}
		return messageDigest;

	}

//	protected abstract HASH_TYPE getHashType();
}
