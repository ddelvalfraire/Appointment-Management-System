package model;

public class Contact {
    Integer id;
    String name;
    String email;

    /** Default Contact Constructor. */
    public Contact() {}

    /** Contact Constructor. */
    public Contact(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** Returns the ID of the contact. */
    public Integer getId() {
        return id;
    }

    /** Sets the ID of the contact. */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Returns the Name of the contact. */
    public String getName() {
        return name;
    }

    /** Sets the Name of the Contact. */
    public void setName(String name) {
        this.name = name;
    }

}
