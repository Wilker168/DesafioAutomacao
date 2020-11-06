package br.com.wilkercavalcanti.desafioneurotech;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ColocarProdutoNaCestaTest {
	WebDriver navegador;
	
	@Before
	public void inicializa() {
		// Abrindo o navegador
		WebDriverManager.chromedriver().setup();
		navegador = new ChromeDriver();

		// Navegar para página Lojas Americanas
		navegador.get("https://www.americanas.com.br/");
		aguardarTempo(3);
	}
	
	@Test
	public void testBuscarProduto() {

		// Clicar em 'Busque aqui seu produto' e buscar pelo produto "PS4"
		WebElement buscarProduto = buscarElementoPorId("h_search-input");
		buscarProduto.sendKeys("PS4");
		buscarProduto.submit();

		aguardarTempo(3);

		// Selecionar o Primeiro console
		WebElement selecionarProduto = buscarElementoPorXpath("//div[@class='src__Wrapper-sc-1di8q3f-2"
				+ " ugkhp']//a//span[text()[contains(.,'Console')]]");
		selecionarProduto.click();
        
		aguardarTempo(3);
		
		// Digitar CEP
		WebElement digitarCEP = buscarElementoPorName("cep");
		digitarCEP.sendKeys("53350-730");
        
		aguardarTempo(3);
		
		// Clicar no botão 'OK'
		WebElement botaoOK = buscarElementoPorXpath("//button[text()[contains(.,'ok')]]");
		botaoOK.click();
		
		aguardarTempo(1);

		// Impressão de preços e prazos
		List<WebElement> listaPrazos = buscarListaElementosPorXpath("//span[@class='src__Text-sc-154pg0p-0"
				+ " freight-options__TextUI-wrpkqe-6 ezEZLI freight-option-range']//b");
		
		List<WebElement> listaPrecos = buscarListaElementosPorXpath("//span[@class=\"src__Text-sc-154pg0p-0"
				+ " gLIAcy freight-option-price\"]//b");
		
		imprimirTextoFrete(listaPrazos, listaPrecos);
		
		aguardarTempo(3);
		
		// Clicar no botão 'COMPRAR'
		WebElement botaoComprar = buscarElementoPorXpath("//div[@class=\"src__BuyButtonWrapper-lknyo-2"
				+ " erPeWH\"]//a//span[text()[contains(.,'comprar')]]");
		botaoComprar.click();
		
		aguardarTempo(3);
		
		// Selecionar garantia +12meses
		WebElement selecionarGarantia = buscarElementoPorXpath("//label//span[text()[contains(.,'12 meses')]]");
		selecionarGarantia.click();
		
		// Clicar no botão 'continuar'
		WebElement botaoContinuar = buscarElementoPorId("btn-continue");
		botaoContinuar.click();
		
		aguardarTempo(3);
		
		WebElement produtoCesta = buscarElementoPorXpath("//div[@class='basket-productTitle__wrapper']"
				+ "//*[contains(@title, 'Console')]");
		
		Assert.assertTrue(produtoCesta.isDisplayed());
	}
	
	@After
	public void finaliza() {
		navegador.quit();
	}

	private void imprimirTextoFrete(List<WebElement> listaPrazos, List<WebElement> listaPrecos) {
		for (int i = 0; i < listaPrazos.size(); i++) {
			String prazo = listaPrazos.get(i).getText();
			String preco = listaPrecos.get(i).getText();
			
			String textoFrete = String.format("Prazo: %s - Preço: %s", prazo, preco);
			System.out.println(textoFrete);
		}
	}

	private List<WebElement> buscarListaElementosPorXpath(String xpathElemento) {
		return navegador.findElements(By.xpath(xpathElemento));
	}

	private WebElement buscarElementoPorId(String idElemento) {
		return navegador.findElement(By.id(idElemento));
	}
	
	private WebElement buscarElementoPorName(String nameElemento) {
		return navegador.findElement(By.name(nameElemento));
	}
	
	private WebElement buscarElementoPorXpath(String xpathElemento) {
		return navegador.findElement(By.xpath(xpathElemento));
	}

	private void aguardarTempo(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
