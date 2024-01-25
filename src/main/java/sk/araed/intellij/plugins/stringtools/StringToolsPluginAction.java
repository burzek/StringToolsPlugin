package sk.araed.intellij.plugins.stringtools;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;

import org.jetbrains.annotations.NotNull;
import sk.araed.intellij.plugins.stringtools.gui.components.GuiFactory;

/**
 * @author boris.brinza 10-Apr-2017.
 */
public class StringToolsPluginAction extends AnAction {


	@Override
	public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
		GuiFactory guiFactory = new GuiFactory();
		DialogBuilder dlg = guiFactory.createMainDialog(getEventProject(anActionEvent));
		dlg.show();
	}
}
