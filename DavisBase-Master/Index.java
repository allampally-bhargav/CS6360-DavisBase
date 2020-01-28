import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.SortedMap;
public class Index{
	public static void createIndex(String tableName,String colName, String dType)
	{

		int serial_Code = 0;
		int record_size = 0;
		if(dType.equalsIgnoreCase("int"))
		{
			record_size=record_size+4;
			serial_Code=0x06;
		}
		else if(dType.equalsIgnoreCase("tinyint"))
		{
			record_size=record_size+1;
			serial_Code=0x04;
		}
		else if(dType.equalsIgnoreCase("smallint"))
		{
			record_size=record_size+2;
			serial_Code=0x05;
		}
		else if(dType.equalsIgnoreCase("bigint"))
		{
			record_size=record_size+8;
			serial_Code=0x07;
		}
		else if(dType.equalsIgnoreCase("real"))
		{
			record_size=record_size+4;
			serial_Code=0x08;
		}
		else if(dType.equalsIgnoreCase("double"))
		{
			record_size=record_size+8;
			serial_Code=0x09;
		}
		else if(dType.equalsIgnoreCase("datetime"))
		{
			record_size=record_size+8;
			serial_Code=0x0A;
		}
		else if(dType.equalsIgnoreCase("date"))
		{
			record_size=record_size+8;
			serial_Code=0x0B;
		}
		else if(dType.equalsIgnoreCase("text"))
		{
			//record_size=record_size+8;
			serial_Code=0x0C;
		}
		int ordinal_pos1 = 0;
		ArrayList<String> ord_position = new ArrayList<>();




		for(int o=0;o<16;o++){
			ord_position.add(".");
		}


		try{

			RandomAccessFile columnFile=new RandomAccessFile("data/"+colName+".ndx", "rw");
			columnFile.setLength(512);
			columnFile.seek(0);
			columnFile.writeByte(serial_Code);
			int null_val = 1;
			columnFile.writeByte(null_val);
			columnFile.write(ordinal_pos1);
			ordinal_pos1++;
			columnFile.writeByte(0x00);
			columnFile.writeByte(0x10);
		}
		catch (Exception e)
		{

			System.out.println(e);

		}    

	}
}

