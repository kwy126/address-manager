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
    private  Queue<CniprPatent> patentQueue = new Queue<CniprPatent>();
    private static int total = 0;

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

    public Map<String,List<CniprPatent>> search(int begin,int end) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, List<CniprPatent>> retMap = new HashMap<>();
//        params.put("exp", "地址=(浙江省宁波)and 公开（公告）日=(20171001 to 20171031)");
        params.put("exp", "(分类号=('b60%' or 'b62d%' or 'f01l%' or 'f01m%' or 'f01n%' or 'f01p%' or 'f02b%' or 'f02d%' or 'f02f%' or 'f02m%' or 'f02n%' or 'f02p%' or 'f16j10%' or 'f16c3/06' or 'f16c3/08' or 'f16c3/10' or 'f16c3/12' or 'f16c3/14' or 'f16c3/16' or 'f16j1/%' or 'f02f3%' or 'f16j9%' or 'f16c7%' or 'f16c9%' or 'f16f15/30%' or 'f16f15/31%' or 'f02c6/12' or 'h01f38/12' or 'h01t13%' or 'h01t21/02%' or 'h01t21/04%' or 'g01m15%' or '26-06%' or 'a62c3/07' or 'e05b65/12' or 'e05b65/14' or 'e05b65/16' or 'e05b65/18' or 'e05b65/19' or 'e05b65/2%' or 'e05b65/3%' or 'e05b65/40' or 'e05b65/42' or 'b29d30%' or 'g01m17/013' or 'g01m17/02' or 'g01m17/04' or 'b62d1/%' or 'h05k11/02' or '13-01%' or 'g01c21/26' or 'g01c21/28' or 'g01c21/30' or 'g01c21/32' or 'g01c21/34' or 'g01c21/36' or '12-08%' or '12-13%' or '12-15%' or '15-01%' or '12-16%')) or (名称,摘要,主权项+=(汽车 or 机动车 or 车辆 or 车用 or 轿车 or 客车 or 货车 or 卡车 or 乘用车 or 商用车 or 电动车 or 车载)) or (名称=((发动机 or 内燃机 or 汽油机 or 柴油机) and (机体 or 缸体 or 气缸 or 汽缸 or 油底壳 or 曲轴箱 or 主轴承盖))) or (名称,摘要,主权项+=(((发动机 or 内燃机 or 汽油机 or 柴油机) and 机体) or 缸体 or 气缸 or 汽缸 or 油底壳 or 曲轴箱 or 主轴承盖) and 分类号=('f02%' or 'f16j%' or 'f16c%' or 'f16b%' or 'f16m%' or '08-08%')) or (名称=(((发动机 or 内燃机 or 汽油机 or 柴油机) and 机体) or 缸体 or 气缸 or 汽缸 or 油底壳 or 曲轴箱 or 主轴承盖) and 分类号=('c21%' or 'c22%' or 'c23%' or 'g01%' or 'b21%' or 'b23%' or 'b22%' or 'b24%')) or (名称,摘要,主权项+=(((发动机 or 内燃机 or 汽油机 or 柴油机) and 机体) or 缸体 or 气缸 or 汽缸 or 油底壳 or 曲轴箱 or 主轴承盖) and 分类号=('c21%' or 'c22%' or 'c23%' or 'g01%' or 'b21%' or 'b23%' or 'b22%' or 'b24%')) or (名称,摘要,主权项+=(曲轴 not 压缩机%曲轴) and 分类号=('f16c%' or 'f16d%' or 'f16h55%' or 'f02%' or '15-99%')) or (名称=(曲轴 not 压缩机%曲轴) and 分类号=('b22%' or 'b23%' or 'g01%' or 'c21%' or 'c22%' or 'c23%' or 'c25%')) or (名称,摘要,主权项+=(活塞) and 分类号=('f16j%' or '15-99%')) or (名称=(活塞) and 分类号=('b21%' or 'b22%' or 'b23%' or 'c21%' or 'c22%' or 'c23%' or 'c25d%' or 'c04b%' or 'c09%' or 'c08l%')) or (名称,摘要,主权项+=(连杆) and 分类号=('f16h%' or 'f16c%' or '15-99%')) or (名称=(连杆) and 分类号=('g01%' or 'c22%' or 'b21%' or 'b22%' or 'b23%')) or (名称,摘要,主权项+=(飞轮) and 分类号=('f16f%' or 'f02%' or 'f16d%' or 'b23%' or '15-99%')) or (名称=(曲柄连杆) and 分类号=('f16f%' or 'f16h%' or 'f16c%')) or (名称,摘要,主权项+=(凸轮轴) and 分类号=('f16h%' or 'f16c%' or '15-99%')) or (名称=(凸轮轴) and 分类号=('b21%' or 'b22%' or 'b23%' or 'b24%' or 'c21%' or 'c22%' or 'g01%')) or (名称,摘要,主权项+=(挺杆 or 挺柱 or 推杆) and 分类号=('f01l%' or '08-08%')) or (名称=(挺杆 or 挺柱 or 推杆) and 分类号=('b22%' or 'b23%' or 'c23%' or 'g01%')) or (名称,摘要,主权项+=(摇臂) and 分类号=('g01b%' or 'c21d%' or 'b23p%' or 'b23q%')) or (名称,摘要,主权项+=(气门) and 分类号=('b21%' or 'b22%' or 'b23%' or 'c22%' or 'c23%')) or (名称,摘要,主权项+=(化油器) and 分类号=('b65d%' or 'c11d%' or '12-99%')) or (名称,摘要,主权项+=(汽油泵) and 分类号=('f04d%' or '15-02%')) or (名称,摘要,主权项+=(喷油器) and 分类号=('b21%' or 'f02c%' or 'c22%' or 'c10l%' or 'g01%' or 'b25b%' or '15-02%' or '10-05%')) or (名称,摘要,主权项+=(输油泵) and 分类号=('f04b%' or 'f04c%' or 'f04d%' or 'f16j%' or 'b21d%' or 'b22%' or '15-02%')) or (名称,摘要,主权项+=(喷油泵) and 分类号=('f04b%' or 'f04d%' or 'f16h%' or 'f16d%' or 'b23%' or 'c23%' or 'g01%' or '15-02%' or '15-99%')) or (名称,摘要,主权项+=(调速器) and 分类号=('f04d1/%')) or (名称,摘要,主权项+=(滤清器) and 分类号=('g01%' or 'c08l%' or 'b01d%' or '15-99%' or '23-01%')) or (名称,摘要,主权项+=(油箱) and 分类号=('c08l%' or '23-01%')) or (名称,摘要,主权项+=(机油收集器 or 机油收集装置) and 分类号=(b01d%)) or (名称,摘要,主权项+=(断油) and 分类号=(f16k%)) or (名称,摘要,主权项+=((燃油 or 汽油 or 柴油 or 燃料) and 压力调节) and 分类号=('g01%')) or (名称,摘要,主权项+=((燃油 or 汽油 or 柴油 or 燃料) and 传感器) and 分类号=('g01f%' or 'g01d%' or '10-05%')) or (名称,摘要,主权项+=((燃油 or 汽油 or 柴油 or 燃料) and 喷射) and 分类号=('15-02%')) or (名称,摘要,主权项+=(燃油泵) and 分类号=('f04d%' or 'c22c%' or 'g01%' or 'h02k%' or '15-02%')) or (名称,摘要,主权项+=(燃油供给 or 燃料供给) and 分类号=(g01m%)) or (名称,摘要,主权项+=(增压系统 or 进气增压 or 涡轮增压 or 气波增压) and 分类号=('f01%' or 'f02%' or 'f04%' or 'f16%' or 'g01%' or 'b21%' or 'b22%' or 'c22%' or '12-06%')) or (名称,摘要,主权项+=(进气管 or 进气歧管) and 分类号=('b29c%' or 'c08l%')) or (名称,摘要,主权项+=(节气门) and 分类号=('g01f%' or 'b23%')) or (名称,摘要,主权项+=(空气滤清器 or 空滤器) and 分类号=('b62d%' or 'b01d39%' or 'b29c%' or '23-01%' or '23-04%' or '15-99%')) or (名称,摘要,主权项+=(空气流量计) and 分类号=('10-04%')) or (名称,摘要,主权项+=(排气%管) and 分类号=('c23c%')) or (名称,摘要,主权项+=(消声器 or 消音器) and 分类号=('b23p%' or 'b21%')) or (名称,摘要,主权项+=(排气净化 or 催化转换器 or 氧传感器 or 微粒过滤器 or 废气再循环 or egr) and 分类号=('g01n%' or 'b28b%' or 'b01j%' or 'b01d%' or '10-05%')) or (名称,摘要,主权项+=(曲轴箱通风) and 分类号=(b21c% or g01%)) or (名称,摘要,主权项+=(排气%传感器) and 分类号=(g01n%)) or (名称,摘要,主权项+=((散热器 or 水箱 or 膨胀箱) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('f25%' or 'f28%' or 'c08j%' or 'b21%' or 'b23%' or 'b32%' or 'c22%' or 'f04d%')) or (名称,摘要,主权项+=(水泵 and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('f04d%' or 'f16h%' or 'f16c%' or 'b23%' or 'b25b%' or 'g01%' or '15-02%')) or (名称,摘要,主权项+=(风扇 and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('g01m%' or '23-04%' or 'f04d%')) or (名称,摘要,主权项+=(风扇%离合器) and 分类号=('f16c%' or 'f16d%' or 'c10m%' or '23-04%' or '15-02%')) or (名称,摘要,主权项+=(冷却水套 or 冷却管路 or 冷却水管) and 分类号=('h02k%')) or (名称,摘要,主权项+=(水温传感器 and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('g01k%')) or (名称,摘要,主权项+=((冷却液 or 冷却剂) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=(c09k% or g01k% or f16k%)) or (名称=(机油泵 and (发动机 or 内燃机 or 汽油机 or 柴油机))) or (名称,摘要,主权项+=(机油泵) and 分类号=('f01%' or 'f02%' or 'f04%' or '15-02%')) or (名称,摘要,主权项+=(机油滤清器 or 机滤 or 润滑油%滤清器 or 机油过滤器 or 机油粗滤 or 机油细滤) and 分类号=('f16n%' or 'b01d%' or 'c08l%' or 'f16k%' or 'g01m%' or '15-99%' or '23-01%')) or (名称,摘要,主权项+=(机油冷却 or 润滑油冷却) and 分类号=('f28f%' or 'f16n%' or 'f16l%' or '15-02%' or '23-01%' or '12-99%')) or (名称,摘要,主权项+=(机油集滤器) and 分类号=('b23k%' or 'b23p%')) or (名称,摘要,主权项+=(润滑限压阀 or 机油限压阀) and 分类号=(f16k%)) or (名称,摘要,主权项+=((机油 or 润滑油 or 润滑脂 or 润滑剂) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=(c10m% or c10l% or g01n% or f16n%)) or (名称=(点火%开关) and 分类号=('08-07%')) or (名称,摘要,主权项+=(点火%开关 and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=(h01h%)) or (名称=(点火%线圈) and 分类号=('h01f%' or '12-99%' or '13-02%')) or (名称=(火花塞) and 分类号=('h01t%' or 'c22%' or 'c04b%' or 'b21%' or 'b23k%' or 'g01%' or 'c09k%')) or (名称,摘要,主权项+=(分电器 or 断电器 or 配电器 or 电容器 or 点火提前) and 分类号=('12-16%')) or (名称,摘要,主权项+=((分电器 or 断电器 or 配电器 or 电容器 or 点火提前) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=(h01r% or h01h% or h01g%)) or (名称,摘要,主权项+=(发动机 or 内燃机 or 汽油机 or 柴油机) and 分类号=('h01m10%')) or (名称,摘要,主权项+=((蓄电池) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('13-02%')) or (名称,摘要,主权项+=(发电机) and 分类号=('12-99%')) or (名称,摘要,主权项+=((发电机) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('h02p%' or 'h02k%' or 'h01r%' or 'b21k%' or 'b23p%' or 'f16h%')) or (名称,摘要,主权项+=(起动电机 or 起动机 or 启动机 or 启动电机) and 分类号=('h02k%' or 'h02p%' or 'g01%' or 'f16d%' or 'f16h%' or 'h01h%' or 'b23k%')) or (名称,摘要,主权项+=(起动预热 or 低温起动 or 启动预热 or 低温启动 or 进气预热 or 火焰预热 or 预热塞) and 分类号=('09-07%')) or (名称,摘要,主权项+=(电热塞) and 分类号=('b22%' or 'h05b%' or 'b21k%' or 'b32b%' or 'f23q%')) or (名称,摘要,主权项+=(起动液 or 启动液) and 分类号=(c10l%)) or (名称,摘要,主权项+=(仪表台 or 仪表板 or 仪表皮 or 仪表罩) and 分类号=('12-99%' or 'c08j%' or 'b29%' or 'c08l%' or 'h01h%' or 'g12b%' or 'b26f%' or 'g01d%' or '10-07%' or '10-04%')) or (名称=(遮阳 or 挡阳 or 遮光 or 挡光) and 分类号=('12-99%')) or (名称,摘要,主权项+=((遮阳 or 挡阳 or 遮光 or 挡光) and (车窗)) and 分类号=(b29c%)) or (名称=((把手 or 拉手) and (车门)) and 分类号=('e05b%' or '08-06%' or 'b29c%')) or (名称=((手柄 or 内把手 or 内拉手 or 内饰%拉手) and (车门)) and 分类号=('e05b%' or '08-06%' or 'b29c%')) or (名称,摘要,主权项+=(立柱 or 前柱 or a柱 or 中柱 or b柱 or 后柱 or c柱 or d柱) and 分类号=('05-05%')) or (名称,摘要,主权项+=((散热器 or 水箱) and (护%罩 or 面罩 or 防护 or 格栅)) and 分类号=('b41m%' or '23-04%' or '23-03%')) or (名称=(车身 or 裙板 or 侧裙 or 定风翼 or 行李箱盖 or 行李舱盖 or 发动机舱盖 or 发动机盖 or 引擎盖 or 翼子板 or 叶子板) and 分类号=('b32b%' or 'b29c%' or 'b21d%' or 'b23k%' or 'c22%' or 'c23%' or 'c25%' or 'c08%' or 'c09%')) or (名称,摘要,主权项+=(车门) and 分类号=('b32b%' or 'b21%' or 'g01%' or 'e05%' or '25-01%')) or (名称,摘要,主权项+=(车窗) and 分类号=(e05%)) or (名称,摘要,主权项+=(车厢 or 车箱) and 分类号=('b23k%' or 'g01%' or 'b32b%')) or (名称,摘要,主权项+=(车 and 门%锁) and 分类号=('e05b%' or 'b23p%' or 'g01%' or 'g06k%' or '08-07%' or '14-03%')) or (名称,摘要,主权项+=(车 and 门%铰链) and 分类号=('e05d%' or 'g01%' or 'f16b%' or '08-06%')) or (名称,摘要,主权项+=((刮水 or 雨刷 or 雨刮 or 除雨) and 车窗) and 分类号=('08-08%' or 'g01%' or 'h02k%' or 'c08l%')) or (名称,摘要,主权项+=(车窗) and 分类号=('h05b3/84' or 'h05b3/86')) or (名称,摘要,主权项+=((玻璃 or 车窗) and 升降) and 分类号=('e05f%' or 'g01m%' or 'h01h%' or 'h02k%' or '12-05%' or '08-05%' or '13-03%')) or (名称,摘要,主权项+=(((玻璃 or 门窗 or 风窗) and (密封)) and (车窗)) and 分类号=('e06b7%' or 'b29c%' or '08-08%' or '25-99%')) or (名称,摘要,主权项+=(变速箱 or 变速器 or 同步器 or 变速操纵 or 变速换挡 or 变速传动 or 变速齿轮 or 变矩器 or cvt) and 分类号=('15-99%')) or (名称,摘要,主权项+=(万向节 or 联轴器 or 万向接头 or 万向传动) and 分类号=('f16d%' or '23-01%' or '15-99%')) or (名称,摘要,主权项+=(传动轴 or 驱动轴) and 分类号=('15-99%')) or (名称,摘要,主权项+=(驱动桥 or 减速器) and 分类号=('f16h%')) or (名称,摘要,主权项+=(差速机构 or 差速器 or 差速%装置) and 分类号=('f16d%' or 'f16h%' or '15-99%')) or (名称,摘要,主权项+=(半轴) and 分类号=('f16h%' or '12-99%')) or (名称,摘要,主权项+=(车架 or 大梁 or 纵梁 or 横梁) and 分类号=('12-99%')) or (名称=(车架) and 分类号=(b21% or b23% or c22% or c21% or g01%)) or (名称=(发动机%护板) and 分类号=('f16m%')) or (名称=(车桥 or 前桥 or 后桥 or 中间桥 or 转向桥 or 转向驱动桥 or 支持桥) and 分类号=(b21% or g01% or b23% or c22%)) or (名称=(车轮 or 轮辋 or 轮辐 or 轮毂 or 钢圈 or 轮彀) and 分类号=('b21%' or 'b22%' or 'b23p%' or 'c22%' or 'c23%' or 'c21d%' or 'f16c%' or 'g01%')) or (名称=(轮胎) and 分类号=('c08l%' or 'b29%' or 'g01%' or 'd02%')) or (名称,摘要,主权项+=(导向器 or 导向座 or 导向套) and 分类号=('f16f%' or 'b22f%')) or (名称,摘要,主权项+=(转向轴 or 转向管柱 or 转向柱 or 转向操纵) and 分类号=('f16d%' or 'f16h%' or 'h01h%' or 'b23%' or 'g01%')) or (名称,摘要,主权项+=(方向盘 or 转向盘) and 分类号=('b27h7/00')) or (名称,摘要,主权项+=(转向器 or 转向机 or 方向机) and 分类号=('f16%' or 'h05k%' or 'f15b%' or 'g01%')) or (名称,摘要,主权项+=(转向摇臂 or 转向拉杆 or 转向直拉杆 or 转向节) and 分类号=('f16b%' or 'f16c%' or 'f16l%' or 'b21%' or 'c21%' or 'g01%')) or (名称,摘要,主权项+=(转向减振 or 转向减震) and 分类号=(f16f% or f16l% or f16j%)) or (名称,摘要,主权项+=(助力转向 or 动力转向 or 线控转向 or 主动转向) and 分类号=('f16k%' or 'f15b%' or 'f16f%' or 'f04c%' or 'h02k%' or 'h05k%' or 'g01%' or 'g05b%' or '10-05%')) or (名称,摘要,主权项+=(制动 or 刹车) and 分类号=('15-99%' or '10-05%')) or (名称,摘要,主权项+=(刹车) and 分类号=('f16%' or 'f04b%' or 'c22%' or 'b22%' or 'b21%' or 'c23%' or 'b29%' or 'g01%')) or (名称,摘要,主权项+=(制动踏板 or 刹车踏板) and 分类号=('b23%' or 'f16%' or 'g01%')) or (名称,摘要,主权项+=(真空助力器 or 制动助力器) and 分类号=('f16d%' or '15-02%' or 'g01%')) or (名称,摘要,主权项+=(制动总泵 or 制动分泵 or 制动主缸 or 制动轮缸 or 制动总泵 or 刹车泵 or 制动油缸) and 分类号=('f16d%' or '15-02%' or 'f04b%')) or (名称,摘要,主权项+=(鼓式制动器 or 盘式制动器) and 分类号=('f16%' or 'b23%' or 'c22c%')) or (名称,摘要,主权项+=(制动液) and 分类号=(c10m%)) or (名称,摘要,主权项+=(防滑控制 or 电子稳定) and 分类号=('g01%')) or (名称,摘要,主权项+=((防夹) and (车窗 or 车门)) and 分类号=(e05f%)) or (名称,摘要,主权项+=(发动机 or 内燃机 or 汽油机 or 柴油机) and 分类号=('h01m10%')) or (名称,摘要,主权项+=((蓄电池) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('13-02%')) or (名称,摘要,主权项+=(发电机) and 分类号=('12-99%')) or (名称,摘要,主权项+=((发电机) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('h02p%' or 'h02k%' or 'h01r%' or 'b21k%' or 'b23p%' or 'f16h%')) or (名称,摘要,主权项+=(闪光器) and 分类号=('10-06%')) or (名称,摘要,主权项+=(报警 or 警报) and 分类号=('f01%' or 'f02%')) or (名称,摘要,主权项+=(防盗) and 分类号=('f02%')) or (名称,摘要,主权项+=(防盗 and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('e05b%' or '14-03%' or '10-05%')) or (名称,摘要,主权项+=((电动机 or 电机 or 马达) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=(h02k% or h02p% or e05b% or f01% or f02%)) or (名称,摘要,主权项+=(起动电机 or 起动机 or 启动机 or 启动电机 or 起动马达 or 启动马达) and 分类号=('h02k%' or 'h02p%' or 'g01%' or 'f16d%' or 'f16h%' or 'h01h%' or 'b23k%')) or (名称,摘要,主权项+=(里程表 or 燃油表 or 汽油表) and 分类号=('g01%' or '10-04%')) or (名称,摘要,主权项+=(水温表) and 分类号=('g01k%' or '10-04%' or '10-05%')) or (名称,摘要,主权项+=(机油压力表 or 油压表) and 分类号=('g01l%' or '10-04%')) or (名称,摘要,主权项+=(转速表) and 分类号=('g01p%' or 'g01m%' or '10-02%' or '10-04%' or '10-07%')) or (名称,摘要,主权项+=(组合仪表) and 分类号=('g01%' or 'g05%' or 'h04l%' or '10-04%' or '10-07%' or '10-03%')) or (名称,摘要,主权项+=((燃油 or 汽油 or 柴油 or 燃料) and 喷射) and 分类号=('15-02%')) or (名称,摘要,主权项+=(防抱死 or 防滑控制 or 制动辅助 or 电子制动力分配 or 电子稳定 or 电子控制制动) and 分类号=('f04b%')) or (名称,摘要,主权项+=(电子驻车) and 分类号=('f16d%' or '10-05%')) or (名称,摘要,主权项+=((电子 or 电控) and (变速器 or 变速箱)) and 分类号=(f16h%)) or (名称,摘要,主权项+=(行车电脑 or 车载电脑) and 分类号=('g06%' or '14-02')) or (名称,摘要,主权项+=((电控单元 or 电子控制单元 or ecu) and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('g06%')) or (名称,摘要,主权项+=(倒车雷达) and 分类号=('g01s%' or '10-05%' or '14-03%' or '10-04%')) or (名称=(传感器 or 继电器) and 分类号=('f01%' or 'f02%')) or (名称,摘要,主权项+=(传感器 and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('h01%' or 'g01%' or 'f16%' or 'b28%' or '10-05%')) or (名称,摘要,主权项+=(继电器 and (发动机 or 内燃机 or 汽油机 or 柴油机)) and 分类号=('h01h%' or 'c22c%' or '13-03%' or 'h03k%' or 'f21%')) or (名称,摘要,主权项+=((发动机 or 内燃机 or 汽油机 or 柴油机 or 引擎 or 变速箱 or 变速器 or 离合器 or 制动 or 水泵 or 油泵) and (粉末 or 烧结)) and 分类号=(f01% or f02% or f16c% or f16d% or f16f% or f16h% or f16j% or f16n% or f04c% or c23% or c22%)) or (名称,摘要,主权项+=(发动机 or 内燃机 or 汽油机 or 柴油机 or 引擎 or 变速箱 or 变速器 or 离合器 or 制动 or 水泵 or 油泵 or 气门%座 or 排气阀座 or 同步环 or 同步器 or ((曲轴 or 正时) and (齿轮 or 链轮 or 带轮)) or 轴承 or 轴套 or 衬套 or 轴瓦 or 连杆 or 凸轮轴 or 减震器 or 减振器 or 导向器 or 导向座 or 导向套) and 分类号=('b22f%' or 'c22c1/04' or 'c22c1/05' or 'c22c33/02' or 'c22c47/14')) or (名称,摘要,主权项+=(发动机 or 内燃机 or 汽油机 or 柴油机 or 引擎 or 变速箱 or 变速器 or 离合器 or 制动 or 水泵 or 油泵 or 气门%座 or 排气阀座 or 同步环 or 同步器 or ((曲轴 or 正时) and (齿轮 or 链轮 or 带轮)) or 轴承 or 轴套 or 衬套 or 轴瓦 or 连杆 or 凸轮轴 or 减震器 or 减振器 or 导向器 or 导向座 or 导向套) and 申请（专利权）人=(粉末冶金)) or (名称,摘要,主权项+=((气门%座 or 排气阀座) and (粉末 or 烧结)) and 分类号=(c23% or c22%)) or (名称,摘要,主权项+=((同步环 or ((同步器 or 变速箱 or 变速器) and (锥环 or 锥毂 or 齿轮 or 齿毂 or 齿环))) and (粉末 or 烧结)) and 分类号=(f16d% or f16h% or c23% or c22%)) or (名称,摘要,主权项+=((曲轴 or 凸轮轴 or 正时) and (齿轮 or 链轮 or 带轮) and (粉末 or 烧结)) and 分类号=(f16h% or f16c% or f01% or c23% or c22%)) or (名称,摘要,主权项+=((机油泵) and (粉末 or 烧结)) and 分类号=(f04c% or f16h% or f16n% or c23% or c22%)) or (名称,摘要,主权项+=((轴承 or 轴套 or 衬套 or 连杆瓦 or 轴瓦) and (粉末 or 烧结)) and 分类号=(f01% or f02% or f16d% or f16f% or f16c% or c23% or c22%)) or (名称,摘要,主权项+=(连杆 and (粉末 or 烧结)) and 分类号=(f16c% or b23p% or c23% or c22%)) or (名称,摘要,主权项+=(凸轮轴 and (粉末 or 烧结)) and 分类号=(f16h% or f16c% or c23% or c22%)) or (名称,摘要,主权项+=((减震器 or 减振器 or 导向器 or 导向座 or 导向套) and (粉末 or 烧结)) and 分类号=(f16f% or f16c% or f16j% or c23% or c22%)) or (名称,摘要,主权项+=((活塞 or 阀体 or 阀座) and (粉末 or 烧结)) and 分类号=(f16f% or f16c% or f16j%))\n");
        params.put("dbs", "FMZL");
