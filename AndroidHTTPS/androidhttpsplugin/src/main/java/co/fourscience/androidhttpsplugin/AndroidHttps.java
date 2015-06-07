package co.fourscience.androidhttpsplugin;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Luz on 7/06/2015.
 */
public class AndroidHttps
{
    //trust everyone. This makes ssl almost useless. Only use for testing/debugging!
    public static void trustEveryone()
    {
        //Create a trust manager that trusts everyone
        X509TrustManager trustEveryone = new TrustEveryoneTrustManager();
        try
        {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{trustEveryone}, new java.security.SecureRandom());
            //this is important: unity will use the default ssl socket factory we just created
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        }catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }catch(KeyManagementException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void trust_only(byte[] crtFileContent)
    {
        try
        {
            // Load CAs from an InputStream
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream caInput = new BufferedInputStream(new ByteArrayInputStream(crtFileContent));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.d("AndroidHttps", "ca=" + ((X509Certificate) ca).getSubjectDN());
                Log.d("AndroidHttps", "Certificate successfully created");
            } finally
            {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            try
            {
                // Create an SSLContext that uses our TrustManager
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

                //this is important: unity will use the default ssl socket factory we just created
                HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
                Log.d("AndroidHttps", "Default SSL Socket set.");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }



    public static void trust(byte[] crtFileContent)
    {
        try
        {
            // Load CAs from an InputStream
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream caInput = new BufferedInputStream(new ByteArrayInputStream(crtFileContent));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.d("JavaSSLHelper", "ca=" + ((X509Certificate) ca).getSubjectDN());
                Log.d("JavaSSLHelper", "Certificate successfully created");
            } finally
            {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore

            TrustManagerFactory defaultTMF = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            defaultTMF.init((KeyStore)null);

            TrustManagerFactory additionalTMF = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            additionalTMF.init(keyStore);

            TrustManager[] defaultTMs = defaultTMF.getTrustManagers();
            TrustManager[] additionalTMs = additionalTMF.getTrustManagers();
            CombinedTrustManager combinedTrustManager = new CombinedTrustManager();
            for(TrustManager tm : defaultTMs)
            {
                if(tm instanceof X509TrustManager)
                    combinedTrustManager.addTrustManager((X509TrustManager)tm);
            }
            for(TrustManager tm : additionalTMs)
            {
                if(tm instanceof X509TrustManager)
                    combinedTrustManager.addTrustManager((X509TrustManager)tm);
            }

            try
            {
                // Create an SSLContext that uses our TrustManager
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, new TrustManager[]{combinedTrustManager}, null);

                //this is important: unity will use the default ssl socket factory we just created
                HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
                Log.d("AndroidHttp", "Default SSL Socket set.");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
