package model;

/**
 * Abstract base class for project deliverables.
 */
public abstract class Deliverable {
    protected String phase;
    protected boolean deleted;

    public Deliverable(String phase) {
        this.phase = phase;
        this.deleted = false;
    }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public boolean isDeleted() { return deleted; }
    public void delete() { this.deleted = true; }

    public abstract String getType();
}
