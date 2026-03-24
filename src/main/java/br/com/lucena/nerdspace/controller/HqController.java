package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaHqDetailed;
import br.com.lucena.nerdspace.dto.DataMediaHqList;
import br.com.lucena.nerdspace.model.Hq;
import br.com.lucena.nerdspace.repository.HqRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/hq")
public class HqController {
    public final HqRepository hqRepository;

    public HqController(HqRepository hqRepository) {
        this.hqRepository = hqRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity viewMediaHq(@PathVariable Long id) {
        Optional<Hq> hq = hqRepository.findById(id);
        if (hq.isPresent()) {
            return ResponseEntity.ok(new DataMediaHqDetailed(hq.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaHqList>> hqList(@PageableDefault Pageable pageable) {
        var page = hqRepository.findAllByActiveTrue(pageable)
                .map(DataMediaHqList::new);
        return ResponseEntity.ok(page);
    }
}
