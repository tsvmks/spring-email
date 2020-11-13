package ru.tsvmks.sgringframework.biemail.model;

public class Version {
    private String version;

    public Version(String version) {
        this.version = version;
    }

    public Version() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
