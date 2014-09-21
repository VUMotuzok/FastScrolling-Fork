package de.santiv.fastscrolling;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.vfs.VirtualFile;
import de.santiv.fastscrolling.listener.FastScrollingKeyListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public class FileEditorListener implements FileEditorManagerListener {

    //    private Map<FileEditor, de.santiv.fastscrolling.listener.FastScrollingKeyListener> listeners = Maps.newHashMap();
    private Multimap<String, FastScrollingKeyListener> keyListeners = ArrayListMultimap.create();

    @Override
    public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
        FileEditor editors[] = fileEditorManager.getAllEditors(virtualFile);

        String filePath = virtualFile.getCanonicalPath();


        for (FileEditor e : editors) {
            if (!keyListeners.containsKey(filePath)) {
                installNewListener(filePath, e);
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

    private void installNewListener(String filePath, FileEditor e) {
        Editor editor = ((TextEditor)e).getEditor();
        FastScrollingKeyListener l = new FastScrollingKeyListener(e);
        editor.getContentComponent().addKeyListener(l);
        keyListeners.put(filePath, l);

        // TODO: logger
        System.out.println("Install new listener for " + filePath);
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
        FileEditor editors[] = fileEditorManager.getAllEditors(virtualFile);

        String filePath = virtualFile.getCanonicalPath();

        for (FileEditor e : editors) {
            removeListeners(filePath, e);
        }

        keyListeners.removeAll(filePath);
    }

    private void removeListeners(String filePath, FileEditor e) {
        Collection<FastScrollingKeyListener> fastScrollingKeyListeners = keyListeners.get(filePath);
        Iterator<FastScrollingKeyListener> iterator = fastScrollingKeyListeners.iterator();
        while(iterator.hasNext()) {
            FastScrollingKeyListener l = iterator.next();

            e.getComponent().removeKeyListener(l);

            System.out.println("Remove listener for " + filePath); // TODO: log
        }
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent fileEditorManagerEvent) {

    }
}
