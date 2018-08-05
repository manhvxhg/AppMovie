package fsoft.training.movieapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CastDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CrewDto;

/**
 * Created by ManhND16 on 9/27/2017.
 */

public class CastAndCrewAdapter extends RecyclerView.Adapter<CastAndCrewAdapter.ViewHolder> {
    private List<CastDto> mCastList;
    private List<CrewDto> mCrewList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitleCastCrew;
        public ImageView imgPosterCastCrew;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitleCastCrew = (TextView) itemView.findViewById(R.id.title_cast_txt);
            imgPosterCastCrew = (ImageView) itemView.findViewById(R.id.poster_cast_img);
        }
    }


    public CastAndCrewAdapter(List<CastDto> castList,List<CrewDto> crewList, Context mContext) {
        this.mCastList = castList;
        this.mCrewList = crewList;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.v("viewholderbind","onBindViewHolder");
        CastDto cast = mCastList.get(position);
        CrewDto crew = mCrewList.get(position);

        if (crew.profilePath != null) {
            holder.tvTitleCastCrew.setText(crew.name);
            Picasso.with(mContext)
                    .load(Constants.URL_BASE_IMAGE + crew.profilePath)
                    .error(R.mipmap.ic_home)      // optional
                    .resize(110, 120)
                    .into(holder.imgPosterCastCrew);
            Log.d("profileImage",Constants.URL_BASE_IMAGE + crew.profilePath+"&&&&&&&&&&&&&&"+crew.name);
        }else if (cast.profilePath != null){
            holder.tvTitleCastCrew.setText(cast.name);
            Picasso.with(mContext)
                    .load(Constants.URL_BASE_IMAGE + cast.profilePath)
                    .error(R.mipmap.ic_home)      // optional
                    .resize(110, 120)
                    .into(holder.imgPosterCastCrew);
            Log.d("profileImage",Constants.URL_BASE_IMAGE + crew.profilePath+"&&&&&&&&&&&&&&"+crew.name);
        }else {
            holder.imgPosterCastCrew.setImageResource(R.mipmap.ic_launcher);
        }
    }
    @Override
    public int getItemCount() {
        return mCastList.size() > mCrewList.size()? mCrewList.size(): mCastList.size();
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View item =mLayoutInflater.inflate(R.layout.cast_crew_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(item);
        Log.v("viewholdercreate","ViewHolderOnCreate");
        return vh;
    }


}