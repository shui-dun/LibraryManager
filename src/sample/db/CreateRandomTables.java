package sample.db;

import java.sql.*;

/**
 * 向数据库中填充随机数据
 *
 * @author 黄小可
 */
public class CreateRandomTables
{
    private static int startStudentID = 10000;
    private static int startManagerID = 1000;
    private static int startBookID = 10000;

    public static void main(String[] args) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/sample/db/library_manager.db");
//        randomManager(connection, 5);
//        randomStudent(connection, 50);
//        randomBook(connection, 100);
//        randomBorrow(connection, 200);
    }

    //    向borrow表中添加随机数据
    static void randomBorrow(Connection connection, int amount)
    {
        PreparedStatement borrow = null;
        try
        {
            borrow = connection.prepareStatement("insert or ignore into borrow(book_ID,borrower_ID) values (?,?);");
            for (int i = 0; i < amount; i++)
            {
                borrow.setInt(1, randomBookID(connection));
                borrow.setInt(2, randomStudentID(connection));
                borrow.executeUpdate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //    向book表中添加随机数据
    static void randomBook(Connection connection, int amount)
    {
        PreparedStatement book = null;
        try
        {
            book = connection.prepareStatement("insert or ignore into book" + " values (?,?,?,?,?,?);");
            for (int i = 0; i < amount; i++)
            {
                book.setInt(1, startBookID++);
                book.setString(2, randomBookName());
                book.setString(3, randomLastName() + randomFirstName());
                book.setString(4, randomPress());
                book.setString(5, randomCategory());
                book.setInt(6, randomArray(2));
                book.executeUpdate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //    向student表中添加随机数据
    static void randomStudent(Connection connection, int amount)
    {
        PreparedStatement student = null;
        try
        {
            student = connection.prepareStatement("insert or ignore into student" + " values (?,?,?,?,?,?,?);");
            for (int i = 0; i < amount; i++)
            {
                student.setInt(1, startStudentID++);
                student.setString(2, randomLastName());
                student.setString(3, randomFirstName());
                student.setString(4, randomGender());
                student.setInt(5, randomArray(6));
                student.setString(6, randomFaculty());
                student.setInt(7, randomArray(4));
                student.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //    向manager表中填充随机数据
    static void randomManager(Connection connection, int amount)
    {
        PreparedStatement manager = null;
        try
        {
            manager = connection.prepareStatement("insert or ignore into manager" + " values (?,?,?,?,?,?);");
            for (int i = 0; i < amount; i++)
            {
                manager.setInt(1, startManagerID++);
                manager.setString(2, randomLastName());
                manager.setString(3, randomFirstName());
                manager.setString(4, randomGender());
                manager.setString(5, randomDate());
                manager.setInt(6, randomArray(4));
                manager.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // 从student表中获取随机ID
    static int randomStudentID(Connection connection) throws Exception
    {
        Statement studentID = connection.createStatement();
        ResultSet count = studentID.executeQuery("select count(*) from student");
        int randomPositon = 1 + (int) Math.floor(Math.random() * count.getInt(1));
        ResultSet resultSet = studentID.executeQuery("select ID from student");
        for (int i = 0; i < randomPositon; i++)
            resultSet.next();
        return resultSet.getInt(1);
    }

    // 从book表中获取随机ID
    static int randomBookID(Connection connection) throws Exception
    {
        Statement bookID = connection.createStatement();
        ResultSet count = bookID.executeQuery("select count(*) from book");
        int randomPositon = 1 + (int) Math.floor(Math.random() * count.getInt(1));
        ResultSet resultSet = bookID.executeQuery("select ID from book");
        for (int i = 0; i < randomPositon; i++)
            resultSet.next();
        return resultSet.getInt(1);
    }

    //    产生随机姓氏
    static String randomLastName()
    {
        String lastNameString = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫房裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣";
        int startIndex = (int) Math.floor(1 + (lastNameString.length() - 1) * Math.random());
        return lastNameString.substring(startIndex, startIndex + 1);
    }

    //    产生随机名字
    static String randomFirstName()
    {
        String firstNameString = "弘翔冬睿维平正抒净冉加伟禾晨令哲弘一锦生冉毓宁宇邦弘宁谊代容永晖安民宁立可冉莹涵冉宁婉晨永以晗弘珅弘书世鸣彦宁奕立昊圣新田世颢弘阳翌宁宁学旭北生新鸿永以暄尚冉圣华宁弘宁希可凡天宁泽玉俐冉巧抒代蓉叶妤丽玉绍宁玉森冬晗圣露可唯圣育宁含妤冉玉菲龙宁玉辰俊生世彦晨平安冉宁羿可上薪宁梦可怡冉斌民呈圣新宁楚弘弘楠升宁晨加昌梦玉夕冉幼雪弘新梦冉卉媛厚生立国亿宁永升书弘昕可懿可泓冉阳平睿司令辉新巧冬逸瑾玉宁耘仲申宁颜仲宁梓叶冉煜丝冉坤生宁儒世辰世泓雨田轩禾芮可永琪孺宁萱叶宁毓占凯世晗良平弘杨叶铭弘铭靖冉奕冉正彬";
        int startIndex = (int) Math.floor(1 + (firstNameString.length() - 1) * Math.random());
        String nameString = firstNameString.substring(startIndex, startIndex + 1);
        String returnString;
        switch ((int) Math.round(Math.random()))
        {
            case 0:
                returnString = nameString;
                break;
            default:
                int startIndex2 = (int) Math.floor(1 + (firstNameString.length() - 1) * Math.random());
                String nameString2 = firstNameString.substring(startIndex2, startIndex2 + 1);
                returnString = nameString + nameString2;
        }
        return returnString;
    }

    //    产生随机性别
    static String randomGender()
    {
        String genderString;
        switch ((int) Math.floor(Math.random() * 3))
        {
            case 0:
                genderString = "男";
                break;
            case 1:
                genderString = "女";
                break;
            default:
                genderString = "保密";
        }
        return genderString;
    }

    //    产生随机数字串
    static int randomArray(int amount)
    {
        String numberString = "0123456789";
        String returnString = "";
        for (int currentAmount = 0; currentAmount < amount; currentAmount++)
        {
            int startIndex = (int) Math.floor(1 + Math.random() * 9);
            returnString += numberString.substring(startIndex, startIndex + 1);
        }
        return Integer.parseInt(returnString);
    }

    //    产生随机院系
    static String randomFaculty()
    {
        String faculty;
        switch ((int) Math.floor(Math.random() * 10))
        {
            case 0:
                faculty = "计算机科学与技术";
                break;
            case 1:
                faculty = "航天航空工程";
                break;
            case 2:
                faculty = "工程力学";
                break;
            case 3:
                faculty = "建筑学";
                break;
            case 4:
                faculty = "金融学";
                break;
            case 5:
                faculty = "汉语言文学";
                break;
            case 6:
                faculty = "城乡规划";
                break;
            case 7:
                faculty = "数学与应用数学";
                break;
            case 8:
                faculty = "车辆工程";
                break;
            default:
                faculty = "软件工程";
                break;
        }
        return faculty;
    }

    //    产生随机出版社
    static String randomPress()
    {
        String press;
        switch ((int) Math.floor(Math.random() * 10))
        {
            case 0:
                press = "中华书局";
                break;
            case 1:
                press = "广西师范大学社";
                break;
            case 2:
                press = "中国人民大学出版社";
                break;
            case 3:
                press = "中信出版社";
                break;
            case 4:
                press = "机械工业出版";
                break;
            case 5:
                press = "译林出版社";
                break;
            case 6:
                press = "福建人民出版社";
                break;
            case 7:
                press = "哈尔滨工程大学出版社";
                break;
            case 8:
                press = "河北教育出版社";
                break;
            default:
                press = "教育科学出版社";
                break;
        }
        return press;
    }

    //    产生随机图书类别
    static String randomCategory()
    {
        String category;
        switch ((int) Math.floor(Math.random() * 10))
        {
            case 0:
                category = "哲学";
                break;
            case 1:
                category = "宗教";
                break;
            case 2:
                category = "法律";
                break;
            case 3:
                category = "文化";
                break;
            case 4:
                category = "艺术";
                break;
            case 5:
                category = "数理科学和化学";
                break;
            case 6:
                category = "医药";
                break;
            case 7:
                category = "推理悬疑";
                break;
            case 8:
                category = "文学";
                break;
            default:
                category = "科学";
                break;
        }
        return category;
    }

    static String randomBookName()
    {
        String returnString = "";
        String string;
        for (int i = 0; i < 4; i++)
        {
            switch ((int) Math.floor(Math.random() * 30))
            {
                case 0:
                    string = "梦想";
                    break;
                case 1:
                    string = "遇见";
                    break;
                case 2:
                    string = "少年";
                    break;
                case 3:
                    string = "水遁";
                    break;
                case 4:
                    string = "万丈";
                    break;
                case 5:
                    string = "星辰";
                    break;
                case 6:
                    string = "大海";
                    break;
                case 7:
                    string = "热望";
                    break;
                case 8:
                    string = "笑容";
                    break;
                case 9:
                    string = "鲜艳";
                    break;
                case 10:
                    string = "温柔";
                    break;
                case 11:
                    string = "可爱";
                    break;
                case 12:
                    string = "风景";
                    break;
                case 13:
                    string = "质朴";
                    break;
                case 14:
                    string = "曲折";
                    break;
                case 15:
                    string = "经典";
                    break;
                case 16:
                    string = "青春";
                    break;
                case 17:
                    string = "倾听";
                    break;
                case 18:
                    string = "奋斗";
                    break;
                case 19:
                    string = "纵波";
                    break;
                case 20:
                    string = "衍射";
                    break;
                case 21:
                    string = "正交";
                    break;
                case 22:
                    string = "矩阵";
                    break;
                case 23:
                    string = "维数";
                    break;
                case 24:
                    string = "级数";
                    break;
                case 25:
                    string = "微分";
                    break;
                case 26:
                    string = "积分";
                    break;
                case 27:
                    string = "异常";
                    break;
                case 28:
                    string = "离散";
                    break;
                default:
                    string = "线性";
                    break;
            }
            returnString += string;
        }
        return returnString;
    }

    //    产生随机日期 1950-1999
    static String randomDate()
    {
        String year = "19" + (int) Math.floor(50 + Math.random() * 50);
        String month = Integer.toString((int) Math.floor(1 + Math.random() * 12));
        String day = Integer.toString((int) Math.floor(1 + Math.random() * 28));
        return year + '-' + month + '-' + day;
    }
}

