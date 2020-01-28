import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.SortedMap;
public class DeleteTable {
	public static void executeDeleteString(String deleteString) {
		System.out.println("DELETE METHOD");
		System.out.println("Executing the string:\"" + deleteString + "\"");
		
		String[] tokens=deleteString.split(" ");
		String table = tokens[3];
		String[] temp = deleteString.split("where");
		String cmpTemp = temp[1];
		String[] cmp = DavisBase.setUpComparisions(cmpTemp);
		if(!DavisBase.IfTableAvailable(table)){
			System.out.println("Table "+table+" does not exist.");
		}
		else
		{
			delete(table, cmp);
		}
	}
	public static void delete(String table, String[] cmp){
		try{
		int key = new Integer(cmp[2]);

		RandomAccessFile file = new RandomAccessFile("data/"+table+".tbl", "rw");
		int numPages = Table.pages(file);
		int page = 0;
		for(int p = 1; p <= numPages; p++)
			if(Page.hasKey(file, p, key)&Page.fetchPageType(file, p)==0x0D){
				page = p;
				break;
			}
		
		if(page==0)
		{
			System.out.println(" key value does not exist");
			return;
		}
		
		short[] cellsAddr = Page.fetchCellArray(file, page);
		int k = 0;
		for(int i = 0; i < cellsAddr.length; i++)
		{
			long loc = Page.fetchCellLoc(file, page, i);
			String[] vals = Table.retrieveValues(file, loc);
			int x = new Integer(vals[0]);
			if(x!=key)
			{
				Page.assignCellOffset(file, page, k, cellsAddr[i]);
				k++;
			}
		}
		Page.assignCellNumber(file, page, (byte)k);
		
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

}
