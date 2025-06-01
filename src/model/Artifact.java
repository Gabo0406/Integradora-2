package model;

import Exceptions.InvalidArtifactTypeException;

public class Artifact extends Deliverable {
    private String artifactType;

public Artifact(String phase, String artifactType) throws InvalidArtifactTypeException {
    super(phase);
    if (artifactType == null || artifactType.isBlank()) {
        throw new InvalidArtifactTypeException("Artifact type cannot be empty.");
    }
    this.artifactType = artifactType;
}
    public String getArtifactType() { return artifactType; }

    @Override
    public String getType() {
        return "Artifact";
    }
}
