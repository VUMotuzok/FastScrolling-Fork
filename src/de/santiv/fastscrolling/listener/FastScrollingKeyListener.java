package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import de.santiv.fastscrolling.configuration.Configuration;
import de.santiv.fastscrolling.enums.Hotkey;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Logger;

public class FastScrollingKeyListener extends KeyAdapter {
    private static final Logger LOGGER = Logger.getLogger(FastScrollingKeyListener.class.getName());

    private final FastScrollingMouseWheelListener fastScrollingMouseWheelListener;

    private Editor editor;
    private boolean fastScrollingEnabled = false;

    public FastScrollingKeyListener(FileEditor fileEditor) {
        this.editor = ((TextEditor) fileEditor).getEditor();
        fastScrollingMouseWheelListener = new FastScrollingMouseWheelListener(editor);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        Hotkey hotkey = Configuration.loadHotkey();

        if (e.getModifiers() == hotkey.getModifiers()) {
            activateFastScrolling();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Hotkey hotkey = Configuration.loadHotkey();

        if (hotkey.isCombination()) {
            List<Hotkey> combinations = hotkey.getCombinations();

            for (Hotkey k : combinations) {
                if (e.getKeyCode() == k.getKeyEvent()) {
                    deactivateFastScrolling();
                    break;
                }
            }
        } else if (e.getKeyCode() == hotkey.getKeyEvent()) {
            deactivateFastScrolling();
        }
    }

    private void activateFastScrolling() {
        if (fastScrollingEnabled) {
            return;
        }
        LOGGER.fine("activateFastScrolling");

        fastScrollingEnabled = true;

        editor.getContentComponent().addMouseWheelListener(fastScrollingMouseWheelListener);
    }

    private void deactivateFastScrolling() {
        LOGGER.fine("deactivateFastScrolling");
        fastScrollingEnabled = false;
        editor.getContentComponent().removeMouseWheelListener(fastScrollingMouseWheelListener);
    }
}
