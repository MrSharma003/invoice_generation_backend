package com.invoice.webservices;


import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
public class Controller {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private PaymentService paymentService;

    @Autowired
    Controller(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/jpa/users/payment/charge")
    public Charge chargeCard(@RequestHeader(value="token") String token, @RequestHeader(value="amount") Double amount) throws Exception{
        System.out.println(this.paymentService.chargeNewCard(token, amount));
        return this.paymentService.chargeNewCard(token, amount);
    }

    @GetMapping("/users/logincheck/{username}/{password}")
    public boolean getSubService(@PathVariable String username, @PathVariable String password) {
        UserDetails details = userDetailsRepository.findUserDetailsByUsernameAndPassword(username, password);
        if(details == null)
            return false;
        else return true;
    }

    @GetMapping("/jpa/users/{username}/todos")
    public List<Invoice> getCompleteInvoice(@PathVariable String username){
        return itemRepository.findByUsername(username);
    }

    // get one entry of invoice
    @GetMapping("/jpa/users/{username}/todos/{id}")
    public Invoice getOneEntryOfInvoice(@PathVariable String username, @PathVariable long id){
        return itemRepository.findById(id).get();
    }

    //Delete /users/{username}/todos/{id}
    @DeleteMapping("/jpa/users/{username}/todos/{id}")
    public String deleteOneEntryOfInvoice(@PathVariable String username, @PathVariable long id){
        itemRepository.deleteById(id);
        return "Delete Successful";
    }

    //Edit/update a ToDO
    //Put /users/{user_name}/todos/{tods_id}
    @PutMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Invoice> updateTodo(
            @PathVariable String username,
            @PathVariable long id, @RequestBody Invoice entry){
        entry.setUsername(username);
        Invoice entryUpdated = itemRepository.save(entry);

        return new ResponseEntity<Invoice>(entryUpdated, HttpStatus.OK);
    }

    @PostMapping("/users/signup")
    public Long Signup(@RequestBody UserDetails userDetails){
        UserDetails ur = userDetailsRepository.save(userDetails);
        return ur.getId();
    }

    @PostMapping("/jpa/users/{username}/todos")
    public ResponseEntity<Void> createInvoiceEntry(
            @PathVariable String username, @RequestBody Invoice entry){

        entry.setUsername(username);

        Invoice createdTodo = itemRepository.save(entry);

        //Location
        //Get current resource url
        ///{id}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
