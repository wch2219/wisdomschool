package com.dlwx.wisdomschool.utiles;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.base.MyApplication;
import com.dlwx.wisdomschool.bean.ClassSeleteBean;
import com.google.gson.Gson;

import java.util.List;


public class SeleteClassDiaUtiles extends AlertDialog implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private ViewHolder vh;
    private String[] class1;
    private String[] class2;
    private String[] class3;
    private ClassSeleteBean classSeleteBean;
    private List<ClassSeleteBean.Class1.Class2.Class3> class3s;
    private List<ClassSeleteBean.Class1.Class2> class2s;

    public SeleteClassDiaUtiles(Context context) {
        super(context);
    }

    protected
    SeleteClassDiaUtiles(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected SeleteClassDiaUtiles(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void init(Context ctx) {
        Gson gson = new Gson();
        classSeleteBean = gson.fromJson(MyApplication.classnames, ClassSeleteBean.class);
        class1 = new String[classSeleteBean.getClass1().size()];
        class2s = classSeleteBean.getClass1().get(0).getClass2();
        class3s = classSeleteBean.getClass1().get(0).getClass2().get(0).getClass3();
        for (int i = 0; i < classSeleteBean.getClass1().size(); i++) {
            class1[i] = classSeleteBean.getClass1().get(i).getClass1name();
        }
        strc1 = class1[0];
        class2 = new String[classSeleteBean.getClass1().get(0).getClass2().size()];
        for (int i = 0; i < classSeleteBean.getClass1().get(0).getClass2().size(); i++) {
            class2[i] = classSeleteBean.getClass1().get(0).getClass2().get(i).getClass2name();
        }
        strc2 = class2[0];
          class3 = new String[classSeleteBean.getClass1().get(0).getClass2().get(0).getClass3().size()];
        for (int i = 0; i < classSeleteBean.getClass1().get(0).getClass2().get(0).getClass3().size(); i++) {
            class3[i] = classSeleteBean.getClass1().get(0).getClass2().get(0).getClass3().get(i).getClass3name();
        }
        strc3 = class3[0];
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_selete_class, null);
        vh = new ViewHolder(view);
        vh.tv_close.setOnClickListener(this);
        vh.btn_aff.setOnClickListener(this);

        vh.class1.setDisplayedValues(class1);
        vh.class1.setMaxValue(class1.length - 1);
        vh.class1.setMinValue(0);
        vh.class1.setOnValueChangedListener(this);

        vh.class2.setDisplayedValues(class2);
        vh.class2.setMaxValue(class2.length - 1);
        vh.class2.setMinValue(0);
        vh.class2.setOnValueChangedListener(this);

        vh.class3.setDisplayedValues(class3);
        vh.class3.setMaxValue(class3.length - 1);
        vh.class3.setMinValue(0);
        vh.class3.setOnValueChangedListener(this);
        this.setView(view);
        this.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_aff://确认
                seleteResult.setResult(strc1+strc2+strc3);
                this.dismiss();
                break;
            case R.id.tv_close:
                seleteResult.setResult("");
                this.dismiss();
                break;
        }
    }
    private int c1 = 0;
    private int c2 = 0;
    private int c3 = 0;
    private String strc1;
    private String strc2;
    private String strc3;
    @Override
    public void onValueChange(NumberPicker numberPicker, int i1, int j) {
        int id = numberPicker.getId();
        Log.i("wch",j+"");
        switch (id) {
            case R.id.class1:
                c1 = j;
                class2s = classSeleteBean.getClass1().get(j).getClass2();
                class2 = new String[classSeleteBean.getClass1().get(j).getClass2().size()];
                strc1 = classSeleteBean.getClass1().get(j).getClass1name();
                for (int i = 0; i < class2s.size(); i++) {
                    class2[i] = classSeleteBean.getClass1().get(j).getClass2().get(i).getClass2name();
                }
                class3 = new String[classSeleteBean.getClass1().get(j).getClass2().get(0).getClass3().size()];
                for (int i = 0; i < classSeleteBean.getClass1().get(j).getClass2().get(0).getClass3().size(); i++) {
                    class3[i] = classSeleteBean.getClass1().get(j).getClass2().get(0).getClass3().get(i).getClass3name();
                }
                if (class2.length-1 >vh.class2.getMaxValue()) {

                    vh.class2.setDisplayedValues(class2);
                    vh.class2.setMaxValue(class2.length-1);
                    vh.class2.setMinValue(0);
                }else{
                    vh.class2.setMaxValue(class2.length-1);
                    vh.class2.setDisplayedValues(class2);
                    vh.class2.setMinValue(0);
                }
                vh.class2.setOnValueChangedListener(this);
                strc2 = class2s.get(0).getClass2name();
                if (class3.length-1 >vh.class3.getMaxValue()) {

                    vh.class3.setDisplayedValues(class3);
                    vh.class3.setMaxValue(class3.length-1);
                    vh.class3.setMinValue(0);
                }else{
                    vh.class3.setMaxValue(class3.length-1);
                    vh.class3.setDisplayedValues(class3);
                    vh.class3.setMinValue(0);
                }
                class3s = classSeleteBean.getClass1().get(c1).getClass2().get(0).getClass3();
                vh.class3.setDisplayedValues(class3);
                strc3 = class3s.get(0).getClass3name();
                break;
            case R.id.class2:
                c2 = j;
                class3s = classSeleteBean.getClass1().get(c1).getClass2().get(j).getClass3();
                strc2 = class2s.get(j).getClass2name();
                strc3 = class3s.get(0).getClass3name();

                class3 = new String[class3s.size()];
                for (int i = 0; i < class3s.size(); i++) {
                    class3[i] = class3s.get(i).getClass3name();
                }


                if (class3.length-1 >vh.class3.getMaxValue()) {

                    vh.class3.setDisplayedValues(class3);
                    vh.class3.setMaxValue(class3.length-1);
                    vh.class3.setMinValue(0);

                }else{
                    vh.class3.setMaxValue(class3.length-1);
                    vh.class3.setDisplayedValues(class3);
                    vh.class3.setMinValue(0);
                }
                vh.class3.setOnValueChangedListener(this);
                break;
            case R.id.class3:
                c3 = j;
                strc3 = class3s.get(j).getClass3name();
                break;
        }
    }
    private SeleteResult seleteResult;
    public interface SeleteResult{
        void setResult(String s);
    }
    public void setSeleteResult(SeleteResult seleteResult){
        this.seleteResult = seleteResult;
    }
    private class ViewHolder {
        public View rootView;
        public NumberPicker class1;
        public NumberPicker class2;
        public NumberPicker class3;
        public TextView tv_close;
        public Button btn_aff;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.class1 = (NumberPicker) rootView.findViewById(R.id.class1);
            this.class2 = (NumberPicker) rootView.findViewById(R.id.class2);
            this.class3 = (NumberPicker) rootView.findViewById(R.id.class3);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.btn_aff = (Button) rootView.findViewById(R.id.btn_aff);
        }

    }
}
