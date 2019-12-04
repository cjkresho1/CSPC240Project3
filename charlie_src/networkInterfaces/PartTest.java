package networkInterfaces;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PartTest {

	@Test
	void test() {
		Part test = new Part("bikeSeat",385913);
		test.setName("bikeWheel");
		assertEquals("bikeWheel", test.getName());
		test.setPartNum(265078);
		assertEquals(265078, test.getPartNum());
	}

}
