package ua.nure.danylenko.epam.db.dao;

import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.db.entity.Category;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import java.sql.*;
import java.util.*;

public class CatalogueDao implements IDao {

    private ConnectionFactory connectionFactory;

    private Connection createConnection() throws DBException {
        connectionFactory = new ConnectionFactory();
        return ConnectionFactory.getInstance().getConnection();
    }

    private static final String SQL_FIND_FULL_CATALOGUE = "SELECT * FROM catalogue";
    private static final String SQL_FIND_CATEGORIES_BY_ID = "SELECT * FROM categories WHERE catalogue_id=?";



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
            con = this.createConnection();
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
