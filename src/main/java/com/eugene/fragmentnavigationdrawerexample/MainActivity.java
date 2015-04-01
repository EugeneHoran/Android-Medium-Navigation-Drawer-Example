package com.eugene.fragmentnavigationdrawerexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
    FragmentOne.FragmentCallbacks, FragmentTwo.FragmentCallbacks, FragmentThree.FragmentCallbacks, RandomFragment.FragmentBackStack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
    }

    private DrawerLayout drawer;

    private void findViewsById() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    Fragment fragment = null;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (drawer != null) drawer.closeDrawer(Gravity.START);
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                fragment = new FragmentOne();
                args.putString("from_activity", "Fragment One");
                break;
            case 1:
                fragment = new FragmentTwo();
                args.putString("from_activity", "Fragment Two");
                break;
            case 2:
                fragment = new FragmentThree();
                args.putString("from_activity", "Fragment Three");
                break;
        }
        if (fragment != null) {
            fragment.setArguments(args); // Passing data to fragment
            fm.beginTransaction()
                .replace(R.id.container, fragment).commit();
        }
    }

    @Override
    public void menuClick() { // Getting data from fragment
        if (drawer != null) drawer.openDrawer(Gravity.START);
    }

    @Override
    public void openRandomFrag() {
        FragmentOne fragmentOne = new FragmentOne();
        fragment = new RandomFragment();
        fm.beginTransaction()
            .replace(R.id.container, fragment).addToBackStack(null).commit();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void menuPopBack() {
        fm.popBackStack();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onBackPressed() {
        if (fragment != new RandomFragment()) {
            super.onBackPressed();
        } else {
            fm.popBackStack();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
