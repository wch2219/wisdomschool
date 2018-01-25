package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25/025.
 */

public class TestListBean {

    /**
     * body : [{"answer":"B","dui_answer":2,"examid":"9","option":"A、1条,B、2条,C、3条,D、4条","option_list":["A、1条","B、2条","C、3条","D、4条"],"title":"公鸡有几条腿"},{"answer":"B","dui_answer":2,"examid":"10","option":"A、1条,B、2条,C、3条,D、4条","option_list":["A、1条","B、2条","C、3条","D、4条"],"title":"公鸡有几条腿"}]
     * code : 200
     * result : 获取成功
     */

    private int code;
    private String result;
    private List<BodyBean> body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * answer : B
         * dui_answer : 2
         * examid : 9
         * option : A、1条,B、2条,C、3条,D、4条
         * option_list : ["A、1条","B、2条","C、3条","D、4条"]
         * title : 公鸡有几条腿
         */

        private String answer;
        private int dui_answer;
        private int selete_answer;
        private String examid;
        private String option;
        private String title;
        private List<String> option_list;

        public int getSelete_answer() {
            return selete_answer;
        }

        public void setSelete_answer(int selete_answer) {
            this.selete_answer = selete_answer;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getDui_answer() {
            return dui_answer;
        }

        public void setDui_answer(int dui_answer) {
            this.dui_answer = dui_answer;
        }

        public String getExamid() {
            return examid;
        }

        public void setExamid(String examid) {
            this.examid = examid;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getOption_list() {
            return option_list;
        }

        public void setOption_list(List<String> option_list) {
            this.option_list = option_list;
        }
    }
}
