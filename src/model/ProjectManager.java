package model;

import java.util.*;
import java.util.regex.Pattern;

import Exceptions.InvalidEmailException;

import java.io.*;

public class ProjectManager {
    private List<Course> courses;
    private List<Professor> professors;
    private List<Project> projects;

    public ProjectManager() {
        this.courses = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.projects = new ArrayList<>();
    }
/**
 * Registers a new course into the system.
 *
 * @param course The course object to be added.
 * @return true if the course was successfully registered, false if it already exists.
 * @pre course is not null and contains valid attributes.
 * @post The course list contains the new course if it wasn't already present.
 */
    public boolean registerCourse(Course course) {
        if (getCourseByCode(course.getCode()) != null) return false;
        courses.add(course);
        return true;
    }

/**
 * Registers a new professor, validating their email and uniqueness.
 *
 * @param professor The professor object to be registered.
 * @return true if the professor was successfully registered, false otherwise.
 * @pre professor is not null and has valid email and ID.
 * @post The professor list includes the new professor if valid and unique.
 */
    public boolean registerProfessor(Professor professor) {
        if (!isValidEmail(professor.getEmail())) return false;
        if (getProfessorById(professor.getId()) != null) return false;
        professors.add(professor);
        return true;
    }

/**
 * Registers a new project.
 *
 * @param project The project to be added.
 * @pre project is not null.
 * @post The project is added to the internal project list.
 */
    public void registerProject(Project project) {
        projects.add(project);
    }
/**
 * Retrieves a course by its unique code.
 *
 * @param code The code of the course.
 * @return The matching Course object or null if not found.
 * @pre code is a non-null, non-empty string.
 * @post Returns the course with the given code or null.
 */
    public Course getCourseByCode(String code) {
        for (Course c : courses) {
            if (c.getCode().equals(code)) return c;
        }
        return null;
    }

/**
 * Retrieves a professor by ID.
 *
 * @param id The ID of the professor.
 * @return The matching Professor object or null if not found.
 * @pre id is a non-null, non-empty string.
 * @post Returns the professor with the given ID or null.
 */
    public Professor getProfessorById(String id) {
        for (Professor p : professors) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
/**
 * Retrieves a project by ID.
 *
 * @param id The ID of the project.
 * @return The matching Professor object or null if not found.
 * @pre id is a non-null, non-empty string.
 * @post Returns the professor with the given ID or null.
 */
    public Project getProjectById(String id) {
        for (Project p : projects) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
/**
 * Adds a result to a project if possible.
 *
 * @param projectId The ID of the project.
 * @param result The result to be added.
 * @return true if the result was added successfully, false otherwise.
 * @pre projectId and result are not null, and project exists.
 * @post The result is added to the project if it hasn't reached its result limit.
 */
    public boolean addResultToProject(String projectId, Result result) {
        Project p = getProjectById(projectId);
        if (p != null) {
            return p.addResult(result);
        }
        return false;
    }
/**
 * Gets all projects that do not have any associated results.
 *
 * @return A list of projects without results.
 * @pre None.
 * @post Returns a list (possibly empty) of projects with no results.
 */
    public List<Project> getProjectsWithoutResults() {
        List<Project> noResults = new ArrayList<>();
        for (Project p : projects) {
            if (p.getResults().isEmpty()) {
                noResults.add(p);
            }
        }
        return noResults;
    }
/**
 * Searches for projects whose course name contains the given string.
 *
 * @param courseName The course name or part of it.
 * @return A list of matching projects.
 * @pre courseName is not null.
 * @post Returns projects whose course name contains the string (case insensitive).
 */
    public List<Project> searchProjectsByCourseName(String courseName) {
        List<Project> results = new ArrayList<>();
        for (Project p : projects) {
            if (p.getCourse().getName().toLowerCase().contains(courseName.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }
/**
 * Searches for projects containing a keyword.
 *
 * @param keyword The keyword to search for.
 * @return A list of projects that include the keyword.
 * @pre keyword is not null.
 * @post Returns matching projects (case insensitive).
 */

    public List<Project> searchProjectsByKeyword(String keyword) {
        List<Project> results = new ArrayList<>();
        for (Project p : projects) {
            for (String kw : p.getKeywords()) {
                if (kw.toLowerCase().contains(keyword.toLowerCase())) {
                    results.add(p);
                    break;
                }
            }
        }
        return results;
    }
/**
 * Modifies project general information.
 *
 * @param projectId The ID of the project to modify.
 * @param name The new project name.
 * @param companies New list of companies.
 * @param semester The updated semester.
 * @param type Updated project type.
 * @param keywords New keywords list.
 * @param description New description.
 * @param statementLink New statement link.
 * @return true if the project was modified successfully, false otherwise.
 * @pre All parameters are valid and projectId exists.
 * @post The project is updated with the new information.
 */
    public boolean modifyProject(String projectId, String name, List<String> companies, String semester,
                                 String type, List<String> keywords, String description, String statementLink) {
        Project p = getProjectById(projectId);
        if (p == null) return false;

        p.setName(name);
        p.setCompanies(companies);
        p.setSemester(semester);
        p.setType(type);
        p.setKeywords(keywords);
        p.setDescription(description);
        p.setStatementLink(statementLink);
        return true;
    }
/**
 * Logically deletes a deliverable from a project's result.
 *
 * @param resultId The ID of the result.
 * @param projectId The ID of the project.
 * @param deliverableType The type of deliverable to delete.
 * @return true if the deliverable was found and deleted, false otherwise.
 * @pre All parameters are valid and non-null.
 * @post If found, the deliverable's "deleted" flag is set to true.
 */
    public boolean deleteDeliverable(String resultId, String projectId, String deliverableType) {
        Project p = getProjectById(projectId);
        if (p == null) return false;
        for (Result r : p.getResults()) {
            if (r.getId().equals(resultId)) {
                for (Deliverable d : r.getDeliverables()) {
                    if (d.getType().equalsIgnoreCase(deliverableType)) {
                        d.delete();
                        return true;
                    }
                }
            }
        }
        return false;
    }

/**
 * Validates the format of an email address.
 *
 * @param email The email address to validate.
 * @return true if valid, false otherwise.
 * @pre email is not null.
 * @post Returns true if email matches standard pattern.
 */
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
/**
 * Validates that the URL belongs to accepted platforms.
 *
 * @param url The URL to validate.
 * @return true if it's valid for GitHub, Drive, etc.
 * @pre url is not null.
 * @post Returns true if URL matches the allowed patterns.
 */
    public boolean isValidURL(String url) {
        String pattern = "^(https?:\\/\\/)?(www\\.)?(github|drive|dropbox|onedrive|sharepoint)\\.[a-z]+\\/.*$";
        return url != null && Pattern.matches(pattern, url);
    }
/**
 * Loads a predefined set of data for testing purposes.
 *
 * @pre None.
 * @post One course, one professor, and one project are added to the system.
 */
    public void loadDemoData() {
        Course c1 = new Course("12345", "Algoritmos I", "Curso base", 4);
  Professor p1 = null;
    try {
    p1 = new Professor("111", "CC", "Carlos Torres", "carlos@icesi.edu.co");
    } catch (InvalidEmailException e) {
    System.out.println("Error creando profesor: " + e.getMessage());
}

        Project pr1 = new Project(c1, p1, "Sistema de biblioteca",
                List.of("Biblioteca ICESI"), "2025-1", "Proyecto de curso",
                List.of("biblioteca", "sistema"), "Un sistema para gestionar libros", "https://drive.google.com/demo1");
        registerCourse(c1);
        registerProfessor(p1);
        registerProject(pr1);
    }

/**
 * Loads course, professor, and project data from a CSV file.
 *
 * @param filepath The path to the CSV file.
 * @throws IOException if the file cannot be read.
 * @pre filepath points to a valid CSV with expected format.
 * @post Data from the file is loaded into the system.
 */
    public void loadFromCSV(String filepath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 9) continue;
                String code = data[0];
                String profId = data[1];
                Course c = getCourseByCode(code);
                Professor p = getProfessorById(profId);
                if (c == null) {
                    c = new Course(code, data[2], data[3], Integer.parseInt(data[4]));
                    registerCourse(c);
                }
                if (p == null) {
            try {
                p = new Professor(profId, data[5], data[6], data[7]);
                    } catch (InvalidEmailException e) {
                    System.out.println("Error loading professor: " + e.getMessage());
                }

                    registerProfessor(p);
                }
                Project pr = new Project(c, p, data[8],
                        Arrays.asList(data[9].split(";")), data[10], data[11],
                        Arrays.asList(data[12].split(";")), data[13], data[14]);
                registerProject(pr);
            }
        }
    }
/**
 * Gets the list of registered courses.
 *
 * @return List of courses.
 * @pre None.
 * @post Returns internal list of courses.
 */
    public List<Course> getCourses() { return courses; }
    
/**
 * Gets the list of registered professors.
 *
 * @return List of professors.
 * @pre None.
 * @post Returns internal list of professors.
 */
public List<Professor> getProfessors() { return professors; }
/**
 * Gets the list of registered projects.
 *
 * @return List of projects.
 * @pre None.
 * @post Returns internal list of projects.
 */
 public List<Project> getProjects() { return projects; }
 /**
 * Exports all projects associated with a given course into a .txt file.
 *
 * @param courseCode The code of the course to export.
 * @throws IOException if the file cannot be created.
 * @throws IllegalArgumentException if course is not found.
 * @pre courseCode is valid and corresponds to a registered course.
 * @post A .txt file with course and project info is created.
 */
public void exportCourseProjectsToTxt(String courseCode) throws IOException {
    Course course = getCourseByCode(courseCode);
    if (course == null) throw new IllegalArgumentException("Course not found");

    List<Project> courseProjects = new ArrayList<>();
    for (Project p : projects) {
        if (p.getCourse().getCode().equals(courseCode)) {
            courseProjects.add(p);
        }
    }

    File dir = new File("data");
    if (!dir.exists()) dir.mkdirs();

    String filename = "data/" + courseCode + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        writer.write("Projects for course: " + course.getName() + " (" + course.getCode() + ")\n");
        writer.write("Description: " + course.getDescription() + ", Credits: " + course.getCredits() + "\n\n");

        for (Project p : courseProjects) {
            writer.write("Project ID: " + p.getId() + "\n");
            writer.write("Name: " + p.getName() + "\n");
            writer.write("Professor: " + p.getProfessor().getFullName() + "\n");
            writer.write("Semester: " + p.getSemester() + "\n");
            writer.write("Type: " + p.getType() + "\n");
            writer.write("Companies: " + String.join(", ", p.getCompanies()) + "\n");
            writer.write("Keywords: " + String.join(", ", p.getKeywords()) + "\n");
            writer.write("Description: " + p.getDescription() + "\n");
            writer.write("Statement Link: " + p.getStatementLink() + "\n");
            writer.write("Results: " + p.getResults().size() + " result(s)\n");
            writer.write("------------------------------------------------------------\n");
        }
    }

    System.out.println("TXT file created: " + filename);
}
}