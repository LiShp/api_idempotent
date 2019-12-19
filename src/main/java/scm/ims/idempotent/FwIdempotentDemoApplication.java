package scm.ims.idempotent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("scm.ims.idempotent.mapper")
@SpringBootApplication
public class FwIdempotentDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FwIdempotentDemoApplication.class, args);
	}

}

	