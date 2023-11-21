package model;

/**
 * @author Darren Woods
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Parts and Products are stored in observable lists within this class
 */
public class Inventory {

    public static int partID = 0;
    public static int productID = 0;
    private static ObservableList<Parts> partsList = FXCollections.observableArrayList();
    private static ObservableList<Products> productsList = FXCollections.observableArrayList();

    /**
     * @param newPart New part is added to the observable list, "allParts"
     */
    public static void addPart(Parts newPart) {
        partsList.add(newPart);
    }

    /**
     * @param newProduct New product is added to the observable list, "allProducts"
     */
    public static void addProduct(Products newProduct) {
        productsList.add(newProduct);
    }

    /**
     * @param partID FOR loop is used to iterate parts and if part is found, part @return
     * @return null if nothing was found
     * alert if item was not found (error message)
     */
    public static Parts lookupPart(int partID) {
        for (Parts parts : Inventory.getAllParts()) {
            while (parts.getId() == partID) {
                return parts;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR MESSAGE!");
        alert.setHeaderText("Item not found!");
        alert.show();
        return null;
    }

    /**
     * @param productID FOR loop is used to iterate products and if product is found, product @return
     * @return null if nothing was found
     * alert2 if item was not found (error message)
     */
    public static Products lookupProduct(int productID) {
        for (Products products : Inventory.getAllProducts()) {
            while (products.getProductID() == productID)
                return products;
        }
        Alert alert2 = new Alert(Alert.AlertType.ERROR);
        alert2.setTitle("ERROR MESSAGE!");
        alert2.setHeaderText("Item not found!");
        alert2.show();
        return null;
    }

    /**
     * @param partName - filtered parts within ObservableList created with lookupPart
     * @return parts that are filtered
     */
    public static ObservableList<Parts> lookupPart(String partName) {
        ObservableList<Parts> PartName = FXCollections.observableArrayList();
        for (Parts part : partsList) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * @param productName Filtered Products within the ObservableList are created with lookupProduct
     * @return products that are filtered
     */
    public static ObservableList<Products> lookupProduct(String productName) {
        ObservableList<Products> ProductName = FXCollections.observableArrayList();
        for (Products product : productsList) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * @param selectedPart - selectedPart updated
     * @param index        - pass in index (position) where the part is
     */
    public static void updatePart(int index, Parts selectedPart) {
        partsList.set(index, selectedPart);
    }

    /**
     * @param index           - pass in index (position) where the product is
     * @param selectedProduct - selectedProduct updated
     */
    public static void updateProduct(int index, Products selectedProduct) {
        productsList.set(index, selectedProduct);
    }

    /**
     * Use of boolean removes the selectedPart
     *
     * @param selectedPart - delete selectedPart
     */
    public static boolean deletePart(Parts selectedPart) {
        if (partsList.contains(selectedPart)) {
            partsList.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Use of boolean removes the selectedProduct
     *
     * @param selectedProduct - delete selectedProduct
     */
    public static boolean deleteProduct(Products selectedProduct) {
        if (productsList.contains(selectedProduct)) {
            productsList.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return allParts
     * Gets/return all the parts list
     */
    public static ObservableList<Parts> getAllParts() {
        return partsList;
    }

    /**
     * @return allProducts
     * Gets/return all the products list
     */
    public static ObservableList<Products> getAllProducts() {
        return productsList;
    }

    public static int getNewPartID() {
        return ++partID;
    }

    public static int getNewProdID() {
        return ++productID;
    }
}