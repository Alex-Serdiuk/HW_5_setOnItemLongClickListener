package com.example.serverapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private List<Contact> contacts;
    private LayoutInflater inflater;
    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.contacts = contacts;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View item = inflater.inflate(resource, parent, false);
        TextView tvFullName = item.findViewById(R.id.tvFullName);
        TextView tvPhone = item.findViewById(R.id.tvPhone);
        Contact contact = contacts.get(position);
        tvFullName.setText(contact.getFirstName() + contact.getLastName());
        tvPhone.setText(contact.getPhone());

        return item;
    }
}