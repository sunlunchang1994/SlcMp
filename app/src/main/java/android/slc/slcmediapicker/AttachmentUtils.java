package android.slc.slcmediapicker;

import android.slc.medialoader.bean.ExtensionFileProperty;
import android.slc.medialoader.bean.i.IFileProperty;
import android.slc.mp.SlcMp;
import android.text.TextUtils;

import java.util.ArrayList;

public class AttachmentUtils {
    public static final int TYPE_WORD = 1;
    public static final int TYPE_EXCEL = 2;
    public static final int TYPE_PPT = 3;
    public static final int TYPE_PDF = 4;
    public static final int TYPE_COMPRESSION = 5;//压缩文件
    public static final String TYPE_NAME_WORD = "Word";
    public static final String TYPE_NAME_EXCEL = "Excel";
    public static final String TYPE_NAME_PPT = "Ppt";
    public static final String TYPE_NAME_PDF = "Pdf";
    public static final String TYPE_NAME_COMPRESSION = "压缩文件";
    public static final IFileProperty Word = ExtensionFileProperty.getWordFileProperty();
    public static final IFileProperty Excel = ExtensionFileProperty.getExcelFileProperty();
    public static final IFileProperty Ppt = ExtensionFileProperty.getPowerPointFileProperty();
    public static final IFileProperty Pdf = ExtensionFileProperty.getPdfFileProperty();
    public static final IFileProperty Compression = ExtensionFileProperty.getInstance(".zip", ".rar", ".z7");

    public static final int IS_ALLOW_EDIT_STATUS_EDF = 0;//默认状态，根据是否为本地资源进行识别
    public static final int IS_ALLOW_EDIT_STATUS_AGREE = 1;//允许
    public static final int IS_ALLOW_EDIT_STATUS_DISAGREE = 2;//不允许

    public static int expandName2MediaType(String expandName) {
        expandName = expandName.toLowerCase();
        if (TextUtils.isEmpty(expandName)) {
            return SlcMp.MEDIA_TYPE_FILE;
        }
        if (isThisExpandName(expandName, "mp3", "m3u", "m4a", "wma", "wav")) {
            return SlcMp.MEDIA_TYPE_AUDIO;
        }
        if (isThisExpandName(expandName, "png", "jpg", "gif")) {
            return SlcMp.MEDIA_TYPE_PHOTO;
        }
        if (isThisExpandName(expandName, "mp4", "3gp", "avi", "mpeg")) {
            return SlcMp.MEDIA_TYPE_VIDEO;
        }
        if (isThisExpandName(expandName, "doc", "docx")) {
            return TYPE_WORD;
        }
        if (isThisExpandName(expandName, "xls", "xlsx")) {
            return TYPE_EXCEL;
        }
        if (isThisExpandName(expandName, "ppt", "pptx")) {
            return TYPE_PPT;
        }
        if (isThisExpandName(expandName, "pdf")) {
            return TYPE_PDF;
        }
        if (isThisExpandName(expandName, "zip", "rar", "z7")) {
            return TYPE_COMPRESSION;
        }
        return SlcMp.MEDIA_TYPE_FILE;
    }

    private static boolean isThisExpandName(String expandName, String... expands) {
        if (expandName == null) {
            return false;
        }
        if (expands == null || expands.length == 0) {
            return false;
        }
        for (String expand : expands) {
            if (expandName.equals(expand) || expandName.contains(expand)) {
                return true;
            }
        }
        return false;
    }

}
