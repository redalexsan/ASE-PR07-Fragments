package com.example.alex.ase_pr07_fragments.ui.avatar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.ase_pr07_fragments.R;
import com.example.alex.ase_pr07_fragments.data.Database;
import com.example.alex.ase_pr07_fragments.data.model.Avatar;
import com.example.alex.ase_pr07_fragments.databinding.FragmentAvatarBinding;
import com.example.alex.ase_pr07_fragments.ui.main.MainActivityViewModel;
import com.example.alex.ase_pr07_fragments.ui.utils.ResourcesUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AvatarFragment extends Fragment {

    private static String ARG_AVATAR = "ARG_AVATAR";
    private AvatarFragmentViewModel avatarVM;
    private MainActivityViewModel mainVM;
    private FragmentAvatarBinding b;
    private int positionImageSelected;
    private ImageView images[];
    private TextView labels[];

    public static AvatarFragment newInstance(Avatar avatar) {
        AvatarFragment avatarFragment = new AvatarFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_AVATAR, avatar);

        avatarFragment.setArguments(bundle);

        return avatarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            b = FragmentAvatarBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        avatarVM = ViewModelProviders.of(this).get(AvatarFragmentViewModel.class);
        mainVM = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        avatarVM.setAvatar(getArguments().getParcelable(ARG_AVATAR));

        setUpActionBar();
        initViews();
        initAvatars();
        showSelectedAvatar();
    }

    private void initViews() {
        images = new ImageView[]{b.imgAvatar1, b.imgAvatar2, b.imgAvatar3, b.imgAvatar4, b.imgAvatar5, b.imgAvatar6};
        labels = new TextView[]{b.lblAvatar1, b.lblAvatar2, b.lblAvatar3, b.lblAvatar4, b.lblAvatar5, b.lblAvatar6};

        b.imgAvatar1.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar1));
        b.imgAvatar2.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar2));
        b.imgAvatar3.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar3));
        b.imgAvatar4.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar4));
        b.imgAvatar5.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar5));
        b.imgAvatar6.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar6));
        b.lblAvatar1.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar1));
        b.lblAvatar2.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar2));
        b.lblAvatar3.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar3));
        b.lblAvatar4.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar4));
        b.lblAvatar5.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar5));
        b.lblAvatar6.setOnClickListener(v -> getSelectedAvatar(b.imgAvatar6));

    }

    private void initAvatars() {
        Avatar avatar;
        for (int i = 0; i < images.length; i++) {
            avatar = Database.getInstance().queryAvatars().get(i);
            images[i].setImageResource(avatar.getImageResId());
            labels[i].setText(avatar.getName());
        }
    }

    private void showSelectedAvatar() {
        Avatar avatarSelected = avatarVM.showSelectedAvatar();
        selectImageView(images[Database.getInstance().queryAvatars().indexOf(avatarSelected)]);
        positionImageSelected = Database.getInstance().queryAvatars().indexOf(avatarSelected);
    }

    private void selectImageView(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(requireContext(), R.dimen.avatar_selected_image_alpha));
    }

    private void getSelectedAvatar(ImageView image) {

        for (int i = 0; i < Database.getInstance().queryAvatars().size(); i++) {
            if (image.getId() == images[i].getId()) {
                unselecImage(positionImageSelected);
                selectImageView(image);
                avatarVM.setAvatar(i);
                positionImageSelected = i;
            }
        }
    }

    private void unselecImage(int unselecImagePosition) {
        images[unselecImagePosition].setAlpha(ResourcesUtils.getFloat(requireContext(), R.dimen.avatar_not_selected_image_alpha));
    }

    private void setUpActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            mainVM.setAvatar(avatarVM.getAvatar());
            mainVM.setOpenAvatar(false);
            requireActivity().getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_avatar,menu);
    }
}

