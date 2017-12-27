package com.circle.service;

import com.circle.constant.PageConstant;
import com.circle.dao.AddressDAO;
import com.circle.dao.StaticsDAO;
import com.circle.dto.ChartStaticsDto;
import com.circle.utils.json.JsonReturn;
import com.circle.utils.pageutil.PageUtils;
import com.circle.vo.AddressModel;
import com.circle.vo.StaticsDto;
import com.circle.vo.StaticsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Service
@Transactional
public class AddressServiceImpl extends BaseService<AddressModel> implements IAddressService{

    @Autowired
    private AddressDAO dao;

    @Autowired
    private StaticsDAO staticsDAO;

    @Autowired
    private IAddressCodeService service;

    public JsonReturn insertBatch(List<AddressModel> lists) {
        dao.insertBatch(lists);
        return JsonReturn.buildSuccess("保存成功");
    }

    public JsonReturn findAddressList(String search, int page, int pageSize, String state,int type, String s) {
        Map<String,Object> result = new HashMap<String,Object>();
        int count = dao.findAddressPage(search,Integer.valueOf(state),type);
        List<AddressModel> addressModelList = dao.findAddressList(search, (page - 1) * pageSize, pageSize,state,type);

        result.put("total",count);
        result.put("rows",addressModelList);
        return JsonReturn.buildSuccess(result);
    }

    public JsonReturn findAddressListByPage(String search, int page, int state, int type) {
        List<AddressModel> addressModelList = dao.findAddressListByPage(search, (page - 1) * PageConstant.DEFAULT_LINE, PageConstant.DEFAULT_LINE,state,type);
        if (CollectionUtils.isEmpty(addressModelList)) {
            return JsonReturn.buildFailure("未获取相关数据！");
        }
        return JsonReturn.buildSuccess(addressModelList);
    }

    public JsonReturn findAddressPage(String search, int page, int state, int type) {
        int count = dao.findAddressPage(search,state,type);
        return JsonReturn.buildSuccess(PageUtils.calculatePage(page,count,PageConstant.DEFAULT_LINE));
    }

