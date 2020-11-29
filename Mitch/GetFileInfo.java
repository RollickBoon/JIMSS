import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.text.SimpleDateFormat;
import java.util.*;

public class GetFileInfo {

  public static SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy", java.util.Locale.ENGLISH);

  public static class DataEntry {
  	int capacity_number;
    String date_time;
    int weekday;
    int hour;
    int minute;
    double x_value;
    
    public DataEntry(int x, String y){
      capacity_number = x;
      date_time = y;

      hour = Integer.parseInt(y.substring(9,11));
      minute = Integer.parseInt(y.substring(12,14));

      try{
        Date my_date = fmt.parse(y.substring(0,8));
        weekday = getDayNumberOld(my_date);
      }
      catch (Exception e){
        System.out.println(e);
      }
    }
  }  

  public static int getDayNumberOld(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.DAY_OF_WEEK);
  } 

  public static void main(String[] args) {

    ArrayList<Integer> capacity_nums = new ArrayList<Integer>();
    ArrayList<String> dates = new ArrayList<String>();

  	ArrayList<DataEntry> data_entries = new ArrayList<>();

    String temp = "";


    try {
      File myObj = new File("log.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
      	temp = myReader.nextLine();
        String[] entries = temp.split(",");
        String[] date_parse = entries[1].split("= ");

        //add exception handling for when capacity is an empty string
      	int cap = Integer.parseInt(entries[0]);
      	String date_time = date_parse[1];

        capacity_nums.add(cap);
        dates.add(date_time);
      }
      myReader.close();

      for(int i=0; i<capacity_nums.size(); i++){
        DataEntry tem = new DataEntry(capacity_nums.get(i), dates.get(i));
        data_entries.add(tem);       
      }

      for(int i=0; i<capacity_nums.size(); i++){
        System.out.println(data_entries.get(i).weekday);    
      }

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

}
