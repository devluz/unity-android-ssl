package co.fourscience.androidhttpsplugin;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
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
    private static KeyStore sKeyStore = null;


    /**
     * Turns off security and accepts all certificates.
     * Only for debugging!
     */
    public static void ignoreCertifcates()
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

    /**
     * Turns off the default security checks and only trusts the single given certificate.
     * @param crtFileContent
     */
    public static void trustOnly(byte[] crtFileContent)
    {
        try
        {
            Certificate ca = readCertificate(crtFileContent);

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


    private static Certificate readCertificate(byte[] cert)
            throws CertificateException
    {
        Certificate ca = null;
        InputStream input = new BufferedInputStream(new ByteArrayInputStream(cert));
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        ca = cf.generateCertificate(input);

        try
        {
            input.close();
        }catch(IOException e)
        {

        }
        return ca;
    }

    /**
     * Allows the given certificate + all default android certificates.
     *
     * @param crtFileContent
     */
    public static void addCertificate(byte[] crtFileContent)
    {
        addCertificates(new byte[][]{crtFileContent});
    }

    public static void addCertificates(byte[][] certificates)
    {
        try
        {
            if(sKeyStore == null)
            {
                // Create a KeyStore containing our trusted CAs
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                sKeyStore = keyStore;
            }
            for(int i = 0; i < certificates.length; i++)
            {
                Certificate ca = readCertificate(certificates[i]);
                sKeyStore.setCertificateEntry("ca" + sKeyStore.size(), ca);
            }

            // Create a TrustManager that trusts the CAs in our KeyStore

            TrustManagerFactory defaultTMF = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            defaultTMF.init((KeyStore)null);

            TrustManagerFactory additionalTMF = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            additionalTMF.init(sKeyStore);

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

            // create a new SSLContext that uses the combined TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{combinedTrustManager}, null);

            //this is important: unity will use the default ssl socket factory we just created
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            Log.d("AndroidHttp", "Default SSL Socket set.");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch (KeyManagementException e)
        {
            throw new RuntimeException(e);
        }
        catch (KeyStoreException e)
        {
            throw new RuntimeException(e);
        }
        catch (CertificateException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
