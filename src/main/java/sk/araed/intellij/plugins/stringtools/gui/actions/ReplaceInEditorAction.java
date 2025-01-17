package sk.araed.intellij.plugins.stringtools.gui.actions;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.ProjectManager;
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

	public ReplaceInEditorAction(DataProvider dataProvider, UpdateStatusListener updateStatusListener) {
		this.dataProvider = dataProvider;
		this.updateStatusListener = updateStatusListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		FileEditor fileEditor = FileEditorManager
				.getInstance(ProjectManager.getInstance().getDefaultProject())
				.getSelectedEditor();
		ConversionData transformationData = dataProvider.getConversionData();
		if (fileEditor instanceof TextEditor) {
			Editor editor = ((TextEditor) fileEditor).getEditor();
			String selectedText = editor.getSelectionModel().getSelectedText();
			if (selectedText != null && !selectedText.isEmpty()) {
				int startPosition = editor.getSelectionModel().getSelectionStart();
				int endPosition = editor.getSelectionModel().getSelectionEnd();
				WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> editor.getDocument().replaceString(startPosition, endPosition, transformationData.getConvertedText()));
				updateStatusListener.updateStatus(ResourceKey.REPLACE_DONE_STATUS);
			} else {
				updateStatusListener.updateStatus(ResourceKey.NO_SELECTION_STATUS);
			}
		}
	}
}
