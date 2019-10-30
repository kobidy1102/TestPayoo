package com.example.testmybasecode.domain.main.enter_phone_number;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.testmybasecode.R;

import java.text.DecimalFormat;
import java.util.List;


public class ValuesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Integer> values;
    private ValueAdapterListener listener;
    private int itemSelected=-1;

    ValuesAdapter(Context context,
                  List<Integer> values,
                  ValueAdapterListener listener) {
        this.context = context;
        this.values = values;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_value, viewGroup, false);
        return new ValueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ValueViewHolder) {
            int value = values.get(i);
            ValueViewHolder holder = (ValueViewHolder) viewHolder;
            String valueFormat=new DecimalFormat("###,###").format(value);
            holder.tvValue.setText(valueFormat);
            if(i==itemSelected){
                holder.tvValue.setBackground(context.getDrawable( R.drawable.bg_grey_radius_border_click));
            }else{
                holder.tvValue.setBackground(context.getDrawable( R.drawable.bg_grey_radius_border));
            }
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

            tvValue.setOnClickListener(view -> {
                if(listener!=null){
                    listener.onValueClick(values.get(getAdapterPosition()));
                    itemSelected= getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }

    }

    public interface ValueAdapterListener{
        void onValueClick(int value);
    }
}
