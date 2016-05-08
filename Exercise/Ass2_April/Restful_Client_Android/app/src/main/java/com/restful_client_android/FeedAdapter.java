package com.restful_client_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.faradaj.blurbehind.BlurBehind;
import com.faradaj.blurbehind.OnBlurCompleteListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    public ArrayList<FeedCardData> cardDataList;
    private AsyncHttpClient likePostClient;

    public FeedAdapter(Context context, Activity activity, ArrayList<FeedCardData> data) {
        this.context = context;
        this.activity = activity;
        this.cardDataList = data;
        likePostClient = new AsyncHttpClient();
    }

    public void likePost(Context context, String postId, final int originLikeNumber, final TextSwitcher textSwitcher) {
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("postid", postId);
            params.put("username", Variables.currentLoginUsername);
            entity = new StringEntity(params.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        likePostClient.post(context, Variables.likePostApiUrl, entity, "application/json", new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("Like a post failed");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String success = response.getString(Variables.apiSuccess);
                    String message = response.getString(Variables.apiMessage);
                    if (success.equals("true")) {
                        System.out.println("Like a post successfully");
                        int newLike = (originLikeNumber + 1);
                        textSwitcher.setText("" + newLike);
                    } else {
                        System.out.println("Like a post ERROR" + message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;

        //bind data
        final FeedCardData card = cardDataList.get(position);
        Picasso.with(context).load(card.avatarUrl).into(holder.ivUserProfile);
        holder.tvUsername.setText(card.username);
        Picasso.with(context).load(card.cardImageUrl).resize(250, 250).centerCrop().into(holder.ivCardImage);
        holder.tvDescription.setText(card.description);
        holder.tsLikesCounter.setText(card.likeNumber);

        //TODO bind event
        ((CellFeedViewHolder) viewHolder).btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
                likePost(context, card.postId, Integer.parseInt(card.likeNumber), holder.tsLikesCounter);
            }
        });
        ((CellFeedViewHolder) viewHolder).btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Comment", Toast.LENGTH_SHORT).show();
//                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(activity, PostDetails.class);
                context.startActivity(intent);
            }
        });
        ((CellFeedViewHolder) viewHolder).ivUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
//                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(activity, ProfileActivity.class);
                context.startActivity(intent);
            }
        });
        ((CellFeedViewHolder) viewHolder).tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
//                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(activity, ProfileActivity.class);
                intent.putExtra(Variables.apiUsername, getCardData(position).username);
                context.startActivity(intent);
            }
        });
        ((CellFeedViewHolder) viewHolder).ivCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "This feature is not available at the moment", Snackbar.LENGTH_LONG).show();
                BlurBehind.getInstance().execute(activity, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(activity, ViewImageActivity.class);
                        intent.putExtra(Variables.cardImageUrl, getCardData(position).cardImageUrl);
                        intent.putExtra(Variables.apiContent, getCardData(position).description);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    public void updateItems() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return cardDataList.size();
    }

    public void insertCard(int position, FeedCardData data) {
        cardDataList.add(position, data);
        notifyItemInserted(position);
    }

    public void insertCard(int position, JSONObject object) {
        try {
            FeedCardData card = new FeedCardData(
                    object.getString(Variables.apiUserAvatar),
                    object.getString(Variables.apiUsername),
                    object.getString(Variables.apiCardImage),
                    object.getString(Variables.apiContent),
                    object.getString(Variables.apiNumberLike),
                    object.getString("id"));
            insertCard(position, card);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void insertCard(FeedCardData data) {
        cardDataList.add(data);
    }

    public void insertCard(JSONObject object) {
        try {
            FeedCardData card = new FeedCardData(
                    object.getString(Variables.apiUserAvatar),
                    object.getString(Variables.apiUsername),
                    object.getString(Variables.apiCardImage),
                    object.getString(Variables.apiContent),
                    object.getString(Variables.apiNumberLike),
                    object.getString("id"));
            insertCard(card);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeCard(FeedCardData data) {
        int pos = cardDataList.indexOf(data);
        cardDataList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void removeAllCard() {
        cardDataList.clear();
        notifyDataSetChanged();
    }

    public FeedCardData getCardData(int position) {
        return cardDataList.get(position);
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
