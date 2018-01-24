package com.circle.controller;


import com.circle.constant.PatentType;
import com.circle.service.IAddressService;
import com.circle.utils.collection.ListUtil;
import com.circle.utils.json.JsonReturn;
import com.circle.vo.AddressModel;
import jxl.common.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = "address")
/**
 * @author keweiyang
 */
public class AddressController extends AbstractController {

    private static final Logger logger = Logger.getLogger(AddressController.class);

    /**
     * 批量上传文件分割值
     */
    private static final int UPLOAD_FILE_SIZE = 10000;

    @Autowired
    private IAddressService service = null;

    @ResponseBody
    @RequestMapping(value = "findAddressList")
    public JsonReturn findAddressList(String search, int pageNumber, int pageSize, String state, int type, HttpServletRequest request) {
        return service.findAddressList(search, pageNumber, pageSize, state, type, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findAddressListByPage")
    public JsonReturn findAddressListByPage(@RequestParam String search, @RequestParam int page, int state, int type, HttpServletRequest request) {
        return service.findAddressListByPage(search, page, state, type);
    }

    @ResponseBody
    @RequestMapping(value = "findAddressPage")
    public JsonReturn findAddressPage(@RequestParam String search, @RequestParam int page, String state, int type, HttpServletRequest request) {
        return service.findAddressPage(search, page, Integer.valueOf(state), type);
    }

    @ResponseBody
    @RequestMapping(value = "parseAddress")
    public JsonReturn parseAddress(@RequestParam Integer[] ids, HttpServletRequest request) {
        return service.parseAddress(ids, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "deleteAddress")
    public JsonReturn deleteAddress(@RequestParam Integer[] ids) {
        return service.deleteAddress(ids);
    }

    @ResponseBody
    @RequestMapping(value = "getStatics")
    public JsonReturn getStatics(int type) {
        return service.getStatics(type);
    }

    @ResponseBody
    @RequestMapping(value = "getChartStatics")
    public JsonReturn getChartStatics(int type) {
        return service.getChartStatics(type);
    }

    @ResponseBody
    @RequestMapping(value = "getDataPrecision")
    public JsonReturn getDataPrecision(int type, Integer functionOrRegion,String time) {
        return service.getDataPrecision(type, functionOrRegion,time);
    }

    @ResponseBody
    @RequestMapping(value = "parseAllAddress")
    public JsonReturn parseAllAddress(@RequestParam int type, HttpServletRequest request) {
        return service.parseAllAddress(type, acctName(request));
    }

    @RequestMapping(value = "exportExcel")
    @ResponseBody
    public JsonReturn exportExcel(int type, String time) {
        String title = time;
        if (type == PatentType.Apply.getType()) {
            title = time+"-申请专利数据";
        } else if (type == PatentType.Authorize.getType()) {
            title = time + "-授权专利数据";
        } else if (type == PatentType.Effection.getType()) {
            title = time + "-有效专利数据";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("C:");
        sb.append(File.separator);
        sb.append(time);
        sb.append(File.separator);
        sb.append(title);
        sb.append(".xlsx");

        File file = new File(sb.toString());

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        ExportExcelUtil<AddressModel> ex = new ExportExcelUtil<AddressModel>();

        LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
        propertyHeaderMap.put("request_number", "申请号");
        propertyHeaderMap.put("request_date", "申请日");
        propertyHeaderMap.put("requester_postcode", "申请人邮编");
        propertyHeaderMap.put("requester_address", "申请人地址");
        propertyHeaderMap.put("file_entry_date", "案卷入库日");
        propertyHeaderMap.put("patent_type", "专利类型");
        propertyHeaderMap.put("requester_type", "申请人类型");
        propertyHeaderMap.put("province", "省份名称");
        propertyHeaderMap.put("city", "城市名称");
        propertyHeaderMap.put("channel", "申请方式名称");
        propertyHeaderMap.put("region", "地址化区");
        propertyHeaderMap.put("function_region", "四区一岛");
        propertyHeaderMap.put("region_marked", "已标记地址化区");
        propertyHeaderMap.put("function_region_marked", "已标记四区一岛");
        propertyHeaderMap.put("remark", "分词结果");
        propertyHeaderMap.put("month", "月份");

        List<AddressModel> list = service.getDataByTypeAndTime(type, time);

        OutputStream out = null;
        try {
            XSSFWorkbook xssfWorkbook = ExportExcelUtil.generateXlsxWorkbook(title, propertyHeaderMap, list);
            out = new FileOutputStream(file);
            xssfWorkbook.write(out);
            return JsonReturn.buildSuccess(title + "已保存到C盘目录下！");
        } catch (FileNotFoundException e) {

            return JsonReturn.buildFailure(e.getMessage());
        } catch (IOException e) {

            return JsonReturn.buildFailure(e.getMessage());
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @RequestMapping(value = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public JsonReturn importExcel(@RequestParam("file") MultipartFile file, @RequestParam String state, @RequestParam int type, HttpServletRequest request) {
        logger.info("开始进行上传文件。。。。。。");
        try {
            //1. 首先判断文件后缀
            String originalFilename = file.getOriginalFilename();
            String prefix = null;
            String time = originalFilename.split("_")[1];

            int prefixIndex = originalFilename.lastIndexOf(".");
            if (prefixIndex != -1) {
                prefix = originalFilename.substring(prefixIndex + 1);
                prefix = prefix.toLowerCase();
            }

            MultipartRequest multipartRequest = (MultipartRequest) request;
            MultipartFile excelFile = multipartRequest.getFile("file");

            if (excelFile != null) {
                List<AddressModel> addressModelList = AddressController.readExcel(excelFile.getInputStream(), state, type);
                List<AddressModel> newCopyList = null;
                if (ListUtil.isNotEmpty(addressModelList)) {
                    if (addressModelList.size() / UPLOAD_FILE_SIZE < 1) {
                        for (int i=0;i<addressModelList.size();i++) {
                            AddressModel model = addressModelList.get(i);
                            model.setMonth(time.substring(0, 4) + "-" + time.substring(4, 6));
                        }
                        service.insertBatch(addressModelList);
                    }else {
                        newCopyList = new ArrayList<AddressModel>();
                        for (int i=0;i<addressModelList.size();i++) {
                            AddressModel model = addressModelList.get(i);
                            model.setMonth(time.substring(0, 4) + "-" + time.substring(4, 6));
                            newCopyList.add(model);
                            if (i % 10000 == 0) {
                                service.insertBatch(newCopyList);
                                newCopyList = new ArrayList<AddressModel>();
                            }
                        }

                        if (ListUtil.isNotEmpty(newCopyList)) {
                            service.insertBatch(newCopyList);
                        }
                    }


                }
            } else {
                return JsonReturn.buildFailureWithEmptyBody();
            }
        } catch (Exception e) {
            return JsonReturn.buildFailure(e.getMessage());
        }
        return JsonReturn.buildSuccessWithEmptyBody();
    }

    private static List<AddressModel> readExcel(InputStream in, String state, int type) throws IOException {
        //得到整个excel对象
        XSSFWorkbook excel = new XSSFWorkbook(in);

        XSSFSheet sheet = excel.getSheetAt(0);
        XSSFRow title = sheet.getRow(0);

        XSSFCell xc = null;
        XSSFCell c = null;
        List<AddressModel> lists = new ArrayList<AddressModel>();

        //1. 遍历所有行
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow r = sheet.getRow(rowNum);

            int colNum = r.getLastCellNum();
            AddressModel model = new AddressModel();
            for (int j = 0; j < colNum; j++) {

                xc = title.getCell(j);
                c = r.getCell(j);
                if (xc == null) {
                    continue;
                }
                if (c == null) {
                    continue;
                }
                if ("".equals(c.toString())) {
                    continue;
                }
                String titleName = xc.getStringCellValue();
                //将申请号有的是数字，有的是字母，统一设置为字符串
                if ("申请号".equals(titleName)) {
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    model.setRequest_number(c.getStringCellValue());
                } else if ("授权入库日".equals(titleName) || "案卷入库日".equals(titleName)) {
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    model.setFile_entry_date(c.getStringCellValue());

                } else if ("申请日".equals(titleName)) {
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    model.setRequest_date(c.getStringCellValue());

                } else if ("专利权人邮编".equals(titleName) || "申请人邮编".equals(titleName)) {
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    model.setRequester_postcode(c.getStringCellValue());
                } else if ("专利权人地址".equals(titleName) || "申请人地址".equals(titleName)) {
                    model.setRequester_address(c.getStringCellValue());
                } else if ("专利类型".equals(titleName)) {
                    model.setPatent_type(c.getStringCellValue());

                } else if ("专利权人类型".equals(titleName) || "申请人类型".equals(titleName)) {
                    model.setRequester_type(c.getStringCellValue());

                } else if ("省份名称".equals(titleName)) {
                    model.setProvince(c.getStringCellValue());

                } else if ("城市名称".equals(titleName)) {
                    model.setCity(c.getStringCellValue());

                } else if ("申请方式名称".equals(titleName)) {
                    model.setChannel(c.getStringCellValue());
                } else if ("地区".equals(titleName) || "地址化区".equals(titleName)) {
                    model.setRegion(c.getStringCellValue());

                } else if ("四区一岛".equals(titleName)) {
                    model.setFunction_region(c.getStringCellValue());
                }else if("已标记地址化区".equals(titleName)){
                    model.setRegion_marked(c.getStringCellValue());
                } else if ("已标记四区一岛".equals(titleName)) {
                    model.setFunction_region_marked(c.getStringCellValue());
                } else {

                }
                model.setType(type);
                model.setState(Integer.valueOf(state));


            }
            lists.add(model);

        }
        return lists;
    }

}
