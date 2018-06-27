package com.gyf.os.service.impl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gyf.os.common.PoSplit;
import com.gyf.os.dto.PoSplitReqDTO;
import com.gyf.os.dto.PoSplitRespDTO;
import com.gyf.os.enums.ResultCode;
import com.gyf.os.exception.ServiceException;
import com.gyf.os.service.OsSecurity;
import com.gyf.os.service.PoSplitCheckService;
import com.gyf.os.service.PoSplitService;
@Service
public class PoSplitServiceImpl extends OsSecurity implements PoSplitService {
	private static Logger logger = LoggerFactory.getLogger(PoSplitServiceImpl.class);
	@Autowired
	private PoSplitCheckService poSplitCheckService;

	@Override
	public PoSplitRespDTO split(PoSplitReqDTO req) throws ServiceException {
		this.verifySign(req);
		this.poSplitCheckService.check(req);
		PoSplit ps = new PoSplit(req.getPoAmt());
		List<Double> body = ps.split();
		PoSplitRespDTO result = new PoSplitRespDTO(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
		result.setBody(body);
		logger.debug("result={}", result);
		return result;
	}
}
