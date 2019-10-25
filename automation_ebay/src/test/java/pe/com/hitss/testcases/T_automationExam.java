package pe.com.hitss.testcases;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



//import atu.testrecorder.ATUTestRecorder;
//import atu.testrecorder.exceptions.ATUTestRecorderException;
//import pe.com.hitss.db.DataBaseValidations;
import pe.com.hitss.util.ExcelUtils;
import pe.com.hitss.util.DriverConnection;
import pe.com.hitss.util.ElementOperations;
import pe.com.hitss.util.ReadConfiguration;

/**
 * @author Nilton Pacheco
 */

public class T_automationExam{

	private WebDriver webDriver = null;
	private File scrFile = null;
	private static Logger logger = Logger.getLogger(T_automationExam.class.getName());
	private static String url = "";  
	private static ElementOperations eO = new ElementOperations();
	//private ATUTestRecorder recorder;
	JavascriptExecutor js = (JavascriptExecutor) webDriver;
    ReadConfiguration readConfiguration = new ReadConfiguration();
    
	@BeforeTest
    //public void Configuracion() throws IOException, ATUTestRecorderException{
		public void Configuracion() throws IOException{
			try {	   
			Properties properties = readConfiguration.getConfigurationProperties();	
			url = properties.getProperty("driver.mainurl");
			//T_Inicio_Test.Inicio();	

	        String log4jConfigFile = System.getProperty("user.dir")
                        + "\\src\\main\\resources" + File.separator + "log4j.properties"; 	       
            PropertyConfigurator.configure(log4jConfigFile);
            logger.info("Cargar la configuracion...");
            webDriver = DriverConnection.getDriverConnection();
            eO.drive = webDriver;
						
			logger.info("Abrir la url: "+url);
			webDriver.get(url);
			} catch (SecurityException e) {logger.error(e.getMessage());}
     }
	
	@BeforeMethod
	public void iniciarTest(Method method) throws Exception  {
		logger.info("============ INICIO: " + method.getName()  + " ============");
	}

