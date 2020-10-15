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

    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    private Connection getConnection() throws DBException {

        return ConnectionFactory.getInstance().getConnection();
    }

    private static final String SQL_FIND_ITEMS_BY_CATEGORY = "SELECT * FROM items WHERE category_id=" +
            "(SELECT id FROM categories WHERE name=? and catalogue_id=?);";
    private static final String SQL_FIND_PRODUCTS_BY_ITEM =
            "SELECT * FROM products WHERE item_id=?";
    private static final String SQL_FIND_PRODUCTS_BY_ITEMID ="SELECT * FROM products WHERE item_id=?";
    private static final String SQL_FIND_MATERIALS_BY_ITEM_ID =
            "SELECT * FROM materials WHERE item_id=?";
    private static final String SQL_FIND_All_COLOURS = "SELECT colour FROM products";
    private static final String SQL_FIND_All_BRANDS = "SELECT brand FROM items";
    private static final String SQL_FIND_All_SIZES = "SELECT product_size FROM products";
    private static final String SQL_FIND_IMAGES_BY_PRODUCT_ID ="SELECT img_name FROM images WHERE product_id=?";
    private static final String SQL_CREATE_NEW_ITEM ="INSERT INTO armadiodb.items (id, product_name, price, release_date, brand, category_id) values (DEFAULT, ?, ?, ?, ?, ?)";
    private static final String SQL_CREATE_NEW_PRODUCT =
            "INSERT INTO armadiodb.products (id, product_name, available, product_size, colour, item_id) values (DEFAULT, ?, ?, ?, ?, ?)";
    private static final String SQL_ADD_NEW_PRODUCT_IMAGE = "INSERT INTO armadiodb.images (id, img_name, product_id) values (DEFAULT, ?, ?)";
    private static final String SQL_ADD_PRODUCT_MATERIAL ="INSERT INTO armadiodb.materials (id, material, percent, item_id) values (DEFAULT, ?, ?, ?)";


    @Override
    public void create(Object entity) {
        DB_LOG.info("create started");
        Item newItem = (Item)entity;
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_CREATE_NEW_ITEM);
            ps.setString(1,newItem.getProductName());
            ps.setDouble(2,newItem.getPrice());
            ps.setDate(3,Date.valueOf(newItem.getReleaseDate()));
            ps.setString(4,newItem.getBrand());
            ps.setInt(5,newItem.getCategoryId());
            ps.execute();

           // addMatherialsForItem(con, newItem.getMaterials());
            con.commit();
            // createNewProductsForItem(con, newItem.getContainer(), newItem.getId());
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao create() SQLException! Trouble with commit: ", ex);
        }catch (DBException dbex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao create() DBException! Trouble with connection: ", dbex);
        } finally {
            ConnectionFactory.close(con, ps);
        }
    }


    private void createNewProductsForItem(Connection con, List<Product>  products, Long itemId) {
        PreparedStatement prep = null;
        try {
            for(int i=0; i<products.size(); i++) {
                prep = con.prepareStatement(SQL_CREATE_NEW_PRODUCT);
                prep.setString(1, products.get(i).getName());
                prep.setInt(2, products.get(i).getAvailable());
                prep.setString(3, products.get(i).getBodySize().getName());
                prep.setString(4, products.get(i).getColour().getName());
                prep.setLong(5, itemId);
                prep.execute();
            }
            addNewProductImages(con, products);
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao createNewProductsForItem() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,prep);
        }finally {
            ConnectionFactory.close(prep);
        }
    }

    private void addNewProductImages(Connection con, List<Product>  products){
        PreparedStatement pStatement = null;
        try {
            for(int i=0; i<products.size(); i++) {
                pStatement = con.prepareStatement(SQL_ADD_NEW_PRODUCT_IMAGE);
                pStatement.setString(1, products.get(i).getImages().get(0));
                pStatement.setLong(2, products.get(i).getId());
                pStatement.execute();
            }
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao addNewProductImages() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,pStatement);
        }finally {
            ConnectionFactory.close(pStatement);
        }
    }

    private void addMatherialsForItem(Connection con, List<Material> materials){
        PreparedStatement ps = null;
        try {
            for(int i=0; i<materials.size(); i++) {
                ps = con.prepareStatement(SQL_ADD_PRODUCT_MATERIAL);
                ps.setString(1, materials.get(i).getName());
                ps.setLong(2, materials.get(i).getPercent());
                ps.setLong(3, materials.get(i).getItemId());
                ps.execute();
            }
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao addMatherialsForItem() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,ps);
        }finally {
            ConnectionFactory.close(ps);
        }
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
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_ITEMS_BY_CATEGORY);
            pstmt.setString(1,categoryName);
            pstmt.setInt(2,catalogId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Item item = extractItem(rs);
                getMaterialsByItem(con,item);
                items.add(item);

            }

            getProductsByItem(con, items);
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("aaaaaaaaba", ex);
            throw new DBException("aaaaaaaaba", ex);
        }catch (DBException dbex) {
            DB_LOG.error("aaaaaaaab", dbex);
            throw new DBException("aaaaaaaab", dbex);
        } finally {
            ConnectionFactory.close(con, pstmt, rs);
        }

        return items;
    }



