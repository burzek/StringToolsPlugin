package sk.araed.intellij.plugins.stringtools.popup;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionFactory;
import sk.araed.intellij.plugins.stringtools.conversion.ConversionProcessor;
import sk.araed.intellij.plugins.stringtools.conversion.converters.JwtDecoder;
import sk.araed.intellij.plugins.stringtools.data.ConversionData;
import sk.araed.intellij.plugins.stringtools.data.DataProvider;
import sk.araed.intellij.plugins.stringtools.data.Operation;

public class StringToolPopupAction extends AnAction  {

  class PopupActionData implements DataProvider {

    @Override
    public ConversionData getConversionData() {
      return null;// new ConversionData();
    }
  }

  @Override
  public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
//    if (ActionManager.getInstance().getAction("sk.araed.intellij.plugins.stringtools.popup.Ascii2Hex").getTemplateText().
//        equalsIgnoreCase(anActionEvent.getPresentation().getText()) {
//      ConversionProcessor conversionProcessor = new ConversionProcessor(this);
//      conversionProcessor.doConversion();
//      new ConversionFactory().getConverter(Operation.STRING_TO_HEX).convert()
//    }
//    Logger.getInstance(StringToolPopupAction.class).info("action:" + anActionEvent);
//    anActionEvent.getActionManager().getAction("sk.araed.intellij.plugins.stringtools.popup.Base64Decoder")

    ActionManager actionManager = ActionManager.getInstance();
    String actionId = actionManager.getId(this);

    System.out.println("action:" + actionId);

  }
}
