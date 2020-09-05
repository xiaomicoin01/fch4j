package org.freecash.math;

import lombok.extern.log4j.Log4j2;
import org.freecash.core.util.FreeCashUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Log4j2
public class FchMathApplication implements CommandLineRunner{

    public static void main(String[] args) throws Exception{

        SpringApplication.run(FchMathApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        for(;;){
            List<String> infos = FreeCashUtil.getFreecashInfo();
            String address = infos.get(0);
            String privateKey = infos.get(1);
            if(address.endsWith("rite")){
                System.out.println("address:"+address);
                System.out.println("privateKey:"+privateKey);
                break;
            }
        }
    }
}
