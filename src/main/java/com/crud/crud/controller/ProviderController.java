package com.crud.crud.controller;

import com.crud.crud.exception.ResourceNotFoundException;
import com.crud.crud.model.Body;
import com.crud.crud.model.Provider;
import com.crud.crud.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    // get Providers
    @GetMapping
    public List<Provider> getAllProviders(){
        return this.providerRepository.findAll();
    }

    //get Providers by Provider Name
    @GetMapping("{providerName}")
    public ResponseEntity<Provider> getProviderByProviderName(@PathVariable(value = "providerName") String providerName){
        Provider provider =  providerRepository.findByProviderName(providerName);
        if(provider!=null){
            return ResponseEntity.ok().body(provider);
        }
        ResourceNotFoundException ex = new ResourceNotFoundException("Provider with name " + providerName + " does not exist");
        throw ex;
    }

    //create Provider
    @PostMapping
    public Provider createProvider(@RequestParam String providerName, @RequestParam String flowName,
                                   @RequestBody Body body) {
        Provider provider = new Provider();
        provider.setProviderName(providerName);
        provider.setFlowName(flowName);
        provider.setDownFrom(body.getDownFrom());
        provider.setDownTo(body.getDownTo());
        return this.providerRepository.save(provider);
    }

    //update Provider
    @PutMapping("{providerName}")
    public ResponseEntity<Provider> updateProvider(@PathVariable(value = "providerName") String providerName,
                                                   @RequestBody Provider providerDetails){

        Provider provider = providerRepository.findByProviderName(providerName);
        if(provider!=null){
            if(providerDetails.getProviderName()!=null){
                provider.setProviderName(providerDetails.getProviderName());
            }
            if(providerDetails.getFlowName()!=null){
                provider.setFlowName(providerDetails.getFlowName());
            }
            if(providerDetails.getDownFrom()!=null){
                provider.setDownFrom(providerDetails.getDownFrom());
            }
            if(providerDetails.getDownTo()!=null){
                provider.setDownTo(providerDetails.getDownTo());
            }
            final Provider updatedProvider = providerRepository.save(provider);
            return ResponseEntity.ok(updatedProvider);
        }
        ResourceNotFoundException ex = new ResourceNotFoundException("Provider with name " + providerName + " does not exist");
        throw ex;
    }

    //delete Provider
    @DeleteMapping("{providerName}")
    public Map<String, Boolean> deleteProviderByProviderName(@PathVariable(value = "providerName") String providerName){
        Provider provider = providerRepository.findByProviderName(providerName);
        if(provider!=null){
            providerRepository.delete(provider);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        }
        ResourceNotFoundException ex = new ResourceNotFoundException("Provider with name " + providerName + " does not exist");
        throw ex;
    }
}
