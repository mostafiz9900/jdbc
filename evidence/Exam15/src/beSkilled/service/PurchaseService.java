package beSkilled.service;

import beSkilled.domain.Purchase;
import beSkilled.domain.Summary;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseService {

    static Connection con = null;

    public static void insert(Purchase purchase) {
        String sql = "insert into purchase(id , name, code, qty, uPrice, tPrice) values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, purchase.getId());
            ps.setString(2, purchase.getName());
            ps.setString(3, purchase.getCode());
            ps.setInt(4, purchase.getQty());
            ps.setDouble(5, purchase.getuPrice());
            ps.setDouble(6, purchase.gettPrice());
            ps.executeUpdate();
            System.out.println("insert data purchase");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Purchase getPurchaseByProductCode(String code) {
        Purchase purchase = new Purchase();
        String sql = "select * from purchase where code=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                purchase.setId(rs.getInt(1));
                purchase.setName(rs.getString(2));
                purchase.setCode(rs.getString(3));
                purchase.setQty(rs.getInt(4));
                purchase.setuPrice(rs.getDouble(5));
                purchase.settPrice(rs.getDouble(6));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchase;
        
    }
    
    public static List<Purchase> getListPurchase() {
        List<Purchase> list = new ArrayList<>();
        String sql = "select * from purchase";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Purchase pur = new Purchase();
                pur.setId(rs.getInt(1));
                pur.setName(rs.getString(2));
                pur.setCode(rs.getString(3));
                pur.setQty(rs.getInt(4));
                pur.setuPrice(rs.getDouble(5));
                pur.settPrice(rs.getDouble(6));
                list.add(pur);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
        
    }

    public static void insertMain(Purchase purchase) {
        if (purchase != null) {
            insert(purchase);
            Purchase p = getPurchaseByProductCode(purchase.getCode());
            try {
                Summary summary = SummaryService.getSummaryByProductCode(purchase.getCode());
                if (purchase.getCode().equalsIgnoreCase(summary.getCode())) {
                    int tqty = summary.getTotalQty() + purchase.getQty();
                    int aqty = summary.getAvailableQty() + purchase.getQty();
                    summary.setTotalQty(aqty);
                    summary.setAvailableQty(aqty);
                    
                }
                SummaryService.update(summary);
            } catch (Exception e) {
                Summary summary2 = new Summary(purchase.getId(), purchase.getName(), purchase.getCode(), purchase.getQty(), 0, purchase.getQty(), p);
                SummaryService.insert(summary2);
            }
            
        }
    }
    
}
