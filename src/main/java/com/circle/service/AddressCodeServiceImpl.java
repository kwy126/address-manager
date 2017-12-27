package com.circle.service;

import com.alibaba.fastjson.JSONObject;
import com.circle.dao.AddressCodeDAO;
import com.circle.dao.AddressDAO;
import com.circle.dao.FunctionCodeDAO;
import com.circle.dto.GaoDeDto;
import com.circle.util.address.AddressLngLatExchange;
import com.circle.util.address.Analyzer;
import com.circle.util.file.FileUtil;
import com.circle.util.http.HttpUtils;
import com.circle.util.json.JsonReturn;
import com.circle.util.mapper.JsonMapper;
import com.circle.util.mapper.XmlMapper;
import com.circle.util.random.IdGenerator;
import com.circle.vo.AddressCodeModel;
import com.circle.vo.AddressModel;
import com.circle.vo.FunctionCodeModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class AddressCodeServiceImpl extends BaseService<AddressCodeModel> implements IAddressCodeService {

    private final static String DATE = "20161001";

    private String pathName = "";

    @Value("#{code.dic}")
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    @Autowired
    private AddressCodeDAO dao;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private FunctionCodeDAO functionCodeDAO;

    public void translate() {
        List<AddressCodeModel> list = dao.selectByLike();
        for (AddressCodeModel addressCodeModel : list) {
            String name = addressCodeModel.getName();
            if (name.endsWith("村委会")) {
                name = name.replace("村委会", "村");
            }

            addressCodeModel.setName(name);
            dao.update(addressCodeModel);
        }
    }

    public void translateByLevel(int level) {
        List<AddressCodeModel> list = dao.selectByLevel(level);
        List<AddressCodeModel> addList = new ArrayList<AddressCodeModel>();
        for (AddressCodeModel addressCodeModel : list) {
            String name = addressCodeModel.getName();
            if (name.endsWith("街道")) {
                name = name.replace("街道", "镇");
                addressCodeModel.setName(name);
                addList.add(addressCodeModel);
            } else if (name.endsWith("镇")) {
                name = name.replace("镇", "街道");
                addressCodeModel.setName(name);
                addList.add(addressCodeModel);
            }
        }
        dao.insertBatch(addList);
    }

    public void translateByLevelAndType(int level, int type) {

        List<AddressCodeModel> list = dao.selectByLevelAndType(level, type);
        List<AddressCodeModel> addList = new ArrayList<AddressCodeModel>();
        for (AddressCodeModel model : list) {
            AddressCodeModel addressCodeModel = new AddressCodeModel();
            addressCodeModel.setName(model.getName());
            addressCodeModel.setDescription(model.getDescription());
            addressCodeModel.setUrl(model.getUrl());
            addressCodeModel.setLevel("6");
            addressCodeModel.setParent_code(model.getParent_code());
            addressCodeModel.setType(0);
            addList.add(addressCodeModel);

        }
        dao.insertBatch(addList);

    }

    public JsonReturn insertBatch(List<AddressCodeModel> lists) {
        dao.insertBatch(lists);
        return JsonReturn.buildSuccess("保存成功！");
    }

    public JsonReturn build() {
        List<AddressCodeModel> lists = dao.findAll();
        if (lists.size() == 0) {
            return JsonReturn.buildSuccess("关键词数据为0");
        }
        for (int i = 0; i < lists.size(); i++) {
            StringBuffer sb = new StringBuffer();
            String name = lists.get(i).getName();
            this.buildString(name, sb);
        }

        return JsonReturn.buildSuccess("保存成功");
    }

    public JsonReturn reorganize() {
        List<AddressCodeModel> list = dao.findAll();
        for (AddressCodeModel model : list) {
            List<AddressCodeModel> sameModels = dao.findByOnlyName(model.getName());
            if (sameModels.size() == 1) {
                AddressCodeModel addressCodeModel = sameModels.get(0);
                AddressCodeModel newModel = null;

                if (addressCodeModel.getLevel().equals("4")) {
                    String name = addressCodeModel.getName();
                    if (name.endsWith("街道")) {
                        List<AddressCodeModel> lists = new ArrayList<AddressCodeModel>();
                        name = name.replace("街道", "").trim();
                        List<AddressCodeModel> models = dao.findByOnlyName(name);
                        if (models == null || models.size() == 0) {
                            newModel = addressCodeModel;
                            newModel.setName(name);
                            lists.add(newModel);
                            dao.insertBatch(lists);
                        }
                    }
                    if (name.endsWith("镇")) {
                        List<AddressCodeModel> lists = new ArrayList<AddressCodeModel>();
                        name = name.replace("镇", "").trim();
                        List<AddressCodeModel> models = dao.findByOnlyName(name);
                        if (models == null || models.size() == 0) {
                            newModel = addressCodeModel;
                            newModel.setName(name);
                            lists.add(newModel);
                            dao.insertBatch(lists);
                        }
                    }
                }


                   /*  if (sameModels.get(0).getDescription().equals("浙江省,宁波市,江东区")) {
                         sameModels.get(0).setDescription("浙江省,宁波市,鄞州区");
                         sameModels.get(0).setOld_desc("浙江省,宁波市,江东区");
                         dao.update(sameModels.get(0));
                     }

                     if (sameModels.get(0).getDescription().equals("浙江省,宁波市,奉化区")) {
                         sameModels.get(0).setDescription("浙江省,宁波市,奉化区");
                         sameModels.get(0).setOld_desc("浙江省,宁波市,奉化市");
                         dao.update(sameModels.get(0));
                     }
                     String name = sameModels.get(0).getName();
                     if(name.equals("集士港镇")||name.equals("古林镇")||name.equals("高桥镇")||name.equals("横街镇")||name.equals("鄞江镇")||name.equals("洞桥镇")
                             ||name.equals("章水镇")||name.equals("龙观乡")||name.equals("石碶街道")){
                         sameModels.get(0).setDescription("浙江省,宁波市,海曙区");
                         sameModels.get(0).setOld_desc("浙江省,宁波市,鄞州区");
                         dao.update(sameModels.get(0));

                         List<AddressCodeModel> childs = dao.findByParentCode(sameModels.get(0).getRegion_code());
                         for(int j=0;j<childs.size();j++) {
                             AddressCodeModel model1 = childs.get(j);
                             model1.setDescription("浙江省,宁波市,海曙区");
                             model1.setOld_desc("浙江省,宁波市,鄞州区");
                             dao.update(model1);
                         }
                     }*/

            }
        }
        return JsonReturn.buildSuccess("整理成功！");
    }

    public JsonReturn add(String address, String level) {
        String retVal;
        StringBuffer sb = new StringBuffer();
        AddressCodeModel addressCodeModel = dao.findByName(address, level, "", 1);
        if (addressCodeModel == null) {

            AddressCodeModel model = new AddressCodeModel();
            model.setName(address);
            model.setRegion_code(IdGenerator.getId());

            String[] s = AddressLngLatExchange.getLngLatFromOneAddr("宁波" + address);
            Map<String, String> params = new HashMap<String, String>();
            params.put("longitude", s[0]);
            params.put("latitude", s[1]);

            String str = HttpUtils.URLGet("http://ditu.amap.com/service/regeo", params, "UTF-8");
            JSONObject obj = JSONObject.parseObject(str);
            String value = (String) obj.get("status");
            if (value.equals("1")) {
                String dataValue = obj.get("data").toString();
                JSONObject dataObject = JSONObject.parseObject(dataValue);
                String desc = dataObject.get("desc").toString();
                model.setDescription(desc);
            }
            sb.append(address);
            sb.append("\t");
            sb.append(level);
            sb.append("\t");
            if (level.equals("province")) {
                model.setLevel(1 + "");
                sb.append("1");
            } else if (level.equals("city")) {
                model.setLevel(2 + "");
                sb.append("2");
            } else if (level.equals("area")) {
                model.setLevel(3 + "");
                sb.append("3");
            } else if (level.equals("town")) {
                model.setLevel(4 + "");
                sb.append("4");
            } else if (level.equals("vil")) {
                model.setLevel(5 + "");
                sb.append("5");
            } else if (level.equals("road")) {
                model.setLevel(6 + "");
                sb.append("6");
            } else {
                model.setLevel("7");
                sb.append("7");
            }

            dao.insertRoad(model);
            FileUtil.writeToFile(sb.toString(), pathName);

            retVal = "保存成功！";
        } else {
            retVal = "地址已存在，不能重复保存";

        }
        return JsonReturn.buildSuccess(retVal);
    }

    private void buildString(String name, StringBuffer sb) {
        if (name.endsWith("村村委会")) {
            sb.append(name.replace("村委会", "\t"));
            sb.append("vil");
            sb.append("\t");
            sb.append("5");
        } else if (name.endsWith("村委会")) {
            sb.append(name.replace("委会", "\t"));
            sb.append("vil");
            sb.append("\t");
            sb.append("5");
        } else if (name.endsWith("社区") || name.endsWith("居委会") || name.endsWith("村")) {
            sb.append(name);

            sb.append("\t");
            sb.append("vil");
            sb.append("\t");
            sb.append("5");
        } else if (name.endsWith("街道") || name.endsWith("镇") || name.endsWith("乡")) {
            sb.append(name);
            sb.append("\t");
            sb.append("town");
            sb.append("\t");
            sb.append("4");
        } else if (name.endsWith("巷") || name.endsWith("路") || name.endsWith("大道") || name.endsWith("道") || name.endsWith("段") || name.endsWith("线") || name.endsWith("街") || name.endsWith("弄")) {
            sb.append(name);
            sb.append("\t");
            sb.append("road");
            sb.append("\t");
            sb.append("6");
        } else {
            sb.append(name);
            sb.append("\t");
            sb.append("a");
            sb.append("\t");
            sb.append("3");
        }
        name = sb.toString();
        FileUtil.writeToFile(name, pathName);
    }

    /**
     * @param model
     * @return
     */
    public String parse(AddressModel model) {
        int type = 1;
        StringBuffer stringBuffer = new StringBuffer();
        String address = model.getRequester_address();
        if (address == null || address.equals("")) {
            return "选中记录地址为空！";
        }

        String returnValue = null;
        if (model.getRequest_date().compareTo(DATE) < 0) {
            type = 0;
        }
        //1. 精确匹配
        List<AddressModel> lists = addressDAO.findByName(address, "1");
        if (lists.size() == 0) {
            //2. 提取待匹配地址
            Map<String, String> map = Analyzer.getToken(address, pathName);
            //3. 获取最小行政区域
            String road = map.get("road");
            String vil = map.get("vil");
            String town = map.get("town");
            String area = map.get("area");
            String province = map.get("province");
            String city = map.get("city");
            if (province != null && province.length() > 0) {
                stringBuffer.append(province);
                stringBuffer.append(",");
            }
            if (city != null && city.length() > 0) {
                stringBuffer.append(city);
                stringBuffer.append(",");
            }
            if (area != null && area.length() > 0) {
                stringBuffer.append(area);
                stringBuffer.append(",");
            }
            if (town != null && town.length() > 0) {
                stringBuffer.append(town);
                stringBuffer.append(",");
            }
            if (vil != null && vil.length() > 0) {
                stringBuffer.append(vil);
                stringBuffer.append(",");
            }

            if (road != null && road.length() > 0) {
                stringBuffer.append(road);
            }
            model.setRemark(stringBuffer.toString());

            // 3. 处理非宁波区域的地址
            if ((province != null && !province.equals("浙江省")) && (province != null && !province.equals("浙江"))) {
                model.setRegion("其他");
                model.setCreateTime(new Date());
                model.setState(1);

                addressDAO.update(model);
                return "解析成功";
            }
            if ((city != null && !city.equals("宁波市")) && (city != null && !city.equals("宁波"))) {
                model.setRegion("其他");
                model.setCreateTime(new Date());
                model.setState(1);
                addressDAO.update(model);
                return "解析成功";
            }

            // 4. 区为空
            if (area == null) {
                //假如area为null，则先确定area
                if (town != null && town != "" && town.length() > 0) {
                    List<AddressCodeModel> townAreas = dao.findByState(town, "4", "", type, 1);
                    if (townAreas != null && townAreas.size() >= 1) {
                        area = townAreas.get(0).getDescription().split(",")[2];
                    }
                    if (area != null && area.length() > 0) {
                        model.setRegion(area);

                        String townVal = this.judgeEqual("4", type, map, model);
                        if (townVal.equals("0")) {
                            updateFunctionCode(model,map);
                            return "解析成功！";
                        } else {
                            if (vil != null && vil != "" && vil.length() > 0) {
                                List<AddressCodeModel> requestAreas = dao.findByState(vil, "5", "", type, 1);
                                if (requestAreas != null && requestAreas.size() == 1) {

                                    area = requestAreas.get(0).getDescription().split(",")[2];
                                }
                                String vilValue = this.judgeEqual("5", type, map,model);
                                if (vilValue.equals("0")) {
                                    updateFunctionCode(model,map);
                                    return "解析成功！";
                                } else {
                                    if (road != null && road != "" && road.length() > 0) {
                                        List<AddressCodeModel> roadAreas = dao.findByState(road, "6", "", type, 1);
                                        if (roadAreas != null && roadAreas.size() == 1) {

                                            area = roadAreas.get(0).getDescription().split(",")[2];
                                        }
                                        String value = this.judgeEqual("6", type, map, model);
                                        if (value.equals("0")) {
                                            updateFunctionCode(model,map);
                                            return "解析成功！";
                                        } else {
                                            return "解析失败！";
                                        }
                                    }else{
                                        this.invokeAPI(model,map);
                                        return "解析成功!";
                                    }
                                }
                            }
                        }
                    }else{
                        this.invokeAPI(model,map);
                        return "解析成功!";
                    }


                } else if (town == null && vil != null && vil != "" && vil.length() > 0) {
                    List<AddressCodeModel> requestAreas = dao.findByState(vil, "5", "", type, 1);
                    if (requestAreas != null && requestAreas.size() == 1) {

                        area = requestAreas.get(0).getDescription().split(",")[2];
                    }

                    if (area != null && area.length() > 0) {
                        model.setRegion(area);
                        String vilValue = this.judgeEqual( "5", type, map,model);
                        if (vilValue.equals("0")) {
                            updateFunctionCode(model,map);
                            return "解析成功！";
                        } else {
                            if (road != null && road != "" && road.length() > 0) {
                                List<AddressCodeModel> roadAreas = dao.findByState(road, "6", "", type, 1);
                                if (roadAreas != null && roadAreas.size() == 1) {

                                    area = roadAreas.get(0).getDescription().split(",")[2];
                                }
                                String value = this.judgeEqual("6", type, map, model);
                                if (value.equals("0")) {
                                    updateFunctionCode(model,map);
                                    return "解析成功！";
                                } else {
                                    // return "解析失败！";
                                    this.invokeAPI(model,map);
                                    return "解析成功!";
                                }
                            }else{
                                this.invokeAPI(model,map);
                                return "解析成功!";
                            }
                        }
                    }else{
                        this.invokeAPI(model,map);
                        return "解析成功!";
                    }


                } else if (town == null && vil == null && road != null && road != "" && road.length() > 0) {
                    List<AddressCodeModel> roadAreas = dao.findByState(road, "6", "", type, 1);
                    if (roadAreas != null && roadAreas.size() == 1) {

                        area = roadAreas.get(0).getDescription().split(",")[2];
                    }
                    if (area != null && area.length() > 0) {
                        model.setRegion(area);
                        String value = this.judgeEqual("6", type, map, model);
                        if (value.equals("0")) {
                            updateFunctionCode(model,map);
                            return "解析成功！";
                        } else {
                            this.invokeAPI(model,map);
                            return "解析成功!";
                            // return "解析失败！";
                        }
                    }else{
                        this.invokeAPI(model,map);
                        return "解析成功!";
                    }

                } else {
                    this.invokeAPI(model,map);
                    return "解析成功!";
                }
            }else if (area != null && area != "" && area.length() > 0) {
                if (area.equals("奉化市")) {
                    area = "奉化区";
                } else if (area.equals("江东区")) {
                    area = "鄞州区";
                }
                model.setRegion(area);

                List<AddressCodeModel> models = dao.findByAreaAndNameAndLevel(area, "", "3");
                if (models == null || models.size() == 0) {
                    model.setRegion("其他");
                    model.setCreateTime(new Date());
                    model.setState(1);
                    addressDAO.update(model);
                    return "解析成功";
                }

                if (town != null && town != "" && town.length() > 0) {
                    String townVal = this.judgeEqual( "4", type,map,model);
                    if (townVal.equals("0")) {
                        updateFunctionCode(model,map);
                        return "解析成功！";
                    } else {
                        if (vil != null && vil != "" && vil.length() > 0) {
                            String vilValue = this.judgeEqual("5", type, map, model);
                            if (vilValue.equals("0")) {
                                updateFunctionCode(model,map);
                                return "解析成功！";
                            } else {
                                if (road != null && road != "" && road.length() > 0) {
                                    String value = this.judgeEqual("6", type, map, model);
                                    if (value.equals("0")) {
                                        updateFunctionCode(model,map);
                                        return "解析成功！";
                                    } else {
                                        this.invokeAPI(model,map);
                                        return "解析成功!";
                                        //  return "解析失败！";
                                    }
                                }else{
                                    this.invokeAPI(model,map);

                                    return "解析成功!";
                                }
                            }
                        }else if (town == null && vil == null && road != null && road != "" && road.length() > 0) {
                            String value = this.judgeEqual("6", type, map, model);
                            if (value.equals("0")) {
                                updateFunctionCode(model,map);
                                return "解析成功！";
                            } else {
                                this.invokeAPI(model,map);
                                return "解析成功!";
                                // return "解析失败！";
                            }
                        } else {
                            this.invokeAPI(model,map);
                            return "解析成功!";
                        }
                    }
                } else if (town == null && vil != null && vil != "" && vil.length() > 0) {
                    String vilValue = this.judgeEqual( "5",type, map, model);
                    if (vilValue.equals("0")) {
                        updateFunctionCode(model,map);
                        return "解析成功！";
                    } else {
                        if (road != null && road != "" && road.length() > 0) {
                            String value = this.judgeEqual( "6", type, map, model);
                            if (value.equals("0")) {
                                updateFunctionCode(model,map);
                                return "解析成功！";
                            } else {
                                this.invokeAPI(model,map);
                                return "解析成功!";
                                //  return "解析失败！";
                            }
                        }else{
                            this.invokeAPI(model,map);
                            return "解析成功!";
                        }
                    }
                } else if (town == null && vil == null && road != null && road != "" && road.length() > 0) {
                    String value = this.judgeEqual("6", type, map, model);
                    if (value.equals("0")) {
                        updateFunctionCode(model,map);
                        return "解析成功！";
                    } else {
                        this.invokeAPI(model,map);
                        return "解析成功!";
                        // return "解析失败！";
                    }
                } else {
                    this.invokeAPI(model,map);
                    return "解析成功!";
                }
            }
        } else {
            model.setFunction_region(lists.get(0).getFunction_region());
            model.setRegion(lists.get(0).getRegion());
            model.setProvince(lists.get(0).getProvince());
            model.setCity(lists.get(0).getCity());
            model.setState(1);
            model.setRemark(lists.get(0).getRemark());
            model.setCreateTime(new Date());
            this.match(model);
            addressDAO.update(model);
            returnValue += " 解析成功！";
        }

        return returnValue;
    }

    public void updateFunctionCode(AddressModel model,Map<String,String> map) {
        String town = map.get("town");
        String vil = map.get("vil");
        String road = map.get("road");
        String area = model.getRegion();
        String address = model.getRequester_address();
        // 更新功能区
        FunctionCodeModel functionCodeModel = null;

        if (address.indexOf("杭州湾新区") > 0) {
            model.setFunction_region("杭州湾新区");
        } else if (address.indexOf("东钱湖") > 0) {
            model.setFunction_region("东钱湖旅游度假区");

        } else if (address.indexOf("高新区") > 0) {
            model.setFunction_region("高新区");
        } else if (address.indexOf("大榭") > 0) {
            model.setFunction_region("大榭开发区");
        } else if (address.indexOf("保税区") > 0 ||address.indexOf("保税东区") > 0) {
            model.setFunction_region("保税区");
        } else {
            if (town != null) {
                functionCodeModel = functionCodeDAO.getFunctionByName(town, "4", area);
            } else if (vil != null) {
                functionCodeModel = functionCodeDAO.getFunctionByName(vil, "5", area);
            } else {
                functionCodeModel = functionCodeDAO.getFunctionByName(road, "6", area);
            }

            if (functionCodeModel != null) {
                model.setFunction_region(functionCodeModel.getParent());
            }

        }

        model.setState(1);
        model.setCreateTime(new Date());
        this.match(model);
        addressDAO.update(model);

    }

    public JsonReturn parse(String address) {
        StringBuffer sb = new StringBuffer();

        //1. 精确匹配
        List<AddressModel> lists = addressDAO.findByName(address, "1");
        if (lists.size() == 0) {
            //2. 提取待匹配地址
            Map<String, String> map = Analyzer.getToken(address, pathName);
            //3. 获取最小行政区域
            String road = map.get("road");
            String vil = map.get("vil");
            String town = map.get("town");
            String area = map.get("area");
            String province = map.get("province");
            String city = map.get("city");
            sb.append(map.toString());
            if (area == null || area.length() == 0) {
                String[] s = AddressLngLatExchange.getLngLatFromOneAddr(address);
                Map<String, String> params = new HashMap<String, String>();
                params.put("longitude", s[0]);
                params.put("latitude", s[1]);

                String str = HttpUtils.URLGet("http://ditu.amap.com/service/regeo", params, "UTF-8");
                JSONObject obj = JSONObject.parseObject(str);
                String value = (String) obj.get("status");
                if (value.equals("1")) {
                    String dataValue = obj.get("data").toString();
                    JSONObject dataObject = JSONObject.parseObject(dataValue);
                    String desc = dataObject.get("desc").toString();
                    sb.append(desc);
                }
            } else {
                sb.append(province).append(",").append(city).append(",").append(area);

            }
        }
        return JsonReturn.buildSuccess(sb.toString());
    }

    private String judgeEqual(String level, int type, Map<String, String> map, AddressModel model) {
        String area = model.getRegion();
        String name = "";
        String address = model.getRequester_address();
        String vil = map.get("vil");
        String town = map.get("town");
        String retVal = "1";

        if (area != null) {
            if (area.equals("奉化市")||area.equals("奉化")) {
                area = "奉化区";
            } else if (area.equals("高新区") || area.equals("东钱湖旅游度假区")||area.equals("鄞州")) {
                area = "鄞州区";
            } else if (area.equals("保税区") || area.equals("大榭开发区")||area.equals("北仑")) {
                area = "北仑区";
            } else if (area.equals("杭州湾新区")||area.equals("慈溪")) {
                area = "慈溪市";
            } else if (area.equals("江东区") || area.equals("江东")) {
                area = "鄞州区";
            } else if (area.equals("象山")) {
                area = "象山县";
            } else if (area.equals("海曙")) {
                area = "海曙区";
            } else if (area.equals("江北")) {
                area = "江北区";
            }else if (area.equals("镇海")) {
                area = "镇海区";
            }else if (area.equals("余姚")) {
                area = "余姚市";

            } else if (area.equals("宁海")) {
                area = "宁海县";
            }
            model.setRegion(area);
        }

        if (level != null) {
            if (level.equals("6")) {
                name = map.get("road");
            } else if (level.equals("5")) {
                name = map.get("vil");
            } else if (level.equals("4")) {
                name = map.get("town");
            }
        }

        if (area.equals("鄞州区")) {
            List<AddressCodeModel> addressCodeModelList = dao.findByAreaAndNameAndLevel(name, area, level);
            if (addressCodeModelList == null || addressCodeModelList.size() == 0) {
                addressCodeModelList = dao.findByAreaAndNameAndLevel(name, "海曙区", level);
                if (addressCodeModelList != null && addressCodeModelList.size() > 0) {
                    model.setRegion("海曙区");
                    retVal = "0";
                }
            }else{
                retVal = "0";
            }
        }else{
            List<AddressCodeModel> addressCodeModelList = dao.findByAreaAndNameAndLevel(name, area, level);
            if (addressCodeModelList == null || addressCodeModelList.size() == 0) {
                    retVal = "1";
            }else{
                retVal = "0";
            }
        }

        return retVal;

        //如果地址中不存在区信息，则根据街道信息查询区信息，再将该街道信息和该区信息查询，如果查询到该记录，则查询到的区信息成立
        //如果区信息写错了，则根据街道信息查询区信息，再将该街道信息和该区信息查询，如果查询到该记录，则查询到的区信息成立
        //如果地址库里不存在路信息，则需要查询村信息，依次类推
    }

    private void invokeAPI(AddressModel model,Map<String,String> map) {
        //String[] s = AddressLngLatExchange.getLngLatFromOneAddr(model.getRequester_address());

        Map<String, String> params = new HashMap<String, String>();
        params.put("output", "JSON");
        params.put("address", model.getRequester_address());
        params.put("key", "ccf1bf3d1a6294bba2dc2caf85366452");

        if (StringUtils.isNotEmpty(model.getCity())) {
            params.put("city", model.getCity());
        }

        String str = HttpUtils.URLGet("http://restapi.amap.com/v3/geocode/geo", params, "UTF-8");

        JsonMapper mapper = new JsonMapper();
        GaoDeDto dto = mapper.fromJson(str, GaoDeDto.class);
        if (StringUtils.isNotEmpty(str)) {
            if (dto.getGeocodes().size() == 1) {

                model.setRegion(dto.getGeocodes().get(0).getDistrict());
                updateFunctionCode(model, map);
            }
        }



          /*  int id = model.getId();
            if (Integer.valueOf(id) != null) {
                dao.setDesc(desc, id);
            }

            if (dataObject == null || dataObject.get("road_list") == null) {
                return;
            }

            String roadList = dataObject.get("road_list").toString();
            List<Road> roads = JSONArray.parseArray(roadList, Road.class);
            for (Road road : roads) {
                String reg = "[0-9]*弄";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(road.getName());
                boolean rs = matcher.find();
                if (rs) {
                    int end = matcher.start();
                    model.setName(road.getName().substring(0, end));
                }else{
                    model.setName(road.getName());
                }

                model.setRegion_code(IdGenerator.getId());
                model.setLevel(6 + "");
                model.setDescription(desc);
                model.setUrl("");
            }*/

    }

    public JsonReturn updateAddressCode() {
     /*   List<AddressCodeModel> lists = dao.findAll();
        for (AddressCodeModel model : lists) {
           *//* if (model.getDescription() != null && model.getDescription().length() > 0) {
                continue;
            }*//*
            this.invokeAPI("宁波市" + model.getName(), model);

        }*/
        return JsonReturn.buildSuccess("保存成功！");
    }


    private AddressModel match(AddressModel model) {
        if (model.getRegion_marked() != null && model.getRegion() != null &&
                model.getRegion_marked().trim().equals(model.getRegion().trim())) {
            model.setIsRight(1);
        } else if ((model.getRegion_marked() != null && model.getRegion() == null)
                || (model.getRegion_marked() == null && model.getRegion() != null)) {
            model.setIsRight(2);
        } else if (model.getRegion_marked() != null && model.getRegion() != null
                && !model.getRegion_marked().trim().equals(model.getRegion().trim())) {
            model.setIsRight(0);
        } else if (model.getRegion_marked() == null) {
            model.setFunctionIsMatch(1);
        }

        if (model.getFunction_region_marked() != null && model.getFunction_region() != null
                && model.getFunction_region_marked().trim().equals(model.getFunction_region().trim())) {
            model.setFunctionIsMatch(1);
        } else if ((model.getFunction_region_marked() != null && model.getFunction_region() == null)
                || (model.getFunction_region_marked() == null && model.getFunction_region() != null)) {
            model.setFunctionIsMatch(2);
        } else if (model.getFunction_region_marked() != null && model.getFunction_region() != null
                && !model.getFunction_region_marked().trim().equals(model.getFunction_region().trim())) {
            model.setFunctionIsMatch(0);
        } else if (model.getFunction_region_marked() == null) {
            model.setFunctionIsMatch(1);
        }
        return model;
    }


}
