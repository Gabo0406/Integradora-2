package ui;

import model.*;
import java.util.*;

import Exceptions.InvalidArtifactTypeException;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidURLException;
import java.io.*;
import java.time.LocalDate;

public class Executable {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProjectManager manager = new ProjectManager();

    public static void main(String[] args) {
        System.out.println("=== PROJECT MANAGEMENT SYSTEM ===");
        initializeData();

        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Register course");
            System.out.println("2. Register professor");
            System.out.println("3. Register project");
            System.out.println("4. Search project by ID");
            System.out.println("5. List projects without results");
            System.out.println("6. Search projects by course name");
            System.out.println("7. Search projects by keyword");
            System.out.println("8. Modify project information");
            System.out.println("9.Export course");
            System.out.println("10. Register result to project");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> registerCourse();
                case 2 -> registerProfessor();
                case 3 -> registerProject();
                case 4 -> searchProjectByID();
                case 5 -> listProjectsWithoutResults();
                case 6 -> searchByCourseName();
                case 7 -> searchByKeyword();
                case 8 -> modifyProject();
                case 9 -> registerResultToProject();
                case 10 -> exportCourse();
                case 11 -> exit = true;
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void initializeData() {
        System.out.println("Do you want to load demo data (D) or from a CSV file (C)?");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.equals("C")) {
            System.out.print("Enter the path to the CSV file: ");
            String path = scanner.nextLine();
            try {
                manager.loadFromCSV(path);
                System.out.println("CSV file loaded successfully.");
            } catch (IOException e) {
                System.out.println("Error loading file: " + e.getMessage());
            }
        } else {
            manager.loadDemoData();
            System.out.println("Demo data loaded.");
        }
    }

    private static void registerCourse() {
        System.out.print("Course code: ");
        String code = scanner.nextLine();
        System.out.print("Course name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Credits: ");
        int credits = Integer.parseInt(scanner.nextLine());
        boolean success = manager.registerCourse(new Course(code, name, desc, credits));
        System.out.println(success ? "Course registered." : "Course already exists.");
    }

