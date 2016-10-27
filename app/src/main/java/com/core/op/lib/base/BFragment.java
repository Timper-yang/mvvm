package com.core.op.lib.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.core.op.lib.utils.inject.InjectUtil;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;


/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/2/2
 */
public class BFragment<V extends BFViewModel, T extends ViewDataBinding> extends RxFragment {

    protected LayoutInflater inflater;

    protected FragmentActivity activity;

    @Inject
    protected V viewModel;

    protected T binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        binding = DataBindingUtil.inflate(inflater, InjectUtil.injectFrgRootView(this), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.setFragment(this);
        viewModel.setBinding(binding);
        binding.setVariable(BR.viewModel, viewModel);
        InjectUtil.injectAfterView(this);
        viewModel.afterViews();
    }


    protected void initBeforeView() {
        InjectUtil.injectBeforeView(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }
}
