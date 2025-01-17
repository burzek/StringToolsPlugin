package sk.araed.intellij.plugins.stringtools.popup;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import org.jetbrains.annotations.NotNull;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionProcessor;
import sk.araed.intellij.plugins.stringtools.data.ConversionData;
import sk.araed.intellij.plugins.stringtools.data.DataProvider;
import sk.araed.intellij.plugins.stringtools.data.Operation;

public class StringToolPopupAction extends AnAction  {

  private static final Logger log = Logger.getInstance(StringToolPopupAction.class);

  static class PopupActionData implements DataProvider {

    private final Editor editor;
    private final String actionId;
    private final String selectedText;

    public PopupActionData(
        final Editor editor,
        final String actionId,
        final String selectedText) {
      this.editor = editor;
      this.actionId = actionId;
      this.selectedText = selectedText;
    }

    @Override
    public ConversionData getConversionData() {
      return new ConversionData(editor, selectedText, "", Operation.valueOf(actionId));
    }
  }

  @Override
  public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
    if (anActionEvent.getProject() == null) {
      log.warn("anActionEvnet.project is null");
      return;
    }

    final Editor editor = FileEditorManager.getInstance(anActionEvent.getProject()).getSelectedTextEditor();
    if (editor == null) {
      log.warn("Editor is null");
      return;
    }

    final String selectedText = editor.getSelectionModel().getSelectedText();
    if (selectedText != null && !selectedText.isEmpty()) {

      final int startPosition = editor.getSelectionModel().getSelectionStart();
      final int endPosition = editor.getSelectionModel().getSelectionEnd();

      ActionManager actionManager = ActionManager.getInstance();
      String actionId = actionManager.getId(this);
      PopupActionData data = new PopupActionData(editor, actionId, selectedText);

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
