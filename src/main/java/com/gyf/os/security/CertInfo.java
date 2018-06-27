package com.gyf.os.security;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Random;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import es.sing.util.CertificateUtils;
import es.sing.util.GeneraClaves;
import es.sing.util.X509Subject;
public class CertInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private PrivateKey priKey;
	private PublicKey pubKey;
	private X509Certificate cer;
	private String issue;

	public CertInfo() {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * 创建证书
	 * 
	 * @param caCertInfo
	 * @param algorithm
	 * @param signAlgorithm
	 * @param limitday
	 * @param userIssue
	 * @throws Exception
	 * @see
	 */
	public void creatPKCS12(CertInfo caCertInfo, String algorithm, String signAlgorithm, int limitday, String userIssue) throws Exception {
		KeyPair keyPair = GeneraClaves.generaParClaves(1024, algorithm);
		this.priKey = keyPair.getPrivate();
		this.pubKey = keyPair.getPublic();
		this.issue = userIssue;
		userIssue = X509Subject.decodeX509Subject(this.issue);
		Random usercertserial = new Random();
		int nserie = usercertserial.nextInt();
		this.cer = CertificateUtils.crearCert(this.pubKey, caCertInfo.getPriKey(), caCertInfo.getPubKey(), caCertInfo.getIssue(), userIssue, nserie, limitday, signAlgorithm);
	}

	/**
	 * 读私钥
	 * 
	 * @param pfxFileName
	 * @param passowrd
	 * @throws Exception
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	public void readKeyFromPKCS12(String pfxFileName, String passowrd) throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(pfxFileName);
			KeyStore keystoreCA = KeyStore.getInstance("PKCS12", "BC");
			keystoreCA.load(fis, passowrd.toCharArray());
			Enumeration aliases = keystoreCA.aliases();
			String keyAlias = null;
			do {
				if ((aliases == null) || (!(aliases.hasMoreElements()))) break;
				keyAlias = (String) aliases.nextElement();
				this.priKey = ((PrivateKey) (PrivateKey) keystoreCA.getKey(keyAlias, passowrd.toCharArray()));
				this.cer = ((X509Certificate) (X509Certificate) keystoreCA.getCertificate(keyAlias));
			}
			while (this.priKey == null);
			this.pubKey = this.cer.getPublicKey();
			this.issue = this.cer.getSubjectDN().toString();
			this.issue = X509Subject.decodeX509Subject(this.issue);
		}
		catch (Exception e) {
			if (fis != null) fis.close();
			throw e;
		}
		if (fis != null) fis.close();
	}

	/**
	 * 读公钥
	 * 
	 * @param certFileName
	 * @throws Exception
	 * @see
	 */
	public void readPublicKeyFromX509Certificate(String certFileName) throws Exception {
		FileInputStream inputStream = new FileInputStream(certFileName);
		readPublicKeyFromX509Certificate(inputStream);
		inputStream.close();
	}

	/**
	 * 读公钥
	 * 
	 * @param certFileName
	 * @throws Exception
	 * @see
	 */
	public void readPublicKeyFromX509Certificate(InputStream inputStream) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		java.security.cert.Certificate cac = cf.generateCertificate(inputStream);
		inputStream.close();
		cer = (X509Certificate) cac;
		pubKey = cac.getPublicKey();
	}

	public static void savePrivateKeyCert_PKCS12(Certificate[] cchain, PrivateKey userPrikey, String certFileName, String privatepass, String alias) throws Exception {
		KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
		ks.load(null, null);
		ks.setKeyEntry(alias, userPrikey, null, cchain);
		savePrivateKey(ks, certFileName, privatepass);
	}

	public static void savePrivateKey(KeyStore ks, String certFileName, String privatepass) throws Exception {
		FileOutputStream out4 = new FileOutputStream(certFileName);
		ks.store(out4, privatepass.toCharArray());
		out4.close();
	}

	public static void savePublicKeyCert(Certificate caCer, String certFileName) throws Exception {
		FileOutputStream caCerOut = new FileOutputStream(certFileName);
		caCerOut.write(caCer.getEncoded());
		caCerOut.close();
	}

	public PrivateKey getPriKey() {
		return this.priKey;
	}

	public PublicKey getPubKey() {
		return this.pubKey;
	}

	public String getIssue() {
		return this.issue;
	}

	public X509Certificate getCer() {
		return this.cer;
	}
}