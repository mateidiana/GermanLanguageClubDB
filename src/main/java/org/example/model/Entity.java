package org.example.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private int id;

    public Entity(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Id: " + id;
    }
}
