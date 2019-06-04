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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = BookActivity.class.getName();

    private String URL;

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

    /**
     * Loading indicator that is displayed before the book data has finished loading.
     */
    private View loadingIndicator;

    /**
     * EditText view for the user input.
     */
    private EditText editText;

    /**
     * EditText input to save the input from the user.
     */
    private String editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: Book Activity onCreate() called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_activity);

        // Find a reference to the {@link ListView} in the layout.
        final ListView bookListView = findViewById(R.id.list);

        // Find a reference to the empty view in the layout.
        mEmptyTextView = findViewById(R.id.empty_view);

        // Create a new adapter that takes an empty list of books as input.
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface.
        bookListView.setAdapter(mAdapter);

        // Find a reference to the EditText view in the layout.
        editText = findViewById(R.id.edit_text);
        // Hide the cursor when the activity is created.
        editText.setCursorVisible(false);

        // Find a reference to the Button in the layout.
        Button searchButton = findViewById(R.id.search_button);

        // Find a reference to the loading indicator in the layout
        loadingIndicator = findViewById(R.id.loading_indicator);

        // Hide the loading indicator when the activity is created.
        loadingIndicator.setVisibility(View.GONE);

        // Set a click listener on the EditText view to make the cursor visible
        // when the user clicks on the view. Also clear EditText of previous user Input
        // text.
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setCursorVisible(true);
                editText.getText().clear();
            }
        });

        // Set a click listener on the button, which sends a query to the google
        // books url with the user's input retrieved from the EditText field.
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the EditText cursor after the search button has been clicked.
                editText.setCursorVisible(false);

                // Get the value of the text entered.
                editTextInput = editText.getText().toString();
                Log.i(LOG_TAG, "This is the input result: " + editTextInput);

                // Remove whitespaces from the EditText input before using it as a URL parameter.
                editTextInput = editTextInput.replaceAll("\\s+", "");

                // URL for the query sent to the google books api.
                URL = "https://www.googleapis.com/books/v1/volumes?q=" + editTextInput;

                // Display the loading before the books data has finished loading.
                loadingIndicator.setVisibility(View.VISIBLE);

                // Get a reference to the InputMethodManager to check the state of the
                // keyboard.
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide keyboard after the search button is clicked.
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                // Get a reference to the ConnectivityManager to check the state of network
                // connectivity.
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network.
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data.
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, inorder to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass
                    // in null for the bundle. Pass in this activity for the LoaderCallbacks
                    // parameter.
                    loaderManager.initLoader(BOOK_LOADER_ID, null, BookActivity.this);
                    Log.i(LOG_TAG, "TEST: calling initLoader() ...");
                } else {
                    // Otherwise, display error.
                    // First hide loading indicator so error message will be visible.
                    loadingIndicator.setVisibility(View.GONE);

                    // Update empty state with no connection error message.
                    mEmptyTextView.setText(R.string.no_internet_connection);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        Log.i(LOG_TAG, "TEST: onCreateLoader() called ...");
        // Create a new loader for the given URL.
        return new BookLoader(this, URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.i(LOG_TAG, "TEST: onLoadFinished() called ...");
        // Hide the loading indicator because the data has been loaded.
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
        Log.i(LOG_TAG, "TEST: onLoaderReset() called ...");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
