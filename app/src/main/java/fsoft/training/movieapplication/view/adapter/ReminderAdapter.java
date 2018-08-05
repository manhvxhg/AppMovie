package fsoft.training.movieapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;

/**
 * Created by mac on 9/29/17.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private List<ReminderDto> mReminderList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitleRemindAndRating;
        public TextView tvDateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitleRemindAndRating = (TextView) itemView.findViewById(R.id.nav_reminder_list_title_tv);
            tvDateTime = (TextView) itemView.findViewById(R.id.nav_reminder_list_date_time_tv);
        }
    }


    public ReminderAdapter(List<ReminderDto> reminderList, Context mContext) {
        this.mReminderList = reminderList;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.v("viewholderbind", "onBindViewHolder");
        ReminderDto reminder = mReminderList.get(position);
        double rating = ((double) Math.round(reminder.voteCountAverage * 10) / 10);
        holder.tvTitleRemindAndRating.setText(reminder.movieName + "-" + rating + "/10");
        String[] parts = reminder.reminderTime.split(",");
        String part1 = parts[0];
        String part2 = parts[1];
        holder.tvDateTime.setText(part1 + " " + part2);
    }

    @Override
    public int getItemCount() {
        return mReminderList.size();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View item = mLayoutInflater.inflate(R.layout.nav_reminder_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(item);
        return vh;
    }
}
