package model;

/**
 * Represents a university course.
 */
public class Course {
    private String code;
    private String name;
    private String description;
    private int credits;

    public Course(String code, String name, String description, int credits) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.credits = credits;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCredits() { return credits; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCredits(int credits) { this.credits = credits; }
}
