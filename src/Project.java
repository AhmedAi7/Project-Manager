
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahmed
 */
public class Project {
int ProID;
String name;
java.sql.Date start,due;
ArrayList <Task> Tasks;
Project()
    {
        Tasks = new ArrayList<>();
    }

}
