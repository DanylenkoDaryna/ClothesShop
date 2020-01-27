package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.db.entity.Material;
import ua.nure.danylenko.epam.db.entity.Product;
import ua.nure.danylenko.epam.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
