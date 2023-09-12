package com.hui.tally.db;
/** 描述记录一项待办的相关内容类*/
public class TodoBean {
    int id;
    String todoName;
    int year;
    int month;
    int day;
    float money;

    public TodoBean(int id, String todoName, int year, int month, int day,float money) {
        this.id = id;
        this.todoName = todoName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.money = money;
    }
    public TodoBean(){}
    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
