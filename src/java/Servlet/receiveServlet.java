/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;
import db.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gry
 */
@WebServlet(name = "receiveServlet", urlPatterns = {"/receiveServlet"})
public class receiveServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
            //ContentType与数据推送格式一致
            response.setContentType("application/json");
            /* TODO output your page here. You may use following sample code. */
            
//            System.out.println("ReceiveServlet is running!");
            PrintWriter out=response.getWriter();
            
            //读取数据
            BufferedReader br = request.getReader();
            String str, wholeStr = "";
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
            
            System.out.println("接受的原始数据："+wholeStr);
            
            //解析Json数据
            JSONObject jo=new JSONObject(wholeStr);
            
           
            int dataType=jo.getInt("dataType");
            String deviceId="";
            String data="";
            
            if(dataType==1)
		{
                        
			System.out.println("数据是原始数据流！");
                        //若dataType为0，则无deviceId
                        data=jo.getString("data");
                        deviceId=jo.getString("deviceId");
			System.out.println("data: "+data);
                        System.out.println("dataType: "+dataType);
                        System.out.println("deviceId: "+deviceId);
		}
            else if(dataType==0)
                {
                    System.out.println("数据是测试数据！");
                    data=jo.getString("data");
                    System.out.println("data: "+data);
                    System.out.println("dataType: "+dataType);
                }
            else if(dataType==2)
		{
			System.out.println("数据是设备上下线消息！");
                        data=jo.getString("data");
                        deviceId=jo.getString("deviceId");
			System.out.println("data: "+data);
                        System.out.println("dataType: "+dataType);
                        System.out.println("deviceId: "+deviceId);
			if(data.contains("0"))
			{
				System.out.println("设备已上线！");
			}
			else
			{
				System.out.println("设备已下线！");
			}
		}
            else if(dataType==3)
		{
			System.out.println("数据是Json数据流！");
			JSONObject dataObject=jo.getJSONObject("data");
                       
                        String deviceid=dataObject.getString("deviceId");
                        System.out.println("deviceid: "+deviceid);
                        String deviceName=dataObject.getString("deviceName");
                        System.out.println("devicename: "+deviceName);
                        String slaveAddr=dataObject.getString("slaveAddr");
                        System.out.println("slaveAddr: "+slaveAddr);
                        String slaveIndex=dataObject.getString("slaveIndex");
                        System.out.println("slaveIndex: "+slaveIndex);
                        JSONObject values=dataObject.getJSONObject("values");
                        System.out.println("values: "+values.toString());
                        String id="68804";
                        double value=values.getDouble(id);
                        System.out.println("value: "+value);
                         
                        data d=new data();
                        d.setDeviceId(deviceid);
                        d.setDeviceName(deviceName);
                        d.setSlaveAddr(slaveAddr);
                        d.setSlaveIndex(slaveIndex);
                        d.setId(id);
                        d.setValue(value);
                        
                        System.out.println("dvalue: "+d.getValue());
                        
                        dataDAO.insertData(d);
                        
		}
            else
		{
			System.out.println("出现未知的dataType！");
		}
	
            if(dataType!=3)
            {
                record r=new record();
                r.setData(data);
                r.setDataType(dataType);
                r.setDeviceId(deviceId);
                recordDAO.insertRecord(r);
            }

    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(receiveServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(receiveServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
