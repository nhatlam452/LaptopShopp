package com.example.myapplication.Utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.Activities.SplashScreenActivity;
import com.example.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppHelper {
    public static class AppCheck {
        private static AppCheck instance;
        private static SharedPreferences sharedPreferences;
        private static  SharedPreferences.Editor editor;
        private static StorageManger localStorageManager;
        private Context context;

        private AppCheck(Context context){
            this.context = context;
            this.sharedPreferences = context.getSharedPreferences(AppConstant.APP_NAME,Context.MODE_PRIVATE);
            this.editor = sharedPreferences.edit();
        }
        public static AppCheck getInstance(Context context){
            if (instance == null){
                instance = new AppCheck(context);
            }
            return instance;
        }
        public StorageManger getLocalStorageManager(){
            return localStorageManager;
        }

        public static class StorageManger{
            public static  void  setUserPhoneNumber(String string){
                editor.putString(AppConstant.USER_PHONE_NUMBER,string).apply();
            }
            public static String getUserPhoneNumber(){
                return  sharedPreferences.getString(AppConstant.USER_PHONE_NUMBER,"");
            }

            public static boolean isUserLogin() {
                return sharedPreferences.getBoolean(AppConstant.IS_USER_LOGIN, false);
            }

            //Đặt trạng trái đăng nhập true nếu đã đăng nhập false nếu chưa đăng nhập
            public static void setUserLogin(Boolean state) {
                editor.putBoolean(AppConstant.IS_USER_LOGIN, state).apply();
            }

            //Kiểm tra có phải lần đầu cài đặt
            public static boolean isFirstInstall() {
                return sharedPreferences.getBoolean(AppConstant.IS_FIRST_INSTALL, false);
            }

            //Đặt trạng trái đăng nhập true đã cài đặt lần đầu false nếu mới cài đăt
            public static void setFirstInstall(Boolean state) {
                editor.putBoolean(AppConstant.IS_USER_LOGIN, state).apply();
            }

        }

    }
    public static class ValidateInput{
        public static boolean isEmpty(String string){
            return TextUtils.isEmpty(string);
        }
        public static boolean isValidPhoneNumber(String string){
            final  String PHONE_REGEX = "0"+"\\d{9}";
            Pattern pattern = Pattern.compile(PHONE_REGEX);
            Matcher matcher = pattern.matcher(string);
            return matcher.matches();
        }
//        public static boolean isValidPassword(String string){
//        }

    }


}