	@Test(priority=1)
	public void Ingreso() {
		  
		 try {	 
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
	     Date date = new Date();
		 //recorder = new ATUTestRecorder("D:\\tmp\\","TestVideo-"+dateFormat.format(date),false);

		 //recorder.start(); 

		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.fail();}
	    }
	@Test(priority=2,dataProvider = "dataCliente")
	public void aniadirCotizacionNatural( 
			String Producto,	
			String Marca	
			//) throws IOException, ATUTestRecorderException{ 
	) throws IOException{ 
		 try {
			 String cantResul,mensajeAlerta;
			 double price[] = new double[5];
			 String price_s[] = new String[5];
			 Thread.sleep(1000);
			 eO.setText("gh-ac", Producto, logger);
			 eO.clickById("gh-btn"); 
			 Thread.sleep(5000);
			 ((JavascriptExecutor)webDriver).executeScript("scroll(0,600)");
			 eO.setTextbyPath("//*[@id=\'w4-w12-0[0]\']", Marca, logger);
			 eO.clickByXpath("//*[@id=\'w4-w12\']/ul/li[6]/div/a/div/input");
			 ((JavascriptExecutor)webDriver).executeScript("scroll(0,600)");
			 eO.clickByXpath("//*[@id=\"x-refine__group_1__2\"]/ul/li[5]/div/a/div/input");
			 cantResul = eO.obtValorByXpath("//*[@id='mainContent']/div[1]/div/div[2]/div/div[1]/h1/span[1]");
			 logger.info("Number of Results "+cantResul);
			 eO.setFocus("w8", logger);		 
			 eO.clickByXpath("//*[@id=\'w8\']/div/div/ul/li[4]/a/span");
			 
			 mensajeAlerta = eO.obtValorByXpath("//*[@id=\'srp-river-results-message1\']/div/p");	
			 String mensaje[] = mensajeAlerta.split(" ");
			 logger.info("mensajeAlerta: " + mensaje[0]);
			 if(mensaje[0].trim().equals("Eliminamos")){
				 eO.clickByXpath("//*[@id=\'srp-river-results-message1\']/div/p/a/span");						 	 
			 	}
			 price[0] = Double.parseDouble(eO.obtValorByXpath("//*[@id=\'srp-river-results-listing1\']/div/div[2]/div[3]/div[1]/span").substring(4).trim());
			 price[1] = Double.parseDouble(eO.obtValorByXpath("//*[@id=\'srp-river-results-listing2\']/div/div[2]/div[3]/div[1]/span").substring(4).trim());
			 price[2] = Double.parseDouble(eO.obtValorByXpath("//*[@id=\'srp-river-results-listing3\']/div/div[2]/div[3]/div[1]/span").substring(4).trim());
			 price[3] = Double.parseDouble(eO.obtValorByXpath("//*[@id=\'srp-river-results-listing4\']/div/div[2]/div[3]/div[1]/span").substring(4).trim());
			 price[4] = Double.parseDouble(eO.obtValorByXpath("//*[@id=\'srp-river-results-listing5\']/div/div[2]/div[3]/div[1]/span").substring(4).trim());
				 
				 
			 System.out.println("The first 5 products with their prices, order by price ascendant"); 
			 System.out.println("Price 1: " + price[0]);
			 System.out.println("Price 2: " + price[1]);
			 System.out.println("Price 3: " + price[2]);
			 System.out.println("Price 4: " + price[3]);
			 System.out.println("Price 5: " + price[4]);
			  
			
			if ( price[0] <= price[1] && price[1] <= price[2] && price[2] <= price[3] && price[3] <= price[4] )
			 {
				System.out.println("El ordenamiento es correcto! "); 
			 }
			 else
			 {
				 System.out.println("El ordenamiento es incorrecto! "); ;
			 }
			
			eO.setFocus("w8", logger);		 
			eO.clickByXpath("//*[@id=\'w8\']/div/div/ul/li[5]/a/span");
			System.out.println("The first 5 products with their prices, Order by price descendant"); 
			price_s[0] = eO.obtValorByXpath("//*[@id=\'srp-river-results-listing1\']/div/div[2]/div[3]/div[1]/span").substring(4).trim();
			price_s[1] = eO.obtValorByXpath("//*[@id=\'srp-river-results-listing2\']/div/div[2]/div[3]/div[1]/span").substring(4).trim();
			price_s[2] = eO.obtValorByXpath("//*[@id=\'srp-river-results-listing3\']/div/div[2]/div[3]/div[1]/span").substring(4).trim();
			price_s[3] = eO.obtValorByXpath("//*[@id=\'srp-river-results-listing4\']/div/div[2]/div[3]/div[1]/span").substring(4).trim();
			price_s[4] = eO.obtValorByXpath("//*[@id=\'srp-river-results-listing5\']/div/div[2]/div[3]/div[1]/span").substring(4).trim();
			 //recorder.stop(); 
			
			 System.out.println("Price 1: " + price_s[0]);
			 System.out.println("Price 2: " + price_s[1]);
			 System.out.println("Price 3: " + price_s[2]);
			 System.out.println("Price 4: " + price_s[3]);
			 System.out.println("Price 5: " + price_s[4]);
			
		 } catch (SecurityException | InterruptedException e) {logger.error(e.getMessage());}
		 
    }
	@AfterMethod
	public void printResult(ITestResult result) throws IOException, InterruptedException {	
		 String testMethod = result.getMethod().getMethodName();
		 if (result.getStatus()==result.SUCCESS){
				logger.info("============ FINAL: " + testMethod  + " ============" + " => PASS");
		 }else{
				logger.info("============ FINAL: " +testMethod  + " ============" + " => FAIL");
		 }
	 } 
	
	@AfterTest
	public void End()
	
	 {
	 if (webDriver != null) {webDriver.quit();}
	 
	 }
	

	@DataProvider 
	public Object[][] dataCliente() throws Exception{	  
		Properties properties = readConfiguration.getConfigurationProperties();
	    Object[][] testObjArray = ExcelUtils.getTableArray(properties.getProperty("driver.dataProviderDir"),"VALIDA-PRODUCTO",2); 
		    	return (testObjArray);	 
	}	
	//recorder.stop();
}
