package sns.platform.biz.view;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="aa")
public class Testss {
	
	
	
	public Testss() {
	}

	@XmlElement(name="test")
	public String test;
	
	@XmlElement(name="aaaa")
	public String aaaa;


	/**
	 * @param test the test to set
	 */
	public void setTest(String test1) {
		this.test = test1;
	}


	/**
	 * @param aaaa the aaaa to set
	 */
	public void setAaaa(String aaaa1) {
		this.aaaa = aaaa1;
	}

	
}

/**
 * input 
 * <aa>
 * <bb>1</bb>
 * </aa>
 * */
