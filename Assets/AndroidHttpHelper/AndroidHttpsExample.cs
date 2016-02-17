using UnityEngine;
using System.Collections;

public class AndroidHttpsExample : MonoBehaviour
{
    private bool mInitialized = false;

    private string mResponse = "";

    void Awake()
    {

        //replace this with the content of your crt file
        string cert1 =
@"-----BEGIN CERTIFICATE-----
MIIDjzCCAncCBFalunUwDQYJKoZIhvcNAQELBQAwgYsxCzAJBgNVBAYTAk5aMRIw
EAYDVQQIEwlTb3V0aGxhbmQxDTALBgNVBAcTBEdvcmUxGDAWBgNVBAoTD2JlY2F1
c2Ugd2h5IG5vdDEgMB4GA1UEAxMXbHV6LmJlY2F1c2Utd2h5LW5vdC5jb20xHTAb
BgkqhkiG9w0BCQEWDmRldmx1ekBsaXZlLmRlMB4XDTE2MDEyNTA2MDIyOVoXDTE3
MDEyNDA2MDIyOVowgYsxCzAJBgNVBAYTAk5aMRIwEAYDVQQIEwlTb3V0aGxhbmQx
DTALBgNVBAcTBEdvcmUxGDAWBgNVBAoTD2JlY2F1c2Ugd2h5IG5vdDEgMB4GA1UE
AxMXbHV6LmJlY2F1c2Utd2h5LW5vdC5jb20xHTAbBgkqhkiG9w0BCQEWDmRldmx1
ekBsaXZlLmRlMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6BgkxEBX
G4QZHx+6fm/QhB1nADl94K6SDZp+XJ3HU1BkHJz6hWLungkOF4secJ3Tkmmt4hUq
eS8mafambYBKPve/8HoTaVugaji1WN9tmm6XeMoeAu4012bThVXgQHQiOFNjIA6Z
y46AHR+DbeKNI7z83BMSx8x73YjyRXHGHPZDGycltqiBNga8xtTLRvMJFTV5LXA4
whSOLJcMoqU1TtmjO1jjrzWPoN6Bxe/7vswJibpCjZ6BrQ0XXtxCMjLMfHBzmBxR
eiKmk8onWg/57ipgYqDLCQGBIEwr+1PMZJ0MLHuAGwIsMEV9IzoKOk1N81JH4m6r
IgaTPG2RE76tUQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQA1LnISPPcplCS+n1WE
Ab38WpKi0TRkhBkn1u9cJCLK49I78ShhoA47GtoTDkK89u3TAou8dOXgovQmk65w
NfCsxZfgJo3Rt/v0Gi8YeBTSBHFIak+FTBmKP7hj0hKVuqYgYrqjEIVB8YxPcz4l
wJLM3SRdPPeHFTde905RsyFDEseXXOFwOa4kOC0Z4DPe9dnedTtWd7SR1kf2HsJW
QmZSCmlYHm0fydoM9lbipStep2rqxydcPXYmRxONSY+bnsoL9BDsqi5w6Atph2wa
kfH/nMH44v07JPVpWHjf3qnDgy7u9ygj+AGJ8AvHKFK2Rvs2q6v/rLUpBCHd2GeB
lumP
-----END CERTIFICATE-----";
            


        AndroidHttpsHelper.AddCertificate(cert1);

        mInitialized = true;
    }

	// Use this for initialization
	void Start ()
    {
	    if(mInitialized == false)
        {
            Debug.LogError("Initialization failed. Default WWW class is used.");
        }

        StartCoroutine(Download("https://luz.because-why-not.com/anothertest.txt"));
        StartCoroutine(Download("https://google.com"));
	}

    IEnumerator Download(string url)
    {
        WWW wwwget = new WWW(url);
        yield return wwwget;

        mResponse += "URL: " + url;
        if (!string.IsNullOrEmpty(wwwget.error))
        {
            mResponse += "\n<color=#FF0000>Error: " + wwwget.error + "</color>";
            mResponse += "\nResponse: " + wwwget.text;
        }else
        {
            string response = wwwget.text;
            if (response.Length > 200)
                response = response.Substring(0, 200);
            mResponse += "\n<color=#00FF00>Connection successfull!</color> ";
            mResponse += "\nResponse: " + response;
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
            GUILayout.Label(mResponse);
        }
        GUILayout.EndVertical();
    }
	
	// Update is called once per frame
	void Update () {
	
	}
}
