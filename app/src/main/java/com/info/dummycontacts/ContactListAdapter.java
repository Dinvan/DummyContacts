package com.info.dummycontacts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ContactListAdapter(List<ContactLog> contactsList, Context context) {
        this.contactsList = contactsList;
        this.context = context;
        mDrawableBuilder = TextDrawable.builder()
                .round();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {

        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);

        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ContactLog contact = contactsList.get(position);
        holder.txtName.setText(contact.getDisplayName());
        holder.txtCommunicationType.setText(contact.getCommunicationType());
        try {
            long timeMiles = contact.getLastContactOn();
            String newTime = DateTimeHelper.getElapsedTime(timeMiles);
            holder.txtLastContact.setText(newTime);
        } catch (Exception e) {
            holder.txtLastContact.setText("");
        }
        if (!TextUtils.isEmpty(contact.photoUri)) {
            holder.roundedIvProfile.setVisibility(View.VISIBLE);
            holder.rounded_iv_profile1.setVisibility(View.GONE);
            Picasso.with(holder.itemView.getContext())
                    .load(contact.photoUri)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.roundedIvProfile);
        } else {
            holder.roundedIvProfile.setVisibility(View.GONE);
            holder.rounded_iv_profile1.setVisibility(View.VISIBLE);
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(contact.getDisplayName().charAt(0)).toUpperCase(), mColorGenerator.getColor(contact.getDisplayName()));
            holder.rounded_iv_profile1.setImageDrawable(drawable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContactsType(context,contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView roundedIvProfile;
        private ImageView rounded_iv_profile1;
        private TextView txtName, txtCommunicationType, txtLastContact;

        public ViewHolder(View itemView) {
            super(itemView);
            roundedIvProfile = (RoundedImageView) itemView.findViewById(R.id.rounded_iv_profile);
            rounded_iv_profile1 = (ImageView) itemView.findViewById(R.id.rounded_iv_profile1);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCommunicationType = (TextView) itemView.findViewById(R.id.txtCommunicationType);
            txtLastContact = (TextView) itemView.findViewById(R.id.txtLastContact);
        }
    }


    public void showContactsType(final Context context,final ContactLog contactLog) {
        final CharSequence[] OPTION_ITEMS = {"Phone","SMS","Whats App","Facebook","Telegram","Cancel"};
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Contact Using..");
        builder.setItems(OPTION_ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        contactLog.setCommunicationType(OPTION_ITEMS[item].toString());
                        contactLog.setLastContactOn(System.currentTimeMillis());
                        contactLog.save();
                        break;
                    case 1:
                        contactLog.setCommunicationType(OPTION_ITEMS[item].toString());
                        contactLog.setLastContactOn(System.currentTimeMillis());
                        contactLog.save();
                        break;
                    case 2:
                        contactLog.setCommunicationType(OPTION_ITEMS[item].toString());
                        contactLog.setLastContactOn(System.currentTimeMillis());
                        contactLog.save();
                        break;
                    case 3:
                        contactLog.setCommunicationType(OPTION_ITEMS[item].toString());
                        contactLog.setLastContactOn(System.currentTimeMillis());
                        contactLog.save();
                        break;
                    case 4:
                        contactLog.setCommunicationType(OPTION_ITEMS[item].toString());
                        contactLog.setLastContactOn(System.currentTimeMillis());
                        contactLog.save();
                        break;
                    case 5:
                        dialog.dismiss();
                        break;
                }
                notifyDataSetChanged();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

}
