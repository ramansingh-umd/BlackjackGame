package tests;
import static org.junit.Assert.*;
import org.junit.Test;

public class PublicTests {
	
	@Test
    public void test1DeckCreation() {
    	String results = SupportPublicTests.pubTest1DeckCreation();
    	System.out.println(results);
    	assertTrue(SupportPublicTests.correctResults("pubTest1DeckCreation.txt", results));
    }
    
	@Test
    public void test2GeneralValues() {
     	String results = SupportPublicTests.pubTest2GeneralValues();
    	assertTrue(SupportPublicTests.correctResults("pubTest2GeneralValues.txt", results));
    }
  
	@Test
    public void test3Deal() {
    	String results = SupportPublicTests.pubTest3Deal();
    	assertTrue(SupportPublicTests.correctResults("pubTest3Deal.txt", results));
    }
 
	@Test
    public void test4Play1() {
       	String results = SupportPublicTests.pubTest4Play1();
    	assertTrue(SupportPublicTests.correctResults("pubTest4Play1.txt", results));
    }
    
	@Test
    public void test5Play2() {
       	String results = SupportPublicTests.pubTest5Play2();
    	assertTrue(SupportPublicTests.correctResults("pubTest5Play2.txt", results));
    }
}