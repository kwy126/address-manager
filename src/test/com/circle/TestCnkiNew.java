package com.circle;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class TestCnkiNew {

    private static CloseableHttpClient httpClient = null;
    private static String url = "http://expert.cnki.net/Expert/Detail/";

    public static void main(String[] args) throws Exception{

        TestCnkiNew cnki = new TestCnkiNew();
        cnki.init();

        for (int i = 5967888; i <= 5967888; i++) {

            String content = cnki.getHtml(url, String.format("%08d",i), httpClient);
            Document doc=Jsoup.parse(content); // 解析网页 得到文档对象
            Elements listHeader = doc.select(".wrap .content");
            if (listHeader == null || listHeader.size() == 0) {
                System.out.println("没有此" + i + "页");
            } else {
                for (Element e : listHeader) {

                    /**
                     * 获取姓名、职称、PCNI
                     */
                    Elements listPerson = doc.select(".wrap .pf-name h1 span");
                    for (Element person : listPerson) {
                        System.out.println(person.text());
                    }

                    //获取单位信息
                    Elements listCompany = doc.select(".pf-intro .txt1");
                    for (Element company : listCompany) {
                        System.out.println(company.text());

                    }

                    //获取研究方向
                    Element researchField = doc.getElementById("headResearchField");
                    if (researchField != null) {
                        System.out.println(researchField.text());
                    }


                    /**
                     * 排序领域
                     */
                    Element rankTitle = doc.getElementById("ranktitle");
                    System.out.println(rankTitle.text());

                    Elements listRank = doc.select(".ranklist li");
                    for (Element element : listRank) {
                        List<Node> listNode = element.childNodes();

                        Elements listTitle = doc.select(".ranklist li label");
                        Elements numbers = doc.select(".ranklist li span");

                        System.out.println(listTitle.text());
                        System.out.println(numbers.text());



                    }
                }
            }

        }

    }

    public void init() {
        httpClient = HttpClients.createDefault(); // 创建httpclient实例
    }

    public  String getHtml(String url,String index,CloseableHttpClient httpclient) throws IOException {
        url = url + index + ".htm";
        System.out.println("url= " + url);
        HttpGet httpget = new HttpGet(url); // 创建httpget实例

        CloseableHttpResponse response = httpclient.execute(httpget); // 执行get请求
        HttpEntity entity=response.getEntity(); // 获取返回实体
        String content=EntityUtils.toString(entity, "utf-8");
        //System.out.println(content);
        response.close(); // 关闭流和释放系统资源
        return content;
    }
}
