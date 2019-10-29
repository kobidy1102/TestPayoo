package com.example.testmybasecode.domain.main.enter_phone_number;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.testmybasecode.R;
import com.example.testmybasecode.domain.dialogs.ContactsDialog;
import com.example.testmybasecode.domain.main.payment.PaymentActivity_;
import com.example.testmybasecode.service.model.Contact;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("Registered")
@EActivity(R.layout.activity_enter_phone_number)
public class EnterPhoneNumberActivity extends AppCompatActivity {
    @ViewById(R.id.activity_add_phone_number_rcv_values)
    protected RecyclerView rcvValues;
    @ViewById(R.id.activity_add_phone_number_bt_continue)
    protected Button btnContinue;
    @ViewById(R.id.activity_add_phone_number_edt_phone_number)
    protected EditText edtPhoneNumber;
  //  private ValuesAdapter valuesAdapter;

    List<String> list= Arrays.asList("10","20.000","30.000","50.000","100.000","200.000","300.000","500.000");


    @AfterViews
    protected void initViews(){


        ValuesAdapter valuesAdapter = new ValuesAdapter(this, list, new ValuesAdapter.ValueAdapterListener() {
            @Override
            public void onValueClick(String value) {
                Toast.makeText(EnterPhoneNumberActivity.this, ""+value, Toast.LENGTH_SHORT).show();
            }
        });
        rcvValues.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        rcvValues.setLayoutManager(gridLayoutManager);
        //rcvValues.addItemDecoration(new FormatItemGridToCenter(3, 40, false));
        rcvValues.setAdapter(valuesAdapter);
    }

    @Click(R.id.activity_add_phone_number_bt_continue)
    protected void onContinueClick(){
        PaymentActivity_.intent(this)
                .phoneNumber("123")
                .value("100")
                .start();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right_two);
    }

    @Click(R.id.activity_add_phone_number_iv_contacts)
    protected void onContactsClick(){

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

    private void getListContacts(){
        List<Contact> contacts= new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
            contacts.add(new Contact(name,phoneNumber));
        }
        phones.close();

        ContactsDialog contactsDialog= ContactsDialog.newInstance(contacts, new ContactsDialog.ContactsDialogListener() {
                    @Override
                    public void onContactClick(ContactsDialog dialog, Contact contact) {
                        dialog.dismiss();
                        edtPhoneNumber.setText(contact.getPhoneNumber());
                    }
                });

                contactsDialog.show(getSupportFragmentManager(), "");
        
        
        
    }
}
