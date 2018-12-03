package com.example.alex.ase_pr07_fragments.ui.list;

import com.example.alex.ase_pr07_fragments.data.DatabaseProfiles;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class ListFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final DatabaseProfiles databaseProfiles;
    public ListFragmentViewModelFactory(DatabaseProfiles databaseProfiles) {
        this.databaseProfiles = databaseProfiles;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListFragmentViewModel(databaseProfiles);
    }
}
