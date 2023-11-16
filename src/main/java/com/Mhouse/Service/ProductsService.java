package com.Mhouse.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Mhouse.Model.ProductsModel;
import com.Mhouse.Repository.ProductsRepository;

@Service
public class ProductsService {

	@Autowired
	ProductsRepository apr;

	public String addProduct(String name, String brand, int price, MultipartFile image) {
		ProductsModel apm = new ProductsModel();
		apm.setName(name);
		apm.setBrand(brand);
		apm.setPrice(price);
		try {
			apm.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		apm.setDatetime(new Date());
		apr.save(apm);
		return "1 product has been added succssfully";
	}

	public List<ProductsModel> getAllProducts(ProductsModel addProducts) {
		return apr.findAll();
	}

	public void deleteProduct(Long productId) {
		if (apr.findById(productId).isPresent()) {
			apr.deleteById(productId);
		} else {
			System.out.println("Id is not present.");
		}
	}

	public ProductsModel getProductById(Long productId) {
		if (apr.findById(productId).isPresent())
			return apr.findById(productId).get();
		else
			return null;
	}

	public String updateProduct(Long productId, String name, String brand, int price, MultipartFile image) {
		ProductsModel apm = apr.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
		apm.setName(name);
		apm.setBrand(brand);
		apm.setPrice(price);
		apm.setDatetime(new Date());
		try {
			apm.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		apr.save(apm);
		return "1 product has been updated succssfully";
	}

	public List<ProductsModel> findByNameOrBrand(String data) {
		return apr.findByNameOrBrand(data, data);
	}

}
