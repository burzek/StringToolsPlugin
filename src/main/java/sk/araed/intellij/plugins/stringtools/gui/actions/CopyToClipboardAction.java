package sk.araed.intellij.plugins.stringtools.gui.actions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import javax.swing.*;

import sk.araed.intellij.plugins.stringtools.data.DataProvider;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 14-Apr-2017.
 */
public class CopyToClipboardAction extends AbstractAction {

	private final DataProvider dataProvider;
	private final UpdateStatusListener updateStatusListener;

	public CopyToClipboardAction(DataProvider dataProvider, UpdateStatusListener updateStatusListener) {
		this.dataProvider = dataProvider;
		this.updateStatusListener = updateStatusListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringSelection stringSelection = new StringSelection(dataProvider.getConversionData().getConvertedText());
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
		updateStatusListener.updateStatus(ResourceKey.COPIED_TO_CLIPBOARD_STATUS);
	}
}
