package com.nafidinara.threesubmission.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.model.ModelWidget;

import java.util.concurrent.ExecutionException;

import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.MOVIE_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    Cursor cursor;
    int ID;
    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
        ID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        if (cursor !=null){
            cursor.close();
        }
        cursor = context.getContentResolver().query(MOVIE_URI,null,null,null,null,null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }
    private ModelWidget items(int position){
        if (!cursor.moveToPosition(position)){
            try {
                throw new IllegalAccessException("Error");
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return new ModelWidget(cursor);
    }
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.itemswidget);
        ModelWidget modelWidget = items(position);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context).asBitmap().load("https://image.tmdb.org/t/p/w500"+modelWidget.getPosterPath()).apply(new RequestOptions().centerInside()).submit().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.img_widget,bitmap);
        Bundle bundle = new Bundle();
        bundle.putInt(NewWidget.EXTRA_ITEM,position);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.img_widget,intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
