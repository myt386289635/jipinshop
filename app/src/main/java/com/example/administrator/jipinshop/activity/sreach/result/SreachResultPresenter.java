package com.example.administrator.jipinshop.activity.sreach.result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SreachTagBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public class SreachResultPresenter {

    Repository mRepository;

    private SreachResultView mView;

    public void setView(SreachResultView view) {
        mView = view;
    }

    @Inject
    public SreachResultPresenter(Repository repository) {
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
                    mView.onResher("2",textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }

    public void initHistroy(final Context context, final FlexboxLayout flexboxLayout, final List<ImageView> deteles,List<SreachTagBean> histroyText) {
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
                deteles.remove(delete);
                histroyText.remove(bean);
                String str = new Gson().toJson(histroyText);
                SPUtils.getInstance(CommonDate.USER).put(CommonDate.historySreach,str);
            });
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.onResher("1",textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }


    public void addSearchFlex(String content, Context context, final FlexboxLayout flexboxLayout, final List<ImageView> deteles,List<SreachTagBean> histroyText){
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
            deteles.remove(delete);
            histroyText.remove(bean);
            String str = new Gson().toJson(histroyText);
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.historySreach,str);
        });
        textView.setOnClickListener(v -> {
            if(mView != null){
                mView.onResher("1",textView.getText().toString());
            }
        });
        deteles.add(0,delete);
        flexboxLayout.addView(itemTypeView,0);
    }


}
