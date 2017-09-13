package io.edgedev.andelaproject;

/**
 * Created by OPEYEMI OLORUNLEKE on 9/13/2017.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OPEYEMI OLORUNLEKE on 9/12/2017.
 */

public class Singleton {
    private static Singleton mSingleton;
    private List<Developer> mDevelopers;

    private Singleton() {
        mDevelopers = new ArrayList<>();
    }

    public static Singleton newInstance() {
        if (mSingleton == null) {
            mSingleton = new Singleton();
        }
        return mSingleton;
    }

    public void setDevelopers(List<Developer> developers) {
        mDevelopers = developers;
    }

    public int size(){
        return mDevelopers.size();
    }

    public List<Developer> getDevelopers() {
        return mDevelopers;
    }

    public Developer findDeveloper(int id){

        for (Developer developer :
                mDevelopers) {
            if (developer.getId() ==id){
                return developer;
            }
        }
        return null;
    }
}