//        params.put("client_id", CLIENT_ID);
        params.put("openid", OPEN_ID);
        params.put("access_token", ACCESS_TOKEN);
        params.put("order", "LIFO");
        params.put("from", ""+begin);
        params.put("to", ""+end);
        String str = HttpUtil.URLPost(URL + "/cnipr-api/rs/api/search/sf26/" + CLIENT_ID, params, "UTF-8");
        System.out.println(str);
        Object obj = JSON.parse(str);
        total = ((JSONObject) obj).getIntValue("total");
        List<CniprPatent> cniprPatentList = JSONArray.parseArray(((JSONObject) obj).getString("results"), CniprPatent.class);
        retMap.put("results", cniprPatentList);
        return retMap;

       /* if (total > 5) {

            str = HttpUtil.URLPost(URL + "/cnipr-api/rs/api/search/sf26/" + CLIENT_ID, params, "UTF-8");

            obj = JSON.parse(str);
            List<CniprPatent> cniprPatentList = JSONArray.parseArray(((JSONObject) obj).getString("results"), CniprPatent.class);
            System.out.println(cniprPatentList.size());

            Connection conn = DataUtil.getConnection();

            for (CniprPatent patent : cniprPatentList) {

                insertSQL(conn, patent);
                //findDetail(patent);
            }
        }*/
    }

    public void findDetail() {

        PreparedStatement stmt = null;
        Connection conn = DataUtil.getConnection();
        StringBuffer sb = new StringBuffer();
        int i = 0;
        try {
            stmt = conn.prepareStatement("select * from te_cnipr_patent where id >9002");
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

                       patentQueue.enQueue(cniprPatent);
                    }

                    System.out.println("此时队列长度========" + patentQueue.size());
                    this.init(conn);

                    i = 0;
                    sb = new StringBuffer();
                }


            }
        } catch (MySQLIntegrityConstraintViolationException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/


    }

    public void insertSQL(Connection conn, CniprPatent patent) {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into te_cnipr_patent(appNumber,pubNumber,dbName,type) values(?,?,?,?)");
            stmt.setString(1, patent.getAppNumber());
            stmt.setString(2, patent.getPubNumber());
            stmt.setString(3, patent.getDbName());
            stmt.setInt(4, 6);
            //执行sql
            boolean flag = stmt.execute();
        } catch (MySQLIntegrityConstraintViolationException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void updateSQL() throws SQLException {
        Connection conn = DataUtil.getConnection();
        PreparedStatement stmt = null;
        try {
            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");
            System.out.println("-----------------------开始更新数据库--------------------------");
            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");
            // 关闭自动提交，开启事务
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement("update te_cnipr_patent SET appDate=?, pubDate=?, title=?, ipc=?, applicantName=?, pid=?, inventroName=?, agencyName=?, agentName=?, addrProvince=?, addrCity=?, " +
                    "addrCounty=?, address=?, patType=?, iapp=?, ipub=?, den=?, abs=?, lprs=?, draws=?, tifDistributePath=?, pages=?, " +
                    "relevance=?, proCode=?, appCoun=?, gazettePath=?, gazettePage=?, gazetteCount=?, statusCode=?, familyNo=?,legalStatus=?,mainIpc=?,appResource=?,initMainIpc=?,initIpc=?," +
                    "divideInitAppNo=?,categoryType=?,patNo=?,eupMainIpc=?,eupIpc=?,pdfAddr=?,sameApp=?,titleKey=?,clKey=?,bgKey=?,nec=?,grantDate=?,expireDate=?,patentee=? where pubNumber=?");

            while(!patentQueue.isEmpty()){
                CniprPatent patent = patentQueue.deQueue();

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

            int[] tt = stmt.executeBatch();
            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");
            System.out.println("----------------插入 " + tt.length + " 条数据！--------------");
            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");
           //
            conn.commit();
            stmt.clearBatch();

            //conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


    public void init(Connection conn) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (patentQueue.size() > 100) {
                        updateSQL();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    public static void main(String[] args) {
        TestCnipr testCnipr = new TestCnipr();
        testCnipr.findDetail();

       /* testCnipr.search(0, 5);
        int begin =0;
        int space = 1000;
        while (begin + space < total) {

            Map<String, List<CniprPatent>> map = testCnipr.search(begin, begin + space);
            begin = begin + space + 1;
            List<CniprPatent> cniprPatentList = map.get("results");
            Connection conn = DataUtil.getConnection();

            for (CniprPatent patent : cniprPatentList) {

                testCnipr.insertSQL(conn, patent);
            }
        }*/
    }

}
