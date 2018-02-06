package com.anubhav_singh.infoedgeassignment.adapters;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhav_singh.infoedgeassignment.R;
import com.anubhav_singh.infoedgeassignment.models.Category;
import com.anubhav_singh.infoedgeassignment.models.Venue;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class VenueAdapter extends PagedListAdapter<Venue,VenueAdapter.VenueItemViewHolder> {

    private Context context;
    private ShimmerRecyclerView shimmerRecyclerView;

    public VenueAdapter(Context context, ShimmerRecyclerView recyclerView) {
        super(Venue.DIFF_CALLBACK);
        this.context = context;
        this.shimmerRecyclerView = recyclerView;
    }

    @Override
    public VenueItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new VenueItemViewHolder(LayoutInflater.from(context).inflate(R.layout.main_menu_recycler_row,parent,false),viewType);
            case 1:
                return new VenueItemViewHolder(LayoutInflater.from(context).inflate(R.layout.no_result_found_stub_inflater,parent,false),viewType);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(VenueItemViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                Venue venue = getItem(position);
                if(venue!=null){
                    holder.tvVenueName.setText(venue.getName());
                    if(venue.getLocation()!=null) {
                        holder.tvDistanceToLocation.setText(String.valueOf(venue.getLocation().getDistance()));
                    }
                    if(venue.getCategories()!=null && venue.getCategories().size()>0){
                        StringBuilder categories = new StringBuilder();
                        for(Category category : venue.getCategories()){
                            categories.append(category.getPluralName());
                            categories.append(",");
                        }
                        holder.tvCategories.setText(categories.toString());
                    }
                    if(!TextUtils.isEmpty(venue.getUserRemarks())){
                        holder.etUserRemarks.setText(venue.getUserRemarks());
                    }else{
                        holder.etUserRemarks.setHint(context.getResources().getString(R.string.edit_personal_user_review));
                    }
                }
                break;
            case 1:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(getCurrentList()!=null && getCurrentList().size()>0){
           return 0;
        }
        return 1;
    }

    @Override
    public void setList(PagedList<Venue> pagedList) {
        super.setList(pagedList);
        if(shimmerRecyclerView!=null){
            shimmerRecyclerView.hideShimmerAdapter();
        }
    }

    class VenueItemViewHolder extends RecyclerView.ViewHolder{

        @Nullable @BindView(R.id.iv_venue_recycler_item)
        ImageView venueImage;
        @Nullable @BindView(R.id.tv_recycler_header)
        TextView tvVenueName;
        @Nullable @BindView(R.id.tv_recycler_distance)
        TextView tvDistanceToLocation;
        @Nullable @BindView(R.id.tv_categories_recycler_item)
        TextView tvCategories;
        @Nullable @BindView(R.id.et_user_review_recycler_item)
        EditText etUserRemarks;



        public VenueItemViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType){
                case 0:
                    ButterKnife.bind(this,itemView);
                    break;
                case 1:
                    break;
            }
        }
    }

}
