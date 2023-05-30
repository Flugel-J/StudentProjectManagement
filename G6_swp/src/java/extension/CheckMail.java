/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extension;

import dal.ClassUserDB;
import dal.FeatureDB;
import dal.FunctionDB;
import dal.SubjectSettingDB;
import dal.TeamDB;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;
import model.ClassUser;
import model.Feature;
import model.Function;
import model.Subject;
import model.SubjectSetting;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 03623
 */
public class CheckMail {
    public static void send(String to, String sub,
            String msg, final String user, final String pass) {
        //create an instance of Properties Class   
        Properties props = new Properties();

        /* Specifies the IP address of your default mail server
     	   for e.g if you are using gmail server as an email sever
           you will pass smtp.gmail.com as value of mail.smtp host. 
           As shown here in the code. 
           Change accordingly, if your email id is not a gmail id
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        //below mentioned mail.smtp.port is optional
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        /* Pass Properties object(props) and Authenticator object   
           for authentication to Session instance 
         */
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {

            /* Create an instance of MimeMessage, 
 	      it accept MIME types and headers 
             */
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setContent(msg, "text/html");

            /* Transport class is used to deliver the message to the recipients */
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            
        }
    }
    
    public static void sendFile(String to, String sub,
            String msg, final String user, final String pass, String namefile) {
        Properties props = new Properties();

        /* Specifies the IP address of your default mail server
     	   for e.g if you are using gmail server as an email sever
           you will pass smtp.gmail.com as value of mail.smtp host. 
           As shown here in the code. 
           Change accordingly, if your email id is not a gmail id
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        //below mentioned mail.smtp.port is optional
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        /* Pass Properties object(props) and Authenticator object   
           for authentication to Session instance 
         */
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {

            /* Create an instance of MimeMessage, 
 	      it accept MIME types and headers 
             */
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setContent(msg, "text/html");

            BodyPart messageBodyPart = new MimeBodyPart();

            // Phan than message
            messageBodyPart.setText("This is file excel "+namefile+" system send by your request");

            // Tao mot multipar message
            Multipart multipart = new MimeMultipart();

            // Thiet lap phan text message
            multipart.addBodyPart(messageBodyPart);

            // Phan hai la attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "D://danhsach2.xlsx";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(namefile+".xlsx");
            multipart.addBodyPart(messageBodyPart);

            // Gui cac phan day du cua message
            message.setContent(multipart);
            /* Transport class is used to deliver the message to the recipients */
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }

    public static void ExportFileExcel(ArrayList<ClassUser> listclassuer) {
        // TODO add your handling code here:
        try
        {
        XSSFWorkbook wordkbook = new XSSFWorkbook();
        XSSFSheet sheet=wordkbook.createSheet("danhsach");
        XSSFRow row =null;
        Cell cell=null;
        
//        row=sheet.createRow(0);
//        cell=row.createCell(0,CellType.STRING);
//        cell.setCellValue("DANH SACH GIA SACH");
        
        row=sheet.createRow(0);
        cell=row.createCell(0,CellType.STRING);
        cell.setCellValue("Class Code");
        
        cell=row.createCell(1,CellType.STRING);
        cell.setCellValue("Subject Code");
        
        cell=row.createCell(2,CellType.STRING);
        cell.setCellValue("Subject Name");
        
        cell=row.createCell(3,CellType.STRING);
        cell.setCellValue("Team");
        
        cell=row.createCell(4,CellType.STRING);
        cell.setCellValue("Email");
        
        cell=row.createCell(5,CellType.STRING);
        cell.setCellValue("Full name");
        
        cell=row.createCell(6,CellType.STRING);
        cell.setCellValue("Roll Number");
        
        cell=row.createCell(7,CellType.STRING);
        cell.setCellValue("Team Leader");
        
        cell=row.createCell(8,CellType.STRING);
        cell.setCellValue("Dropout Date");
        
        cell=row.createCell(9,CellType.STRING);
        cell.setCellValue("Ongoing eval");
        
        cell=row.createCell(10,CellType.STRING);
        cell.setCellValue("Final pres eval");
        
        cell=row.createCell(11,CellType.STRING);
        cell.setCellValue("Final topic eval");
        
        cell=row.createCell(12,CellType.STRING);
        cell.setCellValue("User Not");
        
        int index = -1;
            for (ClassUser c : listclassuer) {
                index++;
                row=sheet.createRow(1+index);
           
                cell=row.createCell(0,CellType.NUMERIC);
                cell.setCellValue(c.getClassId().getClassCode());

                cell=row.createCell(1,CellType.STRING);
                cell.setCellValue(c.getClassId().getSubject().getSubjectCode());

                cell=row.createCell(2,CellType.STRING);
                cell.setCellValue(c.getClassId().getSubject().getSubjectName());

                cell=row.createCell(3,CellType.STRING);
                cell.setCellValue(c.getTeam().getTeamCode());
                
                cell=row.createCell(4,CellType.STRING);
                cell.setCellValue(c.getUser().getEmail());
                
                cell=row.createCell(5,CellType.STRING);
                cell.setCellValue(c.getUser().getFullName());
                
                cell=row.createCell(6,CellType.STRING);
                cell.setCellValue(c.getUser().getRollNumber());
                
                cell=row.createCell(7,CellType.STRING);
                cell.setCellValue(c.isTeamLeader());
                
                cell=row.createCell(8,CellType.STRING);
                cell.setCellValue(c.getDropoutDate());
                
                cell=row.createCell(9,CellType.STRING);
                cell.setCellValue(c.getOngoingEval());
                
                cell=row.createCell(10,CellType.STRING);
                cell.setCellValue(c.getFinalPresEval());
                
                cell=row.createCell(11,CellType.STRING);
                cell.setCellValue(c.getFinalTopicEval());
                
                cell=row.createCell(12,CellType.STRING);
                cell.setCellValue(c.getUserNotes());
            }
            
        Path p = FileSystems.getDefault().getPath("D://danhsach2.xlsx");
        try {
            Files.deleteIfExists(p);
        } catch (IOException x) {
            System.err.println(x);
        }
        
        File f = new File("D://danhsach2.xlsx");
        
        try 
        {
            //DataSource d = wordkbook;
            FileOutputStream fis = new FileOutputStream(f);
            
            wordkbook.write(fis);
            fis.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
       
        }
        catch (IOException ex)
        {
          ex.printStackTrace();
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    public static final int COLUMN_TEAM_CODE = 0;
    public static final int COLUMN_ROLL_NUMBER = 1;
    public static final int COLUMN_FULL_NAME = 2;
    public static final int COLUMN_TEAM_LEADER = 3;
    public static final int COLUMN_NOTE = 4;
     public static ArrayList<ClassUser> readExcel(InputStream inputStream,int classid) throws IOException {
        ArrayList<ClassUser> listClassuser = new ArrayList<>();
        ClassUserDB cldb =new ClassUserDB();
        // Get file
        //InputStream inputStream = new FileInputStream(new File(excelFilePath));
       // String excelFilePath
        // Get workbook
        //Workbook workbook = getWorkbook(inputStream, excelFilePath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);
 
        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }
 
            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();
 
            // Read cells and set value for book object
            ClassUser cluser = new ClassUser();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                cluser.setClassId(cldb.getClassbyid(classid));// ClassID
                
                switch (columnIndex) {
                case COLUMN_TEAM_CODE:
                    try {
                        if(cldb.getTeambyteamcode_classid(classid, (String)getCellValue(cell))==null){
                                cldb.insertTeam((String)getCellValue(cell), classid);
                        }
                        cluser.setTeam(cldb.getTeambyteamcode_classid(classid, (String)getCellValue(cell)));// Team
                    } catch (Exception e) {
                    }
                    break;
                case COLUMN_ROLL_NUMBER:
                    try {
                        cluser.setUser(cldb.getUserbyRollNumber((String)getCellValue(cell)));//user
                    } catch (Exception e) {
                    }
                    break;
                case COLUMN_FULL_NAME:
                    //cluser.set((String)getCellValue(cell));
                    break;
                case COLUMN_TEAM_LEADER:
                    try {
                        cluser.setTeamLeader((boolean)getCellValue(cell));
                    } catch (Exception e) {
                        cluser.setTeamLeader(false);
                    }
                    break;
                case COLUMN_NOTE:
                    try {
                        cluser.setUserNotes((String)getCellValue(cell));// user note
                    } catch (Exception e) {
                    }
                    break;
                default:
                    break;
                }

            }

            listClassuser.add(cluser);
        }
 
        workbook.close();
        inputStream.close();
 
        return listClassuser;
    }
 
    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
 
        return workbook;
    }
 
    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
        case BOOLEAN:
            cellValue = cell.getBooleanCellValue();
            break;
        case FORMULA:
            Workbook workbook = cell.getSheet().getWorkbook();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            cellValue = evaluator.evaluate(cell).getNumberValue();
            break;
        case NUMERIC:
            cellValue = cell.getNumericCellValue();
            break;
        case STRING:
            cellValue = cell.getStringCellValue();
            break;
        case _NONE:
        case BLANK:
        case ERROR:
            break;
        default:
            break;
        }
 
        return cellValue;
    }

    
    
    
    public static void ExportFileExcelFunction(ArrayList<Function> listFunction, HttpServletResponse response,String NameClass,String NameTeam,String Template) throws IOException {
        // TODO add your handling code here:
        try
        {
        XSSFWorkbook wordkbook = new XSSFWorkbook();
        XSSFSheet sheet=wordkbook.createSheet("danhsach");
        XSSFRow row =null;
        Cell cell=null;
        
        
        row=sheet.createRow(0);
        cell=row.createCell(0,CellType.STRING);
        cell.setCellValue("Function Id");
        
        cell=row.createCell(1,CellType.STRING);
        cell.setCellValue("Function Name");
        
        cell=row.createCell(2,CellType.STRING);
        cell.setCellValue("Team Name");
        
        cell=row.createCell(3,CellType.STRING);
        cell.setCellValue("Feature Name");
        
        cell=row.createCell(4,CellType.STRING);
        cell.setCellValue("Access Role");
        
        cell=row.createCell(5,CellType.STRING);
        cell.setCellValue("Complexity");
        
        cell=row.createCell(6,CellType.STRING);
        cell.setCellValue("Priority");
        
        cell=row.createCell(7,CellType.STRING);
        cell.setCellValue("Status");
        
        cell=row.createCell(8,CellType.STRING);
        cell.setCellValue("Description");
        
        int index = -1;
            for (Function c : listFunction) {
                index++;
                row=sheet.createRow(1+index);
           
                cell=row.createCell(0,CellType.NUMERIC);
                cell.setCellValue(c.getId());

                cell=row.createCell(1,CellType.STRING);
                cell.setCellValue(c.getFunctionName());

                cell=row.createCell(2,CellType.STRING);
                cell.setCellValue(c.getTeam().getTeamCode());

                cell=row.createCell(3,CellType.STRING);
                cell.setCellValue(c.getFeature().getFeatureName());
                
                cell=row.createCell(4,CellType.STRING);
                cell.setCellValue(c.getAccessRoles());
                
                cell=row.createCell(5,CellType.STRING);
                cell.setCellValue(c.getSubjectSetting().getSettingTitle());
                
                cell=row.createCell(6,CellType.NUMERIC);
                cell.setCellValue(c.getPriority());
                
                cell=row.createCell(7,CellType.STRING);
                int st = c.getStatus();
                    String st2 = "";
                    if(st==1){st2="Pending";}
                    if(st==2){st2="Planned";}
                    if(st==3){st2="Evaluated";}
                    if(st==4){st2="Rejected";}
                cell.setCellValue(st2);
                
                cell=row.createCell(8,CellType.STRING);
                cell.setCellValue(c.getDescription());
            }
            
        
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        if(Template==null||Template.isEmpty()){
            response.setHeader("Content-Disposition", "attachment; filename=FunctionList "
                        + NameTeam + "-"
                        + NameClass + ".xlsx");
        }else{
            response.setHeader("Content-Disposition", "attachment; filename=Template Function List "+ ".xlsx");
        }
        

        try 
        {
            
            wordkbook.write(response.getOutputStream());
        }catch (FileNotFoundException ex) {
            ex.printStackTrace();
       
        }
        catch (IOException ex)
        {
          ex.printStackTrace();
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    
    }
    
    public static final int Function_Id = 0;
    public static final int Function_Name = 1;
    public static final int Team_Name = 2;
    public static final int Feature_Name = 3;
    public static final int Access_Role = 4;
    public static final int Complexity = 5;
    public static final int Priority = 6;
    public static final int Status = 7;
    public static final int Description = 8;
     public static ArrayList<Function> readExcelFunction(InputStream inputStream,int Teamid) throws IOException {
        ArrayList<Function> listFunction = new ArrayList<>();
        ClassUserDB cldb =new ClassUserDB();
        // Get file
        //InputStream inputStream = new FileInputStream(new File(excelFilePath));
       // String excelFilePath
        // Get workbook
        //Workbook workbook = getWorkbook(inputStream, excelFilePath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);
 
        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }
 
            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();
 
            // Read cells and set value for book object
            Function fun = new Function();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                fun.setId(Teamid);//set bừa
                switch (columnIndex) {
                case Function_Name:
                        fun.setFunctionName((String)getCellValue(cell));// set fname
                    break;
                case Team_Name:
                        TeamDB tdb = new TeamDB();
                        fun.setTeam(tdb.getTeam(String.valueOf(Teamid)));
                    break;
                case Feature_Name:
                    TeamDB tdb2 = new TeamDB();
                    FunctionDB fudb = new FunctionDB();
                    if(fudb.getFeaturebyTeamidbyfname(Teamid,(String)getCellValue(cell))==null){// check co featurename vs team id chua
                        FeatureDB feadb = new FeatureDB();
                        Feature fea = new Feature(1, tdb2.getTeam(String.valueOf(Teamid)), (String)getCellValue(cell), true);
                        feadb.addFeature(fea);// Feature
                        fun.setFeature(fudb.getFeaturebyTeamidbyfname(Teamid,(String)getCellValue(cell)));
                    }else{
                        fun.setFeature(fudb.getFeaturebyTeamidbyfname(Teamid,(String)getCellValue(cell)));
                    }
                        
                    //cluser.set((String)getCellValue(cell));
                    break;
                case Access_Role:
                    fun.setAccessRoles((String)getCellValue(cell));
                    break;
                case Complexity:
                    ClassUserDB cldb2 = new ClassUserDB();
                    TeamDB tdb3 = new TeamDB();
                    FunctionDB fudb2 = new FunctionDB();
                    SubjectSettingDB ssdb = new SubjectSettingDB();
                    Subject sub = cldb2.getTeambyid(Teamid).getClassId().getSubject();
                    if(fudb2.getSubjectSettingbyTile((String)getCellValue(cell), sub.getSubjectId())==null){// settingtile co chua
                        SubjectSetting ss = new SubjectSetting();
                        ss.setSubjectId(sub.getSubjectId());ss.setSettingTitle((String)getCellValue(cell));ss.setStatus(true);
                        ssdb.addSubjectSetting(ss);
                        fun.setSubjectSetting(fudb2.getSubjectSettingbyTile((String)getCellValue(cell), sub.getSubjectId()));
                    }else{
                        fun.setSubjectSetting(fudb2.getSubjectSettingbyTile((String)getCellValue(cell),sub.getSubjectId()));
                    }
                    break;
                case Priority:
                    Integer pr = new BigDecimal((double) cellValue).intValue();
                    int i = pr;
                    fun.setPriority(i);
                    break;
                case Status:
                    int st = 1;
                    String st2 = (String)getCellValue(cell);
                    if(st2.contains("Pending")){st=1;}
                    if(st2.contains("Planned")){st=2;}
                    if(st2.contains("Evaluated")){st=3;}
                    if(st2.contains("Rejected")){st=4;}
                    fun.setStatus(st);
                    break;
                case Description:
                    fun.setDescription((String)getCellValue(cell));
                    break;
                default:
                    break;
                }

            }

            listFunction.add(fun);
        }
 
        workbook.close();
        inputStream.close();
 
        return listFunction;
    }
