package com.example.testmybasecode.domain.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.example.testmybasecode.R;
import com.example.testmybasecode.service.model.Contact;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Objects;

@EFragment(R.layout.dialog_contacts)
public class ContactsDialog extends DialogFragment {

    private ContactsDialogListener listener;
    private List<Contact> contactList;
    @ViewById(R.id.dialog_contacts_rcv_contacts_list)
    protected RecyclerView rcvContactsList;
    public static ContactsDialog newInstance(List<Contact> contacts, ContactsDialogListener listener) {
        ContactsDialog contactsDialog = ContactsDialog_.builder().build();
        contactsDialog.setContactListener(listener);
        contactsDialog.contactList=contacts;
        return contactsDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setGravity(Gravity.CENTER);
        }

        ContactsAdapter contactsAdapter = new ContactsAdapter(this, contactList,listener);
        rcvContactsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvContactsList.setLayoutManager(linearLayoutManager);
        rcvContactsList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        rcvContactsList.setAdapter(contactsAdapter);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.85f;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
        super.onResume();
    }

    @Click(R.id.dialog_contacts_tv_close)
    protected void onCloseClick(){
        dismiss();
    }


    public interface ContactsDialogListener {
        void onContactClick(ContactsDialog contactsDialog, Contact contact);
    }

    private void setContactListener(ContactsDialogListener listener) {
        this.listener = listener;
    }
}
