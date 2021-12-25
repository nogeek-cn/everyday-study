package _2021_08_07_interview_people_walk_ddd;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/***
 * 1. 面向对象的模式下，walk 方法只耦合 age，应该为 People 的内置实现。
 * 2. walk 方法是多变的，所以内置实现不合理
 * 3. 内置实现不合理，（内置工厂对象为重耦合）那么就需要外置工厂
 * 4. 获取外置工厂，使用静态容器控制器去做，弱耦合
 * 5. 工厂的实现用基础设置，最小化复杂度
 *
 * @author darian1996
 */
@SpringBootApplication
public class PeopleApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(PeopleApplicationTest.class, args);

        new People("jack", 1).walk();
        new People("darian_1", 2).walk();
        new People("darian_2", 2).walk();
        new People("james", 3).walk();
        new People("miss", 4).walk();
        new People("m", 5).walk();
    }
}

@Data
@RequiredArgsConstructor
class People {
    private final String name;
    private final int age;


    public void walk() {

        ApplicationContextHold.getApplicationContext()
                .getBean(generatorPeopleWalkBeanName(age), PeopleWalkService.class)
                .walk();
    }

    public static String generatorPeopleWalkBeanName(int age) {
        return "peopleWalkService" + age;
    }
}

@Component
class ApplicationContextHold implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}

interface PeopleWalkService {

    default void walk() {
        System.out.println(String.format("[%s:walk]", getClass().getSimpleName()));
    }
}

@Service
class PeopleWalkService1 implements PeopleWalkService {
}


@Service
class PeopleWalkService2 implements PeopleWalkService {
}

@Service
class PeopleWalkService3 implements PeopleWalkService {

}

@Service
class PeopleWalkService4 implements PeopleWalkService {

}

@Service
class PeopleWalkService5 implements PeopleWalkService {

}

