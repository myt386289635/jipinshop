package com.example.administrator.jipinshop.activity.sreach;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SreachPresenter {

    Repository mRepository;
    private SreachView mView;

    public void setView(SreachView view) {
        mView = view;
    }

    @Inject
    public SreachPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initHot(final Context context, final FlexboxLayout flexboxLayout) {
        for (int i = 0; i < 7; i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            if (i == 0) {
                textView.setText("电动牙刷");
            } else if (i == 1) {
                textView.setText("洗衣机");
            } else if (i == 2) {
                textView.setText("电热水壶");
            } else if (i == 3) {
                textView.setText("吹风机");
            } else if (i == 4) {
                textView.setText("波轮洗衣机");
            } else if (i == 5) {
                textView.setText("美容仪");
            } else if (i == 6) {
                textView.setText("厨卫");
            } else {
                textView.setText("电动牙刷");
            }
            delete.setVisibility(View.GONE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mView != null){
                        mView.jump();
                    }
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }

    public void initHistroy(final Context context, final FlexboxLayout flexboxLayout, final List<ImageView> deteles) {
        for (int i = 0; i < 7; i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            if (i == 0) {
                textView.setText("电动牙刷");
            } else if (i == 1) {
                textView.setText("洗衣机");
            } else if (i == 2) {
                textView.setText("电热水壶");
            } else if (i == 3) {
                textView.setText("吹风机");
            } else if (i == 4) {
                textView.setText("波轮洗衣机");
            } else if (i == 5) {
                textView.setText("美容仪");
            } else if (i == 6) {
                textView.setText("厨卫");
            } else {
                textView.setText("电动牙刷");
            }
            deteles.add(delete);
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    for (int j = 0; j < deteles.size(); j++) {
                        deteles.get(j).setVisibility(View.GONE);
                    }
                    delete.setVisibility(View.VISIBLE);
                    return true;
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flexboxLayout.removeView(itemTypeView);
                    deteles.remove(delete);
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mView != null){
                        mView.jump();
                    }
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }

    public void addSearchFlex(String content, Context context, final FlexboxLayout flexboxLayout, final List<ImageView> deteles){
        final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
        TextView textView = itemTypeView.findViewById(R.id.histroy_item);
        final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
        textView.setText(content);
        delete.setVisibility(View.GONE);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for (int j = 0; j < deteles.size(); j++) {
                    deteles.get(j).setVisibility(View.GONE);
                }
                delete.setVisibility(View.VISIBLE);
                return false;
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flexboxLayout.removeView(itemTypeView);
                deteles.remove(delete);
            }
        });
        deteles.add(0,delete);
        flexboxLayout.addView(itemTypeView,0);
    }

}
