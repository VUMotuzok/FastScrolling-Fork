package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class FileEditorListener implements FileEditorManagerListener {
    private static final Logger LOGGER = Logger.getLogger(FileEditorListener.class.getName());

    @Override
    public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
        FileEditor editors[] = fileEditorManager.getAllEditors(virtualFile);

        String filePath = virtualFile.getCanonicalPath();

        for (FileEditor e : editors) {
            installNewListener(filePath, e);
        }
    }

    private void installNewListener(String filePath, FileEditor e) {
        Editor editor = ((TextEditor) e).getEditor();
        FastScrollingKeyListener l = new FastScrollingKeyListener(e);
        editor.getContentComponent().addKeyListener(l);

        LOGGER.fine("Install new listener for " + filePath);
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
    }


    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent fileEditorManagerEvent) {

    }
}
