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
}
