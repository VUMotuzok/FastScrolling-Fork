package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;
import de.santiv.fastscrolling.FastScrollingSettings;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.logging.Logger;

public class FastScrollingMouseWheelListener implements MouseWheelListener {
    private static final Logger LOGGER = Logger.getLogger(FastScrollingMouseWheelListener.class.getName());
    private Editor editor;

    public FastScrollingMouseWheelListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        LOGGER.finest(String.format("scrolling offset=%s", editor.getScrollingModel().getVerticalScrollOffset()));

        int steps = FastScrollingSettings.loadStepValue();
        if (e.getUnitsToScroll() < 0) {
            steps = -FastScrollingSettings.loadStepValue();
        }

        editor.getScrollingModel().disableAnimation();
        editor.getScrollingModel().scrollVertically(editor.getScrollingModel().getVerticalScrollOffset() + steps);
        editor.getScrollingModel().enableAnimation();
    }
}
