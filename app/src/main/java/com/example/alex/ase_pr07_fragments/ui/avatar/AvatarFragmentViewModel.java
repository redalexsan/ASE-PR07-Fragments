package com.example.alex.ase_pr07_fragments.ui.avatar;

import com.example.alex.ase_pr07_fragments.data.Database;
import com.example.alex.ase_pr07_fragments.data.model.Avatar;

import androidx.lifecycle.ViewModel;

public class AvatarFragmentViewModel extends ViewModel {

    private final Database database = Database.getInstance();
    private Avatar avatar;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(int position) {
        avatar = database.queryAvatars().get(position);
    }

    public void setAvatar(Avatar avatar){
        this.avatar = avatar;
    }

    public Avatar showSelectedAvatar(){
        for(Avatar o: database.queryAvatars())
            if(avatar.getId() == o.getId())
               return o;
         return database.getDefaultAvatar();
    }

}
