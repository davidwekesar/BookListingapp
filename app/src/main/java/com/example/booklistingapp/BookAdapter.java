package com.example.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link BookAdapter} creates a list item layout for each book from the
 * data source (a list of {@link Book} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app.
     * @param books   is the list of books, which is the data source of the adapter.
     */
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if the convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.books_list_item, parent, false);
        }

        // Find the book at the given position in the list of books.
        Book currentBook = getItem(position);

        // Find the TextView with the view ID title.
        TextView titleView = listItemView.findViewById(R.id.title);
        // Get the Title of the book.
        String title = currentBook.getTitle();
        // Display the title of the current book in that TextView.
        titleView.setText(title);

        // Find the TextView with the view ID author.
        TextView authorView = listItemView.findViewById(R.id.author);
        // Get the author of the book.
        String author = currentBook.getAuthor();
        // Display the author of the current book in that TextView.
        authorView.setText(author);

        // Find the TextView with the view ID publish_date.
        TextView dateView = listItemView.findViewById(R.id.publish_date);
        // Get the publish date of the book.
        String date = currentBook.getPublishDate();
        // Display the publish date of the current book in that TextView.
        dateView.setText(date);

        return listItemView;
    }
}