    public JsonReturn parseAddress(Integer[] ids, final String operator) {

        long begin = System.currentTimeMillis();

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1000);
        Map<Integer, Future<?>> jobFutureMap = new HashMap<Integer, Future<?>>();
        if (ids == null || ids.length < 1) {
            return JsonReturn.buildSuccess("请至少选择一项！");
        }
       // StringBuffer sb = new StringBuffer();
        for (final Integer id : ids) {
            Future<?> future = fixedThreadPool.submit(new Runnable() {
                public void run() {
                    AddressModel model = dao.findById(id, "0");
                    model.setCreator(operator);
                    try {
                        service.parse(model);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            jobFutureMap.put(id, future);
        }

        for (final Integer id : ids) {
            Future<?> future = jobFutureMap.get(id);
            try {
                future.get();//调用此方法住线程等待子线程完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        fixedThreadPool.shutdown();
        long end = System.currentTimeMillis();
        return JsonReturn.buildSuccess(ids.length +" 条数据 花费 "+ (end-begin)/60000 + "分钟");
    }

    public JsonReturn deleteAddress(Integer[] ids) {
        if (ids == null || ids.length < 1) {
            return JsonReturn.buildSuccess("请至少选择一项！");
        }
        StringBuffer sb = new StringBuffer();
        for (Integer id : ids) {
            dao.delete(id);
        }
        return JsonReturn.buildSuccess("删除成功！");
    }

    public JsonReturn parseAllAddress(int type,String operator) {
        List<AddressModel> list = dao.findByUnParse(type, "0");
        Integer[] ids = new Integer[list.size()];
        for(int i=0;i<list.size();i++) {
            ids[i] = list.get(i).getId();
        }
        return this.parseAddress(ids,operator);
    }

    public JsonReturn getStatics(int type) {
        List<StaticsDto> staticsDtos = dao.getAllRegions(type);
        Map<String, List<StaticsDto>> map = new HashMap<String, List<StaticsDto>>();
        for (StaticsDto staticsDto : staticsDtos) {
            List<StaticsDto> values = new ArrayList<StaticsDto>();
            if (staticsDto.getRegion()!=null && map.get(staticsDto.getRegion()) != null ) {

                values.add(staticsDto);

            }
            map.put(staticsDto.getRegion(), values);
        }



        Iterator<Map.Entry<String, List<StaticsDto>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<StaticsDto>> entry = it.next();
            List<StaticsDto> values = entry.getValue();
            StaticsModel model = new StaticsModel();
            if (entry.getKey() != null && entry.getKey().length() > 0) {
                model.setRegion(entry.getKey());
                for (StaticsDto staticsDto : values) {
                    if ("发明".equals(staticsDto.getPatent_type())) {
                        model.setInvention_number(staticsDto.getSum());

                    } else if ("外观设计".equals(staticsDto.getPatent_type())) {
                        model.setDesign_number(staticsDto.getSum());

                    } else if ("实用新型".equals(staticsDto.getPatent_type())) {
                        model.setUse_number(staticsDto.getSum());

                    }

                }
                model.setCreate_date(new Date());
                model.setType(type);
                model.setSum(model.getDesign_number()+model.getInvention_number()+model.getUse_number());
                if (staticsDAO.getByRegion(entry.getKey(),type) != null) {
                    staticsDAO.update(model);
                }else{
                    staticsDAO.insert(model);
                }
            }


        }

        Map<String,Object> result = new HashMap<String,Object>();
        List<StaticsModel> staticsModelList = staticsDAO.getAllStatics(type);
     /*   result.put("total",staticsModelList.size());
        result.put("rows",staticsModelList);*/

        return JsonReturn.buildSuccess(staticsModelList);
    }

    public JsonReturn getDataPrecision(int type,Integer functionOrRegion,String time) {

        if (time.equals("")) {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
             time=sdf.format(new java.util.Date());
        }
        String[] str = this.getTimeRange(time);

        String start = str[0];
        String end = str[1];

        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();
        Map<String, String> map3 = new HashMap<String, String>();
        int rightNumber = 0;
        int wrongNumber = 0;
        int otherNumber = 0;
        if (functionOrRegion == 0) {
            rightNumber = dao.getRegionPrecision(type, 1,time);
            wrongNumber = dao.getRegionPrecision(type, 0, time);
            otherNumber = dao.getRegionPrecision(type, 2, time);
        }else{
            rightNumber = dao.getFunctionPrecision(type, 1, time);
            wrongNumber = dao.getFunctionPrecision(type, 0, time);
            otherNumber = dao.getFunctionPrecision(type, 2, time);
        }
        map.put("name", "正确数");
        map.put("value", rightNumber+"");
        Map[] maps = {map, map2, map3};
        map2.put("name", "错误数");
        map2.put("value", wrongNumber+"");
        map3.put("name", "其他情况");
        map3.put("value", otherNumber+"");

        return JsonReturn.buildSuccess(maps);
    }

    public JsonReturn getChartStatics(int type) {
        List<StaticsModel> staticsModelList = staticsDAO.getAllStatics(type);

        List<ChartStaticsDto> chartStaticsDtos = new ArrayList<ChartStaticsDto>();
        for (StaticsModel model : staticsModelList) {
            ChartStaticsDto dto = new ChartStaticsDto();
            dto.setName(model.getRegion());
            dto.setValue(model.getSum());
            chartStaticsDtos.add(dto);
        }

        return JsonReturn.buildSuccess(chartStaticsDtos);
    }

    public List<AddressModel> getDataByTypeAndTime(int type, String time) {
      //  String[] str = this.getTimeRange(time);
        List<AddressModel> list = dao.getDataByTypeAndTime(type, time);
        return list;
    }

    private String[] getTimeRange(String time) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");//小写的mm表示的是分钟
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = null;
        long begin = 0;
        long end = 0;
        try {
            now = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date firstDayOfMonth = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, 0);
            Date lastDayOfMonth = calendar.getTime();
            begin = firstDayOfMonth.getTime();
            end = lastDayOfMonth.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new String[]{ format.format(new Date(begin)), format.format(new Date(end))};

    }

}
