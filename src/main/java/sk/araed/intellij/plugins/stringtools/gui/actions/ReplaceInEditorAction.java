package sk.araed.intellij.plugins.stringtools.gui.actions;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiDocumentManager;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.swing.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;

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
		Project project = null;
    try {
			PsiDocumentManager.getInstance(ProjectManager.getInstance().))
			final DataContext dataContext = DataManager.getInstance().getDataContextFromFocusAsync().blockingGet(1000);
			Object x = dataContext.getData(CommonDataKeys.EDITOR);

		} catch (TimeoutException ex) {
      throw new RuntimeException(ex);
    } catch (ExecutionException ex) {
      throw new RuntimeException(ex);
    }

    FileEditor fileEditor = FileEditorManager
				.getInstance(ProjectManager.getInstance().getDefaultProject())
				.getSelectedEditor();
		ConversionData transformationData = dataProvider.getConversionData();
//		Editor editor = transformationData.getOpenedEditor();
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
