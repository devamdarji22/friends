package com.jadd.friends.ViewModel;

import android.app.Application;
import android.content.Context;

import com.jadd.friends.Classes.User;
import com.jadd.friends.Repository.Repo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NameListViewModel extends AndroidViewModel{
    MutableLiveData<ArrayList<User>> names;
    private User leader;

    public NameListViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Context context){

        if(names!=null){
            return;
        }

        names = Repo.getInsatnce(context).getNames();
        leader = Repo.getInsatnce(context).getLeader();
    }

    public LiveData<ArrayList<User>> getNames(){
        return  names;
    }
    public User getLeader(){
        return leader;
    }
}
