package com.gyf.os.service.impl;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.gyf.os.common.OrgConfig;
import com.gyf.os.dto.PoSplitReqDTO;
import com.gyf.os.enums.ResultCode;
import com.gyf.os.exception.ServiceException;
import com.gyf.os.service.PoSplitCheckService;
@Service
public class PoSplitCheckServiceImpl implements PoSplitCheckService {
	private static Logger logger = LoggerFactory.getLogger(PoSplitCheckServiceImpl.class);
	@Value("${po.split.default.max.amt}")
	private long defaultMaxAmt;
	@Autowired
	private OrgConfig orgConfig;

	@Override
	public void checkPoAmt(double poAmt) {
		if (poAmt == 0) throw new ServiceException(ResultCode.OS_02.getCode(), ResultCode.OS_02.getDesc());
	}

	@Override
	public void checkOrgCode(String orgCode) {
		boolean t = StringUtils.isEmpty(orgCode);
		if (t) throw new ServiceException(ResultCode.OS_05.getCode(), ResultCode.OS_05.getDesc());
	}

	@Override
	public void check(PoSplitReqDTO req) {
		String orgCode = req.getOrgCode();
		this.checkOrgCode(orgCode);
		double poAmt = req.getPoAmt();
		this.checkPoAmt(poAmt);
		long orgSplitMaxAmt = this.getOrgMaxAmt(orgCode);
		this.validatePoAmt(poAmt, orgSplitMaxAmt);
	}

	private void validatePoAmt(double poAmt, double orgSplitMaxAmt) {
		if (poAmt > orgSplitMaxAmt) throw new ServiceException(ResultCode.OS_03.getCode(), ResultCode.OS_03.getDesc());
	}

	private long getOrgMaxAmt(String orgCode) {
		Map<String, Long> map = this.orgConfig.getOrgConfigMap();
		logger.debug("orgConfig={}", map);
		if (map.isEmpty()) return this.defaultMaxAmt;
		Long amt = map.get(orgCode);
		if (amt == null) return this.defaultMaxAmt;
		return amt;
	}
}
