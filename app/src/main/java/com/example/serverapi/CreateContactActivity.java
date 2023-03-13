package com.example.serverapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serverapi.databinding.ActivityCreateContactBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateContactActivity extends AppCompatActivity {
    private ActivityCreateContactBinding binding;
    private ContactApi contactApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactApi = NetworkService.getInstance().getApi();

        binding.btnSave.setOnClickListener(this::saveContact);
    }

    private void saveContact(View view) {
        String firstName = binding.etFirstName.getText().toString();
        String lastName = binding.etLastName.getText().toString();
        String phone = binding.etPhone.getText().toString();
        Contact contact = new Contact(firstName, lastName, phone);
        contactApi.save(contact).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                binding.etFirstName.setText("");
                binding.etLastName.setText("");
                binding.etPhone.setText("");
                Toast.makeText(CreateContactActivity.this, "contact saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Toast.makeText(CreateContactActivity.this, "server error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

