package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.wisdomschool.R;

/**
 * 显示输入图片验证码的窗体
 */

public class AuthWindow extends PopupWindow{
    private static View paren;
    private static ViewHolder vh;
    private static Context ctx;
    private String imageName;
    private String imgurl;
    public  void startShowPopu(Context ctxs, View parent,String imgurl,String imageName) {
        paren = parent;
        ctx = ctxs;
        this.imageName = imageName;
        this.imgurl = imgurl;
        Glide.with(ctx).load(imgurl).into(vh.iv_imgauth);

        this.showAtLocation(paren, Gravity.CENTER, 0, 0);
        ShowKeyboard(vh.et_imgauth);
    }


    public AuthWindow(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popu_auth, null);
        this.setFocusable(true);
        this.setContentView(view);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        vh = new ViewHolder(view);
        //设置弹出窗体需要软键盘，
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //再设置模式，和Activity的一样，覆盖。
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        vh.btn_submit.setOnClickListener(listener);
        vh.iv_imgauth.setOnClickListener(imagListener);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new ColorDrawable(0x77000000));
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                authListener.getResuktAuth("");
            }
        });
    }


   private View.OnClickListener listener = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           String auth = vh.et_imgauth.getText().toString().trim();
           if (TextUtils.isEmpty(auth)) {
               Toast.makeText(ctx, "请输入图形验证码", Toast.LENGTH_SHORT).show();
               return;
           }
           String[] split = imgurl.split("/");
           String s = split[split.length - 1];
           if (!imageName.equals(s)) {
               Toast.makeText(ctx, "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
               vh.et_imgauth.setText("");

               return;
           }
           if (authListener != null) {
               AuthWindow.this.dismiss();
               authListener.getResuktAuth(auth);
           }
       }
   };
    private View.OnClickListener imagListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (authListener != null) {
                AuthWindow.this.dismiss();
                authListener.gainImg();
            }
        }
    };
    //显示虚拟键盘
    public  void ShowKeyboard(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );

        imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);

    }
    private static class ViewHolder {
        public View rootView;
        public ImageView iv_imgauth;
        public EditText et_imgauth;
        public Button btn_submit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_imgauth = (ImageView) rootView.findViewById(R.id.iv_imgauth);
            this.et_imgauth = (EditText) rootView.findViewById(R.id.et_imgauth);
            this.btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        }

    }
    public static AuthListener authListener;
    public interface AuthListener {

        void getResuktAuth(String auth);
        void gainImg();
    }
    public  void setAuthListener(AuthListener listener){
        authListener = listener;
    }
}
