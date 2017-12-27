package com.dlwx.wisdomschool.utiles;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8/008.
 */

public class CityJson {


    /**
     * 从assert文件夹中获取json数据
     */
    public static SanJiLianDBean initJsonData(Context ctx) {
        SanJiLianDBean sanJiLianDBean = new SanJiLianDBean();
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = ctx.getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len = -1;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "gb2312"));//根据字节长度设置编码
            }
            is.close();//关闭流
            //为json赋值
            JSONObject jsonObject = new JSONObject(sb.toString());
            Gson gson = new Gson();
            sanJiLianDBean = gson.fromJson(jsonObject.toString(), SanJiLianDBean.class);
//            Log.i("wch",jsonObject+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanJiLianDBean;
    }


    /**
     * @作者 wch
     * @create at 2017/1/12 0012 下午 4:24
     * @name 城市三级联动
     */
    public static class SanJiLianDBean {
        private List<CityList> citylist;

        public List<CityList> getCitylist() {
            return citylist;
        }

        public void setCitylist(List<CityList> citylist) {
            this.citylist = citylist;
        }

        public class CityList {

            private String p;
            private List<CBean> c;//市集合

            public String getP() {
                return p;
            }

            public void setP(String p) {
                this.p = p;
            }

            public List<CBean> getC() {
                return c;
            }

            public void setC(List<CBean> c) {
                this.c = c;
            }

            public void setC() {

            }

            public class CBean {
                private String n;
                private List<aBean> a;//县区集合

                public String getN() { //市名
                    return n;
                }

                public void setN(String n) {
                    this.n = n;
                }

                public List<aBean> getA() {
                    return a;
                }

                public void setA(List<aBean> a) {
                    this.a = a;
                }

                public class aBean {
                    private String s;//县区名字

                    public String getS() {
                        return s;
                    }

                    public void setS(String s) {
                        this.s = s;
                    }
                }
            }
        }
    }


}
