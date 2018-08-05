package fsoft.training.movieapplication.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import fsoft.training.movieapplication.R;

import static android.content.ContentValues.TAG;

/**
 * Created by mac on 10/24/17.
 */

public class SettingEmptyFragment extends Fragment {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private FrameLayout mFrameLayout;
    private boolean check_reminder;

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    public SettingEmptyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_empty, container, false);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.frame_home);
        Bundle bundle = getArguments();
        if (bundle != null) {
            check_reminder = bundle.getBoolean("check_reminder", false);
            Log.d(TAG, "onCreateView: " + check_reminder);
        }
        if (check_reminder) {
            AllRemindersFragment allRemindersFragment = new AllRemindersFragment();
            switchContent(mFrameLayout.getId(), allRemindersFragment);
        } else {
            replaceSetting();
        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment);
        ft.commit();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.change_view_actionbar).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isAdded()) {
        }
    }

    /**
     * @param idFrame
     * @param fragment
     */
    public void replaceFragment(int idFrame, Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(idFrame, fragment);
        transaction.addToBackStack("HomeMovieFragment");
        transaction.commit();
    }
    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    /**
     * replace fragment setting
     */
    private void replaceSetting() {
        SettingFragment settingFragment = SettingFragment.getInstance();
        replaceFragment(mFrameLayout.getId(), settingFragment);
    }

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////

}
