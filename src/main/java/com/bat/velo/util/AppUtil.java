package com.bat.velo.util;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {

    /**
     * Checks if is collection empty.
     *
     * @param collection the collection
     * @return true, if is collection empty
     */
    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is object empty.
     *
     * @param object the object
     * @return true, if is object empty
     */
    public static boolean isObjectEmpty(Object object) {
        if (object == null) return true;
        else if (object instanceof String) {
            if (((String) object).trim().length() == 0) {
                return true;
            }
        } else if (object instanceof Collection) {
            return isCollectionEmpty((Collection<?>) object);
        }
        return false;
    }

    /**
     * Gets the bean to json string.
     *
     * @param beanClass the bean class
     * @return the bean to json string
     */
    public static String getBeanToJsonString(Object beanClass) {
        return new Gson().toJson(beanClass);
    }

    /**
     * Gets the bean to json string.
     *
     * @param beanClasses the bean classes
     * @return the bean to json string
     */
    public static String getBeanToJsonString(Object... beanClasses) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object beanClass : beanClasses) {
            stringBuilder.append(getBeanToJsonString(beanClass));
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    /**
     * Concatenate.
     *
     * @param listOfItems the list of items
     * @param separator   the separator
     * @return the string
     */
    public String concatenate(List<String> listOfItems, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> stit = listOfItems.iterator();

        while (stit.hasNext()) {
            sb.append(stit.next());
            if (stit.hasNext()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String replaceCharAt(String s, int pos, char c) {
        return s.substring(0,pos) + c + s.substring(pos+1);
    }

    public static String camelCaseToUnderscore(String text) {
        // change from camelCase to camel_case --> for Hibernate-compliant field name
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "_"+m.group().toLowerCase());
        }
        m.appendTail(sb);
        String fieldName = sb.toString();

        return fieldName;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(AppUtil.class);

    public static String handleFileUpload(String scanFile, String SAVE_DIR, String ext) {

        String outputFilePath = "file" + "_" + new Timestamp(System.currentTimeMillis()) + "." + ext;

        if (!scanFile.isEmpty()) {

            try {
//                LOGGER.info("Decoding base64");
//
//                LOGGER.info("Creating the directory to store");
                File fileSaveDir = new File(SAVE_DIR);
                if (!fileSaveDir.exists()) {
                    LOGGER.info("Creates the save directory if it does not exists");
                    fileSaveDir.mkdirs();
                }

                // decode the string and write to file
                byte[] decodedBytes = Base64
                        .getDecoder()
                        .decode(scanFile);

                // create output file
                File outputFile = new File(fileSaveDir
                        //.getParentFile()
                        .getAbsolutePath()
                        + "/" + outputFilePath);

                FileUtils.writeByteArrayToFile(outputFile, decodedBytes);


                return outputFilePath;

            } catch (Exception e) {
                return "You failed to upload file => " + e.getMessage();
            }
        } else {
            return "You failed to upload file because the file is empty.";
        }

    }
    public static String handleFileRemove(String SAVE_DIR, String urlFile) {
        if (!urlFile.isEmpty()) {
            try {

                FileUtils.touch(new File(SAVE_DIR + "/" + urlFile));
                File fileToDelete = FileUtils.getFile(SAVE_DIR + "/" + urlFile);
                boolean success = FileUtils.deleteQuietly(fileToDelete);

                return "success";

            } catch (Exception e) {
                return "You failed to delete file => " + e.getMessage();
            }
        } else {
            return "You failed to delete file because the file is empty.";
        }
    }

    public static boolean positive(int number){
        if(number > 0){
            return true;
        }  else {
            return false;
        }
    }


}
