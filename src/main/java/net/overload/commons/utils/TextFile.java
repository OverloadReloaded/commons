package net.overload.commons.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.List;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.logger.LogLevel;

public class TextFile {

	private File file;
	private List<String> lines;
	private String path;

	public TextFile(String fileName) {
		this.setFile(new File(fileName));
	}
	
	public TextFile(File file) {
		this.setFile(file);
	}

	public Boolean write() {
		CommonsPluginBukkit.get().logger().send(LogLevel.INFO, "Writing file: " + this.getPath().toString());
		try {
			Files.write(this.getFile().toPath(), this.getLines(), Charset.forName("UTF-8"), new OpenOption[0]);
			CommonsPluginBukkit.get().logger().send(LogLevel.SUCCESS, "Success: " + this.getPath().toString());
			return true;
		} catch (Exception e) {
			CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Could not create file. (" + e.getMessage() + ")");
			if (CommonsPluginBukkit.get().config.getPrintStackTrace()) e.printStackTrace();
			return false;
		}
	}
	
	public void addLine(String line) {
		this.getLines().add(line);
	}
	
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
