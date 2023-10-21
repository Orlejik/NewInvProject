package com.inventory.project.Controllers;

import com.inventory.project.Models.PCModel;
import com.inventory.project.Repositories.PCModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@Slf4j
@RestController
@RequestMapping("/pcs_list")
public class PCModelController {

    private final PCModelRepository pcModelRepository;

    public PCModelController(PCModelRepository pcModelRepository) {
        this.pcModelRepository = pcModelRepository;
    }

    @GetMapping
    public List<PCModel> getPCs() {
        return pcModelRepository.findAll();
    }

    @GetMapping("/{id}")
    public PCModel getPCbyId(@PathVariable Long id) {
        return pcModelRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity addPCOnServer(@RequestBody PCModel pcModel) throws URISyntaxException {
        PCModel addedPC = pcModelRepository.save(pcModel);
        return ResponseEntity.created(new URI("/pcs_list/" + addedPC.getId())).body(addedPC);
    }

    @PutMapping("{id}")
    public ResponseEntity updatePC(@PathVariable Long id, @RequestBody PCModel pcModel) {
        PCModel currentPC = pcModelRepository.findById(id).orElseThrow(RuntimeException::new);
        currentPC.setHostname(pcModel.getHostname());
        currentPC.set_Online(pcModel.is_Online());
        currentPC.setOs_name(pcModel.getOs_name());
        currentPC.setOs_arch(pcModel.getOs_arch());
        currentPC.setIp_address(pcModel.getIp_address());
        currentPC.setUser_name(pcModel.getUser_name());

        return ResponseEntity.ok(currentPC);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePC(@PathVariable Long id) {
        pcModelRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
