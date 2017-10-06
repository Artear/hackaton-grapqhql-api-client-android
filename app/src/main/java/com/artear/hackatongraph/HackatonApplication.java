package com.artear.hackatongraph;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.facebook.stetho.Stetho;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HackatonApplication extends Application {

    private static final String BASE_URL = "https://cover-graphql-server.herokuapp.com/graphql";
    private ApolloClient apolloClient;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Stetho.initializeWithDefaults(this);


        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
    }

    public ApolloClient apolloClient() {
        return apolloClient;
    }
}
