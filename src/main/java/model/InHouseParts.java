package model;

/**
 * Class that holds information on information stored within In-House parts.
 */
public class InHouseParts extends Parts {
    /**
     * Creation of the machineID, specifically within In-House Parts
     */
    private int machineID;

    public InHouseParts(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Getter for machineID
     * @return machineID
     */
    public int getMachineID() {

        return machineID;
    }

    /**
     * Setter for machineID
     */
    public void setMachineID(int machineID) {

        this.machineID = machineID;
    }
}
