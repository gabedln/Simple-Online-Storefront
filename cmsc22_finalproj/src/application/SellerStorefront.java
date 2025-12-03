package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import product.Product;
import user.Seller;

import java.util.ArrayList;

public class SellerStorefront {

    private Scene scene;
    private VBox productBox;
    private Seller seller;

    public SellerStorefront(Stage stage, Scene prevScene, Seller seller) {
        this.seller = seller;

        // Set background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/application/images/background_seller_storefront.png"));
        BackgroundImage bgImage = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );

        BorderPane root = new BorderPane();
        root.setBackground(new Background(bgImage));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        // Limit scrollbar to not reach top
        StackPane scrollWrapper = new StackPane();
        scrollWrapper.setStyle("-fx-padding: 125 0 0 0;"); // Add top padding to prevent scrollbar from reaching top

        productBox = new VBox(25);
        productBox.setAlignment(Pos.TOP_CENTER);
        productBox.setStyle("-fx-padding: 20 40 40 40; -fx-background-color: transparent;");

        scrollPane.setContent(productBox);
        scrollWrapper.getChildren().add(scrollPane);

        refreshProducts(stage, prevScene);
        root.setCenter(scrollWrapper);


        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 15;");
        root.setBottom(bottomBox);

        scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    }

    private void refreshProducts(Stage stage, Scene prevScene) {
        productBox.getChildren().clear();

        ArrayList<Product> visible = seller.getProductList();
        ArrayList<Product> hidden = seller.getHiddenList();

        if (!visible.isEmpty()) {
            Label visibleTitle = new Label("Your Products");
            visibleTitle.getStyleClass().add("welcome-text");
            productBox.getChildren().add(visibleTitle);
        }

        for (Product p : visible) {
            productBox.getChildren().add(createProductCard(p, stage, prevScene, true));
        }

        if (!hidden.isEmpty()) {
            Label hiddenTitle = new Label("Hidden Products");
            hiddenTitle.getStyleClass().add("welcome-text");
            productBox.getChildren().add(hiddenTitle);
        }

        for (Product p : hidden) {
            productBox.getChildren().add(createProductCard(p, stage, prevScene, false));
        }

        // Add Product button styled via CSS
        Button addProductBtn = new Button("Add Product");
        addProductBtn.setMinWidth(350);
        addProductBtn.getStyleClass().add("add-button");
        addProductBtn.setOnAction(e -> {
            AddProduct addProduct = new AddProduct(stage, scene, seller);
            stage.setScene(addProduct.getScene());
        });

        VBox addBtnContainer = new VBox(addProductBtn);
        addBtnContainer.setAlignment(Pos.CENTER);
        addBtnContainer.setStyle("-fx-padding: 30;");
        productBox.getChildren().add(addBtnContainer);
    }

    private HBox createProductCard(Product product, Stage stage, Scene prevScene, boolean isVisible) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMaxWidth(950);
        card.setStyle("-fx-padding: 20; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 20; -fx-background-color: #F5DEB3; -fx-background-radius: 20;");

        String imgPath = "/application/images/" + categoryToImage(product.getCategory());
        ImageView img;
        try {
            img = new ImageView(new Image(getClass().getResourceAsStream(imgPath)));
        } catch (Exception e) {
            img = new ImageView();
        }
        img.setFitHeight(80);
        img.setFitWidth(80);
        img.setPreserveRatio(true);

        VBox infoBox = new VBox(8);
        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-family: 'Five by nine'; -fx-font-size: 32px; -fx-text-fill: black; -fx-font-weight: bold;");
        Label detailsLabel = new Label("₱" + String.format("%.2f", product.getPrice()) + " | Stock: " + product.getStock() + " | " + product.getCategory());
        detailsLabel.setStyle("-fx-font-family: 'Five by nine'; -fx-font-size: 24px; -fx-text-fill: black;");
        infoBox.getChildren().addAll(nameLabel, detailsLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Hide/Unhide button styled via CSS
        Button toggleBtn = new Button(isVisible ? "Hide" : "Unhide");
        toggleBtn.setMinWidth(150);
        toggleBtn.getStyleClass().add("back-button");
        toggleBtn.setOnAction(e -> {
            if (isVisible) seller.hideProduct(product);
            else seller.unhideProduct(product);
            refreshProducts(stage, prevScene);
        });

        // Up/Down buttons styled via CSS
        Button upBtn = new Button("↑");
        Button downBtn = new Button("↓");
        upBtn.setMinWidth(70);
        downBtn.setMinWidth(70);
        upBtn.getStyleClass().add("back-button");
        downBtn.getStyleClass().add("back-button");

        upBtn.setOnAction(e -> {
            ArrayList<Product> list = seller.getProductList();
            int idx = list.indexOf(product);
            if (idx > 0) {
                list.remove(product);
                list.add(idx - 1, product);
                refreshProducts(stage, prevScene);
            }
        });
        downBtn.setOnAction(e -> {
            ArrayList<Product> list = seller.getProductList();
            int idx = list.indexOf(product);
            if (idx < list.size() - 1) {
                list.remove(product);
                list.add(idx + 1, product);
                refreshProducts(stage, prevScene);
            }
        });

        buttonBox.getChildren().add(toggleBtn);
        if (isVisible) {
            buttonBox.getChildren().addAll(upBtn, downBtn);
        }

        card.getChildren().addAll(img, infoBox, buttonBox);

        if (!isVisible) {
            card.setStyle("-fx-opacity: 0.7; -fx-padding: 20; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 20; -fx-background-color: #F5DEB3; -fx-background-radius: 20;");
        }

        return card;
    }

    private String categoryToImage(String category) {
        switch (category.toLowerCase()) {
            case "beverages": return "beverages.png";
            case "canned goods": return "cannedgoods.png";
            case "cleaning": return "cleaningtools.png";
            case "condiments": return "condiments.png";
            case "health care": return "healthcare.png";
            default: return "default.png";
        }
    }

    public Scene getScene() {
        return scene;
    }
}