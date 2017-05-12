package com.pasha.efebudak.selectacar.util.ui;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasha.efebudak.selectacar.R;

/**
 * Created by efebudak on 04/05/2017.
 */

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    public interface Listener {
        void onItemClicked(@NonNull final String key, @NonNull final String value);
    }

    @NonNull
    private ArrayMap<String, String> mItemMap;
    @NonNull
    private Listener mListener;

    public SimpleTextAdapter(@NonNull ArrayMap<String, String> itemMap, @NonNull Listener listener) {
        mItemMap = itemMap;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View root = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        final ViewHolder viewHolder = new ViewHolder(root);
        root.setOnClickListener(v -> mListener.onItemClicked(
                mItemMap.keyAt(viewHolder.getAdapterPosition()),
                mItemMap.valueAt(viewHolder.getAdapterPosition())));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mItemMap.valueAt(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? R.layout.simple_text_item : R.layout.bigger_text_item;
    }

    @Override
    public int getItemCount() {
        return mItemMap.size();
    }

    public void updateList(@NonNull final ArrayMap<String, String> map) {
        mItemMap = map;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
