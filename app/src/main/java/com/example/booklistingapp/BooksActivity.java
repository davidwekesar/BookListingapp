package com.example.booklistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    public static final String LOG_TAG = BooksActivity.class.getName();

    EditText editText;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_activity);

        editText = (EditText) findViewById(R.id.search_input);

        button = (Button) findViewById(R.id.search_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                Toast msg = Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG);
                msg.show();
            }
        });

        ArrayList<String> bookList = new ArrayList<>();
        bookList.add("James");
        bookList.add("Mary");
        bookList.add("David");
        bookList.add("Troy");
        bookList.add("Samantha");
        bookList.add("Elle");
        bookList.add("Naomi");

        ListView booksListView = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, bookList);

        booksListView.setAdapter(adapter);
    }
}
