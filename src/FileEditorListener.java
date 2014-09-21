import com.google.common.collect.Maps;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FileEditorListener implements FileEditorManagerListener {

    private Map<FileEditor, FastScrollingKeyListener> listeners = Maps.newHashMap();

    @Override
    public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
        FileEditor editors[] = fileEditorManager.getAllEditors();

//        virtualFile.getCanonicalPath()

        for (FileEditor e : editors) {
            if (!listeners.containsKey(e)) {
                installNewListener(e);
            }
        }
//
//        FileEditor arr$[] = editors;
//        int len$ = arr$.length;
//        for(int i$ = 0; i$ < len$; i$++)
//        {
//            FileEditor fileEditor = arr$[i$];
//            SmoothScrollMouseWheelListener listener = new SmoothScrollMouseWheelListener(fileEditor);
//            _listeners.put(fileEditor, listener);
//            listener.startAnimating();
//            Editor editor = ((TextEditor)fileEditor).getEditor();
//            editor.getContentComponent().addMouseWheelListener(listener);
//        }

    }

    private void installNewListener(FileEditor e) {
        FastScrollingKeyListener l = new FastScrollingKeyListener(e);
        e.getComponent().addKeyListener(l);
        listeners.put(e, l);

        // TODO: logger
        System.out.println("Install new listener for " + ((TextEditor) e).getEditor().getDocument());
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
        FileEditor[] editors = fileEditorManager.getAllEditors();


    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent fileEditorManagerEvent) {

    }
}
