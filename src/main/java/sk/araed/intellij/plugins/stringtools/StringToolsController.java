package sk.araed.intellij.plugins.stringtools;

import com.intellij.openapi.project.ProjectManager;
import org.apache.tools.ant.Project;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionProcessor;
import sk.araed.intellij.plugins.stringtools.data.ConversionData;
import sk.araed.intellij.plugins.stringtools.data.DataProvider;
import sk.araed.intellij.plugins.stringtools.gui.actions.ActionsRequestListener;
import sk.araed.intellij.plugins.stringtools.gui.components.MainPanel;
import sk.araed.intellij.plugins.stringtools.gui.components.StringToolsDialog;

/**
 * @author boris.brinza 10-Oct-2017.
 */
public class StringToolsController implements ActionsRequestListener, DataProvider {

	private final StringToolsDialog dialog;
	private final ConversionProcessor conversionProcessor;


	public StringToolsController(StringToolsDialog dialog) {
		this.dialog = dialog;
		conversionProcessor = new ConversionProcessor(this);

	}

	@Override
	public ConversionData getConversionData() {
		return new ConversionData(getMainPanel().getInputContent(), getMainPanel().getOutputContent(),
				getMainPanel().getSelectedOperation());
	}

	@Override
	public void exitRequested() {
		dialog.getDialogWrapper().close(0, true);
	}

	@Override
	public void transformationRequested() {

		ConversionData transformationData = conversionProcessor.doConversion();

		getMainPanel().showWarning(transformationData);
		getMainPanel().setOutputContent(transformationData.getConvertedText());

	}

	private MainPanel getMainPanel() {
		return dialog.getMainPanel();
	}
}
