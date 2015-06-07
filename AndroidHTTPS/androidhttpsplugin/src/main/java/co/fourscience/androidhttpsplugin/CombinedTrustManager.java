package co.fourscience.androidhttpsplugin;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.X509TrustManager;

/**
 * Created by Luz on 7/06/2015.
 */
public class CombinedTrustManager implements X509TrustManager
{
    private ArrayList<X509TrustManager> x509TrustManagers = new ArrayList<X509TrustManager>();

    public void addTrustManager(X509TrustManager tm)
    {
        x509TrustManagers.add(tm);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
        for( X509TrustManager tm : x509TrustManagers )
        {
            try
            {
                tm.checkClientTrusted(chain, authType);
                return;
            }
            catch( CertificateException e )
            {

            }
        }
        throw new CertificateException();
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {

        for( X509TrustManager tm : x509TrustManagers )
        {
            try
            {
                tm.checkServerTrusted(chain,authType);
                return;
            }
            catch( CertificateException e )
            {

            }
        }
        throw new CertificateException();
    }

    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        final ArrayList<X509Certificate> list = new ArrayList<X509Certificate>();
        for( X509TrustManager tm : x509TrustManagers )
        {
            list.addAll(Arrays.asList(tm.getAcceptedIssuers()));
        }
        return list.toArray(new X509Certificate[list.size()]);
    }
}