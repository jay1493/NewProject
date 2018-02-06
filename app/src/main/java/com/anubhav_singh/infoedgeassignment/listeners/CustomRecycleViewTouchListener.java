package com.anubhav_singh.infoedgeassignment.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.anubhav_singh.infoedgeassignment.R;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class CustomRecycleViewTouchListener implements RecyclerView.OnItemTouchListener {

    private VenueItemClickListener venueItemClickListener;
    private GestureDetector gestureDetector;
    private Context context;
    private ShimmerRecyclerView shimmerRecyclerView;

    public CustomRecycleViewTouchListener(VenueItemClickListener venueItemClickListener, ShimmerRecyclerView recyclerView, Context context) {
        this.venueItemClickListener = venueItemClickListener;
        this.context = context;
        this.gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //As it is already handled by intercepting touch event...
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && venueItemClickListener!=null){
                    if(child instanceof EditText && child.getId() == R.id.et_user_review_recycler_item) {
                        venueItemClickListener.onUserReviewLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && venueItemClickListener!=null && gestureDetector.onTouchEvent(e)){
            venueItemClickListener.onItemClick(child,rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
