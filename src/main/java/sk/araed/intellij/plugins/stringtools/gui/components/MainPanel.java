package sk.araed.intellij.plugins.stringtools.gui.components;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.NORTHWEST;
import static java.awt.GridBagConstraints.VERTICAL;

import com.intellij.util.ui.JBUI.Borders;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.intellij.ui.components.panels.VerticalLayout;

import javax.swing.border.EmptyBorder;
import sk.araed.intellij.plugins.stringtools.StringToolsController;
import sk.araed.intellij.plugins.stringtools.data.ConversionData;
import sk.araed.intellij.plugins.stringtools.data.Operation;
import sk.araed.intellij.plugins.stringtools.gui.actions.CloseAction;
import sk.araed.intellij.plugins.stringtools.gui.actions.CopyToClipboardAction;
import sk.araed.intellij.plugins.stringtools.gui.actions.ReplaceInEditorAction;
import sk.araed.intellij.plugins.stringtools.gui.i18n.ResourceKey;

/**
 * @author boris.brinza 12-Apr-2017.
 */
public class MainPanel extends JPanel {
	private final StringToolsController controller;
	private final List<OperationSelector> operations = new ArrayList<>();

	private InputTextEditor inputText;
	private JTextArea outputText;
	private StatusLine statusLine;

	public MainPanel(StringToolsController controller) {
		this.controller = controller;
		initalizeGUI();
	}

	public void showWarning(ConversionData conversionData) {
		inputText.showWarning(conversionData.isInvalidInput());
		statusLine.updateErrorStatus(conversionData.getErrorMessageForInvalidInput());
	}

	public void setOutputContent(String content) {
		outputText.setText(content);
	}

	public void setInputContent(String content) {
		inputText.setText(content);
	}

	public String getInputContent() {
		return inputText.getText();
	}

	public String getOutputContent() {
		return outputText.getText();
	}

	public Operation getSelectedOperation() {
		return operations.stream().filter(AbstractButton::isSelected).findFirst().map(OperationSelector::getOperation)
				.orElseThrow(IllegalStateException::new);
	}

