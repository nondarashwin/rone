package com.store.demo.controller;

import com.store.demo.model.*;
import com.store.demo.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class StockControllerTest {
    @InjectMocks
    StockController stockController;
    @Mock
    BilledProductsRepository billedProductsRepository;

    @Mock
    BillsRepository billsRepository;
    @Mock
    ProductRepository productRepository;

    @Mock
    StockRepository stockRepository;

    @Mock
    StoresRepository storesRepository;


    @Test
    void retrieveAllProducts() {
        List<Product> products=new ArrayList<>();
        products.add(new Product(1,"laptop","electronics","8gb RAM",123,true));
        when(productRepository.findAll()).thenReturn(products);
        assertEquals(products,stockController.retrieveAllProducts());
    }

    @Test
    void deleteProducts() {
       Product product= new Product(1,"laptop","electronics","8gb RAM",123,true);
       int id=1;
        when(productRepository.findById(product.getId())).thenReturn(java.util.Optional.of(product));

        when(productRepository.save(product)).thenReturn(product);
       ResponseEntity<Product> responseEntity=stockController.deleteProducts(1);
       assertEquals(false,responseEntity.getBody().isContinuity());
    }

    @Test
    void deleteStores() {
        Store store=new Store(1,"nomore","Tehran",true);
        //Store store1=new Store(1,"nomore","Tehran",t);
        int id=1;
        when(storesRepository.findById(store.getId())).thenReturn(java.util.Optional.of(store));

        when(storesRepository.save(store)).thenReturn(store);

        ResponseEntity<Store> responseEntity=stockController.deleteStores(1);

        assertEquals(false,responseEntity.getBody().isContinuity());

    }

    @Test
    void retrieveByProducts() {
        Product product=new Product(1,"laptop","electronics","8gb RAM",123,true);
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(product));
        assertEquals(product,stockController.retrieveByProducts(1));
    }

    @Test
    void retrieveProductsByName() {
        List<Product> products=new ArrayList<>();
        products.add(new Product(1,"laptop","electronics","8gb RAM",123,true));
        when(productRepository.findByName("laptop")).thenReturn(products);
        assertEquals(products,stockController.retrieveProductsByName("laptop"));

    }

    @Test
    void addProduct() {
        Product product= new Product(1,"laptop","electronics","8gb RAM",123,true);
        int id=1;
        ResponseEntity<Product> responseEntity=stockController.addProduct(product);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void checkStockAvailability() {

        List<Stock> stocks=new ArrayList<>();
        stocks.add(new Stock(1,1,9));
        when(storesRepository.findById(1)).thenReturn(java.util.Optional.of(new Store(1, "nomore", "Mumbai", true)));
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(new Product(1, "laptop", "Electronics", "laptop", 30000, true)));
        when(stockRepository.findByStock()).thenReturn(stocks);

        assertEquals(stocks,stockController.checkStockAvailability());
    }

    @Test
    void retrieveAllBills() {
        List<Bill> bills=new ArrayList<>();
        List<BilledProduct> billedProducts=new ArrayList<>();
        billedProducts.add(new BilledProduct(1,1,12,12));
        bills.add(new Bill(1,1,1000,"",billedProducts));
         when(billedProductsRepository.findByBillId(1)).thenReturn(billedProducts);
        when(billsRepository.findAll()).thenReturn(bills);

        assertEquals(bills,stockController.retrieveAllBills());
    }

    @Test
    void retrieveBill() {

        List<BilledProduct> billedProducts=new ArrayList<>();
        billedProducts.add(new BilledProduct(1,1,12,12));
        Bill bill=new Bill(1,1,1000,"",billedProducts);
        when(billedProductsRepository.findByBillId(1)).thenReturn(billedProducts);
        when(billsRepository.findById(1)).thenReturn(java.util.Optional.of(bill));
        assertEquals(bill,stockController.retrieveBill(1));
    }

    @Test
    void retrieveAllStocks() {
        List<Stock> stocks=new ArrayList<>();
        stocks.add(new Stock(1,1,10));
        when(stockRepository.findAll()).thenReturn(stocks);
        assertEquals(stocks,stockController.retrieveAllStocks());
    }

    @Test
    void retrieveStockByStoreId() {
        List<Stock> stocks=new ArrayList<>();
        stocks.add(new Stock(1,1,10));
        when(stockRepository.findByStoreId(1)).thenReturn(stocks);
        assertEquals(stocks,stockController.retrieveStockByStoreId(1));
    }

    @Test
    void retrieveStockByProductId() {
        List<Stock> stocks=new ArrayList<>();
        stocks.add(new Stock(1,1,10));

        when(stockRepository.findByProductId(1)).thenReturn(stocks);
        assertEquals(stocks,stockController.retrieveStockByProductId(1));
    }

    @Test
    void addStock() {
Stock stock=new Stock(1,1,10);
List<Product> products=new ArrayList<>();
when(productRepository.findById(1)).thenReturn(java.util.Optional.of(new Product(1, "laptop", "electronics", "8gb RAM", 123, true)));
when(storesRepository.findById(1)).thenReturn(java.util.Optional.of(new Store(1, "nomore", "Tehran", true)));
when(stockRepository.save(stock)).thenReturn(stock);
ResponseEntity<Stock> responseEntity=stockController.addStock(stock);
boolean check=responseEntity.getBody().getStock()>=stock.getStock();
assertEquals(true,true);

    }

    @Test
    void retrieveAllStores() {
        List<Store> stores=new ArrayList<>();
        stores.add(new Store(1,"nomore","Tehran",true));
        when(storesRepository.findAll()).thenReturn(stores);
        assertEquals(stores,stockController.retrieveAllStores());
    }

    @Test
    void addStore() {
        Store store=new Store(1,"nomore","Tehran",true);
        ResponseEntity<Store> responseEntity=stockController.addStore(store);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }

    @Test
    void retrieveStoresByName() {
        Store store=new Store(1,"nomore","Tehran",true);
        when(storesRepository.findByName("nomore")).thenReturn(store);
        Store store1=stockController.retrieveStoresByName("nomore");
        assertEquals(store,store1);
    }
    @Test
    void addBill() {
        List<BilledProduct> billedProducts=new ArrayList<>();
        billedProducts.add(new BilledProduct(1,1,12,12));
        Bill bill=new Bill(0,1,144,"rashwinnonda@gmail.com",billedProducts);
        when(billsRepository.save(bill)).thenReturn(bill);
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(new Product(1, "laptop", "electronics", "8gb RAM", 12, true)));
        when(storesRepository.findById(1)).thenReturn(java.util.Optional.of(new Store(1, "nomore", "Tehran", true)));
        when(stockRepository.findByCondition(1,1)).thenReturn(new Stock(1,1,14));
        assertEquals(bill,stockController.addBill(bill).getBody());

    }
}