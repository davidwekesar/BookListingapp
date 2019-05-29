package com.example.booklistingapp;

/**
 * An {@link Book} object contains information related to a single book.
 */
public class Book {

    /**
     * Title of the book.
     */
    private String mTitle;

    /**
     * Author of the book.
     */
    private String mAuthor;

    /**
     * Publish date of the book.
     */
    private String mPublishDate;

    /**
     * Constructs a new {@link Book} object.
     *
     * @param title       is the name of the title of the book.
     * @param author      is the name of the Author of the book.
     * @param publishDate is the year when the book was published.
     */
    public Book(String title, String author, String publishDate) {
        mTitle = title;
        mAuthor = author;
        mPublishDate = publishDate;
    }

    /**
     * Returns the title of the book.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the author of the book.
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Returns the publish date of the book.
     */
    public String getPublishDate() {
        return mPublishDate;
    }

}
