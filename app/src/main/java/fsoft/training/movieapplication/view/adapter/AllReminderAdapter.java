package fsoft.training.movieapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;

/**
 * Created by mac on 9/29/17.
 */

public class AllReminderAdapter extends RecyclerView.Adapter<AllReminderAdapter.ViewHolder> {
    private List<ReminderDto> mReminderList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    public int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // each data item is just a string in this case
        public TextView tvTitleRemindAndRating;
        public TextView tvDateTime;
        private ImageView imgPoster;
        private ImageView imgIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitleRemindAndRating = (TextView) itemView.findViewById(R.id.title_reminder_and_rating_all);
            tvDateTime = (TextView) itemView.findViewById(R.id.date_time_reminder_all_tv);
            imgPoster = (ImageView) itemView.findViewById(R.id.poster_reminder_img);
            imgIcon = (ImageView) itemView.findViewById(R.id.icon_img);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(Menu.NONE, R.id.delete_item, Menu.NONE, "Delete");//groupId, itemId, order, title
            contextMenu.add(Menu.NONE, R.id.cancel_item, Menu.NONE, "Cancel");
        }

    }


    public AllReminderAdapter(List<ReminderDto> reminderList, Context mContext) {
        this.mReminderList = reminderList;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.v("viewholderbind", "onBindViewHolder");
        ReminderDto reminder = mReminderList.get(position);
        double rating = ((double) Math.round(reminder.voteCountAverage * 10) / 10);
        holder.tvTitleRemindAndRating.setText(reminder.movieName + "-" + rating + "/10");
        String[] parts = reminder.reminderTime.split(",");
        String part1 = parts[0];
        String part2 = parts[1];
        holder.tvDateTime.setText(part1 + " " + part2);
        Picasso.with(mContext)
                .load(Constants.URL_BASE_IMAGE + reminder.posterPath)
                .error(R.mipmap.ic_home)      // optional
                .into(holder.imgPoster);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(position);
                return false;
            }
        });
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
        View item = mLayoutInflater.inflate(R.layout.reminder_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(item);
        Log.v("viewholdercreate", "ViewHolderOnCreate");
        return vh;
    }

}
