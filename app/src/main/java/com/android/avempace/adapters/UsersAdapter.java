package com.android.avempace.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.avempace.R;
import com.android.avempace.callbacks.RecyclerCallBack;
import com.android.avempace.models.UserModel;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

import javax.annotation.Nullable;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    List<UserModel> usersList;
    RecyclerCallBack callBack;
    boolean adapterJustStarted = true;
    public boolean selectionOn = false;
    public UsersAdapter(List<UserModel> usersList) {
        this.usersList = usersList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.users_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        UserModel userModel = usersList.get(position);
        String username = userModel.getUsername();
        viewHolder.username.setText(username);
        viewHolder.firstLetter.setText(username.substring(0, 1).toUpperCase());

        if (userModel.isMultipleSelection()) {
            viewHolder.checkUser.setVisibility(View.VISIBLE);
            viewHolder.videoCallButton.setVisibility(View.GONE);
        } else {
            viewHolder.checkUser.setVisibility(View.GONE);
            viewHolder.videoCallButton.setVisibility(View.VISIBLE);
        }
        viewHolder.checkUser.setChecked(userModel.isSelected());
        viewHolder.parentLayout.setOnClickListener(v -> {
            adapterJustStarted = false;
            if (userModel.isMultipleSelection()) {
                userModel.setSelected(!userModel.isSelected());
                toggleCheck(viewHolder.checkUser);
                if (checkUserSelected() && !adapterJustStarted) {
                    multipleSelection(false, 3);
                    selectionOn = false;
                }
            }else
                multipleSelection(false, 3);
            notifyDataSetChanged();
            callBack.onUserClicked(userModel, position);
        });
        viewHolder.parentLayout.setOnLongClickListener(v -> {
            adapterJustStarted = false;
            if(!userModel.isMultipleSelection()){
                multipleSelection(true, 3);
                selectionOn = true;
                userModel.setSelected(true);
                notifyDataSetChanged();
                callBack.onUserLongClick(userModel, position);
            }
            return false;
        });
        viewHolder.videoCallButton.setOnClickListener(v -> {
            callBack.onSingleCallClicked(userModel, position);
        });

    }

    private boolean checkUserSelected() {
        boolean nonSelected = true;
        for (UserModel user : usersList) {
            if (user.isSelected()) {
                nonSelected = false;
                break;
            }
        }
        return nonSelected;
    }

    private void toggleCheck(MaterialCheckBox checkBox) {
        checkBox.setChecked(!checkBox.isChecked());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstLetter, username;
        RelativeLayout videoCallButton;
        MaterialCheckBox checkUser;
        CardView parentLayout;

        public ViewHolder(View view) {
            super(view);
            firstLetter = (TextView) view.findViewById(R.id.first_letter_text);
            username = (TextView) view.findViewById(R.id.username);
            videoCallButton = (RelativeLayout) view.findViewById(R.id.call_button);
            checkUser = (MaterialCheckBox) view.findViewById(R.id.checkbox_user);
            parentLayout = (CardView) view.findViewById(R.id.user_parent_view);
        }
    }

    public void multipleSelection(boolean isMultiple,  int isSelected) {
        // consider isSelected values as these: 0 -> false, 1 -> true, 3 -> null
        for (UserModel user : usersList) {
            if(isSelected !=  3){
                boolean userSelected = isSelected == 1;
                user.setSelected(userSelected);
            }
            user.setMultipleSelection(isMultiple);
        }
    }

    public List<UserModel> getUsersList() {
        return usersList;
    }

    public void setRecyclerCallBack(RecyclerCallBack cb) {
        this.callBack = cb;
    }
}
