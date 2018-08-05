package fsoft.training.movieapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.application.utils.AppSingleton;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.data.sqlite.FavouriteHelper;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.listener.FavouritePresenterListener;
import fsoft.training.movieapplication.listener.OnItemClickListener;
import fsoft.training.movieapplication.presentation.FavouritePresenter;

/**
 * Created by ManhND16 on 9/21/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FavouritePresenterListener {
    /**
     * variable Init recyclerview
     */
    private List<MovieDto> mMovieList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private FavouritePresenter mFavouritePresenter;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private RecyclerView mRecyclerView;
    private boolean mTypeLayout;
    private boolean mIsCheckFavourite = false;
    private OnItemClickListener mOnItemClickListener;
    private FavouriteHelper mFavouriteHelper;

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public boolean isTypeLayout() {
        return mTypeLayout;
    }

    public void setTypeLayout(boolean typeLayout) {
        this.mTypeLayout = typeLayout;
    }

    /**
     * contructor
     *
     * @param mContext
     * @param movieList
     * @param recyclerView
     * @param typeLayout
     */
    public MoviesAdapter(Context mContext, List<MovieDto> movieList, RecyclerView recyclerView, boolean typeLayout) {
        this.mContext = mContext;
        this.mMovieList = movieList;
        this.mRecyclerView = recyclerView;
        this.mTypeLayout = typeLayout;

        mTypeLayout = typeLayout;
        if (typeLayout) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        }
        mFavouritePresenter = new FavouritePresenter(this);
        mFavouriteHelper = new FavouriteHelper(mContext);
    }

    /**
     * method get item view type
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mMovieList.get(position) == null) {
            return VIEW_TYPE_LOADING;
        }
        return mMovieList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_TYPE_ITEM) {
            if (mTypeLayout) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
                vh = new MovieViewHolder(view);
            } else {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_list_collection, parent, false);
                vh = new MovieViewHolder(view);
            }

        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MovieViewHolder userViewHolder = null;
        LoadingViewHolder loadingViewHolder = null;
        if (mTypeLayout) {
            final MovieDto movie = mMovieList.get(position);
            userViewHolder = (MovieViewHolder) holder;
            userViewHolder.tvTitle.setText(movie.title.toString());
            userViewHolder.tvReleaseDate.setText(movie.releaseDate.toString());
            userViewHolder.tvRating.setText(movie.voteAverage.toString() + "/10.0");
            userViewHolder.tvOverView.setText(movie.overview.toString());
            Picasso.with(mContext)
                    .load(Constants.URL_BASE_IMAGE + movie.posterPath)
                    .error(R.mipmap.ic_home)      // optional
                    .resize(110, 120)
                    .into(userViewHolder.imgPoster);
            if (movie.adult) {
                userViewHolder.imgAdult.setImageResource(R.mipmap.ic_adult);
            } else {
                userViewHolder.imgAdult.setVisibility(View.GONE);
            }
            mFavouritePresenter.requestCheckIsFavourite(movie.id, mContext);
            movie.isClicked = mIsCheckFavourite;
            final MovieViewHolder finalUserViewHolder = userViewHolder;
            if (movie.isClicked) {
                finalUserViewHolder.imgReward.setImageResource(R.mipmap.ic_start_selected);
            } else {
                finalUserViewHolder.imgReward.setImageResource(R.mipmap.ic_star_border_black);
            }

            userViewHolder.imgReward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (movie.isClicked) {
                        mFavouritePresenter.requestDeleteFavourite(movie, mContext);
                        finalUserViewHolder.imgReward.setImageResource(R.mipmap.ic_star_border_black);
                        AppSingleton.sendDataCount(mContext, mFavouriteHelper);
                        movie.isClicked = false;
                    } else {
                        finalUserViewHolder.imgReward.setImageResource(R.mipmap.ic_start_selected);
                        mFavouritePresenter.requestAddFavourite(movie, mContext);
                        AppSingleton.sendDataCount(mContext, mFavouriteHelper);
                        movie.isClicked = true;
                    }
                }
            });
        } else {
            MovieDto movie = mMovieList.get(position);
            userViewHolder = (MovieViewHolder) holder;
            userViewHolder.tvTitleGrid.setText(movie.title.toString());
            Picasso.with(mContext)
                    .load(Constants.URL_BASE_IMAGE + movie.posterPath)
                    .error(R.mipmap.ic_home)      // optional
                    .resize(110, 120)
                    .into(userViewHolder.imgPosterGrid);
        }

        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSingleton.isClickDetail = true;
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(view, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    @Override
    public void resultAdd(String success) {
        Toast.makeText(mContext, success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resultCheckIsFavourite(boolean isFavourite) {
        this.mIsCheckFavourite = isFavourite;
    }

    @Override
    public void resultDelete(String result) {
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resultListFavourite(List<MovieDto> movieDtoList) {

    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarLoadmore);
        }

    }

    private static class MovieViewHolder extends RecyclerView.ViewHolder {
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
