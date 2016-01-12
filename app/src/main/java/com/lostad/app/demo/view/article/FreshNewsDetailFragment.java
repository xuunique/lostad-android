package com.lostad.app.demo.view.article;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.FreshNews;
import com.victor.loading.rotate.RotateLoading;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 图文混排上，并加入样式
 */
public class FreshNewsDetailFragment extends BaseFragment {
    public static final String DATA_FRESH_NEWS = "DATA_FRESH_NEWS";

    @ViewInject(R.id.webView)
    WebView webView;
    @ViewInject(R.id.loading)
    RotateLoading loading;

    private FreshNews freshNews;

    public FreshNewsDetailFragment() {
    }

    public static FreshNewsDetailFragment getInstance(FreshNews freshNews) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_FRESH_NEWS, freshNews);
        FreshNewsDetailFragment fragment = new FreshNewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fresh_news_detail, container, false);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
        freshNews = (FreshNews) getArguments().getSerializable(DATA_FRESH_NEWS);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 80) {
                    loading.stop();
                }
            }
        });

        loadData();
        loading.start();
        loading.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loading.isShown()) {
                    loading.stop();
                }
            }
        }, 10 * 1000);

    }
    private void loadData(){
        //load data
    }
    private static String getHtml(FreshNews freshNews, String content) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html dir=\"ltr\" lang=\"zh\">");
        sb.append("<head>");
        sb.append("<meta name=\"viewport\" content=\"width=100%; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" />");
        sb.append("<link rel=\"stylesheet\" href='file:///android_asset/style.css' type=\"text/css\" media=\"screen\" />");
        sb.append("</head>");
        sb.append("<body style=\"padding:0px 8px 8px 8px;\">");
        sb.append("<div id=\"pagewrapper\">");
        sb.append("<div id=\"mainwrapper\" class=\"clearfix\">");
        sb.append("<div id=\"maincontent\">");
        sb.append("<div class=\"post\">");
        sb.append("<div class=\"posthit\">");
        sb.append("<div class=\"postinfo\">");
        sb.append("<h2 class=\"thetitle\">");
        sb.append("<a>");
        sb.append(freshNews.getTitle());
        sb.append("</a>");
        sb.append("</h2>");
        sb.append(freshNews.getAuthor().getName() +"     "+freshNews.getDate());
        sb.append("</div>");
        sb.append("<div class=\"entry\">");
        sb.append(content);
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_fresh_news_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.action_comment:
//                Intent intent = new Intent(getActivity(), CommentListActivity.class);
//                intent.putExtra(DATA_THREAD_ID, freshNews.getId());
//                intent.putExtra(DATA_IS_FROM_FRESH_NEWS, true);
//                startActivity(intent);
//                return true;
//            case R.id.action_share:
//                ShareUtil.shareText(getActivity(), freshNews.getTitle() + " " + freshNews.getUrl());
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}