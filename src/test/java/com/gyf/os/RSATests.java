package com.gyf.os;
import java.util.Map;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyf.os.security.CertInfo;
import com.gyf.os.security.cipher.CipherUtil;
import com.gyf.os.security.sign.SignUtil;
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class RSATests {
	/**
	 * 可以用来做缓存
	 */
	// private static Map<String, CertInfo> certMap = new HashMap<String,
	// CertInfo>();
	private static CertInfo signCert = null; // 平台用于签名的私钥证书
	private static String signCertPasswd = "111111";
	private static CertInfo verifyCert = null; // 平台用于验签的商户公钥证书
	private static Logger log = LoggerFactory.getLogger(SignUtil.class);

	/**
	 * 取得签名证书(支付平台私钥证书--签名)
	 * @return
	 * @throws BizException
	 */
	private synchronized static CertInfo getSignCertInfo() {
		if (signCert == null) {
			signCert = new CertInfo();
			try {
				String signCertPath = signCert.getClass().getClassLoader().getResource("").getPath();
				signCertPath = signCertPath + "test.pfx";
				log.info("签名的私钥证书{}", signCertPath);
				signCert.readKeyFromPKCS12(signCertPath, signCertPasswd);
			}
			catch (Exception e) {
				log.error("读取签名的私钥证书失败", e);
			}
		}
		return signCert;
	}

	/**
	 * 取得对应partnerid的商户验签证书（商户公钥证书--验签）
	 * @return
	 * @throws BizException
	 */
	private synchronized static CertInfo getVerifyCertInfo() {
		if (verifyCert == null) {
			verifyCert = new CertInfo();
			String verifyCertPath = verifyCert.getClass().getClassLoader().getResource("").getPath();
			verifyCertPath = verifyCertPath + "test.cer";
			log.info("商户公钥证书{}", verifyCertPath);
			try {
				verifyCert.readPublicKeyFromX509Certificate(verifyCertPath);
			}
			catch (Exception e) {
				log.error("读取商户公钥证书失败", e);
			}
		}
		return verifyCert;
	}

	/**
	 * 测试rsa 签名验签
	 * @throws Exception
	 * @see
	 */
	@Test
	public void testSignMsg() throws Exception {
		String msg = "1000";
		System.out.println("测试字符串签名,报文内容为:" + msg);
		CertInfo signCert = getSignCertInfo();
		String sign = SignUtil.signMsg(signCert, msg, SignUtil.SHA1WithRSA, SignUtil.CHARSET);
		System.out.println("正确签名内容：" + sign);
		CertInfo verifyCert = getVerifyCertInfo();
		System.out.println("正确验证签名：" + SignUtil.verifyMsg(verifyCert, msg, sign, SignUtil.SHA1WithRSA, SignUtil.CHARSET));
		System.out.println("错误验证签名：" + SignUtil.verifyMsg(verifyCert, msg + "1", sign, SignUtil.SHA1WithRSA, SignUtil.CHARSET));
	}

	/**
	 * 测试rsa 签名验签文件
	 * @throws Exception
	 * @see
	 */
	@Test
	public void testSignFile() throws Exception {
		String msg1 = getClass().getClassLoader().getResource("").getPath() + "test1.txt";
		String msg2 = getClass().getClassLoader().getResource("").getPath() + "test2.txt";
		System.out.println("文件-测试签名,报文内容为:" + msg1);
		CertInfo signCert = getSignCertInfo();
		String sign = SignUtil.signFile(signCert, msg1, SignUtil.SHA1WithRSA, SignUtil.CHARSET);
		System.out.println("文件-正确签名内容：" + sign);
		CertInfo verifyCert = getVerifyCertInfo();
		System.out.println("文件-正确验证签名：" + SignUtil.verifyFile(verifyCert, msg1, sign, SignUtil.SHA1WithRSA, SignUtil.CHARSET));
		System.out.println("文件-错误验证签名：" + SignUtil.verifyFile(verifyCert, msg2, sign, SignUtil.SHA1WithRSA, SignUtil.CHARSET));
	}

	/**
	 * 测试rsa加解密
	 * @throws Exception
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void testCipher() throws Exception {
		String msg = "test中国人民";
		System.out.println("测试公钥加密,报文内容为:" + msg);
		CertInfo publicCert = getVerifyCertInfo();
		String encryptedData = CipherUtil.encryptData(publicCert, msg, CipherUtil.PKCS1Padding, SignUtil.CHARSET);
		System.out.println("加密内容：" + encryptedData);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(encryptedData, Map.class);
		String key = map.get("key");
		String content = map.get("content");
		CertInfo privateCert = getSignCertInfo();
		System.out.println("正常解密：" + CipherUtil.decryptData(privateCert, key, content, CipherUtil.PKCS1Padding, SignUtil.CHARSET));
		System.out.println("公钥解密失败：" + CipherUtil.decryptData(publicCert, key, content, CipherUtil.PKCS1Padding, SignUtil.CHARSET));
	}
}