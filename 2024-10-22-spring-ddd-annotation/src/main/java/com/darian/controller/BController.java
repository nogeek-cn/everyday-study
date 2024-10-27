package com.darian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/27  14:08
 */
@Controller
public class BController {

    @Autowired
    private BService bService;;

    public void say(){
        bService.say();
    }
}
