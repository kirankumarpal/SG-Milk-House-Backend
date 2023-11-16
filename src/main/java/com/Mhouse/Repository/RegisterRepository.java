package com.Mhouse.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mhouse.Model.RegisterModel;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterModel, Long> {

	Optional<RegisterModel> findByEmail(String email);

	Optional<RegisterModel> findByPassword(String password);

//	RegisterModel findByMobile(Long mobile);

//	List<RegisterModel> findByName(String name);
//	List<RegisterModel> findByNameOrMobno(String name, String mobno);
}
