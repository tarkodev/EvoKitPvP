package fr.tarkod.kitpvp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileUtils {
	
	public static void createFile(File file) {
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveFile(File file, String text) {
		FileWriter fw;
		
		try {
			createFile(file);
			
			fw = new FileWriter(file);
			fw.write(text);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String loadContent(File file) {
		if(file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				StringBuilder text = new StringBuilder();
				
				String line;
				
				while((line = reader.readLine()) != null) {
					text.append(line);
				}
				
				reader.close();
				
				return text.toString();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	public static YamlConfiguration loadContentYaml(File file) {
		if(file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			return config;
		} else {
			System.out.println(file.getName() + " was null (loadYaml error)");
			return null;
		}
	}
}
