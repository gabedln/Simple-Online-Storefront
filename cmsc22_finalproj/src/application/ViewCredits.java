package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewCredits {
    private Scene creditsScene;

    public ViewCredits(Stage stage, Scene previous) {
        BorderPane root = new BorderPane();

        // Fullscreen background image
        Image creditsImage = new Image(getClass().getResourceAsStream("/application/images/credits.png"));
        ImageView creditsView = new ImageView(creditsImage);
        creditsView.setFitWidth(1024);
        creditsView.setFitHeight(576);
        creditsView.setPreserveRatio(false); // stretch to cover entire screen

        root.setCenter(creditsView);

        // Back button (small, top-left corner)
        Button backButton = new Button("back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> stage.setScene(previous));

        HBox topBar = new HBox(backButton);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setStyle("-fx-padding: 10;");

        root.setTop(topBar);

        Scene scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.creditsScene = scene;
    }

    public Scene getScene() {
        return this.creditsScene;
    }
}
