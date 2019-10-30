package com.example.testmybasecode.domain.main.enter_phone_number;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.testmybasecode.R;
import com.example.testmybasecode.domain.dialogs.ContactsDialog;
import com.example.testmybasecode.domain.main.payment.PaymentActivity_;
import com.example.testmybasecode.service.model.Contact;
import com.example.testmybasecode.util.AppUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("Registered")
@EActivity(R.layout.activity_enter_phone_number)
public class EnterPhoneNumberActivity extends AppCompatActivity {
    private String TAG = EnterPhoneNumberActivity.class.getSimpleName();

    @ViewById(R.id.activity_add_phone_number_rcv_values)
    protected RecyclerView rcvValues;
    @ViewById(R.id.activity_add_phone_number_bt_continue)
    protected Button btnContinue;
    @ViewById(R.id.activity_add_phone_number_edt_phone_number)
    protected EditText edtPhoneNumber;
    @ViewById(R.id.activity_add_phone_number_iv_contacts)
    protected ImageView ivContacts;


    private int valueSelected;


    @AfterViews
    protected void initViews() {
        setTitle(getString(R.string.enter_phone_number));
        btnContinue.setEnabled(false);
        btnContinue.setAlpha(0.5f);
        AppUtil.setEffectView(btnContinue);
        AppUtil.setEffectView(ivContacts);

        //Fake data
        List<Integer> list = Arrays.asList(10000, 20000, 30000, 50000, 100000, 200000, 300000, 500000);
        ValuesAdapter valuesAdapter = new ValuesAdapter(this, list, value -> {
            valueSelected = value;
            checkEnableContinueButton();
        });
        rcvValues.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvValues.setLayoutManager(gridLayoutManager);
        rcvValues.setAdapter(valuesAdapter);
    }

    @Click(R.id.activity_add_phone_number_bt_continue)
    protected void onContinueClick() {
        PaymentActivity_.intent(this)
                .phoneNumber(edtPhoneNumber.getText().toString())
                .value(valueSelected)
                .start();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right_two);
    }

    @TextChange(R.id.activity_add_phone_number_edt_phone_number)
    protected void onEnterPhoneNumber() {
        checkEnableContinueButton();
    }

    @Click(R.id.activity_add_phone_number_iv_contacts)
    protected void onContactsClick() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                getListContacts();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }

        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(getResources().getString(R.string.warning_permisstion))
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
    }

    private void getListContacts() {
        List<Contact> contacts = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (phones != null) {
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
                contacts.add(new Contact(name, phoneNumber));
            }
            phones.close();

        }

        ContactsDialog contactsDialog = ContactsDialog.newInstance(contacts, (dialog, contact) -> {
            dialog.dismiss();
            edtPhoneNumber.setText(contact.getPhoneNumber());
            checkEnableContinueButton();
        });

        contactsDialog.show(getSupportFragmentManager(), TAG);
    }

    private void checkEnableContinueButton() {
        String regexStr = "^[0-9]{10}$";
        String phoneNumber = edtPhoneNumber.getText().toString();
        if (!phoneNumber.isEmpty() && phoneNumber.matches(regexStr) && valueSelected != 0) {
            btnContinue.setEnabled(true);
            btnContinue.setAlpha(1.0f);
        } else {
            btnContinue.setEnabled(false);
            btnContinue.setAlpha(0.5f);
        }

    }
}
