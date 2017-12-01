package com.circle.controller;


import com.circle.service.IAddressCodeService;
import com.circle.util.json.JsonReturn;
import com.circle.vo.AddressCodeModel;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nlpcn.commons.lang.tire.library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="address_code")
public class AddressCodeController extends AbstractController{

    @Autowired
    private IAddressCodeService service;

    @ResponseBody
    @RequestMapping(value = "parse")
    public JsonReturn parse(@RequestParam  String address) {
        return service.parse(address);
    }

    @ResponseBody
    @RequestMapping(value = "reorganize")
    public JsonReturn reorganize() {
        return service.reorganize();
    }

    @ResponseBody
    @RequestMapping(value = "add")
    public JsonReturn add(@RequestParam  String address,@RequestParam String level) {
        return service.add(address,level);
    }

    @ResponseBody
    @RequestMapping(value = "build")
    public JsonReturn build() {
        return service.build();
    }

    @ResponseBody
    @RequestMapping(value = "updateAddressCode")
    public JsonReturn updateAddressCode() {
        return service.updateAddressCode();
    }

    @ResponseBody
    @RequestMapping(value = "translate")
    public JsonReturn translate() {
        service.translate();
        return JsonReturn.buildSuccess("转换成功！");
    }

    @ResponseBody
    @RequestMapping(value = "town_translate")
    public JsonReturn town_translate(int level) {
        service.translateByLevel(level);
        return JsonReturn.buildSuccess("转换成功！");
    }

    @ResponseBody
    @RequestMapping(value = "road_translate")
    public JsonReturn road_translate(int level,int type) {
        service.translateByLevelAndType(level,type);
        return JsonReturn.buildSuccess("转换成功！");
    }


    @ResponseBody
    @RequestMapping(value = "insertBatch")
    public JsonReturn insertBatch(String name, String url , String type,String parent_code) {

        List<AddressCodeModel> lists = this.getProvince(name, url, type, parent_code);
        if (lists.size() > 0) {
            service.insertBatch(lists);
        }

        for (int i = 0; i < lists.size(); i++) {
            AddressCodeModel model = lists.get(i);
            if (model.getUrl() != null && !model.getUrl().equals("") && !model.getUrl().equals("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/33/3302.html")) {
                List<AddressCodeModel> childrens = this.getProvince(name, model.getUrl(), "town", model.getRegion_code());
                if (childrens.size() > 0) {
                    service.insertBatch(childrens);
                }

                for (int j = 0; j < childrens.size(); j++) {
                    AddressCodeModel childModel = childrens.get(j);
                    if (childModel.getUrl() != null && !childModel.getUrl().equals("")) {
                        List<AddressCodeModel> grandsons = this.getProvince(name, childModel.getUrl(), "village", childModel.getRegion_code());
                        if (grandsons.size() > 0) {
                            service.insertBatch(grandsons);
                        }

                    }
                }
            }
        }

        return JsonReturn.buildSuccess("保存成功");
    }

    public Document getHtmlTextByUrl(String url) {
        Document doc = null;
        try {
            //doc = Jsoup.connect(url).timeout(5000000).get();
            int i = (int) (Math.random() * 1000); //做一个随机延时，防止网站屏蔽
            while (i != 0) {
                i--;
            }
            doc = Jsoup.connect(url).data("query", "Java")
                    .userAgent("Mozilla").cookie("auth", "token")
                    .timeout(300000).post();
        } catch (IOException e) {
            try {
                doc = Jsoup.connect(url).timeout(5000000).get();
            } catch (IOException e1) { // TODO Auto-generated catch block  e1.printStackTrace(); } }

                return doc;
            }
        }
        return doc;
    }

    //根据本地路径获取网页文本，如果不存在就通过url从网络获取并保存
    public Document getHtmlTextByPath(String name,String url) {
        String path = "D:/Html/" +name+".html";
        Document doc = null ;
        File input = new File(path);
        String urlcat = url;
        try {
            doc = Jsoup.parse(input, "UTF-8");
            if(!doc.children().isEmpty())
            {
                doc=null; System.out.println("已经存在"); }
        } catch (IOException e) {
            System.out.println("文件未找到，正在从网络获取.......");
            doc = this.getHtmlTextByUrl(url);
            //并且保存到本地
            this.Save_Html(url, name);
        } return doc;
    }  //此处为保存网页的函数

    //将网页保存在本地（通过url,和保存的名字）
    public  void Save_Html(String url,String name) {
        try {
            name =  name+".html";
            // System.out.print(name);
            File dest = new File("C:/Html/" +name);//D:\Html
            //接收字节输入流
            InputStream is;
            //字节输出流
            FileOutputStream fos = new FileOutputStream(dest);

            URL temp = new URL(url);
            is = temp.openStream();

            //为字节输入流加缓冲
            BufferedInputStream bis = new BufferedInputStream(is);
            //为字节输出流加缓冲
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int length;

            byte[] bytes = new byte[1024*20];
            while((length = bis.read(bytes, 0, bytes.length)) != -1){
                fos.write(bytes, 0, length);
            }

            bos.close();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据元素属性获取某个元素内的elements列表
    public Elements getEleByClass(Document doc, String className){
        Elements elements= null;
        elements = doc.select(className);//这里把我们获取到的html文本doc，和工具class名，注意<tr class="provincetr">
        return elements;   //此处返回的就是所有的tr集合
    }

    //获取省 、市 、县等的信息
    public List<AddressCodeModel> getProvince(String name, String url , String type, String parent_code) {
        List<AddressCodeModel> result = new ArrayList();
        //"tr.provincetr"
        String classtype = "tr." + type + "tr";
        //从网络上获取网页
        // Document doc = this.getHtmlTextByUrl(url);
        //从本地获取网页,如果没有则从网络获取
        Document doc2 = this.getHtmlTextByPath(name, url);
        System.out.println(name);
        if (doc2 != null) {
            Elements es = this.getEleByClass(doc2, classtype);  //tr的集合
            for (Element e : es)   //依次循环每个元素，也就是一个tr
            {
                if (e != null) {
                    AddressCodeModel model =new AddressCodeModel();
                    for(int i=0;i<e.children().size();i++){
                        Element ec = e.children().get(i);
                         if(i==0){

                             model.setUrl(url);
                            // System.out.println(ec.children().first().ownText());
                             model.setRegion_code(ec.getElementsByTag("td").text());

                            if(ec.getElementsByTag("td").select("a").size()!=0){
                                String ownurl = ec.children().first().attr("abs:href");
                                //因为如果从本地取得话，会成为本地url，所以保留第一次从网络上的url，保证url不为空
                                model.setUrl(ownurl);
                            }

                             model.setParent_code(parent_code);

                         }else{
                             model.setName(ec.getElementsByTag("td").text());
                         }
                    }
                    result.add(model);
                }
            }
        }
        System.out.println(result);
        return result;  //反回所有的省份信息集合，存数据库，字段类型为： baseurl  name ownurl levelname（type） updatetime
    }


}
