package fsoft.training.movieapplication.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.constant.Constants;

/**
 * Created by mac on 10/24/17.
 */

public class AboutFragment extends Fragment {
    private WebView mWebView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_about, container, false);
        mWebView = rootview.findViewById(R.id.about_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(Constants.URL_ABOUT);
        return rootview;
    }
}
