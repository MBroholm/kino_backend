package org.example.kino_backend.model;

public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}
