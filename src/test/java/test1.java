import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test1 extends Application {
    private int Width = 600;
    private int Height = 600;

    @Override
    public void start(Stage primaryStage) {

        Rectangle rectangle = new Rectangle(34, 4, 10, 10);
        rectangle.setFill(Color.BLUE);
        StackPane sp = new StackPane();
        sp.setLayoutX(3);
        sp.setLayoutY(54);
        sp.setPrefSize(50, 50);
        sp.setBackground(new Background(new BackgroundFill(Color.INDIANRED, null, null)));
        Pane root = new Pane(sp);
        root.setPrefSize(Width, Height);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        animation1(root, sp, 100, Duration.seconds(1));

    }

    public void animation1(Pane parent, Pane child, double insets, Duration time) {
        ScaleTransition st1 = new ScaleTransition(time, child);
        st1.setToX((parent.getPrefWidth() - insets + 20) / child.getPrefWidth());
        st1.setToY((parent.getPrefHeight() - insets + 20) / child.getPrefHeight());
        st1.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition st2 = new ScaleTransition(time, child);
        st2.setToX((parent.getPrefWidth() - insets) / child.getBoundsInParent().getWidth());
        st2.setToY((parent.getPrefHeight() - insets) / child.getBoundsInParent().getHeight());
        st2.setInterpolator(Interpolator.EASE_IN);

        TranslateTransition tt = new TranslateTransition(time, child);
        double finalX = (parent.getPrefWidth() - child.getPrefWidth()) / 2 - child.getLayoutX();
        double finalY = (parent.getPrefHeight() - child.getPrefHeight()) / 2 - child.getLayoutY();
        tt.setToX(finalX);
        tt.setToY(finalY);
        tt.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition pt = new ParallelTransition(st1, tt);
        SequentialTransition sqt = new SequentialTransition(pt, st2);
        sqt.play();
    }
}
