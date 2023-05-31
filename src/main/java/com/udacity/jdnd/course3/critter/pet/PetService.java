package com.udacity.jdnd.course3.critter.pet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;

@Service
@Transactional
public class PetService {
    
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.insertPet(pet);
        customerRepository.save(customer);
        return pet;
    }

    public Pet getPetById(Long petId){
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsByCustomerId(Long customerId){
        return petRepository.getAllByCustomerId(customerId);
    }
}
