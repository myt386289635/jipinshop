package com.example.administrator.jipinshop.activity.sreach;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.bean.SreachTagBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

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

    public void initHot(final Context context, final FlexboxLayout flexboxLayout,List<String> hotText) {
        for (int i = 0; i < hotText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            textView.setText(hotText.get(i));
            delete.setVisibility(View.GONE);
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.jump("2", textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }

    public void initHistroy(final Context context, final FlexboxLayout flexboxLayout, final List<ImageView> deteles,
                            List<SreachTagBean> histroyText,List<View> histroyFlex) {
        for (int i = 0; i < histroyText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            SreachTagBean bean = histroyText.get(i);
            textView.setText(bean.getName());
            deteles.add(delete);
            textView.setOnLongClickListener(view -> {
                for (int j = 0; j < deteles.size(); j++) {
                    deteles.get(j).setVisibility(View.GONE);
                }
                delete.setVisibility(View.VISIBLE);
                return true;
            });
            delete.setOnClickListener(view -> {
                flexboxLayout.removeView(itemTypeView);
                histroyFlex.remove(itemTypeView);
                deteles.remove(delete);
                histroyText.remove(bean);
                String str = new Gson().toJson(histroyText);
                SPUtils.getInstance(CommonDate.USER).put(CommonDate.historySreach,str);
            });
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.jump("1",textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
            histroyFlex.add(itemTypeView);
        }
    }

    public void addSearchFlex(String content, Context context, final FlexboxLayout flexboxLayout, final List<ImageView> deteles,
                              List<SreachTagBean> histroyText,List<View> histroyFlex){

        boolean tag = false;
        int pos = 0;
        for (int i = 0; i < histroyText.size(); i++) {
            if(histroyText.get(i).getName().equals(content)){
                tag = true;
                pos = i;
                break;
            }
        }
        if(tag){
            histroyText.remove(pos);
            View view = histroyFlex.get(pos);
            flexboxLayout.removeView(view);
            histroyFlex.remove(view);
        }

        final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
        TextView textView = itemTypeView.findViewById(R.id.histroy_item);
        final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
        textView.setText(content);
        SreachTagBean bean = new SreachTagBean(content);
        histroyText.add(0,bean);
        delete.setVisibility(View.GONE);
        textView.setOnLongClickListener(view -> {
            for (int j = 0; j < deteles.size(); j++) {
                deteles.get(j).setVisibility(View.GONE);
            }
            delete.setVisibility(View.VISIBLE);
            return true;
        });
        delete.setOnClickListener(view -> {
            flexboxLayout.removeView(itemTypeView);
            histroyFlex.remove(itemTypeView);
            deteles.remove(delete);
            histroyText.remove(bean);
            String str = new Gson().toJson(histroyText);
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.historySreach,str);
        });
        deteles.add(0,delete);
        flexboxLayout.addView(itemTypeView,0);
        histroyFlex.add(0,itemTypeView);

        if (histroyText.size() >= 10){
            for (int i = 10; i < histroyText.size(); i++) {
                View view = histroyFlex.get(i);
                flexboxLayout.removeView(view);
                histroyText.remove(i);
            }
        }
    }

}