//    public static void main(String[] args) {
//        String subject = "Your order has been processing.";
//        String message = "<!DOCTYPE html>\n"
//                + "<html lang=\"en\">\n"
//                + "\n"
//                + "<head>\n"
//                + "</head>\n"
//                + "\n"
//                + "<body>\n"
//                + "    <h3 style=\"color: blue;\">Account registration confirmation email.</h3>\n"
//                + "    <div>Click here: <a href=\"http://localhost:8084/G6_swp/registration\" style=\"color: brown; font-size: 20px;\">confirm</a></div>\n"
//                + "\n"
//                + "</body>\n"
//                + "\n"
//                + "</html>";
//        CheckMail.send("traicvhe153014@fpt.edu.vn", subject, message, "0362351671trai@gmail.com", "0362351671");
//        //vd để gửi email tới "dich@gmail.com" bằng email "nguon@gmail.com" pass "123456"
////        SendMail.send("dich@gmail.com", subject, message, "nguon@gmail.com", "123456");
//    }
    
//    public static void main(String[] args) throws IOException {
//        
//        final String excelFilePath = "C:\\Users\\03623\\OneDrive\\Máy tính\\Import_classuser.xlsx";
//        final ArrayList<ClassUser> books = readExcel(excelFilePath,1);
//        for (ClassUser book : books) {
//            System.out.println(book.getClassId().getClassId());
//            System.out.println(book.getDropoutDate());
//            System.out.println(book.getTeam().getTeamCode());
//            System.out.println(book.isTeamLeader());
//            System.out.println(book.getUser().getFullName());
//            System.out.println(book.getUserNotes());
//        }
//    }
  }
 

