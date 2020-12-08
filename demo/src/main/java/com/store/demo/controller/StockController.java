package com.store.demo.controller;

import com.store.demo.exception.StocksException;
import com.store.demo.mail.SendMail;
import com.store.demo.model.*;
import com.store.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:3000")
@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
public class StockController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BilledProductsRepository billedProductsRepository;
    @Autowired
    private BillsRepository billsRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StoresRepository storesRepository;
    @Autowired
            private StorePasswordRepository storePasswordRepository;
    SendMail sendMail = new SendMail();

    public void sendMailToAdmin(List<Stock> stocks) {

        String message = "Dear Admin,<br>Requires stock update as soon as possible.<br><table border=1px><tr><th>Store Name</th><th>Product Name</th><th>Stock Remaining</th></tr>";
        for (Stock stock : stocks) {
            Store store = storesRepository.findById(stock.getStoreId()).orElseThrow(() -> new StocksException("Store Not Found "));
            Product product = productRepository.findById(stock.getProductId()).orElseThrow(() -> new StocksException("Product Not Found"));
            String temp = message + "<tr><td>" + store.getName() + "</td><th>" + product.getName() + "</td><th>" + stock.getStock() + "</th></tr>";
            message = temp;
        }

        sendMail.sendMail("rashwinnonda@gmail.com", "Stock Update", message + "</table><br>your sincerely,<br>Machine");
    }
    public void sendMailToStore(StorePassword storePassword){
        String message="Dear User,<br>userId:"+storePassword.getStoreId()+"<br>password:"+storePassword.getPassword()+"<br>your sincerely,<br>Machine";
        sendMail.sendMail(storePassword.getMailId(),"Welcome",message);
    }
    public void sendMailToCustomer(Bill bill) {

        String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        if (!bill.getEmailId().matches(emailPattern)) {
            //throw new StocksException("Emailid is not valid");
            return;
        }
        Store store = storesRepository.findById(bill.getStoreId()).orElseThrow(() -> new StocksException("Store NotFound"));
        String message = "Dear Customer,<br>this is your bill for today's shopping<br><h5>Bill ID=" + bill.getId() + "</h5><h5>Store Name" + store.getName() + "</h5> <table border=1px><tr><th>Product Name</th><th>Quantity</th><th>Unit Cost</th><th>totals</th></tr>";
        for (BilledProduct billedProduct : bill.getBilledProducts()) {
            String temp = message;
            Product product = productRepository.findById(billedProduct.getProductId()).orElseThrow(() -> new StocksException("Product Not Found"));
            message = temp + "<tr><td>" + product.getName() + "</td><th>" + billedProduct.getQuantity() + "</th><th>" + billedProduct.getCost() + "</th><th>" + (billedProduct.getQuantity() * billedProduct.getCost()) + "</th></tr>";
        }
        sendMail.sendMail(bill.getEmailId(), "Bill", message + "<tr><th>  </th>  <th>  </th><th>  </th><th>Amount=" + bill.getTotalAmount() + "</th></tr></table><br>yours sincerely<br>Your Favourite Store ");

    }

    public double totalAmount(List<BilledProduct> viewBilledProducts) {
        double total = 0;
        for (BilledProduct viewBilledProduct : viewBilledProducts) {
            Product product = productRepository.findById(viewBilledProduct.getProductId()).orElseThrow(() -> new StocksException("Product Not Found"));
            total += (product.getCost() * viewBilledProduct.getQuantity());

        }
        return total;
    }
    public String generatePassword(){
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "@,";
        int len=8;

        String values = Capital_chars + Small_chars +
                numbers + symbols;

        // Using random method
        Random rndm_method = new Random();

        char[] password = new char[len];

        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            password[i] =
                    values.charAt(rndm_method.nextInt(values.length()));

        }
        return new String(password);
    }
    public void checkStock(List<BilledProduct> viewBilledProducts, int storeID) {
        for (BilledProduct temp : viewBilledProducts) {
            Stock stock = stockRepository.findByCondition(temp.getProductId(), storeID);
            if (stock == null) {
                throw new StocksException("Stock is not available for the given Product");
            }
            if (stock.getStock() < temp.getQuantity()) {
                throw new StocksException("Stock is not available for the given Product");
            }
        }
    }

    @GetMapping(value = "/products")
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    @DeleteMapping(value = "/product/id/{id}")
    public ResponseEntity<Product> deleteProducts(@PathVariable int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new StocksException("Product Not found"));
        product.setContinuity(false);
        return addProduct(product);


    }

    @DeleteMapping(value = "/store/id/{id}")
    public ResponseEntity<Store> deleteStores(@PathVariable int id) {
        Store store = storesRepository.findById(id).orElseThrow(() -> new StocksException("Store Not Found"));

        if (!store.isContinuity()) {
            throw new StocksException("Store Not Found");
        }
        store.setContinuity(false);
        List<Stock> stocks = stockRepository.findByStoreId(store.getId());
        for (Stock stock : stocks) {
            stockRepository.delete(stock);
        }
        return addStore(store);


    }
