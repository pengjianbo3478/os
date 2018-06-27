package com.gyf.os.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import com.gyf.os.dto.PoSplitReqDTO;
import com.gyf.os.enums.ResultCode;
import com.gyf.os.exception.ServiceException;
import com.gyf.os.security.CertInfo;
import com.gyf.os.security.sign.SignUtil;
public abstract class OsSecurity {
	@Value("${os.verify.cert.path}")
	private String verifyCertPath;
	private static Logger logger = LoggerFactory.getLogger(OsSecurity.class);

	private CertInfo getVerifyCertInfo(String orgCode) throws Exception {
		CertInfo verifyCert = new CertInfo();
		String orgCerFullPath = this.verifyCertPath + orgCode + ".cer";
		verifyCert.readPublicKeyFromX509Certificate(orgCerFullPath);
		return verifyCert;
	}

	public boolean verifyMsg(String sign, String orgCode) throws Exception {
		CertInfo certInfo = this.getVerifyCertInfo(orgCode);
		boolean result = SignUtil.verifyMsg(certInfo, orgCode, sign, SignUtil.SHA1WithRSA, SignUtil.CHARSET);
		return result;
	}

	public void verifySign(PoSplitReqDTO req) throws ServiceException {
		try {
			String orgCode = req.getOrgCode();
			String sign = req.getSign();
			boolean result = this.verifyMsg(sign, orgCode);
			if (!result) throw new ServiceException(ResultCode.OS_04.getCode(), ResultCode.OS_04.getDesc());
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ResultCode.OS_04.getCode(), ResultCode.OS_04.getDesc());
		}
	}
}
