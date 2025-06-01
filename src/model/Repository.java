package model;

import Exceptions.InvalidURLException;

/**
 * Represents a GitHub repository deliverable.
 */
public class Repository extends Deliverable {
    private String url;
    private int fileCount;

    public Repository(String phase, String url, int fileCount) throws InvalidURLException {
        super(phase);
        if (!isValidUrl(url)) throw new InvalidURLException("Invalid repository URL.");
        this.url = url;
        this.fileCount = fileCount;
    }

    private static boolean isValidUrl(String url) {
        return url != null && url.matches("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
    }

    public String getUrl() { return url; }
    public int getFileCount() { return fileCount; }

    @Override
    public String getType() {
        return "Repository";
    }
}  