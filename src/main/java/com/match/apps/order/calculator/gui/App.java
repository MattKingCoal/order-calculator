package com.match.apps.order.calculator.gui;

import java.util.ArrayList;
import java.util.List;

import com.match.apps.order.calculator.product.FileProductSource;
import com.match.apps.order.calculator.product.Product;
import com.match.apps.order.calculator.product.ProductSource;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private ProductSource source;
    private Label totalValueLabel;

    public App() {
        source = new FileProductSource(System.getProperty("config.dir", "conf"), "products.txt");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Order calculator");

        List<HBox> sunItemHBoxes = new ArrayList<>();
        List<HBox> thursItemHBoxes = new ArrayList<>();
        for (Product product : source.loadProducts()) {
            if (product.isActive()) {
                sunItemHBoxes.add(createItemHBox(product, createIncrementButton(), createDecrementButton()));
                thursItemHBoxes.add(createItemHBox(product, createIncrementButton(), createDecrementButton()));
            }
        }

        // List<HBox> hBoxes = createRandomlyNamedHBoxesForTesting(20);

        Button totalButton = new Button();
        totalButton.setText("What's the damage?");
        totalButton.setOnAction(e -> {
            VBox vBox = (VBox) totalButton.getParent().getParent();
            ObservableList<Node> itemNodes = vBox.getChildren();
            // We don't want the last one as it's the total hBox
            double cartTotal = 0;
            for (int i = 0; i < itemNodes.size() - 1; i++) {
                Node node = itemNodes.get(i);
                if (node instanceof Label) {
                    continue;
                }
                HBox hBox = (HBox) node;
                Label firstLabel = (Label) hBox.getChildren().get(0);
                if ("Product".equals(firstLabel.getText())) {
                    continue;
                }
                Label itemCountLabel = (Label) hBox.getChildren().get(4);
                int itemCount = Integer.parseInt(itemCountLabel.getText());
                if (itemCount == 0) {
                    continue;
                }
                Label priceLabel = (Label) hBox.getChildren().get(1);
                double price = Double.parseDouble(priceLabel.getText().substring(1));
                double itemTotal = itemCount * price;
                cartTotal += itemTotal;
            }

            // Set the total box label to the calculated cart Total
            totalValueLabel.setText("€" + String.format("%.2f", cartTotal));

            totalButton.setStyle(null);
        });

        HBox tueHheaderBox = createHeaderHBox();
        HBox thursHeaderBox = createHeaderHBox();

        HBox totalBox = new HBox(10);
        totalValueLabel = new Label("----");
        totalBox.getChildren().addAll(totalButton, totalValueLabel);
        totalBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(10);
        vbox.getChildren().add(new Label("Sunday"));
        vbox.getChildren().add(tueHheaderBox);
        vbox.getChildren().addAll(sunItemHBoxes);
        vbox.getChildren().add(new Label("Thursday"));
        vbox.getChildren().add(thursHeaderBox);
        vbox.getChildren().addAll(thursItemHBoxes);
        vbox.getChildren().add(totalBox);
        vbox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 400, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private HBox createHeaderHBox() {
        HBox tueHheaderBox = new HBox(10);
        tueHheaderBox.getChildren().addAll(createHeaderLabel("Product"), createHeaderLabel("Price"),
                createHeaderLabel("Add"), createHeaderLabel("Remove"), createHeaderLabel("Count"));
        tueHheaderBox.setAlignment(Pos.CENTER);
        return tueHheaderBox;
    }

    private Label createHeaderLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private HBox createItemHBox(Product product, Button iButton, Button dButton) {
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(new Label(product.getName()),
                new Label("€" + String.format("%.2f", product.getPrice())), iButton, dButton, new Label("0"));
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private Button createIncrementButton() {
        Button incrementButton = new Button();
        incrementButton.setText("+");
        incrementButton.setOnAction(e -> {
            resetTotalSection(incrementButton);
            Label label = (Label) incrementButton.getParent().getChildrenUnmodifiable().get(4);
            int count = Integer.parseInt(label.getText());
            count++;
            label.setText(String.valueOf(count));
        });

        return incrementButton;
    }

    private void resetTotalSection(Button button) {
        ObservableList<Node> nodesinVbox = button.getParent().getParent().getChildrenUnmodifiable();
        int size = nodesinVbox.size();
        HBox totalHbox = (HBox) nodesinVbox.get(size - 1);
        Button totalButton = (Button) totalHbox.getChildren().get(0);
        // totalButton.setStyle("-fx-background-color: #ff0000; ");
        totalButton.setStyle("-fx-font-weight: bold;");
        totalValueLabel.setText("----");
    }

    private Button createDecrementButton() {
        Button decrementButton = new Button();
        decrementButton.setText("-");
        decrementButton.setOnAction(e -> {
            resetTotalSection(decrementButton);
            Label label = (Label) decrementButton.getParent().getChildrenUnmodifiable().get(4);
            int count = Integer.parseInt(label.getText());
            if (count > 0) {
                count--;
                label.setText(String.valueOf(count));
            }
        });

        return decrementButton;
    }
}