  private static void registerProfessor() {
    try {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("ID type: ");
        String type = scanner.nextLine();
        System.out.print("Full name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Professor prof = new Professor(id, type, name, email);
        boolean success = manager.registerProfessor(prof);
        System.out.println(success ? "Professor registered." : "Invalid data or duplicate.");
    } catch (InvalidEmailException e) {
        System.out.println("Error: " + e.getMessage());
    }
}


    private static void registerProject() {
        System.out.print("Course code: ");
        String code = scanner.nextLine();
        Course course = manager.getCourseByCode(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Professor ID: ");
        String id = scanner.nextLine();
        Professor prof = manager.getProfessorById(id);
        if (prof == null) {
            System.out.println("Professor not found.");
            return;
        }

        System.out.print("Project name: ");
        String name = scanner.nextLine();
        System.out.print("Companies (comma-separated): ");
        List<String> companies = Arrays.asList(scanner.nextLine().split(","));
        System.out.print("Semester: ");
        String semester = scanner.nextLine();
        System.out.print("Project type: ");
        String type = scanner.nextLine();
        System.out.print("Keywords (comma-separated): ");
        List<String> keywords = Arrays.asList(scanner.nextLine().split(","));
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Statement link: ");
        String link = scanner.nextLine();

        Project p = new Project(course, prof, name, companies, semester, type, keywords, desc, link);
        manager.registerProject(p);
        System.out.println("Project registered with ID: " + p.getId());
    }

  private static void searchProjectByID() {
    System.out.print("Project ID: ");
    String id = scanner.nextLine();
    Project p = manager.getProjectById(id);
    if (p == null) {
        System.out.println("Project not found.");
    } else {
        System.out.println("Project: " + p.getName());
        System.out.println("Course: " + p.getCourse().getName());
        System.out.println("Professor: " + p.getProfessor().getFullName());
        System.out.println("Results count: " + p.getResults().size());

        for (Result r : p.getResults()) {
            System.out.println("  Result ID: " + r.getId() + ", Group: " + r.getGroup() + ", Date: " + r.getDate());
            System.out.println("  Deliverables:");
            for (Deliverable d : r.getDeliverables()) {
                System.out.print("    Type: " + d.getType() + ", Phase: " + d.getPhase());
                if (d instanceof Repository repo) {
                    System.out.println(", URL: " + repo.getUrl() + ", Files: " + repo.getFileCount());
                } else if (d instanceof Document doc) {
                    System.out.println(", URL: " + doc.getUrl());
                } else if (d instanceof Artifact art) {
                    System.out.println(", Artifact Type: " + art.getArtifactType());
                } else {
                    System.out.println();
                }
            }
        }
    }
}

    private static void listProjectsWithoutResults() {
        List<Project> list = manager.getProjectsWithoutResults();
        if (list.isEmpty()) System.out.println("All projects have results.");
        else list.forEach(p -> System.out.println(p.getName() + " (" + p.getId() + ")"));
    }

    private static void searchByCourseName() {
        System.out.print("Course name to search: ");
        String name = scanner.nextLine();
        List<Project> list = manager.searchProjectsByCourseName(name);
        list.forEach(p -> System.out.println(p.getName() + " - " + p.getId()));
    }

    private static void searchByKeyword() {
        System.out.print("Keyword: ");
        String kw = scanner.nextLine();
        List<Project> list = manager.searchProjectsByKeyword(kw);
        list.forEach(p -> System.out.println(p.getName() + " - " + p.getId()));
    }

    private static void modifyProject() {
        System.out.print("Project ID: ");
        String id = scanner.nextLine();
        Project p = manager.getProjectById(id);
        if (p == null) {
            System.out.println("No project found with that ID.");
            return;
        }
        System.out.println("Modifying project " + p.getName());
        System.out.print("New name: ");
        String name = scanner.nextLine();
        System.out.print("Companies (comma-separated): ");
        List<String> companies = Arrays.asList(scanner.nextLine().split(","));
        System.out.print("Semester: ");
        String semester = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Keywords (comma-separated): ");
        List<String> keywords = Arrays.asList(scanner.nextLine().split(","));
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Statement link: ");
        String link = scanner.nextLine();

        boolean updated = manager.modifyProject(id, name, companies, semester, type, keywords, desc, link);
        System.out.println(updated ? "Project updated." : "Failed to update project.");
    }

private static void exportCourse() {
    System.out.print("Enter course code: ");
    String code = scanner.nextLine();
    try {
        manager.exportCourseProjectsToTxt(code);
    } catch (IOException e) {
        System.out.println("Error exporting file: " + e.getMessage());
    } catch (IllegalArgumentException iae) {
        System.out.println("Course not found.");
    }
}
private static void registerResultToProject() {
    System.out.print("Project ID: ");
    String projectId = scanner.nextLine();
    Project project = manager.getProjectById(projectId);
    if (project == null) {
        System.out.println("Project not found.");
        return;
    }
    if (!project.canAddResult()) {
        System.out.println("Project already has maximum number of results.");
        return;
    }
    System.out.print("Result ID: ");
    String resultId = scanner.nextLine();
    System.out.print("Group name: ");
    String group = scanner.nextLine();
    System.out.print("Date (YYYY-MM-DD): ");
    String dateStr = scanner.nextLine();
    LocalDate date;
    try {
        date = LocalDate.parse(dateStr);
    } catch (Exception e) {
        System.out.println("Invalid date format.");
        return;
    }

    Result result = new Result(resultId, group, date);
    if (project.addResult(result)) {
        System.out.println("Result added.");

        boolean addMore = true;
        while (addMore) {
            System.out.print("Add deliverable? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (!choice.equals("Y")) break;

            System.out.print("Deliverable type (Repository, Document, Artifact): ");
            String type = scanner.nextLine().trim();

            System.out.print("Phase: ");
            String phase = scanner.nextLine();

            try {
                switch (type.toLowerCase()) {
                    case "repository" -> {
                        System.out.print("Repository URL: ");
                        String url = scanner.nextLine();
                        System.out.print("File count: ");
                        int fileCount = Integer.parseInt(scanner.nextLine());
                        Repository repo = new Repository(phase, url, fileCount);
                        if (!result.addDeliverable(repo))
                            System.out.println("Failed to add deliverable. Possibly max deliverables reached or invalid data.");
                    }
                    case "document" -> {
                        System.out.print("Document URL: ");
                        String url = scanner.nextLine();
                        Document doc = new Document(phase, url);
                        if (!result.addDeliverable(doc))
                            System.out.println("Failed to add deliverable.");
                    }
                    case "artifact" -> {
                        System.out.print("Artifact type: ");
                        String artifactType = scanner.nextLine();
                        Artifact art = new Artifact(phase, artifactType);
                        if (!result.addDeliverable(art))
                            System.out.println("Failed to add deliverable.");
                    }
                    default -> {
                        System.out.println("Unknown deliverable type.");
                    }
                }
            } catch (InvalidURLException | InvalidArtifactTypeException e) {
                System.out.println("Invalid data: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        }
    } else {
        System.out.println("Could not add result.");
    }
}
}
