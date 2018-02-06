package com.anubhav_singh.infoedgeassignment.adapters;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
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
import com.anubhav_singh.infoedgeassignment.models.Item;
import com.anubhav_singh.infoedgeassignment.models.Venue;
import com.bumptech.glide.Glide;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class VenueAdapter extends PagedListAdapter<Item,VenueAdapter.ItemViewHolder> {

    private Context context;
    private ShimmerRecyclerView shimmerRecyclerView;

    public VenueAdapter(Context context, ShimmerRecyclerView recyclerView) {
        super(Item.DIFF_CALLBACK);
        this.context = context;
        this.shimmerRecyclerView = recyclerView;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.main_menu_recycler_row,parent,false),viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                Item item = getItem(position);
                if(item!=null){
                    holder.tvHiddenIdField.setText(item.getVenue().getId());
                    holder.tvVenueName.setText(item.getVenue().getName());
                    if(item.getVenue().getLocation()!=null) {
                        if(!TextUtils.isEmpty(item.getVenue().getLocation().getCrossStreet())) {
                            holder.tvSetorAddress.setText(item.getVenue().getLocation().getCrossStreet() + " ,");
                        }else{
                            holder.tvSetorAddress.setText("");
                        }
                        holder.tvDistanceToLocation.setText(String.valueOf(item.getVenue().getLocation().getDistance()/1000));
                    }
                    if(item.getVenue().getCategories()!=null && item.getVenue().getCategories().size()>0){
                        StringBuilder categories = new StringBuilder();
                        StringBuilder categoryUrl = new StringBuilder();
                        for(Category category : item.getVenue().getCategories()){
                            if(category.getIcon()!=null && !TextUtils.isEmpty(category.getIcon().getPrefix()) && !TextUtils.isEmpty(category.getIcon().getSuffix())){
                                categoryUrl = new StringBuilder();
                                categoryUrl.append(category.getIcon().getPrefix());
                                categoryUrl.append("bg_88");
                                categoryUrl.append(category.getIcon().getSuffix());
                            }
                            categories.append(category.getPluralName());
                            categories.append(",");
                        }
                        Glide.with(context).load(categoryUrl.toString()).into(holder.venueImage);
                        categories = categories.replace(categories.length()-2,categories.length(),"");
                        holder.tvCategories.setText(categories.toString());
                    }

                    if(item.getVenue().getRating()!=null && !TextUtils.isEmpty(item.getVenue().getRatingColor())){
                        holder.tvRating.setText(String.valueOf(item.getVenue().getRating()));
                        holder.tvRating.setBackgroundColor(Color.parseColor("#"+item.getVenue().getRatingColor().trim()));
                    }

                    /*if(!TextUtils.isEmpty(venue.getUserRemarks())){
                        holder.etUserRemarks.setText(venue.getUserRemarks());
                    }else{
                        holder.etUserRemarks.setText("");
                        holder.etUserRemarks.setHint(context.getResources().getString(R.string.edit_personal_user_review));
                    }*/
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
    public void setList(PagedList<Item> pagedList) {
        super.setList(pagedList);
        if(shimmerRecyclerView!=null && pagedList!=null && pagedList.size()>0){
            shimmerRecyclerView.hideShimmerAdapter();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        @Nullable @BindView(R.id.iv_venue_recycler_item)
        ImageView venueImage;
        @Nullable @BindView(R.id.tv_recycler_header)
        TextView tvVenueName;
        @Nullable @BindView(R.id.tv_recycler_distance)
        TextView tvDistanceToLocation;
        @Nullable @BindView(R.id.tv_categories_recycler_item)
        TextView tvCategories;
        @Nullable @BindView(R.id.hiddenVenueIdField)
        TextView tvHiddenIdField;
        @Nullable @BindView(R.id.tv_recycler_sector_addr)
        TextView tvSetorAddress;
        @Nullable @BindView(R.id.tv_rating_recycler)
        TextView tvRating;

       /* @Nullable @BindView(R.id.et_user_review_recycler_item)
        EditText etUserRemarks;*/



        public ItemViewHolder(View itemView, int viewType) {
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
