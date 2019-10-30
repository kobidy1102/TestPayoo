package com.example.testmybasecode.domain.dialogs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.testmybasecode.R;
import com.example.testmybasecode.service.model.Contact;

import java.util.List;


public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ContactsDialog contactsDialog;
    private List<Contact> contacts;
    private ContactsDialog.ContactsDialogListener listener;


    ContactsAdapter(ContactsDialog contactsDialog,
                    List<Contact> contacts,
                    ContactsDialog.ContactsDialogListener listener) {
        this.contactsDialog = contactsDialog;
        this.contacts = contacts;
        this.listener = listener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_contact, viewGroup, false);
        return new ValueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ValueViewHolder) {
            Contact contact = contacts.get(i);
            ValueViewHolder holder = (ValueViewHolder) viewHolder;
            holder.tvName.setText(contact.getName());
            holder.tvPhoneNumber.setText(contact.getPhoneNumber());
        }
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    private class ValueViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPhoneNumber;
        private LinearLayout llRoot;

        private ValueViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_contact_tv_name);
            tvPhoneNumber = itemView.findViewById(R.id.item_contact_tv_phoneNumber);
            llRoot = itemView.findViewById(R.id.item_contact_ll_root);

            llRoot.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onContactClick(contactsDialog, contacts.get(getAdapterPosition()));
                }
            });
        }

    }

}
