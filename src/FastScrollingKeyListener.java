import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class FastScrollingKeyListener extends KeyAdapter {
    private static final Logger LOGGER = Logger.getLogger(FastScrollingKeyListener.class.getName());

    public static final int CTRL_KEY = 17; // TODO
    private final FastScrollingMouseWheelListener fastScrollingMouseWheelListener;

    private Editor editor;
    private boolean fastScrollingEnabled = false;

    public FastScrollingKeyListener(FileEditor fileEditor) {
        this.editor = ((TextEditor)fileEditor).getEditor();
        fastScrollingMouseWheelListener = new FastScrollingMouseWheelListener(editor);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == CTRL_KEY) {
            activateFastScrolling();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == CTRL_KEY) {
            deactivateFastScrolling();
        }
    }

    private void activateFastScrolling() {
        if(fastScrollingEnabled) {
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
