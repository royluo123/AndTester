package com.roy.base.log;

import com.roy.base.util.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/6/19.
 */
public class ModuleLog {

    public static void writePollingLog(String log){
        String file  = FileUtils.getSDPath() + File.separator + "Pooling" + File.separator +"test.txt";
        try {
            String old = FileUtils.readFileToString(file);
            StringBuilder builder = new StringBuilder();
            String newLog = builder.append(old).append("\r\n").append(System.currentTimeMillis()).append(":").append(log).toString();
            FileUtils.saveByteToSDFile(newLog.getBytes(), file, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
