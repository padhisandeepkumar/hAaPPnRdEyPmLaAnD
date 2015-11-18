package com.replad.excel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVReader;

//import com.google.appengine.api.blobstore.BlobKey;
//import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.replad.sub.work.SubworkDbUtils;

public class ExportService extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {/*
            BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
            BlobstoreInputStream is = new BlobstoreInputStream(blobKey);

            CSVReader csvReader = new CSVReader(new InputStreamReader(is));
            List<String[]> rows = csvReader.readAll();

            Iterator<String[]> iterator = rows.iterator();
            //skip header row
            iterator.next();
            
            List<List<String>> workDetailList = new ArrayList<List<String>>();
            List<String> list = null;
            while(iterator.hasNext()){
                String[] record = iterator.next();
                list = new ArrayList<String>();
                list.add(0, record[0]);
                list.add(1, record[1]);
                list.add(2, record[2]);
                workDetailList.add(list);
            }
            int status = new SubworkDbUtils().updateWorkDetails(workDetailList);
           	if(status==0){
           		res.sendRedirect("adminMgmt.jsp?requestFor=workDetails");
           	}
           	csvReader.close();
        */}
}