	private void initalizeGUI() {
		GuiFactory guiFactory = new GuiFactory();
		setLayout(new GridBagLayout());

		//original text area
		add(guiFactory.createLabel(ResourceKey.ORIGINAL_TEXT), guiFactory.getGridBagBuilder().withPos(0, 0)
				.withFill(HORIZONTAL).withAnchor(NORTHWEST).withWeight(0.0, 0.0).toGBC());
		inputText = guiFactory.createInputTextEditor(controller);
		add(inputText, guiFactory.getGridBagBuilder().withPos(1, 0).withAnchor(NORTHWEST).withFill(BOTH).toGBC());

		//converted text area
		add(guiFactory.createLabel(ResourceKey.CONVERTED_TEXT), guiFactory.getGridBagBuilder().withPos(0, 1)
				.withFill(HORIZONTAL).withAnchor(NORTHWEST).withWeight(0.0, 0.0).toGBC());
		outputText = guiFactory.createOutputTextField();
		add(outputText, guiFactory.getGridBagBuilder().withPos(1, 1).withAnchor(NORTHWEST).withFill(BOTH).toGBC());

		//actions panel
		final JPanel actionsPanel = guiFactory.createPanel(new GridLayout(1, 3));
		//conversion actions
		ButtonGroup buttonGroup = new ButtonGroup();
		final JPanel radioPanel = guiFactory.createPanel(new VerticalLayout(0));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.STRING_TO_HEX_ACTION, Operation.STRING_TO_HEX, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.HEX_TO_STRING_ACTION, Operation.HEX_TO_STRING, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.STRING_TO_BINARY_ACTION, Operation.STRING_TO_BIN, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.BINARY_TO_STRING_ACTION, Operation.BIN_TO_STRING, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.STRING_TO_OCT_ACTION, Operation.STRING_TO_OCT, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.OCT_TO_STRING_ACTION, Operation.OCT_TO_STRING, controller, buttonGroup));
		operations.forEach(radioPanel::add);
		guiFactory.addBorder(radioPanel, ResourceKey.CONVERSION_TITLE);
		actionsPanel.add(radioPanel);

		//encoding actions
		final JPanel radioPanel2 = guiFactory.createPanel(new VerticalLayout(0));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.BASE_64_ENCODE_ACTION, Operation.BASE_64_ENCODE, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.BASE_64_DECODE_ACTION, Operation.BASE_64_DECODE, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.BASE_64_MIME_ENCODE_ACTION, Operation.BASE_64_MIME_ENCODE, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.BASE_64_MIME_DECODE_ACTION, Operation.BASE_64_MIME_DECODE, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.URL_ENCODE_ACTION, Operation.URL_ENCODE, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.URL_DECODE_ACTION, Operation.URL_DECODE, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.HTML_ENCODE_ACTION, Operation.HTML_ENCODE, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.HTML_DECODE_ACTION, Operation.HTML_DECODE, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.JWT_DECODE_ACTION, Operation.JWT_DECODE, controller, buttonGroup));
		operations.subList(6, operations.size()).forEach(radioPanel2::add);
		guiFactory.addBorder(radioPanel2, ResourceKey.CODING_TITLE);
		actionsPanel.add(radioPanel2);


		//SHA operations
		final JPanel radioPanel3 = guiFactory.createPanel(new VerticalLayout(0));
		operations.add(guiFactory.createOperationSelector(ResourceKey.SHA_256_ACTION, Operation.SHA256_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.SHA_384_ACTION, Operation.SHA384_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.SHA_512_ACTION, Operation.SHA512_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.SHA3_256_ACTION, Operation.SHA3_256_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.SHA3_384_ACTION, Operation.SHA3_384_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.SHA3_512_ACTION, Operation.SHA3_512_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.KECCAK_256_ACTION, Operation.KECCAK256_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.KECCAK_384_ACTION, Operation.KECCAK384_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.KECCAK_512_ACTION, Operation.KECCAK512_HASH, controller, buttonGroup));
		operations.subList(13, operations.size()).forEach(radioPanel3::add);
		guiFactory.addBorder(radioPanel3, ResourceKey.HASH_TITLE);
		actionsPanel.add(radioPanel3);

		//other operations
		final JPanel radioPanel4 = guiFactory.createPanel(new VerticalLayout(0));
		operations.add(guiFactory.createOperationSelector(ResourceKey.ROT13_ACTION, Operation.ROT13, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.MD2_HASH_ACTION, Operation.MD2_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.MD4_HASH_ACTION, Operation.MD4_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.MD5_HASH_ACTION, Operation.MD5_HASH, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.CRC16_ACTION, Operation.CRC16, controller, buttonGroup));
		operations.add(guiFactory.createOperationSelector(ResourceKey.CRC32_ACTION, Operation.CRC32, controller, buttonGroup));
		operations.add(guiFactory
				.createOperationSelector(ResourceKey.LUHN_DIGIT_GEN_ACTION, Operation.LUHN_DIGIT_GENERATOR, controller, buttonGroup));
		operations.subList(22, operations.size()).forEach(radioPanel4::add);
		guiFactory.addBorder(radioPanel4, ResourceKey.OTHER_TITLE);
		actionsPanel.add(radioPanel4);



		add(actionsPanel, guiFactory.getGridBagBuilder().withPos(0, 3).withGridWidth(3).withAnchor(GridBagConstraints.CENTER).toGBC());



		operations.get(0).setSelected(true);    //select first button

		//add status line
		statusLine = guiFactory.createStatusLine();
		add(statusLine, guiFactory.getGridBagBuilder().withPos(0, 5).withAnchor(GridBagConstraints.CENTER).withGridWidth(2).toGBC());

		//actions
		JPanel buttonPanel = guiFactory.createPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(guiFactory.createActionButton(ResourceKey.REPLACE_ACTION, new ReplaceInEditorAction(controller, statusLine)));
		buttonPanel.add(guiFactory.createActionButton(ResourceKey.COPY_TO_CPB_ACTION, new CopyToClipboardAction(controller, statusLine)));
		buttonPanel.add(guiFactory.createActionButton(ResourceKey.CLOSE_ACTION, new CloseAction(controller)));
		add(buttonPanel,
				guiFactory.getGridBagBuilder().withPos(0, 4).withFill(GridBagConstraints.VERTICAL).withAnchor(GridBagConstraints.WEST)
						.withGridWidth(2).toGBC());



	}
}