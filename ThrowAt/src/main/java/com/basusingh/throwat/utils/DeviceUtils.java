package com.basusingh.throwat.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.Window;

import org.json.JSONObject;

public class DeviceUtils {

    public static String getDeviceInfoAsJSON(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("Brand", Build.BRAND);
            obj.put("BootLoader", Build.BOOTLOADER);
            obj.put("Board", Build.BOARD);
            obj.put("Device", Build.DEVICE);
            obj.put("Display", Build.DISPLAY);
            obj.put("Fingerprint", Build.FINGERPRINT);
            obj.put("Hardware", Build.HARDWARE);
            obj.put("ID", Build.ID);
            obj.put("Manufacturer", Build.MANUFACTURER);
            obj.put("Model", Build.MODEL);
            obj.put("Product", Build.PRODUCT);
            obj.put("RadioVersion", Build.getRadioVersion());
            obj.put("SDK_INT", String.valueOf(Build.VERSION.SDK_INT));
            obj.put("Release", String.valueOf(Build.VERSION.RELEASE));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                obj.put("SDK_INT", String.valueOf(Build.VERSION.SECURITY_PATCH));
                obj.put("SDK_INT", String.valueOf(Build.VERSION.BASE_OS));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return obj.toString();
    }
}
