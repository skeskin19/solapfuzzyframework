package tr.edu.metu.ceng.sk.hac.actions;

import javax.swing.*;


public class ResetAction extends HACAbstractAction {

    private static final String ACTION_COMMAND_KEY_ABOUT = "reset-command";
    private static final String NAME_ABOUT = "Reset";
    private static final String SMALL_ICON_ABOUT = null;
    private static final String LARGE_ICON_ABOUT = null;
    private static final String SHORT_DESCRIPTION_ABOUT = "Reset";
    private static final String LONG_DESCRIPTION_ABOUT = "Resets Algorithm";
    private static final int MNEMONIC_KEY_ABOUT = 'R';

    public ResetAction() {
        putValue(Action.NAME, NAME_ABOUT);
        putValue(Action.SMALL_ICON, getIcon(SMALL_ICON_ABOUT));
        putValue(LARGE_ICON, getIcon(LARGE_ICON_ABOUT));
        putValue(Action.SHORT_DESCRIPTION, SHORT_DESCRIPTION_ABOUT);
        putValue(Action.LONG_DESCRIPTION, LONG_DESCRIPTION_ABOUT);
        putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_KEY_ABOUT));
        putValue(Action.ACTION_COMMAND_KEY, ACTION_COMMAND_KEY_ABOUT);
    }
}
