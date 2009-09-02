package com.norex.gtrax.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.KeyFactory;

public class FileUpload extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		ServletFileUpload upload = new ServletFileUpload();
		
		try {
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				
				String name = item.getFieldName();
				InputStream stream = item.openStream();
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int len;
				byte[] buffer = new byte[8192];
				
				while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
                    out.write(buffer, 0, len);
                }

                int maxFileSize = 10*(1024*2); //10 megs max 
                if (out.size() > maxFileSize) { 
                    System.out.println("File is > than " + maxFileSize);
                    return;
                }
                
                PersistenceManager pm = PMF.getPersistenceManager();

                BlobStorage blob = new BlobStorage();
                
                blob.setBlob(new Blob(out.toByteArray()));
                blob.setContentType(item.getContentType());
                blob.setFilename(item.getName());
                
                pm.makePersistent(blob);
                pm.close();
                
                response.setContentType("text/plain");
                output.write(KeyFactory.keyToString(blob.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
