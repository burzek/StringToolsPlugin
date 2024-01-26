package sk.araed.intellij.plugins.stringtools.conversion;

import java.awt.Component;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ExtendedHashOperationProvider {


  public String[] getSupportedHashes() {
    BouncyCastleProvider provider = new BouncyCastleProvider();
      return new String[] {"SHA3-256", "SHA3-384", "SHA3-512", "KECCAK-256", "KECCAK-384", "KECCAK-512"};
  }
}
