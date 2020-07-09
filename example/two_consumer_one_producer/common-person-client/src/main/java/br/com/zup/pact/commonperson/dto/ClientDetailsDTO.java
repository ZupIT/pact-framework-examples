package br.com.zup.pact.commonperson.dto;

import br.com.zup.pact.commonperson.entity.Client;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDetailsDTO {

    private Integer id;
    private Integer accountId;
    private String name;
    private String finalName;
    private Integer age;

    public static Client fromDtoToEntity(ClientDetailsDTO clientDetailsDTO) {
        if (Objects.nonNull(clientDetailsDTO)) {
            return Client.builder()
                    .id(clientDetailsDTO.getId())
                    .accountId(clientDetailsDTO.getAccountId())
                    .name(clientDetailsDTO.getName())
                    .finalName(clientDetailsDTO.getFinalName())
                    .age(clientDetailsDTO.getAge())
                    .build();

        }
        return null;
    }
}
