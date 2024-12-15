package org.example.model;

public class Book extends Entity{
    private String title;
    private String author;

    public Book(int id, String title, String author)
    {
        super(id);
        this.title=title;
        this.author=author;
    }

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author=author;}

    public String toString(){
        return "\"" + title + "\" by " + author;
    }
}
