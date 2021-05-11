package com.digimaster.digicourse.digicyber.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Toolbar;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.util.OnBackPressedListener;

public abstract class BaseFragment extends Fragment implements View.OnClickListener, OnBackPressedListener {

    protected FloatingActionButton fab;
    protected Toolbar toolbar;
    protected ActionBar actionBar;
    protected ActionBarDrawerToggle toggle;
    protected DrawerLayout drawer;
    protected boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = ((HomeActivity) getActivity()).findViewById(R.id.toolbar);
        actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        fab.setOnClickListener(this);
    }

    /**
     * Simplify fragment replacing in child fragments
     */
    protected void replaceFragment(@NonNull Fragment fragment) {
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        fm.beginTransaction().replace(R.id.screen_area, fragment).commit();
    }

    // hide FAB button
    protected void hideFab() {
        fab.hide();
    }

    //show FAB button
    protected void showFab() {
        fab.show();
    }

    /**
     * Shows Home button as Back button
     * Took from here {@link}https://stackoverflow.com/a/36677279/9381524
     * <p>
     * To keep states of ActionBar and ActionBarDrawerToggle synchronized,
     * when you enable on one, you disable on the other.
     * And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT!!!
     *
     * @param show = true to show <showHomeAsUp> or show = false to show <Hamburger> button
     */
    protected void showBackButton(boolean show) {

        if (show) {
            // Remove hamburger
            toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(v -> onBackPressed());
                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            // Remove back button
            actionBar.setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
        // So, one may think "Hmm why not simplify to:
        // .....
        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        // mDrawer.setDrawerIndicatorEnabled(!enable);
        // ......
        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
    }

    /**
     * Simplify setTitle in child fragments
     */
    protected void setTitle(int resId) {
        getActivity().setTitle(getResources().getString(resId));
    }

    //
    @Override
    public abstract void onClick(View v);

    // Handles BackPress events from MainActivity
    @Override
    public abstract void onBackPressed();
}
