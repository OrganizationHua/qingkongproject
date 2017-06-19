/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.iwangcn.qingkong.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikeafc on 15/11/26.
 */
public class UltraPagerAdapter extends PagerAdapter {
    private List<String> urlList = new ArrayList<>();

    public void notifyDataSetChanged(List<String> urlList) {
        if (urlList != null && urlList.size() > 0) {
            this.urlList.clear();
            this.urlList.addAll(urlList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (urlList != null && urlList.size() != 0) {
            return urlList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.pager_img);
        if (!urlList.get(position).endsWith(".gif")) {
            GlideUtils.loadImageView(imageView.getContext(), urlList.get(position), imageView, R.drawable.default_icon_bg, R.drawable.default_icon_bg);
        } else {
            GlideUtils.loadGIf(imageView.getContext(), urlList.get(position), imageView,R.drawable.default_icon_bg);
        }
        relativeLayout.setId(R.id.item_id);
        container.addView(relativeLayout);
        return relativeLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }
}
