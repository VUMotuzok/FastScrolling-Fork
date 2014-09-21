import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class FastScrollingPlugin implements ProjectComponent {
    private Project project;

    public FastScrollingPlugin(Project project) {
        this.project = project;
    }

    public void initComponent() {
        System.out.println("Initialize " + getComponentName() + "..."); // TODO: logger

        project.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorListener());

    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "FastScrollingPlugin";
    }

    public void projectOpened() {
        // called when project is opened
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
