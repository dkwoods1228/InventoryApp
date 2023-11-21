package woods.inventoryapp;

/**
 * @author Darren Woods
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

    /**Class creates the program and places data within the tables.
     * <br><br>
     *<p>
     *<b>RUNTIME ERROR</b>: When clicking the In-House and Outsourced radio buttons, a runtime error occurred.
        *Originally, my label for switching between In-House and Outsourced was "machineorComp", which was not accepted and led to the runtime error.
        *Therefore, I altered the label name to "MachineOrComp", which was recognized and accepted.
        *</p>
        * <br>
     * <p>
     * <b>LOGICAL ERROR</b>: I received the error message, "unreported exception java.io.IOException" when clicking the Add button for the Parts table.
        * To fix this issue, I caught the exception by adding/throwing the exception with "throws IOException"
        * </p>
        * <br>
     * <p>
     * <b>FUTURE ENHANCEMENT</b>: To improve the program for its next version, I would incorporate a feature that
        * shows price increase/decrease percentages after modifications of parts/products occur. Having a more in-depth
        * look into the history of differences after modifications in general would be a nice feature.
        * </p>
        */
public class Main extends Application {

    /**
     * Links/connects to the Main.fxml file to ensure the program launches.
     * @param primaryStage Stage that FX passes in.
     * @throws IOException Occurs when the main.fxml file is not found.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 480);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Loads the inputted data into the program and launches the program.
     * @param args - Line arguments within tables
     */
    public static void main(String[] args) {
        Parts comforter = new InHouseParts(1, "Comforter", 49.99, 10, 4, 20, 1);
        Inventory.addPart(comforter);
        Parts sheets = new InHouseParts(2, "Sheets", 23.99, 25, 7, 35, 2);
        Inventory.addPart(sheets);
        Parts pillows = new InHouseParts(3, "Pillows", 19.99, 30, 5, 55, 3);
        Inventory.addPart(pillows);
        Parts queen = new OutsourcedParts(4, "Queen Size Mattress", 109.99, 5, 1, 10, "Mattress City");
        Inventory.addPart(queen);
        Parts king = new OutsourcedParts(5, "King Size Mattress", 139.99, 8, 1, 10, "Mattress City");
        Inventory.addPart(king);
        Parts frame = new OutsourcedParts(6, "Bed Frame", 149.99, 10, 1, 20, "Bed Frame Boulevard");
        Inventory.addPart(frame);

        Products Bed1 = new Products(1, "Bed #1", 349.99, 8, 1, 20);
        Inventory.addProduct(Bed1);
        Products Bed2 = new Products(2, "Bed #2", 399.99, 12, 1, 18);
        Inventory.addProduct(Bed2);
        Products Bed3 = new Products(3, "Bed #3", 569.99, 4, 1, 8);
        Inventory.addProduct(Bed3);
        Products Bed4 = new Products(4, "Bed #4", 729.99, 3, 1, 6);
        Inventory.addProduct(Bed4);
        launch();
    }
}