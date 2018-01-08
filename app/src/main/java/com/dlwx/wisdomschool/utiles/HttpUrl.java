package com.dlwx.wisdomschool.utiles;

/**
 * Created by Administrator on 2018/1/2/002.
 */

public class HttpUrl {
    private static final String BaseUrl = "http://192.168.0.199/school/index.php/Mobile/";
    public static final String get_teacher_code = BaseUrl + "Register/get_teacher_code";//获取教师邀请码
    public static final String check_teacher_code = BaseUrl + "Register/check_teacher_code";//教师邀请码验证
    public static final String login = BaseUrl + "Login/login";//登录
    public static final String setnewpassword = BaseUrl + "Register/setnewpassword";//忘记密码
    public static final String register = BaseUrl + "Register/addtelephone";//注册
    public static final String UploadFile = BaseUrl + "Mobile/uploadFile";//上传图片
    public static final String createclass = BaseUrl + "Classroom/createclass";//创建班级
    public static final String Classroom = BaseUrl + "Classroom/index";//我创建的班级/我加入的班级
    public static final String classdesc = BaseUrl + "Classroom/myclass";//班级详情
    public static final String get_classinfo = BaseUrl + "Classroom/get_classinfo";//班级信息
    public static final String set_classinfo = BaseUrl + "Classroom/set_classinfo";//设置班级信息
    public static final String Classapplylist = BaseUrl + "Classroom/applylist"; //班级申请列表
    public static final String findClass = BaseUrl + "Classroom/findclass";//查找班级
    public static final String batch_adduser = BaseUrl + "Classroom/batch_adduser";//分享成员进目标班级
    public static final String JoinClass = BaseUrl + "Classroom/addclass";//加入班级
    public static final String Edit_Apply = BaseUrl + "Classroom/edit_apply";//同意/忽略入班操作，同意/忽略退班操作
    public static final String set_teach = BaseUrl + "Classroom/set_teacher";//添加任课老师
    public static final String LookMemberMess = BaseUrl + "Classroom/class_one_userinfo";//点击成员头像查看联系人信息
    public static final String changeClassJurisd = BaseUrl + "Classroom/edit_user_power";//班级内成员权限修改
    public static final String getClassFile = BaseUrl + "Classroom/classfile";//班级文件
    public static final String CreateClassFile = BaseUrl + "Classroom/makefolder";//"创建班级文件夹";
    public static final String AddFile = BaseUrl + "Classroom/addfile";//添加文件
    public static final String deleteClass = BaseUrl + "Classroom/delclass";//删除班级
    public static final String setWorkTime = BaseUrl + "User/set_worktime";//设置工作时间
}
