package com.example.testmybasecode.domain.main.payment;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.example.testmybasecode.R;
import com.example.testmybasecode.domain.main.confirm.ConfirmActivity_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressLint("Registered")
@EActivity(R.layout.activity_payment)
public class PaymentActivity extends AppCompatActivity {
    @Extra
    protected int value;
    @Extra
    protected String phoneNumber;

    @ViewById(R.id.activity_payment_rcv_payment_method)
    protected RecyclerView rcvPaymentMethod;



    @AfterViews
    protected void initViews(){
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Fake data
        List<String> list= Arrays.asList("Linked Account","International Card","Domestic Card","eWallet");
        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(list, value, phoneNumber, paymentMethod -> {
            ConfirmActivity_.intent(PaymentActivity.this)
                    .phoneNumber(phoneNumber)
                    .value(value)
                    .paymentMethod(paymentMethod)
                    .start();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right_two);

        });
        rcvPaymentMethod.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPaymentMethod.setLayoutManager(linearLayoutManager);
        rcvPaymentMethod.setAdapter(paymentMethodAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right_two, R.anim.slide_out_right);

    }
}
