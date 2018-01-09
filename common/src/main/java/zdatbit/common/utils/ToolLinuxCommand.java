package zdatbit.common.utils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;

public class ToolLinuxCommand extends Thread {


	/**
	 * 执行shell命令 String[] cmd = { "sh", "-c", "lsmod |grep linuxVmux" }或者
	 * String[] cmd = { "sh", "-c", "./load_driver.sh" } int tp = 1 返回执行结果 非1
	 * 返回命令执行后的输出
	 */
	public String runCommand(String[] cmd, int tp) {
		StringBuffer buf = new StringBuffer(1000);
		String rt = "-1";
		try {
			Process pos = Runtime.getRuntime().exec(cmd);
			pos.waitFor();
			if (tp == 1) {
				if (pos.exitValue() == 0) {
					rt = "1";
				}
			} else {
				InputStreamReader ir = new InputStreamReader(
						pos.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				String ln = "";
				while ((ln = input.readLine()) != null) {
					buf.append(ln + "<br>");
				}
				rt = buf.toString();
				input.close();
				ir.close();
			}
		} catch (java.io.IOException e) {
			rt = e.toString();
		} catch (Exception e) {
			rt = e.toString();
		}
		return rt;
	}

	/**
	 * 执行简单命令 String cmd="ls" int tp = 1 返回执行结果 非1 返回命令执行后的输出
	 */
	public static String runCommand(String cmd, int tp) {
		StringBuffer buf = new StringBuffer(1000);
		String rt = "-1";
		try {
			Process pos = Runtime.getRuntime().exec(cmd);
			pos.waitFor();
			if (tp == 1) {
				if (pos.exitValue() == 0) {
					rt = "1";
				}
			} else {
				InputStreamReader ir = new InputStreamReader(
						pos.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				String ln = "";
				while ((ln = input.readLine()) != null) {
					buf.append(ln + "<br>");
				}
				rt = buf.toString();
				input.close();
				ir.close();
			}
		} catch (java.io.IOException e) {
			rt = e.toString();
		} catch (Exception e) {
			rt = e.toString();
		}
		return rt;
	}

	public static void main(String args[]) throws UnsupportedEncodingException {
		 String cmd="ls";
		 int tp = 1; 
		 runCommand(cmd, tp);
	}

}
