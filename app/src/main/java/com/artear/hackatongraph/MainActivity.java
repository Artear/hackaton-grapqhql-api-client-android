package com.artear.hackatongraph;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloCallback;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.cache.normalized.CacheControl;
import com.apollographql.apollo.exception.ApolloException;
import com.artear.hackatongraph.model.Block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int FEED_SIZE = 5;

    HackatonApplication application;
    List<Block> feedAdapter;
    Handler uiHandler = new Handler(Looper.getMainLooper());
    ApolloCall<FeedQuery.Data> githuntFeedCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (HackatonApplication) getApplication();





        fetchFeed();
    }

    private ApolloCall.Callback<FeedQuery.Data> dataCallback = new ApolloCallback<>(new ApolloCall.Callback<FeedQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<FeedQuery.Data> response) {
            // TODO: ADAPT
            int i = 0;

            ListView list = (ListView) findViewById(R.id.graph_list);
            feedAdapter = new ArrayList<>();
            for (FeedQuery.ContainerList data:response.data().feedEntries().containerList()) {
                feedAdapter.add(new Block(i,data.id() + " type: " + data.type()));


                list.setAdapter(new GraphAdapter(MainActivity.this, R.layout.block, feedAdapter));
                i++;
            }
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }, uiHandler);

    private void fetchFeed() {
        final FeedQuery feedQuery = FeedQuery.builder()
                .section("TNChicas")
                .build();
        githuntFeedCall = application.apolloClient()
                .query(feedQuery)
                .cacheControl(CacheControl.NETWORK_FIRST);
        githuntFeedCall.enqueue(dataCallback);
    }
}
