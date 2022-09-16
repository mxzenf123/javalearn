package org.yangxin.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class Demo5 {

    @Autowired
    @Lazy
    private Demo4 demo4;

    public Demo4 getDemo4() {
        return demo4;
    }

    public void setDemo4(Demo4 demo4) {
        this.demo4 = demo4;
    }
}
