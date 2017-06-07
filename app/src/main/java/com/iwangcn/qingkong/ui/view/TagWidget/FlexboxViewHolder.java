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

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.ClientLabel;

/**
 * ViewHolder that represents a cat image.
 */
class FlexboxViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    FlexboxViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.textview);

    }

    void bindTo(ClientLabel cilentLabel, boolean isEditing) {
        if(!TextUtils.isEmpty(cilentLabel.getName())){
            mTextView.setText(cilentLabel.getName());
        }
        if (isEditing) {
            mTextView.setBackgroundResource(R.drawable.normal_tag_seclected);
        } else {
            mTextView.setBackgroundResource(R.drawable.normal_bg);
        }
//        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
//        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
//            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
//            flexboxLp.setFlexGrow(1.0f);
//        }
    }
}
