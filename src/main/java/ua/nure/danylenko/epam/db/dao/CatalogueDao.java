package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.db.entity.Category;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import java.sql.*;
import java.util.*;

/**
 * The OrderItem class provides fields and methods for making orders of bying purchases by customer
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class CatalogueDao implements IDao {

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private Connection getConnection() throws DBException {
        return ConnectionFactory.getInstance().getConnection();
    }

    private static final String SQL_FIND_FULL_CATALOGUE = "SELECT * FROM catalogue";
    private static final String SQL_FIND_CATEGORIES_BY_ID = "SELECT * FROM categories WHERE catalogue_id=?";
    private static final String SQL_ADD_NEW_CATALOGUE_ITEM = "INSERT INTO armadiodb.catalogue (id, name) values (DEFAULT,?)";
    private static final String SQL_REMOVE_CATALOGUE_ITEM = "DELETE FROM armadiodb.catalogue WHERE name=?;";
    private static final String SQL_RENAME_CATALOGUE_ITEM = "UPDATE armadiodb.catalogue SET name=? WHERE id=?;";
    private static final String SQL_ADD_NEW_CATEGORY = "INSERT INTO armadiodb.categories (id, catalogue_id, name) values (DEFAULT,?,?)";
    private static final String SQL_REMOVE_CATEGORY = "DELETE FROM armadiodb.categories WHERE catalogue_id=? AND name=?";
    private static final String SQL_REMOVE_CATEGORY_BY_CATALOGUE_ID = "DELETE FROM armadiodb.categories WHERE catalogue_id=?";
    private static final String SQL_RENAME_CATEGORY = "UPDATE armadiodb.categories SET name=? WHERE catalogue_id=? AND name=?";
    private static final String SQL_GET_CATALOGUE_ID_BY_NAME = "SELECT id FROM armadiodb.catalogue WHERE name=? ";
    private static final String SQL_GET_CATEGORY_ID_BY_CATALOGUE_ID_AND_CATEGORY = "SELECT id FROM armadiodb.categories WHERE catalogue_id=? AND name=?";
    private static final String SQL_REMOVE_ITEM_BY_ID = "DELETE FROM armadiodb.items WHERE id=?";
    private static final String SQL_REMOVE_PRODUCT_BY_ITEM_ID = "DELETE FROM armadiodb.products WHERE name=?;";

    @Override
    public void create(Object entity) {

    }

    @Override
    public Object read() throws DBException {
        Catalogue catalogue = new Catalogue();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_FULL_CATALOGUE);
            while (rs.next()) {
                int id=rs.getInt(Fields.ENTITY_ID);
                String name = rs.getString(Fields.CATEGORY_NAME);
                catalogue.addInCatalogue(name,getCategories(con, id));
            }
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            ConnectionFactory.close(con, stmt, rs);
        }
        catalogue.setContainer(sortByValues(catalogue.getContainer()));
        return catalogue;
    }

    private List<Category> getCategories(Connection con, int id) throws DBException, SQLException {
        List<Category> clothes = new ArrayList<>();

        ResultSet rs = null;
        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_CATEGORIES_BY_ID)){
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                clothes.add(extractCategory(rs));
            }
        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            if(rs!=null){
                rs.close();
            }
        }

        return clothes;
    }

    public void addItem(String itemName) {

        Connection con = null;
        PreparedStatement  ps = null;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_ADD_NEW_CATALOGUE_ITEM);
            DB_LOG.info(SQL_ADD_NEW_CATALOGUE_ITEM);
            DB_LOG.info(itemName);
            ps.setString(1,itemName);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in addItem() for Catalogue", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error(e);
        } finally {
            ConnectionFactory.close(con, ps);
        }
    }



    public void removeCatalogueItem(String itemToDelete) {

        Connection con = null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        long catalogueId=0;
        try{
            /*code to delete when REcreate DB*/
            con = getConnection();
            ps = con.prepareStatement(SQL_GET_CATALOGUE_ID_BY_NAME);
            ps.setString(1,itemToDelete);
            rs=ps.executeQuery();
            while (rs.next()) {
                catalogueId=rs.getLong(Fields.ENTITY_ID);
            }

            ps = con.prepareStatement(SQL_REMOVE_CATEGORY_BY_CATALOGUE_ID);
            ps.setLong(1,catalogueId);
            ps.execute();
            /*code to delete when REcreate DB*/

            ps = con.prepareStatement(SQL_REMOVE_CATALOGUE_ITEM);
            ps.setString(1,itemToDelete);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in removeCatalogueItem() for Catalogue", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error("DBException in removeCatalogueItem() for Catalogue", e);
            DB_LOG.error("If you REcreated DB, delete here code coz it would done automatically", e);
            DB_LOG.error(e);
        } finally {
            ConnectionFactory.close(con, ps, rs);
        }
    }


    public void renameCatalogueItem(String oldName,String newName) {

        Connection con = null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        long catalogueId=0;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_GET_CATALOGUE_ID_BY_NAME);
            ps.setString(1,oldName);
            rs=ps.executeQuery();
            while (rs.next()) {
                catalogueId=rs.getLong(Fields.ENTITY_ID);
            }
            ps = con.prepareStatement(SQL_RENAME_CATALOGUE_ITEM);
            ps.setString(1,newName);
            ps.setLong(2,catalogueId);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in renameCatalogueItem() for Catalogue", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error("DBException in renameCatalogueItem() for Catalogue", e);
            DB_LOG.error(e);
        } finally {
            ConnectionFactory.close(con, ps);
        }
    }

    public void addCategory(String catalogue, String itemName) {

        Connection con = null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        long id=0;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_GET_CATALOGUE_ID_BY_NAME);
            ps.setString(1,catalogue);
            rs=ps.executeQuery();
            while (rs.next()) {
               id=rs.getInt(Fields.ENTITY_ID);
            }
            ps = con.prepareStatement(SQL_ADD_NEW_CATEGORY);
            ps.setLong(1,id);
            ps.setString(2,itemName);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in addCategory() for Catalogue", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error(e);
        } finally {
            ConnectionFactory.close(con, ps, rs);
        }
    }

    public void removeCategory(String catalogue, String itemToDelete) {

        Connection con = null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        long id=0;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_GET_CATALOGUE_ID_BY_NAME);
            ps.setString(1,catalogue);
            rs=ps.executeQuery();
            while (rs.next()) {
                id=rs.getInt(Fields.ENTITY_ID);
            }
            ps = con.prepareStatement(SQL_REMOVE_CATEGORY);
            ps.setLong(1,id);
            ps.setString(2,itemToDelete);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in removeCategory() for Catalogue", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error("DBException in removeCategory() for Catalogue", e);
            DB_LOG.error(e);
        } finally {
            ConnectionFactory.close(con, ps, rs);
        }
    }


    public void renameCategory(String catalogue, String oldName, String newName) {

        Connection con = null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        long id=0;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_GET_CATALOGUE_ID_BY_NAME);
            ps.setString(1,catalogue);
            rs=ps.executeQuery();
            while (rs.next()) {
                id=rs.getInt(Fields.ENTITY_ID);
            }
            ps = con.prepareStatement(SQL_RENAME_CATEGORY);
            ps.setString(1,newName);
            ps.setLong(2,id);
            ps.setString(3,oldName);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in renameCategory() for Catalogue", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error("DBException in renameCategory() for Catalogue", e);
            DB_LOG.error(e);
        } finally {
            ConnectionFactory.close(con, ps, rs);
        }
    }


    public void removeProduct(long idToDelete) {

        Connection con = null;
        PreparedStatement  ps = null;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_REMOVE_ITEM_BY_ID);
            ps.setLong(1,idToDelete);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in removeProduct() for Catalogue", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error("DBException in removeProduct() for Catalogue", e);
        } finally {
            ConnectionFactory.close(con, ps);
        }
    }

    public int getCategoryId(int catalogId, String categoryType){
        Connection con = null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        int categoryId=0;
        try {
            con = getConnection();
            ps = con.prepareStatement(SQL_GET_CATEGORY_ID_BY_CATALOGUE_ID_AND_CATEGORY);
            ps.setInt(1, catalogId);
            ps.setString(2, categoryType);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoryId = rs.getInt(Fields.ENTITY_ID);
            }
            con.commit();

        } catch (SQLException ex) {
        DB_LOG.error("SQLException in getCategoryId() CatalogueDao.java", ex);
        ConnectionFactory.rollback(con);
        } catch (DBException e) {
        DB_LOG.error("DBException in getCategoryId() CatalogueDao.java", e);
        DB_LOG.error(e);
        } finally {
        ConnectionFactory.close(con, ps, rs);
        }
        return categoryId;
    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(long id) {

    }

    private static Map<String,List<Category>> sortByValues(Map<String,List<Category>> map) {

        List<String> keys = new ArrayList<>(map.keySet());
        keys.sort(Collections.reverseOrder());
        // copying the sorted list in HashMap with LinkedHashMap to preserve the insertion order
        HashMap<String,List<Category>> sortedHashMap = new LinkedHashMap<>();
        for (String aList : keys) {
            sortedHashMap.put(aList, map.get(aList));
        }

        return sortedHashMap;
    }


    private static Category extractCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong(Fields.ENTITY_ID));
        category.setName(rs.getString(Fields.CATEGORY_NAME));
        category.setCatalogueId(rs.getInt(Fields.CATALOGUE_ID));
        return category;
    }
}
