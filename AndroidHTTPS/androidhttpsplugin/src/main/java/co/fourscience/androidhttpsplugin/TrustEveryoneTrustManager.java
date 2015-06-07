package co.fourscience.androidhttpsplugin;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Created by Luz on 7/06/2015.
 */
public class TrustEveryoneTrustManager implements X509TrustManager
{
    public java.security.cert.X509Certificate[] getAcceptedIssuers()
    {
        return new java.security.cert.X509Certificate[] {};
    }

    public void checkClientTrusted(X509Certificate[] chain,
                                   String authType) throws CertificateException
    {
    }

    public void checkServerTrusted(X509Certificate[] chain,
                                   String authType) throws CertificateException
    {
    }
}
