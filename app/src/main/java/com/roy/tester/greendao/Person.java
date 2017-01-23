package com.roy.tester.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Administrator on 2017/1/22.
 */

@Entity
public class Person {
    @Id(autoincrement = true)//这里设置是为了让id为主键 自动增加
    private Long id;
    private String name = "";
    private String sex = "";
    @Transient
    private int tempUsageCount; // not persisted


    @Generated(hash = 1401504130)
    public Person(Long id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }


    @Generated(hash = 1024547259)
    public Person() {
    }


    public String toString(){
        return "Person: name = " + name +", sex = " +sex;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSex() {
        return this.sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }
}
