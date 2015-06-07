package co.fourscience.ulib;
import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayerActivity;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Luz on 1/03/2015.
 */
public class CustomUnityActivity extends UnityPlayerActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {

        // call UnityPlayerActivity.onCreate()
        super.onCreate(savedInstanceState);

        // print debug message to logcat
        Log.d("OverrideActivity", "onCreate called!");

        //JavaSSLHelper.trustAllHosts();
    }
}