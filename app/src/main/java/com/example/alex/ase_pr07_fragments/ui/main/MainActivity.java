package com.example.alex.ase_pr07_fragments.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.alex.ase_pr07_fragments.R;
import com.example.alex.ase_pr07_fragments.data.model.Avatar;
import com.example.alex.ase_pr07_fragments.data.model.User;
import com.example.alex.ase_pr07_fragments.ui.avatar.AvatarFragment;
import com.example.alex.ase_pr07_fragments.ui.list.ListFragment;
import com.example.alex.ase_pr07_fragments.ui.profile.ProfileFragment;
import com.example.alex.ase_pr07_fragments.ui.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if (getSupportFragmentManager().findFragmentByTag(ListFragment.class.getSimpleName()) == null) {
            loadListFragment();
        }
        mViewModel.getUser().observe(this, this::openProfileFragment);
        mViewModel.getAvatar().observe(this, this::openAvatarFragment);

    }

    private void loadListFragment() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flActivity, new ListFragment(), ListFragment.class.getSimpleName());
    }

    private void openProfileFragment(User user) {
        if (getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName()) == null && mViewModel.isOpenProfile())
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flActivity, ProfileFragment.newInstance(user), ProfileFragment.class.getSimpleName(),
                    ProfileFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    private void openAvatarFragment(Avatar avatar) {
        if (getSupportFragmentManager().findFragmentByTag(AvatarFragment.class.getSimpleName()) == null && mViewModel.isOpenAvatar()) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flActivity, AvatarFragment.newInstance(avatar), AvatarFragment.class.getSimpleName(),
                    AvatarFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            mViewModel.setAvatarChanged(false);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
