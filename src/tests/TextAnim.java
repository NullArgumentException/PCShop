import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TextAnim extends Application {
    private final String str = """
                ------------------------------------------------------------
                 PC-Shop             Main Menu   by: [owner]
                ------------------------------------------------------------
                1) Add Product
                2) Edit Product
                3) Search Product
                4) Remove Product
                0) Close Shop
                ------------------------------------------------------------""";

    @Override
    public void start(Stage primaryStage) {
        Text text = new Text();
        VBox root = new VBox(text);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 330, 120, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();

        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.01),
                event -> {
                    if (i.get() >= str.length()) {
                        timeline.stop();
                    } else {
                        text.setText(str.substring(0, i.get()));
                        i.set(i.get() + 1);
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}