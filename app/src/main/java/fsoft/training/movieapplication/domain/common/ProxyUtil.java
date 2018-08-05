package fsoft.training.movieapplication.domain.common;


import java.net.Authenticator;
import java.net.PasswordAuthentication;

import fsoft.training.movieapplication.constant.Constants;

/**
 * Created by TanNV13 on 10/12/2016
 */
public class ProxyUtil {

    public static void setProxy() {
        // HTTP/HTTPS Proxy
        System.setProperty("http.proxyHost", "hl-proxyb");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "hl-proxyb");
        System.setProperty("https.proxyPort", "8080");
        Authenticator.setDefault(new ProxyAuth(Constants.ACC, Constants.PASS));

        // SOCKS Proxy
        System.setProperty("socksProxyHost", "hl-proxyb");
        System.setProperty("socksProxyPort", "8080");

        System.setProperty("java.net.socks.username", Constants.ACC);
        System.setProperty("java.net.socks.password", Constants.PASS);
        Authenticator.setDefault(new ProxyAuth(Constants.ACC, Constants.PASS));
    }

    static class ProxyAuth extends Authenticator {
        private PasswordAuthentication mAuth;

        ProxyAuth(String user, String password) {
            mAuth = new PasswordAuthentication(user, password == null ? new char[]{} : password.toCharArray());
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return mAuth;
        }
    }

}
