package crawler.java.thread;

import java.util.*;

public class CrawlerThread  {
 
 
//private String jdbcUrl;
//private String username;
//private String password;
//private String databaseName;
//private Connection conn1 ;
//private Connection conn2 ;
//private OracleDataSource ods;
//private static long rowId;
private static int num = 0;
 
public CrawlerThread(){
 
 
/*rowId = 1;
databaseName = dbName;
username = "system";
password = "wysiwyg";
//jdbcUrl = "jdbc:oracle:thin:"+username+"/"+password+"@127.0.0.1:1521/"+databaseName;
jdbcUrl = "jdbc:oracle:thin:@127.0.0.1:1521:" +  databaseName;
try{
 
DriverManager.registerDriver(new OracleDriver());
//ods = new OracleDataSource();
//ods.setURL(jdbcUrl);
conn1 = DriverManager.getConnection(jdbcUrl,username,password);
conn2 = DriverManager.getConnection(jdbcUrl,username,password);
 
//reset the database
CallableStatement resetStmt = conn1.prepareCall("begin RESETDB; end;");
resetStmt.executeUpdate();
resetStmt.close();
 
}
catch(Exception e){
 
System.out.println(e);
 
}*/
 
 
}
 
synchronized public int inc(){
 
num++;
return num;
 
}
 
synchronized public boolean addToList(HashSet s, String link){
 
 
if(!s.contains(link)){
 
s.add(link);
return true;
 
}
else
return false;
 
/*String query = "";
java.sql.Statement stmt = null;
 
if(!link.contains("'")){
 
if(tableName.equals("newlinks"))
query = "INSERT INTO "+tableName+" (link,visit,linknumber) VALUES('"+link+"',0,linknumber.nextval)";
else if(tableName.equals("oldlinks"))
query = "INSERT INTO "+tableName+" (link) VALUES('"+link+"')";
else
query = "";
 
}
 
else
return ;
try{
 
if ((conn1==null)||conn1.isClosed()){
 
conn1 = DriverManager.getConnection(jdbcUrl,username,password);
 
}
stmt = conn1.createStatement();
stmt.executeUpdate(query);
 
 
}
catch(Exception e){
 
//System.out.println(e);
 
}
finally{
 
try{
 
stmt.close();
 
}
catch(Exception e){
 
}
 
}*/
 
 
}
 
synchronized public boolean isNewLink(ArrayList<HashSet> s, String link){
 
 
for(int i=0;i<s.size();i++){
 
if((s.get(i)).contains(link))
return false;
 
}
return true;
 
}
 
synchronized public String getNextLink(HashSet s){
 
 
String link = "";
synchronized(s){
 
link = s.iterator().next().toString();
 
}
s.remove(link);
return link;
 
/*java.sql.Statement stmt = null;
ResultSet row = null;
//String link = s.iterator().next().toString();
//s.remove(link);
  
//retrieve the first row
String query = "SELECT link FROM "+tableName+" WHERE linknumber = "+rowId+ " AND visit = 0";
rowId ++;
try{
 
if ((conn2==null)||conn2.isClosed()){
 
conn2 = DriverManager.getConnection(jdbcUrl,username,password);
 
}
 
stmt = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
row = stmt.executeQuery(query);
 
while(row.next()){
 
link = row.getString(1);
//row.deleteRow();
 
}
String del_query = "UPDATE newlinks SET visit = 1  WHERE link = '"+link+"'";
stmt.executeUpdate(del_query);
 
 
}
catch(Exception e){
 
System.out.println(e);
 
}
finally{
 
try{
 
row.close();
stmt.close();
 
}
catch(Exception e){
 
}
 
}*/
 
 
}
 
 
 
}
