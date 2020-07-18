package tr.edu.metu.ceng.sk.fuzzy.actions;

import javax.swing.*;


public class RunAction extends FuzzyAbstractAction {

    private static final String ACTION_COMMAND_KEY_ABOUT = "run-command";
    private static final String NAME_ABOUT = "Run";
    private static final String SMALL_ICON_ABOUT = null;
    private static final String LARGE_ICON_ABOUT = null;
    private static final String SHORT_DESCRIPTION_ABOUT = "Run";
    private static final String LONG_DESCRIPTION_ABOUT = "Runs Algorithm";
    private static final int MNEMONIC_KEY_ABOUT = 'U';

    public RunAction() {
        putValue(Action.NAME, NAME_ABOUT);
        putValue(Action.SMALL_ICON, getIcon(SMALL_ICON_ABOUT));
        putValue(LARGE_ICON, getIcon(LARGE_ICON_ABOUT));
        putValue(Action.SHORT_DESCRIPTION, SHORT_DESCRIPTION_ABOUT);
        putValue(Action.LONG_DESCRIPTION, LONG_DESCRIPTION_ABOUT);
        putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_KEY_ABOUT));
        putValue(Action.ACTION_COMMAND_KEY, ACTION_COMMAND_KEY_ABOUT);
    }
}
