package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollingModel;
import de.santiv.fastscrolling.configuration.Configuration;

import java.awt.event.MouseWheelEvent;
import java.util.logging.Logger;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    private static final Logger LOGGER = Logger.getLogger(MouseWheelListener.class.getName());
    private final int currentStep;
    private Editor editor;

    private boolean fastScrollingEnabled = false;

    public MouseWheelListener(Editor editor) {
        this.editor = editor;
        this.currentStep = Configuration.loadStepValue();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        ScrollingModel scrollingModel = editor.getScrollingModel();
        LOGGER.finest(String.format("scrolling offset=%s", scrollingModel.getVerticalScrollOffset()));

        int steps = currentStep;

        if (e.getUnitsToScroll() < 0) {
            steps = -currentStep;
        }

        scrollingModel.disableAnimation();
        scrollingModel.scrollVertically(scrollingModel.getVerticalScrollOffset() + steps);
        scrollingModel.enableAnimation();
    }

    public boolean isFastScrollingEnabled() {
        return fastScrollingEnabled;
    }

    public void setFastScrollingEnabled(boolean fastScrollingEnabled) {
        this.fastScrollingEnabled = fastScrollingEnabled;
    }
}
