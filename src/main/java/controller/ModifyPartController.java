package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Optional;

/**Class used to modify selected parts that are stored within the parts table.*/
public class ModifyPartController {

    @FXML private RadioButton ModifyPartRadioInHouse;
    @FXML private RadioButton ModifyPartRadioOutsourced;
    @FXML private TextField modifyPartID;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInvent;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMin;
    @FXML private TextField modifyPartMax;
    @FXML private Label MachineOrComp;
    @FXML private TextField modifyPartMachineID;

    /** stage used to load FXML loader */
    private Stage stage;
    /** selectedPart within Parts is used */
    public Parts selectedPart;
    /** Sets indexValue to 0 */
    private int indexValue = 0;

    /**
     * After modifying a part, all the values are set within (sent to) the main menu.
     * @param indexSelected
     * @param selectedPart
     */
    @FXML public void setParts(int indexSelected, Parts selectedPart) {
        indexValue = indexSelected;
        this.selectedPart = selectedPart;
        if (selectedPart instanceof InHouseParts) {
            ModifyPartRadioInHouse.setSelected(true);
            modifyPartMachineID.setText(String.valueOf(((InHouseParts) selectedPart).getMachineID()));
        } else {
            ModifyPartRadioOutsourced.setSelected(true);
            modifyPartMachineID.setText(((OutsourcedParts) selectedPart).getCompanyName());
        }
        modifyPartID.setText(String.valueOf(selectedPart.getId()));
        modifyPartName.setText(String.valueOf(selectedPart.getName()));
        modifyPartInvent.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPrice.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMin.setText(String.valueOf(selectedPart.getMin()));
        modifyPartMax.setText(String.valueOf(selectedPart.getMax()));

    }

    /**
     * When In-House radio button is pressed, the final text field (label) is set to "Machine ID".
     * @param actionEvent
     */
    @FXML public void ModifyInHouseButtonPressed(ActionEvent actionEvent) {

        MachineOrComp.setText("Machine ID");
    }

    /**
     * When Outsourced radio button is pressed, the final text field (label) is set to "Company Name".
     * @param actionEvent
     */
    @FXML public void ModifyOutsourcedButtonPressed(ActionEvent actionEvent) {

        MachineOrComp.setText("Company Name");
    }

    /**
     * Saves the part the user has modified and changes the values in the parts table on the main menu.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void ModifyPartSaveButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inventory = Integer.parseInt(modifyPartInvent.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            String companyName;
            int machineID;

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum value should be greater than minimum value.");
                alert.showAndWait();
                return;
            }
            else if (inventory < min || max < inventory) {
               Alert alert2 = new Alert(Alert.AlertType.ERROR, "Inventory value must be within the min and max values.");
               alert2.showAndWait();
               return;
            }

                if (ModifyPartRadioOutsourced.isSelected()) {
                    companyName = modifyPartMachineID.getText();
                    OutsourcedParts newPart = new OutsourcedParts(partID, name, price, inventory, min, max, companyName);
                    Inventory.updatePart(indexValue, newPart);
                }
                if (ModifyPartRadioInHouse.isSelected()) {
                    machineID = Integer.parseInt(modifyPartMachineID.getText());
                    InHouseParts newPart = new InHouseParts(partID, name, price, inventory, min, max, machineID);
                    Inventory.updatePart(indexValue, newPart);
                }
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
        } catch (NumberFormatException e) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR, "There is an input error.");
            alert3.showAndWait();

        }
    }

    /**
     * When cancel button is pushed, user is returned to the main menu.
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void ModifyPartCancelButtonPressed(ActionEvent actionEvent) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes made will be deleted and you will return to the main menu. Do you wish to continue?");
            Optional<ButtonType> finalResult = alert.showAndWait();
            if (finalResult.isPresent() && finalResult.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(root);
            Stage returnToMain = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            returnToMain.setScene(scene);
            returnToMain.show();
        }
    }
}