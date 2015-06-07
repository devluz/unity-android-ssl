package co.fourscience.ulib;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Luz on 1/03/2015.
 */
public class JavaSSLHelper
{


    //trust everyone. This makes ssl almost useless. Only use for testing/debugging!
    public static void trustEveryone()
    {
        //Create a trust manager that trusts everyone
        X509TrustManager trustEveryone = new X509TrustManager()
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
        };


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




    //trust a certain certificate
    //see https://developer.android.com/training/articles/security-ssl.html
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
                Log.d("JavaSSLHelper", "Default SSL Socket set.");
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

            TrustManagerFactory aditionalTMF = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            aditionalTMF.init(keyStore);

            TrustManager[] defaultTMs = defaultTMF.getTrustManagers();
            TrustManager[] additionalTMs = aditionalTMF.getTrustManagers();
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
                Log.d("JavaSSLHelper", "Default SSL Socket set.");
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
    /**
     * Allows you to trust certificates from additional KeyStores in addition to
     * the default KeyStore
     */
    /*
    public class AdditionalKeyStoresSSLSocketFactory extends SSLSocketFactory
    {
        protected SSLContext sslContext = SSLContext.getInstance("TLS");

        public AdditionalKeyStoresSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(null, null, null, null, null, null);
            sslContext.init(null, new TrustManager[]{new AdditionalKeyStoresTrustManager(keyStore)}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }
    */


    public static class CombinedTrustManager implements  X509TrustManager
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
            for( X509TrustManager tm : x509TrustManagers ) {
                list.addAll(Arrays.asList(tm.getAcceptedIssuers()));
            }
            return list.toArray(new X509Certificate[list.size()]);
        }
    }
    /**
     * Based on http://download.oracle.com/javase/1.5.0/docs/guide/security/jsse/JSSERefGuide.html#X509TrustManager
     */
    public static class AdditionalKeyStoresTrustManager implements X509TrustManager
    {

        protected ArrayList<X509TrustManager> x509TrustManagers = new ArrayList<X509TrustManager>();


        protected AdditionalKeyStoresTrustManager(KeyStore... additionalkeyStores) {
            final ArrayList<TrustManagerFactory> factories = new ArrayList<TrustManagerFactory>();

            try {
                // The default Trustmanager with default keystore
                final TrustManagerFactory original = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                original.init((KeyStore) null);
                factories.add(original);

                for( KeyStore keyStore : additionalkeyStores ) {
                    final TrustManagerFactory additionalCerts = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    additionalCerts.init(keyStore);
                    factories.add(additionalCerts);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }



            /*
             * Iterate over the returned trustmanagers, and hold on
             * to any that are X509TrustManagers
             */
            for (TrustManagerFactory tmf : factories)
                for( TrustManager tm : tmf.getTrustManagers() )
                    if (tm instanceof X509TrustManager)
                        x509TrustManagers.add( (X509TrustManager)tm );


            if( x509TrustManagers.size()==0 )
                throw new RuntimeException("Couldn't find any X509TrustManagers");

        }

        /*
         * Delegate to the default trust manager.
         */
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            final X509TrustManager defaultX509TrustManager = x509TrustManagers.get(0);
            defaultX509TrustManager.checkClientTrusted(chain, authType);
        }

        /*
         * Loop over the trustmanagers until we find one that accepts our server
         */
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            for( X509TrustManager tm : x509TrustManagers ) {
                try {
                    tm.checkServerTrusted(chain,authType);
                    return;
                } catch( CertificateException e ) {
                    // ignore
                }
            }
            throw new CertificateException();
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            final ArrayList<X509Certificate> list = new ArrayList<X509Certificate>();
            for( X509TrustManager tm : x509TrustManagers ) {
                list.addAll(Arrays.asList(tm.getAcceptedIssuers()));
            }
            return list.toArray(new X509Certificate[list.size()]);
        }
    }
}
