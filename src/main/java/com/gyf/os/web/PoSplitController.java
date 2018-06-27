package com.gyf.os.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gyf.os.dto.PoSplitReqDTO;
import com.gyf.os.dto.PoSplitRespDTO;
import com.gyf.os.enums.ResultCode;
import com.gyf.os.exception.ServiceException;
import com.gyf.os.service.PoSplitService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
@RequestMapping(value = "os")
public class PoSplitController {
	@Autowired
	private PoSplitService poSplitService;
	private static Logger logger = LoggerFactory.getLogger(PoSplitController.class);

	@HystrixCommand(commandKey = "oss", fallbackMethod = "fallback")
	@PostMapping(value = "/oss")
	public PoSplitRespDTO split(@RequestBody PoSplitReqDTO reqest) {
		try {
			logger.debug("reqest={}", reqest);
			PoSplitRespDTO result = this.poSplitService.split(reqest);
			logger.debug("response={}", result);
			return result;
		}
		catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			return new PoSplitRespDTO(e.getCode(), e.getDesc());
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new PoSplitRespDTO(ResultCode.FAIL.getCode(), ResultCode.FAIL.getDesc());
		}
	}

	public PoSplitRespDTO fallback(@RequestBody PoSplitReqDTO request) {
		logger.error("request is fallback");
		return new PoSplitRespDTO(ResultCode.FAIL.getCode(), ResultCode.FAIL.getDesc());
	}
}
