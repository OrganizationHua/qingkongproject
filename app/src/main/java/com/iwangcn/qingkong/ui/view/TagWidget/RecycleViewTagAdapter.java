/*
 * Copyright 2017 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iwangcn.qingkong.ui.view.TagWidget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iwangcn.qingkong.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Adapter class that handles the data set with the {@link RecyclerView.LayoutManager}
 */
public class RecycleViewTagAdapter extends RecyclerView.Adapter<FlexboxViewHolder> implements RecycleViewItemTouchCallback.ItemTouchAdapter {
    public boolean isEditing = false;

    public static final String[] CAT_IMAGE_IDSS = new String[]{
            "爱干请赶紧骞吖",
            "爱干请赶",
            "爱干请赶紧",
            "赶紧",
            "爱干请",
            "爱干请",
            "家里就快啦",
            "aqg ",
            "爱干请赶紧骞 ",
            "爱",
            "爱干请赶紧骞",
            "爱干请赶紧骞吖 ",
            "爱干请赶紧骞",
            "爱干请赶亲 ",
            "爱干害爱",

    };
    public static List<String> results = Arrays.asList(CAT_IMAGE_IDSS);
    private Context mContext;

    public RecycleViewTagAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FlexboxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_cats, parent, false);
        return new FlexboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlexboxViewHolder holder, int position) {
        String str = CAT_IMAGE_IDSS[position];
        holder.bindTo(str, isEditing);
    }

    @Override
    public int getItemCount() {
        return CAT_IMAGE_IDSS.length;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }
}
