package printer;



/**
 * @author 17326
 * @date 2020/2/27 22:24
 */
public class MingLoggerImpl implements MingLogger{
	String name;
	public MingLoggerImpl(String name) {
		this.name = name;
	}

	/**
	 * 打印调用方的信息
	 */
	public static void log(String remark){
		Throwable throwable = new Exception();
		// 需要打印的方法所在的类
		String className = throwable.getStackTrace()[1].getClassName();
		className = className.substring(className.lastIndexOf(".") + 1);
		// 需要打印的方法名
		String methodName = throwable.getStackTrace()[1].getMethodName();
		int lineNum = throwable.getStackTrace()[1].getLineNumber();
		// 需要打印的调用方方法所在的类
		String callerClassName = throwable.getStackTrace()[2].getClassName();
		callerClassName = callerClassName.substring(callerClassName.lastIndexOf(".") + 1);
		// 需要打印的调用方方法名
		String callerMethodName = throwable.getStackTrace()[2].getMethodName();
		int callerLineNum = throwable.getStackTrace()[2].getLineNumber();
//		logger.info("调用者：" + callerClassName + "#" + callerMethodName + ":" + callerLineNum + "==》被调用者：" + className + "#" +methodName + lineNum + "==》备注：" + remark);
//		System.out.println("调用者：" + callerClassName + "#" + callerMethodName + ":" + callerLineNum + "\n被调用者：" + className + "#" +methodName + lineNum + "\n备注：" + remark +"\n");
//		System.out.println(  callerClassName + "#" + callerMethodName + ":" + callerLineNum + "\n" + className + "#" +methodName + lineNum + "\n" + remark +"\n");
//		logger.info(className + "#" +methodName + lineNum + "==》备注：" + remark);
//		System.out.println(className + "#" +methodName + lineNum + " 备注：" + remark);
	}

	public static MingLogger getLogger(String name) {
		return new MingLoggerImpl(name);
	}

	public static <T> MingLogger getLogger(Class<T> clazz) {
		return new MingLoggerImpl(clazz.getName());
	}

	private String getMsgContent(String msg){

		Throwable throwable = new Exception();
		// 需要打印的方法所在的类
		String className = throwable.getStackTrace()[2].getClassName();
		className = className.substring(className.lastIndexOf(".") + 1);
		// 需要打印的调用方方法名
		String methodName = throwable.getStackTrace()[2].getMethodName();
		int lineNum = throwable.getStackTrace()[2].getLineNumber();
		return className + "#" +methodName + ":" + lineNum + ":" + msg;
	}
	@Override
	public void info(String msg) {
		String output = getMsgContent(msg);
		System.out.println(output);
	}

	@Override
	public void error(String msg) {
		String output = getMsgContent(msg);
		System.err.println(output);
	}

	public static void log() {
		log("");
	}
}
