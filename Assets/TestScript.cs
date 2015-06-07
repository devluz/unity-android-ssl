using UnityEngine;
using System.Collections;

public class TestScript : MonoBehaviour
{
    private bool mInitialized = false;

    private string mResponse = "";

    void Awake()
    {
        //in editor mode the UNITY_ANDROID can be set but it would still be fail. so check if we actually run on android
        //not just for the android compile flag
        if (Application.platform == RuntimePlatform.Android)
        {
#if UNITY_ANDROID
            string cert = 
@"-----BEGIN CERTIFICATE-----
MIIGPjCCBSagAwIBAgIHBVR6r4lN4jANBgkqhkiG9w0BAQsFADCBjDELMAkGA1UE
BhMCSUwxFjAUBgNVBAoTDVN0YXJ0Q29tIEx0ZC4xKzApBgNVBAsTIlNlY3VyZSBE
aWdpdGFsIENlcnRpZmljYXRlIFNpZ25pbmcxODA2BgNVBAMTL1N0YXJ0Q29tIENs
YXNzIDEgUHJpbWFyeSBJbnRlcm1lZGlhdGUgU2VydmVyIENBMB4XDTE1MDMwNzIz
MzUzMFoXDTE2MDMwODExNDQxOFowTTELMAkGA1UEBhMCTloxGDAWBgNVBAMTD3d3
dy40c2NpZW5jZS5jbzEkMCIGCSqGSIb3DQEJARYVdGVjNHNjaWVuY2VAZ21haWwu
Y29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu+HsXz0RoezRfiyd
pi7w5d+7XX4Dd4UJULHQex/dLWgXKnars6LzCgSrY1yKqpMVpnQCzcBh9pGKc6UB
ehpQOg5i9Md8vDYIURgG456yvTi60lbfI04nQFz1eVJJBiqUsLOkhXn4qJv5RB9X
HF820WrR3zu6aawr1b1Y2iHyaP0D3r602AiQ4P9yFcUcpZeTc7UMqSAaZmb2p0QO
1cl2F4OAe1wYAeV+W90wr4AJeYd7AxGbH3xpODAxg9Y1IqLv8t7b91olX838zZQH
FNmudKZEdUtY4B54gByqT9r8soxnft2aGvWXlWKAuKwy9VOYjmkQSkWXjI5stHci
+LcskQIDAQABo4IC4TCCAt0wCQYDVR0TBAIwADALBgNVHQ8EBAMCA6gwEwYDVR0l
BAwwCgYIKwYBBQUHAwEwHQYDVR0OBBYEFGtVHea+QqwkrZHPb54nyTJi5LsbMB8G
A1UdIwQYMBaAFOtCNNCYsKuf9BtrCPfMZC7vDixFMCcGA1UdEQQgMB6CD3d3dy40
c2NpZW5jZS5jb4ILNHNjaWVuY2UuY28wggFWBgNVHSAEggFNMIIBSTAIBgZngQwB
AgEwggE7BgsrBgEEAYG1NwECAzCCASowLgYIKwYBBQUHAgEWImh0dHA6Ly93d3cu
c3RhcnRzc2wuY29tL3BvbGljeS5wZGYwgfcGCCsGAQUFBwICMIHqMCcWIFN0YXJ0
Q29tIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MAMCAQEagb5UaGlzIGNlcnRpZmlj
YXRlIHdhcyBpc3N1ZWQgYWNjb3JkaW5nIHRvIHRoZSBDbGFzcyAxIFZhbGlkYXRp
b24gcmVxdWlyZW1lbnRzIG9mIHRoZSBTdGFydENvbSBDQSBwb2xpY3ksIHJlbGlh
bmNlIG9ubHkgZm9yIHRoZSBpbnRlbmRlZCBwdXJwb3NlIGluIGNvbXBsaWFuY2Ug
b2YgdGhlIHJlbHlpbmcgcGFydHkgb2JsaWdhdGlvbnMuMDUGA1UdHwQuMCwwKqAo
oCaGJGh0dHA6Ly9jcmwuc3RhcnRzc2wuY29tL2NydDEtY3JsLmNybDCBjgYIKwYB
BQUHAQEEgYEwfzA5BggrBgEFBQcwAYYtaHR0cDovL29jc3Auc3RhcnRzc2wuY29t
L3N1Yi9jbGFzczEvc2VydmVyL2NhMEIGCCsGAQUFBzAChjZodHRwOi8vYWlhLnN0
YXJ0c3NsLmNvbS9jZXJ0cy9zdWIuY2xhc3MxLnNlcnZlci5jYS5jcnQwIwYDVR0S
BBwwGoYYaHR0cDovL3d3dy5zdGFydHNzbC5jb20vMA0GCSqGSIb3DQEBCwUAA4IB
AQCMAORkldm5kdlKRwpFvolq2gMqJwcsvL+bxB+9fub+erXtZ/gtg03wuyYY+OFD
1npEb/fb8zUKJb89hXTlC+e8KysuZIwHWddh9vB4k182MtAI1ru/C4PPSIVxF2ra
k0LEKZcAc/a9FkkdOMQZYA3BZPuGHUOkPx92V3Yf0/aPgXl0tB2JIBSD3RGuhwBS
UIxtJ9RMozIKdiL4SU9wm2fdNowtHWYwCd86n3LrDYT0hixH0c+1xYcVEZLVx0lT
UCrqLWdlIhFfAygh4U0DjCItkcEtWZ0mSyRjdbzLwGgO76uyocOuiMHB5bv0IsA9
TiQhVKnT/a8KxWgGDyzO6xIg
-----END CERTIFICATE-----";


        AndroidJavaClass clsJavaSSLHelper = new AndroidJavaClass("co.fourscience.ulib.JavaSSLHelper");
        byte[] certBytes = System.Text.Encoding.ASCII.GetBytes(cert);
        clsJavaSSLHelper.CallStatic("trust", certBytes);
        mInitialized = true;
#else

            mInitialized = false;
#endif
        }

    }

	// Use this for initialization
	void Start ()
    {
	    if(mInitialized == false)
        {
            Debug.LogError("Initialization failed. Default WWW class is used.");
        }
        StartCoroutine(Download("https://4science.co/justatest.txt"));
        StartCoroutine(Download("https://google.com"));
	}

    IEnumerator Download(string url)
    {
        WWW wwwget = new WWW(url);
        yield return wwwget;

        mResponse += "URL: " + url + "/n";
        if (!string.IsNullOrEmpty(wwwget.error))
        {
            mResponse += "<color=#FF0000>Error: " + wwwget.error + "</color>\nResponse: " + wwwget.text;
        }else
        {
            string response = wwwget.text;
            if (response.Length > 200)
                response = response.Substring(0, 200);
            mResponse += "testSuccessful\nResponse: " + response;
        }

        mResponse += "\n\n";
    }

    private void OnGUI()
    {
        GUILayout.BeginVertical();
        if (mInitialized)
        {
            GUILayout.Label("Plugin initialized.");
        }else
        {
            GUILayout.Label("Failed to initialize the plugin.");
        }

        if (mResponse == null)
        {
            GUILayout.Label("No server response.");
        }
        else
        {
            GUILayout.Label("Server response: " + mResponse);
        }
        GUILayout.EndVertical();
    }
	
	// Update is called once per frame
	void Update () {
	
	}
}
