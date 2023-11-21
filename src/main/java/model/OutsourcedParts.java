package model;
/**
 * Class that holds information on information stored within Outsourced parts.
 */
public class OutsourcedParts extends Parts {
    /**
     * Creation of the companyName, specifically within the outsourced parts.
     */
    private String companyName;

    /**
     * Lists information/objects within OutsourcedParts
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutsourcedParts(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for companyName
     * @return CompanyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
