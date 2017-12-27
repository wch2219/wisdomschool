package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16/016.
 */

public class ClassSeleteBean {
        private List<Class1> class1;

    public List<Class1> getClass1() {
        return class1;
    }

    public void setClass1(List<Class1> class1) {
        this.class1 = class1;
    }

    public static class Class1{
            private String class1name;
            private List<Class2> class2;

        public String getClass1name() {
            return class1name;
        }

        public void setClass1name(String class1name) {
            this.class1name = class1name;
        }

        public List<Class2> getClass2() {
            return class2;
        }

        public void setClass2(List<Class2> class2) {
            this.class2 = class2;
        }

        public static class Class2{
            private String class2name;
            private List<Class3> class3;

            public String getClass2name() {
                return class2name;
            }

            public void setClass2name(String class2name) {
                this.class2name = class2name;
            }

            public List<Class3> getClass3() {
                return class3;
            }

            public void setClass3(List<Class3> class3) {
                this.class3 = class3;
            }

            public static class Class3{
                    private String class3name;

                public String getClass3name() {
                    return class3name;
                }

                public void setClass3name(String class3name) {
                    this.class3name = class3name;
                }
            }
            }

        }

}
