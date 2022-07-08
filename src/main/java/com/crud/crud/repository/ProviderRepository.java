package com.crud.crud.repository;

import com.crud.crud.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    //finder method for accessing different providers
    Provider findByProviderName(String providerName);
}
