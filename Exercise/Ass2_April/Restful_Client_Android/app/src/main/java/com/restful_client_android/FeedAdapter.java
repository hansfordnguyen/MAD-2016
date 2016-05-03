package com.restful_client_android;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;

    public FeedAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
//        bindDefaultFeedItem(position, holder);
        //TODO bind event
        ((CellFeedViewHolder) viewHolder).btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
            }
        });
        ((CellFeedViewHolder) viewHolder).btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Comment", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
            }
        });
        ((CellFeedViewHolder) viewHolder).ivUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
            }
        });
        ((CellFeedViewHolder) viewHolder).tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {
        if (position % 2 == 0) {
            holder.ivCardImage.setImageResource(R.drawable.img_feed_center_1);
//            holder.ivFeedBottom.setImageResource(R.drawable.img_feed_bottom_1);
        } else {
            holder.ivCardImage.setImageResource(R.drawable.img_feed_center_2);
//            holder.ivFeedBottom.setImageResource(R.drawable.img_feed_bottom_2);
        }

        holder.btnComments.setTag(position);
        holder.btnMore.setTag(position);
        holder.ivCardImage.setTag(holder);
        holder.btnLike.setTag(holder);

    }

    public void updateItems() {
        itemsCount = 10;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserProfile;
        TextView tvUsername;
        ImageView ivCardImage;
//        ImageView ivFeedBottom;
        TextView tvDescription;
        ImageButton btnComments;
        ImageButton btnLike;
        ImageButton btnMore;
        TextSwitcher tsLikesCounter;
//        FrameLayout vImageRoot;

        public CellFeedViewHolder(View view) {
            super(view);
            ivUserProfile = (ImageView) view.findViewById(R.id.ivUserProfile);
            tvUsername = (TextView) view.findViewById(R.id.tvUsername);
            ivCardImage = (ImageView) view.findViewById(R.id.ivCardImage);
//            ivFeedBottom = (ImageView) view.findViewById(R.id.ivFeedBottom);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            btnComments = (ImageButton) view.findViewById(R.id.btnComments);
            btnLike = (ImageButton) view.findViewById(R.id.btnLike);
            btnMore = (ImageButton) view.findViewById(R.id.btnMore);
            tsLikesCounter = (TextSwitcher) view.findViewById(R.id.tsLikesCounter);
//            vImageRoot = (FrameLayout) view.findViewById(R.id.vImageRoot);
        }
    }
}