package com.example.testmybasecode.domain.main.payment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testmybasecode.R;

import java.util.List;


public class PaymentMethodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> values;
    private PaymentMethodAdapterListener listener;

    public PaymentMethodAdapter(Context context,
                                List<String> values,
                                PaymentMethodAdapterListener listener) {
        this.context = context;
        this.values = values;
        this.listener = listener;
        Log.e("abc","size:"+values.size());

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
            String value = values.get(i);
            ValueViewHolder holder = (ValueViewHolder) viewHolder;
            holder.tvValue.setText(value);
            Log.e("abc","value:"+value);
        }
    }

    @Override
    public int getItemCount() {
        return values != null ? values.size() : 0;
    }

    private class ValueViewHolder extends RecyclerView.ViewHolder {
        private TextView tvValue;

        private ValueViewHolder(View itemView) {
            super(itemView);
            tvValue = itemView.findViewById(R.id.item_value_tv_value);


            tvValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onPaymentMethodClick(values.get(getAdapterPosition()));
                    }
                }
            });
        }

    }

    public interface PaymentMethodAdapterListener{
        void onPaymentMethodClick(String value);
    }
}
