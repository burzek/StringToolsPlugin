package sk.araed.intellij.plugins.stringtools.conversion;

import sk.araed.intellij.plugins.stringtools.conversion.converters.Base64Decode;
import sk.araed.intellij.plugins.stringtools.conversion.converters.Base64Encode;
import sk.araed.intellij.plugins.stringtools.conversion.converters.Base64MimeDecode;
import sk.araed.intellij.plugins.stringtools.conversion.converters.Base64MimeEncode;
import sk.araed.intellij.plugins.stringtools.conversion.converters.BinaryToString;
import sk.araed.intellij.plugins.stringtools.conversion.converters.CRC16CheckSum;
import sk.araed.intellij.plugins.stringtools.conversion.converters.CRC32CheckSum;
import sk.araed.intellij.plugins.stringtools.conversion.converters.HashConverter;
import sk.araed.intellij.plugins.stringtools.conversion.converters.HashConverter.HASH_TYPE;
import sk.araed.intellij.plugins.stringtools.conversion.converters.HexToString;
import sk.araed.intellij.plugins.stringtools.conversion.converters.HtmlDecode;
import sk.araed.intellij.plugins.stringtools.conversion.converters.HtmlEncode;
import sk.araed.intellij.plugins.stringtools.conversion.converters.JwtDecoder;
import sk.araed.intellij.plugins.stringtools.conversion.converters.LuhnDigitAppender;
import sk.araed.intellij.plugins.stringtools.conversion.converters.OctToString;
import sk.araed.intellij.plugins.stringtools.conversion.converters.ROT13String;
import sk.araed.intellij.plugins.stringtools.conversion.converters.StringToBinary;
import sk.araed.intellij.plugins.stringtools.conversion.converters.StringToHex;
import sk.araed.intellij.plugins.stringtools.conversion.converters.StringToOct;
import sk.araed.intellij.plugins.stringtools.conversion.converters.UrlDecode;
import sk.araed.intellij.plugins.stringtools.conversion.converters.UrlEncode;
import sk.araed.intellij.plugins.stringtools.data.Operation;

/**
 * @author boris.brinza 13-Apr-2017.
 */
public class ConversionFactory {

	public Converter getConverter(Operation transformation) {
		switch (transformation) {
			case STRING_TO_BIN:
				return new StringToBinary();
			case BIN_TO_STRING:
				return new BinaryToString();
			case STRING_TO_HEX:
				return new StringToHex();
			case HEX_TO_STRING:
				return new HexToString();
			case STRING_TO_OCT:
				return new StringToOct();
			case OCT_TO_STRING:
				return new OctToString();
			case BASE_64_DECODE:
				return new Base64Decode();
			case BASE_64_ENCODE:
				return new Base64Encode();
			case URL_DECODE:
				return new UrlDecode();
			case URL_ENCODE:
				return new UrlEncode();
			case HTML_ENCODE:
				return new HtmlEncode();
			case HTML_DECODE:
				return new HtmlDecode();
			case ROT13:
				return new ROT13String();
			case MD2_HASH:
				return new HashConverter(HASH_TYPE.MD2);
			case MD4_HASH:
				return new HashConverter(HASH_TYPE.MD4);
			case MD5_HASH:
				return new HashConverter(HASH_TYPE.MD5);
			case SHA256_HASH:
				return new HashConverter(HASH_TYPE.SHA_256);
			case SHA384_HASH:
				return new HashConverter(HASH_TYPE.SHA_384);
			case SHA512_HASH:
				return new HashConverter(HASH_TYPE.SHA_512);
			case SHA3_256_HASH:
				return new HashConverter(HASH_TYPE.SHA3_256);
			case SHA3_384_HASH:
				return new HashConverter(HASH_TYPE.SHA3_384);
			case SHA3_512_HASH:
				return new HashConverter(HASH_TYPE.SHA3_512);
			case KECCAK256_HASH:
				return new HashConverter(HASH_TYPE.KECCAK_256);
			case KECCAK384_HASH:
				return new HashConverter(HASH_TYPE.KECCAK_384);
			case KECCAK512_HASH:
				return new HashConverter(HASH_TYPE.KECCAK_512);
			case CRC16:
				return new CRC16CheckSum();
			case CRC32:
				return new CRC32CheckSum();
			case LUHN_DIGIT_GENERATOR:
				return new LuhnDigitAppender();
			case JWT_DECODE:
				return new JwtDecoder();
			case BASE_64_MIME_DECODE:
				return new Base64MimeDecode();
			case BASE_64_MIME_ENCODE:
				return new Base64MimeEncode();
			default:
				throw new IllegalStateException("Invalid transformation:" + transformation);

		}
	}
}
