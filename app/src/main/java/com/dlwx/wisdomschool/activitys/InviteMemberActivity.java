package com.dlwx.wisdomschool.activitys;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 邀请成员
 */
public class InviteMemberActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_QQ)
    LinearLayout llQQ;
    @BindView(R.id.ll_WX)
    LinearLayout llWX;
    @BindView(R.id.ll_phoneaddress)
    LinearLayout llPhoneaddress;
    @BindView(R.id.ll_inputphone)
    LinearLayout llInputphone;
    @BindView(R.id.ll_myotherclass)
    LinearLayout llMyotherclass;
    @BindView(R.id.ll_downnotifi)
    LinearLayout llDownnotifi;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_invite_member);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成员邀请");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.ll_QQ, R.id.ll_WX, R.id.ll_phoneaddress, R.id.ll_inputphone, R.id.ll_myotherclass, R.id.ll_downnotifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_QQ://从QQ邀请
                break;
            case R.id.ll_WX://从微信邀请
                getWechatApi();
                break;
            case R.id.ll_phoneaddress://从手机通讯录邀请
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK,uri);
                startActivityForResult(intent,0);
                break;
            case R.id.ll_inputphone://输入手机号邀请
                startActivity(new Intent(ctx,InputPhoneInviteActivity.class));
                break;
            case R.id.ll_myotherclass://从我的其他班级添加成员
                startActivity(new Intent(ctx,MyClassMemberActivity.class));
                break;
            case R.id.ll_downnotifi://下载家长使用通知单
                startActivity(new Intent(ctx,DownNotifitionActivity.class));
                break;
        }
    }
    /**
     * 跳转到微信
     */
    private void getWechatApi(){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            Toast.makeText(ctx, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode){
                    case 0:
                        Uri uri=data.getData();
                        String[] contacts=getPhoneContacts(uri);
                        wch("姓名:"+contacts[0]+" "+"手机号:"+contacts[1]);
                        break;
                }
    }

    private String[] getPhoneContacts(Uri uri){
        String[] contact=new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0]=cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if(phone != null){
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        }
        else{
            return null;
        }
        return contact;
    }
}
