import com.weijie.ui.FXTool.ResourcesLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class testIcon extends Application {

    private final VBox student = new VBox(new FontIcon(FontAwesomeSolid.USER), new Label("Student"));
    private final VBox parent = new VBox(new FontIcon(FontAwesomeSolid.USER_FRIENDS), new Label("Parent"));
    private final VBox teacher = new VBox(new FontIcon(FontAwesomeSolid.USER_TIE), new Label("Teacher"));
    private final HBox iconBox = new HBox(student, parent, teacher);
    private final VBox titleBox = new VBox(new Label("Choose your character"), iconBox);

    @Override
    public void start(Stage stage) {
        titleBox.getStyleClass().add("title-box");
        iconBox.getStyleClass().add("icon-box");
        student.getStyleClass().add("student");
        parent.getStyleClass().add("parent");
        teacher.getStyleClass().add("teacher");
        StackPane root = new StackPane(titleBox);
        root.setMinSize(730, 430);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourcesLoader.load("/test1.css"));// 加载基础样式
        Stage s = new Stage();
        s.setScene(scene);
        s.show();
    }
}
