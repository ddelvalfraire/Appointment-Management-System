package model;

public class FirstLevelDivision {
    String id;
    String division;
    String createDate;
    String createdBy;
    String lastUpdate;
    String lastUpdatedBy;
    String countryId;

    /** FirstLevelDivision constructor. */
    public FirstLevelDivision(String id, String division, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, String countryId) {
        this.id = id;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /** Returns the first level division ID. */
    public String getId() {
        return id;
    }

    /** Sets the first level division ID. */
    public void setId(String id) {
        this.id = id;
    }

    /** Returns the first level division ID. */
    public String getDivision() {
        return division;
    }

}
