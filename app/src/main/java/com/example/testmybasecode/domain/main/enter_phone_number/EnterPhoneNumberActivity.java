package com.example.testmybasecode.domain.main.enter_phone_number;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;
import com.example.testmybasecode.R;
import com.example.testmybasecode.domain.main.payment.PaymentActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;

@SuppressLint("Registered")
@EActivity(R.layout.activity_enter_phone_number)
public class EnterPhoneNumberActivity extends AppCompatActivity {
    @ViewById(R.id.activity_add_phone_number_rcv_values)
    protected RecyclerView rcvValues;
    @ViewById(R.id.activity_add_phone_number_bt_continue)
    protected Button btnContinue;
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
}
