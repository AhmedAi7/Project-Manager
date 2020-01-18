
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahmed
 */
public class Task {
    int TaskID;
    String TName;
    int ProID;
    float Duration;
    Employ Emp;
    int parentID;
    ArrayList <Task> Depend;
    Task()
    {
        Depend = new ArrayList<>();    
    }
}