package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FocusListener extends FocusAdapter{
    private Editor editor;
    private MouseWheelListener fastScrollingMouseWheelListener;

    public FocusListener(Editor editor, MouseWheelListener fastScrollingMouseWheelListener) {
        this.editor = editor;
        this.fastScrollingMouseWheelListener = fastScrollingMouseWheelListener;
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(!fastScrollingMouseWheelListener.isFastScrollingEnabled()) {
            return;
        }
        fastScrollingMouseWheelListener.setFastScrollingEnabled(false);
        editor.getContentComponent().removeMouseWheelListener(fastScrollingMouseWheelListener);
    }
}
