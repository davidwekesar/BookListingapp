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
     * Description of the book.
     */
    private String mDescription;

    /**
     * Image URL of the book.
     */
    private String mImageUrl;

    /**
     * Constructs a new {@link Book} object.
     *
     * @param title       is the name of the title of the book.
     * @param author      is the name of the Author of the book.
     * @param publishDate is the year when the book was published.
     * @param description is a brief description about the book.
     */
    public Book(String title, String author, String publishDate, String description,
                String imageUrl) {
        mTitle = title;
        mAuthor = author;
        mPublishDate = publishDate;
        mDescription = description;
        mImageUrl = imageUrl;
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

    /**
     * Returns the description of the book.
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Returns the image url of the book.
     */
    public String getImageUrl() {
        return mImageUrl;
    }
}
