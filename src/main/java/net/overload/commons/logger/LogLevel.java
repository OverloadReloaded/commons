package net.overload.commons.logger;

public enum LogLevel {
	WARNING("\033[33m[WARNING] \033[0m"),
	  ERROR("\033[31m[ERROR] \033[0m"),
	  CRITICAL("\033[31m[CRITICAL] \033[31m"),
	  INFO("\033[36m[INFO] \033[0m"),
	  SUCCESS("\033[32m[SUCCESS] \033[0m"),
	  DEBUG("\033[35m[DEBUG] \033[0m"),
	  STACK_TRACE("\033[35m[STACK_TRACE] \033[0m"),
	  ERROR_MESSAGE("\033[35m[ERROR_MESSAGE] \033[0m"),
	  CMD("\033[34m[COMMAND] \033[0m"),
	  LOG("\033[37m[LOG] \033[0m"),
	  TIMING("\033[37m[TIMING] \033[0m");
	  
	  private String level;
	  
	  LogLevel(String level) {
	    this.level = level;
	  }
	  
	  public String getLevel() {
	    return this.level;
	  }
}
