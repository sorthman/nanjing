package org.linlinjava.litemall.core.util;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ch.qos.logback.core.boolex.Matcher;
import org.linlinjava.litemall.db.domain.Njuser;
import org.linlinjava.litemall.db.domain.Ruser;
import org.linlinjava.litemall.db.domain.Whuser;

public class ExcelUtil<T> {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    private static final Log logger = LogFactory.getLog(ExcelUtil.class);

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) { // Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    public static List<Whuser> getList(String filePath) {
        try {
            File excelFile = new File(filePath); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            Workbook workbook = getWorkbok(in, excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            List<Whuser> words = new ArrayList<Whuser>();
            int count = 0;
            for (Row row : sheet) {
                // 跳过第一行的目录
                if (count < 1) {
                    count++;
                    continue;
                }

                Whuser newword = new Whuser();

                int end = row.getLastCellNum();

                Cell cell = row.getCell(0);
                try {
                    newword.setDatatype(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setDatatype("");
                }

                cell = row.getCell(2);
                try {
                    newword.setName(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(3);
                try {
                    newword.setSex(cell.getStringCellValue());
                } catch (Exception e) {
                }

                try {
                    cell = row.getCell(4);
                    newword.setAge((int) cell.getNumericCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(5);
                try {
                    newword.setIdcard(cell.toString());
                } catch (Exception e) {
                    newword.setIdcard("");
                }

                cell = row.getCell(6);
                try {
                    newword.setIfstay(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setIfstay("");
                }

                cell = row.getCell(7);
                try {
                    newword.setIfwh(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setIfwh("");
                }

                cell = row.getCell(8);
                try {
                    newword.setIfleavenj(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(9);
                try {
                    newword.setIsmanage(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(11);
                try {
                    newword.setIflose(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(12);
                try {
                    newword.setLoseinfo(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(13);
                try {
                    newword.setStreet(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(14);
                try {
                    newword.setNjcommunity(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(15);
                try {
                    newword.setLiveaddress(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(16);
                try {
                    newword.setWhtime(cell.getStringCellValue());
                } catch (Exception e) {
                }

                try {
                    cell = row.getCell(17);
                    Instant instant = cell.getDateCellValue().toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    newword.setArrivedate(instant.atZone(zoneId).toLocalDateTime());
                } catch (Exception e) {
                }

                cell = row.getCell(18);
                try {
                    newword.setHealthinfo(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(19);
                try {
                    newword.setDocinfo(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(20);
                try {
                    newword.setPhone(cell.toString());
                } catch (Exception e) {
                }

                cell = row.getCell(22);
                try {
                    newword.setRemark(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(23);
                try {
                    newword.setJdname(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(24);
                try {
                    newword.setGbname(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(25);
                try {
                    newword.setSgname(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(26);
                try {
                    newword.setMjname(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(27);
                try {
                    newword.setYlname(cell.getStringCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(28);
                try {
                    newword.setUphone(cell.toString());
                } catch (Exception e) {
                }

                cell = row.getCell(29);
                try {
                    newword.setUsertype(cell.getStringCellValue());
                } catch (Exception e) {
                }

                if (!StringUtils.isEmpty(newword.getName())) {
                    words.add(newword);
                }
            }
            return words;
        } catch (Exception ex) {
//			logger.info("解析Excel异常：" + ex.getMessage());
//			System.out.println("解析Excel异常：" + ex.getMessage());
            return null;
        }

    }

    public static List<Whuser> getSJKList(String filePath) {
        try {
            File excelFile = new File(filePath); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            Workbook workbook = getWorkbok(in, excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            List<Whuser> words = new ArrayList<Whuser>();
            int count = 0;
            for (Row row : sheet) {
                // 跳过第一行的目录
                if (count < 1) {
                    count++;
                    continue;
                }

                Whuser newword = new Whuser();

                int end = row.getLastCellNum();

                Cell cell = row.getCell(0);
                newword.setPhone(cell.toString());
                cell = row.getCell(1);
                newword.setLevel(cell.getStringCellValue());
                cell = row.getCell(2);
                String ifconfirm = cell.getStringCellValue();
                if (ifconfirm.equals("是")) {
                    newword.setIfsafe("确诊");
                }

                cell = row.getCell(5);
                newword.setName(cell.getStringCellValue());
                cell = row.getCell(6);
                newword.setSex(cell.getStringCellValue());
                cell = row.getCell(9);
                newword.setArea(cell.getStringCellValue());

                cell = row.getCell(10);
                String street = cell.getStringCellValue();
                cell = row.getCell(11);
                newword.setLiveaddress(street + "," + cell.getStringCellValue());

                cell = row.getCell(12);
                newword.setIfwh(cell.getStringCellValue());
                cell = row.getCell(13);
                newword.setIfcontactSars(cell.toString());
                cell = row.getCell(14);
                newword.setIfcontactHot(cell.toString());

                cell = row.getCell(15);
                String ifhot = cell.getStringCellValue();
                if (ifhot.equals("有")) {
                    newword.setIfhot("是");
                    newword.setIfkesou("是");
                } else {
                    newword.setIfhot("否");
                    newword.setIfkesou("否");
                }

                words.add(newword);
            }
            return words;
        } catch (Exception ex) {
//			logger.info("解析Excel异常：" + ex.getMessage());
//			System.out.println("解析Excel异常：" + ex.getMessage());
            return null;
        }

    }

    public static List<Njuser> getNJList(String filePath) {
        try {
            File excelFile = new File(filePath); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            Workbook workbook = getWorkbok(in, excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            List<Njuser> words = new ArrayList<Njuser>();
            int count = 0;
            for (Row row : sheet) {
                // 跳过第1\2\3\4行
                if (count < 4) {
                    count++;
                    continue;
                }

                Cell cell = row.getCell(0);
                if (cell.toString().indexOf("填表说明") >= 0) {
                    count++;
                    continue;
                }

                Njuser newword = new Njuser();

                int end = row.getLastCellNum();

                cell = row.getCell(1);
                newword.setName(cell.getStringCellValue());

                cell = row.getCell(2);
                newword.setSex(cell.getStringCellValue());

                try {
                    cell = row.getCell(3);
                    newword.setAge((int) cell.getNumericCellValue());
                } catch (Exception e) {
                }

                cell = row.getCell(4);
                newword.setIdcard(cell.getStringCellValue());

                cell = row.getCell(5);
                newword.setPhone(cell.toString());
                cell = row.getCell(6);
                newword.setArea(cell.getStringCellValue());
                cell = row.getCell(7);
                newword.setStreet(cell.getStringCellValue());
                cell = row.getCell(8);
                newword.setCommunity(cell.getStringCellValue());
                cell = row.getCell(9);
                newword.setHjaddress(cell.getStringCellValue());
                cell = row.getCell(10);
                newword.setLiveaddress(cell.getStringCellValue());

                cell = row.getCell(11);
                newword.setGlinfo(cell.getStringCellValue());
                cell = row.getCell(12);
                newword.setZdyqinfo(cell.getStringCellValue());
                cell = row.getCell(13);
                newword.setGlqtinfo(cell.getStringCellValue());

                try {
                    cell = row.getCell(14);
                    Instant instant = cell.getDateCellValue().toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    newword.setStarttime(instant.atZone(zoneId).toLocalDateTime());
                } catch (Exception e) {
                }

                try {
                    cell = row.getCell(15);
                    Instant instant = cell.getDateCellValue().toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    newword.setStoptime(instant.atZone(zoneId).toLocalDateTime());
                } catch (Exception e) {
                }

                cell = row.getCell(16);
                newword.setStopinfo(cell.getStringCellValue());

                try {
                    cell = row.getCell(17);
                    Instant instant = cell.getDateCellValue().toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    newword.setEndtime(instant.atZone(zoneId).toLocalDateTime());
                } catch (Exception e) {
                }

                cell = row.getCell(18);
                newword.setSgname(cell.getStringCellValue());
                cell = row.getCell(19);
                newword.setSgphone(cell.toString());

                cell = row.getCell(20);
                newword.setYlname(cell.getStringCellValue());
                cell = row.getCell(21);
                newword.setYlphone(cell.toString());

                cell = row.getCell(22);
                newword.setMjname(cell.getStringCellValue());
                cell = row.getCell(23);
                newword.setMjphone(cell.toString());

                cell = row.getCell(24);
                newword.setWgname(cell.getStringCellValue());
                cell = row.getCell(25);
                newword.setWgphone(cell.toString());

                cell = row.getCell(26);
                newword.setGbname(cell.getStringCellValue());
                cell = row.getCell(27);
                newword.setGbphone(cell.toString());

                words.add(newword);
            }
            return words;
        } catch (Exception ex) {
//			logger.info("解析Excel异常：" + ex.getMessage());
//			System.out.println("解析Excel异常：" + ex.getMessage());
            return null;
        }

    }

    public static List<Ruser> getRUserList(String filePath) {
        try {
            File excelFile = new File(filePath); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            Workbook workbook = getWorkbok(in, excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            List<Ruser> words = new ArrayList<Ruser>();
            int count = 0;
            int flag = 0;
            for (Row row : sheet) {
                // 跳过第1行
                if (count < 1) {
                    Cell headerCell = row.getCell(0);
                    String firstColumn = headerCell.getStringCellValue();
                    if (firstColumn.equals("编号")) {
                        flag = 1;
                    }
                    count++;
                    continue;
                }

                Ruser newword = new Ruser();
                int end = row.getLastCellNum();

                if (flag == 1) {
                    Cell firstCell = row.getCell(0);
                    double id0 = firstCell.getNumericCellValue();
                    Integer uid = Integer.valueOf(Double.valueOf(id0).intValue());
                    newword.setId(uid);
                }

                Cell cell = row.getCell(flag + 0);
                try {
                    newword.setA1(cell.toString());
                } catch (Exception e) {
                    newword.setA1("");
                }

                cell = row.getCell(flag + 1);
                try {
                    newword.setA2(cell.toString());
                } catch (Exception e) {
                    newword.setA2("");
                }

                cell = row.getCell(flag + 2);
                try {
                    newword.setA3(cell.toString());
                } catch (Exception e) {
                    newword.setA3("");
                }

                cell = row.getCell(flag + 3);
                try {
                    newword.setA4(cell.toString());
                } catch (Exception e) {
                    newword.setA4("");
                }

                cell = row.getCell(flag + 4);
                try {
                    newword.setA5(cell.toString());
                } catch (Exception e) {
                    newword.setA5("");
                }

                cell = row.getCell(flag + 5);
                try {
                    newword.setA6(cell.toString());
                } catch (Exception e) {
                    newword.setA6("");
                }
                cell = row.getCell(flag + 6);
                try {
                    newword.setA7(cell.toString());
                } catch (Exception e) {
                    newword.setA7("");
                }
                cell = row.getCell(flag + 7);
                try {
                    newword.setA8(cell.toString());
                } catch (Exception e) {
                    newword.setA8("");
                }
                cell = row.getCell(flag + 8);
                try {
                    newword.setA9(cell.toString());
                } catch (Exception e) {
                    newword.setA9("");
                }
                cell = row.getCell(flag + 9);
                try {
                    newword.setA10(cell.toString());
                } catch (Exception e) {
                    newword.setA10("");
                }
                cell = row.getCell(flag + 10);
                try {
                    newword.setA11(cell.toString());
                } catch (Exception e) {
                    newword.setA11("");
                }
                cell = row.getCell(flag + 11);
                try {
                    newword.setA12(cell.toString());
                } catch (Exception e) {
                    newword.setA12("");
                }
                cell = row.getCell(flag + 12);
                try {
                    newword.setA13(cell.toString());
                } catch (Exception e) {
                    newword.setA13("");
                }
                cell = row.getCell(flag + 13);
                try {
                    newword.setA14(cell.toString());
                } catch (Exception e) {
                    newword.setA14("");
                }
                cell = row.getCell(flag + 14);
                try {
                    newword.setA15(cell.toString());
                } catch (Exception e) {
                    newword.setA15("");
                }

                try {
                    cell = row.getCell(flag + 15);
                    newword.setA16(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA16("");
                }

                try {
                    cell = row.getCell(flag + 16);
                    newword.setA17(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA17("");
                }

                cell = row.getCell(flag + 17);
                try {
                    newword.setA18(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA18("");
                }
                cell = row.getCell(flag + 18);
                try {
                    newword.setA19(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA19("");
                }
                cell = row.getCell(flag + 19);
                try {
                    newword.setA20(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA20("");
                }
                cell = row.getCell(flag + 20);
                try {
                    newword.setA21(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA21("");
                }
                cell = row.getCell(flag + 21);
                try {
                    newword.setA22(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA22("");
                }
                cell = row.getCell(flag + 22);
                try {
                    newword.setA23(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA23("");
                }
                cell = row.getCell(flag + 23);
                try {
                    newword.setA24(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA24("");
                }
                cell = row.getCell(flag + 24);
                try {
                    newword.setA25(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA25("");
                }
                cell = row.getCell(flag + 25);
                try {
                    newword.setA26(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA26("");
                }
                cell = row.getCell(flag + 26);
                try {
                    newword.setA27(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA27("");
                }
                cell = row.getCell(flag + 27);
                try {
                    newword.setA28(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA28("");
                }
                cell = row.getCell(flag + 28);
                try {
                    newword.setA29(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA29("");
                }
                cell = row.getCell(flag + 29);
                try {
                    newword.setA30(cell.getStringCellValue());
                } catch (Exception e) {
                    newword.setA30("");
                }

                words.add(newword);
            }
            return words;
        } catch (Exception ex) {
//			logger.info("解析Excel异常：" + ex.getMessage());
//			System.out.println("解析Excel异常：" + ex.getMessage());
            return null;
        }

    }

    /**
     * <p>
     * 导出无头部标题行Excel <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param title   表格标题
     * @param dataset 数据集合
     * @param out     输出流
     * @param version 2003 或者 2007，不传时默认生成2003版本
     */
    public void exportExcel(String title, Collection<T> dataset, OutputStream out, String version) {
        if (EXCEL_XLS.equals(version.trim())) {
            exportExcel2003(title, null, dataset, out, "yyyy-MM-dd HH:mm:ss");
        } else {
            exportExcel2007(title, null, dataset, out, "yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * <p>
     * 导出带有头部标题行的Excel <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param title   表格标题
     * @param headers 头部标题集合
     * @param dataset 数据集合
     * @param out     输出流
     * @param version 2003 或者 2007，不传时默认生成2003版本
     */
    public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String version) {
        if (EXCEL_XLS.equals(version.trim())) {
            exportExcel2003(title, headers, dataset, out, "yyyy-MM-dd HH:mm:ss");
        } else {
            exportExcel2007(title, headers, dataset, out, "yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
     * </p>
     *
     * @param title   表格标题名
     * @param headers 表格头部标题集合
     * @param dataset 需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void exportExcel2007(String title, String[] headers, Collection<T> dataset, OutputStream out,
                                String pattern) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(new XSSFColor(java.awt.Color.gray));
//		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setColor(new XSSFColor(java.awt.Color.BLACK));
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(new XSSFColor(java.awt.Color.WHITE));
//		style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        XSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        XSSFCell cellHeader;
        for (int i = 0; i < headers.length; i++) {
            cellHeader = row.createCell(i);
            cellHeader.setCellStyle(style);
            cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        T t;
        Field[] fields;
        Field field;
        XSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        java.util.regex.Matcher matcher;
        String fieldName;
        String getMethodName;
        XSSFCell cell;
        Class tCls;
        Method getMethod;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            t = (T) it.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                field = fields[i];
                fieldName = field.getName();
                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此方法生成2003版本的excel,文件名后缀：xls <br>
     * </p>
     *
     * @param title   表格标题名
     * @param headers 表格头部标题集合
     * @param dataset 需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void exportExcel2003(String title, String[] headers, Collection<T> dataset, OutputStream out,
                                String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setColor(HSSFColor.WHITE.index);
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cellHeader;
        for (int i = 0; i < headers.length; i++) {
            cellHeader = row.createCell(i);
            cellHeader.setCellStyle(style);
            cellHeader.setCellValue(new HSSFRichTextString(headers[i]));
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        T t;
        Field[] fields;
        Field field;
        HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        java.util.regex.Matcher matcher;
        String fieldName;
        String getMethodName;
        HSSFCell cell;
        Class tCls;
        Method getMethod;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            t = (T) it.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                field = fields[i];
                fieldName = field.getName();
                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
