package model;

import Exceptions.InvalidURLException;

/**
 * Represents a document-based deliverable.
 */
public class Document extends Deliverable {
    private String url;

private static boolean isValidUrl(String url) {
    return url != null && url.matches("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
}
public Document(String phase, String url) throws InvalidURLException {
    super(phase);
    if (!isValidUrl(url)) throw new InvalidURLException("Invalid document URL.");
    this.url = url;
}
    public String getUrl() { return url; }

    @Override
    public String getType() {
        return "Document";
    }
}
