package com.example.alex.ase_pr07_fragments.ui.main;

import com.example.alex.ase_pr07_fragments.data.model.Avatar;
import com.example.alex.ase_pr07_fragments.data.model.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<Avatar> avatar = new MutableLiveData<>();
    private boolean isSaved;
    private boolean avatarChanged = false;
    private boolean openProfile = false;
    private boolean openAvatar = false;

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public LiveData<Avatar> getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.setValue(avatar);
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public boolean isOpenProfile() {
        return openProfile;
    }

    public void setOpenProfile(boolean openProfile) {
        this.openProfile = openProfile;
    }

    public boolean isOpenAvatar() {
        return openAvatar;
    }

    public void setOpenAvatar(boolean openAvatar) {
        this.openAvatar = openAvatar;
    }

    public boolean isAvatarChanged() {
        return avatarChanged;
    }

    public void setAvatarChanged(boolean avatarChanged) {
        this.avatarChanged = avatarChanged;
    }
}
