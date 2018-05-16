package com.yaheen.pdaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.adapter.base.CommonAdapter;
import com.yaheen.pdaapp.bean.MsgBean;

public class ManageMsgAdapter extends CommonAdapter<MsgBean.EntityBean> {

    public ManageMsgAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return MsgBean.num;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_manage_msg, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MsgBean.EntityBean data = getItem(0);
        if (data != null) {
            if (position == 0) {
                holder.tvDescribe.setText(R.string.msg_community_text);
                holder.etDetail.setText(data.getCommunity());
            } else if (position == 1) {
                holder.tvDescribe.setText(R.string.msg_username_text);
                holder.etDetail.setText(data.getUserName());
            } else if (position == 2) {
                holder.tvDescribe.setText(R.string.msg_sex_text);
                holder.etDetail.setText(data.getSex());
            } else if (position == 3) {
                holder.tvDescribe.setText(R.string.msg_address_text);
                holder.etDetail.setText(data.getAddress());
            } else if (position == 4) {
                holder.tvDescribe.setText(R.string.msg_mobile_text);
                holder.etDetail.setText(data.getMobile());
            } else if (position == 5) {
                holder.tvDescribe.setText(R.string.msg_phone_text);
                holder.etDetail.setText(data.getTelephone());
            } else if (position == 6) {
                holder.tvDescribe.setText(R.string.msg_people_number_text);
                holder.etDetail.setText(data.getPeopleNumber());
            } else if (position == 7) {
                holder.tvDescribe.setText(R.string.msg_category_text);
                holder.etDetail.setText(data.getCategory());
            } else if (position == 8) {
                holder.tvDescribe.setText(R.string.msg_party_member_text);
                holder.etDetail.setText(data.getPartyMember());
            }
        }
        return convertView;
    }

    public String getMsg() {
        MsgBean.EntityBean data = getItem(0);
        if (data != null) {
            Gson gson = new Gson();
            return gson.toJson(data);
        }
        return "";
    }

    class ViewHolder {
        TextView tvDescribe;

        EditText etDetail;

        public ViewHolder(View view) {
            tvDescribe = view.findViewById(R.id.tv_describe);
            etDetail = view.findViewById(R.id.et_detail);
        }
    }
}
