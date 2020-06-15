package com.mulganov.test_task.puerdev_vk.java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mulganov.test_task.puerdev_vk.R;
import com.mulganov.test_task.puerdev_vk.kt.vk.VKUser;

import java.util.List;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder> {

    // Список юзеров
    private List<VKUser> mData;

    private LayoutInflater mInflater;

    public BoxAdapter(Context context, List<VKUser> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VKUser el = mData.get(position);

        holder.user_name.setText(el.getFirstName() + " " + el.getLastName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_name;

        ViewHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
        }

    }

}