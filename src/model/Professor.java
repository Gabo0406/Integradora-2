package model;

import Exceptions.InvalidEmailException;

/**
 * Represents a professor assigned to courses.
 */
public class Professor {
    private String id;
    private String idType;
    private String fullName;
    private String email;

    public Professor(String id, String idType, String fullName, String email)throws InvalidEmailException {
        this.id = id;
        this.idType = idType;
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() { return id; }
    public String getIdType() { return idType; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    public void setFullName(String fullName) { this.fullName = fullName; }

public void setEmail(String email) throws InvalidEmailException {
    if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
        throw new InvalidEmailException("Invalid email format.");
    }
    this.email = email;
}
}

