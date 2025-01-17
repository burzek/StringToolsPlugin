package sk.araed.intellij.plugins.stringtools.data;

import com.intellij.openapi.editor.Editor;

import com.intellij.openapi.project.Project;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 12-Apr-2017.
 */
public class ConversionData {

  private final Editor currentEditor;
  private final String originalText;
	private String convertedText;
	private final Operation operation;
	private ResourceKey errorMessage;

	public ConversionData(
			final Editor currentEditor,
			final String originalText,
			final String convertedText,
			final Operation operation) {
    this.currentEditor = currentEditor;
    this.originalText = originalText;
		this.convertedText = convertedText;
		this.operation = operation;
	}

	public String getOriginalText() {
		return originalText;
	}

	public String getConvertedText() {
		return convertedText;
	}

	public void setConvertedText(String convertedText) {
		this.convertedText = convertedText;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setErrorMessage(ResourceKey errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ResourceKey getErrorMessageForInvalidInput() {
		return errorMessage;
	}

	public boolean isInvalidInput() {
		return errorMessage != null;
	}

	public Editor getCurrentEditor() {
		return currentEditor;
	}
}
