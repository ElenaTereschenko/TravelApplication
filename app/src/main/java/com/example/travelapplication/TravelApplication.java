package com.example.travelapplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TravelApplication extends Application {
    Realm mRealm;
    RealmConfiguration config;

    @Override public void onCreate() {
        super.onCreate();

        Realm.init(this);
        config = new RealmConfiguration.Builder()
                .schemaVersion(1 )
                .migration(new TravelMigration())
                .build();

        mRealm = Realm.getInstance(config);
    }

    public Realm getRealm(){
        return mRealm;
    }
}

