
import java.sql.Date;
import java.util.ArrayList;
import java.sql.*;  
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahmed
 */
public class Main {
    public static HashMap<Integer,Project> Projects= new HashMap<>();
    public static HashMap<Integer,Employ> Employes= new HashMap<>();
    public static ArrayList <Integer> pIDs=new ArrayList<>();
    public static ArrayList <Integer> eIDs=new ArrayList<>();
    public static int NumofDays,NumofHours;
    
    public static void main(String[] args) {
       load();
       for (int i =0;i<Main.pIDs.size();i++)
                {   
                    System.out.println(Projects.get(pIDs.get(i)).name);
                }
       Form1 f1= new Form1();
       f1.setVisible(true);
    }
    public static ArrayList<Float> dloade(int pnum)
    {
        ArrayList<Float> out=new ArrayList<>(); 
        ArrayList<Task> T=new ArrayList<>();
        ArrayList<Float> d=new ArrayList<>();
        T=Projects.get(pnum).Tasks;
        for(int i =0 ; i<T.size();i++)
        {
            d.add(T.get(i).Duration);
            float tmp=d.get(i);
            float em=T.get(i).Emp.hrs/T.get(i).Emp.NumofTasks;
            float planned=tmp/em;
            out.add(planned);
        }
        return out;
    }
    public static ArrayList<String> Nloade(int pnum)
    {
        ArrayList<Task> T=new ArrayList<>();
        ArrayList<String> N=new ArrayList<>();
        T=Projects.get(pnum).Tasks;
        for(int i =0 ; i<T.size();i++)
            N.add(T.get(i).TName);
    return N;
    }
    public static void load()
    {
    try{  
        Class.forName("com.mysql.jdbc.Driver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:mysql://localhost:3306/isdb","root","12345678");   
        Statement stmt=con.createStatement();  
        
        //Load Project table
        ResultSet rs=stmt.executeQuery("SELECT * FROM `project`");  
        while(rs.next())
        {
            int ID = 0;
            String Name = null;
            java.sql.Date Start,due;
            Project tmp = new Project();
            ID=rs.getInt(1);  
            Name=rs.getString(2);     
            Start=rs.getDate(3);  
            due=rs.getDate(4);
            tmp.ProID=ID;
            tmp.name=Name;
            tmp.start=Start;
            tmp.due=due;
            pIDs.add(ID);
            Projects.put(ID, tmp);
        }
        //=================================================
        //load Employe
        rs=stmt.executeQuery("SELECT * FROM `employ`");  
        while(rs.next())
        {
            Employ em=new Employ();
            int EmID;
            String Name=null;
            String role=null;
            int hrs;
            int NumofTasks;
            EmID=rs.getInt(1);
            Name=rs.getString(2);
            role=rs.getString(3);
            hrs=rs.getInt(4);
            NumofTasks=rs.getInt(5);
            
            em.EmID=EmID;
            em.Name=Name;
            em.NumofTasks=NumofTasks;
            em.hrs=hrs;
            em.role=role;
            eIDs.add(EmID);
            Employes.put(EmID, em);
            
        }
        //=================================================
        //load Tasks
        rs=stmt.executeQuery("SELECT * FROM `tasks`");  
        while(rs.next())
        {
            Task T=new Task();
            int TID;
            int ProID;
            float Du;
            Employ Emp;
            String Tname;
            int parentID;
            int emID;
            TID=rs.getInt(1);
            Tname=rs.getString(2);
            ProID=rs.getInt(3);
            emID=rs.getInt(4);
            Du=rs.getFloat(5);
            parentID=rs.getInt(6);
            T.TaskID=TID;
            T.TName=Tname;
            T.ProID=ProID;
            Emp=Employes.get(emID);;
            T.Emp=Emp;
            T.parentID=parentID;
            T.Duration=Du;
            ResultSet rs2=stmt.executeQuery("SELECT * FROM `depend` WHERE depend.TaskID = "+TID);  
            while(rs2.next())
            {
                int sid;
                sid=rs2.getInt(2);
                T.Depend.add(Projects.get(ProID).Tasks.get(sid));
            }
            Project ptmp=Projects.get(ProID);
            ptmp.Tasks.add(T);
            Projects.put(ProID, ptmp);
        }
        con.close();  
    }   
    catch(Exception e){ System.out.println(e);}  
    }
    public static void Save() throws SQLException, ClassNotFoundException
    {
                System.out.println("@@@@@@@");

        Class.forName("com.mysql.jdbc.Driver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:mysql://localhost:3306/isdb","root","12345678");    
        try{
        Statement stmt = con.createStatement();
        try{
        String dsql = "TRUNCATE TABLE depend";
        stmt.executeUpdate(dsql);
        dsql = "DELETE FROM tasks";
        stmt.executeUpdate(dsql);

        dsql = "DELETE FROM employ";
        stmt.executeUpdate(dsql);
        dsql = "DELETE FROM project";
        stmt.executeUpdate(dsql);
        }
     catch(Exception e){ System.out.println(e);}  
        for (int i=0;i<pIDs.size();i++)
        {
        String sql = "INSERT INTO project (ProID, Name,Start,Due) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1,pIDs.get(i));
        statement.setString(2,Projects.get(pIDs.get(i)).name);
        statement.setDate(3, Projects.get(pIDs.get(i)).start);
        System.out.println("ID: "+pIDs.get(i));
        System.out.println("Name: "+Projects.get(pIDs.get(i)).name);
        System.out.println("Start: "+Projects.get(pIDs.get(i)).start);
        System.out.println("due: "+Projects.get(pIDs.get(i)).due);
        statement.setDate(4, Projects.get(pIDs.get(i)).due);
        statement.executeUpdate();
        System.out.println("sssss");
        }
        for (int i=0;i<eIDs.size();i++)
        {
        String sql = "INSERT INTO employ (EmID, Name,Job,hrsday,NumofTasks) VALUES (?, ?, ?, ?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1,eIDs.get(i));
        statement.setString(2,Employes.get(eIDs.get(i)).Name);
        statement.setString(3, Employes.get(eIDs.get(i)).role);
        statement.setInt(4, Employes.get(eIDs.get(i)).hrs);
        statement.setInt(5, Employes.get(eIDs.get(i)).NumofTasks);
        statement.executeUpdate();
        System.out.println("aAAAAAAAAAA");
        }
        for (int i=0;i<pIDs.size();i++)
        {
            for (int j=0; j<Projects.get(pIDs.get(i)).Tasks.size();j++)
            {
        String sql = "INSERT INTO tasks (TaskID,TName,ProID,EmID,Duration,Parent) VALUES (?, ?, ?, ? ,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1,Projects.get(pIDs.get(i)).Tasks.get(j).TaskID);
        statement.setString(2,Projects.get(pIDs.get(i)).Tasks.get(j).TName);
        statement.setInt(3,pIDs.get(i));
        statement.setInt(4, Projects.get(pIDs.get(i)).Tasks.get(j).Emp.EmID);
        statement.setFloat(5, Projects.get(pIDs.get(i)).Tasks.get(j).Duration);
        statement.setInt(6, Projects.get(pIDs.get(i)).Tasks.get(j).parentID);
        statement.executeUpdate();
        System.out.println("EEEEEEEEEEEE");
        for (int k=0; k<Projects.get(pIDs.get(i)).Tasks.get(j).Depend.size(); k++)
        {
        sql = "INSERT INTO depend (TaskID,dTaskID) VALUES (?,?)";
        statement = con.prepareStatement(sql);
        statement.setInt(1,Projects.get(pIDs.get(i)).Tasks.get(i).TaskID);
        statement.setInt(2,Projects.get(pIDs.get(i)).Tasks.get(j).Depend.get(k).TaskID);
        statement.executeUpdate();
        System.out.println("VVVVVVVVV");
        }
            }
        }
        }
    catch(Exception e){ System.out.println(e);}    
    }
        }
