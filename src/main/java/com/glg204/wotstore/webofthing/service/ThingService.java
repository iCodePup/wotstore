package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dto.ThingDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ThingService {

    Optional<List<ThingDTO>> getThings();

    Optional<ThingDTO> getByTitle(String title);


}
