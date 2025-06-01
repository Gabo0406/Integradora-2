package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a result for a project.
 */
public class Result {
    private String id;
    private String group;
    private LocalDate date;
    private List<Deliverable> deliverables;

    public Result(String id, String group, LocalDate date) {
        this.id = id;
        this.group = group;
        this.date = date;
        this.deliverables = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getGroup() { return group; }
    public LocalDate getDate() { return date; }
    public List<Deliverable> getDeliverables() { return deliverables; }

    /**
     * Validates and adds a deliverable if the limit is not exceeded and the phase is defined.
     * Covers EX7 and EX8.
     */
    public boolean addDeliverable(Deliverable d) {
        if (deliverables.size() < 3 && d != null && d.getPhase() != null && !d.getPhase().isBlank()) {
            deliverables.add(d);
            return true;
        }
        return false;
    }

    /**
     * Check if a deliverable with the given type exists.
     */
    public Deliverable findDeliverableByType(String type) {
        for (Deliverable d : deliverables) {
            if (d.getType().equalsIgnoreCase(type)) return d;
        }
        return null;
    }
}
