package sk.araed.intellij.plugins.stringtools.gui.actions;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import sk.araed.intellij.plugins.stringtools.data.ConversionData;
import sk.araed.intellij.plugins.stringtools.data.DataProvider;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 14-Apr-2017.
 */
public class ReplaceInEditorAction extends AbstractAction {

	private final DataProvider dataProvider;
	private final UpdateStatusListener updateStatusListener;

	public ReplaceInEditorAction(
			final DataProvider dataProvider,
			final UpdateStatusListener updateStatusListener) {
		this.dataProvider = dataProvider;
		this.updateStatusListener = updateStatusListener;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final Editor editor = dataProvider.getConversionData().getCurrentEditor();
		final ConversionData transformationData = dataProvider.getConversionData();
		final String selectedText = editor.getSelectionModel().getSelectedText();
		if (selectedText != null && !selectedText.isEmpty()) {
			final int startPosition = editor.getSelectionModel().getSelectionStart();
			final int endPosition = editor.getSelectionModel().getSelectionEnd();
			WriteCommandAction.runWriteCommandAction(editor.getProject(),
					() -> editor.getDocument().replaceString(startPosition, endPosition, transformationData.getConvertedText()));
			updateStatusListener.updateStatus(ResourceKey.REPLACE_DONE_STATUS);
		} else {
			updateStatusListener.updateStatus(ResourceKey.NO_SELECTION_STATUS);
		}
	}
}
