package com.example.alex.ase_pr07_fragments.ui.list;

import com.example.alex.ase_pr07_fragments.data.DatabaseProfiles;
import com.example.alex.ase_pr07_fragments.data.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ListFragmentViewModel extends ViewModel {
    private final DatabaseProfiles data;
    private LiveData<List<User>> users;

    public ListFragmentViewModel (DatabaseProfiles data) {
        this.data = data;
        this.users = data.getProfiles();
    }

    public LiveData<List<User>> getProfiles(){
        return users;
    }

    public void deleteUser(User profile){
        data.deleteProflie(profile);
    }

    public void addEditedProfile(User profile, int position){
        data.addEditedProflie(profile,position);
    }

    public void addNewProfile(User profile){
        data.addProflie(profile);
    }

}
