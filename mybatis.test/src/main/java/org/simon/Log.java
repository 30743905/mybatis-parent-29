package org.simon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 36410
 * @Copyright © 2017 tiger Inc. All rights reserved.
 * @create 2017-10-23 17:19
 * Description:日志工厂
 */
public interface Log {
  Logger rootLogger = LoggerFactory.getLogger("root");
  Logger metricLog = LoggerFactory.getLogger("metricLog");
  Logger stacktraceLog = LoggerFactory.getLogger("stacktraceLog");
  Logger detailLog = LoggerFactory.getLogger("detailLog");
  Logger errorLog = LoggerFactory.getLogger("errorLog");
}