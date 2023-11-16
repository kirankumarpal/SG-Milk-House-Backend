package com.Mhouse.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mhouse.Model.ProductsModel;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsModel, Long>{

	List<ProductsModel> findByNameOrBrand(String name, String brand);
}
