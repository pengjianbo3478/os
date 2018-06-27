package com.gyf.os.common;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
@Component
public class OrgConfig {
	private static Logger logger = LoggerFactory.getLogger(OrgConfig.class);
	@Value("${os.org.split.max.amt.cofnig.file}")
	private String configFile;

	public Map<String, Long> getOrgConfigMap() {
		Map<String, Long> result = new HashMap<String, Long>();
		File file = new File(this.configFile);
		if (!file.exists()) return null;
		try {
			List<String> data = FileUtils.readLines(file, "UTF-8");
			if (CollectionUtils.isEmpty(data)) return null;
			for (String item : data) {
				String[] entry = item.trim().split("=");
				result.put(entry[0], new Long(entry[1]));
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}
