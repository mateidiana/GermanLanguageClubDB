package org.example.model;

public abstract class Person extends Entity{
    private String name;

    public Person(int id,String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return super.toString() + " Name: " + name;
    }
}
