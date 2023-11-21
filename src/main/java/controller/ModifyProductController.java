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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class used to modify selected products that are stored within the products table.*/
public class ModifyProductController implements Initializable {

    /**ObservableList used in relation to associatedParts*/
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    @FXML private TableView<Products> productsTable;
    @FXML private TextField modifyProdID;
    @FXML private TextField modifyProdName;
    @FXML private TextField modifyProdInvent;
    @FXML private TextField modifyProdPrice;
    @FXML private TextField modifyProdMin;
    @FXML private TextField modifyProdMax;
    @FXML private TextField modifySearchProduct;
    @FXML private TableView<Parts> modifyProdTable;
    @FXML private TableColumn<Parts, Integer> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, Integer> partInventColumn;
    @FXML private TableColumn<Parts, Double> partPriceColumn;
    @FXML private TableColumn<Products, Integer> associatedProdIDColumn;
    @FXML private TableColumn<Products, String> associatedProdNameColumn;
    @FXML private TableColumn<Products, Integer> associatedProdInventColumn;
    @FXML private TableColumn<Products, Double> associatedProdPriceColumn;
    @FXML private TableView<Parts> associatedProdTable;

    /**indexValue set to 0*/
    private int indexValue = 0;

    /**
     * Allows user to search for an ID or name within the table.
     * @param actionEvent
     */
    @FXML public void modifyProdSearchField(ActionEvent actionEvent) {
        String search = modifySearchProduct.getText();
        ObservableList<Parts> finalResult = Inventory.lookupPart(search);
        try {
            while (finalResult.size() == 0) {
                int prodID = Integer.parseInt(search);
                finalResult.add(Inventory.lookupPart(prodID));
            }
            modifyProdTable.setItems(finalResult);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("The part could not be found");
            alert.showAndWait();
        }
    }

    /**
     * After modifying a product, all the values are set within (sent to) the main menu.
     * @param indexSelected
     * @param selectedProd
     */
    @FXML public void setProd(int indexSelected, Products selectedProd) {
        indexValue = indexSelected;
        modifyProdID.setText(String.valueOf(selectedProd.getProductID()));
        modifyProdName.setText(String.valueOf(selectedProd.getName()));
        modifyProdInvent.setText(String.valueOf(selectedProd.getStock()));
        modifyProdPrice.setText(String.valueOf(selectedProd.getProductPrice()));
        modifyProdMin.setText(String.valueOf(selectedProd.getMin()));
        modifyProdMax.setText(String.valueOf(selectedProd.getMax()));

        for (Parts parts: selectedProd.getAllAssociatedParts()) {
            associatedParts.add(parts);
        }
    }

    /**
     * Product is added to the associatedProdTable/observable list
     * @param actionEvent
     */
    public void modifyProdAddButtonPushed(ActionEvent actionEvent) {
        Parts clickedPart = modifyProdTable.getSelectionModel().getSelectedItem();
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
     * Removes the part that was added to the associatedProdTable.
     * @param actionEvent
     */
    public void modifyProdRemoveAssociatedPartButtonPushed(ActionEvent actionEvent) {
        Parts clickedPart = associatedProdTable.getSelectionModel().getSelectedItem();
        if (clickedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("You must select a part from the list to remove.");
            alert.showAndWait();
            return;
        } else if (associatedParts.contains(clickedPart)) {
             {
                Products.deleteAssociatedParts(clickedPart);
                associatedParts.remove(clickedPart);
                associatedProdTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Saves the product the user has modified and changes the values in the products table on the main menu.
     * @param actionEvent
     * @throws IOException
     */
    public void modifyProdSaveButtonPushed(ActionEvent actionEvent) throws IOException {
       try {
        int id = Integer.parseInt(modifyProdID.getText());
        String name = modifyProdName.getText();
        int invent = Integer.parseInt(modifyProdInvent.getText());
        double price = Double.parseDouble(modifyProdPrice.getText());
        int min = Integer.parseInt(modifyProdMin.getText());
        int max = Integer.parseInt(modifyProdMax.getText());

        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Value Error");
            alert.setContentText("Maximum value should be greater than minimum value.");
            alert.showAndWait();
            return;
        }
        if (invent > max || invent < min) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Input Value Error");
            alert2.setContentText("Inventory value must be within the min and max values.");
            alert2.showAndWait();
            return;
        }
        //newID increments by 1
        int newID = 1;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getProductID() == newID) ;

        }
        Products newProd = new Products(id, name, price, invent, min, max);
        if (newProd != associatedParts) {
            Inventory.updateProduct(indexValue, newProd);
        }
           for (Parts part : associatedParts) {
            if (part != associatedParts)
                newProd.addAssociatedPart(part);
        }

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    } catch (NumberFormatException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setContentText("Value is unusable");
           alert.showAndWait();
       }
    }

    /**
     * When cancel button is pushed, user is returned to the main menu.
     * @param actionEvent
     * @throws IOException
     */
    public void modifyProdCancelButtonPushed(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes made will be deleted and you will return to the main menu. Do you wish to continue?");
        Optional<ButtonType> finalResult = alert.showAndWait();
        if (finalResult.isPresent() && finalResult.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(root);
            Stage returnToMain = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            returnToMain.setScene(scene);
            returnToMain.show();
        }
    }

    /**
     * Populate the tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProdTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedProdTable.setItems(associatedParts);
        associatedProdIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedProdNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedProdInventColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedProdPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
}