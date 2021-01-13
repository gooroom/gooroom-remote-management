package kr.gooroom.gpms.common.utils;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : MessageSourceHelper.java
 * @Description : MessageSourceHelper Class
 * @Modification Information
 * @
 * @ 수정일       수정자     수정내용
 * @ ---------- --------- -------------------------------
 * @ 2017.05.08 LDH     최초생성
 *
 * @since 2017.05.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by Hancom All right reserved.
 */
public class PropertyConfigurerHelper {

	private static Properties configCloudrimDrive;

	public PropertyConfigurerHelper(Properties configCloudrimDrive) {
		PropertyConfigurerHelper.configCloudrimDrive = configCloudrimDrive;
	}


}
