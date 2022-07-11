package com.example.baseproject.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseproject.R;
import com.example.baseproject.databinding.ItemLayoutBinding;
import com.example.baseproject.model.AppModel;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppModelHolder> {

    private LayoutInflater layoutInflater;

    private List<AppModel> mListAppModel;

    public AppAdapter() {
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<AppModel> mListAppModel) {
        this.mListAppModel = mListAppModel;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemLayoutBinding mBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_layout, parent, false);
        return new AppModelHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppModelHolder holder, int position) {
        holder.bind(mListAppModel.get(position));
    }

    @Override
    public int getItemCount() {
        if (mListAppModel == null) {
            return 0;
        }
        return mListAppModel.size();
    }

    public class AppModelHolder extends RecyclerView.ViewHolder {
        ItemLayoutBinding mBinding;

        public AppModelHolder(@NonNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(AppModel appModel) {
            mBinding.setAppModel(appModel);
            mBinding.executePendingBindings();
        }
    }
}
