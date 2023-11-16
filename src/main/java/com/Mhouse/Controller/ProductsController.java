package com.Mhouse.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;	
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Mhouse.Model.ProductsModel;
import com.Mhouse.Service.ProductsService;

@RestController
@CrossOrigin("*")
public class ProductsController {

	@Autowired
	ProductsService aps;

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestParam("name") String name, @RequestParam("brand") String brand,
			@RequestParam("price") int price, @RequestParam("image") MultipartFile image) {
		String product = aps.addProduct(name, brand, price, image);
		return ResponseEntity.ok(product);
	}

	@GetMapping("/getAllProducts") 
	public List<ProductsModel> getAllProducts(ProductsModel addProducts) {
		return aps.getAllProducts(addProducts);
	}

	@GetMapping("/getProductById")
	public ProductsModel getProductById(Long productId) {
		return aps.getProductById(productId);
	}

	@GetMapping("/getByNameOrBrand")
	public List<ProductsModel> findByNameOrBrand(@RequestParam("data") String data) {
		return aps.findByNameOrBrand(data);
	}

	@DeleteMapping("/deleteProduct")
	public void deleteProduct(Long productId) {
		aps.deleteProduct(productId);
	}

	@PutMapping("/updateProduct/{productId}")
	public String updateProduct(@PathVariable Long productId, String name, String brand, Integer price, MultipartFile image) {
		return aps.updateProduct(productId, name, brand, price, image);
	}
}
