package com.roy.tester.thread;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Administrator on 2018/2/5.
 */

public class MultiThreadTester {
    private ArrayList<String> mArrayList = new ArrayList<String>();
    private Vector<String> mVector = new Vector<>();
    private HashMap<String, String> mHashmap = new HashMap<String, String>();

    public void testArrayList10(){
        for(int i = 0; i < 10; i++){
            mArrayList.add("" + i);
        }

        for(int i = 0; i < mArrayList.size(); i++){
            String key = mArrayList.get(i);
            if(("2").equals(key)){
                mArrayList.remove(key);//for循环通过下标的方式，对集合中指定位置进行操作，每次遍历会执行判断条件 i<list.size()，满足则继续执行，执行完一次i++；
            }
        }

        Log.i("Thread","testArrayList10:size = " + mArrayList.size());
    }

    public void testArrayList11() {
        for (int i = 0; i < 10; i++) {
            mArrayList.add("" + i);
        }

        for (int i = mArrayList.size() - 1; i >= 0; i--) {

            String key = mArrayList.get(i);
            if (("2").equals(key)) {
                mArrayList.remove(key);//Normal
            }
        }

        Log.i("Thread","testArrayList11:size = " + mArrayList.size());
    }

    public void testArrayList12(){
        for(int i = 0; i < 10; i++){
            mArrayList.add("" + i);
        }

        Iterator<String> iterator = mArrayList.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if(key.equals("2")){
//                mArrayList.remove(key);//Crash java.util.ConcurrentModificationException
                iterator.remove();//Normal
            }
        }

        Log.i("Thread","testArrayList12:size = " + mArrayList.size());
    }

    public void testArrayList13(){
        for(int i = 0; i < 10; i++){
            mArrayList.add("" + i);
        }

        /*
        首先通过iterator()方法获得一个集合的迭代器，然后每次通过游标的形式依次判断是否有下一个元素，
        如果有通过 next()方法则可以取出。 注意：执行完next()方法，游标向后移一位，只能后移，不能前进。
        和for循环的区别在于，它对索引的边界值只会计算一次。所以在foreach中对集合进行添加或删掉会导致错误，
        抛出异常java.util.ConcurrentModificationException
         */
        for(String key : mArrayList){
            if(("2").equals(key)){
                mArrayList.remove(key);//Crash java.util.ConcurrentModificationException
            }
        }

        Log.i("Thread","testArrayList13:size = " + mArrayList.size());
    }



    public void testArrayList2(){
        for(int i = 0; i < 10; i++){
            mArrayList.add("" + i);
        }

        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mArrayList.remove("3");
                }
            }).start();
        }

        for(int j = 0; j< 10; j++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   for(int i = 0; i < 10; i++){
                        mArrayList.add("" + i);
                    }
                }
            }).start();
        }

        Log.i("Thread","testArrayList2:size = " + mArrayList.size());
    }

}
