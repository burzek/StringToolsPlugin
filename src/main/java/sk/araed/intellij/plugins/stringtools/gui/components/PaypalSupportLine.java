package sk.araed.intellij.plugins.stringtools.gui.components;

import com.intellij.ui.JBColor;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import sk.araed.intellij.plugins.stringtools.gui.actions.UpdateStatusListener;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;
import sk.araed.intellij.plugins.stringtools.gui.i18n.Resources;

/**
 * @author boris.brinza
 */
public class PaypalSupportLine extends JPanel {

	private JLabel paypalLink;
	private final Resources resources = new Resources();

	public PaypalSupportLine() {
		initializeGUI();

	}

	private void initializeGUI() {
		setBorder(new LineBorder(JBColor.LIGHT_GRAY));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		paypalLink = new JLabel("Support plugin and make author happy");
		paypalLink.setHorizontalAlignment(SwingConstants.RIGHT);
		paypalLink.setOpaque(false);
		paypalLink.setBorder(new LineBorder(JBColor.BLACK));
		add(paypalLink);
	}


}
