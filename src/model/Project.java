package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a software project.
 */
public class Project {
    private String id;
    private Course course;
    private Professor professor;
    private String name;
    private List<String> companies;
    private String semester;
    private String type;
    private List<String> keywords;
    private String description;
    private String statementLink;
    private List<Result> results;

    public Project(Course course, Professor professor, String name, List<String> companies, String semester,
                   String type, List<String> keywords, String description, String statementLink) {
        this.id = UUID.randomUUID().toString(); // ID único
        this.course = course;
        this.professor = professor;
        this.name = name;
        this.companies = companies;
        this.semester = semester;
        this.type = type;
        this.keywords = keywords;
        this.description = description;
        this.statementLink = statementLink;
        this.results = new ArrayList<>();
    }

    public String getId() { return id; }
    public Course getCourse() { return course; }
    public Professor getProfessor() { return professor; }
    public String getName() { return name; }
    public List<String> getCompanies() { return companies; }
    public String getSemester() { return semester; }
    public String getType() { return type; }
    public List<String> getKeywords() { return keywords; }
    public String getDescription() { return description; }
    public String getStatementLink() { return statementLink; }
    public List<Result> getResults() { return results; }

    public void setName(String name) { this.name = name; }
    public void setCompanies(List<String> companies) { this.companies = companies; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setType(String type) { this.type = type; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
    public void setDescription(String description) { this.description = description; }
    public void setStatementLink(String statementLink) { this.statementLink = statementLink; }
public boolean addResult(Result result) {
    if (canAddResult()) {
        results.add(result);
        return true;
    }
    return false;
}

public boolean canAddResult() {
    return results.size() < 3; // EX7
}
} 