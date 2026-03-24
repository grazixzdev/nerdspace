package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaAnimeList;
import br.com.lucena.nerdspace.dto.DataMediaAnimeDetailed;
import br.com.lucena.nerdspace.model.Anime;

import br.com.lucena.nerdspace.repository.AnimeRepository;
import br.com.lucena.nerdspace.repository.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final MediaRepository mediaRepository;
    private final AnimeRepository animeRepository;

    public AnimeController(MediaRepository mediaRepository, AnimeRepository animeRepository) {
        this.mediaRepository = mediaRepository;
        this.animeRepository = animeRepository;
    }

    @GetMapping
    public ResponseEntity oi(){
        return ResponseEntity.ok().body("Oi");
    }

    @GetMapping("/{id}")
    public ResponseEntity viewMediaAnime(@PathVariable Long id) {
        Optional<Anime> anime = animeRepository.findById(id);
        if (anime.isPresent()) {
            return ResponseEntity.ok().body(new DataMediaAnimeDetailed(anime.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaAnimeList>> animeList(@PageableDefault Pageable pageable) {
        var page = animeRepository.findAllByActiveTrue(pageable)
                .map(DataMediaAnimeList::new);
        return ResponseEntity.ok(page);
    }


//    @PostMapping("/adicionar")
//    @Transactional
//    public ResponseEntity createCourse(@RequestBody @Valid DataCoursePost data, UriComponentsBuilder uriBuilder) {
//        courseService.checkPost(data);
//        Course course = new Course(data);
//        reposityCourse.save(course);
//        var uri = uriBuilder.path("/curso/{id}").buildAndExpand(course.getId()).toUri();
//        return ResponseEntity.created(uri).body(new DataCourseDetailed(course));
//    }

//    @PutMapping("/atualizar/{id}")
//    @Transactional
//    public ResponseEntity updateCourse(@PathVariable Long id, @RequestBody DataCourseUpdate data) {
//        Optional<Course> courseOptional = reposityCourse.findByIdAndActiveTrue(id);
//        if (courseOptional.isPresent()){
//            courseService.checkUpdate(data);
//            Course course = courseOptional.get();
//            course.update(data);
//            return ResponseEntity.ok(new DataCourseDetailed(course));
//        } else return ResponseEntity.notFound().build();
//    }
//    @DeleteMapping("/deletar/{id}")
//    @Transactional
//    public ResponseEntity deleteCourse(@PathVariable Long id){
//        Optional<Course> courseOptional = reposityCourse.findByIdAndActiveTrue(id);
//        if (courseOptional.isPresent()){
//            Course course = courseOptional.get();
//            course.delete();
//            return ResponseEntity.noContent().build();
//        } else return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/categoria/{idCategory}")
//    public ResponseEntity<Page<DataCourseList>> CategoryCourse(@PathVariable int idCategory, @PageableDefault(size = 10) Pageable pageable) {
//        CategoryCourse categoryCourse = CategoryCourse.values()[idCategory];
//        var page = reposityCourse.findAllByCategoryAndActiveTrue(categoryCourse, pageable)
//                .map(DataCourseList::new);
//        return ResponseEntity.ok(page);
//    }
}
