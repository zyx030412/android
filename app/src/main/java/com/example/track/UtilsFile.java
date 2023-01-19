package com.example.track;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//相关介绍请看 https://blog.csdn.net/qq_45146250/article/details/124273332
//android文件读取工具类,Android基础：数据存储(一)：文件存储的工具类UtilsFile
//https://blog.csdn.net/weixin_42360762/article/details/117287754
public class UtilsFile extends AppCompatActivity {
    private static final String TAG = "UtilsFile";
    /**
     * SD卡的状态
     */
    public static final String SDCARDSTATE = Environment.getExternalStorageState();
    /**
     * 文件在SD卡中存储的根路径
     */
    public static final String SDCARDPATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 获取包名下files的路径
     *
     * @param context
     * @return 包名/files
     */
    public static String getFilePath(Context context) {
        Log.d(TAG, "获取包名下files的路径:" + context.getApplicationContext().getFilesDir().getPath());
//       /data/data/<包>/files
        return context.getApplicationContext().getFilesDir().getPath();
    }

    /**
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        return context.getApplicationContext().getCacheDir().getPath();
    }

    /**
     * 保存文件
     *
     * @param data     数据内容
     * @param path     绝对路径
     * @param fileName 文件名
     * @return true 保存成功，false 保存失败
     */
    public static boolean saveFile(String data, String path, String fileName) {
        File file = new File(path);
        if (!file.exists()) {
//            创建文件
            file.mkdirs();
        }
//        如何有文件1.在后面添加2.替代原文件
        file = new File(file, fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(data.getBytes());
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static String readFile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            try {
                throw new Exception("it's not a file,please check!");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        StringBuffer sb = new StringBuffer();

        try {

            FileInputStream in = new FileInputStream(file);

            byte[] b = new byte[in.available()];

            int read = in.read(b);

            while (read != -1) {

                sb.append(new String(b));

                read = in.read(b);
            }
            in.close();

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }
        return sb.toString();

    }

    /**
     * 删除路径下的：文件和文件夹，包括当前文件夹
     * <p>
     * //     * @param filepath ：绝对路径
     *
     * @return true:删除成功，false：删除失败
     * <p>
     * 注意：当前路径不存在时，也返回true
     */

    public static boolean deletFile(String absoluteFilePath) {

        File file = new File(absoluteFilePath);

        try {

            if (!file.exists()) {

                return true;
            }
            if (file.isFile()) {

                file.delete();

                return true;
            }
            if (!absoluteFilePath.endsWith(File.separator)) {

                absoluteFilePath = absoluteFilePath + File.separator;
            }
            if (file.isDirectory()) {

                if (file.listFiles().length == 0) {

                    file.delete();

                } else {

                    File[] files = file.listFiles();

                    for (File dirFile : files) {

                        deletFile(dirFile.getAbsolutePath());

                    }
                }
                file.delete();
            }
        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
        return true;

    }

    public static boolean saveToCahe(String data, String dir, String fileName, Context context) {

        String path = getCachePath(context) + dir;

        return saveFile(data, path, fileName);

    }

    public static String readFromCahe(String dir, Context context) {

        String path = getCachePath(context) + dir;

        return readFile(path);

    }

    public static boolean deletInCahe(String dir, Context context) {

        String path = getCachePath(context) + dir;

        return deletFile(path);

    }

    public static boolean clearCahe(Context context) {

        return deletFile(getCachePath(context));

    }

    /**
     * 将文件保存到应用包名/files目录下
     * ？？？？？？？？？？？
     *
     * @param data     要保存的内容
     * @param dir      保存的相对路径，不包括文件名："/myproject/function1/aa"--目录
     * @param fileName 文件名称："1.txt"
     * @return
     */

    public static boolean saveToFile(String data, String dir, String fileName, Context context) {

        String path = getFilePath(context) + dir;

        return saveFile(data, path, fileName);

    }

    /**
     * 从应用包名/files目录下读取文件
     *
     * @param dir     ："/dd/1.txt"
     * @param context
     * @return
     */

    public static String readFromFile(String dir, Context context) {

        String path = getFilePath(context) + dir;

        return readFile(path);

    }

    public static boolean deletInFile(String dir, Context context) {

        String path = getFilePath(context) + dir;

        return deletFile(path);

    }
//    --------------------------------------------------------

    /**
     * openFileOutput android提供
     *
     * @param data     要保存的数据
     * @param fileName 文件名称
     * @param type     权限文件的保存方式Context.MODE_PRIVATE = 0(user信息保存时0)
     *                 Context.MODE_APPEND = 32768
     *                 Context.MODE_WORLD_READABLE = 1
     *                 Context.MODE_WORLD_WRITEABLE = 2
     *                 Context.MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，
     *                 在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中。可以使用Context.MODE_APPEND
     *                 Context.MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
     *                 Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件。
     *                 MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；
     *                 MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
     * @param
     * @return
     */
    public static boolean saveOpenFile(String data, String fileName, int type, Context context) {
        try {
            Log.d(TAG, "用户信息保存到user.csv中");
            FileOutputStream outputStream = context.openFileOutput(fileName, type);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            Log.d(TAG, "用户信息保存到user.csv中--没有成功");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String loadOpenFile(String fileName, Context context) {
        String fileStr;
        try {
            FileInputStream inStream = context.openFileInput(fileName);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
//                   while ((sCurrentLine = br.readLine()) != null) {
//                    Log.d(TAG,"数据不为空sCurrentLine:"+sCurrentLine);
//                    userStr.append(sCurrentLine);
//                }
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            stream.close();
            inStream.close();
            fileStr = stream.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            return null;
        }
        return fileStr;
    }

//----------------------------------------------------------------------------------------

    /**
     * 保存数据到SD卡上，
     *
     * @param data：要保存的数据内容
     * @param dir：文件的相对路径,"/aa/bb"
     * @param fileName：文件名,"1.txt"
     * @return boolean 是否保存成功，true保存成功，false保存失败
     */

    public static boolean saveToSDCard(String data, String dir, String fileName) {

        if (!Environment.MEDIA_MOUNTED.equals(SDCARDSTATE)) {

            try {

                throw new Exception("SDCard state error");
            } catch (Exception e) {

                e.printStackTrace();

                return false;
            }
        }
        return saveFile(data, SDCARDPATH + dir, fileName);
    }

    /**
     * //     * @param String RelativePath 文件在sd卡中的路径："/bb/dd/1.txt"
     *
     * @return String 文件内容
     */
    public static String getFromSDCard(String dir) {
        String path = SDCARDPATH + dir;
        return readFile(path);
    }

    /**
     * 从SD卡上删除文件
     *
     * @param path
     * @return 是否删除文件成功
     */
    public static boolean deletFileInSDCard(String path) {
        return deletFile(SDCARDPATH + path);
    }


}