package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    String id;
    String name;
    String createDate;
    String createdBy;
    String lastUpdated;
    String lastUpdatedBy;
    ObservableList<FirstLevelDivision> firstLevelDivisions = FXCollections.observableArrayList();

    /**  Default Country constructor. */
    public Country() {}

    /** Country constructor. */
    public Country(String id, String name, String createDate, String createdBy, String lastUpdated, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Returns the Country ID. */
    public String getId() {
        return id;
    }

    /** Sets the Country ID */
    public void setId(String id) {
        this.id = id;
    }

    /** Returns the Name of the country. */
    public String getName() {
        return name;
    }

    /** Sets the Name of the country. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns an ObservableList of first level divisions in the country. */
    public ObservableList<FirstLevelDivision> getFirstLevelDivisions() {
        return firstLevelDivisions;
    }

}
