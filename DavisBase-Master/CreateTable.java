
import java.io.RandomAccessFile;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
public class CreateTable {
public static void executeCreateString(String createString) {
		
		System.out.println("CREATE METHOD");
		System.out.println("Executing the string:\"" + createString + "\"");
		String[] tokens=createString.split(" ");
		
		if (tokens[1].compareTo("index")==0)
		{
		String col = tokens[4];
		String colName = col.substring(1,col.length()-1);
		Index.createIndex(tokens[3],colName, "String");
		}
		else
		{
		
		if (tokens[1].compareTo("table")>0){
			System.out.println("Wrong syntax");
			
		}
		else{
				 
		String tableName = tokens[2];
		String[] temp = createString.split(tableName);
		String cols = temp[1].trim();
		String[] create_cols = cols.substring(1, cols.length()-1).split(",");
		
		for(int i = 0; i < create_cols.length; i++)
			create_cols[i] = create_cols[i].trim();
		
		if(DavisBase.IfTableAvailable(tableName)){
			System.out.println("Table "+tableName+" already exists.");
		}
		else
			{
			createTable(tableName, create_cols);		
			}
			}
		}
	}
public static void createTable(String table, String[] col){                                
	try{	
		
		RandomAccessFile file = new RandomAccessFile("data/"+table+".tbl", "rw");				
		file.setLength(Table.pageSize);										
		file.seek(0);				
		file.writeByte(0x0D);		
		file.close();
		
		file = new RandomAccessFile("data/davisbase_tables.tbl", "rw");
		
		int noPages = Table.pages(file);
		int page=1;
		for(int p = 1; p <= noPages; p++){
			int rm = Page.fetchRightMost(file, p);
			if(rm == 0)
				page = p;
		}
		
		int[] keys = Page.fetchKeyArray(file, page);
		int l = keys[0];
		for(int i = 0; i < keys.length; i++)
			if(keys[i]>l)
				l = keys[i];
		file.close();
		
		String[] values = {Integer.toString(l+1), table};
		insertInto("davisbase_tables", values);

		file = new RandomAccessFile("data/davisbase_columns.tbl", "rw");
		
		noPages = Table.pages(file);
		page=1;
		for(int p = 1; p <= noPages; p++){
			int rm = Page.fetchRightMost(file, p);
			if(rm == 0)
				page = p;
		}
		
		keys = Page.fetchKeyArray(file, page);
		l = keys[0];
		for(int i = 0; i < keys.length; i++)
			if(keys[i]>l)
				l = keys[i];
		file.close();

		for(int i = 0; i < col.length; i++){
			l = l + 1;
			String[] token = col[i].split(" ");
			String col_name = token[0];
			String dt = token[1].toUpperCase();
			String pos = Integer.toString(i+1);
			String nullable;
			if(token.length > 2)
				nullable = "NO";
			else
				 nullable = "YES";
			String[] value = {Integer.toString(l), table, col_name, dt, pos, nullable};
			insertInto("davisbase_columns", value);
		}

	}catch(Exception e){
		System.out.println(e);
	}
}
public static void insertInto(String table, String[] values){
	try{
		RandomAccessFile file = new RandomAccessFile("data/"+table+".tbl", "rw");
		insertValuesInto(file, table, values);
		file.close();

	}catch(Exception e){
		System.out.println(e);
	}
}
public static void insertValuesInto(RandomAccessFile file, String table, String[] values){
	String[] dtype = Table.getDataType(table);
	String[] nullable = Table.getNullable(table);

	for(int i = 0; i < nullable.length; i++)
		if(values[i].equals("null") && nullable[i].equals("NO")){
			System.out.println("NULL-value constraint violation");
			System.out.println();
			return;
		}

	int key = new Integer(values[0]);
	int page = Table.searchKeyPage(file, key);
	if(page != 0)
		if(Page.hasKey(file, page, key)){
			System.out.println("Uniqueness constraint violation");
			return;
		}
	if(page == 0)
		page = 1;


	byte[] stc = new byte[dtype.length-1];
	short plSize = (short) Table.calPayloadSize(table, values, stc);
	int cellSize = plSize + 6;
	int offset = Page.checkLeafSize(file, page, cellSize);


	if(offset != -1){
		Page.insertLeafCell(file, page, offset, plSize, key, stc, values);
	}else{
		Page.splitLeaf(file, page);
		insertValuesInto(file, table, values);
	}
}

}
