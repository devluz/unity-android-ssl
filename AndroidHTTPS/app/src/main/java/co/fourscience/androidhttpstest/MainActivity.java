package co.fourscience.androidhttpstest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import co.fourscience.androidhttpsplugin.AndroidHttps;


public class MainActivity extends ActionBarActivity {


    EditText mText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (EditText)this.findViewById(R.id.log);
        mText.append("\nstart");

        String cert1 = "-----BEGIN CERTIFICATE-----\n" +
                "MIIGPjCCBSagAwIBAgIHBVR6r4lN4jANBgkqhkiG9w0BAQsFADCBjDELMAkGA1UE\n" +
                "BhMCSUwxFjAUBgNVBAoTDVN0YXJ0Q29tIEx0ZC4xKzApBgNVBAsTIlNlY3VyZSBE\n" +
                "aWdpdGFsIENlcnRpZmljYXRlIFNpZ25pbmcxODA2BgNVBAMTL1N0YXJ0Q29tIENs\n" +
                "YXNzIDEgUHJpbWFyeSBJbnRlcm1lZGlhdGUgU2VydmVyIENBMB4XDTE1MDMwNzIz\n" +
                "MzUzMFoXDTE2MDMwODExNDQxOFowTTELMAkGA1UEBhMCTloxGDAWBgNVBAMTD3d3\n" +
                "dy40c2NpZW5jZS5jbzEkMCIGCSqGSIb3DQEJARYVdGVjNHNjaWVuY2VAZ21haWwu\n" +
                "Y29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu+HsXz0RoezRfiyd\n" +
                "pi7w5d+7XX4Dd4UJULHQex/dLWgXKnars6LzCgSrY1yKqpMVpnQCzcBh9pGKc6UB\n" +
                "ehpQOg5i9Md8vDYIURgG456yvTi60lbfI04nQFz1eVJJBiqUsLOkhXn4qJv5RB9X\n" +
                "HF820WrR3zu6aawr1b1Y2iHyaP0D3r602AiQ4P9yFcUcpZeTc7UMqSAaZmb2p0QO\n" +
                "1cl2F4OAe1wYAeV+W90wr4AJeYd7AxGbH3xpODAxg9Y1IqLv8t7b91olX838zZQH\n" +
                "FNmudKZEdUtY4B54gByqT9r8soxnft2aGvWXlWKAuKwy9VOYjmkQSkWXjI5stHci\n" +
                "+LcskQIDAQABo4IC4TCCAt0wCQYDVR0TBAIwADALBgNVHQ8EBAMCA6gwEwYDVR0l\n" +
                "BAwwCgYIKwYBBQUHAwEwHQYDVR0OBBYEFGtVHea+QqwkrZHPb54nyTJi5LsbMB8G\n" +
                "A1UdIwQYMBaAFOtCNNCYsKuf9BtrCPfMZC7vDixFMCcGA1UdEQQgMB6CD3d3dy40\n" +
                "c2NpZW5jZS5jb4ILNHNjaWVuY2UuY28wggFWBgNVHSAEggFNMIIBSTAIBgZngQwB\n" +
                "AgEwggE7BgsrBgEEAYG1NwECAzCCASowLgYIKwYBBQUHAgEWImh0dHA6Ly93d3cu\n" +
                "c3RhcnRzc2wuY29tL3BvbGljeS5wZGYwgfcGCCsGAQUFBwICMIHqMCcWIFN0YXJ0\n" +
                "Q29tIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MAMCAQEagb5UaGlzIGNlcnRpZmlj\n" +
                "YXRlIHdhcyBpc3N1ZWQgYWNjb3JkaW5nIHRvIHRoZSBDbGFzcyAxIFZhbGlkYXRp\n" +
                "b24gcmVxdWlyZW1lbnRzIG9mIHRoZSBTdGFydENvbSBDQSBwb2xpY3ksIHJlbGlh\n" +
                "bmNlIG9ubHkgZm9yIHRoZSBpbnRlbmRlZCBwdXJwb3NlIGluIGNvbXBsaWFuY2Ug\n" +
                "b2YgdGhlIHJlbHlpbmcgcGFydHkgb2JsaWdhdGlvbnMuMDUGA1UdHwQuMCwwKqAo\n" +
                "oCaGJGh0dHA6Ly9jcmwuc3RhcnRzc2wuY29tL2NydDEtY3JsLmNybDCBjgYIKwYB\n" +
                "BQUHAQEEgYEwfzA5BggrBgEFBQcwAYYtaHR0cDovL29jc3Auc3RhcnRzc2wuY29t\n" +
                "L3N1Yi9jbGFzczEvc2VydmVyL2NhMEIGCCsGAQUFBzAChjZodHRwOi8vYWlhLnN0\n" +
                "YXJ0c3NsLmNvbS9jZXJ0cy9zdWIuY2xhc3MxLnNlcnZlci5jYS5jcnQwIwYDVR0S\n" +
                "BBwwGoYYaHR0cDovL3d3dy5zdGFydHNzbC5jb20vMA0GCSqGSIb3DQEBCwUAA4IB\n" +
                "AQCMAORkldm5kdlKRwpFvolq2gMqJwcsvL+bxB+9fub+erXtZ/gtg03wuyYY+OFD\n" +
                "1npEb/fb8zUKJb89hXTlC+e8KysuZIwHWddh9vB4k182MtAI1ru/C4PPSIVxF2ra\n" +
                "k0LEKZcAc/a9FkkdOMQZYA3BZPuGHUOkPx92V3Yf0/aPgXl0tB2JIBSD3RGuhwBS\n" +
                "UIxtJ9RMozIKdiL4SU9wm2fdNowtHWYwCd86n3LrDYT0hixH0c+1xYcVEZLVx0lT\n" +
                "UCrqLWdlIhFfAygh4U0DjCItkcEtWZ0mSyRjdbzLwGgO76uyocOuiMHB5bv0IsA9\n" +
                "TiQhVKnT/a8KxWgGDyzO6xIg\n" +
                "-----END CERTIFICATE-----";


