package gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario;

import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model.FruitEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InventarioApplicationTests {
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @Test
    public void whenMultipleInsert_thenMultipleListedItem() {
        System.out.println("Test #1 - When multiple insert then multiple listed items.");
        
        // given
        FruitEntity banano = new FruitEntity("Banano", 2.50f, 100l);
        FruitEntity papaya = new FruitEntity("Papaya", 15.00f, 15l);
        FruitEntity melon = new FruitEntity("Melon", 14.00f, 10l);
        FruitEntity sandia = new FruitEntity("Sandia", 35.50f, 17l);
        String expectedResult = "[Banano, Papaya, Melon, Sandia]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        FruitEntity insertPapaya = this.testRestTemplate.postForObject("/inventory/fruit/register", papaya, FruitEntity.class);
        FruitEntity insertMelon = this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        FruitEntity insertSandia = this.testRestTemplate.postForObject("/inventory/fruit/register", sandia, FruitEntity.class);
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitNames", String.class);
        
        // then
        Assert.assertEquals("Test #1 failed!!!", expectedResult, actualResult);
    }
    
    @Test
    public void whenSingleInsert_thenSingleListedItem() {
        System.out.println("Test #2 - When single insert then single listed item.");
        
        // given
        FruitEntity banano = new FruitEntity("Banano", 10.50f, 100l);
        String expectedResult = "[Banano]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitOrderedByNameAsc", String.class);
        
        // then
        Assert.assertEquals("Test #2 failed!!!", expectedResult, actualResult);
    }
    
    @Test
    public void whenMultipleInsert_thenMultipleListedItemOrdered() {
        System.out.println("Test #3 - When multiple insert then multiple listed items ordered by name asc.");
        
        // given
        FruitEntity papaya = new FruitEntity("Papaya", 10.50f, 100l);
        FruitEntity melon = new FruitEntity("Melon", 25.00f, 150l);
        String expectedResult = "[Melon, Papaya]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertPapaya = this.testRestTemplate.postForObject("/inventory/fruit/register", papaya, FruitEntity.class);
        FruitEntity insertMelon = this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitOrderedByNameAsc", String.class);
        
        // then
        Assert.assertEquals("Test #3 failed!!!", expectedResult, actualResult);
    }
    
    @Test
    public void whenDeleteSpecificFruit_thenListMustNotContainDeletedFruit() {
        System.out.println("Test #4 - When delete specific fruit, then fruits list must not contain deleted fruit.");
        
        // given
        FruitEntity banano = new FruitEntity("Banano", 2.50f, 100l);
        FruitEntity papaya = new FruitEntity("Papaya", 15.00f, 15l);
        FruitEntity melon = new FruitEntity("Melon", 14.00f, 10l);
        FruitEntity sandia = new FruitEntity("Sandia", 35.50f, 17l);
        String expectedResult = "[Banano, Papaya, Sandia]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        FruitEntity insertPapaya = this.testRestTemplate.postForObject("/inventory/fruit/register", papaya, FruitEntity.class);
        FruitEntity insertMelon = this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        FruitEntity insertSandia = this.testRestTemplate.postForObject("/inventory/fruit/register", sandia, FruitEntity.class);        
        this.testRestTemplate.delete("/inventory/fruit/remove?name=Melon");
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitNames", String.class);
                
        // then
        Assert.assertEquals("Test #4 failed!!!", expectedResult, actualResult);
    }

}
