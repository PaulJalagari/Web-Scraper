package crawler.java.pagemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
 
public class PageManager {
 
 
private String pageContents;
 
public PageManager(){
 
pageContents = "";
 
 
}
 
public ArrayList retrieveLinks(URL pageURL){
 
 
retrieveContents(pageURL);
Pattern p = Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
Matcher m = p.matcher(pageContents);
ArrayList links = new ArrayList();
 
try{
 
 
while (m.find()){
 
String link = m.group(1).trim();
 
// Skip empty links.
         if (link.length() < 1) {
 
          continue;
          
}
          
      // Skip links that are just page anchors.
         if (link.charAt(0) == '#') {
 
          continue;
          
}
          
      // Skip mailto links.
         if (link.indexOf("mailto:") != -1) {
 
          continue;
          
}
         
       //Skip javascript links
         if (link.toLowerCase().indexOf("javascript") != -1) {
 
             continue;
          
}
          
       if(link.startsWith(".")){
 
       link = link.substring(1);
        
}
                 
                
      // String baseURL = getBaseUrl(pageURL);
       
       //relative links
       if (link.indexOf("://") == -1) {
 
            if (link.charAt(0) == '/') {
 
              link = "http://" + pageURL.getHost() + link;
             
}
            else if(link.charAt(0) == '.'){
 
            link = link.substring(1);
            String tmp_link = pageURL.getHost();
            tmp_link = tmp_link.substring(0,tmp_link.lastIndexOf('/'));
            link = "http://" + tmp_link + link;        
             
}
            else
            {
 
              String file = pageURL.getFile();
              if (file.indexOf('/') == -1) {
 
                link = "http://" + pageURL.getHost() + "/" + link;
                 
               
}
              else {
 
                String path = file.substring(0, file.lastIndexOf('/') + 1);
                 
                if(link.charAt(0) == '.'){
 
             link = link.substring(2);
             path = path.substring(0, path.length() -1 );//remove last slash from path
             path = path.substring(0,path.lastIndexOf('/')+1);
              
}               
                 
                link = "http://" + pageURL.getHost() + path + link;
                //link = baseURL + link;
               
}
             
}
            
}
        
      // Remove anchors from link.
         int index = link.indexOf('#');
         if (index != -1) {
 
         link = link.substring(0, index);
           
}
      // Remove leading last slash from URL's host if present.
         link = removeLastSlash(link);
          
       //skip if url format is not valid
         if (!isVerified(pageURL.toString())) {
 
             continue;
            
}
       
         links.add(link);     
          
 
}
 
}
catch(Exception e){
 
 
}
return links;
 
}
 
private void retrieveContents(URL pageURL){
 
 
StringBuffer sb = new StringBuffer();
String s;
try{
 
BufferedReader br = new BufferedReader(new InputStreamReader(pageURL.openStream()));
while((s = br.readLine()) != null){
 
sb.append(s);
 
}
 
pageContents = sb.toString();
br.close();
 
 
 
}
catch(MalformedURLException mue){
 
 System.out.println(mue);
 
 
}
catch(IOException ioe){
 
//System.out.println(ioe);
 
 
}
 
 
}
 
 
private String removeLastSlash(String tUrl)
   {
               
          if(tUrl.endsWith("/"))
          {
 
              tUrl = tUrl.substring(0,tUrl.length()-1);
           
}
       return (tUrl);
    
}
 
public boolean isVerified(String url){
 
 
 if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://"))
 return false;
 
 URL verifiedUrl;
 try {
 
       verifiedUrl = new URL(url);
       return true;
       
}
 catch (Exception e) {
 
        return false;
       
}
 
 
}
 
}