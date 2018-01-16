package com.info.dummycontacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by advanz101 on 16/1/18.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    List<ContactLog> contactsList;
    Context context;

    public ContactListAdapter(List<ContactLog> contactsList, Context context) {
        this.contactsList = contactsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {

        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);

        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactLog contact = contactsList.get(position);
        holder.tvLabel.setText(contact.getDisplayName());
        Picasso.with(holder.itemView.getContext())
                .load(contact.photoUri)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.roundedIvProfile);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView roundedIvProfile;
        private TextView tvLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            roundedIvProfile = (RoundedImageView) itemView.findViewById(R.id.rounded_iv_profile);
            tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
        }
    }
}
