package com.techakram.facebook_integration;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyKeyHash extends Application
{
    @Override
    public void onCreate() {
        super.onCreate( );
        printHashKey();

    }
    public void printHashKey(){
        try {
            PackageInfo info=getPackageManager()
                    .getPackageInfo("com.techakram.facebook_integration",PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures)
            {
                MessageDigest digest=MessageDigest.getInstance("SHA");
                digest.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(digest.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace( );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace( );
        }

    }


}
