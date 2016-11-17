package webviewbrowser;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.List;

/**
 * Demonstrates a WebView object accessing a web page.
 *
 * @see javafx.scene.web.WebView
 * @see javafx.scene.web.WebEngine
 */
public class WebViewBrowser extends Application {

    /**
     * The main() method is ignored in correctly deployed JavaFX
     * application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts,
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new WebViewPane();
        primaryStage.setTitle("Браузер на основе JavaFX");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    /**
     * Create a resizable WebView pane
     */
    public class WebViewPane extends Pane {

        public WebViewPane() {
            VBox.setVgrow(this, Priority.ALWAYS);
            setMaxWidth(Double.MAX_VALUE);
            setMaxHeight(Double.MAX_VALUE);

            WebView view = new WebView();
            view.setMinSize(500, 400);
            view.setPrefSize(500, 400);
            final WebEngine eng = view.getEngine();
            eng.load("http://www.oracle.com/us/index.html");
            final TextField locationField = new TextField("http://www.oracle.com/us/index.html");
            locationField.setMaxHeight(Double.MAX_VALUE);
            Button goButton = new Button("Go");
            goButton.setDefaultButton(true);
            EventHandler<ActionEvent> goAction = event -> eng.load(locationField.getText().startsWith("http://") ? locationField.getText() :
                    "http://" + locationField.getText());
            goButton.setOnAction(goAction);
            locationField.setOnAction(goAction);
            eng.locationProperty().addListener((observable, oldValue, newValue) -> {
                locationField.setText(newValue);
            });
            GridPane grid = new GridPane();
            grid.setVgap(5);
            grid.setHgap(5);
            GridPane.setConstraints(locationField, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
            GridPane.setConstraints(goButton, 1, 0);
            GridPane.setConstraints(view, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
            grid.getColumnConstraints().addAll(
                    new ColumnConstraints(100, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true),
                    new ColumnConstraints(40, 40, 40, Priority.NEVER, HPos.CENTER, true)
            );
            grid.getChildren().addAll(locationField, goButton, view);
            getChildren().add(grid);
        }

        @Override
        protected void layoutChildren() {
            List<Node> managed = getManagedChildren();
            double width = getWidth();
            double height = getHeight();
            double top = getInsets().getTop();
            double right = getInsets().getRight();
            double left = getInsets().getLeft();
            double bottom = getInsets().getBottom();
            for (Node child : managed) {
                layoutInArea(child, left, top,
                        width - left - right, height - top - bottom,
                        0, Insets.EMPTY, true, true, HPos.CENTER, VPos.CENTER);
            }
        }
    }
}
