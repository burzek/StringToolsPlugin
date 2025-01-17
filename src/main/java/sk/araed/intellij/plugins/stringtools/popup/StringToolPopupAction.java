package sk.araed.intellij.plugins.stringtools.popup;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionFactory;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionProcessor;
import sk.araed.intellij.plugins.stringtools.conversion.converters.JwtDecoder;
import sk.araed.intellij.plugins.stringtools.data.ConversionData;
import sk.araed.intellij.plugins.stringtools.data.DataProvider;
import sk.araed.intellij.plugins.stringtools.data.Operation;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

public class StringToolPopupAction extends AnAction  {

  static class PopupActionData implements DataProvider {

    private final String actionId;
    private final String selectedText;

    public PopupActionData(String actionId, String selectedText) {
      this.actionId = actionId;
      this.selectedText = selectedText;
    }

    @Override
    public ConversionData getConversionData() {
      return new ConversionData(selectedText, "", Operation.valueOf(actionId));
    }
  }

  @Override
  public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
    if (anActionEvent.getProject() == null) {
      return;
    }

    final FileEditor fileEditor = FileEditorManager.getInstance(anActionEvent.getProject()).getSelectedEditor();
    if (fileEditor instanceof TextEditor) {
      Editor editor = ((TextEditor) fileEditor).getEditor();
      String selectedText = editor.getSelectionModel().getSelectedText();
      if (selectedText != null && !selectedText.isEmpty()) {

        int startPosition = editor.getSelectionModel().getSelectionStart();
        int endPosition = editor.getSelectionModel().getSelectionEnd();

        ActionManager actionManager = ActionManager.getInstance();
        String actionId = actionManager.getId(this);
        PopupActionData data = new PopupActionData(actionId, selectedText);

        ConversionProcessor processor = new ConversionProcessor(data);
        final ConversionData conversionData = processor.doConversion();
        if (conversionData.isInvalidInput()) {
          return;
        }
        WriteCommandAction.runWriteCommandAction(editor.getProject(),
            () -> editor.getDocument().replaceString(startPosition, endPosition, conversionData.getConvertedText()));
      }
    }
  }
}
