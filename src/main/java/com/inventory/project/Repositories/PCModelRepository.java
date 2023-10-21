package com.inventory.project.Repositories;

import com.inventory.project.Models.PCModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PCModelRepository extends JpaRepository<PCModel, Long> {

}
