package com.circle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.circle.utils.net.HttpUtil;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.*;

/**
 * @author KeWeiYang
 * @date 2018/2/28 15:32
 */
public class TestCnipr {

    private static final String CLIENT_ID = "343A1E6D6C7C82AAC2649BF50B351960";
    private static final String CLIENT_SECRET = "CA194195565F0932BE61C07F3A7169D8";
    private static final String URL = "http://open.cnipr.com";

    private static final String ACCESS_TOKEN = "432ae2544c668b3f9317dd96342cd422";
    private static final String OPEN_ID = "1f147687f97c629b04442cdeb9501d6e";
    private  Queue<CniprPatent> patentQueue = new LinkedList<CniprPatent>();

    public void login() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_account", "nbstipis");
        params.put("user_password", "nbsti87811024");
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("grant_type", "password");
        String str = HttpUtil.URLPost(URL + "/oauth2/json/user/login", params, "UTF-8");
        System.out.println(str);

    }


    public void search() throws SQLException {
        Map<String, String> params = new HashMap<String, String>();
//        params.put("exp", "地址=(浙江省宁波)and 公开（公告）日=(20171001 to 20171031)");
        params.put("exp", "(分类号=('b62d23%' or 'b62d25%' or 'b62d27%' or 'b62d29%' or 'b62d31%' or 'b62d33%' or 'b62d35%' or 'b62d37%' or 'b62d39%' or 'b60j5%' or 'b60j1/%' or 'b60r19%' or 'b60r21%' or 'b60r22%' or 'b60k28%' or 'b60n2/42%' or 'b62d1/11%' or 'b62d1/19%' or 'b62d1/18%' or 'b60n2/48%' or 'a62c3/07' or 'b60j7%' or 'b60r13/02' or 'b60r13/08' or 'b60j10/10' or 'b60j10/12' or 'b60p%' or 'e05b65/12' or 'e05b65/14' or 'e05b65/16' or 'e05b65/18' or 'e05b65/19' or 'e05b65/2%' or 'e05b65/3%' or 'e05b65/40' or 'e05b65/42' or 'b60s1/%' or 'b60n3%' or 'b60n5%' or 'b60j10%' or 'b60j11%' or 'b60h%' or 'b60r5%' or 'b60r7%' or 'b60r9%' or 'b60r11%')) or (名称=(车身 or 裙板 or 侧裙 or 定风翼 or 行李箱盖 or 行李舱盖 or 发动机舱盖 or 发动机盖 or 引擎盖 or 翼子板 or 叶子板) and 分类号=('b60%' or 'b62d%' or 'b32b%' or 'b29c%' or 'b21d%' or 'b23k%' or 'c22%' or 'c23%' or 'c25%' or 'c08%' or 'c09%' or '12-16%' or '12-08%' or '12-13%' or '15-01%')) or (名称,摘要,主权项+=(车门) and 分类号=('b32b%' or 'b21%' or 'g01%' or 'b60r21%' or 'e05%' or 'b60j%' or '12-16%' or '12-08%' or '25-01%')) or (名称,摘要,主权项+=(车窗 or 门窗 or 风窗 or 角窗 or 侧窗 or 顶窗 or 天窗 or 玻璃窗 or 电动窗 or 前窗 or 后窗 or 百叶窗 or 纱窗 or 通风窗) and 分类号=('b60s1/%' or 'b60j%' or 'b60h%' or '12-16%')) or (名称,摘要,主权项+=((门窗 or 风窗 or 角窗 or 侧窗 or 顶窗 or 天窗 or 玻璃窗 or 电动窗 or 前窗 or 后窗 or 百叶窗 or 纱窗 or 通风窗) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=(e05%)) or (名称,摘要,主权项+=(车窗) and 分类号=(e05%)) or (名称,摘要,主权项+=(保险杠 or 保险杆) and 分类号=('b60t7%' or 'b62d%' or '12-16%')) or (名称,摘要,主权项+=((保险杠 or 保险杆) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('b29c%' or 'c08f%' or 'c08l%' or 'b23p11%' or 'b21d%' or 'f27b%' or 'h05b%' or 'g01d5%' or 'b25b%' or 'b65g%')) or (名称,摘要,主权项+=(安全带) and 分类号=('b60q%' or '12-16%')) or (名称,摘要,主权项+=((安全带) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('29-02%' or 'a44b11%' or 'a62b%' or 'a01k%' or 'g01%')) or (名称,摘要,主权项+=(气囊 or 气帘 or 气体发生器) and 分类号=('12-16%' or 'b60n%' or 'b60c%')) or (名称,摘要,主权项+=((气囊 or 气帘 or 气体发生器) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('c01b%' or 'b21%' or 'c06d%' or 'c08%' or 'd01d%' or '06-09%' or '08-08%')) or (名称,摘要,主权项+=(头枕) and 分类号=(b60%)) or (名称,摘要,主权项+=((头枕) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('06-09%' or '06-01%' or 'a47c%' or 'b21d%' or 'g01%' or 'b23p%')) or (名称,摘要,主权项+=(玻璃) and 分类号=('b60j%' or '12-16%')) or (名称,摘要,主权项+=((玻璃) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('c03%' or '25-01%')) or (名称,摘要,主权项+=(地板 or 底板) and 分类号=('b60n%' or 'b60r%' or '12-16%')) or (名称,摘要,主权项+=(顶盖 or 车顶 or 顶板 or 篷盖 or 车篷 or 车棚 or 厢棚 or 顶棚 or 顶篷) and 分类号=('b62d%' or 'b60r%' or 'b60j%' or '12-16%')) or (名称,摘要,主权项+=(侧围 or 侧%板) and 分类号=('b60j%' or 'b60r%' or 'b62d%' or '12-08%' or '12-16%')) or (名称,摘要,主权项+=((侧围 or 侧%板) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('b21d%' or 'b23k%')) or (名称,摘要,主权项+=(车厢 or 车箱) and 分类号=('b62d%' or 'b60%' or '12-16%' or '12-08%' or 'b23k%' or 'b60p%' or 'g01%' or 'b32b%')) or (名称,摘要,主权项+=((车厢 or 车箱) and (倾斜 or 倾卸)) and 分类号=('b62d%' or 'b60%')) or (名称,摘要,主权项+=(门%锁) and 分类号=('b62d%' or 'b60%' or '12-16%')) or (名称,摘要,主权项+=(车 and 门%锁) and 分类号=('e05b%' or 'b23p%' or 'g01%' or 'g06k%' or '08-07%' or '14-03%')) or (名称,摘要,主权项+=(门%铰链) and 分类号=('b62d%' or 'b60%' or '12-16%')) or (名称,摘要,主权项+=(车 and 门%铰链) and 分类号=('e05d%' or 'g01%' or 'f16b%' or '08-06%')) or (名称,摘要,主权项+=(刮水 or 雨刷 or 雨刮 or 除雨) and 分类号=('b60%' or '12-16%')) or (名称,摘要,主权项+=((刮水 or 雨刷 or 雨刮 or 除雨) and (车窗 or 汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('08-08%' or 'g01%' or 'h02k%' or 'c08l%' or '13-01%')) or (名称,摘要,主权项+=((窗 or 玻璃 or 风挡) and (洗涤 or 喷水 or 清洗)) and 分类号=('b60%' or '12-16%')) or (名称,摘要,主权项+=((窗 or 玻璃 or 风挡) and (除霜 or 清霜 or 除雾 or 消雾 or 防雾)) and 分类号=('b60%' or '12-16%')) or (名称,摘要,主权项+=(车窗 or 汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车) and 分类号=('h05b3/84' or 'h05b3/86')) or (名称,摘要,主权项+=((玻璃 or 车窗) and 升降) and 分类号=('b60j%' or 'e05f%' or 'g01m%' or 'h01h%' or 'h02k%' or '13-01%' or '12-05%' or '12-16%' or '08-05%' or '13-03%')) or (名称,摘要,主权项+=(天线) and 分类号=('b60%' or '12-16%')) or (名称,摘要,主权项+=(天线 and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车 or 车载)) and 分类号=('h05k%' or 'h01q%' or 'g01%' or '14-03%')) or (名称,摘要,主权项+=(点烟) and 分类号=('b60%' or '12-16%')) or (名称,摘要,主权项+=(点烟 and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车 or 车载)) and 分类号=('a24f%' or 'f23q%' or 'h01r%' or 'f21v%' or '27-05%' or '13-02%')) or (名称,摘要,主权项+=((玻璃 or 车窗 or 门窗 or 风窗) and (密封)) and 分类号=('b60j%' or 'b62d%' or '12-16')) or (名称,摘要,主权项+=(((玻璃 or 门窗 or 风窗) and (密封)) and (车窗 or 汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车)) and 分类号=('e06b7%' or 'b29c%' or '08-08%' or '25-99%')) or (名称,摘要,主权项+=(空调 or 通风 or 空气 or 暖风 or 暖通 or 暖器 or 暖气 or 采暖 or 加热 or 制冷 or 致冷 or 冷气 or 加湿 or 芳香 or 香薰 or 香味 or 香气) and 分类号=('f02d%' or 'b60n%' or 'b60p%' or 'b60l%' or 'b60w%' or 'b60s%' or 'b60r%' or '12-16%')) or (名称,摘要,主权项+=((空调 or 通风 or 空气 or 暖风 or 暖通 or 暖器 or 暖气 or 采暖 or 加热 or 制冷 or 致冷 or 冷气 or 加湿 or 芳香 or 香薰 or 香味 or 香气) and (汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车 or 车载)) and 分类号=('f04%' or 'f16%' or 'f28%' or 'f24f%' or 'f25%' or 'a47c%' or 'b21%' or 'b23p%' or 'h01h%' or 'h02k%' or 'h05b%' or 'g01%' or '23-01%' or '23-03%' or '23-04%' or '15-02%' or '13-01%' or '28-03%' or '28-99%' or '07-07%')) not (分类号=(b60g%))");
        params.put("dbs", "FMZL");
//        params.put("client_id", CLIENT_ID);
        params.put("openid", OPEN_ID);
        params.put("access_token", ACCESS_TOKEN);
        params.put("order", "LIFO");
        params.put("from", "1001");
        params.put("to", "2000");
        String str = HttpUtil.URLPost(URL + "/cnipr-api/rs/api/search/sf26/" + CLIENT_ID, params, "UTF-8");
        System.out.println(str);
        Object obj = JSON.parse(str);
        int total = ((JSONObject) obj).getIntValue("total");
        if (total > 5) {

            str = HttpUtil.URLPost(URL + "/cnipr-api/rs/api/search/sf26/" + CLIENT_ID, params, "UTF-8");

            obj = JSON.parse(str);
            List<CniprPatent> cniprPatentList = JSONArray.parseArray(((JSONObject) obj).getString("results"), CniprPatent.class);
            System.out.println(cniprPatentList.size());

            Connection conn = DataUtil.getConnection();

            for (CniprPatent patent : cniprPatentList) {

                insertSQL(conn, patent);
                //findDetail(patent);
            }
        }
    }

    public void findDetail() {

        PreparedStatement stmt = null;
        Connection conn = DataUtil.getConnection();
        StringBuffer sb = new StringBuffer();
        int i = 0;
        try {
            stmt = conn.prepareStatement("select * from te_cnipr_patent where id>15032 and id<15232");
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                i++;
                if (i < 50) {
                    sb.append(set.getString("pubNumber")).append(",");
                } else {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("pubNumber", sb.toString());
                    params.put("displayCols", "Basic,Legal,Transfer,Exploitation,Preservation,Fee,Family,rantDate,grantDate,expireDate,iapp,ipub,den,relevance,legalStatus,initMainIpc,initIpc,categoryType,patNo,eupMainIpc,eupIpc,pdfAddr");
                    params.put("openid", OPEN_ID);
                    params.put("access_token", ACCESS_TOKEN);
//                    Thread.sleep(30);
                    String str = HttpUtil.URLPost(URL + "/cnipr-api/rs/api/download/dl3/" + CLIENT_ID, params, "UTF-8");

                    System.out.println(str);
                    Object obj = JSON.parse(str);
                    List<CniprPatent> cniprPatentList = JSONArray.parseArray(((JSONObject) obj).getString("results"), CniprPatent.class);

                    System.out.println(cniprPatentList.size());


                    for (CniprPatent cniprPatent : cniprPatentList) {

                        patentQueue.add(cniprPatent);
                    }

                    this.init(cniprPatentList);

                    i = 0;
                    sb = new StringBuffer();
                }


            }
        } catch (MySQLIntegrityConstraintViolationException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void insertSQL(Connection conn, CniprPatent patent) {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into te_cnipr_patent(appNumber,pubNumber,dbName,type) values(?,?,?,?)");
            stmt.setString(1, patent.getAppNumber());
            stmt.setString(2, patent.getPubNumber());
            stmt.setString(3, patent.getDbName());
            stmt.setInt(4, 3);
            //执行sql
            boolean flag = stmt.execute();
        } catch (MySQLIntegrityConstraintViolationException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void updateSQL(List<CniprPatent> cniprPatentList) throws SQLException {
        Connection conn = DataUtil.getConnection();

        PreparedStatement stmt = null;
        try {
            // 关闭自动提交
            conn.setAutoCommit(false);

            for(CniprPatent patent:cniprPatentList){

                System.out.println("222222222222222222222222222222222222222222222222222222");
                stmt = conn.prepareStatement("update te_cnipr_patent SET appDate=?, pubDate=?, title=?, ipc=?, applicantName=?, pid=?, inventroName=?, agencyName=?, agentName=?, addrProvince=?, addrCity=?, " +
                        "addrCounty=?, address=?, patType=?, iapp=?, ipub=?, den=?, abs=?, lprs=?, draws=?, tifDistributePath=?, pages=?, " +
                        "relevance=?, proCode=?, appCoun=?, gazettePath=?, gazettePage=?, gazetteCount=?, statusCode=?, familyNo=?,legalStatus=?,mainIpc=?,appResource=?,initMainIpc=?,initIpc=?," +
                        "divideInitAppNo=?,categoryType=?,patNo=?,eupMainIpc=?,eupIpc=?,pdfAddr=?,sameApp=?,titleKey=?,clKey=?,bgKey=?,nec=?,grantDate=?,expireDate=?,patentee=? where pubNumber=?");




              /*  stmt = conn.prepareStatement("update te_cnipr_patent SET appDate=?, pubDate=?, title=?, ipc=?, applicantName=?, pid=?, inventroName=?, agencyName=?, agentName=?, addrProvince=?, addrCity=?, " +
                        "addrCounty=?, address=?, patType=?, iapp=?, ipub=?, den=?, abs=?, lprs=?, draws=?, tifDistributePath=?, pages=?, " +
                        "relevance=?, proCode=?, appCoun=?, gazettePath=?, gazettePage=?, gazetteCount=?, statusCode=?, familyNo=?,legalStatus=?,mainIpc=?,appResource=?,initMainIpc=?,initIpc=?," +
                        "divideInitAppNo=?,categoryType=?,patNo=?,eupMainIpc=?,eupIpc=?,pdfAddr=?,sameApp=?,titleKey=?,clKey=?,bgKey=?,nec=?,grantDate=?,expireDate=?,patentee=? where pubNumber=?");*/
                stmt.setString(1, patent.getAppDate());
                stmt.setString(2, patent.getPubDate());
                stmt.setString(3, patent.getTitle());
                stmt.setString(4, patent.getIpc());
                stmt.setString(5, patent.getApplicantName());
                stmt.setString(6, patent.getPid());
                stmt.setString(7, patent.getInventroName());
                stmt.setString(8, patent.getAgencyName());
                stmt.setString(9, patent.getAgentName());
                stmt.setString(10, patent.getAddrProvince());
                stmt.setString(11, patent.getAddrCity());
                stmt.setString(12, patent.getAddrCounty());
                stmt.setString(13, patent.getAddress());
                stmt.setString(14, patent.getPatType());
                stmt.setString(15, patent.getIapp());
                stmt.setString(16, patent.getIpub());
                stmt.setString(17, patent.getDen());
                stmt.setString(18, patent.getAbs());
                stmt.setString(19, patent.getLprs());
                stmt.setString(20, patent.getDraws());
                stmt.setString(21, patent.getTifDistributePath());
                stmt.setString(22, patent.getPages());
                stmt.setString(23, patent.getRelevance());
                stmt.setString(24, patent.getProCode());
                stmt.setString(25, patent.getAppCoun());
                stmt.setString(26, patent.getGazettePath());
                stmt.setString(27, patent.getGazettePage());
                stmt.setString(28, patent.getGazetteCount());
                stmt.setString(29, patent.getStatusCode());
                stmt.setString(30, patent.getFamilyNo());
                stmt.setString(31, patent.getLegalStatus());
                stmt.setString(32, patent.getMainIpc());
                stmt.setString(33, patent.getAppResource());
                stmt.setString(34, patent.getInitMainIpc());
                stmt.setString(35, patent.getInitIpc());
                stmt.setString(36, patent.getDivideInitAppNo());
                stmt.setString(37, patent.getCategoryType());
                stmt.setString(38, patent.getPatNo());
                stmt.setString(39, patent.getEupMainIpc());
                stmt.setString(40, patent.getEupIpc());
                stmt.setString(41, patent.getPdfAddr());
                stmt.setString(42, patent.getSameApp());
                stmt.setString(43, patent.getTitleKey());
                stmt.setString(44, patent.getClKey());
                stmt.setString(45, patent.getBgKey());
                stmt.setString(46, patent.getNec());
                stmt.setString(47, patent.getGrantDate());
                stmt.setString(48, patent.getExpireDate());
                stmt.setString(49, patent.getPatentee());
                stmt.setString(50, patent.getPubNumber());

                stmt.addBatch();

            }
            stmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void init(List<CniprPatent> cniprPatentList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1111111111111111111111111111111111111111111");
                try {
                    updateSQL(cniprPatentList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    public static void main(String[] args) {
        TestCnipr testCnipr = new TestCnipr();
        testCnipr.findDetail();

    }

}
