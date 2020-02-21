package com.dengyun.baselibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dengyun.baselibrary.R;

/**
 * @titile  右侧带全部删除按钮的editText
 * @desc Created by seven on 2018/4/10.
 */

public class ClearAllEditText extends LinearLayout {

    private EditText et_clear;
    private ImageView iv_clear;

    public ClearAllEditText(Context context) {
        this(context,null,0);
    }

    public ClearAllEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClearAllEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClearAllEditText);
        int clearPic = ta.getResourceId(R.styleable.ClearAllEditText_clear_pic, R.drawable.base_view_editdelete);
        String hint = ta.getString(R.styleable.ClearAllEditText_hint);
        String text = ta.getString(R.styleable.ClearAllEditText_text);
        int bgcolor = ta.getColor(R.styleable.ClearAllEditText_bgcolor,getResources().getColor(R.color.baselib_white));
        int maxLengh = ta.getInt(R.styleable.ClearAllEditText_max_lengh,0);
        boolean singline = ta.getBoolean(R.styleable.ClearAllEditText_singeline,false);
        ta.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.base_view_edit_clearall,this);
        et_clear = (EditText) view.findViewById(R.id.edit_clear);
        et_clear.setBackgroundColor(bgcolor);
        iv_clear = (ImageView) view.findViewById(R.id.iv_clear);
        setTextChangeListener();
        setFousChangeListener();
        setClearAllListener();
        iv_clear.setImageResource(clearPic);

        if(!TextUtils.isEmpty(hint)){
            et_clear.setHint(hint);
        }

        //设置最大长度
        if(maxLengh!=0){
            et_clear.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengh)});
        }

        /*设置单行显示*/
        if(singline){
            et_clear.setSingleLine(true);
        }

        if(!TextUtils.isEmpty(text)){
            et_clear.setText(text);
            iv_clear.setVisibility(VISIBLE);
            et_clear.setSelection(text.length());
        }else {
            iv_clear.setVisibility(GONE);
        }
    }

    public String getText(){
        if(TextUtils.isEmpty(et_clear.getText().toString())){
            return "";
        }
        return et_clear.getText().toString();
    }
    public void setText(String text){
        et_clear.setText(text);
    }

    private void setClearAllListener() {
        iv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_clear.setText("");
            }
        });
    }

    /*edit输入变化监听*/
    private void setTextChangeListener() {
        et_clear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String etEnd = s.toString();
                if(TextUtils.isEmpty(etEnd)){
                    iv_clear.setVisibility(GONE);
                }else {
                    iv_clear.setVisibility(VISIBLE);
                }
            }
        });
    }

    /*edit焦点变化监听*/
    private void setFousChangeListener() {
        et_clear.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if(!TextUtils.isEmpty(et_clear.getText().toString())){
                        iv_clear.setVisibility(View.VISIBLE);
                    }
                } else {
                    iv_clear.setVisibility(View.GONE);
                }
            }
        });
    }

    public EditText getEditText(){
        return et_clear;
    }

}
