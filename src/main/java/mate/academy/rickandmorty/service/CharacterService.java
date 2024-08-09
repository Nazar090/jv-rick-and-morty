package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.CharacterDto;

public interface CharacterService {
    CharacterDto getRandomCharacter();

    List<CharacterDto> searchCharacters(String name);
}
