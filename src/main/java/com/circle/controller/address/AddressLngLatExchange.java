package com.circle.controller.address;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.circle.dto.Shop;
import com.circle.utils.io.FileUtil;
import com.circle.utils.net.HttpUtil;
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressLngLatExchange {
    private static final String KEY = "ccf1bf3d1a6294bba2dc2caf85366452";
    private final static String BAIDU_KEY = "vz1c8fG8vghZXGnx5AAOl2wuLdCH7gbX";
    private static final String OUTPUT = "JSON";
    private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo";
    private final static String BIADU_URL = "http://api.map.baidu.com/geocoder/v2/";
    private static final String GET_LNG_PIO_URL = "http://restapi.amap.com/v3/place/polygon";
    private static final String DISTRICT_URL = "http://restapi.amap.com/v3/config/district";
    private static final String SEARCH_URL = "http://restapi.amap.com/v3/place/text";
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressLngLatExchange.class);
    private String pathName = "";

    @Value("#{code.dic}")
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    //获取指定地点经纬度
    public static String[] getLngLatFromOneAddr(String address) {
        if (StringUtils.isBlank(address)) {
            LOGGER.error("地址（" + address + "）为null或者空");
            return null;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("address", address);
        params.put("output", OUTPUT);
        params.put("key", KEY);
        String result = HttpUtil.URLPost(GET_LNG_LAT_URL, params, "");
        JSONObject jsonObject = JSONObject.parseObject(result);
        String[] lngLatArr = new String[2];
        //拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));
        if (status == 1) {
            System.out.println(jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String lngLat = json.getString("location");
                lngLatArr = lngLat.split(",");
            }
        } else {
            String errorMsg = jsonObject.getString("info");
            LOGGER.error("地址（" + address + "）" + errorMsg);
        }
        return lngLatArr;
    }

    public void getDistrict() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("keywords", "中国");
        params.put("subdistrict", "3");
        params.put("key", KEY);
        params.put("extensions", "base");
        String result = HttpUtil.URLPost(DISTRICT_URL, params, "");
        JSONObject jsonObject = JSONObject.parseObject(result);
        //拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));
        if (status == 1) {
            // System.out.println(jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("districts");
            //System.out.println(jsonArray.size());

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                JSONArray provinceArray = json.getJSONArray("districts");
                // StringBuilder sb = new StringBuilder();
                for (int j = 0; j < provinceArray.size(); j++) {
                    JSONObject province = provinceArray.getJSONObject(j);
                    // 输出省份
                    //System.out.println("输出省份-----------------");
                    String level = province.getString("level");
                    String name = province.getString("name");
                    //  System.out.println("level=" + level + " , name=" + name);
                    if ("province".equals(level)) {
                        System.out.println(name + "\t" + "province" + "\t" + "1");
                    }
                    //buildString(name,sb , level);
                    JSONArray cityArray = province.getJSONArray("districts");
                    for (int z = 0; z < cityArray.size(); z++) {
                        JSONObject city = cityArray.getJSONObject(z);
                        // 输出区
                        //System.out.println("输出city-----------------");
                        level = city.getString("level");
                        name = city.getString("name");
                        // System.out.println("level=" + level + " , name=" + name);
                        if ("city".equals(level)) {
                            System.out.println(name + "\t" + "city" + "\t" + "2");
                        }
                        //sb = new StringBuilder();
                        // buildString(name, sb, level);
                        JSONArray districtArray = city.getJSONArray("districts");
                        for (int k = 0; k < districtArray.size(); k++) {
                            JSONObject district = districtArray.getJSONObject(k);
                            // System.out.println("输出district-----------------");
                            level = district.getString("level");
                            name = district.getString("name");
                            //System.out.println("level=" + level + " , name=" + name);
                            if ("district".equals(level)) {
                                System.out.println(name + "\t" + "area" + "\t" + "3");
                            }
                            // sb = new StringBuilder();

                            // buildString(name, sb, level);
                        }
                    }

                }


            }

        } else {
            String errorMsg = jsonObject.getString("info");
        }
        return;
    }

    public void search(String keywords) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("keywords", keywords);
        params.put("city", "宁波");
        params.put("children", "2");
        params.put("offset", "100");
        params.put("page", "1");
        params.put("key", KEY);
        params.put("extensions", "base");
        String result = HttpUtil.URLPost(SEARCH_URL, params, "");
        JSONObject jsonObject = JSONObject.parseObject(result);
        //拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));
        if (status == 1) {
            System.out.println(jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("pois");
            System.out.println(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {

            }
        }
    }

    private void buildString(String name, StringBuilder sb, String level) {
        if ("province".equals(level)) {
            sb.append(name);
            sb.append("\t");
            sb.append("province");
            sb.append("\t");
            sb.append("1");
        } else if ("city".equals(level)) {
            sb.append(name);
            sb.append("\t");
            sb.append("city");
            sb.append("\t");
            sb.append("2");
        } else if ("district".equals(level)) {
            sb.append(name);
            sb.append("\t");
            sb.append("area");
            sb.append("\t");
            sb.append("3");
        } else {

        }
        name = sb.toString();
        try {
            FileUtil.write(name, new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Shop> initialData(String lonLat, String keyword, List<Shop> shopListSon) {
        if (StringUtils.isBlank(keyword)) {
            LOGGER.error("地址（" + keyword + "）为null或者空");
        }
        Map<String, String> params = new HashMap<String, String>();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        params.put("polygon", lonLat);//"118.21,29.11;120.30,30.33"
        params.put("output", OUTPUT);
        params.put("keywords", keyword);
        params.put("offset", "20");
        params.put("page", "1");
        params.put("key", KEY);
        String result = HttpUtil.URLGet(GET_LNG_PIO_URL, params, "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(result);
        int statusOne = Integer.valueOf(jsonObject.getString("status"));
        //第一次获取数据时做的判断
        if (statusOne == 1) {
            int count = Integer.valueOf(jsonObject.getString("count"));
            int pageNumber = count / 20;
            int remainder = count % 20;
            if (remainder > 0) {
                pageNumber = pageNumber + 1;
            }
            for (int i = 1; i <= pageNumber; i++) {
                params.put("page", String.valueOf(i));
                result = HttpUtil.URLGet(GET_LNG_PIO_URL, params, "UTF-8");
                JSONObject jsonObject2 = JSONObject.parseObject(result);
                System.out.println("+++++++++" + result);
                //拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
                int status = Integer.valueOf(jsonObject2.getString("status"));
                if (status == 1) {
                    JSONArray jsonArray = jsonObject2.getJSONArray("pois");
                    if (jsonArray.size() > 0) {
                        for (int j = 0; j < jsonArray.size(); j++) {
                            Shop shop = new Shop();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                            shop.setShopName(jsonObject1.getString("name"));
                            shop.setSpecificAddress(jsonObject1.getString("address"));
                            shop.setId(jsonObject1.getString("id"));
                            String[] initLonLat = jsonObject1.getString("location").split(",");
                            shop.setLongitude(initLonLat[0]);
                            shop.setLatitude(initLonLat[1]);
                            shopListSon.add(shop);
                            //DBObject  doci = new BasicDBObject("shopId", "300"+i).append("shopName", "人生得意"+i).append("shopStatus",0).append("specificAddress","天堂"+i).append("gps", new Point(new Position(lon, lat)));

                        }
                    }
                } else {
                    String errorMsg = jsonObject.getString("info");
                    LOGGER.error("地址（" + keyword + "）" + errorMsg);
                }
            }

        }
        return shopListSon;

    }

    //写入excel中
    public static void creatExcel(List<Shop> shopList) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("高德地图数据");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("店铺id");
        cell = row.createCell(1);
        cell.setCellValue("店铺名称");
        cell = row.createCell(2);
        cell.setCellValue("店铺地址");
        cell = row.createCell(3);
        cell.setCellValue("经度");
        cell = row.createCell(4);
        cell.setCellValue("纬度");

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < shopList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            Shop shop = shopList.get(i);
            //创建单元格设值
            row1.createCell(0).setCellValue(shop.getId());
            row1.createCell(1).setCellValue(shop.getShopName());
            row1.createCell(2).setCellValue(shop.getSpecificAddress());
            row1.createCell(3).setCellValue(shop.getLongitude());
            row1.createCell(4).setCellValue(shop.getLatitude());
        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D:\\geode\\高德便利店地图数据.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double Distance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6371; // 地球半径 6371km
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        BigDecimal bigDecimal = new BigDecimal(d * 1000);
        Double din = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return din;
    }

    public static List<Shop> readFile(String filename) {
        List<Shop> shopList = new ArrayList<Shop>();
        Workbook wb = null;
        Cell cell = null;
        try {
            File f = new File(filename);
            InputStream in = new FileInputStream(f);             //创建输入流
            wb = Workbook.getWorkbook(in);                       //获取Excel文件对象
            jxl.Sheet s = wb.getSheet(0);                        //获取文件的指定工作表，默认为第一个
            String value = null;
            for (int i = 1; i < s.getRows(); i++) {//表头目录不需要，从第一行开始
                Shop shop = new Shop();
                for (int j = 0; j < s.getColumns(); j++) {
                    cell = s.getCell(j, i);
                    value = cell.getContents();
                    if (j == 0) {
                        shop.setId(value);
                    } else if (j == 1) {
                        shop.setShopName(value);
                    } else if (j == 2) {
                        shop.setSpecificAddress(value);
                    } else if (j == 3) {
                        shop.setLongitude(value);
                    } else if (j == 4) {
                        shop.setLatitude(value);
                    }
                }
                shopList.add(shop);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return shopList;
    }



}
