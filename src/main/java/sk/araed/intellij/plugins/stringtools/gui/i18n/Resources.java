package sk.araed.intellij.plugins.stringtools.gui.i18n;

import java.io.IOException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.diagnostic.Logger;



/**
 * @author boris.brinza 12-Apr-2017.
 */
public class Resources {
	private static final String RESOURCE_PATH = "/ui.properties";
	private static final Logger logger = Logger.getInstance(Resources.class);


	private ResourceBundle resourceBundle;


	public Resources() {
		loadResource();
	}

	private void loadResource() {
		try {
			resourceBundle = new PropertyResourceBundle(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCE_PATH)));
		} catch (IOException e) {
			logger.error("Cannot load resource " + RESOURCE_PATH);
			throw new RuntimeException("Cannot load resource " + RESOURCE_PATH, e);
		}
	}

	public String getText(ResourceKey resourceKey) {
		String text;
		if (resourceBundle.containsKey(resourceKey.getResourceKey())) {
			text =  resourceBundle.getString(resourceKey.getResourceKey());
			for (String param : resourceKey.getParameterNames()) {
				text = text.replaceAll("\\$" + param, resourceKey.getParameterValueFor(param));
			}
		} else {
			logger.error("Cannot find resource with key:" + resourceKey.getResourceKey());
			text = "???" + resourceKey.getResourceKey() + "???";
		}
		return text;
	}

	public Icon getIcon(ResourceKey resourceKey) {
		Icon ret = null;
		String key = resourceKey.getResourceKey() + ".icon";
		if (resourceBundle.containsKey(key)) {
			String iconPath = resourceBundle.getString(key);
			ret = IconLoader.getIcon(iconPath, getClass());
		}
		return ret;
	}

	public Character getMnemonic(ResourceKey resourceKey) {
		Character ret = null;
		String key = resourceKey.getResourceKey() + ".mnemonic";
		if (resourceBundle.containsKey(key)) {
			String mnemonicStr = resourceBundle.getString(key);
			if (!mnemonicStr.isEmpty()) {
				ret = mnemonicStr.charAt(0);
			}
		}

		return ret;
	}
}
