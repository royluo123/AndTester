package com.roy.tester.aidl;

import java.util.List;

import com.roy.tester.aidl.IAidlService.Stub;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/12.
 */
public class AidlService extends Service {
    private Context mContext = null;

    //实现AIDL接口中各个方法
    private IAidlService.Stub binder = new Stub(){
        private String name = null;
        public int getAccountBalance() throws RemoteException {
            return 100000;
        }

        public int getCustomerList(String branch, String[] customerList)
                throws RemoteException {
            customerList[0] = name;
            Log.i("lxw", "Name:" + branch);
            return 0;
        }

        public void setOwnerNames(List<String> names) throws RemoteException {
            name = names.get(0);
            Log.i("lxw","Size:"+names.size()+"=="+names.get(0));
        }

        public void showTest() throws RemoteException {
            Intent intent = new Intent(mContext, AidlServerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("lxw", "AidlService : onBind");
        mContext = this;
        return binder;		//返回AIDL接口实例化对象
    }
}