package com.abel.encurtador.services;

import com.abel.encurtador.domain.Url;
import com.abel.encurtador.repository.UrlRepository;
import com.abel.encurtador.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {
    @Autowired
    private UrlRepository repo;

    public Url saveUrl(Url obj) {
        // Verifica se a URL completa já esta no BD,
        Url aux = findLongUrl(obj.getLongUrl());
        if (aux == null) {
            // Não havendo registro inserimos o novo
            return repo.insert(obj);
        }
        // Havendo registro devolvemos a consulta
        return aux;
    }

    public Url findShortUrl(String text) {
        Optional<Url> res = repo.findByShortUrlContaining(text);
        return res.orElseThrow(() -> new ObjectNotFoundException("Não foi possivel localizar associações com essa url curta."));
    }

    public Url findLongUrl(String text) {
        Optional<Url> res = repo.findByLongUrlContaining(text);
        return res.orElseThrow(() -> new ObjectNotFoundException("Error ao tentar encurtar esta URL."));
    }
}
