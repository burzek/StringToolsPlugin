package sk.araed.intellij.plugins.stringtools.data;

import com.intellij.openapi.editor.Editor;

import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 12-Apr-2017.
 */
public class ConversionData {

	private final String originalText;
	private String convertedText;
	private final Operation operation;
	private ResourceKey errorMessage;

	public ConversionData(String originalText, String convertedText, Operation operation) {
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
}
