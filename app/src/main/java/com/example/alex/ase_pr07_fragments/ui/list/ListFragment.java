package com.example.alex.ase_pr07_fragments.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.alex.ase_pr07_fragments.R;
import com.example.alex.ase_pr07_fragments.data.Database;
import com.example.alex.ase_pr07_fragments.data.DatabaseProfiles;
import com.example.alex.ase_pr07_fragments.data.model.User;
import com.example.alex.ase_pr07_fragments.databinding.FragmentListBinding;
import com.example.alex.ase_pr07_fragments.ui.main.MainActivityViewModel;
import androidx.appcompat.app.ActionBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

public class ListFragment extends Fragment {

    private ListFragmentViewModel mViewModel;
    private MainActivityViewModel mainVM;
    private static final int RC_PROFILE = 2;
    private ListFragmentAdapter listAdapter;
    private FragmentListBinding b;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentListBinding.inflate(inflater,container,false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new ListFragmentViewModelFactory(DatabaseProfiles.getInstance())).get(ListFragmentViewModel.class);
        mainVM = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        setUpActionBar();
        setUpViews();
        observeProfiles();
    }

    private void observeProfiles() {
        mViewModel.getProfiles().observe(this,profiles -> {
            listAdapter.submitList(profiles);
            b.lblEmptyView.setVisibility(profiles.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setUpViews() {

        listAdapter = new ListFragmentAdapter();
        listAdapter.setOnDeleteClickListener(position -> mViewModel.deleteUser(listAdapter.getItem(position)));
        listAdapter.setOnEditClickListener(position -> editProfile(listAdapter.getItem(position)));


        b.lstProfiles.setHasFixedSize(true);
        b.lstProfiles.setLayoutManager(new GridLayoutManager(getContext(),getResources().getInteger(R.integer.main_lstProfiles_columns)));
        b.lstProfiles.setItemAnimator(new DefaultItemAnimator());
        b.lstProfiles.setAdapter(listAdapter);

        b.btnFloat.setOnClickListener(v -> openProfile());
    }


    private void editProfile(User user){
        mainVM.setOpenProfile(true);
        mainVM.setSaved(true);
        mainVM.setUser(user);
    }

    public void openProfile(){
        mainVM.setSaved(false);
        mainVM.setOpenProfile(true);
        User user = new User(Database.getInstance().getDefaultAvatar(),"","","","",-1);
        mainVM.setUser(user);
    }

    private void setUpActionBar(){
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.app_name);
        }
    }


}
