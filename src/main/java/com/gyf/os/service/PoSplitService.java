package com.gyf.os.service;
import com.gyf.os.dto.PoSplitReqDTO;
import com.gyf.os.dto.PoSplitRespDTO;
import com.gyf.os.exception.ServiceException;
public interface PoSplitService {
	PoSplitRespDTO split(PoSplitReqDTO req) throws ServiceException;
}