        String cert2 = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDdTCCAl0CBFV013wwDQYJKoZIhvcNAQEFBQAwfzELMAkGA1UEBhMCTloxEjAQ\n" +
                "BgNVBAgTCVNvdXRobGFuZDENMAsGA1UEBxMER29yZTENMAsGA1UEChMEbm9uZTEY\n" +
                "MBYGA1UEAxMPbHV6LjRzY2llbmNlLmNvMSQwIgYJKoZIhvcNAQkBFhV0ZWM0c2Np\n" +
                "ZW5jZUBnbWFpbC5jb20wHhcNMTUwNjA3MjM0NTAwWhcNMTYwNjA2MjM0NTAwWjB/\n" +
                "MQswCQYDVQQGEwJOWjESMBAGA1UECBMJU291dGhsYW5kMQ0wCwYDVQQHEwRHb3Jl\n" +
                "MQ0wCwYDVQQKEwRub25lMRgwFgYDVQQDEw9sdXouNHNjaWVuY2UuY28xJDAiBgkq\n" +
                "hkiG9w0BCQEWFXRlYzRzY2llbmNlQGdtYWlsLmNvbTCCASIwDQYJKoZIhvcNAQEB\n" +
                "BQADggEPADCCAQoCggEBALz3DJ/HB3T9/6Vs8JcQQlnZvqUVgy//RE5iySMrDVd7\n" +
                "i7oYsTG3zZ0ATNXAd8hDlL6wimp/8DAWm3S0Dk5pBB71knCkNqaKtIHAHinSmTMd\n" +
                "caYizMfVUIdbe43o/GM58y4dXtCNLMUvIGN5DFDiY/g5uRdmEUcxkhYGLv7WCNqn\n" +
                "zEdLHUrgmjLdud6Ldooomb7R+R1FVEapp9szcYQONNx960clXZKdZcKb6YcTncQN\n" +
                "V3wnpLAo/FfOQduv+iNVFhhE7nF5Eue8AaT53uRtUMBV7vUnVblN7yfWukOKUaDZ\n" +
                "xKoZhloUQpiN8pg3QkaxzInEt9yheUeq7mRLsHDNVycCAwEAATANBgkqhkiG9w0B\n" +
                "AQUFAAOCAQEAQG8FlKJZsC255kRLL54BAjd2fOqE8GAR7eGVt9mr+HdmU4m/gMrS\n" +
                "PTB2ZwMquqrvw+v3/bO1Lj1HyQiYIgfPo5hlLXaZWc1Ao2SlnooK3rO1FI6++/yi\n" +
                "OARjBg2VvSGjYQH93h2cm+ZGqsYL14wJo86HxxkRjGZQU/FhaLKWsabwh/9XPcgl\n" +
                "6z47K9VR76c3MalFEc08ErILZXKwPUNpUdU1IzfpaySMq3RZvPnsZWVU4kWYaCLp\n" +
                "UvxYEu37FRdy6YkTDOXd/PWcbOhdohcTmhJTrzQt6lhb1lJd+mhv5PHoYVPqzpc3\n" +
                "7aXonx43QzkKUgDq+JBtcJlfzaX5NvOLkQ==\n" +
                "-----END CERTIFICATE-----";
        //AndroidHttps.trust(new byte[][]{cert1.getBytes(), cert2.getBytes()});
        //AndroidHttps.trustEveryone();
        AndroidHttps.addCertificate(cert1.getBytes());
        AndroidHttps.addCertificate(cert2.getBytes());

        TestUrl("https://google.com");
        TestUrl("https://4science.co/justatest.txt");
        TestUrl("https://luz.4science.co/anothertest.txt");

    }

    private void TestUrl(final String addr)
    {


        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                final StringBuilder result = new StringBuilder();
                result.append("\n\nURL:");
                result.append(addr);
                try {
                    URL url = new URL(addr);
                    URLConnection con = url.openConnection();
                    InputStream inStream = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                    result.append("\nResponse:");
                    int length = 0;
                    StringBuilder sb = new StringBuilder();
                    String s = reader.readLine();
                    while(s != null)
                    {
                        sb.append("\n" + s);
                        s = reader.readLine();
                    }
                    String output;
                    if(sb.length() < 200)
                        output = sb.toString();
                    else
                        output = sb.substring(0, 200);

                    result.append("\n" + output);


                }catch(MalformedURLException e)
                {
                    result.append("\nError:" + e.toString());
                    e.printStackTrace();
                }catch(IOException e)
                {
                    result.append("\nError:" + e.toString());
                    e.printStackTrace();
                }finally
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mText.append(result);
                        }
                    });
                }


            }
        };
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

