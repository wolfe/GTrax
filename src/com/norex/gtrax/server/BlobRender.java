package com.norex.gtrax.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class BlobRender extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String width = request.getParameter("w");
		
		if (id != null && !id.equals("")) {
			PersistenceManager pm = PMF.getPersistenceManager();
			BlobStorage blob = pm.getObjectById(BlobStorage.class, id);
			response.setContentType(blob.getContentType());
			
			if (width == null) { // Don't need to resize, just output it
				response.getOutputStream().write(blob.getBlob().getBytes());
			} else {
				ImagesService imageService = ImagesServiceFactory.getImagesService();
				Image oldImage = ImagesServiceFactory.makeImage(blob.getBlob().getBytes());
				Transform resize = ImagesServiceFactory.makeResize(Integer.parseInt(width), oldImage.getHeight());
				
				Image newImage = imageService.applyTransform(resize, oldImage);
				
				response.getOutputStream().write(newImage.getImageData());
			}
		}
	}
}