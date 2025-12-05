package application;

import java.util.List;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.TransactionHistory;
import user.User;

public class TransactionScreen {

    private Scene scene;

    @SuppressWarnings("unchecked")
    public TransactionScreen(Stage stage, Scene previousScene, User currentUser, List<TransactionHistory> historyList) {
        BorderPane root = new BorderPane();
        
        VBox topBar = createTopBar(stage, previousScene, currentUser);
        root.setTop(topBar);

        Label titleLabel = new Label("Transaction Log");
        titleLabel.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: black;");

        TableView<TransactionHistory> table = new TableView<>();
        
        table.setPlaceholder(new Label("")); 
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<TransactionHistory, String> userCol = new TableColumn<>("User's name");
        userCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getBuyer().getDisplayName())
        );
        userCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);

        TableColumn<TransactionHistory, String> prodCol = new TableColumn<>("Product name");
        prodCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProduct().getName())
        );
        prodCol.setMaxWidth(1f * Integer.MAX_VALUE * 35);

        TableColumn<TransactionHistory, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setMaxWidth(1f * Integer.MAX_VALUE * 10);

        TableColumn<TransactionHistory, Float> priceCol = new TableColumn<>("Original price");
        priceCol.setCellValueFactory(cellData -> 
            new SimpleFloatProperty(cellData.getValue().getProduct().getPrice()).asObject()
        );
        priceCol.setMaxWidth(1f * Integer.MAX_VALUE * 15);

        TableColumn<TransactionHistory, Float> totalCol = new TableColumn<>("Discounted price");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        totalCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);

        table.getColumns().addAll(userCol, prodCol, qtyCol, priceCol, totalCol);

        if (historyList != null) {
            ObservableList<TransactionHistory> data = FXCollections.observableArrayList(historyList);
            table.setItems(data);
        }

        table.getStyleClass().add("transaction-table");

        VBox centerContent = new VBox(15, titleLabel, table);
        VBox.setVgrow(table, Priority.ALWAYS);
        centerContent.setPadding(new Insets(10, 80, 50, 80));
        centerContent.setAlignment(Pos.TOP_CENTER);
        
        root.setCenter(centerContent);

        this.scene = new Scene(root, 1024, 576);
        this.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        root.getStyleClass().add("transaction-screen"); 
    }

    public Scene getScene() { return this.scene; }

    private VBox createTopBar(Stage stage, Scene prev, User user) {
        //Create Icons
        Button transBtn = createIcon("transaction_icon.png");
        Button homeBtn = createIcon("home_icon.png");
        Button voucherBtn = createIcon("voucher_icon.png");
        Button wishBtn = createIcon("wishlist_icon.png");
        Button cartBtn = createIcon("cart_icon.png");

        ImageView userView = new ImageView(new Image(getClass().getResourceAsStream("/application/images/user_icon.png")));
        userView.setFitHeight(45); userView.setFitWidth(45);
        Button userBtn = new Button("", userView);
        
        userBtn.setStyle("-fx-background-color: transparent; -fx-padding: 0 0 0 325;");

        userBtn.setOnMouseClicked(e -> {
            UserInformation userInfo = new UserInformation(stage, this.scene, user);
            stage.setScene(userInfo.getScene());
        });

        //Actions
        homeBtn.setOnMouseClicked(e -> stage.setScene(prev));

        HBox icons = new HBox(18, transBtn, homeBtn, voucherBtn, wishBtn, cartBtn);
        VBox topIcons = new VBox(10, userBtn, icons);
        topIcons.setStyle("-fx-padding: 5 0 0 630");
        
        return topIcons;
    }

    private Button createIcon(String name) {
        Button btn = new Button();
        try {
            ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/application/images/" + name)));
            view.setFitHeight(45); view.setFitWidth(45);
            btn.setGraphic(view);
            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        } catch(Exception e) { btn.setText(""); }
        return btn;
    }
}
