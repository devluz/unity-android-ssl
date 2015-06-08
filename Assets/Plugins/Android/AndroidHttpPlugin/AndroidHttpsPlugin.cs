using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using UnityEngine;

public class AndroidHttpsPlugin
{

    static AndroidHttpsPlugin()
    {
    }


    public static void IgnoreCertificates()
    {
        AndroidJavaClass clsJavaSSLHelper = new AndroidJavaClass("co.fourscience.androidhttpsplugin.AndroidHttps");
        clsJavaSSLHelper.CallStatic("ignoreCertifcates");
    }

    public static void TrustOnly(String certFileContent)
    {
        AndroidJavaClass clsJavaSSLHelper = new AndroidJavaClass("co.fourscience.androidhttpsplugin.AndroidHttps");
        byte[] certBytes = System.Text.Encoding.ASCII.GetBytes(certFileContent);
        clsJavaSSLHelper.CallStatic("trustOnly", certBytes);
    }

    public static void AddCertificate(String certFileContent)
    {
        AndroidJavaClass clsJavaSSLHelper = new AndroidJavaClass("co.fourscience.androidhttpsplugin.AndroidHttps");
        byte[] certBytes = System.Text.Encoding.ASCII.GetBytes(certFileContent);
        clsJavaSSLHelper.CallStatic("addCertificate", certBytes);
    }
}