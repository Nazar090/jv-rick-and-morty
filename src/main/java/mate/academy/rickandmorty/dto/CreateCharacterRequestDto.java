package mate.academy.rickandmorty.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateCharacterRequestDto {
    @NotBlank
    private String externalId;
    @NotBlank
    private String name;
    @NotBlank
    private String status;
    @NotBlank
    private String gender;
}
