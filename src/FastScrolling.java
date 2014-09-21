import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FastScrolling extends AnAction {
    private static final Logger LOGGER = Logger.getLogger(FastScrolling.class.getName());

    public static final int CTRL_KEY = 17;
    Editor theEditor;
    boolean fastScrollingEnabled = false;

    FastScrollingMouseWheelListener myMouseWheelListener;

    public FastScrolling() {
        LOGGER.setLevel(Level.ALL);
//        myMouseWheelListener = new FastScrollingMouseWheelListener();
    }

    public void actionPerformed(AnActionEvent e) {
//        Project project = e.getData(PlatformDataKeys.PROJECT);
//
//
//        DataKeys.FILE_EDITOR.getData(e.getDataContext());
//
//        theEditor = DataKeys.EDITOR.getData(e.getDataContext());
//
//        myMouseWheelListener.setEditor(theEditor);
//
//        theEditor.getContentComponent().addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//
//                if (e.getKeyCode() == CTRL_KEY) {
//                    activateFastScrolling();
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                if (e.getKeyCode() == CTRL_KEY) {
//                    deactivateFastScrolling();
//                }
//            }
//        });


    }

    private void activateFastScrolling() {
        if(fastScrollingEnabled) {
            return;
        }
        LOGGER.fine("activateFastScrolling");

        fastScrollingEnabled = true;

        theEditor.getContentComponent().addMouseWheelListener(myMouseWheelListener);
    }

    private void deactivateFastScrolling() {
        LOGGER.fine("deactivateFastScrolling");
        fastScrollingEnabled = false;
        theEditor.getContentComponent().removeMouseWheelListener(myMouseWheelListener);
    }

}
