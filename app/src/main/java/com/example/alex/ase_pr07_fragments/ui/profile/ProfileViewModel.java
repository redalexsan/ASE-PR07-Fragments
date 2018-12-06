package com.example.alex.ase_pr07_fragments.ui.profile;

import com.example.alex.ase_pr07_fragments.data.Database;
import com.example.alex.ase_pr07_fragments.data.DatabaseProfiles;
import com.example.alex.ase_pr07_fragments.data.model.Avatar;
import com.example.alex.ase_pr07_fragments.data.model.User;

import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private Avatar avatar;
    private final Database database = Database.getInstance();
    private final DatabaseProfiles data = DatabaseProfiles.getInstance();

    public ProfileViewModel(){
        avatar = database.getDefaultAvatar();
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void addEditedProfile(User profile){
        data.addEditedProflie(profile);
    }

    public void addNewProfile(User profile){
        data.addProflie(profile);
    }
}
