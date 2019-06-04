package com.example.booklistingapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = BookActivity.class.getName();

    private static final String GOOGLEBKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really comes into play if we're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * Adapter for the list of books.
     */
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty.
     */
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_activity);

        // Find a reference to the {@link ListView} in the layout.
        ListView bookListView = findViewById(R.id.list);

        mEmptyTextView = findViewById(R.id.empty_view);

        // Create a new adapter that takes an empty list of books as input.
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface.
        bookListView.setAdapter(mAdapter);

        // Find a reference to the EditText view in the layout.
        final EditText searchTextInput = findViewById(R.id.search_input);

        // Find a reference to the Button in the layout.
        Button searchButton = findViewById(R.id.search_button);

        // Set a click listener on the button, which sends a query to the google
        // books url with the user's input retrieved from the EditText field.
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the value of the text entered.
                String searchKeyword = searchTextInput.getText().toString();
                Log.i(LOG_TAG, "This is the input result: " + searchKeyword);
            }
        });

        // Get a reference to the ConnectivityManager to check the state of network connectivity.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network.
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data.
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, inorder to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter.
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error.
            // First hide loading indicator so error message will be visible.
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message.
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        // Create a new loader for the given URL.
        return new BookLoader(this, GOOGLEBKS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide the loading indicator because the data has been loaded.
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous book data.
        mAdapter.clear();

        if (books == null && books.isEmpty()) {
            // If there is no valid list of {@link Book}s,
            // Set empty state text to display "No books found."
            mEmptyTextView.setText(R.string.no_books);
        } else {
            // If there is a valid list of {@link Book}s, then add them to the adapter's
            // data set. This will trigger the ListView to update
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
