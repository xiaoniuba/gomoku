package com.yjx.com.yjx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjx.model.SettingsItem;
import com.yjx.wuziqi.R;

import java.util.List;

/**
 * Created by yangjinxiao on 2016/7/8.
 */
public class SettingsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<SettingsItem> mData;
    private OnItemClickListener mListener;

    public SettingsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<SettingsItem> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public SettingsItem getItem(int i) {
        if (mData == null || i < 0 || i >= mData.size()) {
            return null;
        }
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_iamge_text, null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SettingsItem item = getItem(position);
        if (item == null) {
            return convertView;
        }
        viewHolder.iv.setImageResource(item.getImgId());
        viewHolder.tv.setText(item.getContentStr());
        final View finalConvertView = convertView;
        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(finalConvertView, item.getContentStr());
                }
            }
        });
        convertView.setTag(R.id.tv, item.getContentStr());
        return convertView;
    }

    private class ViewHolder {
        public ImageView iv;
        public TextView tv;
    }

    public interface OnItemClickListener {
        void onItemClick(View convertView, String contentStr);
    }
}
