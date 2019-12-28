/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author gry
 */
public class dataDAO {
    
    public static void insertData(data d) throws SQLException
    {
        Connection conn=dbUtil.getConnection();
        
        String sql="INSERT INTO data(datetime,deviceid,devicename,slaveaddr,slaveindex,id,value) VALUES(now(),?,?,?,?,?,?);";
//        System.out.println("Connection: "+conn);
        PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql);
        ps.setString(1,d.getDeviceId());
        ps.setString(2,d.getDeviceName());
        ps.setString(3,d.getSlaveAddr());
        ps.setString(4,d.getSlaveIndex());
        ps.setString(5,d.getId());
        ps.setDouble(6,d.getValue());
        ps.execute();
        
    }
    
}
