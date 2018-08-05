package fsoft.training.movieapplication.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.data.sqlite.ReminderHelper;
import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;
import fsoft.training.movieapplication.listener.ReminderListPresenterListener;
import fsoft.training.movieapplication.presentation.ReminderListPresenter;
import fsoft.training.movieapplication.view.adapter.AllReminderAdapter;

/**
 * Created by mac on 9/29/17.
 */

public class AllRemindersFragment extends Fragment implements ReminderListPresenterListener{

    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private RecyclerView mRecyclerView;
    private AllReminderAdapter mAdapter;
    private List<ReminderDto> mReminderList;
    private ReminderHelper mReminderHelper;
    int position = -1;
    private ReminderListPresenter mReminderListPresenter;

    public AllRemindersFragment() {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_reminder, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_reminder_recycler);
        loadDataToList();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        try {

            position = mAdapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.delete_item:
                // do your stuff
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(Constants.MESSAGE_REMOVE_CONFIRM)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println("DELETE" + mReminderList.get(position));
                                mReminderListPresenter.requestDeleteReminderToResponsitory(mReminderList.get(position),getContext());
                                loadDataToList();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete");
                alert.show();
                break;
            case R.id.cancel_item:
                // do your stuff
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.change_view_actionbar).setVisible(false);
    }


    @Override
    public void resultListFromPresenter(List<ReminderDto> reminderDtoList) {
        this.mReminderList = reminderDtoList;
    }

    @Override
    public void resultRemoveReminderFromPresenter(String result) {
        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
    }
    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Set data to List Reminder All
     */
    private void loadDataToList() {
        mReminderList = new ArrayList<>();
        mReminderListPresenter = new ReminderListPresenter(this);
        mReminderListPresenter.requestListReminderToResponsitory(getContext());
        mAdapter = new AllReminderAdapter(mReminderList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
