package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Parts;
import model.Products;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static model.Inventory.getAllProducts;

/**Class used to control the addition of a product to the products table.*/
public class AddProductController implements Initializable {

    /**ObservableList used in related to associatedParts*/
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    @FXML private TextField addProdID;
    @FXML private TextField addProdName;
    @FXML private TextField addProdInvent;
    @FXML private TextField addProdPrice;
    @FXML private TextField addProdMin;
    @FXML private TextField addProdMax;
    @FXML private TextField addSearchProduct;
    @FXML private TableView<Parts> addProdTable;
    @FXML private TableColumn<Parts, Integer> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, Integer> partInventColumn;
    @FXML private TableColumn<Parts, Double> partPriceColumn;
    @FXML private TableColumn<Products, Integer> associatedPartIDColumn;
    @FXML private TableColumn<Products, String> associatedPartNameColumn;
    @FXML private TableColumn<Products, Integer> associatedPartInventColumn;
    @FXML private TableColumn<Products, Double> associatedPartPriceColumn;
    @FXML private TableView<Parts> associatedProdTable;

    /**
     * Allows user to search for a product ID or name within the table.
     * @param actionEvent
     */
    @FXML public void addProdSearchField(ActionEvent actionEvent) {
        String search = addSearchProduct.getText();
        ObservableList<Parts> finalResult = Inventory.lookupPart(search);
        try {
            while (finalResult.size() == 0) {
                int partID = Integer.parseInt(search);
                finalResult.add(Inventory.lookupPart(partID));
            } addProdTable.setItems(finalResult);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Product could not be found.");
            alert.showAndWait();
        }
    }

    /**
     * Part is added to the associatedProdTable/observable list
     * @param actionEvent
     */
    @FXML public void addProdButtonPushed(ActionEvent actionEvent) {
        Parts clickedPart = addProdTable.getSelectionModel().getSelectedItem();
        if (clickedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please select a part from the list");
            alert.showAndWait();
            return;
        }
            else if (!associatedParts.contains(clickedPart)) ;
        {
            associatedParts.add(clickedPart);
            associatedProdTable.setItems(associatedParts);
        }
    }

    /**
     * Removes the part that was added to the associatedProdTable
     * @param actionEvent
     */
    @FXML public void addProdRemoveAssociatedPartButtonPushed(ActionEvent actionEvent) {
        Parts clickedPart = associatedProdTable.getSelectionModel().getSelectedItem();
        if (clickedPart == null) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setContentText("You must select a part from the list before removing.");
            alert2.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to remove this associated part?");
            Optional<ButtonType> finalResult = alert.showAndWait();

            if (finalResult.isPresent() && finalResult.get() == ButtonType.OK) {
                associatedParts.remove(clickedPart);
                associatedProdTable.setItems(associatedParts);
            }
        }
    }
    /**
     * When pressing the Save Button on the Add Product Menu,
     * it saves the product that the user has inputted and adds the product to the products table on the main menu.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void addProdSaveButtonPushed(ActionEvent actionEvent) throws IOException {
        String name = addProdName.getText();
        int newProdID = getNewProdID();
        int invent = Integer.parseInt(addProdInvent.getText());
        double price = Double.parseDouble(addProdPrice.getText());
        int min = Integer.parseInt(addProdMin.getText());
        int max = Integer.parseInt(addProdMax.getText());

        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Value Error");
            alert.setContentText("Maximum value should be greater than minimum value.");
            alert.showAndWait();
            return;
        }
        if (invent > max || invent < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Value Error");
            alert.setContentText("Inventory value must be within the min and max values.");
            alert.showAndWait();
            return;

        }
        Products prod = new Products(newProdID, name, price, invent, min, max);
       for (Parts part: associatedParts) {
           if (part != associatedParts)
               prod.addAssociatedPart(part);
       }
       getAllProducts().add(prod);

       Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
       Object scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
       stage.setScene(new Scene((Parent) scene));
       stage.show();

    }
    /**
     * The newProdID increments by 1.
     * @return newProdID
     */
    private int getNewProdID() {
        int newProdID = 1;
        for (int i = 0; i < getAllProducts().size(); i++) {
            newProdID++;
        }
        return newProdID;
    }
    /**
     *When pressing the cancel button, the program returns the user to the main menu.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void addProdCancelButtonPushed(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes made will be deleted and you will return to the main menu. Do you wish to continue?");
        Optional<ButtonType> finalResult = alert.showAndWait();
        if (finalResult.isPresent() && finalResult.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(root);
            Stage returnToMain = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            returnToMain.setScene(scene);
            returnToMain.show();
        }
    }

    /**
     * Populates the prodTable and associatedProdTable
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProdTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedProdTable.setItems(associatedParts);
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
