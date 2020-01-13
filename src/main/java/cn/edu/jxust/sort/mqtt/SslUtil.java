package cn.edu.jxust.sort.mqtt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author yuanweimin
 * @date 19/08/10 14:54
 * @description ssl util class
 */
@SuppressWarnings("WeakerAccess")
public class SslUtil {
    public static SSLSocketFactory getSocketFactory(final InputStream stream) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, KeyManagementException {
        Security.addProvider(new BouncyCastleProvider());
        TrustManagerFactory managerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        if (stream != null) {
            // 加载本地指定的 ca 证书
            PEMReader reader = new PEMReader(new InputStreamReader(stream));
            X509Certificate certificate = (X509Certificate) reader.readObject();
            reader.close();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca-certificate", certificate);
            // 把ca作为信任的 ca 列表,来验证服务器证书
            managerFactory.init(keyStore);
        } else {
            //使用系统默认的安全证书
            managerFactory.init((KeyStore) null);
        }
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, managerFactory.getTrustManagers(), null);
        return context.getSocketFactory();
    }
}
