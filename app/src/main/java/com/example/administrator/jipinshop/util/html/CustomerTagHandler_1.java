package com.example.administrator.jipinshop.util.html;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.example.administrator.jipinshop.MyApplication;

import org.xml.sax.Attributes;

import java.util.Stack;

import static com.blankj.utilcode.util.EmptyUtils.isEmpty;

/**
 * @author 莫小婷
 * @create 2019/6/21
 * @Describe
 */
public class CustomerTagHandler_1 implements HtmlParser.TagHandler {
    /**
     * html 标签的开始下标
     */
    private Stack<Integer> startIndex;

    /**
     * html的标签的属性值 value，如:<size value='16'></size>
     * 注：value的值不能带有单位,默认就是sp
     */
    private Stack<String> propertyValue;

    @Override
    public boolean handleTag(boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            handlerStartTAG(tag, output, attributes);
        } else {
            handlerEndTAG(tag, output, attributes);
        }
        return handlerBYDefault(tag);
    }

    private void handlerStartTAG(String tag, Editable output, Attributes attributes) {
        if (tag.equalsIgnoreCase("font")) {
            handlerStartFONT(output, attributes);
        } else if (tag.equalsIgnoreCase("del")) {
            handlerStartDEL(output);
        }
    }


    private void handlerEndTAG(String tag, Editable output, Attributes attributes) {
        if (tag.equalsIgnoreCase("font")) {
            handlerEndFONT(output);
        } else if (tag.equalsIgnoreCase("del")) {
            handlerEndDEL(output);
        }
    }

    private void handlerStartFONT(Editable output, Attributes attributes) {
        if (startIndex == null) {
            startIndex = new Stack<>();
        }
        startIndex.push(output.length());

        if (propertyValue == null) {
            propertyValue = new Stack<>();
        }

        propertyValue.push(HtmlParser.getValue(attributes, "size"));
    }

    private void handlerEndFONT(Editable output) {
        if (!isEmpty(propertyValue)) {
            try {
                int value = Integer.parseInt(propertyValue.pop());
                output.setSpan(new AbsoluteSizeSpan(ConvertUtils.sp2px(value)), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void handlerStartDEL(Editable output) {
        if (startIndex == null) {
            startIndex = new Stack<>();
        }
        startIndex.push(output.length());
    }

    private void handlerEndDEL(Editable output) {
        output.setSpan(new StrikethroughSpan(), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    /**
     * 返回true表示不交给系统后续处理
     * false表示交给系统后续处理
     *
     * @param tag
     * @return
     */
    private boolean handlerBYDefault(String tag) {
        if (tag.equalsIgnoreCase("del")) {
            return true;
        }
        return false;
    }
}
