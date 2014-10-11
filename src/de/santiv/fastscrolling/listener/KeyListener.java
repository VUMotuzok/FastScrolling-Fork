package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;
import de.santiv.fastscrolling.enums.Config;
import de.santiv.fastscrolling.enums.Hotkey;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Logger;

public class KeyListener extends KeyAdapter {
    private static final Logger LOGGER = Logger.getLogger(KeyListener.class.getName());

    private final MouseWheelListener fastScrollingMouseWheelListener;
    private final Editor editor;
    private final Hotkey hotkey;
    private final int modifiers;



    public KeyListener(Editor editor, MouseWheelListener fastScrollingMouseWheelListener) {
        this.editor = editor;
        this.fastScrollingMouseWheelListener = fastScrollingMouseWheelListener;

        this.hotkey = Config.loadEnumValue(Config.HOTKEY, Hotkey.class);
        this.modifiers = hotkey.getModifiers();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (fastScrollingMouseWheelListener.isFastScrollingEnabled()) {
            return;
        }

        if (e.getModifiers() == modifiers) {
            activateFastScrolling();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!fastScrollingMouseWheelListener.isFastScrollingEnabled()) {
            return;
        }

        if (e.getKeyCode() == hotkey.getKeyEvent()) {
            deactivateFastScrolling();
        }
        else if (hotkey.isCombination()) {
            List<Hotkey> combinations = hotkey.getCombinations();

            for (Hotkey k : combinations) {
                if (e.getKeyCode() == k.getKeyEvent()) {
                    deactivateFastScrolling();
                    break;
                }
            }
        }
    }

    private void activateFastScrolling() {
        LOGGER.fine("activate fast scrolling");

        fastScrollingMouseWheelListener.setFastScrollingEnabled(true);

        editor.getContentComponent().addMouseWheelListener(fastScrollingMouseWheelListener);
    }

    private void deactivateFastScrolling() {
        LOGGER.fine("deactivate fast scrolling");

        fastScrollingMouseWheelListener.setFastScrollingEnabled(false);
        editor.getContentComponent().removeMouseWheelListener(fastScrollingMouseWheelListener);
    }
}