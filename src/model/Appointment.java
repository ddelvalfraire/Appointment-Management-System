package model;

public class Appointment {

    private Integer id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private String createDate;
    private String createBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private Integer customerId;
    private Integer userId;
    private Integer contactId;

    /** Default appointment constructor. */
    public Appointment() {};

    /** Appointment constructor. */
    public Appointment(Integer id, String title, String description, String location,
                       String type, String start, String end, String createDate,
                       String createBy, String lastUpdate, String lastUpdatedBy,
                       Integer customerId, Integer userId, Integer contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** Returns the appointment ID. */
    public Integer getId() {
        return id;
    }
    /** Sets the appointment ID. */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Returns the Title of the appointment. */
    public String getTitle() {
        return title;
    }

    /** Returns the Description of the appointment. */
    public String getDescription() {
        return description;
    }

    /** Returns the Location of the appointment. */
    public String getLocation() {
        return location;
    }

    /** Returns the Type of the appointment. */
    public String getType() {
        return type;
    }

    /** Returns the Start time of the appointment. */
    public String getStart() {
        return start;
    }

    /** Returns the End time of the appointment. */
    public String getEnd() {
        return end;
    }

    /** Returns the CustomerID of the appointment. */
    public Integer getCustomerId() {
        return customerId;
    }

    /** Returns the UserID of the appointment. */
    public Integer getUserId() {
        return userId;
    }

    /** Returns the ContactID of the appointment */
    public Integer getContactId() {
        return contactId;
    }

    /** Returns createBy. */
    public String getCreateBy() {
        return createBy;
    }

    /** Sets createBy. */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /** Returns createDate. */
    public String getCreateDate() {
        return createDate;
    }

    /** Sets createDate. */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /** Returns lastUpdatedBy. */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Sets lastUpdatedBy. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Returns lastUpdate. */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /** Sets lastUpdate. */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
