package mate.academy.rickandmorty.service;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public CharacterDto getRandomCharacter() {
        List<Character> characters = characterRepository.findAll();
        Random random = new Random();
        return characterMapper
                .toDo(characters.get(random.nextInt(characters.size())));
    }

    @Override
    public List<CharacterDto> searchCharacters(String name) {
        return characterRepository.findByNameContaining(name).stream()
                .map(characterMapper::toDo)
                .toList();
    }
}