//    public List<Product> getProductsByItemId(int itemId) throws DBException {
//        List <Product> products = new ArrayList<>();
//        ResultSet rs = null;
//        Connection con = null;
//
//        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_PRODUCTS_BY_ITEMID)){
//            con = getConnection();
//            pstmt.setLong(1, itemId);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Product product = extractProduct(rs);
//                //getMaterialsByProduct(con, product);
//                products.add(product);
//            }
//
//        }catch (SQLException ex) {
//            ConnectionFactory.rollback(con);
//            DB_LOG.error("bbbbbbbb", ex);
//            throw new DBException("bbbbbbbb", ex);
//        }catch (DBException dbex) {
//            DB_LOG.error("bbbbbbbbc", dbex);
//            throw new DBException("bbbbbbbbc", dbex);
//        }finally {
//            ConnectionFactory.close(rs);
//        }
//        return products;
//    }

    private void getProductsByItem(Connection con, List<Item> items) throws DBException {
        ResultSet rs = null;
        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_PRODUCTS_BY_ITEM)){
            for (Item item : items) {
                pstmt.setLong(1, item.getId());
                rs = pstmt.executeQuery();
                while (rs.next()) {

                    Product product = extractProduct(rs);
                    item.getContainer().add(product);
                   // getMaterialsByProduct(con, product);
                    getProductImages(con,product);
                }
            }
        }catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("getProductsByItem", ex);
            throw new DBException("getProductsByItem", ex);
        }catch (DBException dbex) {
            DB_LOG.error("getProductsByItem", dbex);
            throw new DBException("getProductsByItem", dbex);
        }finally {
            ConnectionFactory.close(rs);
        }
    }

    private void getProductImages(Connection con, Product product)throws DBException {
            ResultSet rs = null;
            try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_IMAGES_BY_PRODUCT_ID)) {
                pstmt.setLong(1, product.getId());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    product.getImages().add(rs.getString(Fields.IMAGE_NAME));
                }
            }catch (SQLException ex) {
                ConnectionFactory.rollback(con);
                DB_LOG.error("getProductImages() failed", ex);
                throw new DBException("getProductImages() failed", ex);
            }finally {
                ConnectionFactory.close(rs);
            }
    }

    private void getMaterialsByItem(Connection con, Item item) throws DBException {
        ResultSet rs = null;
        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_MATERIALS_BY_ITEM_ID)) {
            pstmt.setLong(1, item.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                item.getMaterials().add(extractMaterial(rs));
            }
        }catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("getMaterialsByItem() failed: ", ex);
            throw new DBException("getMaterialsByItem() failed: ", ex);
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
            con = getConnection();
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
        Date d = rs.getDate(Fields.ITEM_RELEASE_DATE);
        item.setReleaseDate(d.toLocalDate());
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
