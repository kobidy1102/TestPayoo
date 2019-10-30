package com.example.testmybasecode.domain.main.payment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.testmybasecode.R;
import com.example.testmybasecode.util.AppUtil;

import java.util.List;


public class PaymentMethodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String PREFIX_NUMBER="090";
    private static final String LINKKED_ACCOUNT="Linked Account";
    private static final String INTERNATIONAL_CARD="International Card";
    private static final String DOMESTIC_CARD="Domestic Card";


    private List<String> paymentMethods;
    private int value;
    private String phoneNumber;
    private PaymentMethodAdapterListener listener;

    PaymentMethodAdapter(List<String> paymentMethods,
                         int value,
                         String phoneNumber,
                         PaymentMethodAdapterListener listener) {
        this.paymentMethods = paymentMethods;
        this.value=value;
        this.phoneNumber=phoneNumber;
        this.listener = listener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payment_method, viewGroup, false);
        return new ValueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ValueViewHolder) {
            String paymentMethod = paymentMethods.get(i);
            ValueViewHolder holder = (ValueViewHolder) viewHolder;
            holder.tvPaymentMethod.setText(paymentMethod);
            AppUtil.setEffectView(holder.tvPaymentMethod);

            if(phoneNumber.substring(0,3).equals(PREFIX_NUMBER) && paymentMethod.equals(LINKKED_ACCOUNT)){
                holder.tvPaymentMethod.setEnabled(false);
                holder.tvPaymentMethod.setAlpha(0.5f);
            }

            if(value<100000 && (paymentMethod.equals(INTERNATIONAL_CARD) || paymentMethod.equals(DOMESTIC_CARD))){
                holder.tvPaymentMethod.setEnabled(false);
                holder.tvPaymentMethod.setAlpha(0.5f);
            }

        }
    }

    @Override
    public int getItemCount() {
        return paymentMethods != null ? paymentMethods.size() : 0;
    }

    private class ValueViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPaymentMethod;

        private ValueViewHolder(View itemView) {
            super(itemView);
            tvPaymentMethod = itemView.findViewById(R.id.item_payment_method_tv_payment_method);

            tvPaymentMethod.setOnClickListener(view -> {
                if(listener!=null){
                    listener.onPaymentMethodClick(paymentMethods.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface PaymentMethodAdapterListener{
        void onPaymentMethodClick(String paymentMethod);
    }
}
