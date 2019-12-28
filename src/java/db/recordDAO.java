/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author gry
 */
public class recordDAO {
    
    public static void insertRecord(record r) throws SQLException
    {
        Connection conn=dbUtil.getConnection();
        
        String sql="INSERT INTO record(rdatetime,rdata,rdatatype,rdeviceid) VALUES(now(),?,?,?);";
//        System.out.println("Connection: "+conn);
        PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql);
        ps.setString(1,r.getData());
        ps.setInt(2,r.getDataType());
        ps.setString(3,r.getDeviceId());
        ps.execute();
        
    }
        
}
