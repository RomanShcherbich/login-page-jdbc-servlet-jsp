import dao.*;
import model.*;
import org.junit.*;
import org.junit.runners.MethodSorters;
import utils.JdbcUtils;

import java.sql.*;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest {

    protected static final String MY_SQL_DRIVER_URL = "jdbc:mysql://localhost/crud_jsp_jdbc_demo";
    protected static final String MY_SQL_USERNAME = "root";
    protected static final String MY_SQL_PASSWORD = "MySQL";
    protected static final String MY_SQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static Connection connection;

    @BeforeClass
    public static void connectDB() throws SQLException {
        connection = JdbcUtils.getConnection(MY_SQL_DRIVER_URL, MY_SQL_USERNAME, MY_SQL_PASSWORD,
                MY_SQL_DRIVER_CLASS_NAME);
    }

    @Test
    public void T01_Get_User_Test() throws SQLException {
        String user = "tom";
        String pass = "tom001";
        UserAccountDAO dao = new UserAccountDAO(connection);
        UserAccount expectedUserAcc = dao.read(user, pass);
        Assert.assertEquals("Invalid username",user,expectedUserAcc.getUserName());
        Assert.assertEquals("Invalid gender","M",expectedUserAcc.getGender());
        Assert.assertEquals("Invalid password",pass,expectedUserAcc.getPassword());
    }

    @Test
    public void T02_Get_Product_Test() throws SQLException {
        String code = "P001";
        ProductDAO dao = new ProductDAO(connection);
        Product expectedProduct = dao.read(code);
        Assert.assertEquals("Invalid code",code,expectedProduct.getCode());
        Assert.assertEquals("Invalid name","Java Core",expectedProduct.getName());
        Assert.assertTrue("Invalid price",100F == expectedProduct.getPrice());
    }

    @Test
    public void T03_Insert_Product_Test() throws SQLException {
        String code = "P007";
        String name = "TEST";
        Float price = 77F;
        Product expected = new Product(code, name, price);
        ProductDAO dao = new ProductDAO(connection);
        Product expectedProduct = dao.create(expected);
        Assert.assertEquals("Invalid code",code,expectedProduct.getCode());
        Assert.assertEquals("Invalid name",name,expectedProduct.getName());
        Assert.assertTrue("Invalid price",price == expectedProduct.getPrice());
    }

    @Test
    public void T04_Delete_Product_Test() throws SQLException {
        String code = "P007";
        ProductDAO dao = new ProductDAO(connection);
        Assert.assertTrue("Not deleted",dao.delete(code));
        try {
            dao.read(code);
            Assert.fail("Not deleted");
        } catch (SQLException e) {
            System.out.println("Deleted");
        }
    }

    @Test
    public void T05_Select_all_Test() throws SQLException {
        ProductDAO dao = new ProductDAO(connection);
        List<Product> expectedProduct = dao.readAll();
        for (Product pr:
                expectedProduct) {
            Product actual = dao.read(pr.getCode());
            Assert.assertEquals("Invalid code",actual.getCode(),pr.getCode());
            Assert.assertEquals("Invalid name",actual.getName(),pr.getName());
            Assert.assertTrue("Invalid price",actual.getPrice() == pr.getPrice());
        }
    }

    @AfterClass
    public static void closeConnect() throws Exception {
        JdbcUtils.closeQuietly(connection);
    }

}
