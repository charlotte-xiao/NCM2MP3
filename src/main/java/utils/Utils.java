package utils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author charlottexiao
 */
public class Utils {

    /**
     * 获取长度
     * 功能:将用小端字节排序,无符号整型4字节的长度信息转换为十进制数
     *
     * @param bytes 长度信息的字节数组
     * @return 长度
     */
    public static int getLength(byte[] bytes) {
        int len = 0;
        len |= bytes[0] & 0xff;
        len |= (bytes[1] & 0xff) << 8;
        len |= (bytes[2] & 0xff) << 16;
        len |= (bytes[3] & 0xff) << 24;
        return len;
    }

    /**
     * 图片专辑MIME类型
     * 功能:获取图片的MIME类型
     *
     * @param albumImage 图片数据
     * @return 图片类型
     */
    public static String albumImageMimeType(byte[] albumImage) {
        byte[] mPNG = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};// PNG file header
        if (albumImage.length > 8) {
            for (int i = 0; i < 8; i++) {
                if (albumImage[i] != mPNG[i]) {
                    return "image/jpg";
                }
            }
        }
        return "image/png";
    }

    /**
     * 获取文件夹中所有NCM文件
     * 功能:将文件夹内的所有NCM文件存放到列表中
     *
     * @param arrayList 存放NCM文件的列表
     * @param file      文件
     */
    public static void listAllFiles(ArrayList<File> arrayList, File file) {
        if (!file.isDirectory()) {
            String name = file.getName().trim();
            if (name.length() > 3 && "ncm".equalsIgnoreCase(name.substring(name.length() - 3))) {
                arrayList.add(file);
            }
            return;
        }
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            listAllFiles(arrayList, f);
        }
    }
}
