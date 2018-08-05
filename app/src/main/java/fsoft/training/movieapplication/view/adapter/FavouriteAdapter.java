package fsoft.training.movieapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.application.utils.AppSingleton;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.listener.FavouritePresenterListener;
import fsoft.training.movieapplication.presentation.FavouritePresenter;

import static android.content.ContentValues.TAG;
import static fsoft.training.movieapplication.application.utils.AppSingleton.typeLayout;

/**
 * Created by mac on 10/2/17.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MovieViewHolder> implements FavouritePresenterListener {
    /**
     * variable Init recyclerview
     */
    private List<MovieDto> mMovieDtos;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private FavouritePresenter mFavouritePresenter;

    public boolean isTypeLayout() {
        return typeLayout;
    }

    /**
     * contructor
     *
     * @param mContext
     * @param movieList
     */
    public FavouriteAdapter(Context mContext, List<MovieDto> movieList) {
        this.mContext = mContext;
        this.mMovieDtos = movieList;
        mFavouritePresenter = new FavouritePresenter(this);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: " + mMovieDtos);
        MovieViewHolder userViewHolder = null;
        if (holder instanceof MovieViewHolder) {
            final MovieDto movie = mMovieDtos.get(position);
            userViewHolder = (MovieViewHolder) holder;
            userViewHolder.tvTitle.setText(movie.title);
            userViewHolder.tvReleaseDate.setText(movie.releaseDate);
            userViewHolder.tvRating.setText(movie.voteAverage + "/10.0");
            userViewHolder.tvOverView.setText(movie.overview);
            Picasso.with(mContext)
                    .load(Constants.URL_BASE_IMAGE + movie.posterPath)
                    .error(R.mipmap.ic_home)      // optional
                    .resize(119, 131)
                    .into(userViewHolder.imgPoster);
            if (movie.adult) {
                userViewHolder.imgAdult.setImageResource(R.mipmap.ic_adult);
            } else {
                userViewHolder.imgAdult.setVisibility(View.GONE);
            }
            userViewHolder.imgReward.setImageResource(R.mipmap.ic_start_selected);

            userViewHolder.imgReward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFavouritePresenter.requestDeleteFavourite(movie, mContext);
                    mMovieDtos.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMovieDtos == null ? 0 : mMovieDtos.size();
    }

    public void setmMovieDtos(List<MovieDto> mMovieDtos) {
        this.mMovieDtos = mMovieDtos;
    }

    @Override
    public void resultAdd(String success) {

    }

    @Override
    public void resultCheckIsFavourite(boolean isFavourite) {

    }

    @Override
    public void resultDelete(String result) {
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resultListFavourite(List<MovieDto> movieDtoList) {

    }

    public void setFilter(List<MovieDto> mList) {
        mMovieDtos = new ArrayList<>();
        mMovieDtos.addAll(mList);
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseDate, tvRating, tvOverView, tvTitleGrid;
        ImageView imgPoster, imgAdult, imgReward, imgPosterGrid;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tvReleaseDate);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvOverView = (TextView) itemView.findViewById(R.id.tvOverView);
            imgPoster = (ImageView) itemView.findViewById(R.id.imgPoster);
            imgAdult = (ImageView) itemView.findViewById(R.id.imgAdult);
            imgReward = (ImageView) itemView.findViewById(R.id.imgReward);
            tvTitleGrid = (TextView) itemView.findViewById(R.id.tvTitleGrid);
            imgPosterGrid = (ImageView) itemView.findViewById(R.id.imgPosterGrid);

        }
    }
}
