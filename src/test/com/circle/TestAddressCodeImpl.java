package com.circle;

import com.circle.service.AddressCodeServiceImpl;
import com.circle.service.IAddressCodeService;
import com.circle.vo.AddressModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TestAddressCodeImpl {

    @Autowired
    private IAddressCodeService service = null;

    @Test
    public void init() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        AddressModel model = new AddressModel();
        model.setRequester_address("浙江省宁波市鄞州区集仕港宁波宝翔新材料有限公司");
        model.setRequest_number("2013106241473");

        TestAddressCodeImpl impl = (TestAddressCodeImpl) ctx.getBean("testAddressCodeImpl");

        try {
            impl.service.parse(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
