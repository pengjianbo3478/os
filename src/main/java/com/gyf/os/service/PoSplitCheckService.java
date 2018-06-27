package com.gyf.os.service;

import com.gyf.os.dto.PoSplitReqDTO;

public interface PoSplitCheckService {
	
	void checkPoAmt(double poAmt);
	
	void checkOrgCode(String orgCode);
	
	void check(PoSplitReqDTO req);
}
