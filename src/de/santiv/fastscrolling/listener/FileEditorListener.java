package de.santiv.fastscrolling.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.TextEditor;
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

    private void installNewListener(String filePath, FileEditor fileEditor) {
        final Editor editor = ((TextEditor) fileEditor).getEditor();

        final MouseWheelListener fastScrollingMouseWheelListener = new MouseWheelListener(editor);
        KeyListener keyListener = new KeyListener(editor, fastScrollingMouseWheelListener);
        FocusListener focusListener = new FocusListener(editor, fastScrollingMouseWheelListener);

        editor.getContentComponent().addKeyListener(keyListener);
        editor.getContentComponent().addFocusListener(focusListener);

        LOGGER.fine("Install new listener for " + filePath);
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
    }


    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent fileEditorManagerEvent) {
    }
}