@GetMapping("/salesStore")
public List sales(){
        return billsRepository.findByCondition();
}
    @GetMapping("/topSellingProduct")
    public List TopProduct(){
        return productRepository.findByCondition();
    }
    @GetMapping("/topSellingProduct/store/{id}")
    public List TopProductByStore(@PathVariable int id){
        return storesRepository.findByCondition(id);
    }
    @GetMapping(value = "/product/id/{id}")
    public Product retrieveByProducts(@PathVariable int id) {
        return productRepository.findById(id).orElseThrow(() -> new StocksException("Product Not Found"));
    }

    @GetMapping(value = "/product/name/{name}")
    public List<Product> retrieveProductsByName(@PathVariable String name) {
        return productRepository.findByName(name);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        //product.setContinuity(true);
        //System.out.println(product.toString());
        Product product1 = productRepository.save(product);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Response-from", "ProductValue");

        return new ResponseEntity(product1, headers, HttpStatus.OK);
    }
    @PostMapping("/storepassword")
    public ResponseEntity<StorePassword> addStorePassword(@RequestBody StorePassword storePassword){
        storePassword.setPassword(generatePassword());
        System.out.println(storePassword.getPassword());
        StorePassword storePassword1=storePasswordRepository.save(storePassword);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Response-from", "ProductValue");

        sendMailToStore(storePassword1);
        return new ResponseEntity(storePassword1, headers, HttpStatus.OK);

    }
    @PostMapping("/getstorepassword")
    public ResponseEntity<StorePassword> getStorePassword(@RequestBody StorePassword storePassword){
        StorePassword storePassword1=storePasswordRepository.findById(storePassword.getStoreId()).orElseThrow(()->new StocksException("User Id not found"));
        if(!storePassword.getPassword().equals(storePassword1.getPassword())){
            throw new StocksException("Password doesnt match");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Response-from", "ProductValue");

        return new ResponseEntity(storePassword1, headers, HttpStatus.OK);

    }

    @GetMapping("/stock/isenough")
    public List<Stock> checkStockAvailability() {
        List<Stock> stocks = stockRepository.findByStock();
        //MailHandler mailHandler=new MailHandler();
        sendMailToAdmin(stocks);
        return stocks;
    }

    @GetMapping(value = "/bills")
    public List<Bill> retrieveAllBills() {
        List<Bill> bills = billsRepository.findAll();

        for (Bill bill : bills) {

            List<BilledProduct> billedProducts = billedProductsRepository.findByBillId(bill.getId());
            bill.setBilledProducts(billedProducts);
        }
        return bills;
    }

    @GetMapping(value = "/bill/id/{id}")
    public Bill retrieveBill(@PathVariable int id) {
        Bill bill = billsRepository.findById(id).orElseThrow(() -> new StocksException("Bill Id not found"));
        if (bill == null) {
            throw new StocksException("Bill Id Not found");
        }

        List<BilledProduct> billedProducts = billedProductsRepository.findByBillId(bill.getId());
        bill.setBilledProducts(billedProducts);
        return bill;

    }

    @GetMapping(value = "/stock")
    public List<Stock> retrieveAllStocks() {
        return stockRepository.findAll();
    }

    @GetMapping(value = "/store/stock/id/{id}")
    public List<Stock> retrieveStockByStoreId(@PathVariable int id) {
        return stockRepository.findByStoreId(id);
    }

    @GetMapping(value = "/product/stock/id/{id}")
    public List<Stock> retrieveStockByProductId(@PathVariable int id) {
        return stockRepository.findByProductId(id);
    }

    @PostMapping("/stock")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {

        // System.out.println(stock.toString());

        Product product = productRepository.findById(stock.getProductId()).orElseThrow(() -> new StocksException("Product Not Found"));
        if (product == null || !product.isContinuity()) {
            throw new StocksException("Product Not Found");
        }
        Store store = storesRepository.findById(stock.getStoreId()).orElseThrow(() -> new StocksException("Store Not Found"));
        if (store == null || !product.isContinuity()) {
            throw new StocksException("Store Not Found");
        }
        Stock tempStock = stockRepository.findByCondition(stock.getProductId(), stock.getStoreId());
        if (tempStock != null) {
            //System.out.println("not null");
            stockRepository.delete(tempStock);
            stock.setStock(stock.getStock() + tempStock.getStock());
        }
        if (stock.getStock() < 0) {
            throw new StocksException("stock cant be negative");
        }
        Stock stock1 = stockRepository.save(stock);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Response-from", "ProductValue");

        return new ResponseEntity(stock1, headers, HttpStatus.OK);
    }


    @GetMapping(value = "/store")
    public List<Store> retrieveAllStores() {
        return storesRepository.findAll();
    }

    @GetMapping(value = "/store/id/{id}")
    public Store retrieveStoresById(@PathVariable int id) {
        return storesRepository.findById(id).orElseThrow(() -> new StocksException("Store Not Found"));
    }

    @PostMapping("/store")
    public ResponseEntity<Store> addStore(@RequestBody Store store) {
        //store.setContinuity(true);

        //System.out.println(store.toString());
        Store store1 = storesRepository.save(store);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Response-from", "ProductValue");

        return new ResponseEntity(store1, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/store/name/{name}")
    public Store retrieveStoresByName(@PathVariable String name) {
        return storesRepository.findByName(name);
    }


    @PostMapping("/bill")
    public ResponseEntity<Bill> addBill(@RequestBody Bill bill) {
       /*
       if (!bill.getEmailId().matches(emailPattern)) {
            //throw new StocksException("Emailid is not valid");
            return;
        }*/
        checkStock(bill.getBilledProducts(), bill.getStoreId());
        bill.setTotalAmount((int) totalAmount(bill.getBilledProducts()));
        Bill bill1 = billsRepository.save(bill);
        bill.setId(bill1.getId());
        List<BilledProduct> billedProducts = bill.getBilledProducts();
        List<BilledProduct> billedProducts1 = new ArrayList<>();
        for (BilledProduct temp : billedProducts) {
            Stock stock = stockRepository.findByCondition(temp.getProductId(), bill1.getStoreId());
            if (stock != null || (stock.getStock() - temp.getQuantity()) >= 0) {

                stockRepository.delete(stock);
                stock.setStock(stock.getStock() - temp.getQuantity());
                stockRepository.save(stock);
                Product product = productRepository.findById(stock.getProductId()).orElseThrow(() -> new StocksException("Product Not Found"));
                temp.setCost(product.getCost());
                temp.setBillId(bill1.getId());
                billedProducts1.add(temp);
                billedProductsRepository.save(temp);

            } else {
                //billsRepository.delete(bill1);
                throw new StocksException("Stock is Not available");
            }

        }
        bill1.setBilledProducts(billedProducts1);
        sendMailToCustomer(bill1);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Response-from", "ProductValue");

        return new ResponseEntity(bill1, headers, HttpStatus.OK);
    }
}
