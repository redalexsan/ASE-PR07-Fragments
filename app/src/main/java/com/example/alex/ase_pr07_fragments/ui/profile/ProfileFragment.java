package com.example.alex.ase_pr07_fragments.ui.profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.ase_pr07_fragments.R;
import com.example.alex.ase_pr07_fragments.data.model.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.alex.ase_pr07_fragments.databinding.FragmentProfileBinding;
import com.example.alex.ase_pr07_fragments.ui.main.MainActivityViewModel;
import com.example.alex.ase_pr07_fragments.ui.utils.IntentsUtils;
import com.example.alex.ase_pr07_fragments.ui.utils.KeyboardUtils;
import com.example.alex.ase_pr07_fragments.ui.utils.NetworkUtils;
import com.example.alex.ase_pr07_fragments.ui.utils.ValidationUtils;
import com.google.android.material.snackbar.Snackbar;


public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "ARG_USER";
    private FragmentProfileBinding b;
    private ProfileViewModel profileVM;
    private MainActivityViewModel mainVM;
    private User user;

    public static ProfileFragment newInstance(User user) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER, user);
        profileFragment.setArguments(bundle);

        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_profile, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileVM = ViewModelProviders.of(this).get(ProfileViewModel.class);
        mainVM = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        setUpActionBar();
        user = getArguments().getParcelable(ARG_USER);
        initViews();
        if (mainVM.isSaved())
            filldata();
        isChangedAvatar();
    }

    private void filldata() {
        profileVM.setAvatar(user.getAvatar());
        b.lblAvatar.setText(user.getAvatar().getName());
        b.imgAvatar.setImageResource(user.getAvatar().getImageResId());
        b.formProflie.txtName.setText(user.getName());
        b.formProflie.txtAddress.setText(user.getAdress());
        b.formProflie.txtEmail.setText(user.getMail());
        b.formProflie.txtPhonenumber.setText(String.valueOf(user.getPhoneNumer()));
        b.formProflie.txtWeb.setText(user.getWeb());
    }

    private void isChangedAvatar() {
        if (mainVM.isAvatarChanged()) {
            profileVM.setAvatar(mainVM.getAvatar().getValue());
            b.lblAvatar.setText(profileVM.getAvatar().getName());
            b.imgAvatar.setImageResource(profileVM.getAvatar().getImageResId());
            mainVM.setAvatarChanged(false);
        }
    }

    private void initViews() {

        b.lblAvatar.setText(user.getAvatar().getName());
        b.imgAvatar.setImageResource(user.getAvatar().getImageResId());

        b.imgAvatar.setOnClickListener(v -> {
            mainVM.setOpenAvatar(true);
            mainVM.setAvatar(profileVM.getAvatar());
        });

        b.lblAvatar.setOnClickListener(v -> {
            mainVM.setOpenAvatar(true);
            mainVM.setAvatar(profileVM.getAvatar());
        });

        b.formProflie.imgEmail.setOnClickListener(v -> startIntents(IntentsUtils.newMessage(b.formProflie.txtEmail.getText().toString()), true));
        b.formProflie.imgPhonenumber.setOnClickListener(v -> startIntents(IntentsUtils.newDialIntent(b.formProflie.txtPhonenumber.getText().toString()), false));
        b.formProflie.imgAddress.setOnClickListener(v -> startIntents(IntentsUtils.newSearchInMap(b.formProflie.txtAddress.getText().toString()), true));
        b.formProflie.imgWeb.setOnClickListener(v -> startIntents(IntentsUtils.newSearchInWeb(b.formProflie.txtWeb.getText().toString()), true));

        b.formProflie.txtName.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(b.formProflie.lblName, hasFocus));

        b.formProflie.txtEmail.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(b.formProflie.lblEmail, hasFocus));

        b.formProflie.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(b.formProflie.lblPhonenumber, hasFocus));

        b.formProflie.txtAddress.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(b.formProflie.lblAddress, hasFocus));

        b.formProflie.txtWeb.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(b.formProflie.lblWeb, hasFocus));

        b.formProflie.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                request(b.formProflie.txtName, b.formProflie.lblName);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        b.formProflie.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                request(b.formProflie.txtEmail, b.formProflie.lblEmail, b.formProflie.imgEmail);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        b.formProflie.txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                request(b.formProflie.txtPhonenumber, b.formProflie.lblPhonenumber, b.formProflie.imgPhonenumber);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        b.formProflie.txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                request(b.formProflie.txtAddress, b.formProflie.lblAddress, b.formProflie.imgAddress);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        b.formProflie.txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                request(b.formProflie.txtWeb, b.formProflie.lblWeb, b.formProflie.imgWeb);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        b.formProflie.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            save();
            return true;
        });
    }

    private void setUpActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    private void startIntents(Intent intent, boolean connection) {
        if (connection) {
            if (NetworkUtils.isConnectionAvailable(requireContext()))
                startActivity(intent);
        } else startActivity(intent);
    }

    private boolean request(EditText text, TextView textView, ImageView image) {
        boolean resultado = true;

        if (text.getId() == b.formProflie.txtEmail.getId()) {
            if (!ValidationUtils.isValidEmail(String.valueOf(text.getText())))
                resultado = false;
        } else if (text.getId() == b.formProflie.txtPhonenumber.getId()) {
            if (!ValidationUtils.isValidPhone(String.valueOf(text.getText())))
                resultado = false;
        } else if (text.getId() == b.formProflie.txtWeb.getId()) {
            if (!ValidationUtils.isValidUrl(String.valueOf(text.getText())))
                resultado = false;
        } else if (String.valueOf(text.getText()).equals(""))
            resultado = false;


        if (!resultado) {
            text.setError(getString(R.string.main_invalid_data));
            textView.setEnabled(false);
            image.setEnabled(false);
        } else {
            text.setError(null);
            textView.setEnabled(true);
            image.setEnabled(true);
        }

        return resultado;
    }

    private boolean request(EditText text, TextView textView) {

        if (String.valueOf(text.getText()).equals("")) {
            text.setError(getString(R.string.main_invalid_data));
            textView.setEnabled(false);
            return false;
        } else {
            text.setError(null);
            textView.setEnabled(true);
            return true;
        }

    }

    private void setTypeFaceTxt(TextView text, boolean hasfocus) {
        if (hasfocus)
            text.setTypeface(Typeface.DEFAULT_BOLD);
        else
            text.setTypeface(Typeface.DEFAULT);
    }

    public boolean validar() {
        boolean resultado1, resultado2, resultado3, resultado4, resultado5;

        resultado1 = request(b.formProflie.txtName, b.formProflie.lblName);
        resultado2 = request(b.formProflie.txtEmail, b.formProflie.lblEmail, b.formProflie.imgEmail);
        resultado3 = request(b.formProflie.txtPhonenumber, b.formProflie.lblPhonenumber, b.formProflie.imgPhonenumber);
        resultado4 = request(b.formProflie.txtAddress, b.formProflie.lblAddress, b.formProflie.imgAddress);
        resultado5 = request(b.formProflie.txtWeb, b.formProflie.lblWeb, b.formProflie.imgWeb);

        return resultado1 && resultado2 && resultado3 && resultado4 && resultado5;

    }

    private void changeData() {
        user.setName(b.formProflie.txtName.getText().toString());
        user.setAdress(b.formProflie.txtAddress.getText().toString());
        user.setPhoneNumer(Integer.parseInt(b.formProflie.txtPhonenumber.getText().toString()));
        user.setWeb(b.formProflie.txtWeb.getText().toString());
        user.setMail(b.formProflie.txtEmail.getText().toString());
        user.setAvatar(profileVM.getAvatar());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            mainVM.setOpenProfile(false);
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        KeyboardUtils.hideSoftKeyboard(requireActivity());

        if (validar()) {
            changeData();
            if (mainVM.isSaved()) {
                profileVM.addEditedProfile(user);
                mainVM.setSaved(false);
            } else
                profileVM.addNewProfile(user);
            requireActivity().getSupportFragmentManager().popBackStack();
        } else
            Snackbar.make(b.lblAvatar, getString(R.string.main_error_saving), Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onDetach() {
        mainVM.setOpenProfile(false);
        super.onDetach();
    }
}
