package tr.edu.metu.ceng.sk.kmeans.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import javax.swing.event.EventListenerList;


public abstract class KMeansAbstractAction extends AbstractAction {

    // The listener to action events (usually the main UI)
    private EventListenerList listeners;

    // Image directory URL
    public static final String JLF_IMAGE_DIR = "/toolbarButtonGraphics/general/";

    /**
     * The key used for storing a large icon for the action,
     * used for toolbar buttons.
     * <p>
     * Note: Eventually this key belongs in the javax.swing.Action interface.
     */
    public static final String LARGE_ICON = "LargeIcon";

    //
    // These next public methods may belong in the AbstractAction class.
    //

    /**
     * Gets the value from the key Action.ACTION_COMMAND_KEY
     */
    public String getActionCommand()  {
        return (String)getValue(Action.ACTION_COMMAND_KEY);
    }

    /**
     * Gets the value from the key Action.SHORT_DESCRIPTION
     */
    public String getShortDescription()  {
        return (String)getValue(Action.SHORT_DESCRIPTION);
    }

    /**
     * Gets the value from the key Action.LONG_DESCRIPTION
     */
    public String getLongDescription()  {
        return (String)getValue(Action.LONG_DESCRIPTION);
    }

    /**
     * Gets the value from the key Action.LONG_DESCRIPTION
     */
    public String getName()  {
        return (String)getValue(Action.NAME);
    }

    // ActionListener registration and invocation.

    /**
     * Forwards the ActionEvent to the registered listener.
     */
    public void actionPerformed(ActionEvent evt)  {
        if (listeners != null) {
            Object[] listenerList = listeners.getListenerList();

            // Recreate the ActionEvent and stuff the value of the ACTION_COMMAND_KEY
            ActionEvent e = new ActionEvent(evt.getSource(), evt.getID(),
                    (String)getValue(Action.ACTION_COMMAND_KEY));
            for (int i = 0; i <= listenerList.length-2; i += 2) {
                ((ActionListener)listenerList[i+1]).actionPerformed(e);
            }
        }
    }

    public void addActionListener(ActionListener l)  {
        if (listeners == null) {
            listeners = new EventListenerList();
        }
        listeners.add(ActionListener.class, l);
    }

    public void removeActionListener(ActionListener l)  {
        if (listeners == null) {
            return;
        }
        listeners.remove(ActionListener.class, l);
    }

    /**
     * Returns the Icon associated with the name from the resources.
     * The resouce should be in the path.
     * @param name Name of the icon file i.e., help16.gif
     * @return the name of the image or null if the icon is not found.
     */
    public ImageIcon getIcon(String name)  {
        if (name != null) {
            String imagePath = JLF_IMAGE_DIR + name;
            URL url = this.getClass().getResource(imagePath);
            if (url != null)  return new ImageIcon(url);
        }
        return null;
    }
}
