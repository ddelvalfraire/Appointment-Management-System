package model;

public class Customer {

    private Integer id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private String divisionId;

    /** Default Customer constructor. */
    public Customer() {}

    /** Customer constructor. */
    public Customer(Integer id, String name, String address, String postalCode, String phone, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, String divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /** Returns the customer ID. */
    public Integer getId() {
        return id;
    }

    /** Sets the customer ID. */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Returns the customer Name. */
    public String getName() {
        return name;
    }

    /** Sets the customer Name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the customer Address. */
    public String getAddress() {
        return address;
    }

    /** Returns the customer PostalCode. */
    public String getPostalCode() {
        return postalCode;
    }

    /** Returns the customer Phone. */
    public String getPhone() {
        return phone;
    }

    /** Returns the customer DivisionID. */
    public String getDivisionId() {
        return divisionId;
    }

    /** Returns the createDate. */
    public String getCreateDate() {
        return createDate;
    }

    /** Sets the createDate */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /** Returns createdBy. */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Sets createdBy. */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Returns lastUpdate. */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /** Sets lastUpdate. */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Sets lastUpdatedBy. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Returns lastUpdatedBy. */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
}
