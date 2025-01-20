package sk.araed.intellij.plugins.stringtools.gui.components;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import sk.araed.intellij.plugins.stringtools.gui.actions.UpdateStatusListener;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;
import sk.araed.intellij.plugins.stringtools.gui.i18n.Resources;

/**
 * @author boris.brinza
 */
public class StatusLine extends JPanel implements UpdateStatusListener {

	private JLabel statusLabel;
	private final Resources resources = new Resources();

	public StatusLine() {
		initializeGUI();

	}

	private void initializeGUI() {
		setBorder(new LineBorder(JBColor.LIGHT_GRAY));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.85;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;


		statusLabel = new JLabel(resources.getText(ResourceKey.STATUS_DEFAULT_TEXT));
		statusLabel.setBackground(JBColor.red);
		statusLabel.setForeground(JBColor.gray);
		statusLabel.setOpaque(false);
		add(statusLabel, gbc);

		gbc.weightx = 0.15;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		JLabel paypalLbl = new JLabel(resources.getText(ResourceKey.PAYPAL_LINK), SwingConstants.RIGHT);
		paypalLbl.setForeground(Color.BLUE);
		paypalLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Font baseFont = paypalLbl.getFont();
		Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) baseFont.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font underlinedFont = new Font(attributes);
		paypalLbl.setFont(underlinedFont);
		paypalLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent event) {
				try {
					Desktop.getDesktop().browse(
							new URI(
									"https://www.paypal.com/donate/?business=E5TFUG7Z8E3GE&amount=5&no_recurring=0&item_name=StringTools+support&currency_code=EUR"));
				} catch (Exception e) {
					Logger.getInstance(StatusLine.class).error("Error opening paypal url:" + e.getMessage(), e);
				}
			}
		});
		add(paypalLbl, gbc);


	}

	@Override
	public void updateStatus(ResourceKey status) {
		statusLabel.setForeground(JBColor.gray);
		statusLabel.setOpaque(false);
		statusLabel.setText(resources.getText(status));
		Executors.newSingleThreadScheduledExecutor().schedule(() -> statusLabel.setText(resources.getText(ResourceKey.STATUS_DEFAULT_TEXT)), 1, TimeUnit.SECONDS);
	}

	@Override
	public void updateErrorStatus(ResourceKey status) {
		String text;
		boolean opaque;
		JBColor fgColor;

		if (status != null) {
			text = resources.getText(status);
			opaque = true;
			fgColor = JBColor.black;
		} else {
			text = resources.getText(ResourceKey.STATUS_DEFAULT_TEXT);
			opaque = false;
			fgColor = JBColor.gray;
		}
		statusLabel.setText(text);
		statusLabel.setForeground(fgColor);
		statusLabel.setOpaque(opaque);

	}

}
