package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouseParts;
import model.Inventory;
import model.OutsourcedParts;
import java.io.IOException;
import java.util.Optional;

import static model.Inventory.getAllParts;

/**Class used to control the addition of a part to the parts table.*/
public class AddPartController {

    @FXML private RadioButton RadioInHouse;
    @FXML private RadioButton RadioOutsourced;
    @FXML private TextField addPartID;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInvent;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMax;
    @FXML private Label MachineOrComp;
    @FXML private TextField addPartMachineID;

    /**
     * When In-House radio button is pressed, the final text field (label) is set to "Machine ID".
     * <p>
     * <b>
     * RUNTIME ERROR: When clicking the In-House and Outsourced radio buttons, a runtime error occurred.
     * Originally, my label for switching between In-House and Outsourced was "machineorComp", which was not accepted and led to the runtime error.
     * Therefore, I altered the label name to "MachineOrComp", which was recognized and accepted.
     * </b>
     * </p>
     * @param actionEvent
     */
    @FXML public void onActionInHouse(ActionEvent actionEvent) {

        MachineOrComp.setText("Machine ID");
    }

    /**
     * When Outsourced radio button is pressed, the final text field (label) is set to "Company Name".
     *
     * @param actionEvent
     */
    @FXML public void onActionOutsourced(ActionEvent actionEvent) {

        MachineOrComp.setText("Company Name");
    }

    /**
     * When pressing the Save Button on the Add Part Menu,
     * it saves the part that the user has inputted and adds the part to the parts table on the main menu.
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void AddPartSaveButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            String companyName;
            int machineID = 0;
            int invent = Integer.parseInt((addPartInvent.getText()));
            int newID = getNewID();
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            String name = addPartName.getText();

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Value Error");
                alert.setContentText("Maximum value should be greater than minimum value.");
                alert.showAndWait();
                return;

            } else if (invent < min || max < invent) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Value Error");
                alert.setContentText("Inventory value must be within the min and max values.");
                alert.showAndWait();
                return;
            }
            //newID implemented here to ensure id increments by 1.
            if (RadioOutsourced.isSelected()) {
                companyName = addPartMachineID.getText();
                OutsourcedParts addPart = new OutsourcedParts(newID, name, price, invent, min, max, companyName);
                Inventory.addPart(addPart);
            }
            //newID implemented here to ensure id increments by 1.
            if (RadioInHouse.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                InHouseParts addPart = new InHouseParts(newID, name, price, invent, min, max, machineID);
                Inventory.addPart(addPart);
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unusable Values");
            alert.setContentText("Inventory value must be within the min and max values.");
            alert.show();


        }
    }

    /**
     * When pressing the cancel button, the program returns the user to the main menu.
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void AddPartCancelButtonPressed(ActionEvent actionEvent) throws IOException {
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
     * The newID increments by 1.
     *
     * @return newID
     */
    private int getNewID() {
        int newID = 1;
        for (int i = 0; i < getAllParts().size(); i++) {
            newID++;
        }
        return newID;
    }
}
