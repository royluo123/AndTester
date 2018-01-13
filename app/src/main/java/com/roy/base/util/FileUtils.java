/**
 *
 */
package com.roy.base.util;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.ArrayList;

public class FileUtils {
    private final static String TAG = "FileUtils";

    private final static String BAK_EXPANDED_NAME = ".bak";
    /**
     *
     * 
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

    public static String readFileToString(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return readToString(inputStream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] readFileToByte(String filePath) throws Exception {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        InputStream inputStream = null;
        inputStream = new FileInputStream(file);
        byte[] data = toByteArray(inputStream);
        return data;
    }

    public static void saveByteToSDFile(final byte[] byteData, final String filePathName)
            throws Exception {
        saveByteToSDFile(byteData, filePathName, false);
    }

    public static void saveByteToSDFile(final byte[] byteData, final String filePathName, boolean append)
            throws Exception {
        File newFile = createNewFile(filePathName, append);
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(byteData);
        fileOutputStream.flush();
        fileOutputStream.close();
    }


    /**
     * 
     * @param path
     *
     * @param append
     *
     * @return
     */
    public static File createNewFile(String path, boolean append) {
        File newFile = new File(path);
        if (!append) {
            if (newFile.exists()) {
                newFile.delete();
            }
        }
        if (!newFile.exists()) {
            try {
                File parent = newFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                newFile.createNewFile();
            } catch (Exception e) {
                //#if (debug == true)
                e.printStackTrace();
                //#endif
            }
        }
        return newFile;
    }

    /**
     *
     * 
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        boolean result = false;
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                result = file.exists();
                file = null;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return result;
    }

    public static boolean deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 
     * @param inputStream
     * @param encoding
     * @return
     */
    public static String readToString(InputStream inputStream, String encoding) {
        InputStreamReader in = null;
        try {
            StringWriter sw = new StringWriter();
            in = new InputStreamReader(inputStream, encoding);
            copy(in, sw);
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return null;
    }

    private static int copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[1024 * 4];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    /**
     * Reads a text file.
     *  
     * @param file the text file
     * @return the lines of the text file
     * @throws FileNotFoundException when the file was not found
     * @throws IOException when file could not be read.
     */
    public static String[] readTextFile(File file )
            throws FileNotFoundException, IOException
            {
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            lines.add( line );
        }
        in.close();
        return (String[]) lines.toArray( new String[ lines.size() ]);
            }

    /**
     * Reads a text file.
     *
     * @param file
     *            the text file
     * @param encoding
     *            the encoding of the textfile
     * @return the lines of the text file
     * @throws FileNotFoundException
     *             when the file was not found
     * @throws IOException
     *             when file could not be read.
     */
    public static String[] readTextFile(File file, String encoding)
            throws FileNotFoundException, IOException {
        return readTextFile(new FileInputStream(file), encoding);
    }

