package de.santiv.fastscrolling;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import de.santiv.fastscrolling.enums.Strings;
import de.santiv.fastscrolling.listener.FileEditorListener;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class FastScrollingPlugin implements ProjectComponent {
    private static final Logger LOGGER = Logger.getLogger(FastScrollingPlugin.class.getName());

    private Project project;

    public FastScrollingPlugin(Project project) {
        this.project = project;
    }

    public void initComponent() {
        LOGGER.info("Initialize " + getComponentName());

        project.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorListener());
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return Strings.COMPONENT_NAME.getDescription();
    }

    public void projectOpened() {
    }

    public void projectClosed() {
    }
}
