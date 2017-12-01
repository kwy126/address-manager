package com.circle.util.pageutil;

/**
 * Created by keweiyang on 2017/6/11.
 */
public class PageUtils {
    public static PageModel calculatePage(int nowPage, int count, int number) {
        PageModel page = new PageModel();
        page.setCount(count);
        page.setNumber(number);
        page.setNowPage(nowPage <=1  ? 1 : nowPage);
        page.setTotalPage(count % number > 0 ? ((count / number) + 1) : count / number);
        return page;
    }
}
