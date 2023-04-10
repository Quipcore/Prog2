package main;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.graph.Edge;

import java.util.concurrent.atomic.AtomicBoolean;

public class Popup {

    public static void showConnection(Pin p0, Pin p1, Edge<Pin> edge) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connection");
        dialog.setHeaderText(String.format("Connection from %s to %s", p0, p1));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setText(edge.getName());
        nameField.setEditable(false);

        TextField timeField = new TextField();
        timeField.setText(String.valueOf(edge.getWeight()));
        timeField.setEditable(false);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Time:"), 0, 1);
        grid.add(timeField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
    }

    public static Pair<String, Integer> newConnection(Pin p0, Pin p1) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connection");
        dialog.setHeaderText(String.format("Connection from %s to %s", p0, p1));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        TextField timeField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Time:"), 0, 1);
        grid.add(timeField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        AtomicBoolean isCanceled = new AtomicBoolean(false);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.getButtonData().isDefaultButton()) {
                return new Pair<>(nameField.getText(), timeField.getText());
            }
            if (dialogButton.getButtonData().isCancelButton()) {
                isCanceled.set(true);
                return null;
            }


            return null;
        });

        Pair<String, String> dialogPair = dialog.showAndWait().orElse(null);
        if (isCanceled.get()) {
            return new Pair<>("", -1);
        }
        if (dialogPair == null
                || dialogPair.getKey().equals("")
                || dialogPair.getValue().equals("")
                || !dialogPair.getValue().matches("[0-9]+")) {
            return null;
        }

        return new Pair<>(dialogPair.getKey(), Integer.parseInt(dialogPair.getValue()));

    }

    public static String changeConnection(Pin p0, Pin p1, Edge<Pin> edge) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Connection");
        dialog.setHeaderText(String.format("Connection from %s to %s", p0, p1));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setText(edge.getName());
        nameField.setEditable(false);
        TextField timeField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Time:"), 0, 1);
        grid.add(timeField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> timeField.getText());
        return dialog.showAndWait().orElse(null);
    }

    public static String newPlace() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name");
        dialog.setHeaderText("");
        dialog.setContentText("Name of place:");
        return dialog.showAndWait().orElse(null);
    }

    public static void findPath(Pin p0, Pin p1, String path) {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("Message");
        dialog.setHeaderText("The path from " + p0 + " to " + p1);

        TextArea textArea = new TextArea(path);
        textArea.setEditable(false);
        dialog.getDialogPane().setContent(textArea);
        dialog.showAndWait();
    }

    public static boolean unsavedChanges() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("You have unsaved changes, continue anyway?");

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        alert.showAndWait();

        if (alert.getResult() == buttonTypeCancel) {
            return true;
        } else if (alert.getResult() == buttonTypeOk) {
            return false;
        }

        return false;
    }

    public static void error(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }
}
