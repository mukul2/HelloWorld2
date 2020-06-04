package com.doctor.telemedicine.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTask {
    static String link;
    static downloadListener Listenmer;
    public static Bitmap bitmap;


    public static void returnBitmap(String l, downloadListener lis) {
        link = l;
        Listenmer = lis;
        new myAsyncTask().execute();

    }


    public interface downloadListener {
        public void onDownloaded(Bitmap bitmap);
    }

    public static class myAsyncTask extends android.os.AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            bitmap = getBitmapFromURL(link);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Listenmer.onDownloaded(bitmap);

            // remoteViews.setImageViewBitmap(R.id.image, bitmap);

        }


    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}