package com.fatec.ourtub.controller;

import com.fatec.ourtub.model.CurtidaVideo;
import com.fatec.ourtub.repository.CurtidaVideoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curtidavideo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CurtidaVideoController {

	@Autowired
	private CurtidaVideoRepository repository;

	@GetMapping("/{video}")
	public ResponseEntity<List<CurtidaVideo>> getByVideo(@PathVariable Long video) {
		List<CurtidaVideo> curtidas = repository.findByVideoId(video);
		if (curtidas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(curtidas);
	}

	@PostMapping
	public ResponseEntity<CurtidaVideo> post(@RequestBody CurtidaVideo curtidaVideo) {
		try {
			List<CurtidaVideo> curtidas = repository.findByVideoIdAndUsuarioId(curtidaVideo.getVideo().getId(),
					curtidaVideo.getUsuario().getId());
			if (!curtidas.isEmpty()) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(curtidaVideo));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
}
