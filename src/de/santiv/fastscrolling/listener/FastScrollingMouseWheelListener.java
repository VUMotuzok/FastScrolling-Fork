package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollingModel;
import de.santiv.fastscrolling.configuration.Configuration;

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
        ScrollingModel scrollingModel = editor.getScrollingModel();
        LOGGER.finest(String.format("scrolling offset=%s", scrollingModel.getVerticalScrollOffset()));

        int steps = Configuration.loadStepValue();

        if (e.getUnitsToScroll() < 0) {
            steps = -Configuration.loadStepValue();
        }

        scrollingModel.disableAnimation();
        scrollingModel.scrollVertically(scrollingModel.getVerticalScrollOffset() + steps);
        scrollingModel.enableAnimation();
    }
}