    /**
     * Reads the text from the given input stream in the default encoding.
     *
     * @param in
     *            the input stream
     * @param encoding
     *            the encoding of the textfile
     * @return the text contained in the stream
     * @throws IOException
     *             when stream could not be read.
     */
    public static String[] readTextFile(InputStream in, String encoding)
            throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader bufferedIn;
        if (encoding != null) {
            bufferedIn = new BufferedReader(new InputStreamReader(in, encoding));
        } else {
            bufferedIn = new BufferedReader(new InputStreamReader(in));
        }
        String line;
        while ((line = bufferedIn.readLine()) != null) {
            lines.add(line);
        }
        bufferedIn.close();
        in.close();
        return (String[]) lines.toArray(new String[lines.size()]);
    }

    public static void writeContentToFile(String aPath, String aContent)
            throws FileNotFoundException, IOException
            {
        File file = new File(aPath);
        File parentDir = file.getParentFile();
        if ( (parentDir != null) && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        PrintWriter out;
        out = new PrintWriter(new FileWriter( file )  );
        out.print( aContent);
        out.close();
            }

    public static String readContentFromFile(String aPath)
            throws FileNotFoundException, IOException
            {
        BufferedReader in = new BufferedReader(new FileReader(aPath));
        StringBuilder sContent = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sContent.append( line );
            sContent.append("\n");
        }
        in.close();
        return sContent.toString();
            }

    /*******************************************************
     *
     * @author chen.zf
     */

    public static final byte SUCCESS = 0;
    public static final byte NULL_FILE = 1;
    public static final byte ALREADY_FILE = 2;
    public static final byte MKDIR_ERROR = 3;
    public static final byte NO_SPACE = 4;
    public static final byte UNKONW_ERROR = 5;
    public static final byte NOT_ALL = 6;
    public static final byte FAILED = 7;
    public static final byte BAD_ARGUMENT = 8;
    /**
     *
     * @param item
     * @param stack：
     * @return
     */
    public static float caculateFileSize(File item, float stack) {
        if (item == null || !item.exists()) {
            return stack;
        }
        if (item.isDirectory()) {
            float temp = 0;

            if (null != item.listFiles()) {
                for (File subitem : item.listFiles()) {
                    temp += caculateFileSize(subitem, 0);
                }
            }
            return stack + temp;
        }
        else {
            return stack + item.length();
        }
    }

    /**
     *
     * @param path
     * @return
     */
    public static byte makeDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            return ALREADY_FILE;
        }
        if (file.mkdir()) {
            return SUCCESS;
        }
        return UNKONW_ERROR;
    }

    public static byte makeDirs(String path) {
        File file = new File(path);
        if (file.exists()) {
            return ALREADY_FILE;
        }
        if (file.mkdirs()) {
            return SUCCESS;
        }
        return UNKONW_ERROR;
    }

    /**
     *
     * @param item
     * @param newName
     * @return
     */
    public static byte renameFile(File item, String newName) {
        if (item == null || !item.exists()) {
            return NULL_FILE;
        }
        String root = item.getParent();
        File newFileTmp = new File(root + "/" + newName);
        if (newFileTmp.exists()) {
            return ALREADY_FILE;
        }
        if (item.renameTo(newFileTmp)) {
            newFileTmp.setLastModified(System.currentTimeMillis());//0075062【移植同步】 在文件管理，创建一个文件夹，隔几分钟后再修改它，页面属性中的修改日期没有变化
            return SUCCESS;
        }
        return UNKONW_ERROR;
    }

    public static byte renameFile(String aOriginalFullNmae, String aNewFullName) {
        if (StringUtils.isEmpty(aOriginalFullNmae) || StringUtils.isEmpty(aNewFullName)) {
            return BAD_ARGUMENT;
        }	

        File originalFile = new File(aOriginalFullNmae);
        if (null == originalFile || !originalFile.exists()) {
            return NULL_FILE;
        }

        File newFile = new File(aNewFullName);

        if (originalFile.renameTo(newFile)) {
            return SUCCESS;
        }

        return UNKONW_ERROR;
    }


    /**
     *
     * @param item
     */
    public static byte deleteFile(File item) {
        if (item == null || !item.exists()) {
            return FAILED;
        }
        byte ret = SUCCESS;
        int subret = SUCCESS;
        if (item.isDirectory()) {
            File[] filelist = item.listFiles();
            if(filelist != null){
                for (File subitem : filelist) {
                    subret = (subret == deleteFile(subitem) ? subret : NOT_ALL);
                }
            }
        }
        if (item.delete()) {
            ret = SUCCESS;
        } else {
            ret = FAILED;
        }
        return ret == subret ? ret : NOT_ALL;
    }

    /**
     *
     * @param item
     * @param destdir
     * @return
     */
    public static byte moveFile(File item, String destdir) {
        if (item == null || !item.exists()) {
            return NULL_FILE;
        }
        if(!item.canWrite()){
            return FAILED;
        }

        String newDest = destdir + "/" + item.getName();
        if (item.getAbsolutePath().equals(newDest)) {
            return SUCCESS;
        }
        if (item.isDirectory()) {
            File newFileTmp = new File(destdir + "/" + item.getName());
            if(!newFileTmp.exists()){
                newFileTmp.mkdir();
            } else{
                return ALREADY_FILE;
            }
            File[] filelist = item.listFiles();
            if (null != filelist) {
                for (File subitem : filelist) {
                    moveFile(subitem, newDest);
                }
            }
            return SUCCESS;
        }
        File newFileTmp = new File(destdir + "/" + item.getName());
        if (newFileTmp.exists()) {
            return ALREADY_FILE;
        }
        try {
            copyOneFile(item, newFileTmp);
            return SUCCESS;
        } catch (IOException e) {
            //#if (debug == true)
            e.printStackTrace();
            //#endif
            return UNKONW_ERROR;
        }
    }

    public static byte replaceFile(File item, String destdir) {
        if (item == null || !item.exists()) {
            return NULL_FILE;
        }
        String newDest;
        if (item.isDirectory()) {
            File newFileTmp = new File(destdir + "/" + item.getName());
            if (!newFileTmp.exists()) {
                newFileTmp.mkdir();
            }
            newDest = destdir + "/" + item.getName();
            File[] filelist = item.listFiles();

            if (null != filelist) {
                for (File subitem : filelist) {
                    moveFile(subitem, newDest);
                }
            }
            return SUCCESS;
        }
        File newFileTmp = new File(destdir + "/" + item.getName());
        if (newFileTmp.exists()) {
            deleteFile(newFileTmp);
        }

        try {
            copyOneFile(item, newFileTmp);
            return SUCCESS;
        } catch (IOException e) {
            //#if (debug == true)
            e.printStackTrace();
            //#endif
            return UNKONW_ERROR;
        }

    }

    /**
     *
     * @param item
     * @param destdir
     * @return
     */
    public static byte copyFile(File item, String destdir) {
        if (item == null || !item.exists()) {
            return NULL_FILE;
        }
        String newFileName = destdir + "/" + item.getName();
        File destFile = new File(newFileName);
        int pos = 1;
        if (destFile.exists()) {
            String itemName = item.getName();
            String itemExtention;
            if (-1 != itemName.lastIndexOf('.')) {
                itemExtention = itemName.substring(itemName.lastIndexOf('.'));
                itemName = itemName.substring(0, itemName.lastIndexOf('.'));
            } else {
                itemExtention = itemName;
                itemName = "";
            }
            StringBuffer sb = new StringBuffer();
            while (destFile.exists()) {
                sb.delete(0, sb.length());
                sb.append(destdir)
                .append("/")
                .append(itemName)
                .append("(")
                .append(pos)
                .append(")")
                .append(itemExtention);
                newFileName = sb.toString();
                destFile = new File(newFileName);
                pos++;
            }
        }
        if (item.isDirectory()) {
            if (!destFile.mkdir()) {
                return MKDIR_ERROR;
            }
            if (null != item.listFiles()) {
                for (File subitem : item.listFiles()) {
                    copyFile(subitem, destFile.getPath());
                }
            }
        }
        else {
            try {
                copyOneFile(item, destFile);
            } catch (Exception e) {	//
                //#if (debug == true)
                e.printStackTrace();
                //#endif
                return NO_SPACE;
            }
        }
        return SUCCESS;
    }


    static String[] mCs = new String[]{"/", "\\", "?", "*", ":", "<", ">", "|", "\""};

    public static boolean isFileNameCorrect(String fileName){
        if (null == fileName ){
            return false;
        }

        fileName = fileName.trim();
        if (fileName.length() == 0){
            return false;
        }

        for(String c : mCs){
            if (fileName.contains(c)){
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param fileName
     * @return
     */
    public static String fixFileName(final String fileName){
        if(null==fileName){
            return null;
        }
        String result = fileName.toString();


        for(String c : mCs){
            result = result.replace(c, "");
        }
        return result;
    }

    public static String reduceFileName(String fileName, int maxLen) {
        if (null == fileName || fileName.length() < maxLen) {
            return fileName;
        }

        if (maxLen <= 0) {
            return fileName;
        }

        String extension    = getExtension(fileName);
        if (null == extension) {
            return fileName.substring(0, maxLen);
        }

        int nameEndIndex    = maxLen - extension.length() - 1;
        if (nameEndIndex < 0) {
            nameEndIndex    = 0;
        }
        return fileName.substring(0, nameEndIndex) + "." + extension;
    }

    public static String getExtension(String fileName) {
        if (null == fileName) {
            return null;
        }

        int dotIndex    = fileName.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex == fileName.length() -1) {
            return null;
        }

        return fileName.substring(dotIndex + 1);
    }

    public static long getDiskAvailableSize() {
        long sDiskAvailableSize = -1;
        try {
            if (isDiskMounted()) {
                StatFs statFs = new StatFs(getExternalStorage().getPath());
                sDiskAvailableSize = 1L * statFs.getBlockSize() * statFs.getAvailableBlocks();//加个1L防止数组越界 0059855: 【下载】sdcard剩余容量超过2G时，若下载出错，会错误提示sdcard空间不足
            }
        } catch (Throwable e) {
            //#if debug == true
            e.printStackTrace();
            //#endif
        }

        return sDiskAvailableSize;
    }

    public static final boolean isDiskMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public static File getExternalStorage(){
        return Environment.getExternalStorageDirectory();
    }

    /**
     *
     * @param strFolder
     * @return
     */
    public static boolean isFolderExists(String strFolder) {
        try {
            File file = new File(strFolder);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private FileUtils(){

    }

    public static byte[] readBytes(String filePath){
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        String bakFilePath = filePath + BAK_EXPANDED_NAME;
        File bakFile = new File(bakFilePath);
        if(bakFile.exists()){
        	return readBytes(bakFile);
        }else{
        	File file = new File(filePath);
        	return readBytes(file);
        }
    }

    public static byte[] readBytes(File file) {
        FileInputStream fileInput = null;
        try{ 
            if(file.exists()){
                fileInput = new FileInputStream(file);
                byte[] data = IOUtils.readFullBytes(fileInput);
                return data;
            } 
        }catch (Exception e) {

        }finally{
            IOUtils.closeInputStream(fileInput);
            fileInput = null;
        }
        return null;
    }

    
    
    public static boolean writeBytes(String filePath, String fileName, byte[] data){
        if(data == null){
            return false;
        }
        return writeBytes(filePath, fileName, data, 0, data.length);
    }

    public static boolean writeBytes(String filePath, String fileName, byte[] data, int offset, int len){
        if(StringUtils.isEmpty(filePath) || StringUtils.isEmpty(fileName) || data == null){
            return false;
        }
        String tempFileName = System.currentTimeMillis() + fileName;

        File tempFile = createNewFile(filePath + tempFileName);

        boolean result = writeBytes(tempFile, data, offset, len);
        String sourcePath = filePath + fileName;
    	File soucreFile = new File(sourcePath);
        boolean soucreFileExist = soucreFile.exists();
        if(result){        	
        	String bakFileName = sourcePath + BAK_EXPANDED_NAME;
        	if(soucreFileExist){
        		try {
        			if(rename(soucreFile,bakFileName)){
        				//delete(sourcePath);
        				if(rename(tempFile, sourcePath)){
        					result = delete(bakFileName);
            			}
        			}
        		} catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }
        	}else{
        		result = rename(tempFile, sourcePath);
        	}
        }
        
        return result;
    }

    /**
     *
     * @param file
     * @param headData
     * @param bodyData
     * @param bodyOffset
     * @param bodyLen
     * @param forceFlush
     * @return
     */
    public static boolean writeBytes(File file, byte[] headData, byte[] bodyData, int bodyOffset, int bodyLen, boolean forceFlush){
        FileOutputStream fileOutput = null;
        try{
            fileOutput = new FileOutputStream(file);
            if(headData != null) {
                fileOutput.write(headData);
            }
            fileOutput.write(bodyData, bodyOffset, bodyLen);
            fileOutput.flush();
            if (forceFlush) {
                FileDescriptor fd = fileOutput.getFD();
                if(fd != null){
                    fd.sync();
                }
            }
            return true;
        }catch (Exception e) {
            Log.d(TAG, "Some errors", e);
        }finally{
            IOUtils.closeOutputStream(fileOutput);
            fileOutput = null;
        }
        return false;
    }
    
    public static boolean writeBytes(File file, byte[] data, int offset, int len){
        FileOutputStream fileOutput = null;
        try{
            fileOutput = new FileOutputStream(file);
            fileOutput.write(data, offset, len);
            fileOutput.flush();
            FileDescriptor fd = fileOutput.getFD();
            if(fd != null){
                fd.sync();
            }
            return true;
        }catch (Exception e) {

        }finally{
            IOUtils.closeOutputStream(fileOutput);
            fileOutput = null;
        }
        return false;
    }



    public static File createNewFile(String path) {
        return createNewFile(path, false);
    }



    public static boolean rename(File file, String newName){
        return file.renameTo(new File(newName));
    }


    public static boolean delete(String path) {
        return delete(new File(path));
    }

    public static boolean delete(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            for (File item : children) {
                boolean success = delete(item);
                if ( !success ) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return file.delete();
    } 

    public static void appendBytesToSDFile(String aFileName, byte[] aByteData){
        FileOutputStream fileOutputStream = null;
        File newFile = null;
        try {    		
            newFile = new File(getSDPath() + File.separator + aFileName);
            fileOutputStream = new FileOutputStream(newFile, true);
            fileOutputStream.write(aByteData);
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            IOUtils.safeClose(fileOutputStream);
            newFile = null;
        }
    }

    public static void appendStringToSDFile(String aFileName, String aData){
        appendBytesToSDFile(aFileName, aData.getBytes());
    }

    public static boolean deleteDir(String sPath) {
        if (StringUtils.isEmpty(sPath)) {
            return false;
        }

        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }  
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;
        File[] files = dirFile.listFiles();
        if (null == files) {
            return false;
        }
        
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } else {
                flag = deleteDir(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
    
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }
   
    public static String getFileMd5(File file) {
        FileInputStream fis = null;
        String ret = null;
        try {
            fis = new FileInputStream(file);
            byte[] buf = new byte[16384];
            MessageDigest md = MessageDigest.getInstance("MD5");
            int len = 0;
            int totalLen = 0;
            while ((len = fis.read(buf)) > 0) {
                md.update(buf, 0, len);
                totalLen += len;
            }
            
            byte[] digest = md.digest();
            
            ret = bytes2Hex(digest);
        } catch (Exception e) {
            StackTraceElement[] stes = e.getStackTrace();
            if (stes != null) {
                String info = "";
                if (stes.length > 0 && stes[0] != null && stes[0].toString() != null) {
                    info += stes[0].toString();
                }
                
                if (stes.length > 1 && stes[1] != null && stes[1].toString() != null) {
                    info += stes[1].toString();
                }
            }
        } finally {
            if (fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return ret;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;

        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
    
    /**
     *
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    public static void copyOneFile(File sourceFile, File destFile)
            throws IOException {
        copyOneFile(sourceFile,destFile, true);
    }
    /**
     * 
     * @param sourceFile
     * @param destFile
     * @param isAppend
     * @throws IOException
     */
    public static void copyOneFile(File sourceFile, File destFile, boolean isAppend)throws IOException {
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        FileOutputStream output = new FileOutputStream(destFile,isAppend);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);
        byte[] b = new byte[1024 * 8];
        int len;
        try {
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
        } finally {
            outBuff.flush();
            inBuff.close();
            input.close();
            outBuff.close();
            output.close();
        }
        
    }

	
    /**
     * Writes (and creates) a text file.
     * 
     * @param file the file to which the text should be written
     * @param lines the text lines of the file
     * @throws IOException when there is an input/output error during the saving
     */
    public static void writeTextFile(File file, String[] lines, boolean isAppend)
    throws IOException
    {
        File parentDir = file.getParentFile();
        if ( (parentDir != null) && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        PrintWriter out = new PrintWriter(new FileWriter( file, isAppend ) );
        for (int i = 0; i < lines.length; i++) {
            out.println( lines[i] );
        }
        
        out.close();
    }

    public static String readFile(File f) {

        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(f));
            String content = buffer.readLine();

            return content;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.safeClose(buffer);
        }
        return null;
    }

    public static boolean isFileExists(String strFile) {
        if(StringUtils.isEmpty(strFile)){
            return false;
        }
        try {
            File file = new File(strFile);
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

}
