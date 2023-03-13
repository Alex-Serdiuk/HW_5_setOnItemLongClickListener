package com.example.serverapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {
    private ListView lvContacts;
    private ProgressBar progressBar;
    private Button btnAdd;
    private ContactApi contactApi;
    private ContactAdapter adapter;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContacts = findViewById(R.id.lvContacts);
        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAdd);
        contactApi = NetworkService.getInstance().getApi();


        adapter = new ContactAdapter(MainActivity.this, R.layout.contact_item, contacts);
        lvContacts.setAdapter(adapter);

        addListeners();

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position + " " + id, Toast.LENGTH_SHORT).show();
            }
        });

        lvContacts.setOnItemLongClickListener((parent, view, position, id) -> {
            Contact contact = adapter.getItem(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
            builder.setTitle("Confirm deletion");
            builder.setMessage("Are you sure you want to delete " + contact.getFirstName() + " " + contact.getLastName() + "?");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                Call<Void> call = NetworkService.getInstance().getApi().deleteContact(contact.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            adapter.remove(contact);
                            adapter.notifyDataSetChanged();
                        } else {
                            // Handle error
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle error
                    }
                });
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();

            return true;

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
        initListView();

    }

    private void addListeners() {
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateContactActivity.class));
        });
    }

    private void initListView() {
        contactApi.getAll().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                contacts.clear();
                contacts.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


}
