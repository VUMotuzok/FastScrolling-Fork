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
    private final Editor editor;
    private final Hotkey hotkey;
    private final int modifiers;

    private boolean fastScrollingEnabled = false;

    public FastScrollingKeyListener(FileEditor fileEditor) {
        this.editor = ((TextEditor) fileEditor).getEditor();
        this.fastScrollingMouseWheelListener = new FastScrollingMouseWheelListener(editor);
        this.hotkey = Configuration.loadHotkey();
        this.modifiers = hotkey.getModifiers();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (fastScrollingEnabled) {
            return;
        }

        if (e.getModifiers() == modifiers) {
            activateFastScrolling();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!fastScrollingEnabled) {
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

        fastScrollingEnabled = true;

        editor.getContentComponent().addMouseWheelListener(fastScrollingMouseWheelListener);
    }

    private void deactivateFastScrolling() {
        LOGGER.fine("deactivate fast scrolling");

        fastScrollingEnabled = false;
        editor.getContentComponent().removeMouseWheelListener(fastScrollingMouseWheelListener);
    }
}
