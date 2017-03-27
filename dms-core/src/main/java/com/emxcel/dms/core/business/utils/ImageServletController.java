package com.emxcel.dms.core.business.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServletController extends HttpServlet {

  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * @see javax.servlet.http.HttpServlet #doGet(javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String filename = request.getParameter("imageName");
    if (filename != null && !filename.equals("")) {
      System.out.println("filename:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+filename);
      String rootPath = CommonUtil.LOCATION;
      File dir = new File(rootPath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      File file = new File(rootPath + filename);
      if (file.exists()) {
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
      }
    }
  }
}