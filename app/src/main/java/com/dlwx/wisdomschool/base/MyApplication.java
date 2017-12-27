package com.dlwx.wisdomschool.base;

import android.app.Application;

/**
 * Created by Administrator on 2017/12/12/012.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static String classnames = "{\n" +
            "    \"class1\": [\n" +
            "        {\n" +
            "            \"class1name\": \"幼儿园\",\n" +
            "            \"class2\": [\n" +
            "                {\n" +
            "                    \"class2name\": \"小班\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"中班\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"大班\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"class1name\": \"小学\",\n" +
            "            \"class2\": [\n" +
            "                {\n" +
            "                    \"class2name\": \"一年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"二年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"三年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"四年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"五年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"六年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"class1name\": \"初中\",\n" +
            "            \"class2\": [\n" +
            "                {\n" +
            "                    \"class2name\": \"七年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"八年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"class2name\": \"九年级\",\n" +
            "                    \"class3\": [\n" +
            "                        {\n" +
            "                            \"class3name\": \"1班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"2班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"3班\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"class3name\": \"4班\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

}
