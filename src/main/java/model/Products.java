package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Class provided to describe objects within Products and its associated table.*/
public class Products {

    /**ObservableList used in relation to associatedParts*/
    public static ObservableList<Parts> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double productPrice;
    private int stock;
    private int min;
    private int max;

    /**
     * Lists information/objects within Products
     * @param productID
     * @param name
     * @param productPrice
     * @param stock
     * @param min
     * @param max
     */
    public Products(int productID, String name, double productPrice, int stock, int min, int max) {
        this.productID = productID;
        this.name = name;
        this.productPrice = productPrice;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**lists information/objects within Products*/
    public Products() {
        this(0, null, 0.00, 0, 0, 0);
    }

    /**
     * @return productID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @param productID - the productID to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name - the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return productPrice
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice - the productPrice to set
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock - the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min - the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max - the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * adds part to observable list, associated parts
     * @param part - add associated part
     */
    public void addAssociatedPart(Parts part) {
        associatedParts.add(part);
    }

    /**
     * Deletes selected part from the associatedParts
     * @param selectedAssociatedPart - deletes
     * @return true to remove
     */
    public static boolean deleteAssociatedParts(Parts selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * @return associatedParts
     */
    public ObservableList<Parts> getAllAssociatedParts() {
        return associatedParts;
    }

}
