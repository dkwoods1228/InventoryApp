package controller;

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

/**Class holds the information/UI of the main menu, which is used to connect to the Inventory model and links to additional menus.*/
public class MainController implements Initializable {

    @FXML private TableView<Parts> partsTable;
    @FXML private TableColumn<Parts, Integer> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, Integer> partInventColumn;
    @FXML private TableColumn<Parts, Double> partPriceColumn;
    @FXML private TextField searchPart;
    @FXML private Button addPartBut;
    @FXML private Button modifyPartBut;
    @FXML private Button deletePartBut;

    @FXML private TableView<Products> productsTable;
    @FXML private TableColumn<Products, Integer> prodIDColumn;
    @FXML private TableColumn<Products, String> prodNameColumn;
    @FXML private TableColumn<Products, Integer> prodInventColumn;
    @FXML private TableColumn<Products, Double> prodPriceColumn;
    @FXML private TextField searchProduct;
    @FXML private Button addProdBut;
    @FXML private Button modifyProdBut;
    @FXML private Button deleteProdBut;
    @FXML private Button exitMainBut;


    /**
     * Populates the parts and products tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //parts table
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //products table
        productsTable.setItems(Inventory.getAllProducts());
        prodIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInventColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));


    }
    private Parent scene;
    Stage stage;

    /**
     * Loads/connects to the Add Part menu (AddPart.fxml), which allows user to add a part to the partsTable/Inventory.
     * <p>
     * <b>
     * LOGICAL ERROR: I received the error message, "unreported exception java.io.IOException" when clicking the Add button for the Parts table.
     * To fix this issue, I caught the exception by adding/throwing the exception with "throws IOException"
     *</b>
     * </p>
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void MainAddPartButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setTitle("Add Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads/connects to the Modify Part menu (ModifyPart.fxml) to allow user to make modifications to the partsTable.
     * <br>
     * <p>
     * <b>FUTURE ENHANCEMENT: To improve the program for its next version, I would incorporate a feature that
     *  shows price increase/decrease percentages after modifications of parts/products occur. Having a more in-depth
     * look into the history of differences after modifications in general would be a nice feature.</b>
     * </p>
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void MainModifyPartButtonPushed(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loads = new FXMLLoader();
            loads.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loads.load();

            ModifyPartController ModifyPartControl = loads.getController();
            ModifyPartControl.setParts(partsTable.getSelectionModel().getSelectedIndex(), partsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loads.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select a part to modify.");
            alert.show();
        }
    }

    /**
     * Delete button used to delete a part that was clicked/selected by the user.
     * Asks user to confirm deletion before deleting selected part.
     * @param actionEvent
     *
     */
    @FXML public void MainDeletePartButtonPushed(ActionEvent actionEvent) throws IOException {
        Parts clickedPart = partsTable.getSelectionModel().getSelectedItem();

        if (clickedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Value Chosen");
            alert.setContentText("You must select a part to delete.");
            alert.show();

            } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure you would like to delete this part?");
            Optional<ButtonType> finalResult = alert.showAndWait();

            if (finalResult.isPresent() && finalResult.get() == ButtonType.OK) {
                Inventory.deletePart(clickedPart);
            }
        }
    }

    /**
     * Links to AddProduct.fxml to add a product to the products table.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void MainAddProdButtonPushed(ActionEvent actionEvent) throws IOException {
        Parent addProd = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Scene scene = new Scene(addProd);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Links to the ModifyProduct.fxml, which allows user to make modifications to the products table.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void MainModifyProdButtonPushed(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loads = new FXMLLoader();
            loads.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loads.load();

            ModifyProductController ModifyProdControl = loads.getController();
            ModifyProdControl.setProd(productsTable.getSelectionModel().getSelectedIndex(), productsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loads.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select a product to modify.");
            alert.show();
        }
    }

    /**
     * Delete button used to delete a product that was clicked/selected by the user.
     * Asks user to confirm deletion before deleting selected product.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void MainDeleteProdButtonPushed(ActionEvent actionEvent) throws IOException {
           Products clickedProd = productsTable.getSelectionModel().getSelectedItem();
           if (clickedProd == null) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error");
               alert.setHeaderText("Confirm Deletion");
               alert.setContentText("You must select a product to delete.");
               alert.show();

           } else {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Confirmation");
               alert.setHeaderText("Confirm Deletion");
               alert.setContentText("Are you sure you would like to delete this product?");
               Optional<ButtonType> finalResult = alert.showAndWait();

               if (finalResult.isPresent() && finalResult.get() == ButtonType.OK) {
                   Products clickedDeletedProd = productsTable.getSelectionModel().getSelectedItem();
                   if (clickedDeletedProd.getAllAssociatedParts().size() > 0) {
                       Alert noDelete = new Alert(Alert.AlertType.ERROR);
                       noDelete.setTitle("Error Dialog");
                       noDelete.setContentText("You must remove the associated part before deleting this product.");
                       noDelete.showAndWait();
                       return;
                   } else {
                       Inventory.deleteProduct(clickedProd);
                   }
               }
           }
    }

    /**
     * Search field used to search for specific parts within the parts table.
     * The user can search via the id number or the name of the part.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void MainSearchPart(ActionEvent actionEvent) throws IOException {
        String search = searchPart.getText();
        ObservableList<Parts> finalResult = Inventory.lookupPart(search);
        try {
            while (finalResult.size() == 0) {
                int partID = Integer.parseInt(search);
                finalResult.add(Inventory.lookupPart(partID));
            }
            partsTable.setItems(finalResult);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Part could not be found. Please try again.");
            alert.showAndWait();
        }
    }

    /**
     * Search field used to search for specific products within the products table.
     * The user can search via the id number or the name of the part.
     * @param actionEvent
     */

    @FXML public void MainSearchProduct(ActionEvent actionEvent) {
        String search = searchProduct.getText();
        ObservableList<Products> finalResult = Inventory.lookupProduct(search);
        try {
            while (finalResult.size() == 0) {
                int prodID = Integer.parseInt(search);
                finalResult.add(Inventory.lookupProduct(prodID));
            }
            productsTable.setItems(finalResult);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Product could not be found. Please try again.");
            alert.showAndWait();
        }
    }

    /**
     * Exit button used to exit the program.
     * Asks user to confirm exiting before actually exiting the program.
     * @param Exit Exit button
     */
    @FXML public void ExitMainButtonPushed(ActionEvent Exit) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to exit this program?");
        alert.showAndWait();
        Stage stage = (Stage) ((Node) Exit.getSource()).getScene().getWindow();
        stage.close();
    }



}