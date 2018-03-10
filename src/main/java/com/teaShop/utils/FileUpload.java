package com.teaShop.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile; 


public class FileUpload {
	 public String uploadPhoto(HttpServletRequest request,HttpServletResponse response,MultipartFile myFile){
	        try {
	            //输出文件后缀名称
//	            System.out.println(myFile.getOriginalFilename());
	            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	            //图片名称
	            String name = df.format(new Date());

	            Random r = new Random();
	            for(int i = 0 ;i<3 ;i++){
	                name += r.nextInt(10);
	            }
	            //
	            String ext = FilenameUtils.getExtension(myFile.getOriginalFilename()).toLowerCase();
	            //保存图片       File位置 （全路径）   /upload/fileName.jpg
	            String url = request.getSession().getServletContext().getRealPath("image");
	            //相对路径
	            String path = "/"+name + "." + ext;
	            File file = new File(url);
	            if(!file.exists()){
	                file.mkdirs();
	            }

	            
	            myFile.transferTo(new File(url+path));
//	            if(ext.equals("jpg") || ext.equals("png") || ext.equals("jpeg") || ext.equals("gif")){
//	            	this.zipImageFile(new File(url+path),new File(url+path), 1320,800, 0.8f);
//	            }
	            return ("../image"+path);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return null;
	    }
	 
	 //上传Excel文件
	 public Map<String, Object> updateExcel(HttpServletRequest request,HttpServletResponse response,MultipartFile myFile){
	        Map<String, Object> json = new HashMap<String, Object>();
	        try {
	            //输出文件后缀名称
	            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	            //图片名称
	            String name = df.format(new Date());

	            Random r = new Random();
	            for(int i = 0 ;i<3 ;i++){
	                name += r.nextInt(10);
	            }
	            //
	            String ext = FilenameUtils.getExtension(myFile.getOriginalFilename()).toLowerCase();
	            //保存图片       File位置 （全路径）   /upload/fileName.jpg
	            String url = request.getSession().getServletContext().getRealPath("/upload");
	            //相对路径
	            String path = "/"+name + "." + ext;
	            File file = new File(url);
	            if(!file.exists()){
	                file.mkdirs();
	            }
	            //System.out.println(url+path);
	            
	            myFile.transferTo(new File(url+path));
	            json.put("success", url+path);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return json ;

	    }
	 
	 //压缩图片
	 /**
	 * 根据设置的宽高等比例压缩图片文件<br>
	 * 先保存原文件，再压缩、上传
	 *
	 * @param oldFile
	 * 要进行压缩的文件
	 * @param newFile
	 * 新文件
	 * @param  imgWidth
	 * 宽度 //设置宽度时（高度传入0，等比例缩放）
	 * @param imgHeight
	 * 高度 //设置高度时（宽度传入0，等比例缩放）
	 * @param quality
	 * 质量
	 * @return 返回压缩后的文件的全路径
	 */
	 public String zipImageFile(File oldFile, File newFile, int imgWidth,int imgHeight, float quality) {
		 int width ;
		 int height ;
		 
		 if (oldFile == null) {
			 return null;
		 }
		 try {
			 /** 对服务器上的临时文件进行处理 */
			 Image srcFile = ImageIO.read(oldFile);
			 int w = srcFile.getWidth(null);
			 int h = srcFile.getHeight(null);
//			 System.out.println(w);
//			 System.out.println(h);
			 if(w >= h){
				 width = imgWidth;
				 height = (int)(((double)imgWidth / (double)w) * h);
				 if(height > imgHeight){
					 width = (int)(((double)imgHeight / (double)height) * width);
					 height = imgHeight;
				 }
			 }else{
				 height = imgHeight;
				 width = (int)(((double)imgHeight / (double)h) * w);
				 
				 if(width > imgWidth){
					 height = (int)(((double)imgWidth / (double)width) * height);
					 width = imgWidth;
				 }
			 }
			 String srcImgPath = newFile.getAbsoluteFile().toString();
			 // System.out.println(srcImgPath);
			 String subfix = "jpg";
			 subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".") + 1, srcImgPath.length());
		
			 BufferedImage buffImg = null;
			 if (subfix.equals("png")) {
				 buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			 } else {
				 buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			 }
			 Graphics2D graphics = buffImg.createGraphics();
			 graphics.setBackground(new Color(255, 255, 255));
			 graphics.setColor(new Color(255, 255, 255));
			 graphics.fillRect(0, 0, width, height);
			 graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		
			 ImageIO.write(buffImg, subfix, new File(srcImgPath));
		
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return newFile.getAbsolutePath();
	 }
	
}
