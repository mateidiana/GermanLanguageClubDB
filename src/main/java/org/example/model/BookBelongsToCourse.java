package org.example.model;

public class BookBelongsToCourse extends Entity{
    private int readingId;
    private int bookId;

    public BookBelongsToCourse(int id, int readingId, int bookId)
    {
        super(id);
        this.readingId=readingId;
        this.bookId=bookId;
    }

    public int getBook(){return bookId;}
    public void setBook(int bookId){this.bookId=bookId;}

    public int getReading(){return readingId;}
    public void setReadingId(int readingId){this.readingId=readingId;}


    public String toString(){
        return bookId+": "+readingId;
    }
}
