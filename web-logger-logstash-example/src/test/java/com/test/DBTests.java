package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.logstash.domain.OperatorLog;
import com.logstash.jdbc.JDBCOpreator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTests {
	@Autowired
	private JDBCOpreator opreator;

	@Test
	public void save() {
		opreator.save(new OperatorLog());
	}
}
