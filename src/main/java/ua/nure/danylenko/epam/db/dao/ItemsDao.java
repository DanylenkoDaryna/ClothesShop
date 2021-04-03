package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.db.entity.Material;
import ua.nure.danylenko.epam.db.entity.OrderItem;
import ua.nure.danylenko.epam.db.entity.Product;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * The ItemsDao class implements IDao provides requests to db for manipulating with Items and products,
 * images and materials
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class ItemsDao implements IDao {

    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    private Connection getConnection() throws DBException {

        return ConnectionFactory.getInstance().getConnection();
    }

    private static final String SQL_FIND_ITEMS_BY_CATEGORY = "SELECT * FROM items WHERE category_id=(SELECT id FROM categories WHERE name=? and catalogue_id=?);";
    private static final String SQL_GET_ITEM_BY_ID = "SELECT * FROM items WHERE id=?;";
    private static final String SQL_FIND_PRODUCTS_BY_ITEM ="SELECT * FROM products WHERE item_id=?";
    private static final String SQL_FIND_MATERIALS_BY_ITEM_ID="SELECT * FROM materials WHERE item_id=?";
    private static final String SQL_FIND_PRODUCT_BY_ID ="SELECT * FROM products WHERE id=?";
    private static final String SQL_FIND_ITEM_ID_BY_PRODUCT_ID ="SELECT item_id FROM products WHERE id=?";
    private static final String SQL_FIND_All_AVAILABLE_COLOURS = "SELECT colour FROM items";
    private static final String SQL_FIND_All_BRANDS = "SELECT brand FROM items";
    private static final String SQL_FIND_All_SIZES = "SELECT product_size FROM products";
    private static final String SQL_GET_All_DB_COLOURS = "SELECT * FROM colours";
    private static final String SQL_FIND_IMAGE_BY_PRODUCT_ID ="SELECT img_name FROM images WHERE product_id=?";
    private static final String SQL_CREATE_NEW_ITEM ="INSERT INTO armadiodb.items (id, item_name, price, release_date, brand, colour, category_id) values (DEFAULT, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_CREATE_NEW_PRODUCT ="INSERT INTO armadiodb.products (id, product_name, available, product_size, item_id) values (DEFAULT, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PRODUCT_AMOUNT ="UPDATE armadiodb.products SET available=? WHERE  id=?";
    private static final String SQL_ADD_NEW_PRODUCT_IMAGE = "INSERT INTO armadiodb.images (id, img_name, product_id) values (DEFAULT, ?, ?)";
    private static final String SQL_ADD_PRODUCT_MATERIAL ="INSERT INTO armadiodb.materials (id, material, percent, item_id) values (DEFAULT, ?, ?, ?)";
    private static final String SQL_DELETE_PRODUCT_BY_ID ="DELETE FROM armadiodb.products WHERE  id=?";
    private static final String SQL_DELETE_ITEM_BY_ID ="DELETE FROM armadiodb.items WHERE id=?";
    private static final String SQL_DELETE_MATERIALS_BY_ITEM_ID ="DELETE FROM armadiodb.materials WHERE item_id=?";


    @Override
    public void create(Object entity) {
        DB_LOG.info("create started");
        Item newItem = (Item)entity;
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            ps = con.prepareStatement(SQL_CREATE_NEW_ITEM);
            ps.setString(1,newItem.getItemName());
            ps.setDouble(2,newItem.getPrice());
            ps.setDate(3,Date.valueOf(newItem.getReleaseDate()));
            ps.setString(4,newItem.getBrand());
            ps.setString(5,newItem.getColour().toString());
            ps.setInt(6,newItem.getCategoryId());
            ps.execute();

            stmt=con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id()");
            if (rs.next()){
                long itemId=rs.getLong(1);
                newItem.setId(itemId);
                DB_LOG.info("Item id = " + itemId);
                addMatherialsForItem(con, newItem.getMaterials(), itemId);
                createNewProductsForItem(con, newItem.getContainer(), itemId);
            } else{
                DB_LOG.info("Error getting Item id!");
            }

            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao create() SQLException! Trouble with commit: ", ex);
            DB_LOG.error(Messages.ERR_CANNOT_CREATE_ITEM);
        }catch (DBException dbex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao create() DBException! Trouble with connection: ", dbex);
            DB_LOG.error(Messages.ERR_CANNOT_CREATE_ITEM);
        } finally {
            ConnectionFactory.close(con, ps, rs);
            ConnectionFactory.close(stmt);
        }
    }


    private void createNewProductsForItem(Connection con, List<Product>  products, Long itemId) {
        DB_LOG.info("createNewProductsForItem starts");
        PreparedStatement prep = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            for (Product product : products) {
                prep = con.prepareStatement(SQL_CREATE_NEW_PRODUCT);
                prep.setString(1, product.getName());
                DB_LOG.info(product.getName());
                prep.setInt(2, product.getAvailable());
                DB_LOG.info(product.getAvailable());
                prep.setString(3, product.getBodySize().toString());
                DB_LOG.info(product.getBodySize().toString());
                prep.setLong(4, itemId);
                DB_LOG.info(itemId);
                prep.execute();

                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT last_insert_id()");
                if (rs.next()) {
                    product.setId(rs.getLong(1));
                    DB_LOG.info("Product id = " + rs.getLong(1));
                } else {
                    DB_LOG.info("Error getting Item id!");
                }
            }

            addNewProductImages(con, products);
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao createNewProductsForItem() SQLException! Trouble with commit: ", ex);
            DB_LOG.error(Messages.ERR_CANNOT_CREATE_PRODUCT);
            ConnectionFactory.close(con,prep);
        }finally {
            ConnectionFactory.close(prep);
            ConnectionFactory.close(rs);
            ConnectionFactory.close(stmt);
        }
    }

    private void addNewProductImages(Connection con, List<Product>  products){
        PreparedStatement pStatement = null;
        try {
            for(int i=0; i<products.size(); i++) {
                pStatement = con.prepareStatement(SQL_ADD_NEW_PRODUCT_IMAGE);
                pStatement.setString(1, products.get(i).getImage());
                pStatement.setLong(2, products.get(i).getId());
                pStatement.execute();
            }
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In ItemsDao addNewProductImages() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,pStatement);
        }finally {
            ConnectionFactory.close(pStatement);
        }
    }

    private void addMatherialsForItem(Connection con, List<Material> materials, long itemId){
        PreparedStatement ps = null;
        try {
            for(int i=0; i<materials.size(); i++) {
                ps = con.prepareStatement(SQL_ADD_PRODUCT_MATERIAL);
                ps.setString(1, materials.get(i).getName());
                ps.setInt(2, materials.get(i).getPercent());
                ps.setLong(3, itemId);
                materials.get(i).setItemId(itemId);
                ps.execute();
            }
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

    public void deleteProducts(List<OrderItem> orderItems){
        DB_LOG.info("deleteProducts() starts");

        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try{
            con = getConnection();

            for(OrderItem orderItem: orderItems){

                Product product = getProductById(con,orderItem.getProductId());
                if(orderItem.getAmount() < product.getAvailable()){
                    int availableAmount = product.getAvailable();
                    int orderAmount = orderItem.getAmount();
                    product.setAvailable(availableAmount-orderAmount);
                    updateProductAmount(con, product, product.getAvailable());
                }else if(orderItem.getAmount()== product.getAvailable()){
                    long itemId = -1;
                    pst = con.prepareStatement(SQL_FIND_ITEM_ID_BY_PRODUCT_ID);
                    pst.setLong(1,product.getId());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        itemId=rs.getLong(1);
                        DB_LOG.info("itemId="+itemId);
                    }
                    Item item = getItemById(con, itemId);
                    item.getContainer().remove(product);
                    if(item.getContainer().isEmpty()){
                        deleteItem(con, itemId);
                        deleteMaterials(con, itemId);
                    }
                    deleteProduct(con, product);
                }else{
                    DB_LOG.error("error in deleteProducts()");
                }
            }

            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("SQLException - trouble with commit in deleteProducts()", ex);
        }catch (DBException dbx) {
            DB_LOG.error("DBException - trouble with connection in deleteProducts()", dbx);
        } finally {
            ConnectionFactory.close(con, pst, rs);
        }
        DB_LOG.info("deleteProducts() ends");
    }

    private void updateProductAmount(Connection con, Product product, int updatedAmount){

        PreparedStatement ps = null;
        try {
                ps = con.prepareStatement(SQL_UPDATE_PRODUCT_AMOUNT);
                ps.setInt(1, updatedAmount);
                ps.setLong(2, product.getId());
                ps.execute();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In updateProductAmount() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,ps);
        }finally {
            ConnectionFactory.close(ps);
        }

    }

    private void deleteMaterials(Connection con, long itemId){

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_DELETE_MATERIALS_BY_ITEM_ID);
            ps.setLong(1, itemId);
            ps.execute();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In deleteMaterials() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,ps);
        }finally {
            ConnectionFactory.close(ps);
        }

    }

    private void deleteProduct(Connection con, Product product){

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_DELETE_PRODUCT_BY_ID);
            ps.setLong(1, product.getId());
            ps.execute();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In deleteProduct() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,ps);
        }finally {
            ConnectionFactory.close(ps);
        }

    }

    private Item getItemById(Connection con, long itemId){

        Item item = new Item();
        PreparedStatement ps = null;
        ResultSet rs=null;
        try {
                ps = con.prepareStatement(SQL_GET_ITEM_BY_ID);
                ps.setLong(1, itemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                item = extractItem(rs);
                }
        } catch (SQLException ex) {

            ConnectionFactory.rollback(con);
            DB_LOG.error("In getItemById() SQLException! Trouble with commit -> ", ex);
            ConnectionFactory.close(con, ps, rs);
        }finally {

            ConnectionFactory.close(ps);
            ConnectionFactory.close(rs);
        }

        return item;
    }

    private void deleteItem(Connection con, long itemId){

        PreparedStatement ps = null;
        try {
                ps = con.prepareStatement(SQL_DELETE_ITEM_BY_ID);
                ps.setLong(1, itemId);
                ps.execute();
        } catch (SQLException ex) {

            ConnectionFactory.rollback(con);
            DB_LOG.error("In deleteItem() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,ps);
        }finally {

            ConnectionFactory.close(ps);
        }

    }


    public List<Item> getItemsByCategory(String categoryName, int catalogId) throws DBException {
        DB_LOG.info("getItemsByCategory() started! ");
        List <Item> items = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection con = null;
        try{
            con = getConnection();
            pst = con.prepareStatement(SQL_FIND_ITEMS_BY_CATEGORY);
            pst.setString(1,categoryName);
            pst.setInt(2,catalogId);
            rs = pst.executeQuery();
            while (rs.next()) {
                Item item = extractItem(rs);
                getMaterialsByItem(con,item);
                items.add(item);

            }

            getProductsByItem(con, items);
            con.commit();

        } catch (SQLException ex) {

            ConnectionFactory.rollback(con);
            DB_LOG.error("in getItemsByCategory()", ex);
            throw new DBException("in getItemsByCategory()", ex);
        }catch (DBException dbex) {

            DB_LOG.error("in getItemsByCategory()", dbex);
            throw new DBException("in getItemsByCategory()", dbex);
        } finally {

            ConnectionFactory.close(con, pst, rs);
        }

        return items;
    }

    /**
     * Method that gets particular products from db
     * @param con Connection
     * @param items List<Item>
     * @throws DBException if trouble with connection
     */
    private void getProductsByItem(Connection con, List<Item> items) throws DBException {

        DB_LOG.info("getProductsByItem() started! ");
        ResultSet rs = null;
        try(PreparedStatement pst = con.prepareStatement(SQL_FIND_PRODUCTS_BY_ITEM)){
            for (Item item : items) {
                pst.setLong(1, item.getId());
                rs = pst.executeQuery();
                while (rs.next()) {

                    Product product = extractProduct(rs);
                    item.getContainer().add(product);
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


    /**
     * Method that gets particular product from db
     * @param con Connection
     * @param productId id of Product
     * @return new Product
     * @throws DBException if trouble with connection
     */
    private Product getProductById(Connection con, long productId) throws DBException {

        DB_LOG.info("getProductById() started! ");
        Product product = new Product();
        ResultSet rs = null;
        try(PreparedStatement pst = con.prepareStatement(SQL_FIND_PRODUCT_BY_ID)){
            pst.setLong(1, productId);
            rs = pst.executeQuery();
            while (rs.next()) {
                product = extractProduct(rs);
            }
            product=getProductImages(con,product);

        }catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("SQLException - trouble with commit in getProductById()", ex);
        }catch (DBException dbex) {
            DB_LOG.error("DBException - trouble with connection in getProductById()", dbex);
        }finally {
            ConnectionFactory.close(rs);
        }
        return product;
    }


    /**
     * Method that gets particular image of particular product
     * @param con Connection
     * @param product Product
     * @return product
     * @throws DBException if trouble with connection
     */
    private Product getProductImages(Connection con, Product product)throws DBException {
       // DB_LOG.info("getProductImages() started!");

        ResultSet rs = null;
            try(PreparedStatement ps = con.prepareStatement(SQL_FIND_IMAGE_BY_PRODUCT_ID)) {
                ps.setLong(1, product.getId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    product.setImage(rs.getString(Fields.IMAGE_NAME));
                }
            }catch (SQLException ex) {
                ConnectionFactory.rollback(con);
                DB_LOG.error("getProductImages() failed", ex);
                throw new DBException("getProductImages() failed", ex);
            }finally {
                ConnectionFactory.close(rs);
            }
            return product;
    }

    /**
     * Method that gets particular materials of particular Item
     * @param con Connection
     * @param item Item with list of products
     * @throws DBException trouble with connection
     */
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


    /**
     * Method for extracting all Sizes Colours Brands from db for filter products or creating new products
     * @return TreeMap <String,List> result
     * @throws DBException if problems with connection
     */
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
            rs = stmt.executeQuery(SQL_FIND_All_AVAILABLE_COLOURS);
            while (rs.next()) {
                String colour = rs.getString(Fields.ITEM_COLOUR);
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

    /**
     * Method for extracting colour values from db and ordering them by ab
     * @return new TreeSet<String> colours
     * @throws DBException if problems with connection
     */
    public Set<String> getColours() throws DBException {

        Set<String> colours = new TreeSet<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_GET_All_DB_COLOURS);
            while (rs.next()) {
                String colour = rs.getString(Fields.COLOUR);
                colours.add(colour);
            }
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("getColours() failed");
        } finally {
            ConnectionFactory.close(con, stmt, rs);
        }

        return colours;
    }

    /**
     * Method for extracting item values from respond to create item
     * @param rs ResultSet
     * @return new Item
     * @throws SQLException if rs null
     */
    private static Item extractItem(ResultSet rs) throws SQLException {

        Item item = new Item();
        item.setId(rs.getLong(Fields.ENTITY_ID));
        item.setItemName(rs.getString(Fields.ITEM_NAME));
        item.setPrice(rs.getDouble(Fields.ITEM_PRICE));
        Date d = rs.getDate(Fields.ITEM_RELEASE_DATE);
        item.setReleaseDate(d.toLocalDate());
        item.setBrand(rs.getString(Fields.ITEM_BRAND));
        item.extractColourValue(rs.getString(Fields.ITEM_COLOUR));
        item.setCategoryId(rs.getInt(Fields.ITEM_CATEGORY_ID));
        return item;
    }

    /**
     * Method for extracting product values from respond to create product
     * @param rs ResultSet
     * @return new Product
     * @throws SQLException if rs null
     */
    private static Product extractProduct(ResultSet rs) throws SQLException {

        Product product = new Product();
        product.setId(rs.getLong(Fields.ENTITY_ID));
        product.setName(rs.getString(Fields.PRODUCT_NAME));
        product.setAvailable(rs.getInt(Fields.PRODUCT_AVAILABLE));
        product.extractSizeValue(rs.getString(Fields.PRODUCT_SIZE));

        return product;
    }

    /**
     * Method for extracting materials values from respond to Item with products
     * @param rs ResultSet
     * @return Material
     * @throws SQLException if rs null
     */
    private static Material extractMaterial(ResultSet rs) throws SQLException {

        Material material = new Material();
        material.setId(rs.getLong(Fields.ENTITY_ID));
        material.setName(rs.getString(Fields.MAERIAL_NAME));
        material.setPercent(rs.getInt(Fields.MAERIAL_PERCENT));
        material.setItemId(rs.getLong(Fields.ENTITY_ID));
        return material;

    }
}
