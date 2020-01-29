package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.db.entity.Material;
import ua.nure.danylenko.epam.db.entity.Product;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ItemsDao implements IDao {
    private ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger("jdbc");

    private Connection createConnection() throws DBException {
        this.connectionFactory = new ConnectionFactory();
        return ConnectionFactory.getInstance().getConnection();
    }

    private static final String SQL_FIND_ITEMS_BY_CATEGORY = "SELECT * FROM items WHERE category_id=" +
            "(SELECT id FROM categories WHERE name=? and catalogue_id=?);";
    private static final String SQL_FIND_PRODUCTS_BY_ITEM =
            "SELECT * FROM products WHERE item_id=?";
    private static final String SQL_FIND_PRODUCTS_BY_ITEMID =
            "SELECT * FROM products WHERE item_id=?";
    private static final String SQL_FIND_MATERIALS_BY_PRODUCT =
            "SELECT * FROM materials WHERE product_id=?";
    private static final String SQL_FIND_All_COLOURS = "SELECT colour FROM products";
    private static final String SQL_FIND_All_BRANDS = "SELECT brand FROM items";
    private static final String SQL_FIND_All_SIZES = "SELECT product_size FROM products";


    @Override
    public void create(Object entity) {

    }

    @Override
    public Object read() throws DBException {

        return null;
    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(long id) {

    }

    public List<Item> getItemsByCategory(String categoryName, int catalogId) throws DBException {
        List <Item> items = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection con = null;
        try{
            con = this.createConnection();
            pstmt = con.prepareStatement(SQL_FIND_ITEMS_BY_CATEGORY);
            pstmt.setString(1,categoryName);
            pstmt.setInt(2,catalogId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                items.add(extractItem(rs));

            }
            getProductsByItem(con, items);
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            LOG.error("aaaaaaaaba", ex);
            throw new DBException("aaaaaaaaba", ex);
        }catch (DBException dbex) {
            LOG.error("aaaaaaaab", dbex);
            throw new DBException("aaaaaaaab", dbex);
        } finally {
            ConnectionFactory.close(con, pstmt, rs);
        }

        return items;
    }



    public List<Product> getProductsByItemId(int itemId) throws DBException {
        List <Product> products = new ArrayList<>();
        ResultSet rs = null;
        Connection con = null;

        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_PRODUCTS_BY_ITEMID)){
            con = this.createConnection();
            pstmt.setLong(1, itemId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = extractProduct(rs);
                getMaterialsByProduct(con, product);
                products.add(product);
            }

        }catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            LOG.error("bbbbbbbb", ex);
            throw new DBException("bbbbbbbb", ex);
        }catch (DBException dbex) {
            LOG.error("bbbbbbbbc", dbex);
            throw new DBException("bbbbbbbbc", dbex);
        }finally {
            ConnectionFactory.close(rs);
        }
        return products;
    }

    private void getProductsByItem(Connection con, List<Item> items) throws DBException {
        ResultSet rs = null;
        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_PRODUCTS_BY_ITEM)){
            for (Item item : items) {
                pstmt.setLong(1, item.getId());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Product product = extractProduct(rs);
                    item.getContainer().add(product);
                    getMaterialsByProduct(con, product);
                }
            }
        }catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            LOG.error("bbbbbbbb", ex);
            throw new DBException("bbbbbbbb", ex);
        }catch (DBException dbex) {
            LOG.error("bbbbbbbbc", dbex);
            throw new DBException("bbbbbbbbc", dbex);
        }finally {
            ConnectionFactory.close(rs);
        }
    }

    private void getMaterialsByProduct(Connection con, Product product) throws DBException {
        ResultSet rs = null;
        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_MATERIALS_BY_PRODUCT)) {
            pstmt.setLong(1, product.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                product.getMaterials().add(extractMaterial(rs));
            }
        }catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            LOG.error("cccccc", ex);
            throw new DBException("cccccc", ex);
        }finally {
            ConnectionFactory.close(rs);
        }
    }


    public Map<String,List> getSizesColoursBrands() throws DBException {
        List<String> colours = new ArrayList<>();
        List<String> sizes = new ArrayList<>();
        List<String> brands = new ArrayList<>();
        Map<String,List> result = new TreeMap<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = this.createConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_All_COLOURS);
            while (rs.next()) {
                String colour = rs.getString(Fields.PRODUCT_COLOUR);
                colours.add(colour);
            }
            result.put("colours",colours);
            rs = stmt.executeQuery(SQL_FIND_All_BRANDS);
            while (rs.next()) {
                String brand = rs.getString(Fields.ITEM_BRAND);
                brands.add(brand);
            }
            result.put("brands",brands);
            rs = stmt.executeQuery(SQL_FIND_All_SIZES);
            while (rs.next()) {
                String size = rs.getString(Fields.PRODUCT_SIZE);
                sizes.add(size);
            }
            result.put("sizes",sizes);
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            ConnectionFactory.close(con, stmt, rs);
        }

        return result;
    }

    public List<Product> getCategorySizes(String categoryType, int catalogId){
        return null;
    }

    public List<Product> getCategoryBrands(String categoryType, int catalogId){
        return null;
    }

    private static Item extractItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getLong(Fields.ENTITY_ID));
        item.setProductName(rs.getString(Fields.ITEM_NAME));
        item.setPrice(rs.getDouble(Fields.ITEM_PRICE));
        item.setReleaseDate(rs.getDate(Fields.ITEM_RELEASE_DATE));
        item.setBrand(rs.getString(Fields.ITEM_BRAND));
        item.setCategoryId(rs.getInt(Fields.ITEM_CATEGORY_ID));
        return item;
    }

    private static Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong(Fields.ENTITY_ID));
        product.setName(rs.getString(Fields.PRODUCT_NAME));
        product.setAvailable(rs.getInt(Fields.PRODUCT_AVAILABLE));
        product.extractSizeValue(rs.getString(Fields.PRODUCT_SIZE));
        product.extractColourValue(rs.getString(Fields.PRODUCT_COLOUR));

        return product;
    }
    private static Material extractMaterial(ResultSet rs) throws SQLException {
        Material material = new Material();
        material.setId(rs.getLong(Fields.ENTITY_ID));
        material.setName(rs.getString(Fields.MAERIAL_NAME));
        material.setPercent(rs.getInt(Fields.MAERIAL_PERCENT));
        return material;
    }
}
