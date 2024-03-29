package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gdata.client.finance.FinanceService;
import com.google.gdata.data.extensions.Money;
import com.google.gdata.data.finance.DaysGain;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PortfolioFeed;
import com.google.gdata.data.finance.PositionData;
import com.google.gdata.data.finance.PositionEntry;
import com.google.gdata.util.ServiceException;

public class StockWatch1 extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	Hashtable idSymbolHash = new Hashtable();
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		int idcount = 1;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TableLayout t = (TableLayout)findViewById(R.id.table1);
		t.setColumnStretchable(0, true);
		t.setColumnStretchable(1, true);
        t.setColumnStretchable(2, true);
        t.setColumnStretchable(3, true);
        
        // Let us set the headings.
        TableRow tr = new TableRow(this);
        for(int loop=0;loop<5;loop++)
        {
	        String name = new String();
        	switch(loop)
	        {
	        case 0: name="STOCK\nname";break;
	        case 1: name=" ";break;
	        case 2: name="Shares\nowned";break;
	        case 3: name="Stock\nPrice";break;
	        case 4: name="Day's\ngain";break;
	       
	        }
        	
        	
		  	TextView tv = new TextView(this);
		  	tv.setText(name);
		  	tv.setTextSize(14);
		  	tr.addView(tv);
		  	
        }
        tr.setBackgroundColor(Color.rgb(102, 0, 0));
        t.addView(tr);
	  	
        PortfolioManager portfolioManager = PortfolioManager.getPortfolioManager("tttemper888@gmail.com", "tempster");
        List<StockWatchPortfolio> portfolioList =portfolioManager.getPortfolios();
        if(portfolioList == null)
        {
        	System.out.print("\n\n\nLIST EMPTY\n\n\n");
        }
        Iterator<StockWatchPortfolio> portfolioListIterator = portfolioList.iterator();
        while(portfolioListIterator.hasNext())
        {
        	StockWatchPortfolio portfolio = portfolioListIterator.next();
//        	TableRow tableRow = new TableRow(this);
//        	TextView textView = new TextView(this);
//        	textView.setText(portfolio.getPortfolioEntry().getTitle().getPlainText());
        	Set<StockWatchPosition> positionSet = portfolio.getPositions();
//        	tableRow.addView(textView);
//        	t.addView(tableRow);
        	
        	Iterator<StockWatchPosition> positionSetIterator = positionSet.iterator();
        	while(positionSetIterator.hasNext())
        	{
        		StockWatchPosition position = positionSetIterator.next();
        		TableRow tr1 = new TableRow(this);
       	  		TextView tv1 = new TextView(this);
       	  		String name = new String(position.getPositionEntry().getSymbol().getFullName());
       	  		String sname = new String(position.getPositionEntry().getSymbol().getSymbol());
       	  		if(name.length()>15)
       	  		{
       	  			name = name.substring(0,15) + "..";
       	  		}
       	  		tv1.setText(sname);
       	  		// Let us assign an ID to this text box and store relation in the hash table
       	  		tv1.setId(idcount);
       	  		idSymbolHash.put(new Integer(idcount), sname);
       	  		idcount++;
       	  		tv1.setOnClickListener(this);
       	  		
       	  		
       	  		
       	  		
       	  		TextView tv2 = new TextView(this);
       	  		TextView tv3 = new TextView(this);
       	  		TextView tv4 = new TextView(this);
       	  		TextView tv5 = new TextView(this);
       	  		tr1.addView(tv1);
       	  		tr1.addView(tv2);
       	  		tr1.addView(tv3);
       	  		tr1.addView(tv4);
       	  		tr1.addView(tv5);
       	  		t.addView(tr1);
       	  		tv1.setTextSize(14);
       	  		tv2.setTextSize(11);
       	  		tv3.setTextSize(14);
       	  		tv4.setTextSize(14);
       	  		tv5.setTextSize(14);
       	  		
       	  		// Set other IDs as well
       	  	tv2.setId(idcount);
   	  		idSymbolHash.put(new Integer(idcount), sname);
   	  		idcount++;
   	  		tv3.setId(idcount);
	  		idSymbolHash.put(new Integer(idcount), sname);
	  		idcount++;
	  		tv4.setId(idcount);
   	  		idSymbolHash.put(new Integer(idcount), sname);
   	  		idcount++;
   	  		tv5.setId(idcount);
	  		idSymbolHash.put(new Integer(idcount), sname);
	  		idcount++;
	  		
	  		tv2.setOnClickListener(this);
	  		tv3.setOnClickListener(this);
	  		tv4.setOnClickListener(this);
	  		tv5.setOnClickListener(this);
       	  		
       	  		
       	  		tv2.setText(name);
       	  		
       	  	int count = (int)position.getStockCount();
   	  		tv3.setText(Integer.toString(count));
   	  		PositionEntry positionEntry = position.getPositionEntry(); 
   	  		PositionData pdata = positionEntry.getPositionData();		 
   	  		
       	  	
       	  		
       	  	if (pdata.getMarketValue() == null)
	       	 {
	       	       System.out.println("\t\tMarket Value not specified");
	       	 }
	       	 else
	       	 {
	       	 		for (int i = 0; i < pdata.getMarketValue().getMoney().size(); i++) {
	       	 		Money m = pdata.getMarketValue().getMoney().get(i);
	       	         System.out.printf("\t\tThis position is worth %.2f %s.\n", m.getAmount(), m.getCurrencyCode());
	       	         String lastprice = Double.toString(m.getAmount()/position.getStockCount());
	       	         String value = lastprice; // + " " + m.getCurrencyCode();
	       	         tv4.setText(value);
	       	 		}
	       	 }
       	  		
       	  		
       	  	
       	  		if (pdata.getDaysGain() == null) 
       	  		{
       	  			System.out.println("\t\tDay's Gain not specified");
       	  		} 
       	  		else 
       	  		{	
       	  			int limit=5;
       	  			System.out.println(pdata.getDaysGain().getMoney().size());
       	  			for (int i = 0; i < pdata.getDaysGain().getMoney().size(); i++) 
       	  			{
       	  				Money money = pdata.getDaysGain().getMoney().get(i);
       	  				
       	  				
       	  				String gain = Double.toString(money.getAmount()/position.getStockCount());
	       	  			if(gain.length()<5)
	   	  				{
	   	  					limit = gain.length();
	   	  				}
       	  				
       	  				String value = gain.substring(0, limit);// + " " + money.getCurrencyCode();
       	  				
       	  				tv5.setText(value);
       	  				System.out.printf("\t\tThis position made %.2f %s today.\n", money.getAmount(), money.getCurrencyCode());
       	  			}
       	  		}
       	  		if (pdata.getGain() == null) 
       	  		{
       	  			System.out.println("\t\tTotal Gain not specified");
       	  		} 
       	  		else 
       	  		{
       	  			for (int i = 0; i < pdata.getGain().getMoney().size(); i++) 
       	  			{
       	  				Money money = pdata.getGain().getMoney().get(i);
       	  				System.out.printf("\t\tThis position has a total gain of %.2f %s.\n", money.getAmount(), money.getCurrencyCode());
       	  			}
       	  		}
       	  		if (pdata.getMarketValue() == null) 
       	  		{
       	  			System.out.println("\t\tMarket Value not specified");
       	  		} 
       	  		else 
       	  		{
       	  			for (int i = 0; i < pdata.getMarketValue().getMoney().size(); i++) 
       	  			{
       	  				Money money = pdata.getMarketValue().getMoney().get(i);
       	  				System.out.printf("\t\tThis position is worth %.2f %s.\n", money.getAmount(), money.getCurrencyCode());
       	  			}
       	  		}
        	}
        }
    }	
	
	public void onClick(View v)
	{
		Integer id = new Integer(v.getId());
		// Let us recover the symbol from this
		String symbol = (String)idSymbolHash.get(id);
		 Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, symbol, duration);
		toast.show();
		
		
	}
}