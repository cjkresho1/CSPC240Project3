package networkInterfaces;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void test() {
		Person test = new Person("Sean","Ellis","sean@email.com");
		test.setFirst("Charlie");
		assertEquals("Charlie",test.getFirst());
		test.setLast("Kresho");
		assertEquals("Kresho",test.getLast());
		test.setEmail("charlie@email.net");
		assertEquals("charlie@email.net",test.getEmail());
	}

}
