import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Settings{
	private static ArrayList<Setting> prefs=new ArrayList<Setting>();
	
	public static void addSetting(String name, String value) {
		synchronized(prefs){
			int index=Collections.binarySearch((List<Setting>)prefs, new Setting(name,null));
			if(index<0){
				prefs.add(-(index+1),new Setting(name,value));
			}else{
				prefs.get(index).setValue(value);
			}
		}
	}
	public static String getSetting(String name) {
		synchronized(prefs){
			int index=Collections.binarySearch((List<Setting>)prefs, new Setting(name,null));
			if(index<0){
				return "";
			}
			return prefs.get(index).getValue();
		}
	}
	public static void deleteSetting(String name){
		synchronized(prefs){
			int index=Collections.binarySearch((List<Setting>)prefs, new Setting(name,null));
			if(index>=0){
				prefs.remove(index).getName();
			}
		}
	}
	public static void changeSetting(String name, String newValue){
		addSetting(name,newValue);
	}
	public static void importSettings(File prefsFile) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader(prefsFile));
			String inline="";
			synchronized(prefs){
				prefs=new ArrayList<Setting>();
				while((inline=reader.readLine())!=null){
					addSetting(inline.substring(0,inline.indexOf("=")).trim(),inline.substring(inline.indexOf("=")+1).trim());
				}
			}
			reader.close();
		}catch(IOException IOE){
			JOptionPane.showMessageDialog(null,"Could not read from preferences file!\nProgram will now quit.","Preference File Error",JOptionPane.ERROR_MESSAGE);
	    	System.exit(1);
		}
	}
	public static void exportSettings(File prefsFile) {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(prefsFile));
			synchronized(prefs){
				for(int x=0;x<prefs.size();x++){
					writer.write(prefs.get(x).getName()+"="+prefs.get(x).getValue());
					writer.newLine();
					writer.flush();
				}
			}
			writer.close();
		}catch(IOException IOE){
			JOptionPane.showMessageDialog(null,"Could not save to preferences file!\nProgram will now quit.","Preference File Error",JOptionPane.ERROR_MESSAGE);
	    	System.exit(1);
		}
	}	
}